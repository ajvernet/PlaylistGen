package org.ssa.ironyard.controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.LoginService;

public class LoginControllerTest {

   LoginController controller;
    
    LoginService service;
    Map<String, String> map;
    MockHttpSession session;
    
    Capture<String> capturedArg;
    Capture<String> capturedArg2;
    
    User user;
    
    
    
    @Before
    public void mock()
    {
        service = EasyMock.createNiceMock(LoginService.class);

        session = new MockHttpSession();
        controller = new LoginController(service);
        
        map = new HashMap<>();
        map.put("user", "testUser");
        map.put("pass", "testPass");
        
        user = User.builder()
            .firstName("testFirst")
            .lastName("testLast")
            .email("testMail")
            .id(1)
            .build();
            
        capturedArg = EasyMock.newCapture();
        capturedArg2 = EasyMock.newCapture();
    }
    


    @Test
    public void testAuthenticateValidUser() {
        
        EasyMock.expect(service
                .authenticateUser(EasyMock.capture(capturedArg), EasyMock.capture(capturedArg2)))
                .andReturn(user);  
        
       
        
        EasyMock.replay(service);
    
        ResponseEntity<ResponseObject> response = controller.authenticateUser(map, session);    
        
        
        EasyMock.verify(service);
        assertTrue(capturedArg.getValue().equals("testUser"));
        assertTrue(capturedArg2.getValue().equals("testPass"));
        
        assertEquals(response.getBody().getStatus(), STATUS.SUCCESS);
        assertEquals(response.getBody().getMsg(), "Congratulations, you are a valid user");
        assertEquals(response.getBody().getObj(), user.getId());
    }
    
    @Test
    public void testAuthenticateInvalidUser() {
        
        EasyMock.expect(service
                .authenticateUser(EasyMock.capture(capturedArg), EasyMock.capture(capturedArg2)))
                .andReturn(null);  
        
       
        
        EasyMock.replay(service);
    
        ResponseEntity<ResponseObject> response = controller.authenticateUser(map, session);    
        
        
        EasyMock.verify(service);
        assertTrue(capturedArg.getValue().equals("testUser"));
        assertTrue(capturedArg2.getValue().equals("testPass"));
        
        assertEquals(response.getBody().getStatus(), STATUS.ERROR);
        assertEquals(response.getBody().getMsg(), "Username or password invalid");
        assertNull(response.getBody().getObj());   
        }

    @Ignore
    @Test
    public void testShowLoginPage() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testHome() {
        fail("Not yet implemented");
    }

 
}
