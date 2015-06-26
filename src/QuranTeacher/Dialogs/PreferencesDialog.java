/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import QuranTeacher.Interfaces.PreferencesSaveListener;
import QuranTeacher.Preferences.AnimationPreferences;
import QuranTeacher.Preferences.AudioPreferences;
import QuranTeacher.Preferences.Preferences;
import QuranTeacher.Preferences.TranslationPreferences;
import QuranTeacher.Preferences.WordByWordFontPref;
import QuranTeacher.PreferencesPanels.AnimationSetupPanel;
import QuranTeacher.PreferencesPanels.AudioPreferencesPanel;
import QuranTeacher.PreferencesPanels.PreferencesSetupPanel;
import QuranTeacher.PreferencesPanels.TranslationSetupPanel;
import QuranTeacher.PreferencesPanels.WbWFontSetupPanel;


public class PreferencesDialog extends JDialog {


	/**
	 * For handling preferences/settings options
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private PreferencesSaveListener saveListener;
	private JTabbedPane tabbedPane;
	private AnimationSetupPanel animationSetupPanel;
	private TranslationSetupPanel translationSetupPanel;
	private WbWFontSetupPanel wbWFontSetupPanel;
	private AudioPreferencesPanel recitationPanel;
	
	private static AnimationPreferences animationPreferences=
			new AnimationPreferences("animation.preferences",true);
	//all fonts have been initialized
	
	private static TranslationPreferences translationPreferences=
			new TranslationPreferences("translation.preferences");
	
	private static WordByWordFontPref wordByWordFontPref=
			new WordByWordFontPref("wbwFont.preferences");
	
	private static AudioPreferences audioPreferences=
			new AudioPreferences("audio.preferences");
	
	private static Preferences[] allPreferences=//storing references
		{
			animationPreferences,
			translationPreferences,
			wordByWordFontPref,
			audioPreferences
		};
	
	PreferencesSetupPanel[] allSetupPanels=new PreferencesSetupPanel[3];
	
	public PreferencesDialog() {
		
		
		setAllPrefsFromFile();
		
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PreferencesDialog.class.getResource("/QuranTeacher/images/icon64.png")));
		setTitle("Preferences");
		//setPreferredSize(new Dimension(500, 700));
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane);
			
			animationSetupPanel=
					new AnimationSetupPanel("Animation Preferences:",animationPreferences);

			tabbedPane.addTab("Animation",null, animationSetupPanel,null);
			
			translationSetupPanel = 
					new TranslationSetupPanel("Translation Preferences:",translationPreferences);
			
			tabbedPane.addTab("Translation", null, translationSetupPanel, null);
						
			wbWFontSetupPanel=
					new WbWFontSetupPanel("Word Translation Font Set Up",
							wordByWordFontPref,animationPreferences);
			tabbedPane.addTab("Word Translation",null,wbWFontSetupPanel,null);
			
			recitationPanel = new AudioPreferencesPanel(audioPreferences);
			tabbedPane.addTab("Recitation", null, recitationPanel, null);

		}
		
		allSetupPanels[0]=animationSetupPanel;
		allSetupPanels[1]=translationSetupPanel;
		allSetupPanels[2]=wbWFontSetupPanel;

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(saveListener!=null)
						{
							saveListener.preferencesSaved();
						}
						JOptionPane.showMessageDialog(getParent(), "Saved Successfully",
								"Saved", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
					}
				});
				
				JButton btnResetToDefault = new JButton("Reset To Default");
				btnResetToDefault.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int k=tabbedPane.getSelectedIndex();
						if(k!=-1)
						{
							if(k<3)
							{
								allPreferences[k].resetToDefault();
								allSetupPanels[k].updateSetUpPanel();
							}
							else
							{
								audioPreferences.resetToDefault();
								recitationPanel.updateSetupPanel();
							}
						}
						
					}
				});
				
				JButton btnResestAll = new JButton("Resest All");
				btnResestAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						resetAllPrefs();
						updateAllSetupPanels();
					}
				});
				btnResestAll.setFont(new Font("Tahoma", Font.PLAIN, 18));
				buttonPane.add(btnResestAll);
				btnResetToDefault.setFont(new Font("Tahoma", Font.PLAIN, 18));
				buttonPane.add(btnResetToDefault);
				saveButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setPreferencesSaveListener(PreferencesSaveListener listener)
	{
		saveListener=listener;
	}
	
	public void setAllPrefsFromFile()
	{
		File directory=new File(Preferences.storageFolder);
		if(directory.exists())
		{
			for(int i=0;i<allPreferences.length;i++)
			{
				if(!allPreferences[i].setPrefFromFile())
					allPreferences[i].resetToDefault();
			}
		}
		else
		{
			directory.mkdir();
			try {
				Files.setAttribute(directory.toPath(), "dos:hidden", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("new directory created :"+directory);
			resetAllPrefs();
		}
	}
	
	private void resetAllPrefs()
	{
		for(int i=0;i<allPreferences.length;i++)
			allPreferences[i].resetToDefault();
	}
	
	public void updateAllSetupPanels()
	{
		for(int i=0;i<allSetupPanels.length;i++)
		{
			allSetupPanels[i].updateSetUpPanel();
		}
		recitationPanel.updateSetupPanel();
	}
	
	public static AnimationPreferences getAnimPreferences()
	{
		return animationPreferences;
	}
	
	public static TranslationPreferences getTransPref()
	{
		return translationPreferences;
	}
	
	public static WordByWordFontPref getWbWFontPref()
	{
		return wordByWordFontPref;
	}
	
	public static AudioPreferences getAudioPref()
	{
		return audioPreferences;
	}
}
