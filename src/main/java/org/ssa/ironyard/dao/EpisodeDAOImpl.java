
package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.EpisodeORM;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;

@Component
public class EpisodeDAOImpl extends AbstractSpringDAO<Episode> implements EpisodeDAO {

    Logger LOGGER = LogManager.getLogger(EpisodeDAOImpl.class);

    @Autowired
    public EpisodeDAOImpl(DataSource dataSource) {
	this(new EpisodeORM() {
	}, dataSource);
    }

    protected EpisodeDAOImpl(ORM<Episode> orm, DataSource dataSource) {
	super(orm, dataSource);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Episode domainToInsert) throws SQLException {
	insertStatement.setInt(1, domainToInsert.getEpisodeId());
	insertStatement.setString(2, domainToInsert.getName());
	if (domainToInsert.getGenreId() == null)
	    insertStatement.setNull(3, java.sql.Types.INTEGER);
	else
	    insertStatement.setInt(3, domainToInsert.getGenreId());
	insertStatement.setString(4, domainToInsert.getDescription());
	if (domainToInsert.getDuration() == null)
	    insertStatement.setNull(5, java.sql.Types.INTEGER);
	else
	    insertStatement.setInt(5, domainToInsert.getDuration());
	insertStatement.setString(6, domainToInsert.getFileUrl());
	if (domainToInsert.getShow() == null)
	    insertStatement.setNull(7, java.sql.Types.INTEGER);
	else
	    insertStatement.setInt(7, domainToInsert.getShow().getId());
	insertStatement.setString(8, domainToInsert.getShow().getName());
	insertStatement.setString(9, domainToInsert.getShow().getThumbUrl());
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
		if (domainToUpdate.getGenreId() == null)
		    ps.setNull(3, java.sql.Types.INTEGER);
		else
		    ps.setInt(3, domainToUpdate.getGenreId());
		ps.setString(4, domainToUpdate.getDescription());
		if (domainToUpdate.getDuration() == null)
		    ps.setNull(5, java.sql.Types.INTEGER);
		else
		    ps.setInt(5, domainToUpdate.getDuration());
		ps.setString(6, domainToUpdate.getFileUrl());
		if (domainToUpdate.getShow() == null)
		    ps.setNull(7, java.sql.Types.INTEGER);
		else
		    ps.setInt(7, domainToUpdate.getShow().getId());
		ps.setString(8, domainToUpdate.getShow().getName());
		ps.setString(9, domainToUpdate.getShow().getThumbUrl());
		ps.setInt(10, domainToUpdate.getId());
	    }
	};
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.ssa.ironyard.dao.EpisodeDAO#insertIfNotExist(org.ssa.ironyard.model.
     * Episode)
     */

    public Episode readByEpisodeId(Integer episodeId) {
	return this.springTemplate.query(
		("SELECT " + this.orm.projection() + " FROM " + this.orm.table() + " WHERE episodeId=?"),
		(PreparedStatement ps) -> ps.setInt(1, episodeId), (ResultSet cursor) -> {
		    if (cursor.next())
			return this.orm.map(cursor);
		    return null;
		});
    }

    @Override
    public Episode insertIfAbsent(Episode episode) {
	Episode existingEpisode = readByEpisodeId(episode.getEpisodeId());
	// LOGGER.debug(existingEpisode);
	if (existingEpisode == null)
	    return insert(episode);
	/*
	 * Update the persistent episode with the formal argument need the
	 * persistent episode's id
	 */
	episode = episode.of().id(existingEpisode.getId()).build();
	Episode refreshed = update(episode);
	return refreshed;
    }

    @Override
    public List<Episode> getTopTenShows(Integer userId) {
	return this.springTemplate.query(
		"select episodes.showId, max(episodes.episodeId), count(episodes.showId) as episodeCount from episodes inner join playlistepisodes on episodes.id = playlistepisodes.episodeId inner join playlists on playlists.id = playlistepisodes.playlistId where playlists.userId = ? group by episodes.showId order by episodeCount desc limit 10",
		(PreparedStatement ps) -> ps.setInt(1, userId), (ResultSet cursor) -> {
		    List<Episode> episodes = new ArrayList<>();
		    while (cursor.next())
			episodes.add(
				Episode.builder()
				.show(new Show(cursor.getInt(1), null, null))
				.episodeId(cursor.getInt(2))
				.build()				
				);
		    return episodes;
		});
    }

}
