package org.ssa.ironyard.model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.PlayList;
import org.ssa.ironyard.model.User;

public class UserTest {

    String email;
    PlayList list1; 
    PlayList list2;
    PlayList list3; 

    
    @Before
    public void setup()
    {
        email = "user@email.com";
        list1 = new PlayList();
        list2 = new PlayList();
        list3 = new PlayList();

        
    }
    @Test
    public void getIdTest()
    {
        User testUser = new User(email);
        assertEquals(null, testUser.getId());

    }
    
    @Test
    public void getLoadedTest()
    {
        User testUser = new User(email);
        assertFalse(testUser.isLoaded());

    }
    
    @Test
    public void getPlaylistsTest()
    {
        
        User testUser = new User(email);
        assertTrue(testUser.getLists().size() == 0);

    }
    
    @Test
    public void setIdTest()
    {
        User testUser = new User(email);
        
        testUser = testUser.setId(3);
        assertEquals(new Integer(3), testUser.getId());
    }
    
    @Test
    public void setLoadedTest()
    {
        User testUser = new User(email);
        testUser = testUser.setLoaded();
        assertTrue(testUser.isLoaded());
        
    }
    
    @Test
    public void addPlaylistTest()
    {
       User testUser = new User(email);
       
       testUser.addList(list1);
       testUser.addList(list2);
       testUser.addList(list3);

       assertTrue(testUser.getLists().size() == 3);
    }
    
    @Test
    public void deletePlaylistTest()
    {
        User testUser = new User(email);
        
        testUser.addList(list1);
        testUser.addList(list2);
        
        testUser.deleteList(0);
        
        assertTrue(testUser.getLists().size() == 1);
        
    }
    

}
