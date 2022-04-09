package com.tocks.backend.controller.product;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.service.RamService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RamController
{
    private final RamService ramService;

    public RamController(RamService ramService)
    {
        this.ramService = ramService;
    }
    @GetMapping("/ram")
    public ResponseEntity<Map<String, Object>> findAllRamPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? ramService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : ramService.findAllBySellersNotNull();
    }
    @GetMapping("/ram/{id}")
    public ResponseEntity<Object> findRamById(@PathVariable String id){
        return ramService.findCPUById(id);
    }
    @GetMapping("dynamic/ram")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? ramService.dynamicFindAll(pageable, request)
                : ramService.findAllBySellersNotNull();
    }
    @GetMapping("filters/ram")
    @CrossOrigin()
    public List<Filter> filtersList() {
        return ramService.dynamicFilters();
    }
}
