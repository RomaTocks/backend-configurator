package com.tocks.backend.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.tocks.backend.response.exception.RequestException;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response
{
    public static ResponseEntity<Map<String,Object>> configureResponse(Page<?> page, String URL) {
        int totalPages = page.getTotalPages();
        Integer items = page.getNumberOfElements();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String,String> links = new HashMap<>();
        links.put("first",URL + "?page=1");
        if(page.hasNext()) {
            links.put("next",URL + "?page=" + (page.nextPageable().getPageNumber()+1));
        }
        if(page.hasPrevious() && (page.getNumber() <= totalPages)) {
            links.put("prev",URL + "?page=" + (page.previousPageable().getPageNumber()+1));
        }
        links.put("last",URL + "?page=" + totalPages);
        return getMapResponseEntity(page, totalPages, items, map, links);
    }
    public static ResponseEntity<Map<String,Object>> configureResponseWithParameters(Page<?> page, String URL, Map<String, String[]> parameters) {
        int totalPages = page.getTotalPages();
        Integer items = page.getNumberOfElements();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String,String> links = new HashMap<>();
        Map<String, String[]> parametersMap = new HashMap<>(parameters);
        links.put("first", urlWithParametersBuilder(URL,parametersMap, 1));
        if(page.hasNext()) {
            links.put("next", urlWithParametersBuilder(URL, parametersMap, (page.nextPageable().getPageNumber()+1)));
        }
        if(page.hasPrevious() && (page.getNumber() <= totalPages)) {
            links.put("prev", urlWithParametersBuilder(URL, parametersMap, page.previousPageable().getPageNumber()+1));
        }
        links.put("last", urlWithParametersBuilder(URL, parametersMap, totalPages));
        return getMapResponseEntity(page, totalPages, items, map, links);
    }

    private static ResponseEntity<Map<String, Object>> getMapResponseEntity(Page<?> page, Integer totalPages, Integer items, HashMap<String, Object> map, HashMap<String, String> links)
    {
        if (page.getNumberOfElements() == 0) {
            map.put("error", new RequestException("Данная страница не существует!"));
            map.put("pages", totalPages);
            map.put("items", items);
            map.put("links",links);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("products", page.getContent());
            map.put("pages", totalPages);
            map.put("items", items);
            map.put("links",links);
            map.put("total",page.getTotalElements());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    public static String urlWithParametersBuilder(String path, Map<String, String[]> parameters, Integer pageNumber) {
        StringBuilder parametersBuilder = new StringBuilder();
        parameters.replace("page",new String[]{String.valueOf(pageNumber)});
        parameters.forEach((key, value) -> {
            for (String s : value)
            {
                parametersBuilder.append(key).append("=").append(s).append("&");
            }
        });
        parametersBuilder.delete(parametersBuilder.length()-1,parametersBuilder.length());
        return path + "?" + parametersBuilder;
    }
}
