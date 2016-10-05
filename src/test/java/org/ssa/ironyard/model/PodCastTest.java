package org.ssa.ironyard.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Series;

public class PodCastTest {

    Series series;
    String name;
    Integer duration;
    LocalDate date;
    List<String> tags;
    String url;
    Episode testCast;
    
    @Before
    public void setup()
    {
        series = new Series("series");
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
        Episode testCast = new Episode(1, false, series, name, duration , date, url);
        assertEquals(new Integer(1), testCast.getId());
    }
    
    @Test
    public void getLoadedTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertFalse(testCast.isLoaded());
        
        Episode testCast2 = new Episode(1, true, series, name, duration, date, url);
        assertTrue(testCast2.isLoaded());
    }
    
    @Test
    public void getSeriesTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertTrue(series.deeplyEquals(testCast.getSeries()));
    }
    
    @Test
    public void getNameTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertTrue(name.equals(testCast.getName()));
    }
    
    @Test
    public void getDurationTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertTrue(duration == testCast.getDuration());
    }
    
    @Test
    public void getDateTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertTrue(date == testCast.getPublishDate());
    }
    
    @Test
    public void getUrlTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        assertTrue(url.equals(testCast.getfileUrl()));    }
    
    @Test
    public void getTagsTest()
    {
        
        Episode testCast1 = new Episode(1, true, series, name, duration, date, url);
        assertTrue(testCast1.getTags().size() == 0);

    }
    
    @Test
    public void addTagsTest()
    {
        Episode testCast1 = new Episode(1, true, series, name, duration, date, url);

        
        testCast1.addTags(tags);
        assertTrue(testCast1.getTags().size() == 3);
        
    }
    
    // Setter tests
    
    @Test
    public void setIDTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        testCast = testCast.setId(1);
        assertEquals(new Integer(1), testCast.getId());
    }
    
    @Test
    public void setLoadedTest()
    {
        Episode testCast = new Episode(series, name, duration, date, url);
        testCast = testCast.setLoaded();
        assertTrue(testCast.isLoaded());
    }
    
    // comparison testing
    public void equalsTest()
    {
        Episode testCast1 = new Episode(1, true, series, name, duration, date, url);
        Episode testCast2 = new Episode(1, true, series, "other", 2500, date, "something else.com");
        assertTrue(testCast1.equals(testCast2));
        
        Episode testCast3 = new Episode(1, true, series, name, duration, date, url);
        Episode testCast4 = new Episode(2, true, series, "other", 2500, date, "something else.com");
        assertFalse(testCast3.equals(testCast4));
    }
    
    public void deeplyEqualsTest()
    {
        Episode testCast1 = new Episode(1, true, series, name, duration, date, url);
        Episode testCast2 = new Episode(1, true, series, name, duration, date, url);
        assertTrue(testCast1.deeplyEquals(testCast2));
        
        Episode testCast3 = new Episode(1, true, series, name, duration, date, url);
        Episode testCast4 = new Episode(2, true, series, "other", 2500, date, "something else.com");
        assertFalse(testCast3.deeplyEquals(testCast4));
    }
    
    public void cloneTest()
    {
        Episode testCast1 = new Episode(1, true, series, name, duration, date, url);
        Episode testCast2 = (Episode) testCast1.clone();
        assertTrue(testCast1.deeplyEquals(testCast2));

    }
    
}
