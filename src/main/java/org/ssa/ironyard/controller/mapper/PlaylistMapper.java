package org.ssa.ironyard.controller.mapper;

import java.util.List;

public class PlaylistMapper {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer targetDuration;
    private Integer currentDuration;
    private List<EpisodeMapper> episodes;

    public PlaylistMapper() {
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getTargetDuration() {
	return targetDuration;
    }

    public void setTargetDuration(Integer targetDuration) {
	this.targetDuration = targetDuration;
    }

    public Integer getCurrentDuration() {
	return currentDuration;
    }

    public void setCurrentDuration(Integer currentDuration) {
	this.currentDuration = currentDuration;
    }

    public List<EpisodeMapper> getEpisodes() {
	return episodes;
    }

    public void setEpisodes(List<EpisodeMapper> episodes) {
	this.episodes = episodes;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((currentDuration == null) ? 0 : currentDuration.hashCode());
	result = prime * result + ((episodes == null) ? 0 : episodes.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((targetDuration == null) ? 0 : targetDuration.hashCode());
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PlaylistMapper other = (PlaylistMapper) obj;
	if (currentDuration == null) {
	    if (other.currentDuration != null)
		return false;
	} else if (!currentDuration.equals(other.currentDuration))
	    return false;
	if (episodes == null) {
	    if (other.episodes != null)
		return false;
	} else if (!episodes.equals(other.episodes))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (targetDuration == null) {
	    if (other.targetDuration != null)
		return false;
	} else if (!targetDuration.equals(other.targetDuration))
	    return false;
	if (userId == null) {
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "PlaylistMapper [id=" + id + ", name=" + name + ", userId=" + userId + ", targetDuration="
		+ targetDuration + ", currentDuration=" + currentDuration + ", episodes=" + episodes + "]";
    }

}
