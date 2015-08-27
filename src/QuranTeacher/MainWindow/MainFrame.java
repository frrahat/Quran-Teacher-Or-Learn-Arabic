/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JSplitPane;
















import QuranTeacher.Basics.Ayah;
import QuranTeacher.Dialogs.AboutDialog;
import QuranTeacher.Dialogs.AdvancedSettingsDialog;
import QuranTeacher.Dialogs.HelpDialog;
import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Dialogs.ShowNewUpdateDialog;
import QuranTeacher.Dialogs.UpdateSettingDialog;
import QuranTeacher.Interfaces.AudioButtonListener;
import QuranTeacher.Interfaces.PreferencesSaveListener;
import QuranTeacher.Interfaces.AyahSelectionListener;
import QuranTeacher.Interfaces.UpdateActivityReturnListener;
import QuranTeacher.Interfaces.UserInputListener;
import QuranTeacher.MainWindow.MainDisplayPart.AnimationPanel;
import QuranTeacher.MainWindow.MainDisplayPart.DisplayPanel;
import QuranTeacher.MainWindow.MainDisplayPart.StartUpLoaderPanel;
import QuranTeacher.MainWindow.MainDisplayPart.StartUpLoaderPanel.StartButtonActionListener;
import QuranTeacher.MainWindow.MainDisplayPart.TranslationPanel;
import QuranTeacher.MainWindow.MainDisplayPart.DisplayPanel.DisplayPage;
import QuranTeacher.MainWindow.SidePart.SelectionPanel;
import QuranTeacher.MainWindow.SidePart.SidePanel;
import QuranTeacher.Preferences.deltaPixelProperty;

import QuranTeacher.Utils.Updater;
import QuranTeacher.Utils.VersionInfo;
import QuranTeacher.WordInformation.WordInfoLoader;

public class MainFrame extends JFrame {

	/**
	 * Creates the full application frame
	 */
	public static final String AppName="Quran Teacher or Learn Arabic";
	public static final String version="1.2.1";
	private String[] args={"QT",version};
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private final DisplayPanel displayPanel;
	private final PreferencesDialog preferencesDialog;
	
	private final JButton btnRestartDisplay;
	private final JButton btnPauseDisplay;
	private final JButton btnSpeedUp;
	private final JButton btnSpeedDown;
	
	private final AnimationPanel animPanel;
	private final TranslationPanel translationPanel;
	//private final TafsirPanel tafsirPanel;

	private SidePanel sidePanel;
	private boolean isActionButtonEnabled;
	
	public MainFrame() {
		setTitle(AppName+" "+version);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainFrame.class.getResource("/QuranTeacher/images/icon128.png")));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 900, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				StartUpLoaderPanel startUpLoaderPanel=
							displayPanel.getStartUpLoaderPanel();
				
				startUpLoaderPanel.setLoadingCompletionListener(new StartUpLoaderPanel.LoadingCompleted() {
					
					@Override
					public void startInitializingTask() {
						sidePanel.getSelectionPanel().setComboboxModels();
						//preferences dialog initializes and gets all prefs 
						//from file before window opens
						SelectionPanel.setSelectionIndex(
								PreferencesDialog.getAudioPref().getCurrentAyah());
						
						if(!sidePanel.getInformationPanel().isInfoSet()){
							sidePanel.getInformationPanel().setInfo(SelectionPanel.
									getSelectedAyah().suraIndex);
						}
					}
				});
				
				startUpLoaderPanel.setStartButtonActionListener(new StartButtonActionListener() {
					
					@Override
					public void startButtonClicked() {
						startAnimation(SelectionPanel.getSelectedAyah());
					}
				});
				
				
				startUpLoaderPanel.startLoading();
				
				//updater
				if(UpdateSettingDialog.isToCheckForUpdate()){
					Updater updater=new Updater();
					updater.setUpdateActivityReturnListener(new UpdateActivityReturnListener() {
						
						@Override
						public void nextToDo(boolean wasDownloadSuccess, VersionInfo newVersionInfo) {
							if(newVersionInfo!=null){
					            Toolkit.getDefaultToolkit().beep();
								new ShowNewUpdateDialog(newVersionInfo).setVisible(true);
							}
						}
					});
					
					updater.startUpdateActivity();
				}
				
			};
			
			
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.out.println("closing");
				storeAllPrefs();
				System.exit(0);
			}
		});
		
		System.out.println("Starting Quran Teacher or Learn Arabic "+version);
		preferencesDialog=new PreferencesDialog();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.BLACK);
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.ORANGE);
		mnFile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnFile);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setForeground(Color.ORANGE);
		mntmAbout.setBackground(Color.DARK_GRAY);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog aboutDialog=new AboutDialog();
				aboutDialog.setVisible(true);
			}
		});
		mntmAbout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnFile.add(mntmAbout);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setForeground(Color.ORANGE);
		mntmHelp.setBackground(Color.DARK_GRAY);
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpDialog aboutDialog=new HelpDialog();
				aboutDialog.setVisible(true);
			}
		});
		
		mntmHelp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnFile.add(mntmHelp);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setBackground(Color.DARK_GRAY);
		mntmExit.setForeground(Color.ORANGE);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeAllPrefs();
				System.exit(0);
			}
		});
		mntmExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnFile.add(mntmExit);
		
		JMenu mnSetting = new JMenu("Setting");
		mnSetting.setForeground(Color.ORANGE);
		mnSetting.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnSetting);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.setForeground(Color.ORANGE);
		mntmPreferences.setBackground(Color.DARK_GRAY);
		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preferencesDialog.setVisible(true);
			}
		});
		mntmPreferences.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnSetting.add(mntmPreferences);
		
		JMenuItem mntmUpdateSetting = new JMenuItem("Update Settings");
		mntmUpdateSetting.setForeground(Color.ORANGE);
		mntmUpdateSetting.setBackground(Color.DARK_GRAY);
		mntmUpdateSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateSettingDialog().setVisible(true);
			}
		});
		mntmUpdateSetting.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnSetting.add(mntmUpdateSetting);
		
		JMenuItem mntmadvancedSetting = new JMenuItem("Advanced Settings");
		mntmadvancedSetting.setForeground(Color.ORANGE);
		mntmadvancedSetting.setBackground(Color.DARK_GRAY);
		mntmadvancedSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdvancedSettingsDialog().setVisible(true);
			}
		});
		mntmadvancedSetting.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnSetting.add(mntmadvancedSetting);
		
		
		JMenu mnTools = new JMenu("Tools");
		mnTools.setForeground(Color.ORANGE);
		mnTools.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnTools);
		
		JMenuItem mntmUpdate = new JMenuItem("Check For Update");
		mntmUpdate.setForeground(Color.ORANGE);
		mntmUpdate.setBackground(Color.DARK_GRAY);
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Updater updater=new Updater();
				updater.setUpdateActivityReturnListener(new UpdateActivityReturnListener() {
					
					@Override
					public void nextToDo(boolean wasDownloadSuccess,VersionInfo newVersionInfo) {
						if(newVersionInfo==null){
							if(!wasDownloadSuccess){
								JOptionPane.showMessageDialog(getParent(), "Failed to connect to the server.\n"
										+ "Please check your internet connection.","Failure",JOptionPane.ERROR_MESSAGE);
							}else{
								JOptionPane.showMessageDialog(getParent(), "Current version is "+version+", "
										+ "And it is up to date.");
							}
						}
						
						else{
							if(!wasDownloadSuccess){
								JOptionPane.showMessageDialog(getParent(), "An upgraded version has been released earlier. "
										+ "But failed to connect to the server.\n"
										+ "Please check your internet connection for the latest update.");
							}
				            Toolkit.getDefaultToolkit().beep();
							new ShowNewUpdateDialog(newVersionInfo).setVisible(true);
						}
					}
				});
				
				updater.startUpdateActivity();
			}
		});
		mntmUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnTools.add(mntmUpdate);
		
		JMenuItem mntmDownload = new JMenuItem("Download audios and images");
		mntmDownload.setForeground(Color.ORANGE);
		mntmDownload.setBackground(Color.DARK_GRAY);
		mntmDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath=System.getProperty("user.dir")+"/tools/Resource Downloader.jar";
				filePath="\""+filePath+"\"";
				
				try {
					Runtime.getRuntime().exec("java -jar "+filePath+" "+args[0]+" "+args[1]);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(getParent(),
							"Something went wrong. IOException Occured.",
							"Oops",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mntmDownload.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnTools.add(mntmDownload);
		
		JMenuItem mntmHitEditor = new JMenuItem("Edit or Play Hits");
		mntmHitEditor.setForeground(Color.ORANGE);
		mntmHitEditor.setBackground(Color.DARK_GRAY);
		mntmHitEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!WordInfoLoader.isLoaded){
					JOptionPane.showMessageDialog(getParent(), 
							"Word Information is not loaded yet. Wait a little "
							+ "and try again.");
					return;
				}
				animPanel.showHitFileEditorDialog();
				if(displayPanel.getDisplayPage()!=DisplayPage.AnimationPage)
				{
					displayPanel.setDisplayPage(DisplayPage.AnimationPage);
					animPanel.startAnimationTimer();
					animPanel.updateWordStartPoint();
				}
				animPanel.setAnimationStateSimple();
				enableActionButtons();
				
			}
		});
		mntmHitEditor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnTools.add(mntmHitEditor);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.BLACK);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnRestartDisplay = new JButton("Restart Display");
		btnRestartDisplay.setForeground(Color.YELLOW);
		btnRestartDisplay.setBackground(Color.BLACK);
		btnRestartDisplay.setEnabled(false);
		btnRestartDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPanel.setDisplayAction("restart");
				animPanel.refresh();
				
				btnPauseDisplay.setText("Pause Display");
			}
		});
		btnRestartDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBar.add(btnRestartDisplay);
		
		btnPauseDisplay = new JButton("Pause Display");
		btnPauseDisplay.setForeground(Color.YELLOW);
		btnPauseDisplay.setBackground(Color.BLACK);
		btnPauseDisplay.setEnabled(false);
		btnPauseDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(animPanel.isAnimationRunning())
				{
					animPanel.setDisplayAction("pause");
					btnPauseDisplay.setText("Resume Display");
				}
				else
				{
					btnPauseDisplay.setText("Pause Display");
					animPanel.setDisplayAction("resume");
				}
				animPanel.refresh();
			}
		});
		btnPauseDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBar.add(btnPauseDisplay);
		
		btnSpeedUp = new JButton("Speed Up");//increase deltaPixel
		btnSpeedUp.setForeground(Color.YELLOW);
		btnSpeedUp.setBackground(Color.BLACK);
		btnSpeedUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPanel.setDisplayAction("speedUp");
				updateSpeedButton(true);
			}
		});
		btnSpeedUp.setEnabled(false);
		btnSpeedUp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBar.add(btnSpeedUp);
		
		btnSpeedDown = new JButton("Speed Down");//decrease deltaPixel
		btnSpeedDown.setForeground(Color.YELLOW);
		btnSpeedDown.setBackground(Color.BLACK);
		btnSpeedDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPanel.setDisplayAction("speedDown");
				updateSpeedButton(false);
			}
		});
		btnSpeedDown.setEnabled(false);
		btnSpeedDown.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBar.add(btnSpeedDown);
		
		
		sidePanel=new SidePanel();
		//sidePanel.setVisible(false);
		sidePanel.setBackground(Color.RED);
		//contentPane.add(sidePanel,BorderLayout.WEST);
		
		displayPanel=new DisplayPanel();
		
		animPanel=displayPanel.getAnimationPanel();
		translationPanel=displayPanel.getTranslationPanel();
		//tafsirPanel=displayPanel.getTafsirPanel();
		
		animPanel.setUserInputListener(new UserInputListener() {
			
			@Override
			public void pauseStateChanged(boolean paused) {
				if(paused)
					btnPauseDisplay.setText("Resume Display");
				else
					btnPauseDisplay.setText("Pause Display");
			}

			@Override
			public void speedChanged(boolean upCalled) {
				updateSpeedButton(upCalled);
			}

		});

		
		
		final JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setEnabled(false);
		contentPane.add(splitPane,BorderLayout.CENTER);
		
		splitPane.setLeftComponent(sidePanel);
		splitPane.setRightComponent(displayPanel);
		
		
		BasicSplitPaneUI l_ui = (BasicSplitPaneUI) splitPane.getUI();
        BasicSplitPaneDivider l_divider = l_ui.getDivider();
        l_divider.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Dimension l_pane_size = splitPane.getSize();
                
                int l_new_loc = splitPane.getDividerLocation() + e.getX();
                if (l_new_loc >= 0 && l_new_loc <= l_pane_size.width) {
                    splitPane.setDividerLocation(l_new_loc);
                }
        		
                
            }
        });
		//when go,prev,next button clicked
		sidePanel.setSelectionListener(new AyahSelectionListener() {
			
			@Override
			public void ayahSelected(Ayah ayah) {
				startAnimation(ayah);
			}
		});
		
		sidePanel.setAudioButtonListener(new AudioButtonListener() {
			
			@Override
			public void actionPerformed(String actionCommand) {
				animPanel.setAudioAction(actionCommand);
			}
		});
		
		
		
		//preferences saving
		preferencesDialog.setPreferencesSaveListener(new PreferencesSaveListener() {
			
			@Override
			public void preferencesSaved() {
				updateAllPrefs();
			}
		});
		
	}
	
	
	private void startAnimation(Ayah ayah) {
		//System.out.println("In main:"+ayah.suraIndex+" "+ayah.ayahIndex);
		//animPanel.setAction("Showing Ayah no :"+(ayah.ayahIndex+1)+" from Sura "+
		//SuraInformation.suraInformations[ayah.suraIndex].title);
		
		if(displayPanel.getDisplayPage()!=DisplayPage.AnimationPage)
		{
			displayPanel.setDisplayPage(DisplayPage.AnimationPage);
			animPanel.startAnimationTimer();
			animPanel.updateWordStartPoint();
		}
		animPanel.setAnimationStateSimple();	
		animPanel.setAyah(ayah);
		
		enableActionButtons();
	}

	private void updateSpeedButton(boolean upCalled)
	{
		int deltaPixel=animPanel.getDeltaPixel();
		if(upCalled)
		{
			if(deltaPixel==deltaPixelProperty.maxDeltaPixel)
			{
				btnSpeedUp.setEnabled(false);
			}
			if(btnSpeedDown.isEnabled()==false && deltaPixel>deltaPixelProperty.minDeltaPixel)
				btnSpeedDown.setEnabled(true);
		}
		else
		{
			if(deltaPixel==deltaPixelProperty.minDeltaPixel)
			{
				btnSpeedDown.setEnabled(false);
			}
			if(btnSpeedUp.isEnabled()==false && deltaPixel>deltaPixelProperty.minDeltaPixel)
				btnSpeedUp.setEnabled(true);
		}
	}
	
	private void storeAllPrefs()
	{
		System.out.println("Storing all prefs");
		
		animPanel.getAnimationPref().savePrefToFile();
		animPanel.getWbWFontPref().savePrefToFile();
		animPanel.getAdvancedAnimPref().savePrefToFile();
		animPanel.getAudioPref().savePrefToFile();
		
		translationPanel.getTranslationPref().savePrefToFile();

	}
	
	//when save clicked
	private void updateAllPrefs() {
		animPanel.updatAnimPreferences();
		animPanel.updateWbWFontPref();
		animPanel.updateAudioPref();//same update flagged for two different panel
		sidePanel.getAudioNavPanel().updateAudioPref();
		
		translationPanel.updateTransPref();
	}


	private void enableActionButtons() {
		if(!isActionButtonEnabled){

			btnPauseDisplay.setEnabled(true);
			btnPauseDisplay.setText("Pause Display");
			btnRestartDisplay.setEnabled(true);
			
			int deltaPixel=animPanel.getDeltaPixel();
			
			if(deltaPixel<deltaPixelProperty.maxDeltaPixel)
				btnSpeedUp.setEnabled(true);//increase deltaPixel
			if(deltaPixel>deltaPixelProperty.minDeltaPixel)
				btnSpeedDown.setEnabled(true);//decrease deltaPixel
			
			isActionButtonEnabled=true;
		}
	}

}
