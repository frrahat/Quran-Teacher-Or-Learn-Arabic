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
			+"\nMouse Right Click-- Pause/Resume Display"
			+ "\n\n\nAudio files are downloaded automatically while the application is running"
			+ " and internet connection is available."
			+ "\nBut image files aren't. They should be downloaded manually."
			+ "\nYou can go to Tools>>'Download all audio and image files' to start "
			+ "audio and image downloader."
			+ "\n\nFonts, colors of texts can be changed in Settings>>Preferences."
			+ "\nWhile setting \'Animation preferences\' take extra care that "
			+ "the arabic Quran text appears properly in preview field."
			+ "\nSome fonts will show \'Rabbal\' instead of \'Rabbil\' in the "
			+ "preview field. Prescribed fonts are Tahoma, Zekr Quran, me Quran etc."
			+ "\nYou may try with other \'allowable\' fonts also."
			+ "\n\n\nFor any specific problem or bug please contact this email id:"
			+ "\nfr.rahat@gmail.com");
				
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
