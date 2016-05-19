package com.krithi.service;

import java.util.List;

import com.krithi.dao.UserProfiler;
import com.krithi.dao.UserProfilerImpl;
import com.krithi.model.User;

/**
 * Service level contract implementation.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public class UserServiceProviderImpl implements UserServiceProvider{
	
	/**
	 * Calls a DAO layer method to check whether the given
	 * user name exists in the system or not.
	 * 
	 * @param <code>String userName</code>
	 * @return <code>boolean</code>
	 * @since v1.0
	 */
	@Override
	public boolean checkForUserExistence(String userName) {
		UserProfiler userProfile = new UserProfilerImpl();
		
		return userProfile.verifyUserExistence(userName);
	}

	/**
	 * Calls a DAO layer method to register a new user
	 * in the system.
	 * 
	 * @param <code>User user</code>
	 * @since v1.0
	 */
	@Override
	public void registerNewUser(User user) {
		UserProfiler userProfile = new UserProfilerImpl();
		userProfile.createUser(user);
	}

	/**
	 * Calls a DAO layer method to fetch all the
	 * users saved in the app system.
	 * 
	 * @return <code>List<User></code>
	 * @since v1.0
	 */
	@Override
	public List<User> fetchAllUsersInApp() {
		UserProfiler userProfile = new UserProfilerImpl();
		List<User> allUsers = userProfile.getAllUsers();
		if(allUsers.size() > 0){
			return allUsers;
		}
		return null;
	}

	/**
	 * Calls a DAO layer method to save the given
	 * user in the system.
	 * 
	 * @param <code>User user</code>
	 * @since v1.0
	 */
	@Override
	public void saveUser(User user) {
		UserProfiler userProfile = new UserProfilerImpl();
		userProfile.saveUserStatus(user);
		
	}

	/**
	 * Calls a DAO layer method to do the initial
	 * application environment setup.
	 * 
	 * @since v1.0
	 */
	@Override
	public void createAppEnvironment() {
		UserProfiler userProfile = new UserProfilerImpl();
		userProfile.createAppEnvironment();
		
	}

	/**
	 * Calls a DAO layer method to save the given
	 * user in the system.
	 * 
	 * @param <code>String userName</code>
	 * @return <code>User</code>
	 * @since v1.0
	 */
	@Override
	public User getSpecificUser(String userName) {
		UserProfiler userProfile = new UserProfilerImpl();
		return userProfile.fetchUser(userName);
	}

	/**
	 * Calls a DAO layer method to delete the specified details
	 * of a specified user.
	 * 
	 * @param <code>User user</code>
	 * @param <code>List<String> websiteNames</code>
	 * @since v1.0
	 */
	@Override
	public void deleteSpecificUserDetails(User user, List<String>websiteNames) {
		UserProfiler userProfile = new UserProfilerImpl();
		userProfile.removeUserDetails(user, websiteNames);
		
	}

	@Override
	public void editSpecificUserDetails(User user, String webSiteName, String newPassword) {
		UserProfiler userProfile = new UserProfilerImpl();
		userProfile.editUserDetails(user, webSiteName, newPassword);
		
	}

}
