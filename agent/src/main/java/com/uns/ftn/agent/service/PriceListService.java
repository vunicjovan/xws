package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.domain.PriceList;
import com.uns.ftn.agent.domain.PriceListItem;
import com.uns.ftn.agent.dto.PriceListDTO;
import com.uns.ftn.agent.dto.PriceListItemDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.PriceListItemRepository;
import com.uns.ftn.agent.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.NewDiscountResponse;
import rs.ac.uns.ftn.catalog.PriceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceListService {

    private PriceListItemRepository priceListItemRepository;
    private AdvertisementClient advertisementClient;
    private PriceListRepository priceListRepository;

    @Autowired
    public PriceListService(PriceListItemRepository priceListItemRepository,
                            AdvertisementClient advertisementClient,
                            PriceListRepository priceListRepository) {
        this.priceListItemRepository = priceListItemRepository;
        this.advertisementClient = advertisementClient;
        this.priceListRepository = priceListRepository;
    }

    public PriceListDTO getPriceList() {
        return new PriceListDTO(findOne());
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
        priceListItem.setPriceList(findOne());

        priceListItemDTO.setCreatorId((long) 2);
        PriceResponse priceResponse = advertisementClient.createPriceListItem(priceListItemDTO);

        if(priceResponse != null) {
            priceListItem.setServicesId(priceResponse.getPriceListItem().getServiceId());
        }
        priceListItem = priceListItemRepository.save(priceListItem);

        return new PriceListItemDTO(priceListItem);
    }

    public PriceList findOne() {
        return priceListRepository.findById((long) 1)
                .orElseThrow(() -> new BadRequestException("Could not retrieve price list!"));
    }

    public String createDiscount(double discount) {
        PriceList priceList = findOne();

        if (discount < 0 || discount > 1) {
            throw new BadRequestException("Discount must be between 0% and 100%");
        }

        NewDiscountResponse discountResponse = advertisementClient.createDiscount((long) 2, discount);

        if (discountResponse == null) {
            throw new BadRequestException("Could not create discount!");
        }

        priceList.setDiscount(discount);
        priceListRepository.save(priceList);

        return "Discount successfully created.";
    }

}
