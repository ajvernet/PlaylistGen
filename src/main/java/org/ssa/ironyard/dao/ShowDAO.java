package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.dao.orm.ShowORM;
import org.ssa.ironyard.model.Show;

public class ShowDAO extends AbstractSpringDAO<Show> implements DAO<Show> {

    public ShowDAO(DataSource dataSource) {
	this(new ShowORM() {
	}, dataSource);
    }

    protected ShowDAO(ORM<Show> orm, DataSource dataSource) {
	super(orm, dataSource);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Show domainToInsert) throws SQLException {
	insertStatement.setString(1, domainToInsert.getName());
	insertStatement.setInt(2, domainToInsert.getShowId());
	insertStatement.setString(3, domainToInsert.getImgUrl());
	insertStatement.setString(4, domainToInsert.getThumbUrl());
    }

    @Override
    protected Show afterInsert(Show copy, Integer id) {
	return Show.builder(copy).id(id).loaded(true).build();
    }

    @Override
    protected Show afterUpdate(Show copy) {
	return Show.builder(copy).loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Show domainToUpdate) {
	return new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, domainToUpdate.getName());
		ps.setInt(2, domainToUpdate.getShowId());
		ps.setString(3, domainToUpdate.getImgUrl());
		ps.setString(4, domainToUpdate.getThumbUrl());
		ps.setInt(5, domainToUpdate.getId());
	    }
	};
    }

}
