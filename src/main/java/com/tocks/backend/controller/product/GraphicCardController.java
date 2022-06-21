package com.tocks.backend.controller.product;

import com.tocks.backend.service.FilterService;
import com.tocks.backend.service.GraphicCardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GraphicCardController
{
    private final GraphicCardService graphicCardService;
    private final FilterService filterService;

    public GraphicCardController(GraphicCardService graphicCardService, FilterService filterService)
    {
        this.graphicCardService = graphicCardService;
        this.filterService = filterService;
    }

    @GetMapping("/gpu")
    public ResponseEntity<Map<String, Object>> findAllGraphicCardsPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0") ? graphicCardService.findAllBySellersNotNull(pageable,request.getRequestURL().toString()) : graphicCardService.findAllBySellersNotNull();
    }
    @GetMapping("/gpu/{id}")
    @CrossOrigin()
    public ResponseEntity<Object> findGraphicCardById(@PathVariable String id){
        return graphicCardService.findCPUById(id);
    }
    @GetMapping("dynamic/gpu")
    @CrossOrigin()
    public ResponseEntity<Map<String, Object>> findAllCPUDynamicPageable(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) {
        return request.getParameter("page") != null && !request.getParameter("page").equals("0")
                ? graphicCardService.dynamicFindAll(pageable, request)
                : graphicCardService.findAllBySellersNotNull();
    }
    @GetMapping("/gpu/additional")
    @CrossOrigin()
    public Map<String, String> additional() {
        return graphicCardService.additionalInformation();
    }
    @GetMapping("filters/gpu")
    @CrossOrigin()
    public List<?> filtersList() {
        return graphicCardService.dynamicFilters();
    }
}
