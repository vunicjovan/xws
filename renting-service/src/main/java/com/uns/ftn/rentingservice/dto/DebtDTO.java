package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.Debt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebtDTO {
    private Long id;
    private Long simpleUserId;
    private Long agentId;
    private double value;

    public DebtDTO(Debt debt) {
        this.id = debt.getId();
        this.simpleUserId = debt.getSimpleUserId();
        this.agentId = debt.getAgentId();
        this.value = debt.getValue();
    }
}
