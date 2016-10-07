package org.ssa.ironyard.model;

public class ImageUrl {
    private String full;
    private String thumb;
    
    public ImageUrl(){}

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
	return "ImageUrl [full=" + full + ", thumb=" + thumb + "]";
    }
    
    
}
