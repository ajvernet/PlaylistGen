package org.ssa.ironyard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.dao.UserDAO;

@Service
public class LoginService {
    
    UserDAO userDao;

    @Autowired
    public LoginService(UserDAO userDao){
	this.userDao = userDao;
    }
    
    public boolean authenticateUser(String username, String password){
	return true;
    }
}
