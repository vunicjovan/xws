package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.domain.*;
import com.uns.ftn.agent.dto.*;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.*;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.CommentResponse;
import rs.ac.uns.ftn.catalog.NewAdvertisementResponse;
import rs.ac.uns.ftn.catalog.NewCommentResponse;
import rs.ac.uns.ftn.catalog.NewRentingIntervalResponse;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class AdvertisementService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AdvertisementRepository advertisementRepository;
    private VehicleRepository vehicleRepository;
    private CatalogService catalogService;
    private AdvertisementClient advertisementClient;
    private AdWrapperRepository adWrapperRepository;
    private CommentRepository commentRepository;
    private final RentingIntervalRepository rentingIntervalRepository;

    @Autowired
    public AdvertisementService(
            AdvertisementRepository advertisementRepository,
            VehicleRepository vehicleRepository,
            CatalogService catalogService,
            AdvertisementClient advertisementClient,
            AdWrapperRepository adWrapperRepository,
            CommentRepository commentRepository,
            RentingIntervalRepository rentingIntervalRepository) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.catalogService = catalogService;
        this.advertisementClient = advertisementClient;
        this.adWrapperRepository = adWrapperRepository;
        this.commentRepository = commentRepository;
        this.rentingIntervalRepository = rentingIntervalRepository;
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
        logger.info("Add new advertisement with");

        if (!validateAdPostingData(adDTO)) {
            logger.error("Invalid or corrupted data of advertisement or vehicle");
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
        logger.info("Vehicle with id {} has been saved", vehicle.getId());

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
        logger.info("Advertisement with id {} has been saved", ad.getId());

        vehicle.setAdvertisement(ad);
        saveVehicle(vehicle);
        logger.info("Advertisment with id {} has been assigned to vehicle with id {}", ad.getId(), vehicle.getId());

        NewAdvertisementResponse response = advertisementClient.newAdvertisement(new AdvertisementDTO(ad));
        if (response != null) {
            logger.info("Advertisement has been successfully save in microservices database with id {}",
                    response.getAdvertisement().getId());
            AdWrapper adWrapper = new AdWrapper();
            adWrapper.setRemoteId(response.getAdvertisement().getId());
            adWrapper.setAdvertisementId(ad.getId());
            adWrapperRepository.save(adWrapper);
            logger.info("Saving wrapper for advertisement with id {}", response.getAdvertisement().getId());
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
        logger.info("Adding renting interval");
        NewRentingIntervalResponse response = advertisementClient.newRentingInterval(rentingIntervalDTO);

        RentingIntervalDTO responseDTO = new RentingIntervalDTO();
        RentingInterval rentingInterval = new RentingInterval();
        try {
            rentingInterval.setStartDate(rentingIntervalDTO.getStartDate());
            rentingInterval.setEndDate(rentingIntervalDTO.getEndDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Renting interval with start date {} and end date {} has been successfully save in microservices database",
                rentingIntervalDTO.getStartDate(), responseDTO.getEndDate());

        return new RentingIntervalDTO(response.getRentingInterval());
    }

    public PublisherCommentDTO publisherPostComment(PublisherCommentDTO publisherCommentDTO) {
        logger.info("Publishing new comment from user with id {}", publisherCommentDTO.getUserId());
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
        logger.info("Getting statistic report for user with id {}", id);
        List<StatisticDTO> bestRated = findStatisticalAds(id, "Rating");
        List<StatisticDTO> mostCommented = findStatisticalAds(id, "Commented");
        List<StatisticDTO> mostTraveled = findStatisticalAds(id, "Traveled");

        return new ResponseEntity<>(new StatisticReportDTO(bestRated, mostCommented, mostTraveled), HttpStatus.OK);
    }

    private List<StatisticDTO> findStatisticalAds(Long ownerId, String statType) {
        logger.info("Gathering {} type statistic", statType);
        List<Advertisement> ownersAds = advertisementRepository.findByOwnerId(ownerId);
        logger.info("Retrieving advertisements created by user with id {}", ownerId);
        List<StatisticDTO> retval = new ArrayList<>();

        switch (statType) {
            // best rated advertisements
            case "Rating":
                logger.debug("Processing advertisements for best rating statistic");
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
                logger.debug("Processing advertisements for most commented ad statistic");
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
                logger.debug("Processing advertisements for longest distance traveled statistic");
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

    public List<DetailedAdvertisementDTO> getDetailedAdvertisements() {
        logger.info("Retrieving advertisement and comments");
        CommentResponse commentResponse = advertisementClient.getComments((long) 2);
        List<Advertisement> advertisements = advertisementRepository.findAll();
        List<DetailedAdvertisementDTO> detailedAdvertisementDTOS = new ArrayList<>();
        logger.info("Advertisements have been retrieved from database");

        advertisements.forEach(advertisement -> {

            List<CommentDTO> commentDTOList = advertisement.getComments()
                    .stream()
                    .map(comment -> new CommentDTO(comment.getId(), comment.getTitle(), comment.getContent()))
                    .collect(Collectors.toList());

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
                        comment = commentRepository.save(comment);
                        logger.info("Comment with id {} and title {} has been save", comment.getId(), comment.getTitle());
                        commentDTOList.add(new CommentDTO(comment.getId(), comment.getTitle(), comment.getContent()));
                    }
                }
            });

            detailedAdvertisementDTOS.add(new DetailedAdvertisementDTO(advertisement, commentDTOList));
        });

        return detailedAdvertisementDTOS;
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
        logger.debug("Retrieving all renting intervals");
        return rentingIntervalRepository.findAll().stream().collect(Collectors.toSet());
    }

    public RentingInterval save(RentingInterval rentingInterval) {
        logger.info("Saving renting interval");
        return rentingIntervalRepository.save(rentingInterval);
    }

}
