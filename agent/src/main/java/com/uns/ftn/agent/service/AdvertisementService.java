package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.domain.*;
import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.domain.Comment;
import com.uns.ftn.agent.domain.PriceListItem;
import com.uns.ftn.agent.domain.RentingInterval;
import com.uns.ftn.agent.domain.Vehicle;
import com.uns.ftn.agent.dto.*;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.*;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class AdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private VehicleRepository vehicleRepository;
    private CatalogService catalogService;
    private AdvertisementClient advertisementClient;
    private AdWrapperRepository adWrapperRepository;
    private CommentRepository commentRepository;
    private final RentingIntervalRepository rentingIntervalRepository;
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    public AdvertisementService(
            AdvertisementRepository advertisementRepository,
            VehicleRepository vehicleRepository,
            CatalogService catalogService,
            AdvertisementClient advertisementClient,
            AdWrapperRepository adWrapperRepository,
            CommentRepository commentRepository,
            RentingIntervalRepository rentingIntervalRepository,
            PriceListItemRepository priceListItemRepository) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.catalogService = catalogService;
        this.advertisementClient = advertisementClient;
        this.adWrapperRepository = adWrapperRepository;
        this.commentRepository = commentRepository;
        this.rentingIntervalRepository = rentingIntervalRepository;
        this.priceListItemRepository = priceListItemRepository;
    }

    public Advertisement saveAd(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public AdWrapper findOneAdWrapper(Long id) {
        return adWrapperRepository.findByAdvertisementId(id);
    }

    public Advertisement findOne(Long id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist"));
    }

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

        PriceListItem priceListItem = priceListItemRepository.findById(adDTO.getPriceListItemDTO().getId())
                .orElseThrow(() -> new NotFoundException("Requested price list item doesn't exist"));
        Advertisement ad = new Advertisement();
        ad.setVehicle(vehicle);
        ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
        ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
        ad.setDescription(adDTO.getDescription());
        ad.setLocation(adDTO.getLocation());
        ad.setOwnerId(2L);
        ad.setRating(0.0);
        ad.setPrice(priceListItem.getDailyPrice());
        ad.setPriceListItem(priceListItem);
        ad = saveAd(ad);

        vehicle.setAdvertisement(ad);
        saveVehicle(vehicle);

        NewAdvertisementResponse response = advertisementClient.newAdvertisement(new AdvertisementDTO(ad));
        if (response != null) {
            AdWrapper adWrapper = new AdWrapper();
            adWrapper.setRemoteId(response.getAdvertisement().getId());
            adWrapper.setAdvertisementId(ad.getId());
            adWrapperRepository.save(adWrapper);
        }

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.CREATED);
    }

    public RentingIntervalDTO manuallyAddInterval(RentingIntervalDTO rentingIntervalDTO) {
//        Advertisement advertisement = advertisementRepository.findById(rentingIntervalDTO.getAdvertisementId()).
//                orElse(null);
//
//        if (advertisement == null) {
//            throw new BadRequestException("Renting interval does not exist.");
//        }
//
//        if (rentingIntervalDTO.getStartDate().after(rentingIntervalDTO.getEndDate())) {
//            throw new BadRequestException("Starting date cannot be after ending date.");
//        }
//
//        RentingInterval rentingInterval = new RentingInterval();
//        rentingInterval.setAdvertisement(advertisement);
//        rentingInterval.setStartDate(rentingIntervalDTO.getStartDate());
//        rentingInterval.setEndDate(rentingIntervalDTO.getEndDate());
//
//        if (!findIfRangeOverlaps(getAll(), rentingIntervalDTO.getStartDate(), rentingIntervalDTO.getEndDate())) {
//            save(rentingInterval);
////            return new ResponseEntity<> (new RentingIntervalDTO(rentingInterval), HttpStatus.CREATED);
//            return new RentingIntervalDTO(rentingInterval);
//        } else {
//            throw new BadRequestException("It is not possible to fit in desired renting interval. Please choose another.");
//        }
        NewRentingIntervalResponse response = advertisementClient.newRentingInterval(rentingIntervalDTO);

        RentingIntervalDTO responseDTO = new RentingIntervalDTO();
        RentingInterval rentingInterval = new RentingInterval();
        try {
            rentingInterval.setStartDate(rentingIntervalDTO.getStartDate());
            rentingInterval.setEndDate(rentingIntervalDTO.getEndDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RentingIntervalDTO(response.getRentingInterval());
    }

    public PublisherCommentDTO publisherPostComment(PublisherCommentDTO publisherCommentDTO) {
        NewCommentResponse response = advertisementClient.newPublisherComment(publisherCommentDTO);

        return new PublisherCommentDTO(response.getComment());
    }

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

    public DetailedAdvertisementDTO getOneDetailedAdvertisement(Long id) {
        updateComments();
        return new DetailedAdvertisementDTO(findOne(id));
    }

    public List<DetailedAdvertisementDTO> getDetailedAdvertisements() {
        updateComments();

        return advertisementRepository.findAll().stream().map(DetailedAdvertisementDTO::new)
                .collect(Collectors.toList());
    }

    private void updateComments() {
        CommentResponse commentResponse = advertisementClient.getComments((long) 1);
        List<Advertisement> advertisements = advertisementRepository.findAll();

        advertisements.forEach(advertisement -> {
            /*
             * For all comments retrieved from microservices database check if there exist comment,
             * posted on current advertisement, that is not present in agent database and save that comment.
             */
            commentResponse.getComments().forEach(servicesComment -> {

                if (servicesComment.getAdvertisementId() == advertisement.getId()) {

                    Comment comment = advertisement.getComments()
                            .stream()
                            .filter(comm -> comm.getId() == servicesComment.getId())
                            .findFirst()
                            .orElse(null);

                    if (comment == null) {
                        comment = new Comment();
                        comment.setTitle(servicesComment.getTitle());
                        comment.setContent(servicesComment.getContent());
                        comment.setAllowed(servicesComment.isAllowed());
                        comment.setUserId(servicesComment.getUserId());
                        comment.setAdvertisement(advertisement);
                        commentRepository.save(comment);
                    }
                }
            });
        });
    }

    private Boolean findIfRangeOverlaps(Set<RentingInterval> rentingIntervals, Date startDate, Date endDate) {
        Boolean overlaps = false;
        for(RentingInterval rentingInterval : rentingIntervals) {
            if (!(endDate.before(rentingInterval.getStartDate()) || startDate.after(rentingInterval.getEndDate()))){
                overlaps = true;
                break;
            }
        }
        return overlaps;
    }

    public Set<RentingInterval> getAll() {
        return rentingIntervalRepository.findAll().stream().collect(Collectors.toSet());
    }

    public RentingInterval save(RentingInterval rentingInterval) {
        return rentingIntervalRepository.save(rentingInterval);
    }

    public UpdateAdvertisementDTO updateAdvertisement(UpdateAdvertisementDTO advertisementDTO) {

        validateUpdateData(advertisementDTO);

        AdWrapper adWrapper = findOneAdWrapper(advertisementDTO.getAdvertisementId());

        PriceListItem priceListItem = priceListItemRepository.findById(advertisementDTO.getPriceListItemId())
                            .orElseThrow(() -> new NotFoundException("Requested price list item doesn't exist"));

        Advertisement advertisement = findOne(advertisementDTO.getAdvertisementId());
        advertisement.setDescription(advertisementDTO.getDescription());
        advertisement.setPriceListItem(priceListItem);
        advertisement.setPrice(priceListItem.getDailyPrice());
        advertisementRepository.save(advertisement);



        if(adWrapper != null) {
            advertisementDTO.setAdvertisementId(adWrapper.getRemoteId());
            advertisementDTO.setPriceListItemId(priceListItem.getServicesId());
            UpdateAdvertisementResponse advertisementResponse = advertisementClient.updateAdvertisement(advertisementDTO);
        }
        return new UpdateAdvertisementDTO(advertisement.getId(),
                priceListItem.getId(), advertisement.getDescription());
    }

    private void validateUpdateData(UpdateAdvertisementDTO advertisementDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (advertisementDTO.getDescription() == null || advertisementDTO.getDescription().trim().equals("") ||
                !pattern.matcher(advertisementDTO.getDescription().trim()).matches()) {
            throw new BadRequestException("Data not well formed!");
        }

        advertisementDTO.setDescription(Encode.forHtml(advertisementDTO.getDescription()));
    }

}
