package com.tocks.backend.service;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Chassis;
import com.tocks.backend.model.product.PSU;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PSUService {
    List<PSU> findAll();
    List<PSU> findAllById(List<String> ids);
    List<PSU> findAllByPositionsNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    PSU findById(String id);
    List<Filter> dynamicFilters();
    Map<String, String> additionalInformation();
    ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request);
    void saveAll(List<PSU> psus);
}
