package com.tocks.backend.service.implementation;

import com.tocks.backend.model.product.HDD;
import com.tocks.backend.repository.common.HDDRepository;
import com.tocks.backend.service.HDDService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tocks.backend.response.exception.MessageResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tocks.backend.response.Response.configureResponse;


@Service
public class HDDServiceImpl implements HDDService
{
    private final HDDRepository hddRepository;

    public HDDServiceImpl(HDDRepository hddRepository)
    {
        this.hddRepository = hddRepository;
    }

    @Override
    public List<HDD> findAll()
    {
        return hddRepository.findAll();
    }

    @Override
    public List<HDD> findAllByPositionsNotNull()
    {
        return hddRepository.findAllByPositionsNotNull();
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull()
    {
        HashMap<String, Object> map = new HashMap<>();
        List<HDD> list = hddRepository.findAllByPositionsNotNull();
        map.put("products", list);
        map.put("total", (long) list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Map<String, Object>> findAllBySellersNotNull(Pageable pageable, String URL)
    {
        return configureResponse(hddRepository.findAllByPositionsNotNull(pageable),URL);
    }
    @Override
    public ResponseEntity<Object> findCPUById(String id)
    {
        if(hddRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(hddRepository.findById(id).get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Продукт с id : " + id + " не найден."), HttpStatus.OK);
        }
    }

    @Override
    public HDD findById(String id)
    {
        if(hddRepository.findById(id).isPresent()) {
            return hddRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void saveAll(List<HDD> hdds)
    {
        hddRepository.saveAll(hdds);
    }
}
