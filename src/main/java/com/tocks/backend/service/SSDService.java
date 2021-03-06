package com.tocks.backend.service;

import com.tocks.backend.model.product.SSD;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SSDService {
    List<SSD> findAll();
    List<SSD> findAllByPositionsNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    SSD findById(String id);
    void saveAll(List<SSD> ssds);
}
