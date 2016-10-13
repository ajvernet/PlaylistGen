package org.ssa.ironyard.model;

public class Episode extends DomainObject {

    private final Integer episodeId;
    private final String name;
    private final Integer genreId;
    private final String description;
    private final Integer duration;
    private final String fileUrl;
    private final Show show;

    public Episode(Integer id, boolean loaded, Integer episodeId, String name, Integer genreId, String description,
	    Integer duration, String fileUrl, Show show) {
	super(id, loaded);
	this.episodeId = episodeId;
	this.name = name;
	this.genreId = genreId;
	this.description = description == null || description.length() <= 20000 ? description
		: description.substring(0, 20000);
	this.duration = duration;
	this.fileUrl = fileUrl;
	this.show = show;
    }

    @Override
    public Episode clone() {
	return this;
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

    public Integer getGenreId() {
	return genreId;
    }

    public String getDescription() {
	return description;
    }

    @Override
    public String toString() {
	return "Episode [episodeId=" + episodeId + ", name=" + name + ", genreId=" + genreId + ", description="
		+ description + ", duration=" + duration + ", fileUrl=" + fileUrl + ", show=" + show + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	DomainObject other = (DomainObject) obj;
	if (id == null) {
	    return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public boolean deeplyEquals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Episode other = (Episode) obj;
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

    /**
     *
     * @return {@link EpisodeBuilder builder} initialized from this
     */
    public EpisodeBuilder of() {
	return Episode.builder(this);
    }

    public static EpisodeBuilder builder() {
	return new EpisodeBuilder();
    }

    public static EpisodeBuilder builder(Episode episode) {
	return new EpisodeBuilder(episode);
    }

    public static class EpisodeBuilder {
	private Integer id;
	private boolean loaded = false;
	private Integer episodeId;
	private String name;
	private Integer genreId;
	private String description;
	private Integer duration;
	private String fileUrl;
	private Show show;

	public EpisodeBuilder() {
	}

	public EpisodeBuilder(Episode episode) {
	    this.loaded = episode.isLoaded();
	    this.id = episode.getId();
	    this.episodeId = episode.getEpisodeId();
	    this.name = episode.getName();
	    this.description = episode.getDescription();
	    this.genreId = episode.getGenreId();
	    this.duration = episode.getDuration();
	    this.fileUrl = episode.getFileUrl();
	    this.show = episode.getShow();
	}

	public EpisodeBuilder id(Integer id) {
	    this.id = id;
	    return this;
	}

	public EpisodeBuilder loaded(boolean loaded) {
	    this.loaded = loaded;
	    return this;
	}

	public EpisodeBuilder episodeId(Integer episodeId) {
	    this.episodeId = episodeId;
	    return this;
	}

	public EpisodeBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public EpisodeBuilder genreId(Integer genreId) {
	    this.genreId = genreId;
	    return this;
	}

	public EpisodeBuilder description(String description) {
	    this.description = description;
	    return this;
	}

	public EpisodeBuilder duration(Integer duration) {
	    this.duration = duration;
	    return this;
	}

	public EpisodeBuilder fileUrl(String fileUrl) {
	    this.fileUrl = fileUrl;
	    return this;
	}

	public EpisodeBuilder show(Show show) {
	    this.show = show;
	    return this;
	}

	public Episode build() {
	    return new Episode(id, loaded, episodeId, name, genreId, description, duration, fileUrl, show);
	}
    }
}
