package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhotoDTO {
    private Long id;
    private String path;
    private AdvertisementDTO advertisement;

    public PhotoDTO(Photo photo) {
        this.id = photo.getId();
        this.path = photo.getPath();
        this.advertisement = new AdvertisementDTO(photo.getAdvertisement());
    }
}
