package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.dto.PolicyRequest;
import com.digitalhouse.rentacarnow.entity.Policy;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public List<Policy> findAll() {
        return policyRepository.findAll().stream()
                .sorted((a, b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder()))
                .toList();
    }

    @Override
    public Policy findById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + id));
    }

    @Override
    public Policy findBySlug(String slug) {
        return policyRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Policy not found with slug: " + slug));
    }

    @Override
    @Transactional
    public Policy create(PolicyRequest request) {
        if (policyRepository.existsByTitle(request.title())) {
            throw new ConflictException("Ya existe una política con el título: " + request.title());
        }
        if (policyRepository.existsBySlug(request.slug())) {
            throw new ConflictException("Ya existe una política con el slug: " + request.slug());
        }
        Policy policy = new Policy();
        policy.setTitle(request.title());
        policy.setSlug(request.slug());
        policy.setContent(request.content());
        policy.setDisplayOrder(request.displayOrder());
        return policyRepository.save(policy);
    }

    @Override
    @Transactional
    public Policy update(Long id, PolicyRequest request) {
        Policy policy = findById(id);
        if (!policy.getTitle().equals(request.title()) && policyRepository.existsByTitle(request.title())) {
            throw new ConflictException("Ya existe una política con el título: " + request.title());
        }
        if (!policy.getSlug().equals(request.slug()) && policyRepository.existsBySlug(request.slug())) {
            throw new ConflictException("Ya existe una política con el slug: " + request.slug());
        }
        policy.setTitle(request.title());
        policy.setSlug(request.slug());
        policy.setContent(request.content());
        policy.setDisplayOrder(request.displayOrder());
        return policyRepository.save(policy);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Policy policy = findById(id);
        policyRepository.delete(policy);
    }
}
