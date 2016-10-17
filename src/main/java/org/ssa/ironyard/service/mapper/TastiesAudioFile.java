package org.ssa.ironyard.service.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TastiesAudioFile {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("duration")
    Integer duration;
    @JsonProperty("urls")
    List<String> urls;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getDuration() {
	return duration;
    }

    public void setDuration(Integer duration) {
	this.duration = duration;
    }

    public List<String> getUrls() {
	return urls;
    }

    public void setUrls(List<String> urls) {
	this.urls = urls;
    }

    @Override
    public String toString() {
	return "TastiesAudioFile [id=" + id + ", duration=" + duration + ", urls=" + urls + "]";
    }

}
