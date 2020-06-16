package com.uns.ftn.agent.service;

import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.domain.Vehicle;
import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.dto.StatisticDTO;
import com.uns.ftn.agent.dto.StatisticReportDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.repository.AdvertisementRepository;
import com.uns.ftn.agent.repository.VehicleRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;

@Service
public class AdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private VehicleRepository vehicleRepository;
    private CatalogService catalogService;

    @Autowired
    public AdvertisementService(
            AdvertisementRepository advertisementRepository,
            VehicleRepository vehicleRepository,
            CatalogService catalogService
    ) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.catalogService = catalogService;
    }

    public Advertisement saveAd(Advertisement advertisement) { return advertisementRepository.save(advertisement); }
    public Vehicle saveVehicle(Vehicle vehicle) { return vehicleRepository.save(vehicle); }

    public ResponseEntity<?> addNewAdvertisement(AdvertisementDTO adDTO) {

        if (!validateAdPostingData(adDTO)) {
            throw new BadRequestException("Invalid advertisement or vehicle data.");
        }

        adDTO.setDescription(Encode.forHtml(adDTO.getDescription()));
        adDTO.setLocation(Encode.forHtml(adDTO.getLocation()));

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(catalogService.findOneModel(adDTO.getVehicle().getModelId()));
        vehicle.setFuelType(catalogService.findOneFuelType(adDTO.getVehicle().getFuelTypeId()));
        vehicle.setGearboxType(catalogService.findOneGearboxType(adDTO.getVehicle().getGearboxTypeId()));
        vehicle.setVehicleClass(catalogService.findOneVehicleClass(adDTO.getVehicle().getVehicleClassId()));
        vehicle.setChildSeatNumber(adDTO.getVehicle().getChildSeatNumber());
        vehicle.setHasAndroid(adDTO.getVehicle().getHasAndroid());
        vehicle.setKilometersTraveled(adDTO.getVehicle().getKilometersTraveled());
        vehicle = saveVehicle(vehicle);

        Advertisement ad = new Advertisement();
        ad.setVehicle(vehicle);
        ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
        ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
        ad.setDescription(adDTO.getDescription());
        ad.setLocation(adDTO.getLocation());
        ad.setOwnerId(2L);
        ad.setRating(0.0);
        ad.setPrice(adDTO.getPrice());
        ad = saveAd(ad);

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.CREATED);
    }

    private Boolean validateAdPostingData(AdvertisementDTO adDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,;\\s?]+)$";
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
        List<Advertisement> ownersAds = advertisementRepository.findByOwnerId(ownerId);
        List<StatisticDTO> retval = new ArrayList<>();

        switch (statType) {
            // best rated advertisements
            case "Rating":
                ownersAds.sort(comparing(Advertisement::getRating).reversed());
                if (ownersAds.size() > 5) {
                    ownersAds = ownersAds.subList(0, 5);
                }

                for (Advertisement ad : ownersAds) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ad.getVehicle().getModel().getBrand().getName()).append(" ")
                            .append(ad.getVehicle().getModel().getName());

                    retval.add(new StatisticDTO(ad, sb.toString()));
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
                    StringBuilder sb = new StringBuilder();
                    sb.append(ad.getVehicle().getModel().getBrand().getName()).append(" ")
                            .append(ad.getVehicle().getModel().getName());

                    retval.add(new StatisticDTO(ad, sb.toString()));
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
                    StringBuilder sb = new StringBuilder();
                    sb.append(v.getModel().getBrand().getName()).append(" ").append(v.getModel().getName());

                    retval.add(new StatisticDTO(v.getAdvertisement(), sb.toString()));
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
