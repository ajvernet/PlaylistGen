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
import org.ssa.ironyard.dao.orm.PlaylistORM;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.Playlist.PlaylistBuilder;

@Component
public class PlaylistDAOImpl extends AbstractSpringDAO<Playlist> implements PlaylistDAO {

    Logger LOGGER = LogManager.getLogger(PlaylistDAOImpl.class);

    private static final EpisodeORM episodeOrm = new EpisodeORM() {
    };

    private final EpisodeDAO episodeDao;

    @Autowired
    public PlaylistDAOImpl(DataSource dataSource, EpisodeDAO episodeDao) {
	super(new PlaylistORM() {
	}, dataSource);
	this.episodeDao = episodeDao;
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Playlist domainToInsert) throws SQLException {
	insertStatement.setString(1, domainToInsert.getName());
	insertStatement.setInt(2, domainToInsert.getUser().getId());
	insertStatement.setInt(3, domainToInsert.getTargetDuration());
	insertStatement.setInt(4, domainToInsert.getCurrentDuration());
    }

    @Override
    protected Playlist afterInsert(Playlist copy, Integer id) {
	return Playlist.builder(copy).loaded(true).id(id).build();
    }

    @Override
    protected Playlist afterUpdate(Playlist copy) {
	return Playlist.builder(copy).loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Playlist domainToUpdate) {
	return new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, domainToUpdate.getName());
		ps.setInt(2, domainToUpdate.getUser().getId());
		ps.setInt(3, domainToUpdate.getTargetDuration());
		ps.setInt(4, domainToUpdate.getCurrentDuration());
		ps.setInt(5, domainToUpdate.getId());
	    }
	};
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ssa.ironyard.dao.PlaylistDAO#readByUser(java.lang.Integer)
     */
    @Override
    public List<Playlist> readByUser(Integer userId) {
	List<Playlist> lists = new ArrayList<Playlist>();

	return this.springTemplate.query(((PlaylistORM) this.orm).prepareReadByUser(),
		(PreparedStatement ps) -> ps.setInt(1, userId), (ResultSet cursor) -> {
		    while (cursor.next())
			lists.add(this.orm.map(cursor));

		    return lists;
		});
    }

    @Override
    public boolean replaceEpisodes(Integer playlistId, List<Episode> episodes) {
	LOGGER.debug("Updating episodes in playlist: {}", playlistId);
	LOGGER.debug("Updated playlist will have {} episodes", episodes.size());
	this.springTemplate.update(("DELETE FROM PlaylistEpisodes WHERE playlistId = ?"),
		(PreparedStatement ps) -> ps.setInt(1, playlistId));
	LOGGER.debug("Old playlist data cleared");
	StringBuilder updateString = new StringBuilder(
		"INSERT INTO PlaylistEpisodes(playlistId, episodeId, sortOrder) VALUES");
	for (int i = 0; i < episodes.size(); i++) {
	    Episode loadedEpisode = episodeDao.insertIfAbsent(episodes.get(i));
	    LOGGER.debug("Insert If Not Exist returned episode w/ id: {}", loadedEpisode.getId());
	    updateString.append("(" + playlistId + ", " + loadedEpisode.getId() + ", " + i + "),");
	    LOGGER.debug("UPDATE STRING: {}", updateString.toString());
	}
	updateString.deleteCharAt(updateString.length() - 1);
	LOGGER.debug("{}", updateString.toString());
	return this.springTemplate.update(updateString.toString()) == episodes.size();
    }

    @Override
    public Playlist readByPlaylistId(Integer playlistId) {
	return this.springTemplate.query(((PlaylistORM) this.orm).prepareEagerRead(),
		(PreparedStatement ps) -> ps.setInt(1, playlistId), (ResultSet cursor) -> {
		    if (!cursor.next())
			return null;
		    PlaylistBuilder playlist = Playlist.builder(this.orm.map(cursor));
		    do {
			playlist.addEpisode(episodeOrm.map(cursor));
		    } while (cursor.next());
		    return playlist.build();
		});
    }
}
