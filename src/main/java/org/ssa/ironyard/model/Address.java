package org.ssa.ironyard.model;

public class Address {
    private final String street;
    private final String city;
    private final ZipCode zip;
    private final State state;
    
    public Address(String street, String city, ZipCode zip, State state) {
	this.street = street;
	this.city = city;
	this.zip = zip;
	this.state = state;
    }

    public String getStreet() {
	return this.street;
    }

    public String getCity() {
	return this.city;
    }

    public ZipCode getZip() {
	return this.zip;
    }

    public State getState() {
	return this.state;
    }

    public static class ZipCode {
	private final String raw;

	public ZipCode(String raw) {
	    raw = raw.replaceAll("[^0-9]", "");
	    this.raw = raw;
	}

	public int length() {
	    return this.raw.length();
	}

	public String datafy() {
	    return this.raw;
	}

	@Override
	public String toString() {
	    if (length() == 5)
		return this.raw;
	    return this.raw.substring(0, 5) + "-" + this.raw.substring(5);
	}

    };

    public enum State {
	ALABAMA("AL", "Alabama"), ARIZONA("AZ", "Arizona"); // etc, etc

	private final String abbreviation, fullText;

	private State(String abbreviation, String fullText) {
	    this.abbreviation = abbreviation;
	    this.fullText = fullText;
	}

	public String getAbbreviation() {
	    return this.abbreviation;
	}

	public String getFullText() {
	    return this.fullText;
	}

	public static State getInstance(String abbreviation) {
	    for (State state : State.values()) {
		if (state.abbreviation.equals(abbreviation))
		    return state;
	    }
	    return null;
	}

    }
    
    public static class AddressBuilder{
	    private String street;
	    private String city;
	    private ZipCode zip;
	    private State state;
	    
	    public AddressBuilder(){}
	    
	    public AddressBuilder(Address address){
		this.street = address.getStreet();
		this.city = address.getCity();
		this.zip = address.getZip();
		this.state = address.getState();
	    }
	    
	    public AddressBuilder street(String street){
		this.street = street;
		return this;
	    }
	    
	    public AddressBuilder city(String city){
		this.city = city;
		return this;
	    }
	    
	    public AddressBuilder zip(ZipCode zip){
		this.zip = zip;
		return this;
	    }
	    
	    public AddressBuilder state(State state){
		this.state = state;
		return this;
	    }
	    
	    public Address build(){
		return new Address(street, city, zip, state);
	    }
    }
}
