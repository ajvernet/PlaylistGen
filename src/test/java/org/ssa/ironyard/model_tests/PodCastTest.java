package org.ssa.ironyard.model_tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.Podcast;
import org.ssa.ironyard.model.PodcastSeries;

public class PodCastTest {

    PodcastSeries series;
    String name;
    Integer duration;
    LocalDate date;
    List<String> tags;
    String url;
    Podcast testCast;
    
    @Before
    public void setup()
    {
        series = new PodcastSeries("series");
        name = "Somecast";
        duration = 3600;
        date = LocalDate.now();
        tags = new ArrayList<>(Arrays.asList("Alex", "Andy", "Dan"));
        url = "www.url.com";
    }
    
    
    // Getter tests 
    
    @Test
    public void getIdTest()
    {
        Podcast testCast = new Podcast(1, false, series, name, duration , date, url);
        assertEquals(new Integer(1), testCast.getId());
    }
    
    @Test
    public void getLoadedTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        assertFalse(testCast.isLoaded());
        
        Podcast testCast2 = new Podcast(1, true, series, name, duration, date, url);
        assertTrue(testCast2.isLoaded());
    }
    
    @Test
    public void getSeriesTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        System.out.println(series);
        System.out.println(testCast.getSeries());
        
        
        
    }
    
    @Test
    public void getNameTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        assertTrue(name.equals(testCast.getName()));
    }
    
    @Test
    public void getDurationTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        assertTrue(duration == testCast.getDuration());
    }
    
    @Test
    public void getDateTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        assertTrue(date == testCast.getPublishDate());
    }
    
    @Test
    public void getUrlTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        assertTrue(url.equals(testCast.getfileUrl()));    }
    
    @Test
    public void getTagsTest()
    {
        
        Podcast testCast1 = new Podcast(1, true, series, name, duration, date, url);
        assertTrue(testCast1.getTags().size() == 0);

    }
    
    @Test
    public void addTagsTest()
    {
        Podcast testCast1 = new Podcast(1, true, series, name, duration, date, url);

        
        testCast1.addTags(tags);
        assertTrue(testCast1.getTags().size() == 3);
        
    }
    
    // Setter tests
    
    @Test
    public void setIDTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        testCast = testCast.setId(1);
        assertEquals(new Integer(1), testCast.getId());
    }
    
    @Test
    public void setLoadedTest()
    {
        Podcast testCast = new Podcast(series, name, duration, date, url);
        testCast = testCast.setLoaded();
        assertTrue(testCast.isLoaded());
    }
    
    // comparison testing
    public void equalsTest()
    {
        Podcast testCast1 = new Podcast(1, true, series, name, duration, date, url);
        Podcast testCast2 = new Podcast(1, true, series, "other", 2500, date, "something else.com");
        assertTrue(testCast1.equals(testCast2));
        
        Podcast testCast3 = new Podcast(1, true, series, name, duration, date, url);
        Podcast testCast4 = new Podcast(2, true, series, "other", 2500, date, "something else.com");
        assertFalse(testCast3.equals(testCast4));
    }
    
    public void deeplyEqualsTest()
    {
        Podcast testCast1 = new Podcast(1, true, series, name, duration, date, url);
        Podcast testCast2 = new Podcast(1, true, series, name, duration, date, url);
        assertTrue(testCast1.deeplyEquals(testCast2));
        
        Podcast testCast3 = new Podcast(1, true, series, name, duration, date, url);
        Podcast testCast4 = new Podcast(2, true, series, "other", 2500, date, "something else.com");
        assertFalse(testCast3.deeplyEquals(testCast4));
    }
    
    public void cloneTest()
    {
        Podcast testCast1 = new Podcast(1, true, series, name, duration, date, url);
        Podcast testCast2 = (Podcast) testCast1.clone();
        assertTrue(testCast1.deeplyEquals(testCast2));

    }
    
}
