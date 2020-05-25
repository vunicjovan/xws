package com.uns.ftn.accountservice.service;

import com.netflix.discovery.converters.Auto;
import com.uns.ftn.accountservice.domain.Company;
import com.uns.ftn.accountservice.dto.CompanyDTO;
import com.uns.ftn.accountservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public ArrayList<CompanyDTO> getAll() {
        List<Company> companies = companyRepository.findAll();
        ArrayList<CompanyDTO> companyDTOArrayList = new ArrayList<>();

        companies.forEach(company -> companyDTOArrayList.add(new CompanyDTO(company)));

        return companyDTOArrayList;
    }

    public Company getOneByBusinessNumber(String companyBusinessNumber) {
        return companyRepository.findByBusinessNumber(companyBusinessNumber);
    }

}
