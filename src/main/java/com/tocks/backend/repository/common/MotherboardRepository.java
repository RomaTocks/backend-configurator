package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.Motherboard;
import com.tocks.backend.repository.custom.MotherboardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotherboardRepository extends MongoRepository<Motherboard, String>, MotherboardRepositoryCustom
{
    List<Motherboard> findAllByPositionsNotNull();
    Page<Motherboard> findAllByPositionsNotNull(Pageable pageable);
}
