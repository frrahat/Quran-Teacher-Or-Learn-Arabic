/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.SidePart;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JRadioButton;

import QuranTeacher.Basics.Ayah;
import QuranTeacher.Basics.SurahInformationContainer;
import QuranTeacher.Interfaces.AyahSelectionListener;
import QuranTeacher.Preferences.AnimationPreferences;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class SelectionPanel extends JPanel {

	/**
	 * Contains list of Sura names and their corresponding Ayah no.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	
	private DefaultComboBoxModel<String> suraNamesBox=new DefaultComboBoxModel<>();
	@SuppressWarnings("unchecked")
	private final ComboBoxModel<Integer>[] models=new ComboBoxModel[114];
	
	private AyahSelectionListener ayahSelectionListener;
	private static JComboBox<Integer> ayahBox;
	private static JComboBox<String> suraBox;
	
	private JRadioButton rdbtnSingleAyah;
	private JRadioButton rdbtnContinuous;
	private JButton btnNext;
	private JButton buttonPrev;
	private JButton btnGo;
	//private static JLabel lblStatus;
	
	public SelectionPanel() {
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 255, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSura = new JLabel("Sura :");
		lblSura.setForeground(Color.ORANGE);
		lblSura.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSura = new GridBagConstraints();
		gbc_lblSura.insets = new Insets(0, 0, 5, 5);
		gbc_lblSura.anchor = GridBagConstraints.EAST;
		gbc_lblSura.gridx = 0;
		gbc_lblSura.gridy = 0;
		add(lblSura, gbc_lblSura);
		
		//creating suraBox
		suraBox = new JComboBox<String>();
		suraBox.setBackground(Color.LIGHT_GRAY);
		suraBox.setForeground(Color.BLACK);
		suraBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		GridBagConstraints gbc_suraBox = new GridBagConstraints();
		gbc_suraBox.gridwidth = 5;
		gbc_suraBox.insets = new Insets(0, 0, 5, 0);
		gbc_suraBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_suraBox.gridx = 1;
		gbc_suraBox.gridy = 0;
		add(suraBox, gbc_suraBox);
		
		JLabel lblAya = new JLabel("Aya :");
		lblAya.setForeground(Color.ORANGE);
		lblAya.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAya = new GridBagConstraints();
		gbc_lblAya.anchor = GridBagConstraints.EAST;
		gbc_lblAya.insets = new Insets(0, 0, 5, 5);
		gbc_lblAya.gridx = 0;
		gbc_lblAya.gridy = 1;
		add(lblAya, gbc_lblAya);
		
		//creating ayahBox
		
		ayahBox = new JComboBox<Integer>();
		ayahBox.setForeground(Color.BLACK);
		ayahBox.setBackground(Color.LIGHT_GRAY);
		ayahBox.setEditable(true);
		ayahBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_ayaBox = new GridBagConstraints();
		gbc_ayaBox.gridwidth = 5;
		gbc_ayaBox.insets = new Insets(0, 0, 5, 0);
		gbc_ayaBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_ayaBox.gridx = 1;
		gbc_ayaBox.gridy = 1;
		add(ayahBox, gbc_ayaBox);
		
		buttonPrev = new JButton("<<Prev");
		buttonPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=suraBox.getSelectedIndex();
				int j=ayahBox.getSelectedIndex();
				
				Ayah ayah=new Ayah(i,j).getPrevAyah();
				if(ayah!=null){
					setSelectionIndex(ayah);
					ayahSelectionListener.ayahSelected(ayah);
				}
			}
		});
		
		buttonPrev.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.EAST;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 7;
		add(buttonPrev, gbc_button);
		
		btnNext = new JButton("Next>>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=suraBox.getSelectedIndex();
				int j=ayahBox.getSelectedIndex();
				
				Ayah ayah=new Ayah(i,j).getNextAyah();
				if(ayah!=null){
					setSelectionIndex(ayah);
					ayahSelectionListener.ayahSelected(ayah);
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.anchor = GridBagConstraints.EAST;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 2;
		gbc_btnNext.gridy = 7;
		add(btnNext, gbc_btnNext);
		
		btnGo = new JButton("Go");
		btnGo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo.anchor = GridBagConstraints.EAST;
		gbc_btnGo.gridx = 3;
		gbc_btnGo.gridy = 7;
		add(btnGo, gbc_btnGo);
		
		
		JLabel lblDisplay = new JLabel("Animation :");
		lblDisplay.setForeground(Color.ORANGE);
		GridBagConstraints gbc_lblDisplay = new GridBagConstraints();
		gbc_lblDisplay.insets = new Insets(0, 0, 0, 5);
		gbc_lblDisplay.gridx = 0;
		gbc_lblDisplay.gridy = 8;
		add(lblDisplay, gbc_lblDisplay);
		
		rdbtnSingleAyah = new JRadioButton("Single Ayah");
		
		rdbtnSingleAyah.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getStateChange()==ItemEvent.SELECTED)
					AnimationPreferences.continuous=false;
				else
					AnimationPreferences.continuous=true;
			}
		});
		rdbtnSingleAyah.setBackground(Color.DARK_GRAY);
		rdbtnSingleAyah.setForeground(Color.YELLOW);
		GridBagConstraints gbc_rdbtnSingleAyah = new GridBagConstraints();
		gbc_rdbtnSingleAyah.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnSingleAyah.gridx = 1;
		gbc_rdbtnSingleAyah.gridy = 8;
		add(rdbtnSingleAyah, gbc_rdbtnSingleAyah);
		
		rdbtnContinuous = new JRadioButton("Continuous");
		rdbtnContinuous.setForeground(Color.YELLOW);
		rdbtnContinuous.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_rdbtnContinuous = new GridBagConstraints();
		gbc_rdbtnContinuous.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnContinuous.gridx = 2;
		gbc_rdbtnContinuous.gridy = 8;
		add(rdbtnContinuous, gbc_rdbtnContinuous);
		
		
		setAyahAnimationType();//single ayah or continuous
		
		ButtonGroup buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnSingleAyah);
		buttonGroup.add(rdbtnContinuous);
		
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=suraBox.getSelectedIndex();
				int j=ayahBox.getSelectedIndex();
				
				Ayah ayah=new Ayah(i, j);
				if(ayahSelectionListener!=null)
					ayahSelectionListener.ayahSelected(ayah);
				else
					System.out.println("No listener available");
				
				//System.out.println("Going to "+suraNameList[i]+" Ayah "+(j+1));
			}
		});
		
		disableAllButtons();
		//suraBox action listening
		suraBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Sura box responded");
				int i=suraBox.getSelectedIndex();
				ayahBox.setModel(models[i]);
				Ayah ayah=new Ayah(i, -1);
				if(ayahSelectionListener!=null)
					ayahSelectionListener.ayahSelected(ayah);//going to sidePanel
				else
					System.out.println("No listener available");
			}
		});
		
		/*lblStatus = new JLabel();
		resetStatusLabel();
		lblStatus.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatus.anchor = GridBagConstraints.CENTER;
		gbc_lblStatus.gridwidth=0;
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 9;
		add(lblStatus, gbc_lblStatus);*/
	}



	public void setComboboxModels() {
		for (int i = 0; i < 114; i++) {
			suraNamesBox.addElement(Integer.toString(i + 1) + "."
					+ SurahInformationContainer.getSuraInfo(i).title);
			
			Integer[][] totalAyahList=new Integer[114][];
			int totalAyah=SurahInformationContainer.totalAyahs[i];
			totalAyahList[i] = new Integer[totalAyah];
			for (int j = 0; j < totalAyah; j++) {
				totalAyahList[i][j] = j + 1;
			}
			models[i] = new DefaultComboBoxModel<>(totalAyahList[i]);
		}
		
		//setting initial values to suraBox and ayahBox
		suraBox.setModel(suraNamesBox);
		suraBox.setSelectedIndex(0);
		ayahBox.setModel(models[0]);
		ayahBox.setSelectedIndex(0);
		
		enableAllButtons();
	}
	
	public void setSelectionListener(AyahSelectionListener listener)
	{
		this.ayahSelectionListener=listener;
	}
	
	public static void setSelectionIndex(Ayah ayah)
	{
		suraBox.setSelectedIndex(ayah.suraIndex);
		ayahBox.setSelectedIndex(ayah.ayahIndex);
	}
	
	public void setAyahAnimationType()
	{
		if(AnimationPreferences.continuous)
			rdbtnContinuous.setSelected(true);
		else
			rdbtnSingleAyah.setSelected(true);
	}
	
	private void disableAllButtons(){
		buttonPrev.setEnabled(false);
		btnNext.setEnabled(false);
		btnGo.setEnabled(false);
	}
	
	private void enableAllButtons(){
		buttonPrev.setEnabled(true);
		btnNext.setEnabled(true);
		btnGo.setEnabled(true);
	}



	public static Ayah getSelectedAyah() {
		return new Ayah(suraBox.getSelectedIndex(),ayahBox.getSelectedIndex());
	}
	
/*	public static void setStatusLabel(String statusText){
		lblStatus.setText(statusText);
	}



	public static void resetStatusLabel() {
		lblStatus.setText("---");
	}
	
	public static JLabel getStatusLabel(){
		return lblStatus;
	}*/
}
