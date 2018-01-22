package com.krithi.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krithi.model.User;
import com.krithi.model.UserSystemProperties;

/**
 * DAO level contract implementation.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public class UserProfilerImpl implements UserProfiler {

	/**
	 * Creates the given user in the system.
	 * 
	 * @since v1.0
	 */
	@Override
	public void createUser(User user) {
		List<User> allUsers = getAllUsers();
		String userName = user.getUserName();
		if (!allUsers.contains(user)) {
			System.out.println("Registering user " + userName + "...");
			allUsers.add(user);
		}
		String appFilePath = UserSystemProperties.getAppPropFilePath();
		try {
			FileOutputStream fos = new FileOutputStream(appFilePath);
			ObjectOutputStream userOutputStream = new ObjectOutputStream(fos);
			userOutputStream.writeObject(allUsers);
			userOutputStream.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Edits the provided user details in the system.
	 * 
	 * @param <code>User user</code>
	 * @param <code>String websiteName</code>
	 * @param <code>String password</code>
	 * 
	 * @since v1.0
	 */
	@Override
	public void editUserDetails(User user, String websiteName, String password) {
		List<User> allUsers = getAllUsers();
		if(allUsers.contains(user)){
			List<Map<String, String>>allPasswordDetails = user.getListOfUserPasswords();
			for(Map<String, String> eachPasswordDetail : allPasswordDetails){
				Set<String> allWebEntries = eachPasswordDetail.keySet();
				if(allWebEntries.contains(websiteName)){
					String oldUserIdPasswordCombo = eachPasswordDetail.get(websiteName);
					String[] userPass = oldUserIdPasswordCombo.split("\\|");
					userPass[1] = password;
					String newUserPassCombo = userPass[0] + "|" + password;
					eachPasswordDetail.replace(websiteName, newUserPassCombo);
					break;
				}
			}
			saveUserStatus(user);
		}else{
			System.out.println("$$ User not in system!! $$");
		}
	}

	/**
	 * Returns a list of all the registered users in
	 * the system.
	 * 
	 * @return <code>List<User></code>
	 * @since v1.0
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<>();

		try {
			FileInputStream fis = new FileInputStream(UserSystemProperties.getAppPropFilePath());
			ObjectInputStream obs = new ObjectInputStream(fis);
			allUsers = (List<User>) obs.readObject();
			obs.close();
			fis.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allUsers;
	}

	/**
	 * Checks whether the given user name exists in the
	 * app environment or not.
	 * 
	 * @param <code>String userName</code>
	 * @return <code>boolean</code>
	 * 
	 * @since v1.0
	 */
	@Override
	public boolean verifyUserExistence(String userName) {
		boolean isUserExists = false;
		List<User> allUsers = getAllUsers();
		for (User eachUser : allUsers) {
			if (eachUser.getUserName().equalsIgnoreCase(userName)) {
				isUserExists = true;
				break;
			}
		}
		return isUserExists;

	}

	/**
	 * Saves the current user state in the system.
	 * 
	 * @param <code>User user</code>
	 * @since v1.0
	 */
	@Override
	public void saveUserStatus(User user) {
		List<User> allUsers = getAllUsers();
		for (int i = 0; i < allUsers.size(); i++) {
			User eachUser = allUsers.get(i);
			if (eachUser.equals(user)) {
				allUsers.remove(i);
				allUsers.add(user);
				break;
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(UserSystemProperties.getAppPropFilePath());
			ObjectOutputStream allUsersOutput = new ObjectOutputStream(fos);
			allUsersOutput.writeObject(allUsers);
			allUsersOutput.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates initial application setup in the
	 * user's system.
	 * 
	 * @since v1.0
	 */
	@Override
	public void createAppEnvironment() {
		List<User> appUserList = new ArrayList<>();
		try {
			FileOutputStream fos = new FileOutputStream(UserSystemProperties.getAppPropFilePath());
			ObjectOutputStream allUsersOutput = new ObjectOutputStream(fos);
			allUsersOutput.writeObject(appUserList);
			allUsersOutput.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a user with the given user name from the system.
	 * 
	 * @param <code>String userName</code>
	 * @return <code>User user</code>
	 * @since v1.0
	 */
	@Override
	public User fetchUser(String userName) {
		List<User> allUsers = getAllUsers();
		User requiredUser = null;
		for(User eachUser : allUsers){
			if(eachUser.getUserName().equalsIgnoreCase(userName)){
				requiredUser = eachUser;
				break;
			}
		}
		return requiredUser;
	}

	/**
	 * Removes the specified user details form
	 * the system.
	 * 
	 * @param <code>User user</code>
	 * @param <String websiteName</code>
	 */
	@Override
	public void removeUserDetails(User user, List<String> websiteNames) {
		List<User> allUsers = getAllUsers();
		if(allUsers.contains(user)){
			List<Map<String, String>>allPasswordDetails = user.getListOfUserPasswords();
			for(String eachWebsite : websiteNames){
			for(int i = 0; i < allPasswordDetails.size(); i++){
				Map<String, String> eachPasswordDetail = allPasswordDetails.get(i);
					if(eachPasswordDetail.containsKey(eachWebsite)){
						allPasswordDetails.remove(i);
						break;
					}
				}
			}
			user.setListOfUserPasswords(allPasswordDetails);
			saveUserStatus(user);
			
		}else{
			System.out.println("## User does not exists ##");
		}
	}

}
