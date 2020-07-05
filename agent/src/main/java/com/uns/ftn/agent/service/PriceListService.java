package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.domain.PriceListItem;
import com.uns.ftn.agent.dto.PriceListItemDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.repository.PriceListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.PriceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceListService {

    private PriceListItemRepository priceListItemRepository;
    private AdvertisementClient advertisementClient;

    @Autowired
    public PriceListService(PriceListItemRepository priceListItemRepository, AdvertisementClient advertisementClient) {
        this.priceListItemRepository = priceListItemRepository;
        this.advertisementClient = advertisementClient;
    }

    public List<PriceListItemDTO> getPriceList() {
        return priceListItemRepository.findAll()
                .stream()
                .map(priceListItem -> new PriceListItemDTO(priceListItem))
                .collect(Collectors.toList());
    }

    public PriceListItemDTO createPriceListItem(PriceListItemDTO priceListItemDTO) {
        if (priceListItemDTO.getCdwPrice() < 0 || priceListItemDTO.getDailyPrice() < 0
                ||  priceListItemDTO.getDebtPrice() < 0) {
            throw new BadRequestException("Price cannot be negative!");
        }
        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setCdwPrice(priceListItemDTO.getCdwPrice());
        priceListItem.setDailyPrice(priceListItemDTO.getDailyPrice());
        priceListItem.setDebtPrice(priceListItemDTO.getDebtPrice());

        priceListItemDTO.setCreatorId((long) 2);
        PriceResponse priceResponse = advertisementClient.createPriceListItem(priceListItemDTO);

        if(priceResponse != null) {
            priceListItem.setServicesId(priceResponse.getPriceListItem().getServiceId());
        }
        priceListItem = priceListItemRepository.save(priceListItem);

        return new PriceListItemDTO(priceListItem);
    }

}
