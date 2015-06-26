/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.SidePart;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.JRadioButton;

import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Cursor;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Interfaces.AudioButtonListener;
import QuranTeacher.Preferences.AudioPreferences;
import QuranTeacher.RenderAudio.Reciter;

public class AudioNavigationPanel extends JPanel {
	
	/**
	 * For handling quick audio on/off, pause/resume/stop of audio
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel lblProgressText;	
	//private boolean playing;
	private boolean paused;
	private AudioPreferences audioPrefs;
	private boolean isAudNavAudioOn;//uninmpotant, can be used as audioprefs.isAudioOn() instead
	
	private ImageIcon playIcon=new ImageIcon(AudioNavigationPanel.class.
			getResource("/QuranTeacher/images/audio/player-play16.gif"));
	private ImageIcon pauseIcon=new ImageIcon(AudioNavigationPanel.class.
			getResource("/QuranTeacher/images/audio/player-pause16.gif"));
	private ImageIcon stopIcon=new ImageIcon(AudioNavigationPanel.class.
			getResource("/QuranTeacher/images/audio/player-stop16.gif"));
	private JButton btnStop;
	private JButton btnPause;
	private AudioButtonListener audioButtonListener;
	
	private static JRadioButton rdbtnOn;
	private static JRadioButton rdbtnOff;
	
	public AudioNavigationPanel() {
		
		audioPrefs=PreferencesDialog.getAudioPref();
		//playing=false;
		paused=false;
		
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Audio Navigation", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GREEN));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{59, 67, 186, 0};
		gridBagLayout.rowHeights = new int[]{25, 25, 16, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAudio = new JLabel("Audio :");
		lblAudio.setForeground(Color.ORANGE);
		lblAudio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAudio = new GridBagConstraints();
		gbc_lblAudio.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAudio.insets = new Insets(0, 0, 5, 5);
		gbc_lblAudio.gridx = 0;
		gbc_lblAudio.gridy = 0;
		add(lblAudio, gbc_lblAudio);
		
		rdbtnOn = new JRadioButton("ON");
		
		rdbtnOn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					audioPrefs.setAudioON(true);
					lblProgressText.setText("Audio is On");
				}
				else
				{
					audioPrefs.setAudioON(false);
					lblProgressText.setText("Audio is Off");
				}
			}
		});
		rdbtnOn.setBackground(Color.BLACK);
		rdbtnOn.setForeground(Color.GREEN);
		GridBagConstraints gbc_rdbtnOn = new GridBagConstraints();
		gbc_rdbtnOn.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnOn.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnOn.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOn.gridx = 1;
		gbc_rdbtnOn.gridy = 0;
		add(rdbtnOn, gbc_rdbtnOn);
		
		rdbtnOff = new JRadioButton("OFF");
		rdbtnOff.setForeground(Color.GREEN);
		rdbtnOff.setBackground(Color.BLACK);
		GridBagConstraints gbc_rdbtnOff = new GridBagConstraints();
		gbc_rdbtnOff.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnOff.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnOff.gridx = 2;
		gbc_rdbtnOff.gridy = 0;
		add(rdbtnOff, gbc_rdbtnOff);
		
		btnStop = new JButton("");
		btnStop.setBackground(Color.WHITE);
		btnStop.setIcon(stopIcon);
		btnStop.setToolTipText("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				paused=false;
				btnPause.setIcon(pauseIcon);
				btnPause.setToolTipText("Pause");
				
				audioButtonListener.actionPerformed("stop");
				lblProgressText.setText("stopped");
			}
		});
		
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 0;
		gbc_btnStop.gridy = 1;
		add(btnStop, gbc_btnStop);
		
		btnPause = new JButton("");
		btnPause.setBackground(Color.WHITE);
		btnPause.setToolTipText("Pause");
		btnPause.setIcon(pauseIcon);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Reciter.isAlive())
					return;
				
				if(paused)
				{
					paused=false;
					btnPause.setIcon(pauseIcon);
					btnPause.setToolTipText("Pause");
					lblProgressText.setText("Resumed");
					
					audioButtonListener.actionPerformed("resume");
				}
				else
				{
					paused=true;
					btnPause.setIcon(playIcon);
					btnPause.setToolTipText("Resume");
					lblProgressText.setText("Paused");
					
					audioButtonListener.actionPerformed("pause");
				}
			}
		});
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 1;
		add(btnPause, gbc_btnPause);
		
		JLabel lblProgress = new JLabel("Progress :");
		lblProgress.setForeground(Color.ORANGE);
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblProgress.insets = new Insets(0, 0, 0, 5);
		gbc_lblProgress.gridx = 0;
		gbc_lblProgress.gridy = 2;
		add(lblProgress, gbc_lblProgress);
		

		ButtonGroup buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnOn);
		buttonGroup.add(rdbtnOff);
		
		lblProgressText = new JLabel("New label");
		lblProgressText.setForeground(Color.YELLOW);
		GridBagConstraints gbc_lblProgressText = new GridBagConstraints();
		gbc_lblProgressText.anchor = GridBagConstraints.WEST;
		gbc_lblProgressText.gridwidth = 2;
		gbc_lblProgressText.gridx = 1;
		gbc_lblProgressText.gridy = 2;
		add(lblProgressText, gbc_lblProgressText);
		
		updateAudioPref();
	}

	public static void setProgressText(String text) {
		lblProgressText.setText(text);
	}
	
	private void refresh()
	{
		if(isAudNavAudioOn)
		{
			rdbtnOn.setSelected(true);
			lblProgressText.setText("Audio On");
		}
		else
		{
			rdbtnOff.setSelected(true);
			lblProgressText.setText("Audio Off");
		}
	}
	
	public void updateAudioPref()
	{
		//audioPrefs=PreferencesDialog.getAudioPref();
		isAudNavAudioOn=audioPrefs.isAudioON();
		refresh();
	}

	public void setAudioButtonListener(AudioButtonListener listener) {
		audioButtonListener=listener;
	}
}
