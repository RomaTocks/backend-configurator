package com.tocks.backend.service;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.GraphicCard;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GraphicCardService {
    List<GraphicCard> findAll();
    List<GraphicCard> findAllById(List<String> ids);
    List<GraphicCard> findAllByPositionsNotNull();
    ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request);
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull();
    ResponseEntity<Map<String,Object>> findAllBySellersNotNull(Pageable pageable, String URL);
    ResponseEntity<Object> findCPUById(String id);
    GraphicCard findById(String id);
    Map<String, String> additionalInformation();
    List<Filter> dynamicFilters();
    void saveAll(List<GraphicCard> graphicCards);
}
