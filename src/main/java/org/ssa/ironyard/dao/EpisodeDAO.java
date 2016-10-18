package org.ssa.ironyard.dao;

import java.util.List;

import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;

public interface EpisodeDAO extends DAO<Episode> {

    public Episode insertIfAbsent(Episode episode);
    public List<Episode> getTopTenShows(Integer userId);
}