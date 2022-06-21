package com.tocks.backend.controller.product;

import com.tocks.backend.service.CpuService;
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
public class ProcessorController
{
    private final CpuService service;
    private final FilterService filterService;

    public ProcessorController(CpuService service, FilterService filterService) {
        this.service = service;
        this.filterService = filterService;
    }

    @GetMapping("/cpu")
    public ResponseEntity<Map<String, Object>> findAllCPUPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? service.findAllBySellersNotNull(pageable,request.getRequestURL().toString())
                : service.findAllBySellersNotNull();
    }
    @GetMapping("/cpu/additional")
    @CrossOrigin()
    public Map<String, String> additionalOfCPU() {
        return service.additionalInformation();
    }
    @GetMapping("dynamic/cpu")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? service.dynamicFindAll(pageable, request)
                :service.findAllBySellersNotNull();
    }
    @GetMapping("filters/cpu")
    @CrossOrigin()
    public List<?> filtersList() {
        return filterService.getProductFilters("cpu").getFilters();
    }
    @GetMapping("/cpu/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findCPUById(@PathVariable String id){
        return service.findCPUById(id);
    }
}
