package com.pass.store.model;

import java.io.File;

/**
 * A utility which will provide the user with tha app
 * folder location and app info file location.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 */
public class UserSystemProperties {
	private static final String APP_FOLDER_NAME = "pass_store";
	private static final String APP_PROP_FILE_NAME_SUFFIX = "_properties.txt";
	
	/**
	 * Returns the application property file path
	 * on the user system.
	 * 
	 * @return <code>String</code>
	 * @since v1.0
	 */
	public static String getAppPropFilePath(){
		String userHome = System.getProperty("user.home");
		String systemUser = System.getProperty("user.name");
		String appPath = userHome + File.separator + APP_FOLDER_NAME;
		String appFilePath = appPath + File.separator + systemUser + APP_PROP_FILE_NAME_SUFFIX;
		
		return appFilePath;
	}
	
	/**
	 * Returns application folder path on the 
	 * user system.
	 * 
	 * @return <code>String</code>
	 * @since v1.0
	 */
	public static String getAppFolderPath(){
		String userHome = System.getProperty("user.home");
		String appPath = userHome + File.separator + APP_FOLDER_NAME;
		
		return appPath;
	}
}
