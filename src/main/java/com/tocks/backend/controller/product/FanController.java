package com.tocks.backend.controller.product;

import com.tocks.backend.service.FanService;
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
public class FanController
{
    private final FanService fanService;
    private final FilterService filterService;

    public FanController(FanService fanService, FilterService filterService)
    {
        this.fanService = fanService;
        this.filterService = filterService;
    }

    @GetMapping("/fan")
    public ResponseEntity<Map<String, Object>> findAllFanPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? fanService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : fanService.findAllBySellersNotNull();
    }
    @GetMapping("/fan/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findFanById(@PathVariable String id){
        return fanService.findCPUById(id);
    }
    @GetMapping("/fan/additional")
    @CrossOrigin()
    public Map<String, String> additional() {
        return fanService.additionalInformation();
    }
    @GetMapping("dynamic/fan")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? fanService.dynamicFindAll(pageable, request)
                : fanService.findAllBySellersNotNull();
    }
    @GetMapping("filters/fan")
    @CrossOrigin()
    public List<?> filtersList() {
        return filterService.getProductFilters("fan").getFilters();
    }
}
