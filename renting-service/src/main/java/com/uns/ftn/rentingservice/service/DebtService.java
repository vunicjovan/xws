package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Debt;
import com.uns.ftn.rentingservice.dto.DebtDTO;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtService {

    private DebtRepository debtRepository;

    @Autowired
    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Debt findOne(Long id) {
        return debtRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Requested debt doesn't exist."));
    }


    public List<DebtDTO> getDebt(Long userId) {
        return  debtRepository.findAllBySimpleUserIdAndDeleted(userId, false).stream()
                .map(DebtDTO::new).collect(Collectors.toList());
    }

    public List<DebtDTO> getIncome(Long agentId) {
        return debtRepository.findAllByAgentIdAndDeleted(agentId, false).stream().map(DebtDTO::new)
                .collect(Collectors.toList());
    }

    public DebtDTO payDebt(Long id) {
        Debt debt = findOne(id);

        debt.setDeleted(true);

        return new DebtDTO(debtRepository.save(debt));
    }
}
