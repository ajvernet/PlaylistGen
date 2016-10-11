package org.ssa.ironyard.service.mapper;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {
    private List<PodcastEpisode> results = new ArrayList<>();
    
    public SearchResults(){}

    public SearchResults(List<PodcastEpisode> results) {
	super();
	this.results = results;
    }

    public List<PodcastEpisode> getResults() {
        return results;
    }

    public void setResults(List<PodcastEpisode> results) {
        this.results = results;
    }

    @Override
    public String toString() {
	return "SearchResults [results=" + results + "]";
    }
    

    
}
