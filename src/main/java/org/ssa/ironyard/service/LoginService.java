package org.ssa.ironyard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.UserDAO;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.User.UserBuilder;

@Service
public class LoginService {
    
    private final UserDAO users;
    private final BCryptSecurePassword verifier;

    @Autowired
    public LoginService(UserDAO users, BCryptSecurePassword verifier){
	this.users = users;
	this.verifier = verifier;
    }
    
    public User authenticateUser(String email, String password){
	User user = users.readByEmail(email);
	if(user == null) return null;
	if(verifier.verify(password, user.getPassword()))
	    return user;
	return null;
    }
}
