package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;














import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import QuranTeacher.FilePaths;
import QuranTeacher.Basics.Ayah;
import QuranTeacher.Basics.TimedAyah;
import QuranTeacher.MainWindow.MainDisplayPart.AnimationPanel;
import QuranTeacher.WordInformation.WordInfoLoader;
import QuranTeacher.WordInformation.WordInformation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Rahat
 *	01-07-2015
 */
public class HitFileEditorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ButtonActionListener buttonActionListener;
	private boolean fileChanged;
	private JFileChooser fileChooser;
	private final String[] supportedFileExts={"txt"};
	private ArrayList<TimedAyah> timedAyahs;
	private JLabel lblTime;
	private JComboBox<String> ayahComboBox;
	private JList<String> list;
	private JButton btnPlayContinuous;
	
	private static File hitFile;

	/**
	 * Create the dialog.
	 */
	public HitFileEditorDialog() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Hit File Editor Dialog");
		setBounds(20, 200, 400, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 249, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JPanel navigationPanel = new JPanel();
		navigationPanel.setBackground(Color.BLACK);
		GridBagConstraints gbc_navigationPanel = new GridBagConstraints();
		gbc_navigationPanel.anchor = GridBagConstraints.NORTH;
		gbc_navigationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_navigationPanel.gridx = 0;
		gbc_navigationPanel.gridy = 0;
		contentPanel.add(navigationPanel, gbc_navigationPanel);
		GridBagLayout gbl_navigationPanel = new GridBagLayout();
		gbl_navigationPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_navigationPanel.rowHeights = new int[]{0, 0, 0};
		gbl_navigationPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_navigationPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		navigationPanel.setLayout(gbl_navigationPanel);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setToolTipText("Edit Current Ayah Hit Timing");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int k=ayahComboBox.getSelectedIndex();
				if(k==-1){
					JOptionPane.showMessageDialog(getParent(), "No file has been loaded");
					return;
				}
				buttonActionListener.buttonClicked("edit", k);
				fileChanged=true;
				
				JOptionPane.showMessageDialog(getParent(), "Press Enter Key"
						+ " To Start The Timing");
			}
		});
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 0;
		gbc_btnEdit.gridy = 0;
		navigationPanel.add(btnEdit, gbc_btnEdit);
	
		JButton btnPlay = new JButton("Play");
		btnPlay.setToolTipText("Play Current Ayah");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int k=ayahComboBox.getSelectedIndex();
				if(k==-1){
					JOptionPane.showMessageDialog(getParent(), "No file has been loaded");
					return;
				}
				buttonActionListener.buttonClicked("play", k);
				setTextButtonContinuous();
			}
		});
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay.gridx = 1;
		gbc_btnPlay.gridy = 0;
		navigationPanel.add(btnPlay, gbc_btnPlay);
	
		JButton btnPlayNext = new JButton("Next Ayah");
		btnPlayNext.setToolTipText("Next Ayah To Edit Or Play");
		btnPlayNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonActionListener.buttonClicked("next", ayahComboBox.getSelectedIndex()+1);	
			}
		});
		GridBagConstraints gbc_btnPlayNext = new GridBagConstraints();
		gbc_btnPlayNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayNext.gridx = 2;
		gbc_btnPlayNext.gridy = 0;
		navigationPanel.add(btnPlayNext, gbc_btnPlayNext);
	
		btnPlayContinuous = new JButton("Play Continuous");
		btnPlayContinuous.setToolTipText("Continuously Play All Hit Ayahs");
		btnPlayContinuous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int k=ayahComboBox.getSelectedIndex();
				if(k==-1){
					JOptionPane.showMessageDialog(getParent(), "No file has been loaded");
					return;
				}
				buttonActionListener.buttonClicked("playContinuous", k);
				toggleTextPlayContinuous();
			}
		});
		GridBagConstraints gbc_btnPlayContinuous = new GridBagConstraints();
		gbc_btnPlayContinuous.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayContinuous.gridx = 3;
		gbc_btnPlayContinuous.gridy = 0;
		navigationPanel.add(btnPlayContinuous, gbc_btnPlayContinuous);
	
		JLabel lblTimeLabel = new JLabel("Time :");
		lblTimeLabel.setToolTipText("Hit Time");
		lblTimeLabel.setForeground(Color.ORANGE);
		lblTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTimeLabel = new GridBagConstraints();
		gbc_lblTimeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblTimeLabel.gridx = 0;
		gbc_lblTimeLabel.gridy = 1;
		navigationPanel.add(lblTimeLabel, gbc_lblTimeLabel);
	
		lblTime = new JLabel("0");
		lblTime.setToolTipText("Hit Time");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTime.setForeground(Color.CYAN);
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.WEST;
		gbc_lblTime.gridwidth = 0;
		gbc_lblTime.gridx = 1;
		gbc_lblTime.gridy = 1;
		navigationPanel.add(lblTime, gbc_lblTime);
	
	
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		list = new JList<String>();
		list.setToolTipText("Hit Time Tracking. Double Click To Edit A Hit Time.");
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()<2)
					return;
				Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
				if (r != null && r.contains(e.getPoint())) {
					int index = list.locationToIndex(e.getPoint());
					
					if(index!=-1){
						String text=JOptionPane.showInputDialog(getParent(),
								"Enter time :",timedAyahs.get(ayahComboBox.getSelectedIndex()).getWordHitTime(index));
						if(text==null)//cancelled
							return;
						int time=0;
						try{
							time=Integer.parseInt(text);
						}catch(NumberFormatException ne){
							JOptionPane.showMessageDialog(getParent(), 
									"NumberFormat Exception Occured","Invalid time",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						timedAyahs.get(ayahComboBox.getSelectedIndex()).
							setWordTime(index, time);
						
						updateList();
						fileChanged=true;
					}
				}
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 16));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.DARK_GRAY);
		list.setForeground(Color.ORANGE);
		scrollPane.setViewportView(list);
	
		ayahComboBox = new JComboBox<String>();
		ayahComboBox.setToolTipText("Ayah No");
		scrollPane.setColumnHeaderView(ayahComboBox);
		
		ayahComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int k=ayahComboBox.getSelectedIndex();
				setListModel(k);
			}
		});
		
	
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.BLACK);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton saveButton = new JButton("Save");
		saveButton.setToolTipText("Save As Hit File");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!fileChanged){
					JOptionPane.showMessageDialog(getParent(),"No Changes To Save");
					return;
				}else{
					saveHitFile();
				}
			}
		});
	
		JButton btnLoad = new JButton("Load");
		btnLoad.setToolTipText("Load An Existing Hit File");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChooser==null){
					Boolean old = UIManager.getBoolean("FileChooser.readOnly");  
					UIManager.put("FileChooser.readOnly", Boolean.TRUE);   
					fileChooser=new JFileChooser(FilePaths.hitFileDirName);
					UIManager.put("FileChooser.readOnly", old); 
					setFileChooserFont(fileChooser.getComponents(),(new Font("Tahoma",Font.PLAIN,20)));
					fileChooser.setPreferredSize(new Dimension(800,600));
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "Text Files (*.txt)", supportedFileExts);
					fileChooser.setFileFilter(filter);
				}
				
				int returnVal=fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
			       loadHitFile(fileChooser.getSelectedFile());
				}
			}
		});
		JButton btnNew = new JButton("New");
		btnNew.setToolTipText("Make A New Hit File");
		btnNew.setBackground(Color.PINK);
		buttonPane.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fileChanged){
					int k=JOptionPane.showConfirmDialog(getParent(), 
							"Changes have been made. Do you want to SAVE those, otherwise they will be lost?","Confirm Exit", 
							JOptionPane.WARNING_MESSAGE);
					
					if(k==JOptionPane.OK_OPTION){
						saveHitFile();
					}
				}
				String suraNoText=JOptionPane.showInputDialog(getParent(),"Enter Surah No. for New hit file","1");
				if(suraNoText==null)//cancelled
					return;
				String ayahNoText=JOptionPane.showInputDialog(getParent(),"Enter Ayah No. for New hit file","1");
				if(ayahNoText==null)//cancelled
					return;
				int surahNo=-1;
				int ayahNo=-1;
				try{
					surahNo=Integer.parseInt(suraNoText);
					ayahNo=Integer.parseInt(ayahNoText);
					
					buttonActionListener.buttonClicked("new", surahNo*1000+ayahNo);
					
					fileChanged=true;
					hitFile=null;
					
					JOptionPane.showMessageDialog(getParent(), "Press Enter Key"
							+ " To Start The Timing");
					
				}catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(getParent(), "Number Format Exception Occured.","Invalid Input",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		buttonPane.add(btnLoad);
	
		saveButton.setActionCommand("save");
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);
	
	
		JButton exitButton = new JButton("Exit");
		exitButton.setToolTipText("Exit The Dialog");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChanged){
					int k=JOptionPane.showConfirmDialog(getParent(), 
							"Changes have been made and NOT saved yet. Do you want to Exit WITHOUT saving?","Confirm Exit", 
							JOptionPane.WARNING_MESSAGE);
					
					if(k==JOptionPane.OK_OPTION){
						//saveHitFile();
						setVisible(false);
					}
					else{
					}
				}else{
					setVisible(false);
				}
			}
		});
		exitButton.setActionCommand("exit");
		buttonPane.add(exitButton);
		
	}

	private void setFileChooserFont(Component[] comp, Font font) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				setFileChooserFont(((Container) comp[x]).getComponents(), font);
			try {
				comp[x].setFont(font);
			} catch (Exception e) {
			}// do nothing
		}
	}
	
	private void saveHitFile() {
		String fileNameHint="";
		if(hitFile!=null){
			fileNameHint=hitFile.getName();
		
			int k=fileNameHint.lastIndexOf('.');
			if(k!=-1){
				fileNameHint=fileNameHint.substring(0, k);
			}
		}else{
			Ayah startAyah=timedAyahs.get(0).getAyah();
			Ayah endAyah=timedAyahs.get(timedAyahs.size()-1).getAyah();
			
			fileNameHint="surah-"+Integer.toString(startAyah.suraIndex+1)+","+
					"ayah-"+Integer.toString(startAyah.ayahIndex+1)+" to ";
			if(startAyah.suraIndex!=endAyah.suraIndex){
				fileNameHint+="surah-"+Integer.toString(endAyah.suraIndex+1)+",";
			}
			fileNameHint+="ayah-"+Integer.toString(endAyah.ayahIndex+1);
		}
		
		String text=JOptionPane.showInputDialog(getParent(), "Enter File Name",fileNameHint+".txt");
		if(text==null)//cancelled
			return;
		
		File outputFile=new File(FilePaths.hitFileDirName+"/"+text);
		
		int k=text.lastIndexOf('.');
		if(k!=-1){
			text=text.substring(0, k);
		}
		int version=1;
		while(outputFile.exists()){
			outputFile=new File(FilePaths.hitFileDirName+"/"+text+"("+Integer.toString(version)+")"+".txt");
			version++;
		}
		
		try{
			PrintWriter writer=new PrintWriter(outputFile);
			for(int i=0;i<timedAyahs.size();i++){
				TimedAyah t=timedAyahs.get(i);
				writer.write("#"+Integer.toString(t.getAyah().suraIndex)+"\n");
				writer.write(Integer.toString(t.getAyah().ayahIndex)+"\n");
				
				ArrayList<Integer>hitTimes=t.getWordHitTimes();
				for(int j=0;j<hitTimes.size();j++){
					writer.write(Integer.toString(hitTimes.get(j))+"\n");
				}
			}
			
			writer.flush();
			writer.close();
			
			JOptionPane.showMessageDialog(getParent(),"Saved Successfully");
			fileChanged=false;
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}
	
	public interface ButtonActionListener{
		public void buttonClicked(String actionCommand, int index);
	}
	
	public void setButtonActionListener(ButtonActionListener listener){
		this.buttonActionListener=listener;
	}

	private void loadHitFile(File file){
		HitFileEditorDialog.hitFile=file;
		if(AnimationPanel.loadHitFile(file)){
			setTimedAyahs(AnimationPanel.getTimedAyahs());
			setComboBoxModel();
		}else{
			JOptionPane.showMessageDialog(getParent(), "Information in the file "
					+ "doesn't follow specified format. See help for more info.",
					"Invalid File",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setTimedAyahs(ArrayList<TimedAyah> timedAyahs){
		this.timedAyahs=timedAyahs;
	}
	
	public void setComboBoxModel(){
		if(timedAyahs==null){
			setTimedAyahs(AnimationPanel.getTimedAyahs());
		}
		String ayahStrings[]=new String[timedAyahs.size()];
		for(int i=0;i<ayahStrings.length;i++){
			ayahStrings[i]=timedAyahs.get(i).getAyah().toString();
		}
		ayahComboBox.setModel(new DefaultComboBoxModel<String>(ayahStrings));
		setListModel(ayahComboBox.getSelectedIndex());
	}
	
	public int getComboBoxSelectedIndex(){
		return ayahComboBox.getSelectedIndex();
	}
	
	public int getAyahComboboxItemCount(){
		return ayahComboBox.getItemCount();
	}
	
	public void setFileState(boolean changed){
		fileChanged=changed;
	}
	public void setComboBoxSelectedIndex(int index){
		ayahComboBox.setSelectedIndex(index);
	}
	
	public void setTimeLabel(long time){
		lblTime.setText(Long.toString(time));
	}
	
	private void setListModel(int selectedIndex) {
		if(selectedIndex==-1)
			return;
		DefaultListModel<String> listModel=new DefaultListModel<>();
		ArrayList<Integer> WordTimes=timedAyahs.get(selectedIndex).getWordHitTimes();
		for(int i=0;i<WordTimes.size();i++){
			String text=Integer.toString(WordTimes.get(i))+">>";
			
			WordInformation w=WordInfoLoader.getWordInfo(timedAyahs.get(selectedIndex).getBigIndexOfWord(i));
			text+=w.transLiteration+">>("+w.meaning+")";
			
			listModel.addElement(text);
		}
		list.setModel(listModel);
	}
	
	public void setListItemSelectedIndex(int index){
		list.setSelectedIndex(index);//no bound check is needed
	}
	
/*	
	public void updateDialog(){
		//setTimedAyahs(AnimationPanel.getTimedAyahs());
		//setComboBoxModel();
		updateList();
	}*/
	
	public void updateList(){
		setListModel(ayahComboBox.getSelectedIndex());
	}
	
	public void updateComboBox(){
		setComboBoxModel();
	}
	
	private void toggleTextPlayContinuous(){
		if(btnPlayContinuous.getText().equals("Play Continuous")){
			btnPlayContinuous.setText("Play Single");
			btnPlayContinuous.setToolTipText("Stop Continuous Playing");
		}else{
			setTextButtonContinuous();
		}
	}
	
	
	private void setTextButtonContinuous() {
		btnPlayContinuous.setText("Play Continuous");
		btnPlayContinuous.setToolTipText("Continuously Play All Hit Ayahs");
	}
}
