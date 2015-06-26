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

import QuranTeacher.FilePaths;
import QuranTeacher.Basics.Ayah;
import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Preferences.TranslationPreferences;
import QuranTeacher.RenderTexts.QuranText;
import QuranTeacher.RenderTexts.TranslationTextsContainer;

import java.awt.Font;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

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
	
	private static ArrayList<QuranText> texts;
	
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
		
		txtrTranslationtext.setText("TransLationText");
		scrollPane.setViewportView(txtrTranslationtext);
		
		Runnable transLoader=new Runnable() {
			
			@Override
			public void run() {
				try
				{
					//Thread.sleep(3000);
					texts=new ArrayList<>();
					
					for(int i=0;i<TranslationTextsContainer.getSize();i++){
						texts.add(new QuranText(TranslationTextsContainer.getTransFile(i).getInputStream(), false));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}				
			}
		};
		
		Thread t=new Thread(transLoader);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private void setDesign() {
		
		txtrTranslationtext.setBackground(bgColor);
		txtrTranslationtext.setForeground(fgColor);
		txtrTranslationtext.setFont(transFont);	
	}

	public static void setTranslationText(Ayah ayah)
	{
		String text;
		text=ayah.toDetailedString()+"\n"+
					texts.get(primaryTextIndex).getQuranText(ayah);
		
		if(secondaryTextIndex!=-1){
			text+="\n\n"+ayah.toDetailedString()+"\n"+
					texts.get(secondaryTextIndex).getQuranText(ayah);
		}
		
		txtrTranslationtext.setText(text);
		
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
