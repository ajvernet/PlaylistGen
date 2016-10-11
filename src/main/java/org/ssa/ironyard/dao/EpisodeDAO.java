
package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.EpisodeORM;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.model.Episode;

@Component
public class EpisodeDAO extends AbstractSpringDAO<Episode> implements DAO<Episode> {

    @Autowired
    public EpisodeDAO(DataSource dataSource) {
	this(new EpisodeORM() {
	}, dataSource);
    }

    protected EpisodeDAO(ORM<Episode> orm, DataSource dataSource) {
	super(orm, dataSource);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Episode domainToInsert) throws SQLException {
	insertStatement.setInt(1, domainToInsert.getEpisodeId());
	insertStatement.setString(2, domainToInsert.getName());
	insertStatement.setInt(3, domainToInsert.getDuration());
	insertStatement.setString(4, domainToInsert.getFileUrl());
    }

    @Override
    protected Episode afterInsert(Episode copy, Integer id) {
	return Episode.builder(copy).id(id).loaded(true).build();
    }

    @Override
    protected Episode afterUpdate(Episode copy) {
	return Episode.builder(copy).loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Episode domainToUpdate) {
	return new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setInt(1, domainToUpdate.getEpisodeId());
		ps.setString(2, domainToUpdate.getName());
		ps.setInt(3, domainToUpdate.getDuration());
		ps.setString(4, domainToUpdate.getFileUrl());
		ps.setInt(8, domainToUpdate.getId());
	    }
	};
    }
    
    public void insertIfNotExist(Episode episode){
	
    }

}
