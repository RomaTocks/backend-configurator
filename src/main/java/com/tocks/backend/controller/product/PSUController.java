package com.tocks.backend.controller.product;

import com.tocks.backend.service.FilterService;
import com.tocks.backend.service.PSUService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PSUController
{
    private final PSUService psuService;
    private final FilterService filterService;

    public PSUController(PSUService psuService, FilterService filterService)
    {
        this.psuService = psuService;
        this.filterService = filterService;
    }
    @GetMapping("/psu")
    public ResponseEntity<Map<String, Object>> findAllPSUPageable(@PageableDefault(page = 1, size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? psuService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : psuService.findAllBySellersNotNull();
    }
    @GetMapping("/psu/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findPSUById(@PathVariable String id){
        return psuService.findCPUById(id);
    }
    @GetMapping("dynamic/psu")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? psuService.dynamicFindAll(pageable, request)
                : psuService.findAllBySellersNotNull();
    }
    @GetMapping("/psu/additional")
    @CrossOrigin()
    public Map<String, String> additional() {
        return psuService.additionalInformation();
    }
    @GetMapping("filters/psu")
    @CrossOrigin()
    public List<?> filtersList() {
        return filterService.getProductFilters("psu").getFilters();
    }
}
