package com.tocks.backend.repository.custom.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Chassis;
import com.tocks.backend.repository.custom.ChassisRepositoryCustom;
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
public class ChassisRepositoryImpl implements ChassisRepositoryCustom
{
    private final MongoTemplate mongoTemplate;

    private final Map<String, String> IN_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("БП", "psuKit"),
            new AbstractMap.SimpleEntry<>("Цвет", "color"),
            new AbstractMap.SimpleEntry<>("Макс. размер материнской платы", "maxSizeOfMotherboard"),
            new AbstractMap.SimpleEntry<>("Расположение блока питания", "psuLocation"),
            new AbstractMap.SimpleEntry<>("Водяное охлаждение", "waterCoolingSupport")
    ));
    private final Map<String, String> INT_RANGE_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Вентиляторов в комплекте", "fanKit"),
            new AbstractMap.SimpleEntry<>("Высота", "height"),
            new AbstractMap.SimpleEntry<>("Макс. длинна видеокарты", "maxGPULength"),
            new AbstractMap.SimpleEntry<>("Макс. высота кулера", "maxCPUCoolingSystemHeight")
    ));
    private final Map<String, String> DOUBLE_RANGE_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Вес", "weight")
    ));

    @Autowired
    public ChassisRepositoryImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Chassis> dynamicQuery(Map<String, String[]> parametersMap, Pageable pageable)
    {
        Query query = queryWithCriteria(parametersMap, pageable, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
        List<Chassis> chassises = mongoTemplate.find(query, Chassis.class);
        return PageableExecutionUtils.getPage(chassises, pageable, count(query, mongoTemplate, Chassis.class));
    }
    public Map<String, String> additional() {
        Map<String, String> fullMap = new HashMap<>();
        fullMap.putAll(IN_MAP);
        fullMap.putAll(INT_RANGE_MAP);
        fullMap.putAll(DOUBLE_RANGE_MAP);
        return fullMap;
    }
    public List<Filter> filters() {
        Query query = new Query();
        List<Chassis> chassises = mongoTemplate.find(query, Chassis.class);
        return getFilters(chassises, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
    }
}
