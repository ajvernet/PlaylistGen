package org.ssa.ironyard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.service.LoginService;

@RestController
public class LoginController {

    
    private LoginService loginService;
    
    @Autowired
    public LoginController(LoginService loginService){
	this.loginService = loginService;
    }
    
    
    public ResponseEntity<ResponseObject> authenticateUser(HttpServletRequest request){
	String user = request.getParameter("user");
	String pass = request.getParameter("pass");
	if(loginService.authenticateUser(user, pass))
	    return ResponseEntity.ok()
			.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Congratulations, you are a valid user", "welcome.html"));
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.ERROR, "Username or password invalid", "requestpasswordreset.html"));
	
    }
}
