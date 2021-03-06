package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.domain.PriceList;
import com.uns.ftn.agentservice.domain.PriceListItem;
import com.uns.ftn.agentservice.dto.PriceListDTO;
import com.uns.ftn.agentservice.dto.PriceListItemDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.PriceListItemRepository;
import com.uns.ftn.agentservice.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PriceListService {

    private PriceListRepository priceListRepository;
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository, PriceListItemRepository priceListItemRepository) {
        this.priceListRepository = priceListRepository;
        this.priceListItemRepository = priceListItemRepository;
    }

    public PriceListItem findOneItem(Long id) {
        return priceListItemRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Requested price list item does not exist."));
    }

    public PriceListItemDTO createPriceListItem(PriceListItemDTO priceListItemDTO) {
        PriceList priceList = priceListRepository.findByOwnerId(priceListItemDTO.getCreatorId());

        if (priceList == null) {
            priceList = new PriceList();
            priceList.setOwnerId(priceListItemDTO.getCreatorId());
            priceList = priceListRepository.save(priceList);
        }

        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setCdwPrice(priceListItemDTO.getCdwPrice());
        priceListItem.setDailyPrice(priceListItemDTO.getDailyPrice());
        priceListItem.setDebtPrice(priceListItemDTO.getDebtPrice());
        priceListItem.setPriceList(priceList);

        priceListItem = priceListItemRepository.save(priceListItem);

        return new PriceListItemDTO(priceListItem.getId(), priceListItem.getDailyPrice(), priceListItem.getCdwPrice(),
                priceListItem.getDebtPrice(), priceList.getOwnerId());
    }

    public ResponseEntity<?> getAllItemsForUser(Long id) {
        PriceList priceList = priceListRepository.findByOwnerId(id);
        if (priceList != null)
            return new ResponseEntity<>(new PriceListDTO(priceList), HttpStatus.OK);

        return new ResponseEntity<>(new PriceListDTO(), HttpStatus.OK);
    }

    public ResponseEntity<?> createDiscount(Long ownerId, double discount) {
        PriceList priceList = priceListRepository.findByOwnerId(ownerId);
        if (discount < 0 || discount > 1) {
            throw new BadRequestException("Discount must be between 0% and 100%");
        }

        if (priceList != null) {
            priceList.setDiscount(discount);
            priceListRepository.save(priceList);
            return new ResponseEntity<>("Discount successfully created.", HttpStatus.OK);
        }

        priceList = new PriceList();
        priceList.setDiscount(discount);
        priceList.setOwnerId(ownerId);
        priceListRepository.save(priceList);

        return new ResponseEntity<>("Discount successfully created.", HttpStatus.OK);
    }

}
