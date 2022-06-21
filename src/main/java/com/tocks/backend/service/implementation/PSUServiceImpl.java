package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.PSU;
import com.tocks.backend.repository.common.PSURepository;
import com.tocks.backend.response.exception.RequestException;
import com.tocks.backend.service.PSUService;
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
public class PSUServiceImpl implements PSUService
{
    private final PSURepository psuRepository;

    public PSUServiceImpl(PSURepository psuRepository)
    {
        this.psuRepository = psuRepository;
    }

    @Override
    public List<PSU> findAll()
    {
        return psuRepository.findAll();
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<PSU> psu = psuRepository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(psu,request.getRequestURL().toString(),request.getParameterMap());
    }
    public List<Filter> dynamicFilters() {
        return psuRepository.filters();
    }

    @Override
    public List<PSU> findAllByPositionsNotNull()
    {
        return psuRepository.findAllByPositionsNotNull();
    }
    @Override
    public Map<String, String> additionalInformation() {
        return psuRepository.additional();
    }
    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<PSU> list = psuRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(psuRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(psuRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(psuRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new RequestException("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public PSU findById(String id)
    {
        if(psuRepository.findById(id).isPresent()) {
            return psuRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<PSU> psus)
    {
        psuRepository.saveAll(psus);
    }
}
