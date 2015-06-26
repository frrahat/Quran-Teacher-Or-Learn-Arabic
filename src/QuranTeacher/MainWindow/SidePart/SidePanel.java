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

import QuranTeacher.Basics.Ayah;
import QuranTeacher.Basics.SurahInformationContainer;
import QuranTeacher.Interfaces.AudioButtonListener;
import QuranTeacher.Interfaces.SelectionListener;

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
	private SelectionListener selectionListener;
	private final InformationPanel informationPanel;
	private AudioNavigationPanel audioNavPanel;
	private AudioButtonListener audioButtonListener;
	
	public SidePanel() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setPreferredSize(new Dimension(300, 300));
		
		//----my edition----
		loadSuraInfo();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{296, 0};
		gridBagLayout.rowHeights = new int[] {98, 98, 98, 100};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		SelectionPanel selectionPanel=new SelectionPanel();
		selectionPanel.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_selectionPanel = new GridBagConstraints();
		gbc_selectionPanel.fill = GridBagConstraints.BOTH;
		gbc_selectionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectionPanel.gridx = 0;
		gbc_selectionPanel.gridy = 0;
		add(selectionPanel, gbc_selectionPanel);
		
		selectionPanel.setSelectionListener(new SelectionListener() {
			
			@Override
			public void ayahSelected(Ayah ayah) {
				//System.out.println("In sidepanel :"+ayah.suraIndex+" "+ayah.ayahIndex);
				if(ayah.ayahIndex==-1)//sura combobox changed
					informationPanel.setInfo(ayah.suraIndex);
				else if(selectionListener!=null)//go button clicked
					selectionListener.ayahSelected(ayah);//going to main
					
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

	
	public void setSelectionListener(SelectionListener listener) 
	{
		this.selectionListener=listener;
	}
	
	public void setAudioButtonListener(AudioButtonListener listener)
	{
		audioButtonListener=listener;
	}
	
	private void loadSuraInfo()
	{
		//go to the sura area
		SurahInformationContainer.loadAllSurahInfos();
	}
	
	public AudioNavigationPanel getAudioNavPanel()
	{
		return audioNavPanel;
	}
}
