package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Feature;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    @Override
    public Feature create(String name, String icon) {
        if (featureRepository.existsByName(name)) {
            throw new ConflictException("Ya existe una feature con el nombre: " + name);
        }
        Feature feature = new Feature();
        feature.setName(name);
        feature.setIcon(icon);
        return featureRepository.save(feature);
    }

    @Override
    public void delete(Long id) {
        if (!featureRepository.existsById(id)) {
            throw new RuntimeException("Feature not found with id: " + id);
        }
        featureRepository.deleteById(id);
    }
}
