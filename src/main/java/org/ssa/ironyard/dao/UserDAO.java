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
	insertStatement.setString(1, domainToInsert.getEmail());
	insertStatement.setString(2, domainToInsert.getPassword().getSalt());
	insertStatement.setString(3, domainToInsert.getPassword().getHash());
    }

    @Override
    protected User afterInsert(User copy, Integer id) {
	return copy.setId(id);
    }

    @Override
    protected User afterUpdate(User copy) {
	return copy.setLoaded();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(User domainToUpdate) {
	return new PreparedStatementSetter(){

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, domainToUpdate.getEmail());
		ps.setString(2, domainToUpdate.getPassword().getSalt());
		ps.setString(3, domainToUpdate.getPassword().getHash());
	    }
	    
	};
    }

}
