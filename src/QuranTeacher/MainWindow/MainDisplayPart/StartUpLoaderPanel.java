package QuranTeacher.MainWindow.MainDisplayPart;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JProgressBar;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import QuranTeacher.FilePaths;
import QuranTeacher.Dialogs.AboutDialog;
import QuranTeacher.Dialogs.HelpDialog;
import QuranTeacher.MainWindow.MainFrame;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class StartUpLoaderPanel extends JPanel {

	private JProgressBar progressBar;
	private JLabel lblProgress;
	private Task task;
	private JButton btnStart;


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
				//TODO
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
		                        "Completed %d%% of task.\n", progress));
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
            
            int totalQuranFiles=TranslationTextInfoContainer.getSize()+1;
            int step=(int)(100.0/(33+totalQuranFiles))*(totalQuranFiles);
            //load arabic QuranText
            InputStream in=StartUpLoaderPanel.class.getResourceAsStream(FilePaths.ArabicTextFilePath);
            AllTextsContainer.arabicText=new QuranText(in, true);
            progress+=step;
            setProgress(progress);
            
            AllTextsContainer.translationtexts=new ArrayList<>();
            for(int i=0;i<TranslationTextInfoContainer.getSize();i++){
            	AllTextsContainer.translationtexts.add(new QuranText(
            			TranslationTextInfoContainer.getTransFile(i).getInputStream(),
            			false));
            	
            	progress+=step;
            	setProgress(progress);
            }
            
            //load wordInfoFile
            new WordInfoLoader().load();
            setProgress(100);
            
            return null;
        }
 
        /*
         * Executed in event dispatch thread
         */
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            btnStart.setEnabled(true);
            btnStart.setText("Start");
            lblProgress.setText("Done!\n");
        }
    }
}
