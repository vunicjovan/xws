package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Advertisement;
import com.uns.ftn.viewservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private Long userId;
    private Set<AdvertisementDTO> ratedAds;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.ratedAds = user.getRatedAds().stream().map(AdvertisementDTO::new).collect(Collectors.toSet());
    }
}
