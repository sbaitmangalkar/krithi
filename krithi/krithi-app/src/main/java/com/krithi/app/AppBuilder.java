package com.krithi.app;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.krithi.model.User;
import com.krithi.model.UserSystemProperties;
import com.krithi.service.UserServiceProvider;
import com.krithi.service.UserServiceProviderImpl;

/**
 * Generates the GUI in swing.
 * (Will be updated to JavaFX in next release.)
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @version v1.0
 * @since v1.0
 *
 */
public class AppBuilder {
	private JFrame appFrame;
	private JPanel appPanel;
	private Dimension screenDim;
	private ImageIcon frameImg;
	private int appRefreshCount;
	
	/**
	 * Returns the app info message.
	 * 
	 * @return <code>String</code>
	 * @since v1.0
	 */
	private String aboutMessage(){
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("");
		messageBuilder.append("This standalone app is developed by Shyam Baitmangalkar.");
		messageBuilder.append("\n");
		messageBuilder.append("In case of any issues or complaints, you can reach out to him on catch.shyamBaitmangalkar@gmail.com");
		return messageBuilder.toString();
	}

	/**
	 * Creates the app folder and app properties file
	 * if not present in the user's system. Initialization
	 * of app parameters are done.
	 * 
	 * @since v1.0
	 */
	private void init() {
		appRefreshCount = 0;
		screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		//URL url = AppBuilder.class.getResource("/images/K_icon.jpg");
		
		frameImg = new ImageIcon("/images/K_icon.jpg");
		//frameImg = new ImageIcon(url);
		String systemUser = System.getProperty("user.name");
		String appFolderPathStr = UserSystemProperties.getAppFolderPath();
		Path appFolderPath = Paths.get(appFolderPathStr);
		UserServiceProvider serviceProvider = null;
		if (!Files.exists(appFolderPath, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Path appDirPath = Files.createDirectory(appFolderPath);
				Path appInfoFileResolvedPath = appDirPath.resolve(systemUser + "_properties.txt");
				Files.createFile(appInfoFileResolvedPath);
				//Files.setAttribute(appFilePath, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
				
				Path aboutAppFileResolvedPath = appDirPath.resolve("about-krithi.txt");
				Path aboutFilePath = Files.createFile(aboutAppFileResolvedPath);
				String about = aboutMessage();
				byte[] aboutInBytes = about.getBytes();
				
				try(OutputStream out = new BufferedOutputStream(Files.newOutputStream(aboutFilePath))){
					out.write(aboutInBytes, 0, aboutInBytes.length);
				}
				serviceProvider = new UserServiceProviderImpl();
				serviceProvider.createAppEnvironment();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				serviceProvider = null;
			}
		}
	}

	/**
	 * Builds the GUI
	 * 
	 * @since v1.0
	 */
	private void buildUI() {
		init();

		appFrame = new JFrame("K.R.I.T.H.I v1.0");
		appPanel = new JPanel(new FlowLayout());
		

		JPanel appNamePanel = new JPanel(new FlowLayout());
		JLabel appNameLabel = new JLabel("Keeping your Rightful Information Tightly Hidden Inside");
		appNameLabel.setFont(new Font("Times New Roman", 1, 14));
		appNamePanel.add(appNameLabel);
		/*
		 * START: Adding user name field
		 */
		JPanel userNameTextBoxPanel = new JPanel(new FlowLayout());
		JLabel userName = new JLabel("User Name:");
		userName.setFont(new Font("Times New Roman", 1, 12));
		JTextField userNameField = new JTextField(20);
		userNameTextBoxPanel.add(userName);
		userNameTextBoxPanel.add(userNameField);
		/*
		 * END: Adding user name field
		 */
		/*
		 * START: Adding password field
		 */
		JPanel passwordPanel = new JPanel(new FlowLayout());
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("Times New Roman", 1, 12));
		JPasswordField passwordField = new JPasswordField(20);
		passwordPanel.add(password);
		passwordPanel.add(passwordField);
		/*
		 * END: Adding password field
		 */
		/*
		 * START: Adding login and register buttons
		 */
		JPanel loginAndRegisterPanel = new JPanel(new FlowLayout());
		JButton loginButton = new JButton("Login");
		JButton register = new JButton("New User");
		loginAndRegisterPanel.add(loginButton);
		loginAndRegisterPanel.add(register);
		
		//START: Adding button actions.
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				appFrame.setEnabled(false);
				JFrame registerUserFrame = new JFrame("K.R.I.T.H.I v1.0");
				JPanel registerUserPanel = new JPanel(new FlowLayout());
				
				JPanel registerTitlePanel = new JPanel(new FlowLayout());
				JLabel registerLabel = new JLabel("Register New User");
				registerLabel.setFont(new Font("Times New Roman", 1, 13));
				registerTitlePanel.add(registerLabel);
				
				JPanel userFieldPanel = new JPanel(new FlowLayout());
				JLabel userNameLabel = new JLabel("Enter User Name:");
				userNameLabel.setFont(new Font("Times New Roman", 1, 12));
				JTextField userNameField = new JTextField(20);
				userFieldPanel.add(userNameLabel);
				userFieldPanel.add(userNameField);
				
				JPanel passwordFieldPanel = new JPanel(new FlowLayout());
				JLabel passwordLabel = new JLabel("Enter a Password:");
				passwordLabel.setFont(new Font("Times New Roman", 1, 12));
				JPasswordField passwordField = new JPasswordField(20);
				passwordFieldPanel.add(passwordLabel);
				passwordFieldPanel.add(passwordField);
				
				JPanel buttonPanel = new JPanel(new FlowLayout());
				JButton register = new JButton("Register User");
				buttonPanel.add(register);
				
				register.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String userNameStr = userNameField.getText();
						char[] passwordChars = passwordField.getPassword();
						String passwordStr = new String(passwordChars);
						
						user.setUserName(userNameStr);
						user.setUserAppPassword(passwordStr);
						ArrayList<Map<String, String>> userPasswordList = new ArrayList<>();
						user.setListOfUserPasswords(userPasswordList);
						UserServiceProvider service = new UserServiceProviderImpl();
						service.registerNewUser(user);
						appFrame.setEnabled(true);
						registerUserFrame.dispose();
						
					}
				});
				
				registerUserPanel.add(appNamePanel);
				registerUserPanel.add(registerTitlePanel);
				registerUserPanel.add(userFieldPanel);
				registerUserPanel.add(passwordFieldPanel);
				registerUserPanel.add(buttonPanel);
				registerUserFrame.getContentPane().add(registerUserPanel);
				registerUserFrame.setVisible(true);
				registerUserFrame.setSize(400, 240);
				registerUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				registerUserFrame.setResizable(false);
				registerUserFrame.setLocation(screenDim.width / 2 - registerUserFrame.getSize().width / 2, screenDim.height/2 - registerUserFrame.getSize().height / 2);
				registerUserFrame.setIconImage(frameImg.getImage());
			}
		});
		/*
		 * START: Adding ActionListener for login button. 
		 */
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserServiceProvider service = new UserServiceProviderImpl();
				String userNameStr = userNameField.getText();
				String passwordStr = new String(passwordField.getPassword());
				
				boolean isUserAlreadyExists = service.checkForUserExistence(userNameStr);
				if(isUserAlreadyExists){
					User specificUser = service.getSpecificUser(userNameStr);
					String existingUserPassword = specificUser.getUserAppPassword();
					System.out.println("User " + userNameStr + " exists in system!!");
					if(existingUserPassword.equalsIgnoreCase(passwordStr)){
						appPanel.removeAll();
						JPanel treePanel = new JPanel(new FlowLayout());
						String displayName = userNameStr.toUpperCase();
						DefaultMutableTreeNode userRoot = new DefaultMutableTreeNode(displayName + "'S PASSWORD VAULT");
						List<User> allUsers = service.fetchAllUsersInApp();
						List<Map<String, String>> allPasswords = null;
						for(User eachUser : allUsers){
							String userName = eachUser.getUserName();
							if(userName.equalsIgnoreCase(userNameStr)){
								allPasswords = eachUser.getListOfUserPasswords();
								
								if(allPasswords.size() > 0){
									for(Map<String, String> userPasswordDetails : allPasswords){
										for(Map.Entry<String, String> eachWebAndPassword : userPasswordDetails.entrySet()){
											String webSite = eachWebAndPassword.getKey();
											String userDetails = eachWebAndPassword.getValue();
											String[] userNameAndPassword = userDetails.split("\\|");
											String user = userNameAndPassword[0];
											String password = userNameAndPassword[1];
											
											DefaultMutableTreeNode websiteNode = new DefaultMutableTreeNode(webSite);
											websiteNode.add(new DefaultMutableTreeNode(user));
											websiteNode.add(new DefaultMutableTreeNode(password));
											
											userRoot.add(websiteNode);
										}
									}
								}
							}else{
								continue;
							}
							
						}
						
						JTree resultTree = new JTree(userRoot);
						treePanel.add(resultTree);
						JScrollPane treeScrollPane = new JScrollPane(treePanel);
						treeScrollPane.setPreferredSize(new Dimension(300, 200));
						JPanel addPanel = new JPanel(new FlowLayout());
						JButton addButton = new JButton("Add Details");
						JButton editDetails = new JButton("Edit Details");
						JButton deleteDetails = new JButton("Delete Details");
						JButton refresh = new JButton("Refresh");
						
						/*
						 * START: Adding ActionListener for Delete Details Button.
						 */
						deleteDetails.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								appFrame.setEnabled(false);
								JFrame deleteDetailsFrame = new JFrame("K.R.I.T.H.I v1.0");
								JPanel deleteDetailsPanel = new JPanel(new FlowLayout());
								
								JPanel deleteDetailsTitlePanel = new JPanel(new FlowLayout());
								JLabel deleteDetailsLabel = new JLabel("Select a Website To Delete The Details");
								deleteDetailsLabel.setFont(new Font("Times New Roman", 1, 13));
								deleteDetailsTitlePanel.add(deleteDetailsLabel);
								
								JPanel optionsPanel = new JPanel(new FlowLayout());
								Box verticalBox = Box.createVerticalBox();
							
								List<Map<String, String>> allUserPasswords = specificUser.getListOfUserPasswords();
								for(Map<String, String> eachUserPassword : allUserPasswords){
									Set<String> allWebNames = eachUserPassword.keySet();
									for(String eachWebName : allWebNames){
										JCheckBox webOptionCheck = new JCheckBox(eachWebName);
										verticalBox.add(webOptionCheck);
									}
								}
								
								JPanel deletionButtonsPanel = new JPanel(new FlowLayout());
								JButton deleteButton = new JButton("Delete");
								JButton closeButton = new JButton("Close");
								deletionButtonsPanel.add(deleteButton);
								deletionButtonsPanel.add(closeButton);
								
								/*
								 * START: Adding ActionListener for Delete Button.
								 */
								deleteButton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										Component[] availableOptionsForDeletion = verticalBox.getComponents();
										List<String> websiteNames = new ArrayList<>();
										for(Component eachComponent : availableOptionsForDeletion){
											JCheckBox eachOption = (JCheckBox)eachComponent;
											if(eachOption.isSelected()){
												String optionName = eachOption.getText();
												websiteNames.add(optionName);
												System.out.println("Adding " + optionName + "...");
											}
										}
										
										service.deleteSpecificUserDetails(specificUser, websiteNames);
										appFrame.setEnabled(true);
										deleteDetailsFrame.dispose();
										
									}
								});
								/*
								 * END: Adding ActionListener for Delete Button.
								 */
								
								/*
								 * START: Adding ActionListener for Close Button.
								 */
								closeButton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										appFrame.setEnabled(true);
										deleteDetailsFrame.dispose();
										
									}
								});
								/*
								 * END: Adding ActionListener for Close Button.
								 */
								
								optionsPanel.add(verticalBox);
								deleteDetailsPanel.add(deleteDetailsTitlePanel);
								deleteDetailsPanel.add(optionsPanel);
								deleteDetailsPanel.add(deletionButtonsPanel);
								deleteDetailsFrame.getContentPane().add(deleteDetailsPanel);
								deleteDetailsFrame.setVisible(true);
								deleteDetailsFrame.setSize(250, 340);
								deleteDetailsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
								//deleteDetailsFrame.setResizable(false);
								deleteDetailsFrame.setLocation(screenDim.width / 2 - deleteDetailsFrame.getSize().width / 2, screenDim.height/2 - deleteDetailsFrame.getSize().height / 2);
								deleteDetailsFrame.setIconImage(frameImg.getImage());
								
							}
						});
						/*
						 * END: Adding ActionListener for Delete Details Button.
						 */
						
						/*
						 * START: Adding ActionListener for Edit Details button.
						 */
						editDetails.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								
							}
						});
						/*
						 * END: Adding ActionListener for Edit Details button.
						 */
						
						/*
						 * START: Adding ActionListener for Refresh Button.
						 */
						refresh.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								appRefreshCount++;
								System.out.println("App refreshed " + appRefreshCount + " time(s)");
								appPanel.removeAll();
								JPanel treePanel = new JPanel(new FlowLayout());
								String displayName = userNameStr.toUpperCase();
								DefaultMutableTreeNode userRoot = new DefaultMutableTreeNode(displayName + "'S PASSWORD VAULT");
								List<User> allUsers = service.fetchAllUsersInApp();
								for(User eachUser : allUsers){
									if(eachUser.getUserName().equalsIgnoreCase(specificUser.getUserName())){
										List<Map<String, String>> allDetails = eachUser.getListOfUserPasswords();
										if(allDetails.size() > 0){
											for(Map<String, String> userPasswordDetails : allDetails){
												for(Map.Entry<String, String> eachWebAndPassword : userPasswordDetails.entrySet()){
													String webSite = eachWebAndPassword.getKey();
													String userDetails = eachWebAndPassword.getValue();
													String[] userNameAndPassword = userDetails.split("\\|");
													String user = userNameAndPassword[0];
													String password = userNameAndPassword[1];
													
													DefaultMutableTreeNode websiteNode = new DefaultMutableTreeNode(webSite);
													websiteNode.add(new DefaultMutableTreeNode(user));
													websiteNode.add(new DefaultMutableTreeNode(password));
													
													userRoot.add(websiteNode);
												}
											}
										}
									}
									break;
								}
								JTree resultTree = new JTree(userRoot);
								treePanel.add(resultTree);
								JScrollPane treeScrollPane = new JScrollPane(treePanel);
								treeScrollPane.setPreferredSize(new Dimension(300, 200));
								appPanel.add(appNamePanel);
								appPanel.add(treeScrollPane);
								appPanel.add(addPanel);
								appFrame.setSize(502, 340);
								if(appRefreshCount > 1){
									int widthSize = 502 + appRefreshCount;
									appFrame.setSize(widthSize, 340);
								}
							}
						});
						/*
						 * END: Adding ActionListener for Refresh Button.
						 */
						/*
						 * START: Adding ActionListener for Add Details Button.
						 */
						addButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								appFrame.setEnabled(false);
								JFrame addDetailsFrame = new JFrame("K.R.I.T.H.I v1.0");
								JPanel addDetailsPanel = new JPanel(new FlowLayout());
								JPanel headingPanel = new JPanel(new FlowLayout());
								JLabel heading = new JLabel("Add Details");
								heading.setFont(new Font("Times New Roman", 1, 14));
								headingPanel.add(heading);
								
								JPanel webSitePanel = new JPanel(new FlowLayout());
								JLabel webSite = new JLabel("Enter Website Name: ");
								webSite.setFont(new Font("Times New Roman", 1, 12));
								JTextField websiteField = new JTextField(20);
								webSitePanel.add(webSite);
								webSitePanel.add(websiteField);
								
								JPanel userNameAdditionPanel = new JPanel(new FlowLayout());
								JLabel userAdditionField = new JLabel("Enter User Name: ");
								userAdditionField.setFont(new Font("Times New Roman", 1, 12));
								JTextField userField = new JTextField(20);
								userNameAdditionPanel.add(userAdditionField);
								userNameAdditionPanel.add(userField);
								
								JPanel passwordAdditionPanel = new JPanel(new FlowLayout());
								JLabel passwordAdditionLabel = new JLabel("Enter Password: ");
								passwordAdditionLabel.setFont(new Font("Times New Roman", 1, 12));
								JPasswordField password = new JPasswordField(20);
								passwordAdditionPanel.add(passwordAdditionLabel);
								passwordAdditionPanel.add(password);
								
								JPanel buttonGuardPanel = new JPanel(new FlowLayout());
								JButton registerDetails = new JButton("Register Details");
								JButton closeButton = new JButton("Close");
								buttonGuardPanel.add(registerDetails);
								buttonGuardPanel.add(closeButton);
								closeButton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										appFrame.setEnabled(true);
										addDetailsFrame.dispose();
										
									}
								});
								registerDetails.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										appFrame.setEnabled(true);
										String webName = websiteField.getText();
										String userValue = userField.getText();
										String passValue = new String(password.getPassword());
										String userPasscodeCombo = userValue + "|" + passValue;
										Map<String, String> webDetails = new HashMap<>();
										webDetails.put(webName, userPasscodeCombo);
										List<Map<String, String>>userWebDetails = specificUser.getListOfUserPasswords();
										userWebDetails.add(webDetails);
										service.saveUser(specificUser);
										
										addDetailsFrame.dispose();
										
									}
								});
								
								
								addDetailsPanel.add(headingPanel);
								addDetailsPanel.add(webSitePanel);
								addDetailsPanel.add(userNameAdditionPanel);
								addDetailsPanel.add(passwordAdditionPanel);
								addDetailsPanel.add(buttonGuardPanel);
								addDetailsFrame.getContentPane().add(addDetailsPanel);
								addDetailsFrame.setVisible(true);
								addDetailsFrame.setSize(450, 240);
								addDetailsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
								addDetailsFrame.setResizable(false);
								addDetailsFrame.setLocation(screenDim.width / 2 - addDetailsFrame.getSize().width / 2, screenDim.height/2 - addDetailsFrame.getSize().height / 2);
								addDetailsFrame.setIconImage(frameImg.getImage());
							}
						});
						/*
						 * END: Adding ActionListener for Add Details Button.
						 */
						addPanel.add(addButton);
						addPanel.add(editDetails);
						addPanel.add(deleteDetails);
						addPanel.add(refresh);
						appPanel.add(appNamePanel);
						appPanel.add(treeScrollPane);
						appPanel.add(addPanel);
						appFrame.setSize(500, 340);
					}else{
						System.out.println("@@@ Incorrect Password!! Please enter correct password!! @@@");
					}
					
				}else{
					appFrame.setEnabled(false);
					JFrame popup = new JFrame("K.R.I.T.H.I v1.0");
					JPanel popupPanel = new JPanel(new FlowLayout());
					JPanel msgPanel = new JPanel(new FlowLayout());
					JLabel msgLabel = new JLabel("User " + userNameStr + " is not registered in the System!!");
					msgLabel.setFont(new Font("Times New Roman", 1, 14));
					msgPanel.add(msgLabel);
					
					JPanel buttonPanel = new JPanel(new FlowLayout());
					JButton okButton = new JButton("Ok");
					buttonPanel.add(okButton);
					
					okButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							appFrame.setEnabled(true);
							popup.dispose();
							
						}
					});
					
					popupPanel.add(msgPanel);
					popupPanel.add(buttonPanel);
					popup.getContentPane().add(popupPanel);
					popup.setVisible(true);
					popup.setSize(280, 140);
					popup.setResizable(false);
					popup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					popup.setLocation(screenDim.width / 2 - popup.getSize().width / 2, screenDim.height/2 - popup.getSize().height / 2);
					popup.setIconImage(frameImg.getImage());
					System.out.println("User " + userNameStr + " does not exists in system!!");
				}
				
			}
		});
		/*
		 * END: Adding ActionListener for login Button. 
		 */
		//END: Adding button actions.
		/*
		 * END: Adding login and register buttons
		 */
		
		appPanel.add(appNamePanel);
		appPanel.add(userNameTextBoxPanel);
		appPanel.add(passwordPanel);
		appPanel.add(loginAndRegisterPanel);
		appFrame.getContentPane().add(appPanel);
		appFrame.setVisible(true);
		appFrame.setSize(400, 240);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.setResizable(false);
		appFrame.setLocation(screenDim.width / 2 - appFrame.getSize().width / 2, screenDim.height/2 - appFrame.getSize().height / 2);
		appFrame.setIconImage(frameImg.getImage());
	}
	
	/**
	 * App launcher
	 * @param args
	 */
	public static void main(String[] args) {
		AppBuilder app = new AppBuilder();
		app.buildUI();
	}
}
