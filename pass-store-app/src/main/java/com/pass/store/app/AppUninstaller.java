package com.pass.store.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.pass.store.model.UserSystemProperties;

/**
 * Uninstaller for the PassStore app.
 * 
 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
 * @since v1.0
 *
 */
public class AppUninstaller{
	private JFrame mainUninstallerFrame;
	private JPanel mainUninstallerPanel;
	private Dimension screenDim;
	private ImageIcon frameImg;
	private JProgressBar dummyProgressBar;
	
	/**
	 * Background task that will increment the progress bar.
	 * 
	 * @author Shyam Baitmangalkar | catch.shyambaitmangalkar@gmail.com
	 * @since v1.0
	 */
	class Task extends SwingWorker<Void, Void>{

		/**
		 * 
		 */
		@Override
		protected Void doInBackground() throws Exception {
		        int progress = 0;
		        //Initialize progress property.
		        setProgress(0);
		        while (progress < 100) {
		            //Sleep for up to one second.
		            try {
		                //original code: Thread.sleep(random.nextInt(1000));
		                Thread.sleep(10);
		            } catch (InterruptedException ignore) {}
		            //Make random progress.
		            //originalcode: progress += random.nextInt(10);
		            progress++;
		            setProgress(Math.min(progress, 100));
		        }
		        return null;
		}
	}

	/**
	 * Builds the swing UI for the uninstaller.
	 * @since v1.0
	 */
	public void buildUninstaller() {
		screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		 * To be used while running locally in IDE. Comment this below line
		 * while extracting the executable jar.
		 */
		frameImg = new ImageIcon("src/main/resources/images/K_icon.jpg");
		/*
		 * To be used when extracting the executable jar. Uncomment the below
		 * statement.
		 */
		//frameImg = new ImageIcon(getClass().getResource("/resources/images/K_icon.jpg"));
		mainUninstallerFrame = new JFrame("PassStore v1.0");
		mainUninstallerPanel = new JPanel(new FlowLayout());

		JPanel titlePanel = new JPanel(new FlowLayout());
		JLabel titleLabel = new JLabel("Uninstaller Wizzard");
		titleLabel.setFont(new Font("Times New Roman", 1, 14));
		titlePanel.add(titleLabel);

		JPanel messagePanel = new JPanel(new FlowLayout());
		JLabel uninstallMsgLabel = new JLabel("Are you sure you want to uninstall PassStore from your system?");
		uninstallMsgLabel.setFont(new Font("Times New Roman", 1, 13));
		messagePanel.add(uninstallMsgLabel);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton yesButton = new JButton("Yes");
		JButton cancelButton = new JButton("Cancel");

		buttonPanel.add(yesButton);
		buttonPanel.add(cancelButton);

		/*
		 * START: Adding ActionListener for cancel button.
		 */
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainUninstallerFrame.dispose();
				System.exit(0);

			}
		});
		/*
		 * END: Adding ActionListener for cancel button.
		 */

		/*
		 * START: Adding ActionListener for Yes Button.
		 */
		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainUninstallerFrame.setEnabled(false);
				JFrame subUninstallFrame = new JFrame("K.R.I.T.H.I v1.0");
				JPanel subUninstallPanel = new JPanel(new FlowLayout());

				JPanel genLabelPanel = new JPanel(new FlowLayout());
				JLabel genLabel = new JLabel("Uninstalling...");
				genLabel.setFont(new Font("Times New Roman", 1, 13));
				genLabelPanel.add(genLabel);

				JPanel progressPanel = new JPanel(new FlowLayout());
				dummyProgressBar = new JProgressBar(0, 100);
				dummyProgressBar.setStringPainted(true);
				progressPanel.add(dummyProgressBar);
				
				JPanel buttonPanel = new JPanel(new FlowLayout());
				JButton startButton = new JButton("Start");
				JButton cancel = new JButton("Cancel");
				buttonPanel.add(startButton);
				buttonPanel.add(cancel);
				/*
				 * START: Adding ActionListener for Cancel Button.
				 */
				cancel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						mainUninstallerFrame.setEnabled(true);
						subUninstallFrame.dispose();
						
					}
				});
				/*
				 * END: Adding ActionListener for Cancel Button.
				 */
				/*
				 * START: Adding ActionListener for Start Button.
				 */
				startButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						cancel.setEnabled(false);
						startButton.setEnabled(false);
						Task task = new Task();
						task.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if ("progress" == evt.getPropertyName()) {
									int progress = (Integer) evt.getNewValue();
						            dummyProgressBar.setValue(progress);
								}
							}
						});
						
						task.execute();
						String appFolderPath = UserSystemProperties.getAppFolderPath();
						
						String aboutAppFilePath = appFolderPath + File.separator + AppBuilder.ABOUT_APP_FILE_NAME;
						
						String appPropFilePath = UserSystemProperties.getAppPropFilePath();
						
						/*
						 * Delete the app folder
						 */
						try{
							System.out.println("Deleting " + aboutAppFilePath + "...");
							Files.delete(Paths.get(aboutAppFilePath));
							System.out.println("Deleting " + appPropFilePath + "...");
							Files.delete(Paths.get(appPropFilePath));
							System.out.println("Deleting " + appFolderPath + "...");
							Files.delete(Paths.get(appFolderPath));
						}catch(IOException ioe){
							ioe.printStackTrace();
						}
						JFrame finalMsgFrame = new JFrame("K.R.I.T.H.I v1.0");
						JPanel finalMsgPanel = new JPanel(new FlowLayout());
						JPanel msgPanel = new JPanel(new FlowLayout());
						JLabel messageLabel1 = new JLabel("K.R.I.T.H.I has been successfully uninstalled from your system!!");
						messageLabel1.setFont(new Font("Times New Roman", 1, 14));
						msgPanel.add(messageLabel1);
						
						
						JPanel buttonPanel = new JPanel(new FlowLayout());
						JButton closeButton = new JButton("Close");
						closeButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								subUninstallFrame.dispose();
								System.exit(0);
								
							}
						});
						buttonPanel.add(closeButton);
						finalMsgPanel.add(msgPanel);
						finalMsgPanel.add(buttonPanel);
						finalMsgFrame.getContentPane().add(finalMsgPanel);
						finalMsgFrame.setVisible(true);
						finalMsgFrame.setSize(420, 150);
						finalMsgFrame.setLocation(screenDim.width / 5 - finalMsgFrame.getSize().width / 5, screenDim.height / 4 - finalMsgFrame.getSize().height / 4);
						finalMsgFrame.setIconImage(frameImg.getImage());
						finalMsgFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						finalMsgFrame.setResizable(false);
						
					}
				});
				/*
				 * END: Adding ActionListener for Start Button.
				 */
				
				subUninstallPanel.add(genLabel);
				subUninstallPanel.add(progressPanel);
				subUninstallPanel.add(buttonPanel);
				subUninstallFrame.getContentPane().add(subUninstallPanel);
				subUninstallFrame.setVisible(true);
				subUninstallFrame.setSize(250, 180);
				subUninstallFrame.setResizable(false);
				subUninstallFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				subUninstallFrame.setLocation(screenDim.width / 2 - subUninstallFrame.getSize().width / 2,
						screenDim.height / 2 - subUninstallFrame.getSize().height / 2);
				subUninstallFrame.setIconImage(frameImg.getImage());

			}
		});
		/*
		 * END: Adding ActionListener for Yes Button.
		 */

		mainUninstallerPanel.add(titlePanel);
		mainUninstallerPanel.add(messagePanel);
		mainUninstallerPanel.add(buttonPanel);
		mainUninstallerFrame.getContentPane().add(mainUninstallerPanel);
		mainUninstallerFrame.setVisible(true);
		mainUninstallerFrame.setSize(370, 200);
		mainUninstallerFrame.setResizable(false);
		mainUninstallerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainUninstallerFrame.setLocation(screenDim.width / 2 - mainUninstallerFrame.getSize().width / 2,
				screenDim.height / 2 - mainUninstallerFrame.getSize().height / 2);
		mainUninstallerFrame.setIconImage(frameImg.getImage());
	}
	

	/**
	 * Application launcher.
	 * 
	 * @param args
	 * @since v1.0
	 */
	public static void main(String[] args) {
		AppUninstaller uninstaller = new AppUninstaller();
		uninstaller.buildUninstaller();
	}
}
