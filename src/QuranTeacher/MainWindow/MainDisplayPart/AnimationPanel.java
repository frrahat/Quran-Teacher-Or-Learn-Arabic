/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.MainDisplayPart;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import QuranTeacher.Dialogs.HitFileEditorDialog;
import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Dialogs.SubTextDialog;
import QuranTeacher.Interfaces.UserInputListener;
import QuranTeacher.MainWindow.SidePart.SelectionPanel;
import QuranTeacher.Model.Ayah;
import QuranTeacher.Model.HitCommand;
import QuranTeacher.Model.HitString;
import QuranTeacher.Model.SurahInformationContainer;
import QuranTeacher.Model.TimedAyah;
import QuranTeacher.Preferences.AnimationPreferences;
import QuranTeacher.Preferences.AudioPreferences;
import QuranTeacher.Preferences.DeltaPixelProperty;
import QuranTeacher.Preferences.Preferences;
import QuranTeacher.Preferences.SubtextPreferences;
import QuranTeacher.PreferencesSetupPanels.AudioPreferencesPanel;
import QuranTeacher.RenderAnimation.Animation;
import QuranTeacher.RenderAnimation.FocusCheckRunnable;
import QuranTeacher.RenderAudio.Reciter;
import QuranTeacher.RenderImages.ImageLoader;
import QuranTeacher.RenderTexts.AllTextsContainer;
import QuranTeacher.WordInformation.WordInfoLoader;


public class AnimationPanel extends Animation {

	/**
	 * For handling user input
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Ayah runningAyah=new Ayah(0, 0);
	/*private String bismillah="\u0628\u0650\u0633\u0652\u0645\u0650 "
			+ "\u0627\u0644\u0644\u0651\u064e\u0647\u0650 \u0627\u0644"
			+ "\u0631\u0651\u064e\u062d\u0652\u0645\u064e\u0670\u0646"
			+ "\u0650 \u0627\u0644\u0631\u0651\u064e\u062d\u0650\u064a\u0645\u0650";*/
	//private String displayText;
	
	//private Reciter reciter;
	private boolean paused=false;
	private UserInputListener userInputListener;
	private Thread focusCheckingThread;
	
	private AudioPreferences audioPrefs;
	private boolean isAnimAudioOn;//unimportant, not actually used except in one case
	private int audioSIndex;
	
	
	private long startTime;
	private int displayedHitWords;
	
	
	private FileInputStream audioFileInputStream;
	
	//private long injuryTime;//for the lagging of reciter
	private static ArrayList<TimedAyah> timedAyahs;
	private static HitFileEditorDialog hitFileEditorDialog;
	private int editTimedAyahIndex;
	private int editWordIndexOfTimedAyah;
	private Timer autoDisplayTimer;
	
	private static ArrayList<Integer>wordHitTimes;
	private static ArrayList<String>hitStrings;
		
	private boolean isHitPlayPaused;
	private long hitPlayPausedTime;
	private long hitPlayResumedTime;
	
	private SubTextDialog subTextDialog;
	private int backspacePressedCount; // on a timed ayah word
	
	public AnimationPanel() {
		//System.out.println("DisplayPanel() called");
		
		setFocusable(true);
		focusCheckingThread=new Thread();
		
		audioPrefs=PreferencesDialog.getAudioPref();
		updateAudioPref();
		addListeners();
		
		wordHitTimes=new ArrayList<>();
		autoDisplayTimer=new Timer(1000/100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				autoRun();
			}
		});
		
		animationType=Animation_type.Simple_Animation;
		
		timedAyahs=new ArrayList<>();
		hitFileEditorDialog=new HitFileEditorDialog();
		
		hitFileEditorDialog.setButtonActionListener(new HitFileEditorDialog.ButtonActionListener() {
			
			@Override
			public void buttonClicked(String actionCommand,int index) {
				if("play".equals(actionCommand)){
					animationType=Animation_type.Timed_Animation;
					resetHitTimesToNewTimedAyah(index);
				}
				else if("psrsm".equals(actionCommand)){
					if(startTime==0)
						return;
					if(isHitPlayPaused){
						isHitPlayPaused=false;
						hitPlayResumedTime=System.currentTimeMillis();
					}else{
						isHitPlayPaused=true;
						hitPlayPausedTime=System.currentTimeMillis();
					}
				}
				else if("next".equals(actionCommand)){
					if(animationType==Animation_type.Edit_Timed_Ayah){
						
						editTimedAyahIndex=index;
						editWordIndexOfTimedAyah=0;
						
						if(timedAyahs.size()==editTimedAyahIndex){
							Ayah ayah=timedAyahs.get(editTimedAyahIndex-1).getAyah().getNextAyah();
							if(ayah!=null){
								timedAyahs.add(new TimedAyah(ayah));
							}else{
								JOptionPane.showMessageDialog(getParent(), "Ayah is Null","ERROR",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						
						
						JOptionPane.showMessageDialog(hitFileEditorDialog.getParent(), "Press Enter Key"
								+ " To Start The Timing For NEXT ayah");
						hitFileEditorDialog.updateComboBox();
						hitFileEditorDialog.setComboBoxSelectedIndex(editTimedAyahIndex);
						hitFileEditorDialog.updateList();
						SelectionPanel.setSelectionIndex(timedAyahs.get(index).getAyah());
						
						requestFocus();
						
					}else{
						if(index==hitFileEditorDialog.getAyahComboboxItemCount()){
							JOptionPane.showMessageDialog(hitFileEditorDialog.getParent(), "Reached To Last.");
							return;
						}
						else{
							hitFileEditorDialog.setComboBoxSelectedIndex(index);
						}
						resetHitTimesToNewTimedAyah(index);
					}
				}
				else if("playContinuous".equals(actionCommand)){
					if(animationType==Animation_type.Timed_Animation_Continuous){
						//toggling
						animationType=Animation_type.Timed_Animation;
					}else{
						animationType=Animation_type.Timed_Animation_Continuous;
						resetHitTimesToNewTimedAyah(index);
					}
				}
				else if("edit".equals(actionCommand)){
					animationType=Animation_type.Edit_Timed_Ayah;
					
					editTimedAyahIndex=index;
					editWordIndexOfTimedAyah=0;
					
					hitFileEditorDialog.updateList();
					SelectionPanel.setSelectionIndex(timedAyahs.get(index).getAyah());
					
					requestFocus();
				}
				else if("new".equals(actionCommand)){
					editTimedAyahIndex=0;
					editWordIndexOfTimedAyah=0;
					
					animationType=Animation_type.Edit_Timed_Ayah;
					int surahIndex=index/1000 - 1;
					int ayahIndex=index%1000 - 1;
					Ayah ayah=new Ayah(surahIndex,ayahIndex);
					
					timedAyahs.clear();
					timedAyahs.add(new TimedAyah(ayah));
					hitFileEditorDialog.updateComboBox();
					
					SelectionPanel.setSelectionIndex(ayah);
					
					requestFocus();
				}
			}
		});
		
		isHitPlayPaused=false;
		hitPlayPausedTime=hitPlayResumedTime=0;
		//subTextDialog=new SubTextDialog(300,200,500,100);
	}

	private void autoRun() {
		if(wordHitTimes.size()>displayedHitWords)
			//for animationType=Simple_Animation 
			//wordHitTimes.size()=0, displayed word=0
		{	
			if(isHitPlayPaused)
				return;
			
			long time=System.currentTimeMillis()-startTime;
			time-=(hitPlayResumedTime-hitPlayPausedTime);
			
			if(wordHitTimes.get(displayedHitWords)<=time)
			{
				String hitString=hitStrings.get(displayedHitWords);
				if(hitString.length()>0 && hitString.charAt(0)=='%'){
					//TODO
					String commStrings[]=hitString.split("%");
					
					for(int i=1;i<commStrings.length;i++){
						String commandString=commStrings[i];
						//remove command catching
						if(commandString.startsWith(HitString.COMMAND_STRNG_REMOVE_LAST_WORDS)){
							int value=Integer.parseInt(
									commandString.substring(commandString.indexOf('=')+1).trim());
							removeLastAddedWordsFromSentence(value);
						}
						//subtext command catching
						else if(commandString.startsWith(HitString.COMMAND_STRNG_SUBTEXT)){//subTextOn
							String subtext=commandString.substring(commandString.indexOf('=')+1);
							if(subtext.length()>0){
								if(subTextDialog==null){
									Rectangle rectDisplayPanel=this.getParent().getParent().getBounds();
									
									subTextDialog=new SubTextDialog
										(rectDisplayPanel.x, getSize().height, rectDisplayPanel.width,100);
									
								}
								if(!subTextDialog.isVisible()){
									subTextDialog.setVisible(true);
								}
								subTextDialog.setText(subtext);
							}else if(subTextDialog.isVisible()){
								subTextDialog.setVisible(false);
								subTextDialog=null;
							}
						}
					}
					
				}
				
				addNextWordToSentence();
				
				hitFileEditorDialog.setListItemSelectedIndex(displayedHitWords);
				displayedHitWords++;
			}
			hitFileEditorDialog.setTimeLabel(time);
		}
		
	}
	
	public void showHitFileEditorDialog(){
		hitFileEditorDialog.setVisible(true);
	}
	
	private void resetHitTimesToNewTimedAyah(int index) {

		wordHitTimes=timedAyahs.get(index).getWordHitTimes();
		hitStrings=timedAyahs.get(index).getHitStrings();
		setAyah(timedAyahs.get(index).getAyah());

		displayedHitWords=0;
		//injuryTime=0;
		hitPlayPausedTime=hitPlayResumedTime=0;
		
		startTime=System.currentTimeMillis();
		if(!autoDisplayTimer.isRunning()){
			autoDisplayTimer.start();
		}
	}


	public static ArrayList<TimedAyah> getTimedAyahs(){
		return timedAyahs;
	}


	public void setAnimationStateSimple() {
		animationType=Animation_type.Simple_Animation;
	}
	
	protected void addListeners() {
		//for getting info box
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getY()<currentDisplayPoint.y+height)
				{
					Point focused=e.getPoint();
					focused.y+=scrollY;
					
					if( !focusCheckingThread.isAlive())
					{
						focusCheckingThread=new Thread(new FocusCheckRunnable(focused));
						focusCheckingThread.start();
						
						//System.out.println(Thread.activeCount());
					}
				}
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(e.getX()>=startPoint.x-20)
				{
					scrollY=(e.getY()*currentDisplayPoint.y)/getBounds().height;
				}
			}
		});
		
		//for scrolling
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				//System.out.println(e.getWheelRotation());
				int delta=e.getWheelRotation();
				
				if((delta <0 && (scrollY+delta*scrollDelta)>=0) || 
						(delta>0 && (scrollY+delta*scrollDelta)<currentDisplayPoint.y))
					scrollY+=delta*scrollDelta;
				
				//System.out.println(scrollY);
				
			}
		});
		
		//for requesting focus
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				animationRunning=paused;
				paused=!paused;
				userInputListener.pauseStateChanged(paused);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
				
				//System.out.println("Entered");
				//setCursor(new Cursor(Cursor.HAND_CURSOR));
				//System.out.println(getCursor().getName());
			}
		});
		
		//keyBoard input
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				int keyCode=e.getKeyCode();
				//animate next word and store time for the word appearance : <Enter>
				if(keyCode==KeyEvent.VK_ENTER && 
						animationType==Animation_type.Edit_Timed_Ayah)
				{
					
					backspacePressedCount=0;
					
					if(timedAyahs.size()==editTimedAyahIndex){//go to new ayah
						Ayah ayah=timedAyahs.get(editTimedAyahIndex-1).getAyah().getNextAyah();
						if(ayah!=null){
							timedAyahs.add(new TimedAyah(ayah));
							hitFileEditorDialog.updateComboBox();
						}else{
							JOptionPane.showMessageDialog(getParent(), "Ayah is Null","ERROR",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					
					hitFileEditorDialog.setComboBoxSelectedIndex(editTimedAyahIndex);
					int hitTime=0;
					
					if(editWordIndexOfTimedAyah==0)//firstWord
					{
						//start reciter
						setAyah(timedAyahs.get(editTimedAyahIndex).getAyah());
						//
						
						startTime=System.currentTimeMillis();
						hitTime=0;
					}
					
					else{
						hitTime=(int) (System.currentTimeMillis()-startTime);
					}
					
					if(editWordIndexOfTimedAyah<timedAyahs.get(editTimedAyahIndex).getTotalAddedWords()){
						timedAyahs.get(editTimedAyahIndex).setWordTime(editWordIndexOfTimedAyah, hitTime);
					}else{
						timedAyahs.get(editTimedAyahIndex).addWordHitTime(hitTime);
					}
					
					hitFileEditorDialog.setTimeLabel(hitTime);
					addNextWordToSentence();

					hitFileEditorDialog.updateList();
					hitFileEditorDialog.setListItemSelectedIndex(editWordIndexOfTimedAyah);
					
					editWordIndexOfTimedAyah++;
					
					if(ayahDisplayFinished){
						editTimedAyahIndex++;
						editWordIndexOfTimedAyah=0;
					}
				}
				
				else if(keyCode==KeyEvent.VK_BACK_SPACE
						&& animationType==Animation_type.Edit_Timed_Ayah){
					backspacePressedCount++;
					
					timedAyahs.get(editTimedAyahIndex).addWordHitTime(-1);
					timedAyahs.get(editTimedAyahIndex).setHitString(editWordIndexOfTimedAyah, 
							"%"+HitString.COMMAND_STRNG_REMOVE_LAST_WORDS+"="+Integer.toString(backspacePressedCount));
					
					hitFileEditorDialog.updateList();
					
					removeLastAddedWordsFromSentence(1);
				}
				/*if(keyCode==KeyEvent.VK_ENTER)
				{
					animationRunning=paused;
					paused=!paused;
					userInputListener.pauseStateChanged(paused);
				}*/
				//pause ,unpause
				else if(keyCode==KeyEvent.VK_SHIFT){
					animationRunning=paused;
					paused=!paused;
					userInputListener.pauseStateChanged(paused);
				}
				//scroll up
				else if(keyCode==KeyEvent.VK_UP && (scrollY-scrollDelta)>=0)
				{
					scrollY-=scrollDelta;
				}
				//scroll Down
				else if(keyCode==KeyEvent.VK_DOWN && (scrollY+scrollDelta)<currentDisplayPoint.y)
				{
					scrollY+=scrollDelta;
				}
				//speed up
				else if(keyCode==KeyEvent.VK_RIGHT)//-->
				{
					setDisplayAction("speedDown");
					userInputListener.speedChanged(false);
				}
				//speed down
				else if(keyCode==KeyEvent.VK_LEFT)//<--
				{
					setDisplayAction("speedUp");
					userInputListener.speedChanged(true);
				}
				
				else if(keyCode==KeyEvent.VK_SPACE)
				{
					setAnimationStateSimple();
					if(runningAyah==null){
						runningAyah=SelectionPanel.getSelectedAyah();
					}
					Ayah nextAyah=runningAyah.getNextAyah();
					
					if(nextAyah!=null)
					{
						setAyah(nextAyah);
					}
				}
			}
		});
	}

	/*
	 * Called when an ayaah printing finished
	 */
	@Override
	protected void goToNextStep()
	{
		//System.out.println("goToNextStep()");
		if(Reciter.isAlive() || paused){
			return;
		}
		if(animationType==Animation_type.Timed_Animation_Continuous){
			int selectedIndex=hitFileEditorDialog.getComboBoxSelectedIndex();
			if(selectedIndex<timedAyahs.size()-1){
				resetHitTimesToNewTimedAyah(selectedIndex+1);
				hitFileEditorDialog.setComboBoxSelectedIndex(selectedIndex+1);
			}
			else if(autoDisplayTimer.isRunning()){
				autoDisplayTimer.stop();
			}
		}
		else if(animationType==Animation_type.Timed_Animation){
			if(autoDisplayTimer.isRunning()){
				autoDisplayTimer.stop();
			}
			return;
		}
		//else simple animation, as autoTimer is off in edit
		//time type this func is not called then
		else if(animationType!=Animation_type.Edit_Timed_Ayah && AnimationPreferences.continuous){
		//System.out.println(runningAyah.toDetailedString());
			Ayah nextAyah=runningAyah.getNextAyah();
			
			if(nextAyah!=null)
			{
				setAyah(nextAyah);
			}
		}
		//else do nothing
		
	}
	
	public void setDisplayAction(String action)//toolbar buttons
	{
		if(action.equals("pause"))
		{
			animationRunning=false;
			paused=true;
		}
		else if(action.equals("resume"))
		{
			animationRunning=true;
			paused=false;
		}
		
		else if(action.equals("restart"))
		{
			animationType=Animation_type.Simple_Animation;
			if(runningAyah!=null){
				
				resetDisplay();
				paused=false;
				if(audioPrefs.isAudioON()){
					
					if(Reciter.isAlive()){
						Reciter.stop();
					}
					Thread fstreamLoader=new Thread(new Runnable() {
						
						@Override
						public void run() {
							audioFileInputStream=Reciter.getPlayFileInputStream(runningAyah);
							new Reciter(audioFileInputStream).reciteAyah();
						}
					});
					fstreamLoader.start();				
				}
			}
		}
		
		else if(action.equals("speedUp"))
		{
			//decrease deltaPixel
			deltaPixel+=DeltaPixelProperty.delta;
			if(deltaPixel>DeltaPixelProperty.maxDeltaPixel)
				deltaPixel=DeltaPixelProperty.maxDeltaPixel;
			
		}
		else if(action.equals("speedDown"))
		{
			//increase deltaPixel
			deltaPixel-=DeltaPixelProperty.delta;
			if(deltaPixel<DeltaPixelProperty.minDeltaPixel)
				deltaPixel=DeltaPixelProperty.minDeltaPixel;
			
		}
	}
	
	public void setAudioAction(String actionCommand)
	{
		if(!Reciter.isAlive()){
			return;
		}
		
		if(actionCommand.equals("stop")){
			Reciter.stop();
		}
		else if(actionCommand.equals("pause")){
			Reciter.pause();
		}
		else if(actionCommand.equals("resume")){
			Reciter.resume();
		}
	}
	
	//TODO
	public void setAyah(Ayah ayah)//go button,next,previous,restart clicked
	{
		Thread fstreamLoader=null;
		
		if(audioPrefs.isAudioON())
		{
			setAudioAction("stop");
			fstreamLoader=new Thread(new Runnable() {
				
				@Override
				public void run() {
					audioFileInputStream=Reciter.getPlayFileInputStream(runningAyah);
				}
			});
			fstreamLoader.start();
		}
		
		runningAyah=ayah;
		SelectionPanel.setSelectionIndex(ayah);
		//System.out.println("to display : "+ayah.toDetailedString());
		displayText=AllTextsContainer.arabicText.getQuranText(ayah);
		//System.out.println(displayText.length());
		setInfoOfWords(ayah);
		TranslationPanel.setTranslationText(ayah);
		//TafsirPanel.setTafsirText(ayah);	
		
		resetDisplay();//invoke to update screen with new ayah
		
		if(paused)
		{
			paused=false;
			userInputListener.pauseStateChanged(paused);
		}
		
		//TODO need to be fixed and uncommented
		/*if(ayah.ayahIndex==0 && !(ayah.suraIndex==0 || ayah.suraIndex==8)){
			//not sura fatiha and sura atTawba
			setFirstSentence(bismillah);
		}*/
		
		if(fstreamLoader!=null){
			//freeze but don't lag
			//if simple animation, then if it is continuous
			//or it is hitFile editing or playing stage
			//if(AnimationPreferences.continuous || animationType!=Animation_type.Simple_Animation){	
			//}
			try{
				fstreamLoader.join();
				new Reciter(audioFileInputStream).reciteAyah();
			}catch(InterruptedException ie){};
		}
		//repaint();
	}
	
	//declared in Animation.java
	/*public void setPreferences(Preferences pref) {
		
		this.preferences=pref;
	}*/
	
	public void refresh()
	{
		repaint();
	}
	
	public int getDeltaPixel()
	{
		return deltaPixel;
	}
	
	public boolean isAnimationRunning()
	{
		return animationRunning;
	}
	
	private void setInfoOfWords(Ayah ayah)
	{
		//index of first ayah of the sura in all ayah sets
		int indexOfFirstAyah=SurahInformationContainer.totalAyahsUpto[ayah.suraIndex];
		int indexOfSelectedAyah=indexOfFirstAyah+ayah.ayahIndex;
		//address in the info list of that selected ayah
		if(ayah.ayahIndex==-1)
			indexOfSelectedAyah=0;
		//index of the first word of this ayah
		int indxOfFirstWord=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah);
		//index of the first word of the next ayah
		int indxOfFWNextA=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah+1);
		
		infoOfWord = WordInfoLoader.getWordInfos(indxOfFirstWord,indxOfFWNextA-1);
		wordImages = new ImageLoader(false).getImagesSameSurah(indxOfFirstWord, indxOfFWNextA-1, 
				animPreferences.isDownloadImageEnabled());		

	}
	
	
	public void setUserInputListener(UserInputListener listener)
	{
		this.userInputListener=listener;
	}
	
	public void startAnimationTimer()
	{
		if(!timer.isRunning())
			timer.start();
	}
	
	public AudioPreferences getAudioPref()
	{
		return new AudioPreferences("audio.preferences",isAnimAudioOn,audioSIndex,
				SelectionPanel.getSelectedAyah());
	}
	
	public void updateAudioPref()
	{
		//audioPrefs=PreferencesDialog.getAudioPref();
		isAnimAudioOn=audioPrefs.isAudioON();
		audioSIndex=audioPrefs.getAudioSourceIndex();
		
		String url=AudioPreferencesPanel.getAudioSourceLink(audioSIndex);
		if(url!=Reciter.DefaultURL)
		{
			Reciter.setDefaultUrl(url);
			//System.out.println("new reciter url set");
		}
	}
	
	public static boolean loadHitFile(File file)
	{
		boolean success=false;
		BufferedReader reader=null;
    	try {
    		reader=new BufferedReader(new FileReader(file));
    		String text;
    		
    		timedAyahs.clear();

    		int totalAyahs=0;
    		while((text=reader.readLine())!=null)
    		{
    			//System.out.println(text);
    			if(text.length()==0)
    				continue;
    			
    			if(text.charAt(0)==HitString.COMMENT_START)
    				continue;
    			
    			if(text.charAt(0)==HitString.AyaahNum_START){
    				String parts[]=text.substring(1).split(":");
    				try{
    					int surahNum=Integer.parseInt(parts[0].trim());
    					int ayaahNum=Integer.parseInt(parts[1].trim());
    					
    					timedAyahs.add(new TimedAyah(new Ayah(surahNum-1,ayaahNum-1)));
						totalAyahs++;
    				}
    				catch(NumberFormatException | ArrayIndexOutOfBoundsException nae){
        				return false;
        			}
    			}
    			
    			else{
    				String parts[]=text.split(" ");
    				
    				try{
    					int k=Integer.parseInt(parts[0].trim());
						timedAyahs.get(totalAyahs-1).addWordHitTime(k);
    				}
    				catch(NumberFormatException | IndexOutOfBoundsException nie){
        				return false;
        			}
    				
    				if(parts.length>1 && parts[1].length()>0){
    					int index=timedAyahs.get(totalAyahs-1).getTotalAddedWords()-1;
    					timedAyahs.get(totalAyahs-1).setHitString(index, text.substring(parts[0].length()+1));
    				}
    			}
    		}
    	    
    		success=true;
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		//JOptionPane.showMessageDialog(getParent(), ex.getMessage());

        } finally
        {
        	try
        	{
        		if(reader!=null)
        			reader.close();
        	}catch(IOException io)
        	{
        		io.printStackTrace();
        	}
        }
    	
    	return success;
	}

	/**
	 * 
	 */
	public void updateSubtextPref() {
		if(subTextDialog!=null){
			subTextDialog.updateFontFromPref();;
		}
	}

	public SubtextPreferences getSubtextPref() {
		if(subTextDialog!=null){
			return subTextDialog.getSubtextPref();
		}
		return null;
	}
}
