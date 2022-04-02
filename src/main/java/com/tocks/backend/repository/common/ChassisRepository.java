package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.Chassis;
import com.tocks.backend.repository.custom.ChassisRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChassisRepository extends MongoRepository<Chassis, String>, ChassisRepositoryCustom
{
    List<Chassis> findAllByPositionsNotNull();
    Page<Chassis> findAllByPositionsNotNull(Pageable pageable);
}
