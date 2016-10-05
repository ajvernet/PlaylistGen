package org.ssa.ironyard.model;

public class PodcastEpisode {

    private Integer id;
    private String title;
    private String digital_location;
    private Integer duration;

    public PodcastEpisode() {
    }

    public PodcastEpisode(Integer id, String title, String digital_location, Integer duration) {
	super();
	this.id = id;
	this.title = title;
	this.digital_location = digital_location;
	this.duration = duration;
    }

    @Override
    public String toString() {
	return "PodcastEpisode [id=" + id + ", title=" + title + ", digital_location=" + digital_location
		+ ", duration=" + duration + "]";
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public void setDigital_location(String digital_location) {
	this.digital_location = digital_location;
    }

    public void setDuration(Integer duration) {
	this.duration = duration;
    }

    public Integer getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public String getDigital_location() {
	return digital_location;
    }

    public Integer getDuration() {
	return duration;
    }

}
