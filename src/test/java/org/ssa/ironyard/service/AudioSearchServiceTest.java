package org.ssa.ironyard.service;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AudioSearchServiceTest {
    
    AudioSearchService ass;
    
    @Before
    public void setup() throws IOException{
	ass = new AudioSearchService(new AuthorizationService());
    }

    @Ignore
    @Test
    public void testTrending() {
	ass.getTrending();
    }
    
    @Test 
    public void testRandomEpisode(){
	ass.getRandomEpisode();
    }
    
    @Test
    public void testChartDaily(){
	ass.getChartDaily();
    }

}
