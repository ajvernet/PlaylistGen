package org.ssa.ironyard.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.EpisodeDAOImpl;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.dao.UserDAOImpl;
import org.ssa.ironyard.model.Episode;

import com.mysql.cj.jdbc.MysqlDataSource;

public class LoadEpisodes {

    private static AudiosearchService ass;
    private static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&"
	    + "useServerPrepStmts=true";
    private EpisodeDAO episodeDao;

    @BeforeClass
    public static void initServices() {
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setUrl(URL);
	EpisodeDAO episodes = new EpisodeDAOImpl(datasource);
	PlaylistDAO playlists = new PlaylistDAOImpl(datasource, episodes);
	PlaylistService playlistService = new PlaylistService(playlists,episodes);
	ass = new AudiosearchService(new AudiosearchAuthorizationService(), playlistService);
    }

    @Before
    public void init() {
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setUrl(URL);
	episodeDao = new EpisodeDAOImpl(datasource);
    }

    @Ignore // Don't run this test unless you have a few days/weeks
    @Test
    public void test() {
	Integer i = 117;
	List<Episode> episodes = new ArrayList<>();
	do {
	    try {
		episodes = ass.searchEpisodesByHundreds(i);
	    } catch (Exception e) {
		continue;
	    }
	    for (Episode episode : episodes)
		try {
		    episodeDao.insertIfAbsent(episode);
		} catch (Exception e) {
		}
	    i++;
	} while (episodes == null || !episodes.isEmpty());
    }
}
