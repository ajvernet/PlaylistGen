package org.ssa.ironyard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.ssa.ironyard.controller.mapper.UserPass;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.LoginService;

@RestController
@RequestMapping("/podcasts")
public class LoginController {

    private final LoginService loginService;

    Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    public LoginController(LoginService loginService) {
	this.loginService = loginService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> authenticateUser(@RequestBody UserPass up, HttpServletRequest request, HttpSession session) {
	String username = up.getUser();
	String password = up.getPass();
	LOGGER.debug("Processing login attempt for {} - {}", username, password);
	User user = loginService.authenticateUser(username, password);
	if (user == null)
	    return ResponseEntity.ok()
		    .body(ResponseObject.instanceOf(STATUS.ERROR, "Username or password invalid", null));
	session.setAttribute("UserID", user.getId());
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Congratulations, you are a valid user", user.getId()));
    }
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public View showLoginPage(){
	return new InternalResourceView("/login.html");
    }
}
