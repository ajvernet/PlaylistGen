package org.ssa.ironyard.service;

import java.util.List;

import org.ssa.ironyard.model.Episode;

public interface AudiosearchService {

    List<Episode> searchEpisodesAlt(String genre, String searchText, Integer size);

    List<Episode> getTasties();

    List<String> getGenres();

    List<Episode> getNewShowsByUserId(Integer userId);

}