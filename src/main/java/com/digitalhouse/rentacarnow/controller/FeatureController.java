package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.Feature;
import com.digitalhouse.rentacarnow.service.FeatureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public ApiResponse<List<Feature>> findAll() {
        return ApiResponse.success(featureService.findAll());
    }

    @PostMapping
    public ApiResponse<Feature> create(@RequestParam String name,
                                       @RequestParam(value = "icon", required = false) String icon) {
        return ApiResponse.success(featureService.create(name, icon));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        featureService.delete(id);
        return ApiResponse.success(null);
    }
}
