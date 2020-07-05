package com.uns.ftn.androidservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Coordinates {

    private List<Coordinate> coords = new ArrayList<>();

    public Coordinates() {
        coords.add(new Coordinate(45.223168, 20.025831));
        coords.add(new Coordinate(45.224022, 20.026324));
        coords.add(new Coordinate(45.224763, 20.026753));
        coords.add(new Coordinate(45.225005, 20.026088));
        coords.add(new Coordinate(45.225504, 20.024522));
        coords.add(new Coordinate(45.225957, 20.022848));
        coords.add(new Coordinate(45.226221, 20.021303));
        coords.add(new Coordinate(45.225012, 20.020949));
        coords.add(new Coordinate(45.223274, 20.020520));
        coords.add(new Coordinate(45.221332, 20.019973));
        coords.add(new Coordinate(45.221105, 20.021690));
        coords.add(new Coordinate(45.220916, 20.023117));
        coords.add(new Coordinate(45.222095, 20.023557));
        coords.add(new Coordinate(45.223546, 20.024265));
    }

}
