package com.tocks.backend.repository.common;


import com.tocks.backend.model.product.CPU;
import com.tocks.backend.repository.custom.CPURepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CPURepository extends MongoRepository<CPU, String>, CPURepositoryCustom
{
    List<CPU> findAllByPositionsNotNull();
    Page<CPU> findAllByPositionsNotNull(Pageable pageable);
}
