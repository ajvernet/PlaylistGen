package org.ssa.ironyard.controller;

import java.nio.charset.StandardCharsets;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.web.MockHttpSession;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.LoginService;

public class LoginControllerTest
{

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
    public void testAuthenticateValidUser()
    {

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

        assertFalse("should be a valid session", this.session.isInvalid());
        assertEquals("user should be placed in session after succesful authentication", this.user,
                     this.session.getAttribute("User"));
    }

    @Test
    public void testAuthenticateInvalidUser()
    {

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
        
        assertNull("should be no authenticated user in session", this.session.getAttribute("User"));
    }
    
    @Test
    public void jsonMappingTest() throws Exception
    {
        final String requestBody = "{" +
                                   "\"user\": \"someone@somewhere.com\"," +
                                   "\"pass\": \"letmein\"" +
                                   "}";
                
        MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
        HttpInputMessage requestBytes = new MockHttpInputMessage(requestBody.getBytes(StandardCharsets.UTF_8));
        Map<String, String> mapArgument = (Map) jackson.read(Map.class, requestBytes);
        assertEquals("", "someone@somewhere.com", mapArgument.get("user"));
        assertEquals("", "letmein", mapArgument.get("pass"));
        
        
    }

}
