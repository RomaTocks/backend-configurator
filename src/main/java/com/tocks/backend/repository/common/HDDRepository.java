package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.HDD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HDDRepository extends MongoRepository<HDD,String>
{
    List<HDD> findAllByPositionsNotNull();
    Page<HDD> findAllByPositionsNotNull(Pageable pageable);
}
