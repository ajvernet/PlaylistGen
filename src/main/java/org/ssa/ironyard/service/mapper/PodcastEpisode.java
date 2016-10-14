package org.ssa.ironyard.service.mapper;

import java.util.ArrayList;
import java.util.List;

public class PodcastEpisode {

    private Integer id;
    private String title;
    private Integer show_id;
    private String show_title;
    private Integer duration;
    private List<AudioFiles> audio_files = new ArrayList<>();
    private List<ImageUrl> img_urls = new ArrayList<>();

    public PodcastEpisode() {
    }

    public PodcastEpisode(Integer id, String title, Integer show_id, String show_title, Integer duration,
	    List<AudioFiles> audio_files) {
	super();
	this.id = id;
	this.title = title;
	this.show_id = show_id;
	this.show_title = show_title;
	this.duration = duration;
	this.audio_files = audio_files;
    }

    public List<ImageUrl> getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(List<ImageUrl> img_urls) {
        this.img_urls = img_urls;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<AudioFiles> getAudio_files() {
        return audio_files;
    }

    public void setAudio_files(List<AudioFiles> audio_files) {
        this.audio_files = audio_files;
    }

    @Override
    public String toString() {
	return "PodcastEpisode [id=" + id + ", title=" + title + ", show_id=" + show_id + ", show_title=" + show_title
		+ ", duration=" + duration + ", audio_files=" + audio_files + "]";
    }

    

}
