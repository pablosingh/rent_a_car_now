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
    public Feature update(Long id, String name, String icon) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found with id: " + id));
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        String trimmedName = name.trim();
        Feature byName = featureRepository.findByName(trimmedName).orElse(null);
        if (byName != null && !byName.getId().equals(id)) {
            throw new ConflictException("Ya existe una feature con el nombre: " + trimmedName);
        }
        feature.setName(trimmedName);
        feature.setIcon(icon == null || icon.isBlank() ? null : icon.trim());
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
