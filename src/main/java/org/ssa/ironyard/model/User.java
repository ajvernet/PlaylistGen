package org.ssa.ironyard.model;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

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
    public boolean deeplyEquals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	User other = (User) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	return true;
    }

    public static UserBuilder builder() {
	return new UserBuilder();
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

    public static class UserBuilder {
	private Integer id;
	private boolean loaded = false;
	private String firstName;
	private String lastName;
	private String email;
	private Password password;
	private Address address;

	public UserBuilder() {
	}

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
