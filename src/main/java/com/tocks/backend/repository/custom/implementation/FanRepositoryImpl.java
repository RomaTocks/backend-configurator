package com.tocks.backend.repository.custom.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Fan;
import com.tocks.backend.repository.custom.FanRepositoryCustom;
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
public class FanRepositoryImpl implements FanRepositoryCustom
{
    private final MongoTemplate mongoTemplate;

    private final Map<String, String> IN_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Тип охлаждения", "coolingType"),
            new AbstractMap.SimpleEntry<>("Цвет", "color"),
            new AbstractMap.SimpleEntry<>("Материал радиатора", "material"),
            new AbstractMap.SimpleEntry<>("PWM", "PWM"),
            new AbstractMap.SimpleEntry<>("Тип подключения", "connectType"),
            new AbstractMap.SimpleEntry<>("Тип подшипника", "bearing"),
            new AbstractMap.SimpleEntry<>("Контроль тепла", "thermalControl"),
            new AbstractMap.SimpleEntry<>("LED-подсветка", "LED")
            ));
    private final Map<String, String> INT_RANGE_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Высота", "height"),
            new AbstractMap.SimpleEntry<>("Диаметр вентилятора", "diameterFan"),
            new AbstractMap.SimpleEntry<>("TDP", "dispelPower")
    ));
    private final Map<String, String> DOUBLE_RANGE_MAP = new HashMap<>(Map.ofEntries(
    ));

    @Autowired
    public FanRepositoryImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }


    public Page<Fan> dynamicQuery(Map<String, String[]> parametersMap, Pageable pageable)
    {
        Query query = queryWithCriteria(parametersMap, pageable, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
        List<Fan> fans = mongoTemplate.find(query, Fan.class);
        return PageableExecutionUtils.getPage(fans, pageable, count(query, mongoTemplate, Fan.class));
    }
    public Map<String, String> additional() {
        Map<String, String> fullMap = new HashMap<>();
        fullMap.putAll(IN_MAP);
        fullMap.putAll(INT_RANGE_MAP);
        fullMap.putAll(DOUBLE_RANGE_MAP);
        return  fullMap;
    }
    public List<Filter> filters() {
        Query query = new Query();
        List<Fan> fans = mongoTemplate.find(query, Fan.class);
        return getFilters(fans, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
    }
}
