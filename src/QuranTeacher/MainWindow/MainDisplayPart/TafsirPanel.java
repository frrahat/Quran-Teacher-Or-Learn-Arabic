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

import java.awt.Font;
import java.awt.Color;

public class TafsirPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private static JTextArea txtrTafsitText;
	
	
	public TafsirPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		txtrTafsitText = new JTextArea();
		txtrTafsitText.setEditable(false);
		txtrTafsitText.setWrapStyleWord(true);
		txtrTafsitText.setLineWrap(true);
		txtrTafsitText.setBackground(Color.BLACK);
		txtrTafsitText.setForeground(Color.WHITE);
		txtrTafsitText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtrTafsitText.setText("TafsirText");
		scrollPane.setViewportView(txtrTafsitText);

	}
	
	public static void setTafsirText(Ayah ayah)
	{
		txtrTafsitText.setText("Not available in this version");
	}

}
