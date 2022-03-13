package com.tocks.backend.repository.common;

import com.tocks.backend.model.product.GraphicCard;
import com.tocks.backend.repository.custom.GraphicCardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphicCardRepository extends MongoRepository<GraphicCard,String>, GraphicCardRepositoryCustom
{
    List<GraphicCard> findAllByPositionsNotNull();
    Page<GraphicCard> findAllByPositionsNotNull(Pageable pageable);
}
