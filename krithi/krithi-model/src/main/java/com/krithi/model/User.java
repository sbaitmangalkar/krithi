package com.krithi.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Represents a application User and his functionalities.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userAppPassword;
	private List<Map<String, String>> listOfUserPasswords;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserAppPassword() {
		return userAppPassword;
	}
	
	public void setUserAppPassword(String userAppPassword) {
		this.userAppPassword = userAppPassword;
	}
	
	
	public List<Map<String, String>> getListOfUserPasswords() {
		return listOfUserPasswords;
	}

	public void setListOfUserPasswords(List<Map<String, String>> listOfUserPasswords) {
		this.listOfUserPasswords = listOfUserPasswords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userAppPassword == null) ? 0 : userAppPassword.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (userAppPassword == null) {
			if (other.userAppPassword != null)
				return false;
		} else if (!userAppPassword.equals(other.userAppPassword))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userAppPassword=" + userAppPassword + ", listOfUserPasswords="
				+ listOfUserPasswords + "]";
	}
}
