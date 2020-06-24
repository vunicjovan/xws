package com.uns.ftn.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetDTO {

    private String email;
    private String newPassword;
    private String newPasswordRetype;
    private String token;

}
