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

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;

import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import QuranTeacher.MainWindow.MainFrame;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;

public class AboutDialog extends JDialog {

	/**
	 * About the application
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public AboutDialog() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/QuranTeacher/images/info48.png")));
		setTitle("About");
		setBounds(100, 100, 450, 475);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(AboutDialog.class.getResource("/QuranTeacher/images/icon64.png")));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblQuranTeacherOr = new JLabel("Quran Teacher Or Learn Arabic "+MainFrame.version);
			lblQuranTeacherOr.setForeground(Color.ORANGE);
			GridBagConstraints gbc_lblQuranTeacherOr = new GridBagConstraints();
			gbc_lblQuranTeacherOr.insets = new Insets(0, 0, 5, 0);
			gbc_lblQuranTeacherOr.gridx = 0;
			gbc_lblQuranTeacherOr.gridy = 1;
			contentPanel.add(lblQuranTeacherOr, gbc_lblQuranTeacherOr);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JTextArea txtrAboutText = new JTextArea();
				txtrAboutText.setLineWrap(true);
				txtrAboutText.setWrapStyleWord(true);
				txtrAboutText.setForeground(Color.BLACK);
				txtrAboutText.setBackground(Color.LIGHT_GRAY);
				txtrAboutText.setEditable(false);
				txtrAboutText.setFont(new Font("Tahoma", Font.PLAIN, 18));
				txtrAboutText.setText("Quran Teacher or Learn Arabic "+MainFrame.version
						+ "\r\n=======================\r\n"
						+ "was built as a term project\r\n"
						+ "by Abdullah Al Zishan and "
						+ "Fazle Rabbi Rahat students of Department of Computer Science & Engineering"
						+ " (CSE),"
						+ "Bangladesh University "
						+ "of\r\nEngineering And Technology (BUET)."
						+ "\nAnd this project was accomplished under"
						+ " the supervision of Md. Iftekharul Islam Sakib,"
						+" Lecturer,"
						+" Department of Computer Science & Engineering"
						+ " (CSE),"
						+" Bangladesh University of Engineering & Technology (BUET),"
						+" Dhaka, Bangladesh."
						+ "\r\n\r\nThis was intended to be used for "
						+ "learning the translations of 'Quranic words' "
						+ "And thus understanding the Quran and at the "
						+ "same time accelerating learning arabic.\r\n\r\n"
						+ "All word by word transliteration, meaning, images "
						+ "and grammatical descriptions were collected "
						+ "from \"http://corpus.quran.com/wordbyword.jsp\" which "
						+ "is \"an annotated linguistic resource which shows the "
						+ "Arabic grammar, syntax and morphology for each word "
						+ "in the Holy Quran.\"\r\n\r\nThe Quran text is "
						+ "Uthmani Text and has been collceted from "
						+ "tanzil.net which follows The Medina Mushaf "
						+ "(officially: Mushaf al-Madinah an-Nabawiyyah,"
						+ " Arabic: مصحف المدينة النبوية) is an authentic copy of"
						+ " the holy quran printed by King Fahad Complex "
						+ "for Printing of the Holy Quran).\r\n\r\nEnglish"
						+ " translation text is from Yusuf Ali translation."
						+ "\r\nBengali translation text is from Muhiuddin "
						+ "Khan (most probably).\r\n\r\nThe audio recitations "
						+ "are from everyayah.com."
						+ "\n\nMeaning of the title of sura, themes "
						+ "were collected from wikipedia.org and then"
						+ " they were revised (Some Bibilic words were modified ie."
						+ " Abraham has been changed to Ibraheem (PBUH) etc.)"
						+ " before they were implemented."
						+ "\r\n\r\nFor the "
						+ "developers:\r\n================\r\nProgramming "
						+ "language was : Java.\r\n\r\nEach word taken from "
						+ "the Quran Text file was matched by index with the "
						+ "information parts taken from corpus.quran.com. "
						+ "There were no OCR (Optical Charecter Recognition) "
						+ "or high level algorithms used. There has been found "
						+ "mismatch only at sura no. 37, ayah no. 130 "
						+ "(with programming approach). Here إِلْ يَاسِينَ are two "
						+ "words separated by a space in Quran Text, But "
						+ "regarded as a single word in corpus.quran.com "
						+ "website. As, changing one of them will be regarded "
						+ "as breaking license agreement, this problem has been left "
						+ "unsolved. And it will be revised again, only when "
						+ "any one of the source makes an update to the "
						+ "words.\r\n\r\nThere is posibility that other "
						+ "problems of this type may arise, but user will "
						+ "be able to mark those as a problem for sure as "
						+ "there is transliteration given below of each "
						+ "word. \r\n\r\nThe source code of this program "
						+ "will soon be available insha Allah.\r\n");
				txtrAboutText.setCaretPosition(0);
				scrollPane.setViewportView(txtrAboutText);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close\r\n");
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

}
