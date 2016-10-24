package org.ssa.ironyard.service.sandbox;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.service.AudiosearchAuthorizationService;
/**
 *
 * @author thurston
 */
public class AsyncAudioSearchTests 
{
    AsyncAudioService service;

    @Before
    public void setup()
    {
//        this.service = new AsyncAudioService(new AudiosearchAuthorizationService());
    }
    
    //@Test
    public void config() throws Exception
    {
        //this.service.testShows(Arrays.asList(new Show(2428, "", "thumb")));
    }
    
    
    @Test
    public void multiple() throws Exception
    {
        List<Episode> latestEpisodes = new ArrayList<>(18);
        latestEpisodes.add(Episode.builder().episodeId(18053).show(new Show(14, "Fresh Air", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(5049).show(new Show(51, "Bookworm", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(112745).show(new Show(72, "NewsHour", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(3348).show(new Show(81, "The Nerdist", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(99786).show(new Show(311, "Storycorps", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(16605).show(new Show(353, "Freakonomics Radio", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(6329).show(new Show(358, "Stuff You Should Know", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(32181).show(new Show(368, "Ask Me Another", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(17113).show(new Show(381, "All Songs Considered", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(64151).show(new Show(399, "Stuff To Blow Your Mind", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(5084).show(new Show(461, "Slate's Amicus with Dahlia Lithwick", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(6908).show(new Show(464, "Slate Money", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(8686).show(new Show(521, "Note To Self", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(41218).show(new Show(1203, "CLUBCast - EDM", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(43098).show(new Show(1388, "Afrojack - JACKED Radio (Official Podcast)", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(39622).show(new Show(1477, "Mixhunt.co", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(177260).show(new Show(6164, "Wikishuffle", "")).build());
        latestEpisodes.add(Episode.builder().episodeId(197244).show(new Show(7806, "House of Borgeous", "")).build());
        
        
        List<Show> uniqueShows = new ArrayList<>(18);
//        uniqueShows.add(new Show(464, "Slate Money", ""));
//        uniqueShows.add(new Show(521, "Note To Self", ""));
//        uniqueShows.add(new Show(72, "NewsHour", ""));
//        uniqueShows.add(new Show(461, "Slate's Amicus", ""));
//        uniqueShows.add(new Show(368, "Ask Me Another", ""));
//        uniqueShows.add(new Show(381, "All Songs Considered", ""));
//        uniqueShows.add(new Show(81, "The Nerdist", ""));
//        uniqueShows.add(new Show(399, "Stuff To Blow Your Mind", ""));
//        uniqueShows.add(new Show(51, "Bookworm", ""));
//        uniqueShows.add(new Show(14, "Fresh Air", ""));
//        uniqueShows.add(new Show(353, "Freakonomics Radio", ""));
//        uniqueShows.add(new Show(358, "Stuff You Should Know", ""));
//        uniqueShows.add(new Show(311, "Storycorps", ""));
//        uniqueShows.add(new Show(1477, "Mixhunt.co", ""));
//        uniqueShows.add(new Show(1388, "Afrojack", ""));
//        uniqueShows.add(new Show(6164, "Wikishuffle", ""));
//        uniqueShows.add(new Show(1203, "CLUBCast EDM", ""));
//        uniqueShows.add(new Show(7806, "House of Bourgeous", ""));
        
        this.service.testShows(latestEpisodes);
        
    }
}