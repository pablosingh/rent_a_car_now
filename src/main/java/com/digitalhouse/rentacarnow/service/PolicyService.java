package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.dto.PolicyRequest;
import com.digitalhouse.rentacarnow.entity.Policy;

import java.util.List;

public interface PolicyService {
    List<Policy> findAll();

    Policy findById(Long id);

    Policy findBySlug(String slug);

    Policy create(PolicyRequest request);

    Policy update(Long id, PolicyRequest request);

    void delete(Long id);
}
