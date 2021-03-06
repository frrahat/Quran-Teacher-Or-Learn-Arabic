/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.SidePart;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import QuranTeacher.Interfaces.AudioButtonListener;
import QuranTeacher.Interfaces.AyahSelectionListener;
import QuranTeacher.Model.Ayah;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SidePanel extends JPanel {

	/**
	 * Panel that contains selectionPanel, informationPanel, audioNavigationPanel
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private AyahSelectionListener ayahSelectionListener;
	private final InformationPanel informationPanel;
	private AudioNavigationPanel audioNavPanel;
	private AudioButtonListener audioButtonListener;
	private SelectionPanel selectionPanel;
	
	public static final int SuraBoxChangeCode=-100;
	
	public SidePanel() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setPreferredSize(new Dimension(300, 300));
		
		//----my edition----
		//loadSuraInfo();//assumed uses litte time to load
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{296, 0};
		gridBagLayout.rowHeights = new int[] {98, 98, 98, 100};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		selectionPanel=new SelectionPanel();
		selectionPanel.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_selectionPanel = new GridBagConstraints();
		gbc_selectionPanel.fill = GridBagConstraints.BOTH;
		gbc_selectionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectionPanel.gridx = 0;
		gbc_selectionPanel.gridy = 0;
		add(selectionPanel, gbc_selectionPanel);
		
		selectionPanel.setSelectionListener(new AyahSelectionListener() {
			
			@Override
			public void ayahSelected(Ayah ayah) {
				//System.out.println("In sidepanel :"+ayah.suraIndex+" "+ayah.ayahIndex);
				if(ayah.ayahIndex==SuraBoxChangeCode)//sura combobox changed
					informationPanel.setInfo(ayah.suraIndex);
				else if(ayahSelectionListener!=null)//go button clicked
					ayahSelectionListener.ayahSelected(ayah);//going to main
					
			}
		});
		
		audioNavPanel = new AudioNavigationPanel();
		GridBagConstraints gbc_audioNavPanel = new GridBagConstraints();
		gbc_audioNavPanel.fill = GridBagConstraints.BOTH;
		gbc_audioNavPanel.insets = new Insets(0, 0, 5, 0);
		gbc_audioNavPanel.gridx = 0;
		gbc_audioNavPanel.gridy = 1;
		add(audioNavPanel, gbc_audioNavPanel);
		
		audioNavPanel.setAudioButtonListener(new AudioButtonListener() {
			
			@Override
			public void actionPerformed(String actionCommand) {
				audioButtonListener.actionPerformed(actionCommand);
			}
		});
		
		informationPanel=new InformationPanel();
		informationPanel.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_informationPanel = new GridBagConstraints();
		gbc_informationPanel.gridheight = 2;
		gbc_informationPanel.fill = GridBagConstraints.BOTH;
		gbc_informationPanel.gridx = 0;
		gbc_informationPanel.gridy = 2;
		add(informationPanel, gbc_informationPanel);
		
	}

	
	public void setSelectionListener(AyahSelectionListener listener) 
	{
		this.ayahSelectionListener=listener;
	}
	
	public void setAudioButtonListener(AudioButtonListener listener)
	{
		audioButtonListener=listener;
	}
	
	/*private void loadSuraInfo()
	{
		//go to the sura area
		SurahInformationContainer.loadAllSurahInfos();
	}*/
	
	public AudioNavigationPanel getAudioNavPanel()
	{
		return audioNavPanel;
	}


	public SelectionPanel getSelectionPanel() {
		return selectionPanel;
	}
	
	public InformationPanel getInformationPanel(){
		return informationPanel;
	}
}
