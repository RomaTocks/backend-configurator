package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Ram;
import com.tocks.backend.repository.common.RamRepository;
import com.tocks.backend.response.exception.RequestException;
import com.tocks.backend.service.RamService;
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
public class RamServiceImpl implements RamService
{
    private final RamRepository ramRepository;

    public RamServiceImpl(RamRepository ramRepository)
    {
        this.ramRepository = ramRepository;
    }

    @Override
    public List<Ram> findAll()
    {
        return ramRepository.findAll();
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<Ram> ram = ramRepository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(ram,request.getRequestURL().toString(),request.getParameterMap());
    }
    public List<Filter> dynamicFilters() {
        return ramRepository.filters();
    }

    @Override
    public List<Ram> findAllByPositionsNotNull()
    {
        return ramRepository.findAllByPositionsNotNull();
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<Ram> list = ramRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(ramRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(ramRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(ramRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new RequestException("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public Ram findById(String id)
    {
        if(ramRepository.findById(id).isPresent()) {
            return ramRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<Ram> rams)
    {
        ramRepository.saveAll(rams);
    }
}
