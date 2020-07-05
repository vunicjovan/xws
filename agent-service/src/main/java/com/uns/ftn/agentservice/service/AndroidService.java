package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.domain.AndroidLocation;
import com.uns.ftn.agentservice.dto.AndroidLocationDTO;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.AndroidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AndroidService {

    private AndroidRepository androidRepository;

    @Autowired
    public AndroidService(AndroidRepository androidRepository) {
        this.androidRepository = androidRepository;
    }

    public void updateLocation(AndroidLocationDTO locationDTO) {
        AndroidLocation location = androidRepository.findByToken(locationDTO.getToken());

        if (location != null) {
            location.setLatitude(locationDTO.getLatitude());
            location.setLongitude(locationDTO.getLongitude());

            androidRepository.save(location);
        }
    }

    public ResponseEntity<?> getVehicleLocation(Long id) {
        AndroidLocation location = androidRepository.findByVehicle_Id(id);

        if (location == null) {
            throw new NotFoundException("Location for requested vehicle cannot be found.");
        }

        return new ResponseEntity<>(new AndroidLocationDTO(location), HttpStatus.OK);
    }

}
