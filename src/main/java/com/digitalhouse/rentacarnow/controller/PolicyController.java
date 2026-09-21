package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.PolicyRequest;
import com.digitalhouse.rentacarnow.entity.Policy;
import com.digitalhouse.rentacarnow.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ApiResponse<List<Policy>> findAll() {
        return ApiResponse.success(policyService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Policy> findById(@PathVariable Long id) {
        return ApiResponse.success(policyService.findById(id));
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<Policy> findBySlug(@PathVariable String slug) {
        return ApiResponse.success(policyService.findBySlug(slug));
    }

    @PostMapping
    public ApiResponse<Policy> create(@Valid @RequestBody PolicyRequest request) {
        return ApiResponse.success(policyService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Policy> update(@PathVariable Long id, @Valid @RequestBody PolicyRequest request) {
        return ApiResponse.success(policyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        policyService.delete(id);
        return ApiResponse.success(null);
    }
}
