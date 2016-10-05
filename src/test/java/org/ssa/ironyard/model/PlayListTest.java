package org.ssa.ironyard.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.PlayList;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Series;

public class PlayListTest {

    Episode cast1;
    Episode cast2;
    Episode cast3;
    
    User user;
    List<Episode> podcasts;
    
    @Before
    public void setup()
    {
        cast1 = new Episode(1, true, new Series("series"), "lowName", 3000, LocalDate.of(2015, 1, 1), "something.com");
        cast2 = new Episode(2, true,  new Series("series2"), "medName", 2500, LocalDate.of(2015, 2, 1), "something.com");
        cast3 = new Episode(3, true,  new Series("series3"),"ultName", 3000, LocalDate.of(2015, 3, 1), "something.com");
        
        user = new User("joe", new Password("salt", "hash"));
        podcasts = Arrays.asList(cast1, cast2, cast3);
    }
    
    @Test
    public void getIdTest()
    {
        PlayList testList = new PlayList();
        assertTrue(testList.getId() == null);
        
        PlayList testList2 = new PlayList(1, true, user);
        assertEquals(new Integer(1), testList2.getId());
    }
    
    @Test
    public void getLoadedTest()
    {
        PlayList testList = new PlayList();
        assertFalse(testList.isLoaded());
        
        testList = testList.setLoaded();
        assertTrue(testList.isLoaded());
    }
    
    @Test
    public void getPodcastsTest()
    {
        PlayList testList = new PlayList();
        assertEquals(0, testList.getPodcasts().size());
        
    }
    
    @Test
    public void setIdTest()
    {
        PlayList testList = new PlayList();
        testList = testList.setId(4);
        assertEquals(new Integer(4), testList.getId());
    }
    
    @Test
    public void setLoadedTest()
    {
        PlayList testList = new PlayList();
        testList = testList.setLoaded();
        assertTrue(testList.isLoaded());
    }
    
    @Test
    public void addPodcastTest()
    {
        PlayList testList = new PlayList();
        testList.addPodcast(cast1);
        assertEquals(1, testList.getPodcasts().size());
    }
    
    @Test
    public void appendPlaylistTest()
    {
        PlayList testList = new PlayList();
        testList.addPodcasts(podcasts);
        assertEquals(3, testList.getPodcasts().size());
    }
    
    @Test
    public void deletePodcastTest()
    {
        PlayList testList = new PlayList();
        testList.addPodcasts(podcasts);
        testList.deletePodcast(0);
        assertEquals(2, testList.getPodcasts().size());
        
    }
    
    @Test
    public void replaceListTest()
    {
        PlayList testList = new PlayList();
        testList.addPodcast(cast1);
        testList.replacePodcasts(podcasts);
        assertEquals(3, testList.getPodcasts().size());
    }
    
    

}
