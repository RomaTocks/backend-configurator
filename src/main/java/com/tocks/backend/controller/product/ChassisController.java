package com.tocks.backend.controller.product;

import com.tocks.backend.service.ChassisService;
import com.tocks.backend.service.FilterService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChassisController
{
    private final ChassisService chassisService;
    private final FilterService filterService;

    public ChassisController(ChassisService chassisService, FilterService filterService)
    {
        this.chassisService = chassisService;
        this.filterService = filterService;
    }
    @GetMapping("/chassis")
    public ResponseEntity<Map<String, Object>> findAllChassisPageable(@PageableDefault(page = 1, size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? chassisService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : chassisService.findAllBySellersNotNull();
    }
    @GetMapping("/chassis/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findChassisById(@PathVariable String id){
        return chassisService.findCPUById(id);
    }
    @GetMapping("/chassis/additional")
    @CrossOrigin()
    public Map<String, String> additional() {
        return chassisService.additionalInformation();
    }
    @GetMapping("dynamic/chassis")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? chassisService.dynamicFindAll(pageable, request)
                : chassisService.findAllBySellersNotNull();
    }
    @GetMapping("filters/chassis")
    @CrossOrigin()
    public List<?> filtersList() {
        return filterService.getProductFilters("chassis").getFilters();
    }
}
