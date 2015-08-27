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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class HelpDialog extends JDialog {

	/**
	 * Tips for the users
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public HelpDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HelpDialog.class.getResource("/QuranTeacher/images/icon64.png")));
		setTitle("Help");
		setModal(true);
		setBackground(Color.DARK_GRAY);
		setBounds(100, 100, 450, 436);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				JTextArea txtrHelpShortcut = new JTextArea();
				txtrHelpShortcut.setEditable(false);
				txtrHelpShortcut.setFont(new Font("rahat", Font.BOLD, 18));
				//System.out.println(txtrHelpShortcut.getFont().getFontName());
				txtrHelpShortcut.setForeground(Color.BLACK);
				txtrHelpShortcut.setBackground(Color.LIGHT_GRAY);
				txtrHelpShortcut.setLineWrap(true);
				txtrHelpShortcut.setWrapStyleWord(true);
				txtrHelpShortcut.setText("Help:\r\n=========\r\n\r\nShortcut Keys:\r\n\r\n"
						+ "\nSPACEBAR -- Go to next ayah"
			+"\nKey RIGHT-- Speed Down"
			+"\nKey LEFT -- Speed Up"
			+"\nKey UP   -- Scroll Up"
			+"\nKey DOWN -- Scroll Down"
			+"\nKey SHIFT or Mouse Click-- Pause/Resume Display"
			+ ""
			+ ""
			+ "\r\n\r\nAudios and Images"
			+ "\r\n--------------------------"
			+ "\n\nAudio files are downloaded automatically while the application is running and Audio is checked \'On\'"
			+ " and internet connection is available."
			+ "\nImage files also can be downloaded automatically before an ayah animation starts. To enable this go to "
			+ "Settings>>Preferences>>Animation Preferences and then Tick \'On\' Automatic Image Download."
			+ ""
			+ "\r\n\r\nThere is a better alternative way, that is, download all audios and images altogether."
			+ "\r\nTo do this go to Tools>>'Download all audio and image files' to start "
			+ "audio and image downloader."
			+ "\r\nAnother way to download all audios is : you can download the complete recitation of "
			+ "your favourite reciter from everyayah.com, unzip the zipped file, then copy/cut all the .mp3 files and "
			+ "paste to \\res\\QuranAudio folder."
			+ ""
			+ ""
			+ "\r\n\r\nAdditional Translation Text and Fonts"
			+ "\r\n------------------------------"
			+ "\r\nTranslation text of .txt format and Additional fonts of .ttf format can be added by just putting them in the corresponding folder of the \'res\' directory"
			+ ""
			+ ""
			+ "\r\n\r\nOther Settings:"
			+ "\n\nFonts, colors of texts can be changed in Settings>>Preferences."
			+ "\nWhile setting \'Animation preferences\' take extra care that "
			+ "the arabic Quran text appears properly in preview field."
			+ "\nSome fonts will show \'Rabbal\' instead of \'Rabbil\' in the "
			+ "preview field. Prescribed fonts are Tahoma, Zekr Quran, me Quran etc."
			+ "\nYou may try with other \'allowable\' fonts also."
			+ "\r\n\r\nFor controlling gapping in the display texts for different fonts some Advanced settings also have been included."
			+ ""
			+ "\r\n\r\nEditing And Playing Hit Files"
			+ "\r\n-------------------------------"
			+ "\r\nHit Files are nothing but a text file containing time of appearance of a word according to the recited audio. "
			+ "Hit Files can be created, edited or loaded and played. Hit File Editor can be started from \'Tools\' from the menubar. "
			+ "\r\nFor creating a new hit file at first you have to select (click on \'New\') the ayah you want to start from. Then you have to press <Enter> key on the keyboard "
			+ "to display the next word in time. The times will be recorded automatically. When you are done do not forget to save this file. "
			+ "You can solve some lagging/slowness problem while recording by downloading all the required audios and images."
			+ ""
			+ "\r\n\r\nFor playing a hit file at first you have to load the target file and press \'Play\' button."
			+ ""
			+ "\r\n\r\nHit times can be edited by double clicking on the time list or pressing <Enter> key after clicking on the \'Edit\' button."
			+ "\r\nAlso, Hit times in a file can be edited manually with a text editor. But do not change the specified pattern of word time representation. "
			+ "Else it will loose validity."
			+ ""
			+ "\n\n\nFor any specific problem or bug or help you can contact this email address:\r\n"
			+ "fr.rahat@gmail.com"
			+ "\r\nOr you may visit our facebook page:"
			+ "\r\nhttps://www.facebook.com/QuranTeacherOrLearnArabic");
				
				txtrHelpShortcut.setCaretPosition(0);
				scrollPane.setViewportView(txtrHelpShortcut);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ok");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("Cancel");
				buttonPane.add(okButton);
			}
		}
	}

}
