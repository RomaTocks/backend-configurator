package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.GraphicCard;
import com.tocks.backend.repository.common.GraphicCardRepository;
import com.tocks.backend.response.exception.RequestException;
import com.tocks.backend.service.GraphicCardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tocks.backend.response.Response.configureResponse;
import static com.tocks.backend.response.Response.configureResponseWithParameters;


@Service
public class GraphicCardServiceImpl implements GraphicCardService
{
    private final GraphicCardRepository graphicCardRepository;

    public GraphicCardServiceImpl(GraphicCardRepository graphicCardRepository)
    {
        this.graphicCardRepository = graphicCardRepository;
    }
    public List<Filter> dynamicFilters() {
        return graphicCardRepository.filters();
    }

    @Override
    public List<GraphicCard> findAll()
    {
        return graphicCardRepository.findAll();
    }

    @Override
    public List<GraphicCard> findAllByPositionsNotNull()
    {
        return graphicCardRepository.findAllByPositionsNotNull();
    }

    @Override
    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request)
    {
        Page<GraphicCard> cpuPage = graphicCardRepository.dynamicQuery(request.getParameterMap(),pageable);
        return configureResponseWithParameters(cpuPage,request.getRequestURL().toString(),request.getParameterMap());
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<GraphicCard> list = graphicCardRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(graphicCardRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(graphicCardRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(graphicCardRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new RequestException("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public GraphicCard findById(String id)
    {
        if(graphicCardRepository.findById(id).isPresent()) {
            return graphicCardRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<GraphicCard> graphicCards)
    {
        graphicCardRepository.saveAll(graphicCards);
    }
}
