package org.ssa.ironyard.model;

public enum Genre {

	COMEDY(1, "Comedy"), SPORTS(2, "Sports & Recreation"), BUSINESS(3, "Business"), OTHER(4, "Other"), SOCIETY(5,
		"Society & Culture"), ARTS(6, "Arts"), NEWS(7, "News & Politics"), TECHNOLOGY(8, "Technology"), EDUCATION(9, "Education");

	private final Integer id;
	private final String name;

	private Genre(Integer id, String name) {
	    this.id = id;
	    this.name = name;
	}

	public Integer getId() {
	    return this.id;
	}

	public String getName() {
	    return this.name;
	}

	public static Genre getInstance(String name) {
	    for (Genre state : Genre.values()) {
		if (state.name.equals(name))
		    return state;
	    }
	    return null;
	}
	
	
    }

