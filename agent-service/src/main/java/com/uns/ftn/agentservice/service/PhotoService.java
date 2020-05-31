package com.uns.ftn.agentservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PhotoService {

    void store(MultipartFile[] file, Long adId) throws IOException, URISyntaxException;

    Resource loadAsResource(String filename, Long adId) throws IOException;
}
