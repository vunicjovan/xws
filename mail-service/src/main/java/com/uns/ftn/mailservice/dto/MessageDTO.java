package com.uns.ftn.mailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    private String title;
    private String content;
    private String token;
    private Boolean isAgentRegistration;

}
