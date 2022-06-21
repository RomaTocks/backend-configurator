package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.DynamicFilters;
import com.tocks.backend.repository.common.FilterRepository;
import com.tocks.backend.service.FilterService;
import org.springframework.stereotype.Service;

@Service
public class FilterServiceImpl implements FilterService
{
    private final FilterRepository filterRepository;

    public FilterServiceImpl(FilterRepository filterRepository)
    {
        this.filterRepository = filterRepository;
    }

    @Override
    public DynamicFilters getProductFilters(String id)
    {
        return filterRepository.findById(id).get();
    }
}
