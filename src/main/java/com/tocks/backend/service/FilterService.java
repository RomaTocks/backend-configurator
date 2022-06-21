package com.tocks.backend.service;

import com.tocks.backend.model.common.filters.DynamicFilters;

public interface FilterService
{
    DynamicFilters getProductFilters(String id);
}
