package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.Show;

public class EpisodeORM implements ORM<Episode>{

    @Override
    public String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner
            .add("id")
            .add("episodeId")
            .add("name")
            .add("duration")
            .add("fileUrl")
            .add("showId")
            .add("playlistId");
        return joiner.toString();
    }

    @Override
    public String eagerProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String table() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Episode map(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Episode eagerMap(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareInsert() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareUpdate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareRead() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareDelete() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareEagerRead() {
        // TODO Auto-generated method stub
        return null;
    }

}
