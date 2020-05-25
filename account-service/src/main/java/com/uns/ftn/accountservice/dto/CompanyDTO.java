package com.uns.ftn.accountservice.dto;

import com.uns.ftn.accountservice.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private String state;
    private String city;
    private String street;
    private String businessNumber;

    public CompanyDTO (Company company) {
        this.state = company.getState();
        this.city = company.getCity();
        this.state = company.getStreet();
        this.businessNumber = company.getBusinessNumber();
    }

}
