package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.DetailedAdvertisementDTO;
import com.uns.ftn.viewservice.dto.SimpleAdvertisementDTO;
import com.uns.ftn.viewservice.exceptions.NotFoundException;
import com.uns.ftn.viewservice.repository.AdvertisementRepository;
import com.uns.ftn.viewservice.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ViewService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ModelRepository modelRepository;

    public Set<SimpleAdvertisementDTO> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        Set<SimpleAdvertisementDTO> simpleAdvertisementDTOSet = new HashSet<>();

        for (Advertisement advertisement : advertisements) {
            Vehicle vehicle = advertisement.getVehicle();
            Model model = modelRepository.findById(vehicle.getModelId()).orElse(null);
            Set<Photo> photos = advertisement.getPhotos();
            Set<String> photoPaths = new HashSet<>();
            photos.forEach(photo -> photoPaths.add(photo.getPath()));
            simpleAdvertisementDTOSet.add(new SimpleAdvertisementDTO(
                    advertisement.getId(),
                    advertisement.getPrice(),
                    advertisement.getPlace(),
                    model.getBrand().getName(),
                    model.getName(),
                    photoPaths
            ));
        }

        return simpleAdvertisementDTOSet;
    }

    public DetailedAdvertisementDTO getAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advertisement with that id does not exist!"));

        Model model = modelRepository.findById(advertisement.getVehicle().getModelId()).orElse(null);
        Set<Photo> photos = advertisement.getPhotos();
        Set<String> photoPaths = new HashSet<>();
        photos.forEach(photo -> photoPaths.add(photo.getPath()));
        return null;
    }

}
