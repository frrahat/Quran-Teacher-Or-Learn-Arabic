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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;









import QuranTeacher.FilePaths;
import QuranTeacher.Model.SurahInformationContainer;
import QuranTeacher.Preferences.TranslationPreferences;
import QuranTeacher.RenderTexts.TranslationTextInfoContainer;

public class TranslationSetupPanel extends PreferencesSetupPanel {

	/**
	 * For setting translation preferences
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	
	private JFileChooser fileChooser;
	private final String[] supportedFileExts={"txt","zip"};

	private int totalAyahs;

	private static JComboBox<String> SecondTextSelectComboBox;

	private static JComboBox<String> PrimTextSelectComboBox;
	
	private static TranslationPreferences pref;
	
	public TranslationSetupPanel(String name, final TranslationPreferences preferences) {
		super(name, preferences);
		
		TranslationSetupPanel.pref=preferences;
		
		int primTransFileIndex=preferences.getPrimaryTextIndex();
		if(primTransFileIndex>=TranslationTextInfoContainer.getSize()){
			primTransFileIndex=0;
			preferences.setPrimaryTextIndex(primTransFileIndex);
		}
		previewText=TranslationTextInfoContainer.getTransFile(primTransFileIndex).getPreviewText();
		
		int secondTransFileIndex=preferences.getSecondaryTextIndex();
		if(secondTransFileIndex>=TranslationTextInfoContainer.getSize()){
			secondTransFileIndex=-1;
			preferences.setSecondaryTextIndex(secondTransFileIndex);
		}
		
		if(secondTransFileIndex!=-1){
			previewText+="\n\n"+TranslationTextInfoContainer.getTransFile(preferences.getSecondaryTextIndex()).getPreviewText();
		}
		
		fontPreviewPanel.setText(previewText);
		fontPreviewPanel.updateFontPreview();
		
		JLabel lblLangNamePrim = new JLabel("Primary Text :");
		lblLangNamePrim.setToolTipText("Set First Text For Translation Panel");
		lblLangNamePrim.setForeground(Color.ORANGE);
		lblLangNamePrim.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLangNamePrim = new GridBagConstraints();
		gbc_lblLangNamePrim.anchor = GridBagConstraints.WEST;
		gbc_lblLangNamePrim.insets = new Insets(0, 0, 5, 5);
		gbc_lblLangNamePrim.gridx = 0;
		gbc_lblLangNamePrim.gridy = 7;
		add(lblLangNamePrim, gbc_lblLangNamePrim);
		
		PrimTextSelectComboBox = new JComboBox<String> ();
		PrimTextSelectComboBox.setModel(new DefaultComboBoxModel<>(TranslationTextInfoContainer.getAllFileNames(false)));
		PrimTextSelectComboBox.setBackground(Color.LIGHT_GRAY);
		PrimTextSelectComboBox.setSelectedIndex(preferences.getPrimaryTextIndex());
		PrimTextSelectComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int i=PrimTextSelectComboBox.getSelectedIndex();
				preferences.setPrimaryTextIndex(i);
				if(preferences.getSecondaryTextIndex()!=-1){
					fontPreviewPanel.setText(TranslationTextInfoContainer.getTransFile(i).getPreviewText()
							+"\n\n"+TranslationTextInfoContainer.getTransFile(preferences.getSecondaryTextIndex()).getPreviewText());
				}
				else{
					fontPreviewPanel.setText(TranslationTextInfoContainer.getTransFile(i).getPreviewText());
				}
				fontPreviewPanel.updateTextArea();
			}
		});
		PrimTextSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_PrimTextSelectComboBox = new GridBagConstraints();
		gbc_PrimTextSelectComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_PrimTextSelectComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_PrimTextSelectComboBox.gridx = 1;
		gbc_PrimTextSelectComboBox.gridy = 7;
		add(PrimTextSelectComboBox, gbc_PrimTextSelectComboBox);
		
		
		
		
		
		JLabel lblLangNameSecond = new JLabel("Secondary Text :");
		lblLangNameSecond.setToolTipText("Set Second Text For Translation Panel");
		lblLangNameSecond.setForeground(Color.ORANGE);
		lblLangNameSecond.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLangNameSecond = new GridBagConstraints();
		gbc_lblLangNameSecond.anchor = GridBagConstraints.WEST;
		gbc_lblLangNameSecond.insets = new Insets(0, 0, 5, 5);
		gbc_lblLangNameSecond.gridx = 0;
		gbc_lblLangNameSecond.gridy = 8;
		add(lblLangNameSecond, gbc_lblLangNameSecond);
		
		
		SecondTextSelectComboBox = new JComboBox<String> ();
		SecondTextSelectComboBox.setModel(new DefaultComboBoxModel<>(TranslationTextInfoContainer.getAllFileNames(true)));
		SecondTextSelectComboBox.setBackground(Color.LIGHT_GRAY);
		if(preferences.getSecondaryTextIndex()==-1){
			SecondTextSelectComboBox.setSelectedIndex(TranslationTextInfoContainer.getSize());
		}
		else{
			SecondTextSelectComboBox.setSelectedIndex(preferences.getSecondaryTextIndex());
		}
		SecondTextSelectComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int i=SecondTextSelectComboBox.getSelectedIndex();
				if(i!=TranslationTextInfoContainer.getSize()){
					preferences.setSecondaryTextIndex(i);
					fontPreviewPanel.setText(
							TranslationTextInfoContainer.getTransFile(preferences.getPrimaryTextIndex()).getPreviewText()
							+"\n\n"+
									TranslationTextInfoContainer.getTransFile(i).getPreviewText());
				}else{
					preferences.setSecondaryTextIndex(-1);
					fontPreviewPanel.setText(
							TranslationTextInfoContainer.getTransFile(preferences.getPrimaryTextIndex()).getPreviewText());
				}
				
				fontPreviewPanel.updateTextArea();
			}
		});
		SecondTextSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_SecondTextSelectComboBox = new GridBagConstraints();
		gbc_SecondTextSelectComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_SecondTextSelectComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_SecondTextSelectComboBox.gridx = 1;
		gbc_SecondTextSelectComboBox.gridy = 8;
		add(SecondTextSelectComboBox, gbc_SecondTextSelectComboBox);
		
		
		JButton addAdditionalTextFiles = new JButton("Add Additional Texts");
		addAdditionalTextFiles.setToolTipText("Browse And Add More Translation File");
		//addAdditionalTextFiles.setForeground(Color.ORANGE);
		addAdditionalTextFiles.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnAdditText = new GridBagConstraints();
		gbc_btnAdditText.anchor = GridBagConstraints.WEST;
		gbc_btnAdditText.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdditText.gridx = 1;
		gbc_btnAdditText.gridy = 9;
		add(addAdditionalTextFiles, gbc_btnAdditText);
		
		
		totalAyahs=SurahInformationContainer.totalAyahsUpto[114];
		addAdditionalTextFiles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileChooser==null){
					Boolean old = UIManager.getBoolean("FileChooser.readOnly");  
					UIManager.put("FileChooser.readOnly", Boolean.TRUE);  
					  
					fileChooser=new JFileChooser(System.getProperty("user.home"));
					UIManager.put("FileChooser.readOnly", old); 
					setFileChooserFont(fileChooser.getComponents(),(new Font("Tahoma",Font.PLAIN,20)));
					fileChooser.setPreferredSize(new Dimension(800,600));
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "Text or Zip Files (*.txt,*.zip)", supportedFileExts);
					fileChooser.setFileFilter(filter);
				}
				
				int returnVal=fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
			       File file=fileChooser.getSelectedFile();
			       if(addNewFile(file)){
			    	   JOptionPane.showMessageDialog(getParent(),"File Added Successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
			    	   updateTextSelectionCBox();
			       }else{
			    	   JOptionPane.showMessageDialog(getParent(),"Invalid or Corrupted File.","Failure",JOptionPane.ERROR_MESSAGE);
			       }
				}
			}
		});
	}
	
	public static void updateTextSelectionCBox(){
		PrimTextSelectComboBox.setModel(
				new DefaultComboBoxModel<>(TranslationTextInfoContainer.getAllFileNames(false)));
		PrimTextSelectComboBox.setSelectedIndex(pref.getPrimaryTextIndex());
		
		
		SecondTextSelectComboBox.setModel(
				new DefaultComboBoxModel<>(TranslationTextInfoContainer.getAllFileNames(true)));
		
		if(pref.getSecondaryTextIndex()==-1){
			SecondTextSelectComboBox.setSelectedIndex(TranslationTextInfoContainer.getSize());
		}else{
			SecondTextSelectComboBox.setSelectedIndex(pref.getSecondaryTextIndex());
		}
	}
	public void setFileChooserFont(Component[] comp, Font font) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				setFileChooserFont(((Container) comp[x]).getComponents(), font);
			try {
				comp[x].setFont(font);
			} catch (Exception e) {
			}// do nothing
		}
	}
	
	
	private boolean addNewFile(final File file){

		if(file.getName().endsWith(".txt")){
			
			return writeToFileFrom(file.getName(), getInputStream(file));
		}
		else if(file.getName().endsWith(".zip")){
			return addFilesFromZipFile(file);
		}
		else return false;
		
	}
	
	
	private InputStream getInputStream(File file){
		InputStream inStream=null;
		try{
			inStream=new FileInputStream(file);
		}catch(IOException ie){
			//very low chance of this exception
		}
		
		return inStream;
	}
	
	private boolean writeToFileFrom(String writingFileName, InputStream fromInStream)
	{
		boolean success=false;
		
		File writingDir=new File(FilePaths.additionalTextsDir);
		if(!writingDir.exists()){
			writingDir.mkdirs();
		}
		File writingFile=new File(writingDir,writingFileName);
        try {
        	BufferedReader br=new BufferedReader(new InputStreamReader(fromInStream));
        	
            FileOutputStream f = new FileOutputStream(writingFile);
            PrintWriter pw = new PrintWriter(f);
            
            String text=null;
            int lineCount=0;
            while((text=br.readLine()) != null)
            {
                pw.append(text).append("\n");
                lineCount++;
            }
            
            if(lineCount>=totalAyahs){//validity check
            
	            pw.flush();           
	            pw.close();
	            
	            success=true;
	            //Toast.makeText(this,"Saved Successfully :\n"+toFile.getName(), Toast.LENGTH_LONG).show();
	            TranslationTextInfoContainer.addToTransFileList(writingFile);
	            f.close();
            }else if(writingFile.exists()){//file has been saved
            		writingFile.delete();
            }

            fromInStream.close();
            br.close();
            
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return success;
	}
	
	private boolean addFilesFromZipFile(File file){
		boolean atLeastOnefileWritten=false;
		try {
	        @SuppressWarnings("resource")
			ZipFile zipFile = new ZipFile(file);
	        for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); ) {
	            ZipEntry entry = (ZipEntry) e.nextElement();

	            if (!entry.isDirectory() && entry.getName().endsWith(".txt")){
	            	//TODO do something
	            	//read it and check if it contains total ayahs of 7...
	            	//no checking for replacements of same files
	            	InputStream inStream=zipFile.getInputStream(entry);
	            	if(writeToFileFrom(entry.getName(),inStream)){
	            		if(!atLeastOnefileWritten){
	            			atLeastOnefileWritten=true;
	            		}
	            	}

	            }
	        }
	    } catch (ZipException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return atLeastOnefileWritten;
	}

}
