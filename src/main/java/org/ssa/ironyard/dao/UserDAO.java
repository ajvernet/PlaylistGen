package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.dao.orm.UserORM;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.User.UserBuilder;

@Component
public class UserDAO extends AbstractSpringDAO<User> {

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
	insertStatement.setString(4, domainToInsert.getFirstName());
	insertStatement.setString(5, domainToInsert.getLastName());
	insertStatement.setString(6, domainToInsert.getAddress().getStreet());
	insertStatement.setString(7, domainToInsert.getAddress().getCity());
	insertStatement.setString(8, domainToInsert.getAddress().getState().getAbbreviation());
	insertStatement.setString(9, domainToInsert.getAddress().getZip().datafy());
    }

    @Override
    protected User afterInsert(User copy, Integer id) {
	return new UserBuilder(copy).id(id).loaded(true).build();
    }

    @Override
    protected User afterUpdate(User copy) {
	return new UserBuilder(copy).loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(User domainToUpdate) {
	return new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, domainToUpdate.getEmail());
		ps.setString(2, domainToUpdate.getPassword().getSalt());
		ps.setString(3, domainToUpdate.getPassword().getHash());
		ps.setString(4, domainToUpdate.getFirstName());
		ps.setString(5, domainToUpdate.getLastName());
		ps.setString(6, domainToUpdate.getAddress().getStreet());
		ps.setString(7, domainToUpdate.getAddress().getCity());
		ps.setString(8, domainToUpdate.getAddress().getState().getAbbreviation());
		ps.setString(9, domainToUpdate.getAddress().getZip().datafy());
		ps.setInt(10, domainToUpdate.getId());
	    }
	};
    }

    public User readByEmail(String email){
	return this.springTemplate.query(((UserORM)this.orm).prepareReadByEmail(), (PreparedStatement ps) -> ps.setString(1, email),
		(ResultSet cursor) -> 
	     {  if (cursor.next())
	          return this.orm.map(cursor);
	       return null;
	     });
    }

}
