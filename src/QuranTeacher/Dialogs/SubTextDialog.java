package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextPane;

import QuranTeacher.Preferences.SubtextPreferences;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Rahat
 * @since June 12, 2016
 */
public class SubTextDialog extends JDialog {

	private JTextPane textPane;
	private SubtextPreferences subtextPreferences;

	public SubTextDialog(int x, int y, int width, int height) {
		setAutoRequestFocus(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(x, y, width, height);
		getRootPane ().setOpaque (false);
		setBackground (new Color (0, 0, 0, 0));
		getContentPane ().setBackground (new Color (0, 0, 0, 0));
		getContentPane().setLayout(new BorderLayout());
		
		subtextPreferences=PreferencesDialog.getSubtextPreferences();
		textPane = new JTextPane();
		textPane.setFocusable(false);
		textPane.setOpaque(false);
		textPane.setEditable(false);
		//textPane.setBackground(Color.BLACK);
		updateFontFromPref();
		getContentPane().add(textPane, BorderLayout.CENTER);
		
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
	
	public void setText(String text){
		textPane.setText(text);
	}
	
	public void updateFontFromPref(){
		textPane.setFont(subtextPreferences.getFont());
		textPane.setForeground(subtextPreferences.getForeGroundColor());
	}
	
	public SubtextPreferences getSubtextPref(){
		return new SubtextPreferences("subtext.preferences",subtextPreferences.getForeGroundColor(),
				subtextPreferences.getFont());
	}
}
