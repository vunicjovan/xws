package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.AndroidLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AndroidLocationDTO {

    private Long id;
    private String token;
    private double latitude;
    private double longitude;

    public AndroidLocationDTO(AndroidLocation location) {
        this.id = location.getId();
        this.token = location.getToken();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

}
