package com.uns.ftn.searchservice.dto;

import com.uns.ftn.searchservice.domain.Photo;
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
    private Long advertisementId;

    public PhotoDTO(Photo photo) {
        this.id = photo.getId();
        this.path = photo.getPath();
        this.advertisementId = photo.getAdvertisement().getId();
    }
}
