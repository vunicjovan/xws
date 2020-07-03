package com.uns.ftn.searchservice.service;

import com.uns.ftn.searchservice.domain.Advertisement;
import com.uns.ftn.searchservice.domain.RentingInterval;
import com.uns.ftn.searchservice.dto.SimpleAdvertisementDTO;
import com.uns.ftn.searchservice.exceptions.BadRequestException;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private AdvertisementService advertisementService;

    @Autowired
    public SearchService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    public ResponseEntity<?> simpleSearch(String location, Date startDate, Date endDate) {
        String lrx = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        Pattern lpattern = Pattern.compile(lrx);

        if (!lpattern.matcher(location).matches()) {
            throw new BadRequestException("Invalid location format.");
        }

        location = Encode.forHtml(location);
        List<Advertisement> advertisements = advertisementService.findByLocationLike(location);

        return new ResponseEntity<>(findAvailable(advertisements, startDate, endDate), HttpStatus.OK);
    }

    public List<SimpleAdvertisementDTO> advancedSearch(String location, Date startDate, Date endDate, Long brandId,
                                                       Long modelId, Long fuelId, Long gearboxId, Long classId,
                                                       Double minPrice, Double maxPrice, Integer kmTraveled, Integer kmPlaned,
                                                       Boolean cdw, Integer childrenSeatNum) {

        String lrx = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        Pattern lpattern = Pattern.compile(lrx);

        if (!lpattern.matcher(location).matches()) {
            throw new BadRequestException("Invalid location format.");
        }

        location = Encode.forHtml(location);
        List<Advertisement> advertisements = advertisementService.findByLocationLike(location);

        List<Advertisement> adRet = advertisements.stream().filter(ad -> {
            if (brandId != null && brandId != 0 && !ad.getVehicle().getModel().getBrand().getId().equals(brandId)) {
                return false;
            }

            if (modelId != null && modelId != 0 && !ad.getVehicle().getModel().getId().equals(modelId)) {
                return false;
            }

            if (fuelId != null && fuelId != 0 && !ad.getVehicle().getFuelType().getId().equals(fuelId)) {
                return false;
            }

            if (gearboxId != null && gearboxId != 0 && !ad.getVehicle().getGearboxType().getId().equals(gearboxId)) {
                return false;
            }

            if (classId != null && classId != 0 && !ad.getVehicle().getVehicleClass().getId().equals(classId)) {
                return false;
            }

            if ((minPrice != null && minPrice > ad.getPrice()) || (maxPrice != null && maxPrice < ad.getPrice())) {
                return false;
            }

            if (kmTraveled != null && kmTraveled != 0 && kmTraveled != ad.getVehicle().getKilometersTraveled()) {
                return false;
            }

            if (kmPlaned != null && kmPlaned != 0 && kmPlaned > ad.getKilometersPerDayLimit()) {
                return false;
            }

            if (cdw != null && cdw != ad.getCollisionDamageWaiver()) {
                return false;
            }

            if (childrenSeatNum != null && childrenSeatNum != 0 && ad.getVehicle().getChildSeatNumber() < childrenSeatNum) {
                return false;
            }

            return true;
        }).collect(Collectors.toList());

        return findAvailable(adRet, startDate, endDate);
    }

    private List<SimpleAdvertisementDTO> findAvailable
            (List<Advertisement> advertisements, Date startDate, Date endDate) {
        List<SimpleAdvertisementDTO> advertisementDTOList = new ArrayList<>();

        for(Advertisement ad : advertisements) {
            if(ad.getRentingIntervals().isEmpty()) {
                advertisementDTOList.add(new SimpleAdvertisementDTO(ad));
            } else {
                if(!findIfRangeOverlaps(ad.getRentingIntervals(), startDate, endDate)) {
                    advertisementDTOList.add(new SimpleAdvertisementDTO(ad));
                }
            }
        }
        return advertisementDTOList;
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

}
