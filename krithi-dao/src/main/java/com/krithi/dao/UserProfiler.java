package com.krithi.dao;

import java.util.List;

import com.krithi.model.User;

/**
 * Contract which is to be followed by the application
 * for user operations.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public interface UserProfiler {
	public abstract void createAppEnvironment();
	public abstract void createUser(User user);
	public abstract void editUserDetails(User user, String websiteName, String password);
	public abstract List<User> getAllUsers();
	public abstract boolean verifyUserExistence(String userName);
	public abstract void saveUserStatus(User user);
	public abstract User fetchUser(String userName);
	public abstract void removeUserDetails(User user, List<String>webSiteNames);
}
