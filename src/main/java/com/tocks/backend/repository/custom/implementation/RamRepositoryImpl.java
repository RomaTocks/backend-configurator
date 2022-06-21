package com.tocks.backend.repository.custom.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Ram;
import com.tocks.backend.repository.custom.RamRepositoryCustom;
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
public class RamRepositoryImpl implements RamRepositoryCustom
{
    private final MongoTemplate mongoTemplate;

    private final Map<String, String> IN_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Тип", "type"),
            new AbstractMap.SimpleEntry<>("Тайминги", "timing"),
            new AbstractMap.SimpleEntry<>("XMP-профиль", "xmp")
    ));
    private final Map<String, String> INT_RANGE_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Набор", "kit"),
            new AbstractMap.SimpleEntry<>("Рабочая частота", "frequency"),
            new AbstractMap.SimpleEntry<>("Общий объем", "value"),
            new AbstractMap.SimpleEntry<>("Объем одного модуля", "singleValue")
            ));
    private final Map<String, String> DOUBLE_RANGE_MAP = new HashMap<>(Map.ofEntries(
    ));

    @Autowired
    public RamRepositoryImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Ram> dynamicQuery(Map<String, String[]> parametersMap, Pageable pageable)
    {
        Query query = queryWithCriteria(parametersMap, pageable, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
        List<Ram> ram = mongoTemplate.find(query, Ram.class);
        return PageableExecutionUtils.getPage(ram, pageable, count(query, mongoTemplate, Ram.class));
    }
    public Map<String, String> additional() {
        Map<String, String> fullMap = new HashMap<>();
        fullMap.putAll(IN_MAP);
        fullMap.putAll(INT_RANGE_MAP);
        fullMap.putAll(DOUBLE_RANGE_MAP);
        return  fullMap;
    }
    public List<Filter> filters()
    {
        Query query = new Query();
        List<Ram> ram = mongoTemplate.find(query, Ram.class);
        return getFilters(ram, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
    }
}
