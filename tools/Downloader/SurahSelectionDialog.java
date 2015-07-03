package Downloader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Rahat
 *	03-Jul-2015
 */
public class SurahSelectionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	Integer numbers[];

	private JComboBox<Integer> endSurahComboBox;

	private JComboBox<Integer> startSurahComboBox;
	/**
	 * Create the dialog.
	 */
	public SurahSelectionDialog() {
		setModal(true);
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
		numbers=new Integer[114];
		for(int i=1;i<=114;i++)numbers[i-1]=i;
		
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel labelStartSurah = new JLabel("From Surah :");
			labelStartSurah.setForeground(Color.ORANGE);
			labelStartSurah.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_labelStartSurah = new GridBagConstraints();
			gbc_labelStartSurah.insets = new Insets(0, 0, 5, 5);
			gbc_labelStartSurah.anchor = GridBagConstraints.EAST;
			gbc_labelStartSurah.gridx = 0;
			gbc_labelStartSurah.gridy = 0;
			contentPanel.add(labelStartSurah, gbc_labelStartSurah);
		}
		{
			startSurahComboBox = new JComboBox<Integer>();

			startSurahComboBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
						endSurahComboBox.setSelectedIndex(
								Math.max(startSurahComboBox.getSelectedIndex(), 
										endSurahComboBox.getSelectedIndex()));
						
						}
				}
			});
			startSurahComboBox.setModel(new DefaultComboBoxModel<Integer>(numbers));
			startSurahComboBox.setSelectedIndex(0);
			
			startSurahComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_startSurahComboBox = new GridBagConstraints();
			gbc_startSurahComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_startSurahComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_startSurahComboBox.gridx = 1;
			gbc_startSurahComboBox.gridy = 0;
			contentPanel.add(startSurahComboBox, gbc_startSurahComboBox);
		}
		{
			JLabel lblToSurah = new JLabel("To Surah :");
			lblToSurah.setForeground(Color.ORANGE);
			lblToSurah.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblToSurah = new GridBagConstraints();
			gbc_lblToSurah.anchor = GridBagConstraints.EAST;
			gbc_lblToSurah.insets = new Insets(0, 0, 0, 5);
			gbc_lblToSurah.gridx = 0;
			gbc_lblToSurah.gridy = 1;
			contentPanel.add(lblToSurah, gbc_lblToSurah);
		}
		{
			endSurahComboBox = new JComboBox<Integer>();
			
			endSurahComboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
					endSurahComboBox.setSelectedIndex(
							Math.max(startSurahComboBox.getSelectedIndex(), 
									endSurahComboBox.getSelectedIndex()));
					
					}
				}
			});
			endSurahComboBox.setModel(new DefaultComboBoxModel<Integer>(numbers));
			endSurahComboBox.setSelectedIndex(113);
			
			endSurahComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_endSurahComboBox = new GridBagConstraints();
			gbc_endSurahComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_endSurahComboBox.gridx = 1;
			gbc_endSurahComboBox.gridy = 1;
			contentPanel.add(endSurahComboBox, gbc_endSurahComboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public int getStartIndex(){
		return startSurahComboBox.getSelectedIndex();
	}

	public int getEndIndex(){
		return endSurahComboBox.getSelectedIndex();
	}
}
