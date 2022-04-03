package com.tocks.backend.repository.criteria;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

public class DynamicCriteria
{
    public static void newCriteriaInArray(Query query, Map<String, String[]> parametersMap, String[] inArray) {
        for(String inField : inArray)
        {
            addNewCriteriaIn(query, parametersMap, inField);
        }
    }
    public static void newCriteriaIntegerRangeArray(Query query, Map<String, String[]> parametersMap, String[] integerRangeArray) {
        for (String intRangeField : integerRangeArray)
        {
            addNewCriteriaRange(query,parametersMap,intRangeField);
        }
    }
    public static void newCriteriaDoubleRangeArray(Query query, Map<String, String[]> parametersMap, String[] doubleRangeArray) {
        for (String doubleRangeField : doubleRangeArray)
        {
            addNewCriteriaDoubleRange(query, parametersMap, doubleRangeField);
        }
    }
    public static void addNewCriteriaIn(Query query, Map<String, String[]> parametersMap, String name) {
        if (parametersMap.containsKey(name))
        {
            List<String> list = List.of(parametersMap.get(name));
            query.addCriteria(Criteria.where("additional." + name).in(list));
        }

    }
    public static void addNewCriteriaRange(Query query, Map<String, String[]> parametersMap, String criteriaName) {
        Criteria rangeCriteria = Criteria.where("values." + criteriaName);
        Integer minValue = null;
        int maxValue;
        if(parametersMap.containsKey(criteriaName + "_min")) {
            minValue = Integer.valueOf(parametersMap.get(criteriaName + "_min")[0]);
            if (minValue < 0) {
                minValue = 0;
            }
            rangeCriteria.gte(minValue);
        }
        if(parametersMap.containsKey(criteriaName + "_max")) {
            maxValue = Integer.parseInt(parametersMap.get(criteriaName + "_max")[0]);
            if(minValue != null && minValue > maxValue) {
                maxValue = minValue;
            }
            if(maxValue < 0) {
                maxValue = 0;
            }
            rangeCriteria.lte(maxValue);
        }
        if(parametersMap.containsKey(criteriaName + "_min") || parametersMap.containsKey(criteriaName + "_max")) {
            query.addCriteria(rangeCriteria);
        }
    }
    public static void addNewCriteriaDoubleRange(Query query, Map<String, String[]> parametersMap, String criteriaName) {
        Criteria rangeCriteria = Criteria.where("values." + criteriaName);
        Double minValue = null;
        double maxValue;
        if(parametersMap.containsKey(criteriaName + "_min")) {
            minValue = Double.valueOf(parametersMap.get(criteriaName + "_min")[0]);
            if (minValue < 0.0) {
                minValue = 0.0;
            }
            rangeCriteria.gte(minValue);
        }
        if(parametersMap.containsKey(criteriaName + "_max")) {
            maxValue = Double.parseDouble(parametersMap.get(criteriaName + "_max")[0]);
            if(minValue != null && minValue > maxValue) {
                maxValue = minValue;
            }
            if(maxValue < 0.0) {
                maxValue = 0.0;
            }
            rangeCriteria.lte(maxValue);
        }
        if(parametersMap.containsKey(criteriaName + "_min") || parametersMap.containsKey(criteriaName + "_max")) {
            query.addCriteria(rangeCriteria);
        }
    }
}

