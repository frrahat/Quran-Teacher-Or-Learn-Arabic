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
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import QuranTeacher.Preferences.MyColorsContainer;
import QuranTeacher.Preferences.MyFontsContainer;
import QuranTeacher.Preferences.Preferences;


public class PreferencesSetupPanel extends JPanel {

	/**
	 * General setup panel for all kinds of preferences except audioPreferences
	 */
	private static final long serialVersionUID = 1L;
	protected String previewText;
	protected JLabel lblBackgroundColor;
	protected JLabel lblForegroundColor;
	protected FontPreviewPanel fontPreviewPanel;
	
	private static Color[] colors=MyColorsContainer.getColors();
	private static String[] colorNames=MyColorsContainer.getColorNames();
	private static int[] fontStyles=MyFontsContainer.getFontStyles();
	private static String[] fontStyleNames=MyFontsContainer.getFontStyleNames();
	
	private String panelText;
	private JComboBox<String> FStyleComboBox;
	private JComboBox<String> FcomboBox;
	private JComboBox<String> FCcomboBox;
	private JComboBox<String> BCcomboBox;
	private JComboBox<Integer> FSizeComboBox;
	private Preferences panelPreferences;
	
	
	//preferences and panelPreferences are to shared object
	//if preferences' attributes is changed, panelPreferences' also
	//panelPref's attribute change doesn't effect that of prefs
	public PreferencesSetupPanel(String name, Preferences preferences) {
		
		this.panelText=name;
		this.panelPreferences=preferences;
		
		fontPreviewPanel = new FontPreviewPanel(panelPreferences);
		
		setForeground(Color.ORANGE);
		setBackground(Color.DARK_GRAY);
		//tabbedPane.addTab("Display", displaySetupPanel);
		GridBagLayout gbl_displaySetupPanel = new GridBagLayout();
		gbl_displaySetupPanel.columnWidths = new int[]{0, 0, 0};
		gbl_displaySetupPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_displaySetupPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_displaySetupPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_displaySetupPanel);
		{
			JLabel lblPanelname = new JLabel(panelText);
			lblPanelname.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblPanelname.setForeground(Color.MAGENTA);
			GridBagConstraints gbc_lblPanelname = new GridBagConstraints();
			gbc_lblPanelname.gridwidth = 2;
			gbc_lblPanelname.insets = new Insets(0, 0, 5, 0);
			gbc_lblPanelname.gridx = 0;
			gbc_lblPanelname.gridy = 0;
			add(lblPanelname, gbc_lblPanelname);
		}
		{
			lblBackgroundColor = new JLabel("Background Color");
			lblBackgroundColor.setToolTipText("Set Background Color");
			lblBackgroundColor.setForeground(Color.ORANGE);
			lblBackgroundColor.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblBackgroundColor = new GridBagConstraints();
			gbc_lblBackgroundColor.anchor = GridBagConstraints.WEST;
			gbc_lblBackgroundColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblBackgroundColor.gridx = 0;
			gbc_lblBackgroundColor.gridy = 1;
			add(lblBackgroundColor, gbc_lblBackgroundColor);
		}

		{
			BCcomboBox = new JComboBox<String> (colorNames);
			BCcomboBox.setBackground(Color.LIGHT_GRAY);
			BCcomboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panelPreferences.setBackGroundColor(colors[BCcomboBox.getSelectedIndex()]);
					fontPreviewPanel.updateFontPreview();
				}
			});
			BCcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_BCcomboBox = new GridBagConstraints();
			gbc_BCcomboBox.insets = new Insets(0, 0, 5, 0);
			gbc_BCcomboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_BCcomboBox.gridx = 1;
			gbc_BCcomboBox.gridy = 1;
			add(BCcomboBox, gbc_BCcomboBox);
		}
		{
			lblForegroundColor = new JLabel("Foreground Color");
			lblForegroundColor.setToolTipText("Set Foreground Color");
			lblForegroundColor.setForeground(Color.ORANGE);
			lblForegroundColor.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblForegroundColor = new GridBagConstraints();
			gbc_lblForegroundColor.anchor = GridBagConstraints.WEST;
			gbc_lblForegroundColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblForegroundColor.gridx = 0;
			gbc_lblForegroundColor.gridy = 2;
			add(lblForegroundColor, gbc_lblForegroundColor);
		}
		{
			//Fore Ground Color
			FCcomboBox = new JComboBox<String>(colorNames);
			FCcomboBox.setBackground(Color.LIGHT_GRAY);
			FCcomboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panelPreferences.setForeGroundColor(colors[FCcomboBox.getSelectedIndex()]);
					fontPreviewPanel.updateFontPreview();			}
			});
			FCcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_FCcomboBox = new GridBagConstraints();
			gbc_FCcomboBox.insets = new Insets(0, 0, 5, 0);
			gbc_FCcomboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_FCcomboBox.gridx = 1;
			gbc_FCcomboBox.gridy = 2;
			add(FCcomboBox, gbc_FCcomboBox);
		}
		{
			JLabel lblFont = new JLabel("Font");
			lblFont.setToolTipText("Set Your Favourite Font");
			lblFont.setForeground(Color.ORANGE);
			lblFont.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblFont = new GridBagConstraints();
			gbc_lblFont.anchor = GridBagConstraints.WEST;
			gbc_lblFont.insets = new Insets(0, 0, 5, 5);
			gbc_lblFont.gridx = 0;
			gbc_lblFont.gridy = 3;
			add(lblFont, gbc_lblFont);
		}
		{//Font Names
			FcomboBox = new JComboBox<String>();
			
			setFontComboboxItems();
			
			FcomboBox.setBackground(Color.LIGHT_GRAY);
			FcomboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Font prev=panelPreferences.getFont();
					Font f=MyFontsContainer.getMyFont(FcomboBox.getSelectedIndex());
					f=f.deriveFont(prev.getStyle(), prev.getSize());
					panelPreferences.setFont(f);
					fontPreviewPanel.updateFontPreview();
				}
			});
			FcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_FcomboBox = new GridBagConstraints();
			gbc_FcomboBox.insets = new Insets(0, 0, 5, 0);
			gbc_FcomboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_FcomboBox.gridx = 1;
			gbc_FcomboBox.gridy = 3;
			add(FcomboBox, gbc_FcomboBox);
		}
		{
			JLabel lblFontStyle = new JLabel("Font Style");
			lblFontStyle.setToolTipText("Set Which Font Style You Like");
			lblFontStyle.setForeground(Color.ORANGE);
			lblFontStyle.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblFontStyle = new GridBagConstraints();
			gbc_lblFontStyle.anchor = GridBagConstraints.WEST;
			gbc_lblFontStyle.insets = new Insets(0, 0, 5, 5);
			gbc_lblFontStyle.gridx = 0;
			gbc_lblFontStyle.gridy = 4;
			add(lblFontStyle, gbc_lblFontStyle);
		}
		{
			FStyleComboBox = new JComboBox<String>(fontStyleNames);
			FStyleComboBox.setBackground(Color.LIGHT_GRAY);
			FStyleComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Font f=panelPreferences.getFont();
					f=f.deriveFont(fontStyles[FStyleComboBox.getSelectedIndex()]);
					panelPreferences.setFont(f);
					fontPreviewPanel.updateFontPreview();			}
			});
			FStyleComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_FStyleComboBox = new GridBagConstraints();
			gbc_FStyleComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_FStyleComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_FStyleComboBox.gridx = 1;
			gbc_FStyleComboBox.gridy = 4;
			add(FStyleComboBox, gbc_FStyleComboBox);
		}
		{
			JLabel lblFontSize = new JLabel("Font Size :");
			lblFontSize.setToolTipText("Set The Font at Your Comfortable Size");
			lblFontSize.setForeground(Color.ORANGE);
			lblFontSize.setHorizontalAlignment(SwingConstants.LEFT);
			lblFontSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblFontSize = new GridBagConstraints();
			gbc_lblFontSize.anchor = GridBagConstraints.WEST;
			gbc_lblFontSize.insets = new Insets(0, 0, 5, 5);
			gbc_lblFontSize.gridx = 0;
			gbc_lblFontSize.gridy = 5;
			add(lblFontSize, gbc_lblFontSize);
		}
		{
			FSizeComboBox = new JComboBox<Integer>();
			FSizeComboBox.setBackground(Color.LIGHT_GRAY);
			FSizeComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Font f=panelPreferences.getFont();
					f=f.deriveFont((float) (FSizeComboBox.getSelectedIndex()+5));
					panelPreferences.setFont(f);
					fontPreviewPanel.updateFontPreview();				
					}
			});
			FSizeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_FSizeComboBox = new GridBagConstraints();
			gbc_FSizeComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_FSizeComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_FSizeComboBox.gridx = 1;
			gbc_FSizeComboBox.gridy = 5;
			
			DefaultComboBoxModel<Integer>model=new DefaultComboBoxModel<>();
			for(int i=5;i<=99;i++)
				model.addElement(i);
			FSizeComboBox.setModel(model);
			add(FSizeComboBox, gbc_FSizeComboBox);
		}
		{
			//fronPreviewPanel
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 2;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 6;
			add(fontPreviewPanel, gbc_panel);
		}
		
		updateSetUpPanel();
	}

	public void setFontComboboxItems() {
		DefaultComboBoxModel<String> model=new DefaultComboBoxModel<>();
		ArrayList<Font> fontList=MyFontsContainer.getMyFontList();
		for(int i=0;i<fontList.size();i++){
			model.addElement(fontList.get(i).getName());
		}
		FcomboBox.setModel(model);
	}
	
	public PreferencesSetupPanel(String name, Preferences preferences,
			Preferences pref2) {
		this(name,preferences);
		fontPreviewPanel.setSecondPref(pref2);
	}

	public void updateSetUpPanel()
	{
		BCcomboBox.setSelectedItem
		(MyColorsContainer.getColorName(panelPreferences.getBackGroundColor()));
		FCcomboBox.setSelectedItem
		(MyColorsContainer.getColorName(panelPreferences.getForeGroundColor()));
		FcomboBox.setSelectedIndex
		(MyFontsContainer.getFontIndex(panelPreferences.getFont().getName()));
		FStyleComboBox.setSelectedIndex(
				MyFontsContainer.getFontStyleIndex((panelPreferences.getFont().getStyle())));
		FSizeComboBox.setSelectedItem(panelPreferences.getFont().getSize());

		fontPreviewPanel.updateFontPreview();
	}
}

