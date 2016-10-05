package org.ssa.ironyard.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.model.PlayList;


public class UserTest {

    String email;
    Password password;
    final BCryptSecurePassword security = new BCryptSecurePassword();
    PlayList list1; 
    PlayList list2;
    PlayList list3; 

    
    @Before
    public void setup()
    {
        email = "user@email.com";
        password = security.secureHash("test");
        list1 = new PlayList();
        list2 = new PlayList();
        list3 = new PlayList();

        
    }
    @Test
    public void getIdTest()
    {
        User testUser = new User(email, password);
        assertEquals(null, testUser.getId());

    }
    
    @Test
    public void getLoadedTest()
    {
        User testUser = new User(email, password);
        assertFalse(testUser.isLoaded());

    }
    

    
    @Test
    public void setIdTest()
    {
        User testUser = new User(email, password);
        
        testUser = testUser.setId(3);
        assertEquals(new Integer(3), testUser.getId());
    }
    
    @Test
    public void setLoadedTest()
    {
        User testUser = new User(email, password);
        testUser = testUser.setLoaded();
        assertTrue(testUser.isLoaded());
        
    }

}
