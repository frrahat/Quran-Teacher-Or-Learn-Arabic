/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.PreferencesPanels;

import QuranTeacher.Preferences.AnimationPreferences;
import QuranTeacher.Preferences.WordByWordFontPref;

public class WbWFontSetupPanel extends PreferencesSetupPanel {

	/**
	 * To setUp the word by word translation (that appears under each word) fonts and color
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * @param animationPreferences 
	 */
	public WbWFontSetupPanel(String name,WordByWordFontPref preferences, AnimationPreferences animPref) 
	{
		super(name,preferences,animPref);
		
		lblBackgroundColor.setText("Transliteration Color :");
		lblBackgroundColor.setToolTipText("Set Transliteration Color");
		
		lblForegroundColor.setText("Word Meaning Color :");
		lblForegroundColor.setToolTipText("Set Meaning Color");
		
		//fontPreviewPanel.setVisible(false);
		previewText="\u0628\u0650\u0633\u0652\u0645\u0650";
		String[] texts={"{bis'mi}","In (the) name"};
		
		fontPreviewPanel.setText(previewText);
		fontPreviewPanel.setOtherTexts(texts);
		fontPreviewPanel.setTextAreaVisible(false);
	}

}
