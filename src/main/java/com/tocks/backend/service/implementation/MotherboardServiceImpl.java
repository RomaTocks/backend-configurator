package com.tocks.backend.service.implementation;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.model.product.Motherboard;
import com.tocks.backend.repository.common.MotherboardRepository;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.MotherboardService;
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
public class MotherboardServiceImpl implements MotherboardService
{
    private final MotherboardRepository motherboardRepository;

    public MotherboardServiceImpl(MotherboardRepository motherboardRepository)
    {
        this.motherboardRepository = motherboardRepository;
    }

    public ResponseEntity<Map<String, Object>> dynamicFindAll(Pageable pageable, HttpServletRequest request) {
        Page<Motherboard> motherboardPage = motherboardRepository.dynamicQuery(request.getParameterMap(), pageable);
        return configureResponseWithParameters(motherboardPage,request.getRequestURL().toString(),request.getParameterMap());
    }
    public List<Filter> dynamicFilters() {
        return motherboardRepository.filters();
    }

    @Override
    public List<Motherboard> findAll()
    {
        return motherboardRepository.findAll();
    }

    @Override
    public List<Motherboard> findAllById(List<String> ids)
    {
        return (List<Motherboard>) motherboardRepository.findAllById(ids);
    }

    @Override
    public List<Motherboard> findAllByPositionsNotNull()
    {
        return motherboardRepository.findAllByPositionsNotNull();
    }
    @Override
    public Map<String, String> additionalInformation() {
        return motherboardRepository.additional();
    }
    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<Motherboard> list = motherboardRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(motherboardRepository.findAllByPositionsNotNull(pageable),URL);
    }

    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(motherboardRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(motherboardRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public Motherboard findById(String id)
    {
        if(motherboardRepository.findById(id).isPresent()) {
            return motherboardRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<Motherboard> motherboards)
    {
        motherboardRepository.saveAll(motherboards);
    }
}
