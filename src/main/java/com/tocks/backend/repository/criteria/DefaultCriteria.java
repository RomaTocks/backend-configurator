package com.tocks.backend.repository.criteria;

import com.tocks.backend.model.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;
import java.util.regex.Pattern;

import static com.tocks.backend.repository.criteria.DynamicCriteria.*;

public class DefaultCriteria
{
    public static void defaultCriteria(Query query, Map<String, String[]> parametersMap, Pageable pageable) {
        query.with(pageable);
        searchCriteria(query, parametersMap);
        manufacturerCriteria(query, parametersMap);
        priceCriteria(query, parametersMap);
        dynamicSort(query, parametersMap);
    }
    public static void searchCriteria(Query query, Map<String, String[]> parametersMap) {
        if(parametersMap.containsKey("name")) {
            String name = parametersMap.get("name")[0];
            Pattern pattern = Pattern.compile(".*" + name + ".*",Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        }
    }
    public static void manufacturerCriteria(Query query, Map<String, String[]> parametersMap) {
        if (parametersMap.containsKey("mfr"))
        {
            List<String> list = List.of(parametersMap.get("mfr"));
            query.addCriteria(Criteria.where("manufacturer.key").in(list));
        }
    }
    public static void dynamicSort(Query query, Map<String, String[]> parametersMap) {
        if(parametersMap.containsKey("sort")) {
            String sortValue = parametersMap.get("sort")[0];
            switch (sortValue) {
                case "price_min": {
                    query.with(Sort.by("prices.price_min.value").ascending());
                    break;
                }
                case "price_max": {
                    query.with(Sort.by("prices.price_max.value").descending());
                    break;
                }
                case "rating" : {
                    query.with(Sort.by("reviews.rating").descending());
                }
                default: {
                    break;
                }
            }
        }
    }
    public static void priceCriteria(Query query, Map<String, String[]> parametersMap) {
        Criteria priceCriteria = Criteria.where("prices.price_min.value");
        query.addCriteria(Criteria.where("prices").ne(null));
        Double minPrice = null;
        double maxPrice;
        if (parametersMap.containsKey("price_min"))
        {
            minPrice = Double.valueOf(parametersMap.get("price_min")[0]);
            if (minPrice < 0.0)
            {
                minPrice = 0.0;
            }
            priceCriteria.gte(minPrice);
        }
        if (parametersMap.containsKey("price_max"))
        {
            maxPrice = Double.parseDouble(parametersMap.get("price_max")[0]);
            if (minPrice != null && minPrice > maxPrice)
            {
                maxPrice = minPrice;
            }
            if (maxPrice < 0.0)
            {
                maxPrice = 0.0;
            }
            priceCriteria.lte(maxPrice);
        }

        if (parametersMap.containsKey("price_min") || parametersMap.containsKey("price_max"))
        {
            query.addCriteria(priceCriteria);
        }
    }
    public static Query queryWithCriteria(Map<String, String[]> parametersMap, Pageable pageable, Map<String, String> in_map, Map<String, String> int_range_map, Map<String, String> double_range_map)
    {
        Query query = new Query();
        defaultCriteria(query, parametersMap, pageable);
        newCriteriaInArray(query,parametersMap, in_map.values().toArray(new String[0]));
        newCriteriaIntegerRangeArray(query,parametersMap, int_range_map.values().toArray(new String[0]));
        newCriteriaDoubleRangeArray(query, parametersMap, double_range_map.values().toArray(new String[0]));
        return query;
    }
    public static LongSupplier count(Query query, MongoTemplate mongoTemplate, Class<? extends Product> entityClass) {
        return () -> mongoTemplate.count((Query.of(query).limit(-1).skip(-1)), entityClass);
    }
}
