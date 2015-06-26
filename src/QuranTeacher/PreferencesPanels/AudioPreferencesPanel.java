/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.PreferencesPanels;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JRadioButton;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import QuranTeacher.FilePaths;
import QuranTeacher.Preferences.AudioPreferences;

import javax.swing.JTextArea;

public class AudioPreferencesPanel extends JPanel {

	/**
	 * Preferences panel to handle audio preferences. It doesn't extends
	 * the PreferencesPanel class
	 */
	private static final long serialVersionUID = 1L;
	private List<String> QariNames=new ArrayList<>();
	private static List<String> audioSourceLinks=new ArrayList<>();
	private AudioPreferences audioSetupPrefs;
	private JRadioButton rdbtnOn;
	private JRadioButton rdbtnOff;
	
	private JComboBox<String>comboBox;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AudioPreferencesPanel(final AudioPreferences audioPrefs) {
		this.audioSetupPrefs=audioPrefs;
		
		setBackground(Color.DARK_GRAY);
		setForeground(Color.RED);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 32, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel("Recitation Preferences");
		lblHeader.setForeground(Color.MAGENTA);
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridwidth = 4;
		gbc_lblHeader.insets = new Insets(0, 0, 5, 0);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblAudioState = new JLabel("Recitation State :");
		lblAudioState.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAudioState.setForeground(Color.ORANGE);
		GridBagConstraints gbc_lblAudioState = new GridBagConstraints();
		gbc_lblAudioState.insets = new Insets(0, 0, 5, 5);
		gbc_lblAudioState.gridx = 0;
		gbc_lblAudioState.gridy = 2;
		add(lblAudioState, gbc_lblAudioState);
		
		rdbtnOn = new JRadioButton("ON");
		rdbtnOn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		rdbtnOn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					audioSetupPrefs.setAudioON(true);
					//System.out.println("On");
				}
				else
				{
					audioSetupPrefs.setAudioON(false);
					//System.out.println("Off");
				}
			}
		});
		rdbtnOn.setBackground(Color.DARK_GRAY);
		rdbtnOn.setForeground(Color.GREEN);
		GridBagConstraints gbc_rdbtnOn = new GridBagConstraints();
		gbc_rdbtnOn.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOn.gridx = 1;
		gbc_rdbtnOn.gridy = 2;
		add(rdbtnOn, gbc_rdbtnOn);
		
		rdbtnOff = new JRadioButton("Off");
		rdbtnOff.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnOff.setBackground(Color.DARK_GRAY);
		rdbtnOff.setForeground(Color.GREEN);
		GridBagConstraints gbc_rdbtnOff = new GridBagConstraints();
		gbc_rdbtnOff.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOff.gridx = 2;
		gbc_rdbtnOff.gridy = 2;
		add(rdbtnOff, gbc_rdbtnOff);
		
		ButtonGroup buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnOn);
		buttonGroup.add(rdbtnOff);
		
		JLabel lblSelectQari = new JLabel("Select Qari :");
		lblSelectQari.setForeground(Color.ORANGE);
		lblSelectQari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSelectQari = new GridBagConstraints();
		gbc_lblSelectQari.anchor = GridBagConstraints.WEST;
		gbc_lblSelectQari.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectQari.gridx = 0;
		gbc_lblSelectQari.gridy = 4;
		add(lblSelectQari, gbc_lblSelectQari);
		
		storeQariSource();
		
		comboBox = new JComboBox(QariNames.toArray());
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				audioSetupPrefs.setAudioSourceIndex(comboBox.getSelectedIndex());
				//System.out.println(AudioPreferences.audioSource);
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		int k=audioSetupPrefs.getAudioSourceIndex();
		if(k<0 || k>QariNames.size())k=0;
		comboBox.setSelectedIndex(k);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 0;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 4;
		add(comboBox, gbc_comboBox);
		
		JTextArea txtrNote = new JTextArea();
		txtrNote.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrNote.setEditable(false);
		txtrNote.setLineWrap(true);
		txtrNote.setWrapStyleWord(true);
		txtrNote.setForeground(Color.PINK);
		txtrNote.setBackground(Color.DARK_GRAY);
		txtrNote.setText("Note: If you change Qari name, it will take effect only for the \"next to be downoaded\" recitation files. So, the Qari for the previously downloaded files will not change.   ");
		GridBagConstraints gbc_txtrNote = new GridBagConstraints();
		gbc_txtrNote.gridwidth = 0;
		gbc_txtrNote.insets = new Insets(0, 0, 0, 5);
		gbc_txtrNote.fill = GridBagConstraints.BOTH;
		gbc_txtrNote.gridx = 0;
		gbc_txtrNote.gridy = 5;
		add(txtrNote, gbc_txtrNote);
		
		
		updateButtonGroup();

	}
	
	private void updateButtonGroup() {
		if(audioSetupPrefs.isAudioON())
			rdbtnOn.setSelected(true);
		else
			rdbtnOff.setSelected(true);		
	}

	private void storeQariSource()
	{
		InputStream inStream=this.getClass().getResourceAsStream(FilePaths.audioLinksFile);
		BufferedReader reader=new BufferedReader(new InputStreamReader(inStream));
		String text;
		try {
			while((text=reader.readLine())!=null)
			{
				if(text.startsWith("name"))
				{
					QariNames.add(text.split("=")[1]);
				}
				else if(text.startsWith("link"))
				{
					audioSourceLinks.add(text.split("=")[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getAudioSourceLink(int index) {
		return audioSourceLinks.get(index);
	}
	
	public void updateSetupPanel()
	{
		updateButtonGroup();
		
		int k=audioSetupPrefs.getAudioSourceIndex();
		if(k<0 || k>QariNames.size())k=0;
		comboBox.setSelectedIndex(k);
	}
}
