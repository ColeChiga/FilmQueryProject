package com.skilldistillery.filmquery.entities;

import java.util.*;

public class Actor {
	private int id;
	private String firstName;
	private String lastName;
	private List<Film> films;
		
	public Actor() {}
	
	public Actor(String fn, String ln) {
		firstName = fn;
		lastName=ln;
	}
	public Actor(String fn, String ln, int id) {
		this(fn,ln);
		this.id=id;
	}

//Getters/setters
	
		
	
	public void setFilms(Film film){
		films.add(film);
	}
	
	public List<Film> getFilms(){
		List<Film> copy = new ArrayList<>();
		for (Film film : films) {
			copy.add(film);
		}
		return copy;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	

	
//hash and equals
	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName);
	}
	
	
//to String
	@Override
	public String toString() {
		return String.format("|%-4d | %-10s | %-10s", id, firstName, lastName);
	}

	

	


	
}

