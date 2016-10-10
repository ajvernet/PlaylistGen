package org.ssa.ironyard.model;

public class Show extends DomainObject {

    private final String name;
    private final Integer showId;
    private final String imgUrl;
    private final String thumbUrl;

    public Show(Integer id, boolean loaded, String name, Integer showId, String imgUrl, String thumbnailUrl) {
	super(id, loaded);
	this.name = name;
	this.showId = showId;
	this.imgUrl = imgUrl;
	this.thumbUrl = thumbnailUrl;
    }

    public String getImgUrl() {
	return imgUrl;
    }

    public String getThumbUrl() {
	return thumbUrl;
    }

    public String getName() {
	return name;
    }

    public Integer getShowId() {
	return showId;
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
	Show other = (Show) obj;
	if (imgUrl == null) {
	    if (other.imgUrl != null)
		return false;
	} else if (!imgUrl.equals(other.imgUrl))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (showId == null) {
	    if (other.showId != null)
		return false;
	} else if (!showId.equals(other.showId))
	    return false;
	if (thumbUrl == null) {
	    if (other.thumbUrl != null)
		return false;
	} else if (!thumbUrl.equals(other.thumbUrl))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Show [name=" + name + ", showId=" + showId + ", imgUrl=" + imgUrl + ", thumbnailUrl=" + thumbUrl
		+ "]";
    }

    public static ShowBuilder builder() {
	return new ShowBuilder();
    }

    public static ShowBuilder builder(Show show) {
	return new ShowBuilder(show);
    }

    public static class ShowBuilder {
	private Integer id;
	private boolean loaded = false;
	private String name;
	private Integer showId;
	private String imgUrl;
	private String thumbUrl;

	public ShowBuilder(){}
	
	public ShowBuilder(Show show){
	    this.id = show.getId();
	    this.loaded = show.isLoaded();
	    this.name = show.getName();
	    this.showId = show.getShowId();
	    this.imgUrl = show.getImgUrl();
	    this.thumbUrl = show.getThumbUrl();
	}
	
	public ShowBuilder id(Integer id) {
	    this.id = id;
	    return this;
	}

	public ShowBuilder loaded(boolean loaded) {
	    this.loaded = loaded;
	    return this;
	}

	public ShowBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public ShowBuilder showId(Integer showId) {
	    this.showId = showId;
	    return this;
	}

	public ShowBuilder imgUrl(String imgUrl) {
	    this.imgUrl = imgUrl;
	    return this;
	}

	public ShowBuilder thumbUrl(String thumbUrl) {
	    this.thumbUrl = thumbUrl;
	    return this;
	}

	public Show build() {
	    return new Show(id, loaded, name, showId, imgUrl, thumbUrl);
	}
    }
}