package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.domain.AdWrapper;
import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.domain.Photo;
import com.uns.ftn.agent.dto.PhotoRequestDTO;
import com.uns.ftn.agent.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.catalog.NewPhotoRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    PhotoRepository photoRepository;
    AdvertisementService advertisementService;
    AdvertisementClient advertisementClient;

    @Autowired
    public PhotoService(PhotoRepository photoRepository,
                        AdvertisementService advertisementService,
                        AdvertisementClient advertisementClient) {
        this.photoRepository = photoRepository;
        this.advertisementService = advertisementService;
        this.advertisementClient = advertisementClient;
    }

    public void store(MultipartFile[] files, Long adId) throws IOException, URISyntaxException {
        String folder = "images/" + adId + "/";
        Advertisement advertisement = advertisementService.findOne(adId);
        AdWrapper adWrapper = advertisementService.findOneAdWrapper(adId);
        if(adWrapper != null) {
            Long remoteId = adWrapper.getRemoteId();
            for (MultipartFile file : files) {
                Photo photo = new Photo();
                byte[] bytes = file.getBytes();
                Files.createDirectories(Paths.get(folder));
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);
                photo.setPath(file.getOriginalFilename());
                photo.setAdvertisement(advertisement);
                photoRepository.save(photo);
                PhotoRequestDTO request = new PhotoRequestDTO();
                request.setBytes(bytes);
                request.setPath(photo.getPath());
                request.setId(new Long(remoteId));
                advertisementClient.newPhoto(request);
            }
        }
    }

    public Resource loadAsResource(String filename, Long adId) throws IOException {
        String folder = "images/" + adId + "/";
        byte[] bytes = Files.readAllBytes(Paths.get(folder + filename));

        return new ByteArrayResource(bytes);
    }

}
