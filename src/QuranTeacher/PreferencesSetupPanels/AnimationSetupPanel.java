/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.PreferencesSetupPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import QuranTeacher.Preferences.AnimationPreferences;

public class AnimationSetupPanel extends PreferencesSetupPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*String text="\n  \u0628\u0650\u0633\u0652\u0645\u0650 "
			+ "\u0627\u0644\u0644\u0651\u064e\u0647\u0650 \u0627\u0644"
			+ "\u0631\u0651\u064e\u062d\u0652\u0645\u064e\u0670\u0646"
			+ "\u0650 \u0627\u0644\u0631\u0651\u064e\u062d\u0650\u064a\u0645\u0650 \n";*/
	
	public AnimationSetupPanel(String name, final AnimationPreferences preferences) {
		super(name, preferences);
		
		//[1:2]
		previewText="\n\u0627\u0644\u0652\u062d\u064e\u0645\u0652\u062f\u064f "
				+ "\u0644\u0650\u0644\u0651\u064e\u0647\u0650 "
				+ "\u0631\u064e\u0628\u0651\u0650 "
				+ "\u0627\u0644\u0652\u0639\u064e\u0627\u0644\u064e\u0645\u0650\u064a\u0646\u064e\n";
		
		fontPreviewPanel.setText(previewText);
		fontPreviewPanel.setTextAreaVisible(false);
		
		JLabel lblRestingTime = new JLabel("Resting Time :");
		lblRestingTime.setToolTipText("Time to wait before recitation of another "
				+ "ayah starts while in continuous animation.");
		lblRestingTime.setForeground(Color.ORANGE);
		lblRestingTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblRestingTime = new GridBagConstraints();
		gbc_lblRestingTime.anchor = GridBagConstraints.WEST;
		gbc_lblRestingTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblRestingTime.gridx = 0;
		gbc_lblRestingTime.gridy = 7;
		add(lblRestingTime, gbc_lblRestingTime);
		
		final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 10);
		slider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				preferences.setRestingTime(slider.getValue());
			}
		});
		slider.setBackground(Color.DARK_GRAY);
		slider.setValue(0);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.gridwidth = 0;
		gbc_slider.fill = GridBagConstraints.BOTH;
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 7;
		add(slider, gbc_slider);
		
		JLabel lblshowInfoBox = new JLabel("Info Box :");
		lblshowInfoBox.setToolTipText("Show additional word information pop up box "
				+ "after a word is displayed.");
		lblshowInfoBox.setForeground(Color.ORANGE);
		lblshowInfoBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblshowInfoBox = new GridBagConstraints();
		gbc_lblshowInfoBox.anchor = GridBagConstraints.WEST;
		gbc_lblshowInfoBox.insets = new Insets(0, 0, 5, 5);
		gbc_lblshowInfoBox.gridx = 0;
		gbc_lblshowInfoBox.gridy = 8;
		add(lblshowInfoBox, gbc_lblshowInfoBox);
		
		JCheckBox chckbxShowInfoBox = new JCheckBox("Show Pop Up Info Box");
		chckbxShowInfoBox.setBackground(Color.DARK_GRAY);
		chckbxShowInfoBox.setForeground(Color.GREEN);
		chckbxShowInfoBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxShowInfoBox.setSelected(preferences.isShowPopUpBox());
		chckbxShowInfoBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					preferences.setShowPopUpBox(true);
				}
				else
				{
					preferences.setShowPopUpBox(false);
				}
			}
		});
		
		GridBagConstraints gbc_chckbxShowInfoBox = new GridBagConstraints();
		gbc_chckbxShowInfoBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxShowInfoBox.gridwidth = 3;
		gbc_chckbxShowInfoBox.weightx = 0.5;
		gbc_chckbxShowInfoBox.gridx = 1;
		gbc_chckbxShowInfoBox.gridy = 8;
		add(chckbxShowInfoBox, gbc_chckbxShowInfoBox);
		
		//download image control
		JLabel lblImageDownloading = new JLabel("Image Download :");
		lblImageDownloading.setToolTipText("If ticked on, word images will be downloaded "
				+ "automatically before animating an ayah.");
		lblImageDownloading.setForeground(Color.ORANGE);
		lblImageDownloading.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblImageDownloading = new GridBagConstraints();
		gbc_lblImageDownloading.anchor = GridBagConstraints.WEST;
		gbc_lblImageDownloading.insets = new Insets(0, 0, 5, 5);
		gbc_lblImageDownloading.gridx = 0;
		gbc_lblImageDownloading.gridy = 9;
		add(lblImageDownloading, gbc_lblImageDownloading);
		
		JCheckBox chckbxEnableDownloadImages = new JCheckBox("Download Images Automatically");
		chckbxEnableDownloadImages.setBackground(Color.DARK_GRAY);
		chckbxEnableDownloadImages.setForeground(Color.GREEN);
		chckbxEnableDownloadImages.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxEnableDownloadImages.setSelected(preferences.isDownloadImageEnabled());
		chckbxEnableDownloadImages.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					preferences.setDownloadImageEnabled(true);
				}
				else
				{
					preferences.setDownloadImageEnabled(false);
				}
			}
		});
		
		GridBagConstraints gbc_chckbxEnableDownloadImages = new GridBagConstraints();
		gbc_chckbxEnableDownloadImages.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxEnableDownloadImages.gridwidth = 3;
		gbc_chckbxEnableDownloadImages.weightx = 0.5;
		gbc_chckbxEnableDownloadImages.gridx = 1;
		gbc_chckbxEnableDownloadImages.gridy = 9;
		add(chckbxEnableDownloadImages, gbc_chckbxEnableDownloadImages);
		
		//auto scroll control
		JLabel lblAutoScroll = new JLabel("Scrolling Control :");
		lblAutoScroll.setToolTipText("Controlling Automatic Scrolling while animation is running.");
		lblAutoScroll.setForeground(Color.ORANGE);
		lblAutoScroll.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAutoScroll = new GridBagConstraints();
		gbc_lblAutoScroll.anchor = GridBagConstraints.WEST;
		gbc_lblAutoScroll.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutoScroll.gridx = 0;
		gbc_lblAutoScroll.gridy = 10;
		add(lblAutoScroll, gbc_lblAutoScroll);
		
		
		JCheckBox chckbxAutoScroll = new JCheckBox("Enable Auto Scroll");
		chckbxAutoScroll.setBackground(Color.DARK_GRAY);
		chckbxAutoScroll.setForeground(Color.GREEN);
		chckbxAutoScroll.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxAutoScroll.setSelected(preferences.isAutoScrollEnabled());
		chckbxAutoScroll.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					preferences.setAutoScrollEnabled(true);
				}
				else
				{
					preferences.setAutoScrollEnabled(false);
				}
			}
		});
		
		GridBagConstraints gbc_chckbxAutoScroll = new GridBagConstraints();
		gbc_chckbxAutoScroll.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAutoScroll.gridwidth = 3;
		gbc_chckbxAutoScroll.weightx = 0.5;
		gbc_chckbxAutoScroll.gridx = 1;
		gbc_chckbxAutoScroll.gridy = 10;
		add(chckbxAutoScroll, gbc_chckbxAutoScroll);
	}
	

}
