package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class AudioSearchServiceTest {

    @Test
    public void test() {
	
	AudioSearchService ass = new AudioSearchService();
	System.out.println(ass.getPodcastsFromTastemakers(10));
    }

}
