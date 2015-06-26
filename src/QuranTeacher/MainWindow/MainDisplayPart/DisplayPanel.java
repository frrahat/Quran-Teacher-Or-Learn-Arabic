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
import javax.swing.JTabbedPane;

import java.awt.Font;

import javax.swing.JSplitPane;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import java.awt.CardLayout;


public class DisplayPanel extends JPanel {
	
	/**
	 * The main display panel includes animationPanel and tafsir/translationPanel
	 */
	private static final long serialVersionUID = 1L;
	private AnimationPanel animationPanel;
	private TranslationPanel translationPanel;
	private TafsirPanel tafsirPanel;
	
	public static enum DisplayPage{StartUpLoadingPage,WelcomePage,AnimationPage};
	private DisplayPage displayPage;
	
	private String mainDisplayName="mainDisplay";
	private String welcomeDisplayName="welcomeDisplay";
	private String startUpDisplayName="startUpDisplayName";
	private StartUpLoaderPanel loaderPanel;
	
	public DisplayPanel() {
		setLayout(new CardLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(Color.DARK_GRAY);
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		animationPanel=new AnimationPanel();
		animationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitPane.setLeftComponent(animationPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, null, null, null));
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabbedPane.setForeground(Color.BLUE);
		splitPane.setRightComponent(tabbedPane);
		
		translationPanel=new TranslationPanel();
		tabbedPane.addTab("Translation", null, translationPanel, null);
		
		tafsirPanel=new TafsirPanel();
		tabbedPane.addTab("Tafsir", null, tafsirPanel, null);
		
		add(splitPane, mainDisplayName);
		
		
		loaderPanel=new StartUpLoaderPanel();
		add(loaderPanel,startUpDisplayName);
		
		//WelcomeDisplayPanel welcomePanel = new WelcomeDisplayPanel();
		//add(welcomePanel, welcomeDisplayName);
		
		
		displayPage=DisplayPage.StartUpLoadingPage;
		setDisplayPage(displayPage);
	}
	

	
	public AnimationPanel getAnimationPanel()
	{
		return animationPanel;
	}

	public TranslationPanel getTranslationPanel() {
		return translationPanel;
	}

	public TafsirPanel getTafsirPanel() {
		return tafsirPanel;
	}
	
	public DisplayPage getDisplayPage()
	{
		return displayPage;
	}
	
	public StartUpLoaderPanel getStartUpLoaderPanel(){
		return loaderPanel;
	}
	
	public void setDisplayPage(DisplayPage display)
	{
		CardLayout c=(CardLayout)getLayout();
		
		if(display==DisplayPage.StartUpLoadingPage)
			c.show(this, startUpDisplayName);
		else if(display==DisplayPage.AnimationPage)
			c.show(this, mainDisplayName);
		else if(display==DisplayPage.WelcomePage)
			c.show(this, welcomeDisplayName);
		
	}
}

