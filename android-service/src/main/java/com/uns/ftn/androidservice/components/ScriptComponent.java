package com.uns.ftn.androidservice.components;

import com.uns.ftn.androidservice.dto.AndroidLocationDTO;
import com.uns.ftn.androidservice.dto.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScriptComponent {

    @Autowired
    private QueueProducer queueProducer;

    @PostConstruct
    public void init() {
        Coordinates coords = new Coordinates();
        int index = 0;

        while (true) {
            try {
                queueProducer.produce(new AndroidLocationDTO(null, "789ae3406905658fe37daa6f834842e8",
                        coords.getCoords().get(index).getLatitude(), coords.getCoords().get(index).getLongitude()));
                index++;

                if (index > 13) {
                    index = 0;
                }

                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
