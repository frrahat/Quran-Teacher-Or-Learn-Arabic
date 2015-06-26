/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher;

import java.awt.EventQueue;

import javax.swing.JFrame;

import QuranTeacher.MainWindow.MainFrame;

public class QuranTeacherMain extends JFrame {
	/**
	 * Contains the main function
	 */
	private static final long serialVersionUID = 1L;

	public QuranTeacherMain() {
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//setDesign("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainFrame frame = new MainFrame();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
/*    private static void setDesign(String newLookAndFeel)
	{
		try
		{
			UIManager.setLookAndFeel(newLookAndFeel);
		}
		catch(Exception e)
		{
			System.out.println("Unable to load look and feel");
		}
	}*/

}
