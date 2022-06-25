package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Fan;
import com.tocks.backend.repository.common.FanRepository;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.FanService;
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
public class FanServiceImpl implements FanService
{
    private final FanRepository fanRepository;

    public FanServiceImpl(FanRepository fanRepository)
    {
        this.fanRepository = fanRepository;
    }

    @Override
    public List<Fan> findAll()
    {
        return fanRepository.findAll();
    }

    @Override
    public List<Fan> findAllById(List<String> ids)
    {
        return (List<Fan>) fanRepository.findAllById(ids);
    }

    @Override
    public List<Fan> findAllByPositionsNotNull()
    {
        return fanRepository.findAllByPositionsNotNull();
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<Fan> fan = fanRepository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(fan,request.getRequestURL().toString(),request.getParameterMap());
    }
    public List<Filter> dynamicFilters() {
        return fanRepository.filters();
    }
    @Override
    public Map<String, String> additionalInformation() {
        return fanRepository.additional();
    }
    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<Fan> list = fanRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(fanRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(fanRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(fanRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public Fan findById(String id)
    {
        if(fanRepository.findById(id).isPresent()) {
            return fanRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<Fan> fans)
    {
        fanRepository.saveAll(fans);
    }
}
