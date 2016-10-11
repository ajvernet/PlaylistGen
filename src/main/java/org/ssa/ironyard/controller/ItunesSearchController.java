package org.ssa.ironyard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.service.ItunesSearchService;

@RestController
@RequestMapping("/itunessearch")
public class ItunesSearchController {

    private ItunesSearchService itunesSearch;
    
    @Autowired
    public ItunesSearchController(ItunesSearchService itunesSearch){
	this.itunesSearch = itunesSearch;
    }
    
    @RequestMapping("term/{term}")
    public ResponseEntity<ResponseObject> searchByTerm(@PathVariable String term){
	return ResponseEntity.ok().body(
		ResponseObject.instanceOf(STATUS.SUCCESS, "Searched iTunes store for you", itunesSearch.searchByTerm(term)));
    }
}
