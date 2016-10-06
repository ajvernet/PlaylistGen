package org.ssa.ironyard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.UserDAO;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;

@Service
public class LoginService {
    
    private final UserDAO userDao;
    private final BCryptSecurePassword verifier;

    @Autowired
    public LoginService(UserDAO userDao, BCryptSecurePassword verifier){
	this.userDao = userDao;
	this.verifier = verifier;
    }
    
    public User authenticateUser(String username, String password){
	
	User user =  new User(1, true, "test@user.com", new Password("$2a$04$5XIJBbX7Y4DiaJ9H4bkYe.", "Iyl4CRhVdmg2hKWMVd2lZ6tqdXZmUI."));
	if(verifier.verify(password, user.getPassword()))
	    return user;
	return null;
    }
}
