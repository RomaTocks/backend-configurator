package com.tocks.backend.repository.common;

import com.tocks.backend.model.common.filters.DynamicFilters;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends MongoRepository<DynamicFilters, String>
{
}
