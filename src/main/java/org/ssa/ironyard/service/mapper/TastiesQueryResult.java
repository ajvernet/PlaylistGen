package org.ssa.ironyard.service.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TastiesQueryResult {

    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    
    private List<TastiesResult> tasties;

    public List<TastiesResult> getTasties() {
        return tasties;
    }

    public void setTasties(List<TastiesResult> tasties) {
        this.tasties = tasties;
    }
    
    
}
