package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Feature;

import java.util.List;

public interface FeatureService {
    List<Feature> findAll();

    Feature create(String name, String icon);

    void delete(Long id);
}
