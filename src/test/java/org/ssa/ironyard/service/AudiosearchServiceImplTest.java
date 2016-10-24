package org.ssa.ironyard.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.model.Show;

public class AudiosearchServiceImplTest {

    private static AudiosearchService ass;
    private static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&"
	    + "useServerPrepStmts=true";
    private PlaylistService playlistService;

    @Before
    public void setup() {
	playlistService = EasyMock.createNiceMock(PlaylistService.class);
	ass = new AudiosearchServiceImpl( playlistService);
    }

    @Test
    public void searchEpisodesAltTest() {
	List<Episode> episodes = ass.searchEpisodesAlt(null, null, 50);
	assertTrue(0 <= episodes.size() && episodes.size() <= 50);
    }

    @Test
    public void searchEpisodesWithGenre() {
	List<Episode> episodes = ass.searchEpisodesAlt(Genre.ARTS.getName(), null, 50);
	assertTrue(0 <= episodes.size() && episodes.size() <= 50);
	for (Episode episode : episodes)
	    assertTrue("Expected : " + Genre.ARTS.getId() + ", Received: " + episode.getGenreId(),
		    episode.getGenreId() <= Genre.ARTS.getId());
    }

    @Test
    public void getGenresTest() {
	for (String s : ass.getGenres())
	    assertTrue(Genre.getInstance(s).getClass().equals(Genre.class));
    }

    @Test
    public void testTasties() {
	assertFalse(ass.getTasties().isEmpty());
    }

    @Test
    public void testGetNewShows() {
	Integer showId = 27; // This American Life
	Integer userId = 1;
	Integer episodeId = 1;
	List<Episode> episodes = new ArrayList<>();
	episodes.add(Episode.builder().episodeId(episodeId).show(new Show(showId, null, null)).build());
	EasyMock.expect(playlistService.getTopTenShowsByUserId(userId)).andReturn(episodes);
	EasyMock.replay(playlistService);
	List<Episode> results = ass.getNewShowsByUserId(userId);
	for (Episode episode : results)
	    assertTrue(episode.getShow().getId().equals(showId));
    }

}
