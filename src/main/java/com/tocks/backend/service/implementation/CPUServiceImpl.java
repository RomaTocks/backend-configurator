package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.CPU;
import com.tocks.backend.repository.common.CPURepository;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.CpuService;
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
public class CPUServiceImpl implements CpuService
{
    private final CPURepository repository;

    @Autowired
    public CPUServiceImpl(CPURepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<CPU> cpuPage = repository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(cpuPage,request.getRequestURL().toString(),request.getParameterMap());
    }
    public List<Filter> dynamicFilters() {
        return repository.filters();
    }

    @Override
    public List<CPU> findAll() {
        return repository.findAll();
    }

    @Override
    public List<CPU> findAllById(List<String> ids)
    {
        return (List<CPU>) repository.findAllById(ids);
    }

    @Override
    public List<CPU> findAllByPositionsNotNull()
    {
        return repository.findAllByPositionsNotNull();
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull() {
        HashMap<String, Object> map = new HashMap<>();
        List<CPU> list = repository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL) {
        Page<CPU> page = repository.findAllByPositionsNotNull(pageable);
        return configureResponse(page,URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id) {
        if(repository.findById(id).isPresent()) {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public CPU findById(String id)
    {
        if(repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        }
        return null;
    }

    @Override
    public Map<String, String> additionalInformation() {
        return repository.additional();
    }

    @Override
    public void saveAll(List<CPU> cpus) {
        repository.saveAll(cpus);
    }
}
