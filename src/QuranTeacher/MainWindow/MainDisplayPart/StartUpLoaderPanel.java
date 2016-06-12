package QuranTeacher.MainWindow.MainDisplayPart;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JProgressBar;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import QuranTeacher.FilePaths;
import QuranTeacher.Dialogs.AboutDialog;
import QuranTeacher.Dialogs.HelpDialog;
import QuranTeacher.MainWindow.MainFrame;
import QuranTeacher.Model.SurahInformationContainer;
import QuranTeacher.PreferencesSetupPanels.TranslationSetupPanel;
import QuranTeacher.RenderAudio.Reciter;
import QuranTeacher.RenderImages.ImageLoader;
import QuranTeacher.RenderTexts.AllTextsContainer;
import QuranTeacher.RenderTexts.QuranText;
import QuranTeacher.RenderTexts.TranslationTextInfoContainer;
import QuranTeacher.WordInformation.WordInfoLoader;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author Rahat
 *
 */
public class StartUpLoaderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JLabel lblProgress;
	private Task task;
	private JButton btnStart;
	private LoadingCompleted loadingCompleted;
	private StartButtonActionListener startButtonListener;


	/**
	 * Create the panel.
	 */
	public StartUpLoaderPanel() {
		setBackground(Color.BLACK);
		setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblBismillah = new JLabel("Bismillahir Rahmanir Rahim");
		lblBismillah.setForeground(Color.ORANGE);
		lblBismillah.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_lblBismillah = new GridBagConstraints();
		gbc_lblBismillah.anchor = GridBagConstraints.NORTH;
		gbc_lblBismillah.insets = new Insets(0, 0, 5, 0);
		gbc_lblBismillah.gridwidth = 0;
		gbc_lblBismillah.gridx = 0;
		gbc_lblBismillah.gridy = 0;
		add(lblBismillah, gbc_lblBismillah);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(StartUpLoaderPanel.class.getResource("/QuranTeacher/images/icon128.png")));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.SOUTH;
		gbc_lblIcon.insets = new Insets(0, 0, 5, 0);
		gbc_lblIcon.gridwidth = 0;
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 1;
		add(lblIcon, gbc_lblIcon);
		
		JLabel lblAppname = new JLabel(MainFrame.AppName+" "+MainFrame.version);
		lblAppname.setForeground(Color.ORANGE);
		lblAppname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAppname.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppname.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAppname = new GridBagConstraints();
		gbc_lblAppname.anchor = GridBagConstraints.NORTH;
		gbc_lblAppname.weighty = 0.1;
		gbc_lblAppname.gridwidth = 0;
		gbc_lblAppname.insets = new Insets(0, 0, 5, 0);
		gbc_lblAppname.gridx = 0;
		gbc_lblAppname.gridy = 2;
		add(lblAppname, gbc_lblAppname);
		
		btnStart = new JButton("Please Wait...");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButtonListener.startButtonClicked();
			}
		});
		
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.setForeground(Color.WHITE);
		btnStart.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 0;
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 3;
		add(btnStart, gbc_btnStart);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpDialog().setVisible(true);
			}
		});
		
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnHelp.setBackground(Color.BLACK);
		btnHelp.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.gridwidth = 0;
		gbc_btnHelp.insets = new Insets(0, 0, 5, 0);
		gbc_btnHelp.gridx = 1;
		gbc_btnHelp.gridy = 4;
		add(btnHelp, gbc_btnHelp);
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutDialog().setVisible(true);
			}
		});
		btnAbout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAbout.setForeground(Color.WHITE);
		btnAbout.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnAbout = new GridBagConstraints();
		gbc_btnAbout.anchor = GridBagConstraints.NORTH;
		gbc_btnAbout.weighty = 0.1;
		gbc_btnAbout.gridwidth = 0;
		gbc_btnAbout.insets = new Insets(0, 0, 5, 0);
		gbc_btnAbout.gridx = 1;
		gbc_btnAbout.gridy = 5;
		add(btnAbout, gbc_btnAbout);
		
		lblProgress = new JLabel("Progress");
		lblProgress.setForeground(Color.GREEN);
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.insets = new Insets(0, 0, 5, 0);
		gbc_lblProgress.gridwidth = 0;
		gbc_lblProgress.gridx = 0;
		gbc_lblProgress.gridy = 6;
		add(lblProgress, gbc_lblProgress);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 0;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 7;
		add(progressBar, gbc_progressBar);
		
	}
	
	
	public void startLoading(){
		progressBar.setIndeterminate(true);
        btnStart.setEnabled(false);
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress" == evt.getPropertyName()) {
		            int progress = (Integer) evt.getNewValue();
		            progressBar.setIndeterminate(false);
		            progressBar.setValue(progress);
		            lblProgress.setText(String.format(
		                        "Lodaing necessary files : %d%% completed.\n", progress));
		        }
			}
		});
        task.execute();
	}
	
	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
        	//load word info file(apprx 33 mb) and quran text files(apprx 1.5 mb/file average)
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            //load surahInfo
            SurahInformationContainer.loadAllSurahInfos();
            
            int totalQuranFiles=TranslationTextInfoContainer.getSize()+1;
            //each file assumed to be of size 1.5MB in average
            double step=90.0/(10+totalQuranFiles*1.5);
            //load arabic QuranText
            InputStream in=StartUpLoaderPanel.class.getResourceAsStream(FilePaths.ArabicTextFilePath);
            AllTextsContainer.arabicText=new QuranText(in, true);
            progress+=(int)step*1.5;
            setProgress(progress);
            
            AllTextsContainer.translationtexts=new ArrayList<>();
            boolean invalidFileFound=false;
            for(int i=0;i<TranslationTextInfoContainer.getSize();i++){
            	//System.out.println("fileName "+TranslationTextInfoContainer.getTransFile(i).getFileName());
            	QuranText quranText=new QuranText(
            			TranslationTextInfoContainer.getTransFile(i).getInputStream(),
            			false);
            	if(quranText.isValid()){
            		System.out.println(TranslationTextInfoContainer.getTransFile(i).getFileName());
            		AllTextsContainer.translationtexts.add(quranText);
            	}else{
            		invalidFileFound=true;
            		System.err.println("Invalid file Found ,"+TranslationTextInfoContainer.getTransFile(i).getFileName());
            		TranslationTextInfoContainer.getAllTranslationTextFiles().remove(i);
            		i--;
            	}
            	if(invalidFileFound){
            		TranslationSetupPanel.updateTextSelectionCBox();
            	}
            	progress+=(int)step*1.5;
            	//System.out.println(i+","+progress+","+step);
            	setProgress(progress);
            }
            
            //load wordInfoFile
            new WordInfoLoader().load();
            /*setProgress(83);
            //update: loading additional font task moved to MyFontsContainer.init()
            //loadAdditional fonts
            int addedFiles=MyFontsContainer.loadAdditionalFonts();
            if(addedFiles!=0){
				MyFontsContainer.refresh();
				System.out.println(addedFiles+" file(s) added. Updated fonts list.");
			}*/
            setProgress(Math.max(progress, 90));
            
            manageDirForStorage();
            setProgress(100);
            
            return null;
        }
 
        /*
         * Executed in event dispatch thread
         */
        public void done() {
        	//for showing exception
        	try {
				get();
				System.out.println("Starup loading task completed.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        	
            btnStart.setEnabled(true);
            btnStart.setText("Start");
            lblProgress.setText("Finished!\n");
            loadingCompleted.startInitializingTask();
        }
    }
	
	public interface LoadingCompleted{
		public void startInitializingTask();
	}

	public void setLoadingCompletionListener(LoadingCompleted completed) {
		this.loadingCompleted=completed;
	}
	
	public interface StartButtonActionListener{
		public void startButtonClicked();
	}
	
	public void setStartButtonActionListener(StartButtonActionListener actionListener){
		this.startButtonListener=actionListener;
	}
	
	
	private void manageDirForStorage() {
		//for image storage
		//TODO
		new ImageLoader(true).manageStorageDirForImages();;
		
		//for audio
		Reciter.manageStorageDirForAudios();
		
		//for hitFiles
		File hitFilesDir=new File(FilePaths.hitFileDirName);
		if(!hitFilesDir.exists()){
			hitFilesDir.mkdirs();
		}
	}
}
