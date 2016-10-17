package org.ssa.ironyard.service.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TastiesEpisodeResult {

    @JsonProperty("id")
    Integer id;
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("tags")
    List<String> tags;
    @JsonProperty("date_added")
    String date_added;
    @JsonProperty("show_id")
    Integer show_id;
    @JsonProperty("show_title")
    String show_title;
    @JsonProperty("audio_files")
    TastiesAudioFile audio_files;

    @JsonProperty("id")
    public Integer getId() {
	return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
	this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
	return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
	this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
	return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
	this.description = description;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
	return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
	this.tags = tags;
    }

    @JsonProperty("date_added")
    public String getDate_added() {
	return date_added;
    }

    @JsonProperty("date_added")
    public void setDate_added(String date_added) {
	this.date_added = date_added;
    }

    @JsonProperty("show_id")
    public Integer getShow_id() {
	return show_id;
    }

    @JsonProperty("show_id")
    public void setShow_id(Integer show_id) {
	this.show_id = show_id;
    }

    @JsonProperty("audio_files")
    public String getShow_title() {
	return show_title;
    }

    @JsonProperty("audio_files")
    public void setShow_title(String show_title) {
	this.show_title = show_title;
    }

    @JsonProperty("audio_files")
    public TastiesAudioFile getAudio_files() {
	return audio_files;
    }

    @JsonProperty("audio_files")
    public void setAudio_files(TastiesAudioFile audio_files) {
	this.audio_files = audio_files;
    }

    @Override
    public String toString() {
	return "TastiesEpisodeResult [id=" + id + ", title=" + title + ", description=" + description + ", tags=" + tags
		+ ", date_added=" + date_added + ", show_id=" + show_id + ", show_title=" + show_title
		+ ", audio_files=" + audio_files + "]";
    }

}
