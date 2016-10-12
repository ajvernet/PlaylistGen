package org.ssa.ironyard.dao;

import org.ssa.ironyard.model.User;

public interface UserDAO extends DAO<User> {

    User readByEmail(String email);

}