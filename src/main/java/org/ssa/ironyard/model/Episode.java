package org.ssa.ironyard.model;

public class Episode extends DomainObject {

    final Integer episodeId;
    final String name;
    final Integer duration;
    final String fileUrl;
    final Show show;
    final Playlist playlist;

    public Episode(Integer id, boolean loaded, Integer episodeId, String name, Integer duration, String fileUrl,
	    Show show, Playlist playlist) {
	super(id, loaded);
	this.episodeId = episodeId;
	this.name = name;
	this.duration = duration;
	this.fileUrl = fileUrl;
	this.show = show;
	this.playlist = playlist;
    }

    public Integer getEpisodeId() {
	return episodeId;
    }

    public String getName() {
	return name;
    }

    public Integer getDuration() {
	return duration;
    }

    public String getFileUrl() {
	return fileUrl;
    }

    public Show getShow() {
	return show;
    }

    @Override
    boolean deeplyEquals(Object other) {
	// TODO Auto-generated method stub
	return false;
    }

}
