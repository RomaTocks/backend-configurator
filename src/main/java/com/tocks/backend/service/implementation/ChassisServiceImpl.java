package com.tocks.backend.service.implementation;


import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Chassis;
import com.tocks.backend.repository.common.ChassisRepository;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.ChassisService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ChassisServiceImpl implements ChassisService
{
    private final ChassisRepository chassisRepository;

    @Autowired
    public ChassisServiceImpl(ChassisRepository chassisRepository)
    {
        this.chassisRepository = chassisRepository;
    }

    @Override
    public List<Chassis> findAll()
    {
        return chassisRepository.findAll();
    }

    @Override
    public List<Chassis> findAllById(List<String> ids)
    {
       return (List<Chassis>) chassisRepository.findAllById(ids);
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<Chassis> chassis = chassisRepository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(chassis,request.getRequestURL().toString(),request.getParameterMap());
    }
    @Override
    public List<Chassis> findAllByPositionsNotNull()
    {
        return chassisRepository.findAllByPositionsNotNull();
    }

    public List<Filter> dynamicFilters() {
        return chassisRepository.filters();
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<Chassis> list = chassisRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(chassisRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(chassisRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(chassisRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("?????????????? ?? id : " + id + " ???? ????????????."), HttpStatus.OK);
        }
    }
    @Override
    public Map<String, String> additionalInformation() {
        return chassisRepository.additional();
    }
    @Override
    public Chassis findById(String id)
    {
        if(chassisRepository.findById(id).isPresent()) {
            return chassisRepository.findById(id).get();
        }
        return null;
    }

    public void saveAll(List<Chassis> chassis)
    {
        chassisRepository.saveAll(chassis);
    }
}
