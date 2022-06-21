package com.tocks.backend.controller.product;

import com.tocks.backend.service.FilterService;
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
    private final FilterService filterService;

    public MotherboardController(MotherboardService motherboardService, FilterService filterService)
    {
        this.motherboardService = motherboardService;
        this.filterService = filterService;
    }
    @GetMapping("/motherboard")
    public ResponseEntity<Map<String, Object>> findAllMotherboardsPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? motherboardService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : motherboardService.findAllBySellersNotNull();
    }
    @GetMapping("/motherboard/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findMotherboardById(@PathVariable String id){
        return motherboardService.findCPUById(id);
    }
    @GetMapping("/motherboard/additional")
    @CrossOrigin()
    public Map<String, String> additional() {
        return motherboardService.additionalInformation();
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
    public List<?> filtersList() {
        return filterService.getProductFilters("motherboard").getFilters();
    }
}
