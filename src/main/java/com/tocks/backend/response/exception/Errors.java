package com.tocks.backend.response.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor

public class Errors
{
    private Integer count;
    private Set<String> productsWarnings;
    private Set<String> productsErrors;
    private Set<String> productsInfo;
    private List<ConfigurationException> errors;

    public Errors()
    {
        productsErrors = new HashSet<>();
        productsWarnings = new HashSet<>();
        productsInfo = new HashSet<>();
        errors = new ArrayList<>();
    }
    public void addWarningProduct(String id) {
        productsWarnings.add(id);
    }
    public void addErrorProduct(String id) {
        productsErrors.add(id);
    }
    public void addInfoProduct(String id) {
        productsInfo.add(id);
    }
    public void addAllExceptions(List<ConfigurationException> exceptions) {
        errors.addAll(exceptions);
    }
    public void addException(ConfigurationException exception) {
        errors.add(exception);
    }
    public void configureErrors() {
        productsInfo.removeIf(info -> productsWarnings.contains(info));
        productsInfo.removeIf(info -> productsErrors.contains(info));
        productsWarnings.removeIf(warning -> productsErrors.contains(warning));
    }
}
