package org.ssa.ironyard.model;

public class User extends DomainObject {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Password password;
    private final Address address;

    public User(Integer id, boolean loaded, String firstName, String lastName, String email, Password password,
	    Address address) {
	super(id, loaded);
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
	this.address = address;
    }

    public String getFirstName() {
	return firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public String getEmail() {
	return email;
    }

    public Password getPassword() {
	return password;
    }

    public Address getAddress() {
	return address;
    }

    @Override
    boolean deeplyEquals(Object other) {
	return false;
    }


    public static class UserBuilder {
	private Integer id;
	private boolean loaded = false;
	private String firstName;
	private String lastName;
	private String email;
	private Password password;
	private Address address;

	public UserBuilder(){}

	public UserBuilder(User user) {
	    this.id = user.getId();
	    this.loaded = user.isLoaded();
	    this.firstName = user.getFirstName();
	    this.lastName = user.getLastName();
	    this.email = user.getEmail();
	    this.password = user.getPassword();
	    this.address = user.getAddress();
	}
	
	public UserBuilder id(Integer id) {
	    this.id = id;
	    return this;
	}

	public UserBuilder loaded(boolean loaded) {
	    this.loaded = loaded;
	    return this;
	}

	public UserBuilder firstName(String firstName) {
	    this.firstName = firstName;
	    return this;
	}

	public UserBuilder lastName(String lastName) {
	    this.lastName = lastName;
	    return this;
	}

	public UserBuilder email(String email) {
	    this.email = email;
	    return this;
	}

	public UserBuilder password(Password password) {
	    this.password = password;
	    return this;
	}

	public UserBuilder address(Address address) {
	    this.address = address;
	    return this;
	}

	public User build() {
	    return new User(id, loaded, firstName, lastName, email, password, address);
	}

    }
}
