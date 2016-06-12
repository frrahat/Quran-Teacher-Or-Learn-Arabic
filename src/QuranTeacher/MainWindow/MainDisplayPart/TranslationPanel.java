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
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Model.Ayah;
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
	private static JTextPane txtPaneTranslationtext;
	
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
		
		txtPaneTranslationtext = new JTextPane();
		txtPaneTranslationtext.setEditable(false);
		//txtrTranslationtext.setLineWrap(true);
		//txtrTranslationtext.setWrapStyleWord(true);
		
		StyledDocument doc = txtPaneTranslationtext.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		updateTransPref();
		
		txtPaneTranslationtext.setText("");
		scrollPane.setViewportView(txtPaneTranslationtext);

	}
	
	private void setDesign() {
		
		txtPaneTranslationtext.setBackground(bgColor);
		txtPaneTranslationtext.setForeground(fgColor);
		txtPaneTranslationtext.setFont(transFont);	
	}

	public static void setTranslationText(Ayah ayah)
	{
		String text;
		String detailedSurahName="["+ayah.toDetailedString()+"]";
		/*if((ayah.suraIndex | ayah.ayahIndex)==0){
			detailedSurahName="";
		}*/
		text=detailedSurahName+"\n"+
					AllTextsContainer.translationtexts.get(primaryTextIndex).getQuranText(ayah);
		
		if(secondaryTextIndex!=-1){
			text+="\n\n"+detailedSurahName+"\n"+
					AllTextsContainer.translationtexts.get(secondaryTextIndex).getQuranText(ayah);
		}
		
		txtPaneTranslationtext.setText(text);
		txtPaneTranslationtext.setCaretPosition(0);
		
	}
	
	public static void setText(String text){
		txtPaneTranslationtext.setText(text);
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
