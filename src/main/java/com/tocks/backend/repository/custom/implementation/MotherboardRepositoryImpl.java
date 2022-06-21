package com.tocks.backend.repository.custom.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Motherboard;
import com.tocks.backend.repository.custom.MotherboardRepositoryCustom;
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
public class MotherboardRepositoryImpl implements MotherboardRepositoryCustom
{
    private final MongoTemplate mongoTemplate;

    private final Map<String, String> IN_MAP = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Поддержка процессоров", "cpuSupport"),
            new AbstractMap.SimpleEntry<>("Сокет", "socket"),
            new AbstractMap.SimpleEntry<>("Форм-фактор", "formFactor"),
            new AbstractMap.SimpleEntry<>("Режим памяти", "memoryMode"),
            new AbstractMap.SimpleEntry<>("Wi-Fi", "wifi"),
            new AbstractMap.SimpleEntry<>("Чипсет", "chipset"),
            new AbstractMap.SimpleEntry<>("Подсветка", "backlight"),
            new AbstractMap.SimpleEntry<>("Поддержка встроенной графики", "integratedGraphics"),
            new AbstractMap.SimpleEntry<>("Поддержка SLI", "sli")
    ));
    private final Map<String, String> INT_RANGE_MAP = new HashMap<>(Map.ofEntries(
    ));
    private final Map<String, String> DOUBLE_RANGE_MAP = new HashMap<>(Map.ofEntries(
    ));

    @Autowired
    public MotherboardRepositoryImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    public Page<Motherboard> dynamicQuery(Map<String, String[]> parametersMap, Pageable pageable)
    {
        Query query = queryWithCriteria(parametersMap, pageable, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
        List<Motherboard> motherboards = mongoTemplate.find(query, Motherboard.class);
        return PageableExecutionUtils.getPage(motherboards, pageable, count(query, mongoTemplate, Motherboard.class));
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
        List<Motherboard> motherboards = mongoTemplate.find(query, Motherboard.class);
        return getFilters(motherboards, IN_MAP, INT_RANGE_MAP, DOUBLE_RANGE_MAP);
    }
}
