package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.Ram;
import com.tocks.backend.repository.custom.RamRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends MongoRepository<Ram, String>, RamRepositoryCustom
{
    List<Ram> findAllByPositionsNotNull();
    Page<Ram> findAllByPositionsNotNull(Pageable pageable);
}
