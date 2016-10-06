package org.ssa.ironyard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.LoginService;

@RestController
@RequestMapping("/podcasts")
public class LoginController {

    
    private LoginService loginService;
    
    @Autowired
    public LoginController(LoginService loginService){
	this.loginService = loginService;
    }
    
    @RequestMapping("login")
    public ResponseEntity<ResponseObject> authenticateUser(HttpServletRequest request){
	String username = request.getParameter("user");
	String password = request.getParameter("pass");
	User user = loginService.authenticateUser(username, password);
	if(user!=null)
	    return ResponseEntity.ok()
			.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Congratulations, you are a valid user", user.getId()));
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.ERROR, "Username or password invalid", null));
	
    }
}
