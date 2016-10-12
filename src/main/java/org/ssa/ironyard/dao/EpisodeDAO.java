package org.ssa.ironyard.dao;

import org.ssa.ironyard.model.Episode;

public interface EpisodeDAO extends DAO<Episode> {

    Episode insertIfNotExist(Episode episode);

}