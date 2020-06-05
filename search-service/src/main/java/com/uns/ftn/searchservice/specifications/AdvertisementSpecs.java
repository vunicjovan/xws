package com.uns.ftn.searchservice.specifications;

import com.uns.ftn.searchservice.domain.Advertisement;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class AdvertisementSpecs implements Specification<Advertisement>{

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Advertisement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if(criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString()
            );
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%"
                );
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
