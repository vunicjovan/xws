package com.uns.ftn.searchservice.service;

import com.uns.ftn.searchservice.domain.Advertisement;
import com.uns.ftn.searchservice.domain.RentingInterval;
import com.uns.ftn.searchservice.dto.AdvertisementDTO;
import com.uns.ftn.searchservice.dto.SimpleAdvertisementDTO;
import com.uns.ftn.searchservice.specifications.AdvertisementSpecs;
import com.uns.ftn.searchservice.specifications.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class SearchService {

    private AdvertisementService advertisementService;

    @Autowired
    public SearchService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    public ResponseEntity<?> simpleSearch(String location, Date startDate, Date endDate) {

        List<Advertisement> advertisements = advertisementService.findByLocationLike(location);

        return new ResponseEntity<>(findAvailable(advertisements, startDate, endDate), HttpStatus.OK);
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
