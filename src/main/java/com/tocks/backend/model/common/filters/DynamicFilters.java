package com.tocks.backend.model.common.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dynamicFilters")
public class DynamicFilters
{
    private String id;
    private List<?> filters;
}
