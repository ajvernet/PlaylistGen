package org.ssa.ironyard.model;

public class Show extends DomainObject {

    final String name;
    final Integer showId;
    final String imgUrl;
    final String thumbnailUrl;

    public Show(Integer id, boolean loaded, String name, Integer showId, String imgUrl, String thumbnailUrl) {
	super(id, loaded);
	this.name = name;
	this.showId = showId;
	this.imgUrl = imgUrl;
	this.thumbnailUrl = thumbnailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
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
	if (thumbnailUrl == null) {
	    if (other.thumbnailUrl != null)
		return false;
	} else if (!thumbnailUrl.equals(other.thumbnailUrl))
	    return false;
	return true;
    }



}