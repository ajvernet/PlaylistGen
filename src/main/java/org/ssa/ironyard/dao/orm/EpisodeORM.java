package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;

public interface EpisodeORM extends ORM<Episode> {

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
	joiner
	.add("id")
	.add("episodeId")
	.add("name")
	.add("duration")
	.add("fileUrl")
	.add("showName")
	.add("thumbUrl");
	return joiner.toString();
    }

    @Override
    default String eagerProjection() {
	return projection();
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
		.show(new Show(results.getString(table()+ ".showName"), results.getString(table() + ".thumbUrl")))
		.build();
    }

    @Override
    default Episode eagerMap(ResultSet results) throws SQLException {
	return Episode.builder(map(results)).build();
    }

    @Override
    default String prepareInsert() {
	return "INSERT INTO " + table()
		+ "(episodeId, name, duration, fileUrl, showName, thumbUrl) VALUES (?,?,?,?,?,?)";
    }

    @Override
    default String prepareUpdate() {
	return "UPDATE " + table()
		+ " SET episodeId=?, name=?, duration=?, fileUrl=?, showName=?, thumbUrl=? WHERE id=?";
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
	return prepareRead();
    }
    
    default String prepareReadByPlaylist(){
	return "SELECT " + projection() + " FROM " + table() + " WHERE playlistId=?";
    }

}