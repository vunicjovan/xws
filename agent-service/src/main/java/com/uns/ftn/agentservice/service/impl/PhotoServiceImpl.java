package com.uns.ftn.agentservice.service.impl;

import com.uns.ftn.agentservice.components.QueueProducer;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Photo;
import com.uns.ftn.agentservice.dto.PhotoDTO;
import com.uns.ftn.agentservice.repository.PhotoRepository;
import com.uns.ftn.agentservice.service.AdvertisementService;
import com.uns.ftn.agentservice.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;
    AdvertisementService advertisementService;
    QueueProducer queueProducer;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository,
                            AdvertisementService advertisementService,
                            QueueProducer queueProducer) {
        this.photoRepository = photoRepository;
        this.advertisementService = advertisementService;
        this.queueProducer = queueProducer;
    }

    @Override
    public void store(MultipartFile[] files, Long adId) throws IOException, URISyntaxException {
        String folder = "images/" + adId + "/";
        Advertisement advertisement = advertisementService.findById(adId);
        for(MultipartFile file : files) {
            Photo photo = new Photo();
            byte[] bytes = file.getBytes();
            Files.createDirectories(Paths.get(folder));
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            photo.setPath(file.getOriginalFilename());
            photo.setAdvertisement(advertisement);
            photoRepository.save(photo);
            queueProducer.producePhoto(new PhotoDTO(photo));
        }
    }

    @Override
    public Resource loadAsResource(String filename, Long adId) throws IOException {
        String folder = "images/" + adId + "/";
        byte[] bytes = Files.readAllBytes(Paths.get(folder + filename));

        return new ByteArrayResource(bytes);
    }
}
