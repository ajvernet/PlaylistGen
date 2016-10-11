package org.ssa.ironyard.model;

public class Show{

    private final String name;
    private final String thumbUrl;

    public Show(String name, String thumbUrl) {
	this.name = name;
	this.thumbUrl = thumbUrl;
    }

    public String getName() {
	return name;
    }

    public String getThumbUrl() {
	return thumbUrl;
    }

    @Override
    public String toString() {
	return "Show [name=" + name + ", thumbUrl=" + thumbUrl + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((thumbUrl == null) ? 0 : thumbUrl.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Show other = (Show) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (thumbUrl == null) {
	    if (other.thumbUrl != null)
		return false;
	} else if (!thumbUrl.equals(other.thumbUrl))
	    return false;
	return true;
    }

}