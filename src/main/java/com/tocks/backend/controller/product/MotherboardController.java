package com.tocks.backend.controller.product;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.service.MotherboardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MotherboardController
{
    private final MotherboardService motherboardService;

    public MotherboardController(MotherboardService motherboardService)
    {
        this.motherboardService = motherboardService;
    }
    @GetMapping("/motherboard")
    public ResponseEntity<Map<String, Object>> findAllMotherboardsPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? motherboardService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : motherboardService.findAllBySellersNotNull();
    }
    @GetMapping("/motherboard/{id}")
    public ResponseEntity<Object> findMotherboardById(@PathVariable String id){
        return motherboardService.findCPUById(id);
    }
    @GetMapping("dynamic/motherboard")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? motherboardService.dynamicFindAll(pageable, request)
                : motherboardService.findAllBySellersNotNull();
    }
    @GetMapping("filters/motherboard")
    @CrossOrigin()
    public List<Filter> filtersList() {
        return motherboardService.dynamicFilters();
    }
}
