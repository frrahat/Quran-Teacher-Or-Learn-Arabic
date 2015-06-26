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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JSplitPane;
























import QuranTeacher.Basics.Ayah;
import QuranTeacher.Dialogs.AboutDialog;
import QuranTeacher.Dialogs.HelpDialog;
import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Interfaces.AudioButtonListener;
import QuranTeacher.Interfaces.PreferencesSaveListener;
import QuranTeacher.Interfaces.SelectionListener;
import QuranTeacher.Interfaces.UserInputListener;
import QuranTeacher.MainWindow.MainDisplayPart.AnimationPanel;
import QuranTeacher.MainWindow.MainDisplayPart.DisplayPanel;
import QuranTeacher.MainWindow.MainDisplayPart.TranslationPanel;
import QuranTeacher.MainWindow.MainDisplayPart.DisplayPanel.DisplayPage;
import QuranTeacher.MainWindow.SidePart.SidePanel;
import QuranTeacher.Preferences.deltaPixelProperty;

public class MainFrame extends JFrame {

	/**
	 * Creates the full application frame
	 */
	public static String version="1.2.0";
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
	public ProgressMonitor progressMonitor;
	protected Task task;
	
	public MainFrame() {
		setTitle("Quran Teacher or Learn Arabic "+version);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainFrame.class.getResource("/QuranTeacher/images/icon128.png")));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 900, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				progressMonitor = new ProgressMonitor(getParent(),
						"Running a Long Task", "", 0, 100);
				progressMonitor.setProgress(0);
				task = new Task();
				task.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if ("progress" == evt.getPropertyName() ) {
				            int progress = (Integer) evt.getNewValue();
				            progressMonitor.setProgress(progress);
				            String message =
				                String.format("Completed %d%%.\n", progress);
				            progressMonitor.setNote(message);
				            if (progressMonitor.isCanceled() || task.isDone()) {
				                Toolkit.getDefaultToolkit().beep();
				                if (progressMonitor.isCanceled()) {
				                    task.cancel(true);
				                } 
				            }
				        }
					}
				});
				
				task.execute();
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
		
		JMenu mnTools = new JMenu("Tools");
		mnTools.setForeground(Color.ORANGE);
		mnTools.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuBar.add(mnTools);
		
		JMenuItem mntmUpdate = new JMenuItem("Update");
		mntmUpdate.setForeground(Color.ORANGE);
		mntmUpdate.setBackground(Color.DARK_GRAY);
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(),
						"Updates not available.",
						"Requires Manual Check",JOptionPane.ERROR_MESSAGE);
			}
		});
		mntmUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnTools.add(mntmUpdate);
		
		JMenuItem mntmDownload = new JMenuItem("Download audios and images");
		mntmDownload.setForeground(Color.ORANGE);
		mntmDownload.setBackground(Color.DARK_GRAY);
		mntmDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath=System.getProperty("user.dir")+"/tools/Downloader.jar";
				filePath="\""+filePath+"\"";
				try {
					Runtime.getRuntime().exec("java -jar "+filePath+" "+args[0]+" "+args[1]);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(getParent(),
							"Downloader couldn't be started. Try Downloader.jar",
							"Oops",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mntmDownload.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mnTools.add(mntmDownload);
		
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

		
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setEnabled(false);
		contentPane.add(splitPane,BorderLayout.CENTER);
		
		splitPane.setLeftComponent(sidePanel);
		splitPane.setRightComponent(displayPanel);
		
		
		
		
		//when go,prev,next button clicked
		sidePanel.setSelectionListener(new SelectionListener() {
			
			@Override
			public void ayahSelected(Ayah ayah) {
				//System.out.println("In main:"+ayah.suraIndex+" "+ayah.ayahIndex);
				//animPanel.setAction("Showing Ayah no :"+(ayah.ayahIndex+1)+" from Sura "+
				//SuraInformation.suraInformations[ayah.suraIndex].title);
				
				if(displayPanel.getDisplayPage()!=DisplayPage.AnimationPage)
				{
					displayPanel.setDisplayPage(DisplayPage.AnimationPage);
					animPanel.startAnimationTimer();
				}
					
				animPanel.setAyah(ayah);
				
				btnPauseDisplay.setEnabled(true);
				btnPauseDisplay.setText("Pause Display");
				btnRestartDisplay.setEnabled(true);
				
				int deltaPixel=animPanel.getDeltaPixel();
				
				if(deltaPixel<deltaPixelProperty.maxDeltaPixel)
					btnSpeedUp.setEnabled(true);//increase deltaPixel
				if(deltaPixel>deltaPixelProperty.minDeltaPixel)
					btnSpeedDown.setEnabled(true);//decrease deltaPixel
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
	
	 class Task extends SwingWorker<Void, Void> {
	        @Override
	        public Void doInBackground() {
	            Random random = new Random();
	            int progress = 0;
	            setProgress(0);
	            try {
	                Thread.sleep(1000);
	                while (progress < 100 && !isCancelled()) {
	                    //Sleep for up to one second.
	                    Thread.sleep(random.nextInt(1000));
	                    //Make random progress.
	                    progress += random.nextInt(10);
	                    setProgress(Math.min(progress, 100));
	                }
	            } catch (InterruptedException ignore) {}
	            return null;
	        }
	 
	        @Override
	        public void done() {
	            Toolkit.getDefaultToolkit().beep();
	            //startButton.setEnabled(true);
	            progressMonitor.close();
	        }
	    }
}
