package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.domain.PriceList;
import com.uns.ftn.agentservice.domain.PriceListItem;
import com.uns.ftn.agentservice.dto.PriceListItemDTO;
import com.uns.ftn.agentservice.repository.PriceListItemRepository;
import com.uns.ftn.agentservice.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListService {

    private PriceListRepository priceListRepository;
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository, PriceListItemRepository priceListItemRepository) {
        this.priceListRepository = priceListRepository;
        this.priceListItemRepository = priceListItemRepository;
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

}
