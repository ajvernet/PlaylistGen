package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.Show;

public interface EpisodeORM extends ORM<Episode> {

    static final PlaylistORM playlistOrm = new PlaylistORM() {
    };
    static final ShowORM showOrm = new ShowORM() {
    };

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
	joiner.add("id").add("episodeId").add("name").add("duration").add("fileUrl").add("showId").add("playlistId").add("playOrder");
	return joiner.toString();
    }

    @Override
    default String eagerProjection() {
	return projection() + ", " + playlistOrm.projection();
    }

    @Override
    default String table() {
	return "episodes";
    }

    @Override
    default Episode map(ResultSet results) throws SQLException {
	return Episode.builder().id(results.getInt(table() + ".id")).loaded(true)
		.episodeId(results.getInt(table() + ".episodeId")).name(results.getString(table() + ".name"))
		.duration(results.getInt(table() + ".duration")).fileUrl(results.getString(table() + ".fileUrl"))
		.show(Show.builder().id(results.getInt(table() + ".showId")).build())
		.playlist(Playlist.builder().id(results.getInt(table() + ".playlistId")).build())
		.playOrder(results.getInt(table() + ".playOrder")).build();
    }

    @Override
    default Episode eagerMap(ResultSet results) throws SQLException {
	return Episode.builder(map(results)).show(showOrm.map(results)).playlist(playlistOrm.map(results)).build();
    }

    @Override
    default String prepareInsert() {
	return "INSERT INTO " + table()
		+ "(episodeId, name, duration, fileUrl, showId, playlistId, playOrder) VALUES (?,?,?,?,?,?,?)";
    }

    @Override
    default String prepareUpdate() {
	return "UPDATE " + table()
		+ " SET episodeId=?, name=?, duration=?, fileUrl=?, showId=?, playlistId=?, playOrder=? WHERE id=?";
    }

    @Override
    default String prepareRead() {
	return "SELECT " + projection() + " FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareDelete() {
	return "DELETE FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareEagerRead() {
	return "SELECT " + projection() + ", " + playlistOrm.projection() + ", " + showOrm.projection() + " FROM "
		+ table() + "INNER JOIN " + playlistOrm.table() + " ON " + table() + ".id=" + playlistOrm.table()
		+ ".id INNER JOIN " + showOrm.table() + " ON id=" + showOrm.table() + ".id WHERE id=?";
    }
    
    default String prepareReadByPlaylist(){
	return "SELECT " + projection() + " FROM " + table() + " WHERE playlistId=?";
    }

}
