package com.uns.ftn.agentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.agentservice.client.AccountClient;
import com.uns.ftn.agentservice.client.CatalogClient;
import com.uns.ftn.agentservice.components.QueueProducer;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.PriceListItem;
import com.uns.ftn.agentservice.domain.User;
import com.uns.ftn.agentservice.domain.Vehicle;
import com.uns.ftn.agentservice.dto.*;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
import com.uns.ftn.agentservice.repository.VehicleRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;

@Service
public class AdvertisementService {

    private final AdvertisementRepository adRepo;
    private final VehicleRepository vehicleRepo;
    private final QueueProducer queueProducer;
    private final CatalogClient catalogClient;
    private final AccountClient accountClient;
    private final PriceListService priceListService;

    @Autowired
    public AdvertisementService(AdvertisementRepository adRepo, VehicleRepository vehicleRepo,
                                QueueProducer queueProducer, CatalogClient catalogClient,
                                PriceListService priceListService, AccountClient accountClient) {
        this.adRepo = adRepo;
        this.vehicleRepo = vehicleRepo;
        this.queueProducer = queueProducer;
        this.catalogClient = catalogClient;
        this.priceListService = priceListService;
        this.accountClient = accountClient;
    }

    public ResponseEntity<?> postNewAd(AdvertisementDTO adDTO) {

        // data validation
        if (!validateAdPostingData(adDTO)) {
            return new ResponseEntity<>("Invalid advertisement or vehicle data.", HttpStatus.BAD_REQUEST);
        }

        // data sanitization
        adDTO.setDescription(Encode.forHtml(adDTO.getDescription()));

        // check if gearbox, fuel, class and model exist ---> catalog-service
        String checker = adDTO.getVehicle().getModelId() + "-" + adDTO.getVehicle().getFuelTypeId() + "-" +
                adDTO.getVehicle().getGearboxTypeId() + "-" + adDTO.getVehicle().getVehicleClassId();

        CheckResponseDTO crd = catalogClient.checkIfResourcesExist(checker);
        if (!crd.getMessage().equals("All good.")) {
            return new ResponseEntity<>(crd.getMessage(), HttpStatus.NOT_FOUND);
        }

        int numberOfAds = accountClient.getNumberOfPostedAds(adDTO.getOwnerId());

        if (numberOfAds >= 3) {
            throw new BadRequestException("You cannot post more than three advertisements as a simple user! " +
                    "Sign up as agent for unlimited access.");
        }

        // setting advertisement properties
        Advertisement ad = new Advertisement();

        PriceListItem priceItem = priceListService.findOneItem(adDTO.getPriceListItemId());
        ad.setPrice(priceItem.getDailyPrice());
        ad.setPriceListItem(priceItem);

        ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
        ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
        ad.setDescription(adDTO.getDescription());
        ad.setOwnerId(adDTO.getOwnerId());
        ad.setLocation(adDTO.getLocation());

        // setting vehicle properties
        Vehicle vehicle = new Vehicle();
        vehicle.setChildSeatNumber(adDTO.getVehicle().getChildSeatNumber());
        vehicle.setKilometersTraveled(adDTO.getVehicle().getKilometersTraveled());
        vehicle.setHasAndroid(adDTO.getVehicle().getHasAndroid());
        vehicle.setFuelTypeId(adDTO.getVehicle().getFuelTypeId());
        vehicle.setGearboxTypeId(adDTO.getVehicle().getGearboxTypeId());
        vehicle.setVehicleClassId(adDTO.getVehicle().getVehicleClassId());
        vehicle.setModelId(adDTO.getVehicle().getModelId());

        adRepo.save(ad);
        vehicle.setAdvertisement(ad);
        vehicleRepo.save(vehicle);

        try {
            ad.setVehicle(vehicle);
            queueProducer.produce(new AdvertisementDTO(ad));
            if (numberOfAds != -1) {
                numberOfAds++;
                queueProducer.producePublishedAdCount(new AdCountDTO(ad.getOwnerId(), numberOfAds));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.CREATED);
    }

    public Boolean getUserHasAds(Long id) {
        if (adRepo.findByOwnerIdAndAndDeleted(id, false).isEmpty()) {
            return false;
        }
        return true;
    }

    public ResponseEntity<?> updateAdvertisement(Long id, AdvertisementUpdateDTO adto) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        PriceListItem priceItem = priceListService.findOneItem(adto.getPriceListItemId());

        if (adto.getDescription() == null || adto.getDescription().equals("") ||
                !pattern.matcher(adto.getDescription()).matches()) {
            throw new BadRequestException("Data is not well formed.");
        }

        Advertisement ad = findById(id);
        if (ad == null || ad.getDeleted()) {
            throw new NotFoundException("Requested advertisement does not exist.");
        }

        ad.setDescription(Encode.forHtml(adto.getDescription()));
        ad.setPrice(priceItem.getDailyPrice());
        ad.setPriceListItem(priceItem);
        ad = adRepo.save(ad);

        try {
            queueProducer.produce(new AdvertisementDTO(ad));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAdvertisement(Long adId) {
        Advertisement advertisement = findById(adId);

        if (advertisement == null) {
            throw new NotFoundException("Requested advertisement does not exist.");
        }

        advertisement.setDeleted(true);
        advertisement = save(advertisement);

        try {
            queueProducer.produce(new AdvertisementDTO(advertisement));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new AdvertisementDTO(advertisement), HttpStatus.OK);
    }

    /*
     * Returns TRUE if data is valid, else returns FALSE.
     */
    private Boolean validateAdPostingData(AdvertisementDTO adDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        String lrx = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        Pattern pattern = Pattern.compile(regex);
        Pattern lpattern = Pattern.compile(lrx);

        if (adDTO.getDescription() == null || adDTO.getDescription().trim().equals("") ||
                !pattern.matcher(adDTO.getDescription().trim()).matches() || adDTO.getPrice() < 0 ||
                adDTO.getVehicle().getKilometersTraveled() < 0 || adDTO.getVehicle().getChildSeatNumber() < 0 ||
                adDTO.getLocation() == null || adDTO.getLocation().trim().equals("") ||
                !lpattern.matcher(adDTO.getLocation().trim()).matches()) {
            return false;
        }

        return true;
    }

    public List<Advertisement> findByOwner(Long id) {
        return adRepo.findByOwnerId(id);
    }

    public Advertisement findById(Long id) {
        return adRepo.findById(id).orElse(null);
    }

    public Advertisement save(Advertisement advertisement) {
        return adRepo.save(advertisement);
    }

    /* START: Methods for checking when deleting catalog item. */
    public ResponseEntity<?> checkForModel(Long id) {
        Set<Vehicle> vehicles = vehicleRepo.findAllByModelId(id);
        if (!vehicles.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public ResponseEntity<?> checkForClass(Long id) {
        Set<Vehicle> vehicles = vehicleRepo.findAllByVehicleClassId(id);
        if (!vehicles.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public ResponseEntity<?> checkForGearbox(Long id) {
        Set<Vehicle> vehicles = vehicleRepo.findAllByGearboxTypeId(id);
        if (!vehicles.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public ResponseEntity<?> checkForFuel(Long id) {
        Set<Vehicle> vehicles = vehicleRepo.findAllByFuelTypeId(id);
        if (!vehicles.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    /* END: Methods for checking when deleting catalog item. */

    public ResponseEntity<?> generateDebt(Long adId, int numberOfDays, int kmTraveled) {
        double retval = 0;
        Advertisement advertisement = findById(adId);

        if (advertisement == null) {
            throw new NotFoundException("Requested advertisement does not exist.");
        }

        if (advertisement.getKilometersPerDayLimit() > 0 && advertisement.getKilometersPerDayLimit() * numberOfDays < kmTraveled) {
            double debtPrice = advertisement.getPriceListItem().getDebtPrice();
            int kmLimit = advertisement.getKilometersPerDayLimit() * numberOfDays;
            int kmDifference = kmTraveled - kmLimit;
            retval = kmDifference * debtPrice;
        }

        advertisement.getVehicle().setKilometersTraveled(advertisement.getVehicle().getKilometersTraveled() + kmTraveled);
        vehicleRepo.save(advertisement.getVehicle());

        try {
            queueProducer.produce(new AdvertisementDTO(advertisement));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(retval, HttpStatus.OK);
    }

    /*
     * Collecting data used for statistic report.
     */
    public ResponseEntity<?> returnStatisticReport(Long id) {
        List<StatisticDTO> bestRated = findStatisticalAds(id, "Rating");
        List<StatisticDTO> mostCommented = findStatisticalAds(id, "Commented");
        List<StatisticDTO> mostTraveled = findStatisticalAds(id, "Traveled");

        return new ResponseEntity<>(new StatisticReportDTO(bestRated, mostCommented, mostTraveled), HttpStatus.OK);
    }

    private List<StatisticDTO> findStatisticalAds(Long ownerId, String statType) {
        List<Advertisement> ownersAds = adRepo.findByOwnerId(ownerId);
        List<StatisticDTO> retval = new ArrayList<>();

        switch (statType) {
            // best rated advertisements
            case "Rating":
                ownersAds.sort(comparing(Advertisement::getRating).reversed());
                if (ownersAds.size() > 5) {
                    ownersAds = ownersAds.subList(0, 5);
                }

                for (Advertisement ad : ownersAds) {
                    retval.add(new StatisticDTO(ad));
                }

                break;
            // most commented advertisements
            case "Commented":
                for (int i = 0; i < ownersAds.size() - 1; i++) {
                    for (int j = i + 1; j < ownersAds.size(); j++) {
                        if (ownersAds.get(i).getComments().size() < ownersAds.get(j).getComments().size()) {
                            Collections.swap(ownersAds, i, j);
                        }
                    }
                }

                if (ownersAds.size() > 5) {
                    ownersAds = ownersAds.subList(0, 5);
                }

                for (Advertisement ad : ownersAds) {
                    retval.add(new StatisticDTO(ad));
                }

                break;
            // vehicles with longest distance traveled
            case "Traveled":
                List<Vehicle> ownersVehicles = new ArrayList<>();

                for (Advertisement ad : ownersAds) {
                    ownersVehicles.add(ad.getVehicle());
                }

                ownersVehicles.sort(comparing(Vehicle::getKilometersTraveled).reversed());
                if (ownersAds.size() > 5) {
                    ownersVehicles = ownersVehicles.subList(0, 5);
                }

                for (Vehicle v : ownersVehicles) {
                    retval.add(new StatisticDTO(v.getAdvertisement()));
                }

                break;
            // return empty list by default
            default:
                break;
        }

        // DTO with three lists, each containing TOP5 (or less) advertisements by different criteria
        return retval;
    }

}
