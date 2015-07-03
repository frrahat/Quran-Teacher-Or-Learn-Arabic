/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.MainDisplayPart;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import QuranTeacher.Basics.Ayah;
import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Preferences.TranslationPreferences;
import QuranTeacher.RenderTexts.AllTextsContainer;

import java.awt.Font;
import java.awt.Color;

public class TranslationPanel extends JPanel {

	/**
	 * Panel that shows translation of ayah
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private static JTextArea txtrTranslationtext;
	
	private TranslationPreferences preferences;
	
	private Color bgColor;
	private Color fgColor;
	private Font transFont;
	private static int primaryTextIndex;
	private static int secondaryTextIndex;
	
	
	public TranslationPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		preferences=PreferencesDialog.getTransPref();
		
		txtrTranslationtext = new JTextArea();
		txtrTranslationtext.setEditable(false);
		txtrTranslationtext.setLineWrap(true);
		txtrTranslationtext.setWrapStyleWord(true);
		
		updateTransPref();
		
		txtrTranslationtext.setText("");
		scrollPane.setViewportView(txtrTranslationtext);

	}
	
	private void setDesign() {
		
		txtrTranslationtext.setBackground(bgColor);
		txtrTranslationtext.setForeground(fgColor);
		txtrTranslationtext.setFont(transFont);	
	}

	public static void setTranslationText(Ayah ayah)
	{
		String text;
		text="["+ayah.toDetailedString()+"]\n\t"+
					AllTextsContainer.translationtexts.get(primaryTextIndex).getQuranText(ayah);
		
		if(secondaryTextIndex!=-1){
			text+="\n\n["+ayah.toDetailedString()+"]\n\t"+
					AllTextsContainer.translationtexts.get(secondaryTextIndex).getQuranText(ayah);
		}
		
		txtrTranslationtext.setText(text);
		txtrTranslationtext.setCaretPosition(0);
		
	}
	
	public void updateTransPref()
	{
		//System.out.println("translation preferences overloaded");
		//preferences=PreferencesDialog.getTransPref();
		bgColor=preferences.getBackGroundColor();
		fgColor=preferences.getForeGroundColor();
		transFont=preferences.getFont();
		primaryTextIndex=preferences.getPrimaryTextIndex();
		secondaryTextIndex=preferences.getSecondaryTextIndex();
		
		setDesign();
	}
	
	public TranslationPreferences getTranslationPref()
	{
		return new TranslationPreferences(
				"translation.preferences",bgColor,fgColor,transFont,
				primaryTextIndex,secondaryTextIndex);
	}
}
