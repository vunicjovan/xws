package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.service.PhotoService;
import com.uns.ftn.agentservice.service.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping(value = "/images")
public class PhotoController {

    PhotoService photoService;

    @Autowired
    PhotoController(PhotoServiceImpl photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> handleFileUpload(@RequestParam("files")MultipartFile[] files, @PathVariable("id") Long id) throws IOException, URISyntaxException {

        photoService.store(files, id);

        return null;
    }

    /**
     *
     * @param filename - image name can be read from Photo table
     * @param id - ad id, finds folder using id
     * @return Resource as ByteArray
     *
     */
    @GetMapping("/{id}/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, @PathVariable("id") Long id) throws IOException {

        Resource file = photoService.loadAsResource(filename, id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
