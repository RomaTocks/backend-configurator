package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.Fan;
import com.tocks.backend.repository.custom.FanRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FanRepository extends MongoRepository<Fan, String>, FanRepositoryCustom
{
    List<Fan> findAllByPositionsNotNull();
    Page<Fan> findAllByPositionsNotNull(Pageable pageable);
}
