package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.dao.orm.UserORM;
import org.ssa.ironyard.model.User;

@Component
public class UserDAO extends AbstractSpringDAO<User> implements DAO<User> {

    public UserDAO(DataSource datasource, ORM<User> orm) {
	super(orm, datasource);
    }

    @Autowired
    public UserDAO(DataSource datasource) {
	this(datasource, new UserORM() {
	});
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, User domainToInsert) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    protected User afterInsert(User copy, Integer id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected User afterUpdate(User copy) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(User domainToUpdate) {
	// TODO Auto-generated method stub
	return null;
    }

}
