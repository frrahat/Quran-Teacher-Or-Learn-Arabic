/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package Downloader;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;


import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class DownloaderFrame extends JFrame {

	/**
	 * 
	 */
	public static String version="1.2.0";
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	private JPanel menuPanel;
	private JPanel audioPanel;
	private JPanel imagePanel;
	private JLabel lblSelectQari;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JCheckBox chckbxOverwriteAudio;
	private JScrollPane scrollPane;
	private JTextArea txtrAudioprogresstext;
	private JButton btnStart;
	private JProgressBar progressBar;
	private JButton btnBackToMenu;
	private JCheckBox chckbxOverwriteImage;
	private JButton btnStart_1;
	private JProgressBar progressBar_1;
	private JScrollPane scrollPane_1;
	private JTextArea txtrImageprogresstext;
	private JButton btnBackToMenu_1;
	
	public boolean audioOverwriteSelected=false;
	public boolean imageOverwriteSelected=false;
	
	private List <String> QariNames=new ArrayList<>();
	private List <String> audioSourceLinks=new ArrayList<>();
	//public static List <String> imageSourceLinks=new ArrayList<>();
	
	private AudioDownloader audioTask;
	private ImageDownloader imageTask;
	private JLabel lblIcon;
	private JLabel lblKbDownloaded;
	private JLabel lblKbDownloaded_1;
	private JLabel lblProgresslabel;
	private JLabel lblProgresslabel_1;
	private SurahSelectionDialog surahSelectionDialog;
	
	public static void main(String[] args) {
		if(args.length==0) return; if(!args[0].equals("QT")) return;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                    setDesign("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					DownloaderFrame frame = new DownloaderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DownloaderFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DownloaderFrame.class.getResource("/Downloader/icon128_2.png")));
		setTitle("Downloader "+version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		
		
		
		menuPanel = new JPanel();
		menuPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		menuPanel.setBackground(Color.DARK_GRAY);
		menuPanel.setForeground(Color.ORANGE);
		menuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "MENU", TitledBorder.CENTER, TitledBorder.TOP, null, Color.ORANGE));
		contentPane.add(menuPanel,"menuPanel");
		GridBagLayout gbl_menuPanel = new GridBagLayout();
		gbl_menuPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_menuPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_menuPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0};
		gbl_menuPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		menuPanel.setLayout(gbl_menuPanel);
		
		JButton btnAudioButton = new JButton("Download All Audio Files For Quran Teacher");
		btnAudioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl=(CardLayout)contentPane.getLayout();
				cl.show(contentPane,"audioPanel");
			}
		});
		btnAudioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnAudio = new GridBagConstraints();
		gbc_btnAudio.anchor = GridBagConstraints.SOUTH;
		gbc_btnAudio.weighty = 1.0;
		gbc_btnAudio.weightx = 0.5;
		gbc_btnAudio.insets = new Insets(0, 0, 5, 5);
		gbc_btnAudio.gridwidth = 0;
		gbc_btnAudio.gridx = 0;
		gbc_btnAudio.gridy = 1;
		menuPanel.add(btnAudioButton, gbc_btnAudio);
		
		JButton btnImageButton = new JButton("Download All Image Files For Quran Teacher");
		btnImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl=(CardLayout)contentPane.getLayout();
				cl.show(contentPane,"imagePanel");
			}
		});
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(DownloaderFrame.class.getResource("/Downloader/icon128.png")));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.SOUTH;
		gbc_lblIcon.weightx = 0.5;
		gbc_lblIcon.gridwidth = 0;
		gbc_lblIcon.insets = new Insets(0, 0, 5, 0);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 0;
		menuPanel.add(lblIcon, gbc_lblIcon);
		btnImageButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnImagePanel = new GridBagConstraints();
		gbc_btnImagePanel.insets = new Insets(0, 0, 5, 5);
		gbc_btnImagePanel.anchor = GridBagConstraints.NORTH;
		gbc_btnImagePanel.weighty = 1.0;
		gbc_btnImagePanel.gridwidth = 0;
		gbc_btnImagePanel.gridx = 0;
		gbc_btnImagePanel.gridy = 2;
		menuPanel.add(btnImageButton, gbc_btnImagePanel);
		
		
		
		storeQariSource();
		//storeImageLinks();
		surahSelectionDialog=new SurahSelectionDialog();
		
		audioPanel = new JPanel();
		audioPanel.setForeground(Color.ORANGE);
		audioPanel.setBackground(Color.DARK_GRAY);
		audioPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		audioPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Download Audio Files", TitledBorder.CENTER, TitledBorder.TOP, null, Color.ORANGE));
		contentPane.add(audioPanel,"audioPanel");
		GridBagLayout gbl_audioPanel = new GridBagLayout();
		gbl_audioPanel.columnWidths = new int[]{0, 0, 0};
		gbl_audioPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 19, 183, 0, 0};
		gbl_audioPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_audioPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		audioPanel.setLayout(gbl_audioPanel);
		
		lblSelectQari = new JLabel("Select Qari :");
		lblSelectQari.setForeground(Color.ORANGE);
		lblSelectQari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSelectQari = new GridBagConstraints();
		gbc_lblSelectQari.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectQari.anchor = GridBagConstraints.EAST;
		gbc_lblSelectQari.gridx = 0;
		gbc_lblSelectQari.gridy = 0;
		audioPanel.add(lblSelectQari, gbc_lblSelectQari);
		
		comboBox = new JComboBox(QariNames.toArray());
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		int k=QariNames.indexOf("Alafasy 64kbps");
		if(k==-1)k++;
		comboBox.setSelectedIndex(k);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		audioPanel.add(comboBox, gbc_comboBox);
		
		chckbxOverwriteAudio = new JCheckBox("Overwrite Old Audio Files");
		chckbxOverwriteAudio.setBackground(Color.DARK_GRAY);
		chckbxOverwriteAudio.setForeground(Color.GREEN);
		chckbxOverwriteAudio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxOverwriteAudio.setSelected(audioOverwriteSelected);
		chckbxOverwriteAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioOverwriteSelected=chckbxOverwriteAudio.isSelected();
				if(audioOverwriteSelected)
				{
					JOptionPane.showMessageDialog(audioPanel,
						"Previously downloaded files will might be deleted.\n"
						+ "Uncheck to avoid this.",
						 "Are you sure?",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_chckbxOverwriteAudio = new GridBagConstraints();
		gbc_chckbxOverwriteAudio.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxOverwriteAudio.gridwidth = 2;
		gbc_chckbxOverwriteAudio.weightx = 0.5;
		gbc_chckbxOverwriteAudio.gridx = 0;
		gbc_chckbxOverwriteAudio.gridy = 1;
		audioPanel.add(chckbxOverwriteAudio, gbc_chckbxOverwriteAudio);
		
		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnStart.getText().equals("Stop"))
				{
					audioTask.stop();;
					//btnStart.setText("Start");
					return;
				}
				String address=audioSourceLinks.get(comboBox.getSelectedIndex());
				
				txtrAudioprogresstext.setText("Started.");
				txtrAudioprogresstext.append("\nSelected : "+comboBox.getSelectedItem());
				txtrAudioprogresstext.append("\nOverwrite : "+chckbxOverwriteAudio.isSelected());
				txtrAudioprogresstext.append("\n\n\n");
				
				btnStart.setText("Stop");
				progressBar.setValue(0);
				
				audioTask=new AudioDownloader(btnStart,txtrAudioprogresstext,
						address,audioOverwriteSelected,lblKbDownloaded,lblProgresslabel,
						surahSelectionDialog.getStartIndex(),surahSelectionDialog.getEndIndex());
				
				audioTask.execute();
				audioTask.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getPropertyName().equals("progress"))
						{
							int value= (int) evt.getNewValue();
							progressBar.setValue(value);
						}						
					}
				});
			}
		});
		//TODO
		JButton btnSurahSelection_1 = new JButton("Select Specific Surah");
		btnSurahSelection_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				surahSelectionDialog.setVisible(true);
			}
		});
		btnSurahSelection_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_SurahSelection_1 = new GridBagConstraints();
		gbc_SurahSelection_1.gridwidth = 2;
		gbc_SurahSelection_1.insets = new Insets(0, 0, 5, 0);
		gbc_SurahSelection_1.gridx = 0;
		gbc_SurahSelection_1.gridy = 2;
		audioPanel.add(btnSurahSelection_1, gbc_SurahSelection_1);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 3;
		audioPanel.add(btnStart, gbc_btnStart);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridwidth = 2;
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 4;
		audioPanel.add(progressBar, gbc_progressBar);
		
		lblProgresslabel = new JLabel("progress");
		lblProgresslabel.setForeground(Color.ORANGE);
		GridBagConstraints gbc_lblProgresslabel = new GridBagConstraints();
		gbc_lblProgresslabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblProgresslabel.gridwidth = 2;
		gbc_lblProgresslabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblProgresslabel.gridx = 0;
		gbc_lblProgresslabel.gridy = 5;
		audioPanel.add(lblProgresslabel, gbc_lblProgresslabel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		audioPanel.add(scrollPane, gbc_scrollPane);
		
		txtrAudioprogresstext = new JTextArea();
		txtrAudioprogresstext.setBackground(Color.LIGHT_GRAY);
		txtrAudioprogresstext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtrAudioprogresstext.setEditable(false);
		txtrAudioprogresstext.setWrapStyleWord(true);
		txtrAudioprogresstext.setLineWrap(true);
		txtrAudioprogresstext.setText("Click Start to Start Downloading.");
		scrollPane.setViewportView(txtrAudioprogresstext);
		
		lblKbDownloaded = new JLabel("0 KB Downloaded");
		scrollPane.setColumnHeaderView(lblKbDownloaded);
		
		btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl=(CardLayout)contentPane.getLayout();
				cl.show(contentPane,"menuPanel");
			}
		});
		GridBagConstraints gbc_btnBackToMenu = new GridBagConstraints();
		gbc_btnBackToMenu.gridwidth = 2;
		gbc_btnBackToMenu.weightx = 0.5;
		gbc_btnBackToMenu.gridx = 0;
		gbc_btnBackToMenu.gridy = 7;
		audioPanel.add(btnBackToMenu, gbc_btnBackToMenu);
		
		
		
		
		
		
		imagePanel = new JPanel();
		imagePanel.setBackground(Color.DARK_GRAY);
		imagePanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		imagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Download Image FIles", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 200, 0)));
		contentPane.add(imagePanel,"imagePanel");
		GridBagLayout gbl_imagePanel = new GridBagLayout();
		gbl_imagePanel.columnWidths = new int[]{0, 0};
		gbl_imagePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_imagePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_imagePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		imagePanel.setLayout(gbl_imagePanel);
		
		chckbxOverwriteImage = new JCheckBox("Overwrite Old Image Files");
		chckbxOverwriteImage.setForeground(Color.GREEN);
		chckbxOverwriteImage.setBackground(Color.DARK_GRAY);
		chckbxOverwriteImage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxOverwriteImage.setSelected(imageOverwriteSelected);
		chckbxOverwriteImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageOverwriteSelected=chckbxOverwriteImage.isSelected();
				if(imageOverwriteSelected)
				{
					JOptionPane.showMessageDialog(imagePanel,
						"Previously downloaded files will might be deleted.\n"
						+ "Uncheck to avoid this.",
						 "Are you sure?",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_chckbxOverwriteImage = new GridBagConstraints();
		gbc_chckbxOverwriteImage.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxOverwriteImage.weightx = 0.5;
		gbc_chckbxOverwriteImage.gridx = 0;
		gbc_chckbxOverwriteImage.gridy = 0;
		imagePanel.add(chckbxOverwriteImage, gbc_chckbxOverwriteImage);
		
		btnStart_1 = new JButton("Start");
		btnStart_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnStart_1.getText().equals("Stop"))
				{
					imageTask.stop();;
					//btnStart_1.setText("Start");
					return;
				}
				
				txtrImageprogresstext.setText("Started.");
				txtrImageprogresstext.append("\nOverwrite : "+chckbxOverwriteImage.isSelected());
				txtrImageprogresstext.append("\n\n\n");
				
				btnStart_1.setText("Stop");
				progressBar_1.setValue(0);
				
				imageTask=new ImageDownloader(btnStart_1,txtrImageprogresstext,imageOverwriteSelected,
						lblKbDownloaded_1,lblProgresslabel_1,
						surahSelectionDialog.getStartIndex(),surahSelectionDialog.getEndIndex());
				
				imageTask.execute();
				imageTask.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getPropertyName().equals("progress"))
						{
							int value= (int) evt.getNewValue();
							progressBar_1.setValue(value);
						}						
					}
				});
			}
		});
		
		JButton btnSurahselection_2 = new JButton("Select Specific Surah");
		btnSurahselection_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				surahSelectionDialog.setVisible(true);
			}
		});
		btnSurahselection_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnSurahselection_2 = new GridBagConstraints();
		gbc_btnSurahselection_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnSurahselection_2.gridx = 0;
		gbc_btnSurahselection_2.gridy = 1;
		imagePanel.add(btnSurahselection_2, gbc_btnSurahselection_2);
		
		GridBagConstraints gbc_btnStart_1 = new GridBagConstraints();
		gbc_btnStart_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart_1.gridx = 0;
		gbc_btnStart_1.gridy = 2;
		imagePanel.add(btnStart_1, gbc_btnStart_1);
		
		progressBar_1 = new JProgressBar();
		GridBagConstraints gbc_progressBar_1 = new GridBagConstraints();
		gbc_progressBar_1.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar_1.gridx = 0;
		gbc_progressBar_1.gridy = 3;
		imagePanel.add(progressBar_1, gbc_progressBar_1);
		
		lblProgresslabel_1 = new JLabel("progress");
		lblProgresslabel_1.setForeground(Color.ORANGE);
		GridBagConstraints gbc_lblProgresslabel_1 = new GridBagConstraints();
		gbc_lblProgresslabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblProgresslabel_1.gridx = 0;
		gbc_lblProgresslabel_1.gridy = 4;
		imagePanel.add(lblProgresslabel_1, gbc_lblProgresslabel_1);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 5;
		imagePanel.add(scrollPane_1, gbc_scrollPane_1);
		
		txtrImageprogresstext = new JTextArea();
		txtrImageprogresstext.setBackground(Color.LIGHT_GRAY);
		txtrImageprogresstext.setEditable(false);
		txtrImageprogresstext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtrImageprogresstext.setLineWrap(true);
		txtrImageprogresstext.setWrapStyleWord(true);
		txtrImageprogresstext.setText("Click Start to Start Downloading.");
		scrollPane_1.setViewportView(txtrImageprogresstext);
		
		lblKbDownloaded_1 = new JLabel("0 KB Downloaded");
		scrollPane_1.setColumnHeaderView(lblKbDownloaded_1);
		
		btnBackToMenu_1 = new JButton("Back to Menu");
		btnBackToMenu_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBackToMenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl=(CardLayout)contentPane.getLayout();
				cl.show(contentPane,"menuPanel");
			}
		});
		GridBagConstraints gbc_btnBackToMenu_1 = new GridBagConstraints();
		gbc_btnBackToMenu_1.gridx = 0;
		gbc_btnBackToMenu_1.gridy = 6;
		imagePanel.add(btnBackToMenu_1, gbc_btnBackToMenu_1);

	}
	
	
	public void storeQariSource()
	{
		BufferedReader reader=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("AudioLinks")));
		String text;
		try {
			while((text=reader.readLine())!=null)
			{
				if(text.startsWith("name"))
				{
					QariNames.add(text.split("=")[1]);
				}
				else if(text.startsWith("link"))
				{
					audioSourceLinks.add(text.split("=")[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public void storeImageLinks()
	{
		BufferedReader reader=new BufferedReader(new InputStreamReader(
				this.getClass().getResourceAsStream("WbWImageLinks")));
		String text;
		try {
			while((text=reader.readLine())!=null)
			{
				imageSourceLinks.add(text);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
        
    private static void setDesign(String newLookAndFeel)
	{
		try
		{
			UIManager.setLookAndFeel(newLookAndFeel);
		}
		catch(Exception e)
		{
			System.out.println("Unable to load look and feel");
		}
	}
}

