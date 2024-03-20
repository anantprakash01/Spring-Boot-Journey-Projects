package com.anant.rest.services.restfulwebservices.versioning;

public class Name {
	private String firstName;
	private String lastName;
	
	public Name(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	@Override
	public String toString() {
		return "PersonV2 [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
