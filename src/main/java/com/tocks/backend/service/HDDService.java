package com.tocks.backend.service;

import com.tocks.backend.model.product.HDD;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface HDDService {
    List<HDD> findAll();
    List<HDD> findAllByPositionsNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    HDD findById(String id);
    void saveAll(List<HDD> hdds);
}
