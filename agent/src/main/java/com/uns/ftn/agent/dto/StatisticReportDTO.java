package com.uns.ftn.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StatisticReportDTO {

    private List<StatisticDTO> bestRatedAds;
    private List<StatisticDTO> mostCommentedAds;
    private List<StatisticDTO> mostKmTraveledAds;

}
