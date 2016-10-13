package org.ssa.ironyard.controller.mapper;

import org.ssa.ironyard.model.Show;

public class EpisodeMapper {

    private Integer id;
    private Integer episodeId;
    private String name;
    private Integer genreId;
    private String description;
    private Integer duration;
    private String fileUrl;
    private ShowMapper show;

    public EpisodeMapper() {
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getEpisodeId() {
	return episodeId;
    }

    public void setEpisodeId(Integer episodeId) {
	this.episodeId = episodeId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getGenreId() {
	return genreId;
    }

    public void setGenreId(Integer genreId) {
	this.genreId = genreId;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getDuration() {
	return duration;
    }

    public void setDuration(Integer duration) {
	this.duration = duration;
    }

    public String getFileUrl() {
	return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
	this.fileUrl = fileUrl;
    }

    public ShowMapper getShow() {
	return show;
    }

    public void setShow(ShowMapper show) {
	this.show = show;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((duration == null) ? 0 : duration.hashCode());
	result = prime * result + ((episodeId == null) ? 0 : episodeId.hashCode());
	result = prime * result + ((fileUrl == null) ? 0 : fileUrl.hashCode());
	result = prime * result + ((genreId == null) ? 0 : genreId.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((show == null) ? 0 : show.hashCode());
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
	EpisodeMapper other = (EpisodeMapper) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (duration == null) {
	    if (other.duration != null)
		return false;
	} else if (!duration.equals(other.duration))
	    return false;
	if (episodeId == null) {
	    if (other.episodeId != null)
		return false;
	} else if (!episodeId.equals(other.episodeId))
	    return false;
	if (fileUrl == null) {
	    if (other.fileUrl != null)
		return false;
	} else if (!fileUrl.equals(other.fileUrl))
	    return false;
	if (genreId == null) {
	    if (other.genreId != null)
		return false;
	} else if (!genreId.equals(other.genreId))
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
	if (show == null) {
	    if (other.show != null)
		return false;
	} else if (!show.equals(other.show))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "EpisodeMapper [id=" + id + ", episodeId=" + episodeId + ", name=" + name + ", genreId=" + genreId
		+ ", description=" + description + ", duration=" + duration + ", fileUrl=" + fileUrl + ", show=" + show
		+ "]";
    }

}
