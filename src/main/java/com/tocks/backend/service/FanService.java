package com.tocks.backend.service;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Fan;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface FanService {
    List<Fan> findAll();
    List<Fan> findAllById(List<String> ids);
    List<Fan> findAllByPositionsNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    Fan findById(String id);
    Map<String, String> additionalInformation();
    List<Filter> dynamicFilters();
    ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request);
    void saveAll(List<Fan> fans);
}
