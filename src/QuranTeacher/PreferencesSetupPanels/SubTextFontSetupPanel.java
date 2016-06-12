package QuranTeacher.PreferencesSetupPanels;

import QuranTeacher.Preferences.Preferences;
import QuranTeacher.Preferences.SubtextPreferences;

/**
 * @author Rahat
 * @since June 13, 2016
 */
public class SubTextFontSetupPanel extends PreferencesSetupPanel{


	public SubTextFontSetupPanel(String name, SubtextPreferences subtextPreferences) {
		super(name, subtextPreferences);		
		lblBackgroundColor.setText("Background Color :");
		lblBackgroundColor.setToolTipText("Set Background Color");
		
		lblForegroundColor.setText("Text Color :");
		lblForegroundColor.setToolTipText("Set Text Color");
		
		//fontPreviewPanel.setVisible(false);
		previewText="Subtext is a hit file functionality."
				+ " The preferences are not saved until an instance of subtext dialog is run.";
		
		fontPreviewPanel.setText(previewText);
		fontPreviewPanel.updateFontPreview();
	}

}
