package com.tocks.backend.repository.custom.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.PSU;
import com.tocks.backend.repository.custom.PSURepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tocks.backend.repository.criteria.DefaultCriteria.count;
import static com.tocks.backend.repository.criteria.DefaultCriteria.queryWithCriteria;
import static com.tocks.backend.repository.criteria.FilterCriteria.getFilters;

@Component
public class PSURepositoryImpl implements PSURepositoryCustom
{
    private final MongoTemplate mongoTemplate;

    private final Map<String, String> IN_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Стандарт", "standard"),
            new AbstractMap.SimpleEntry<>("Сертификат", "certificate"),
            new AbstractMap.SimpleEntry<>("CPU 4 PIN", "cpu4pin"),
            new AbstractMap.SimpleEntry<>("PCI 6 PIN", "pcie6pin"),
            new AbstractMap.SimpleEntry<>("Вольт", "range")
    ));
    private final Map<String, String> INT_RANGE_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Мощность", "power"),
            new AbstractMap.SimpleEntry<>("Ширина", "width"),
            new AbstractMap.SimpleEntry<>("Высота", "height")
    ));
    private final Map<String, String> DOUBLE_RANGE_MAP = new HashMap<>(Map.ofEntries(
    ));

    @Autowired
    public PSURepositoryImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<PSU> dynamicQuery(Map<String, String[]> parametersMap, Pageable pageable)
    {
        Query query = queryWithCriteria(parametersMap, pageable, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
        List<PSU> psu = mongoTemplate.find(query, PSU.class);
        return PageableExecutionUtils.getPage(psu, pageable, count(query, mongoTemplate, PSU.class));
    }

    public List<Filter> filters()
    {
        Query query = new Query();
        List<PSU> psu = mongoTemplate.find(query, PSU.class);
        return getFilters(psu, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
    }
}
