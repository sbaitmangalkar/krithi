package com.krithi.service;

import java.util.List;

import com.krithi.model.User;

/**
 * Service level contract which is to be followed and used
 * by the app to access DAO level functionalities.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public interface UserServiceProvider {
	public abstract boolean checkForUserExistence(String userName);
	public abstract void registerNewUser(User user);
	public abstract List<User> fetchAllUsersInApp();
	public abstract void saveUser(User user);
	public abstract void createAppEnvironment();
	public abstract User getSpecificUser(String userName);
	public abstract void deleteSpecificUserDetails(User user, List<String> websiteNames);
	public abstract void editSpecificUserDetails(User user, String webSiteName, String newPassword);
}
