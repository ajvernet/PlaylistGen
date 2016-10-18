package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.EpisodeDAOImpl;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.service.mapper.SearchResults;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlDataSource;

public class AudiosearchServiceTest {

    private static AudiosearchService ass;
    private static ObjectMapper mapper;

    String keyword;
    private static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&"
	    + "useServerPrepStmts=true";

    @BeforeClass
    public static void initServices() {
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setUrl(URL);
	EpisodeDAO episodes = new EpisodeDAOImpl(datasource);
	PlaylistDAO playlists = new PlaylistDAOImpl(datasource, episodes);
	PlaylistService playlistService = new PlaylistService(playlists, episodes);
	ass = new AudiosearchService(new AudiosearchAuthorizationService(), playlistService);
	mapper = new ObjectMapper(); // can reuse, share globally
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Before
    public void setup() {
	keyword = "a";
    }

    @Test
    public void searchEpisodesAltTest() {
	assertTrue(ass.searchEpisodesAlt(null, keyword, 50).size() >= 0);
	assertTrue(ass.searchEpisodesAlt(null, keyword, 50).size() < 51);

    }

    @Test
    public void searchEpisodesWithGenre() {
	assertTrue(ass.searchEpisodesAlt(Genre.ARTS.getName(), keyword, 50).size() >= 0);
	assertTrue(ass.searchEpisodesAlt(Genre.ARTS.getName(), keyword, 50).size() < 51);

    }

    @Test
    public void getGenresTest() {
	for (String s : ass.getGenres())
	    assertTrue(Genre.getInstance(s).getClass().equals(Genre.class));
    }

    @Test
    public void testTasties() {
	System.out.println(ass.getTasties());
    }

    @Ignore
    @Test
    public void testGetNewEpisodes() {
	ass.getNewShowsByUserId(7);
	// https://www.audiosear.ch/api/shows/1800
    }

}
