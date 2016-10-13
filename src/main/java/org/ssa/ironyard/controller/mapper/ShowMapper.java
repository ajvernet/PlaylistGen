package org.ssa.ironyard.controller.mapper;

public class ShowMapper {
    private  Integer id;
    private  String name;
    private  String thumbUrl;
    
    public ShowMapper(){}

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

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
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
	ShowMapper other = (ShowMapper) obj;
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
    public String toString() {
	return "ShowMapper [id=" + id + ", name=" + name + ", thumbUrl=" + thumbUrl + "]";
    }
    
    
}
