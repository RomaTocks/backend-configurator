package com.tocks.backend.controller.product;

import com.tocks.backend.model.common.filters.Filter;
import com.tocks.backend.service.ChassisService;
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

    public ChassisController(ChassisService chassisService)
    {
        this.chassisService = chassisService;
    }
    @GetMapping("/chassis")
    public ResponseEntity<Map<String, Object>> findAllChassisPageable(@PageableDefault(page = 1, size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? chassisService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : chassisService.findAllBySellersNotNull();
    }
    @GetMapping("/chassis/{id}")
    public ResponseEntity<Object> findChassisById(@PathVariable String id){
        return chassisService.findCPUById(id);
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
    public List<Filter> filtersList() {
        return chassisService.dynamicFilters();
    }
}
