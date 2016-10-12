package org.ssa.ironyard.model;

public class Show implements Cloneable {

    private final Integer id;
    private final String name;
    private final String thumbUrl;

    public Show(Integer id, String name, String thumbUrl) {
	super();
	this.id = id;
	this.name = name;
	this.thumbUrl = thumbUrl;
    }

    public Integer getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getThumbUrl() {
	return thumbUrl;
    }

    @Override
    public String toString() {
	return "Show [id=" + id + ", name=" + name + ", thumbUrl=" + thumbUrl + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((thumbUrl == null) ? 0 : thumbUrl.hashCode());
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
	Show other = (Show) obj;
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
	if (thumbUrl == null) {
	    if (other.thumbUrl != null)
		return false;
	} else if (!thumbUrl.equals(other.thumbUrl))
	    return false;
	return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
	return this;
    }

}