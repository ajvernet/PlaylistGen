package org.ssa.ironyard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.UserDAO;
import org.ssa.ironyard.model.User;

@Service
public class LoginService {
    
    UserDAO userDao;

    @Autowired
    public LoginService(UserDAO userDao){
	this.userDao = userDao;
    }
    
    public User authenticateUser(String username, String password){
	return new User(1, true, "test@user.com", new BCryptSecurePassword().secureHash("password"));
    }
}
