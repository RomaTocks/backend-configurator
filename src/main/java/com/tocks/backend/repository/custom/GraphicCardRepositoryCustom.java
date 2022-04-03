package com.tocks.backend.repository.custom;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.GraphicCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GraphicCardRepositoryCustom
{
    Page<GraphicCard> dynamicQuery(Map<String, String[]> parameterMap, Pageable pageable);
    List<Filter> filters();
}