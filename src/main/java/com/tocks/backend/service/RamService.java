package com.tocks.backend.service;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Chassis;
import com.tocks.backend.model.product.Ram;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RamService {
    List<Ram> findAll();
    List<Ram> findAllById(List<String> ids);
    List<Ram> findAllByPositionsNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    Ram findById(String id);
    List<Filter> dynamicFilters();
    Map<String, String> additionalInformation();
    ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request);
    void saveAll(List<Ram> rams);
}
