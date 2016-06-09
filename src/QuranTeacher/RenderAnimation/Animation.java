/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderAnimation;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import QuranTeacher.Dialogs.PreferencesDialog;
import QuranTeacher.Preferences.AdvancedAnimPref;
import QuranTeacher.Preferences.AnimationPreferences;
import QuranTeacher.Preferences.WordByWordFontPref;
import QuranTeacher.WordInformation.SegmentColors;
import QuranTeacher.WordInformation.WordInformation;
import QuranTeacher.Preferences.deltaPixelProperty;


public abstract class Animation extends JPanel {

	/**
	 * The main animation is created here
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	
	protected Point currentDisplayPoint, startPoint;
	protected AnimationPreferences animPreferences;//protected : to know from animPanel if download image is enabled
	private WordByWordFontPref wordByWordFontPref;
	protected int deltaPixel=deltaPixelProperty.initialDeltaPixel;
	
	protected int lineStringWidth;//sentence width
	protected int height;
	
	//private String ExtraSpaceString="    ";//initial extra spacing is 4 spaces
	private static String spaceBetweenWords="    ";//minimum spaces between the words
	
	private int distanceCovered;
	private int wordsDisplayedSoFar;
	public static int mouseFocusedOn;//focused by mouse
	
	private static int lineHeight;//difference between lines
	private static int extraHeight;//fontmetrics extraHeight
	
	private int spaceWidth;
	
	protected int scrollY;//how much scrolled
	protected int scrollDelta;//measurement of scrolled in pixel
	protected int scrollbarPosY;
	
	protected boolean animationRunning;
	
	private Color bgColor,fgColor;
	
	private String[] words;//main text slits to multiple words
	private ArrayList<CoordinatedWord> coordinatedWords;
	
	private Font animFont;

	private FontMetrics fontMetrics;
	
	public static List<Rectangle>rectangles;//to draw borders around a word after it's been displayed
	
	protected final Timer timer;//animation timer
	
	protected int timeGapElapsed;//time total slept after one ayah display finished
	int restingTimeGap;
	
	private boolean showPopUpInfoBox;
	/*String bismillah="\u0628\u0650\u0633\u0652\u0645\u0650 "
			+ "\u0627\u0644\u0644\u0651\u064e\u0647\u0650 \u0627\u0644"
			+ "\u0631\u0651\u064e\u062d\u0652\u0645\u064e\u0670\u0646"
			+ "\u0650 \u0627\u0644\u0631\u0651\u064e\u062d\u0650\u064a\u0645\u0650";*/
			
	protected String displayText="";//not set to null,to avoid null pointer exception
	protected List<WordInformation> infoOfWord;
	protected Image[] wordImages;

	protected boolean ayahDisplayFinished;
	
	private Color wbwMeaningColor;//word meaning font color
	private Color wbwTrnslitrtionColor;
	private Font wbwFont;//word meaning font
	private FontMetrics wbwFontMetrics;//for word meaning font

	private boolean downloadImagesEnabled;
	private boolean enableAutoScroll;
	private static boolean animatePartialWord;
	public enum Animation_type{Simple_Animation,Timed_Animation, Timed_Animation_Continuous,
		Edit_Timed_Ayah};
	
	protected Animation_type animationType;

	private AdvancedAnimPref advancedAnimPref;

	private final int LineHeightFactor=2;

	
	public Animation() {
		
		startPoint=new Point(0,100);
		currentDisplayPoint=new Point(0,100);
		
		words=displayText.split(" ");
		
		coordinatedWords=new ArrayList<>();
		
		animationRunning=false;
		ayahDisplayFinished=false;
		
		animPreferences=PreferencesDialog.getAnimPreferences();
		wordByWordFontPref=PreferencesDialog.getWbWFontPref();
		
		animatePartialWord=true;
		advancedAnimPref=new AdvancedAnimPref("advancedAnim.preferences");
		if(advancedAnimPref.setPrefFromFile()){
			animatePartialWord=advancedAnimPref.isHidePartial();
			int wordGap=advancedAnimPref.getWordGap();
			//initially spaceBetweenWords.length()=4
			for(int i=spaceBetweenWords.length();i<wordGap;i++){
				spaceBetweenWords+=" ";
			}
		}
		
		updatAnimPreferences();
		updateWbWFontPref();
		
		scrollY=0;
		scrollDelta=lineHeight/4;
		
		rectangles=new ArrayList<>();
		infoOfWord=new ArrayList<>();
		//wordImages=new ArrayList<>();
		
		mouseFocusedOn=-1;
		
		timer=new Timer(1000/20, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
		//timer.start();
		//timer is started from mainFrame()
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(bgColor);
		
		g.setColor(fgColor.darker().darker());
		drawFullSentence(g);
		
		g.setColor(fgColor);
		//g.setFont(font);
		if(animationRunning)
			drawAnimation(g);
		else
			drawFixedDisplay(g);
	}

	//############################################################################
	protected void step() {
		if(!animationRunning)//paused or ayah Display Finished
		{
			repaint();
			if(ayahDisplayFinished){
				if(timeGapElapsed>=restingTimeGap)
					goToNextStep();
				else
					timeGapElapsed++;
			}
			return;
		}else if(ayahDisplayFinished){//animation running but ayah display finished
			
			if(distanceCovered<lineStringWidth+50)//pass times to last red boundary disappears
				distanceCovered+=deltaPixel;//others wise last red boundary can't be seen
			else
			{
				animationRunning=false;
				timeGapElapsed=0;
			}
			
			repaint();
			return;
		}
		
		if(distanceCovered<lineStringWidth)
		{
			distanceCovered+=deltaPixel;
		}
		else{//a word animation finished as distance covered last word width
			if(animationType==Animation_type.Simple_Animation){//not in autoDisplayMode
				addNextWordToSentence();
			}
		}
		repaint();
	}
	//###########################################################################
		
	public void addNextWordToSentence()// TODO
	{
		if (ayahDisplayFinished) {
			return;
		}
		
		int wordsSize=coordinatedWords.size();
		wordsDisplayedSoFar++;
		CoordinatedWord word=coordinatedWords.get(wordsDisplayedSoFar-1);
		int y=startPoint.y+ lineHeight * LineHeightFactor * word.getLineIndex();
		if(y>currentDisplayPoint.y){//words displayed in new line
			currentDisplayPoint.y=y;
			lineStringWidth=distanceCovered=0;
		}
		lineStringWidth+=word.getWordWidth();
		distanceCovered+=word.getExtraWidth();
		
		
		if (wordsDisplayedSoFar == wordsSize) {
				// System.out.println("Ayah display finished");
				ayahDisplayFinished = true;// all words has been displayed
				// animationRunning=false;
		}
		
	}

	private boolean isWaqf(String word) {
		int k=word.charAt(0);
		if((k>='\u0610' && k<='\u0615') || (k>='\u06D6' && k<='\u06ED'))
			return true;
		return false;
	}
		
	protected abstract void goToNextStep();

	private void drawFullSentence(Graphics g){
		if(ayahDisplayFinished)
			return;
		
		updateWordStartPoint();
		g.setFont(animFont);
		
		for(int wordIndex=0;wordIndex < coordinatedWords.size();wordIndex++){
			CoordinatedWord word=coordinatedWords.get(wordIndex);
			g.drawString(word.getWord(), word.getStartX(),startPoint.y+LineHeightFactor*lineHeight*word.getLineIndex()-scrollY);
		}
		drawMeaningOfWord(g,true);
	}
	
	private void drawAnimation(Graphics g)
	{
		//updateWordStartPoint();
		g.setFont(animFont);
		
		for(int wordIndex=0;wordIndex < wordsDisplayedSoFar;wordIndex++){
			CoordinatedWord word=coordinatedWords.get(wordIndex);
			g.drawString(word.getWord(), word.getStartX(),startPoint.y+LineHeightFactor*lineHeight*word.getLineIndex()-scrollY);
		}
		//draw scrollbar in right position
		scrollbarPosY=(int) ((getBounds().getHeight()*scrollY)/currentDisplayPoint.y);
			
		g.fillRect(startPoint.x+5, scrollbarPosY, 5, 30);
		
		//WORD HIDING under a rectangle
		if(animatePartialWord)
			HidePartial(g);
		
		drawMeaningOfWord(g,false);
		
		g.setColor(Color.RED);
		if(wordsDisplayedSoFar>0)
		{
			//System.out.println("total rectangles :"+rectangles.size());
			Rectangle rect;
			
			if(showPopUpInfoBox)
			{
				rect=rectangles.get(wordsDisplayedSoFar-1);
				g.drawRect(rect.x,rect.y-scrollY,rect.width,rect.height);
				drawInfoBox(g, false);
			}
			
			if(mouseFocusedOn!=-1)
			{
				g.setColor(Color.GREEN);
				rect=rectangles.get(mouseFocusedOn);
				g.drawRect(rect.x,rect.y-scrollY,rect.width,rect.height);
				
				drawInfoBox(g, true);
			}
		}
		
		if(enableAutoScroll && 
				currentDisplayPoint.y-scrollY+lineHeight+150>getBounds().height)
		{
			scrollY+=scrollDelta;
		}
	}

	public void updateWordStartPoint() {
		startPoint.x=this.getBounds().width-10;
	}
	
	
	private void HidePartial(Graphics g) {
		if(distanceCovered>=lineStringWidth)
			return;
		
		g.setColor(bgColor);
		//g.setColor(new Color(240, 240, 200));
		//startX -5 and width +5 added for TWA effect--> displaying of the letter TWA
		//height+=extraHeight for displaying ZER
		//all is the fontmetrics problem, unsolved
		g.fillRect(startPoint.x-lineStringWidth-10, 
				currentDisplayPoint.y-height-scrollY-extraHeight/4, 
				lineStringWidth-distanceCovered+10, height+extraHeight);
	}

	private void drawFixedDisplay(Graphics g) 
	{
		g.setFont(animFont);
		//drawAyah
		for(int wordIndex=0;wordIndex < wordsDisplayedSoFar;wordIndex++){
			CoordinatedWord word=coordinatedWords.get(wordIndex);
			g.drawString(word.getWord(), word.getStartX(),startPoint.y+LineHeightFactor*lineHeight*word.getLineIndex()-scrollY);
		}
		
		//draw scrollbar

		int scrollingPosY=(int) ((getBounds().getHeight()*scrollY)/currentDisplayPoint.y);
			
		g.fillRect(startPoint.x+5, scrollingPosY, 5, 30);
		
		//WORD HIDING under a rectangle
		if(animatePartialWord)
			HidePartial(g);
		
		drawMeaningOfWord(g,false);
		
		if(mouseFocusedOn!=-1)
		{
			Rectangle rect;
			g.setColor(Color.GREEN);
			rect=rectangles.get(mouseFocusedOn);
			g.drawRect(rect.x,rect.y-scrollY,rect.width,rect.height);
			
			drawInfoBox(g, true);
		}
	}
	
	private void drawInfoBox(Graphics g,boolean fromMouseFocus)
	{
		int x,y,boxWidth,boxHeight;
		
		boxWidth=200;
		boxHeight=350;
		Rectangle rect;
		
		int index;
		
		if(fromMouseFocus)//user invoked
			index=mouseFocusedOn;
		else
			index=wordsDisplayedSoFar-1;
		
		rect=rectangles.get(index);
		x=rect.x+rect.width/2-boxWidth/2;
		y=rect.y+2*height;
		
		if(x+boxWidth+10>startPoint.x)
			x=startPoint.x-boxWidth-10;
		
		else if(x<30)
			x=30;
		
		//infoBox pad
		g.setColor(Color.WHITE);
		g.fillRect(x, y-scrollY, boxWidth, boxHeight);
		
		//infoBox Boundary
		if(fromMouseFocus)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		
		g.drawRect(x, y-scrollY,boxWidth, boxHeight);
		g.drawLine(rect.x+rect.width/2, rect.y+rect.height-scrollY,
					x+boxWidth/2, y-scrollY);
		
		//infoBox text
		
		//g.drawString("This is an info", x+10,y+30-scrollY);
		if(infoOfWord.size()>index)
		{
			//images
			
			//image
			g.setFont(wbwFont.deriveFont(Font.BOLD));
			g.setColor(Color.BLACK);
			int writeY=y+30-scrollY;
			
			Image img=wordImages[index];
			if(img!=null)
			{
				g.drawImage(img, x+10, writeY, this);
				writeY+=img.getHeight(this);
			}
			else
			{
				writeY=drawFixedString(g, "<image not found. See help for more info.>",
						boxWidth-10, x+10,writeY);	
				
				return;
			}

			//partsOf speeches
			String[] details=infoOfWord.get(index).partsOfSpeechDetails;
			String[] segColors=infoOfWord.get(index).segmentColors;
			
			writeY+=wbwFontMetrics.getHeight()*2;
			for(int i=0;i<segColors.length;i++)
			{
				
				g.setColor(SegmentColors.getColor(segColors[i]));								
				writeY=drawFixedString(g, details[i],
						boxWidth-10, x+10,writeY);
				writeY+=wbwFontMetrics.getHeight();
			}
		}
		else
		{
			drawFixedString(g, "A Problem Occured. "
					+ "If it is not (37:130) please inform the developers "
					+ "mentioning where (in which sura"
					+ " and ayah) did it happen.",
					boxWidth-10, x+10,y+30-scrollY);
		}
	}
	
	/*only for wbw fontmetrics
	 * 
	 */
	private int drawFixedString(Graphics g,String text,
			int gapWidth,int leftX,int LeftY)
	{
		//as this is called after g.setColor() and setFont,so excluded
		String[] words=text.split(" ");
		List<String>sentences=new ArrayList<>();
		
		String aSentence=words[0];
		for(int i=1;i<words.length;i++)
		{
			if(wbwFontMetrics.stringWidth(aSentence+" "+words[i])>gapWidth)
			{
				sentences.add(aSentence);
				aSentence=words[i];
			}
			else
			{
				aSentence+=" "+words[i];
			}
		}
		sentences.add(aSentence);
		
		int j=LeftY;

		for(int i=0;i<sentences.size();i++)
		{
			g.drawString(sentences.get(i), leftX, j);
			j+=wbwFontMetrics.getHeight();
		}
		
		return j;
	}
	
	
	private void drawMeaningOfWord(Graphics g, boolean isFullSentence)
	{
		/*if(rectangles.size()==0)
			return;*/
		
		if(infoOfWord==null)
			return;
		
		g.setFont(wbwFont);
		
		int writeX,writeY;
		Rectangle rect;
		
		for(int i=0;i<wordsDisplayedSoFar;i++)
		{
			rect=rectangles.get(i);
			
			if(infoOfWord.size()>i)
			{
				writeX=rect.x;
				writeY=rect.y+rect.height+extraHeight;
				
				g.setColor(wbwTrnslitrtionColor);
				g.drawString("{"+infoOfWord.get(i).transLiteration+"}", writeX, writeY-scrollY);
				g.setColor(wbwMeaningColor);
				g.drawString(infoOfWord.get(i).meaning, writeX, writeY+22-scrollY);
			}
			else
			{
				problemOccured(i);
			}
		}
		
		if(isFullSentence){
			for(int i=wordsDisplayedSoFar;i<coordinatedWords.size();i++)
			{
				rect=rectangles.get(i);
				
				if(infoOfWord.size()>i)
				{
					writeX=rect.x;
					writeY=rect.y+rect.height+extraHeight;
					
					g.setColor(wbwTrnslitrtionColor.darker());
					g.drawString("{"+infoOfWord.get(i).transLiteration+"}", writeX, writeY-scrollY);
					g.setColor(wbwMeaningColor.darker());
					g.drawString(infoOfWord.get(i).meaning, writeX, writeY+22-scrollY);
				}
				else
				{
					problemOccured(i);
				}
			}
		}
	}

	protected void resetDisplay()
	{
		distanceCovered=0;
		lineStringWidth=0;
		
		wordsDisplayedSoFar=0;
		
		currentDisplayPoint.y=startPoint.y;
		
		//lineHeight=getFontMetrics(animFont).getHeight()+20;
		scrollY=0;
		mouseFocusedOn=-1;
		
		animationRunning=true;
		ayahDisplayFinished=false;
		
		//mouseFocusedOn=-1;//unimportant
		
		setNewAyahFixedDisplay();
	}
	
	private void setNewAyahFixedDisplay(){
		//TODO update
		coordinatedWords.clear();
		rectangles.clear();
		fontMetrics=getFontMetrics(animFont);
		
		words=displayText.split(" ");
		
		Point displayPoint=new Point(0,startPoint.y);
		int wordsLength=words.length;
		String pickedWordToDisplay;//word+ waqfs

		//String workingLineString="";
		int lineStringWidth=0;
		int validWordsSoFar=0;//word+waqfs
		int currentLineIndex=0;
		
		for(int wordIndex=0;wordIndex<wordsLength;wordIndex++){
			pickedWordToDisplay = words[wordIndex];
			int wordWidth=fontMetrics.stringWidth(pickedWordToDisplay);
			int waqfWidth=0;
			
			while(wordIndex<wordsLength-1 && isWaqf(words[wordIndex+1])){
				wordIndex++;
				pickedWordToDisplay += spaceBetweenWords + words[wordIndex];
				waqfWidth+=fontMetrics.stringWidth(spaceBetweenWords+words[wordIndex]);
			}
			
			int extraStringWidth=getExtraStringWidth(validWordsSoFar,pickedWordToDisplay);
			String extraSpaceString="";
			int k=0;
			while(k<extraStringWidth)
			{
				extraSpaceString+=" ";
				k+=spaceWidth;
			}
			
			pickedWordToDisplay = spaceBetweenWords + extraSpaceString
					+ pickedWordToDisplay;
			
			int wordStringWidth = fontMetrics.stringWidth(pickedWordToDisplay);
			
			if (lineStringWidth + wordStringWidth >= getBounds().width - 30)
			{
				lineStringWidth=0;
				displayPoint.y += lineHeight * LineHeightFactor;// go to next line
				currentLineIndex++;
			}
			lineStringWidth += wordStringWidth;
			
			int x=startPoint.x - lineStringWidth + waqfWidth;
			int y=displayPoint.y - height;
			coordinatedWords.add(new CoordinatedWord(pickedWordToDisplay,
					validWordsSoFar,wordStringWidth,
					fontMetrics.stringWidth(spaceBetweenWords)+extraStringWidth));
			validWordsSoFar++;
			//experimental calculation, x = (x - width) for coordinated word
			coordinatedWords.get(validWordsSoFar-1).setCoordinate(x-waqfWidth, currentLineIndex);
			rectangles.add(new Rectangle(x,y, wordWidth,height + 15));
		}
	}
	/*
	 * to display the meanings appearing under each arabic word
	 */
	private int getExtraStringWidth(int wordId, String pickedWord) 
	{
		if(infoOfWord.size()>(wordId))
		{
			int stringWidth1=wbwFontMetrics.stringWidth
					(infoOfWord.get(wordId).transLiteration);
			int stringWidth2=wbwFontMetrics.stringWidth
					(infoOfWord.get(wordId).meaning);
			
			int extraStringWidth = 
					Math.max(stringWidth1,stringWidth2) - fontMetrics.stringWidth(pickedWord);				
			return extraStringWidth;
			//System.out.println(words[wordIndexToDisplay]);
			/**/
		}
		return -1;
	}

	protected void setFirstSentence(String sentence)
	{//reset display is called before calling this function
		/*totalLinesDisplayed=1;
		currentLine=1;
		runningStringAtLine[1]=runningStringAtLine[0];//what has been initialized in resetDisplay()

		runningStringAtLine[0]=sentence;//=bismillah
		 */	
		//TODO find alternatives
		//currentDisplayPoint.y+=lineHeight* LineHeightFactor;
	}
	
	public void updatAnimPreferences()
	{
		//animPreferences=PreferencesDialog.getAnimPreferences();
		
		animFont=animPreferences.getFont();
		bgColor=animPreferences.getBackGroundColor();
		fgColor=animPreferences.getForeGroundColor();
		restingTimeGap=animPreferences.getRestingTime();
		showPopUpInfoBox=animPreferences.isShowPopUpBox();
		downloadImagesEnabled=animPreferences.isDownloadImageEnabled();
		enableAutoScroll=animPreferences.isAutoScrollEnabled();
		
		fontMetrics=getFontMetrics(animFont);
		spaceWidth=fontMetrics.stringWidth(" ");
		height=fontMetrics.getHeight();
		
		lineHeight=Math.max(fontMetrics.getHeight()+20,advancedAnimPref.getLineGap());
		extraHeight=Math.max(fontMetrics.getDescent()+20,advancedAnimPref.getExtraHeight());
		
	}
	
	public void updateWbWFontPref()
	{
		//wordByWordFontPref=PreferencesDialog.getWbWFontPref();
		
		wbwFont=wordByWordFontPref.getFont();
		wbwTrnslitrtionColor=wordByWordFontPref.getBackGroundColor();
		wbwMeaningColor=wordByWordFontPref.getForeGroundColor();
		wbwFontMetrics=getFontMetrics(wbwFont);
	}
	
	public AnimationPreferences getAnimationPref()
	{
		return new AnimationPreferences(
				"animation.preferences",bgColor,fgColor,animFont,restingTimeGap,showPopUpInfoBox,
				downloadImagesEnabled, enableAutoScroll);
	}
	
	public WordByWordFontPref getWbWFontPref()
	{
		return new WordByWordFontPref(
				"wbwFont.preferences",wbwTrnslitrtionColor,wbwMeaningColor,wbwFont);
	}
	
	public AdvancedAnimPref getAdvancedAnimPref(){
		return new AdvancedAnimPref("advancedAnim.preferences",lineHeight,
				extraHeight,spaceBetweenWords.length(),animatePartialWord);
	}
	
	public static void setHidePartialWord(boolean value){
		animatePartialWord=value;
	}
	
	public static boolean isAnimatePartialWord(){
		return animatePartialWord;
	}
	//for font changing problem 
	public static boolean changeExtraHeight(boolean increase){
		if(increase){
			if(extraHeight<200){
				extraHeight++;
				return true;
			}
		}
		else{
			if(extraHeight>20){
				extraHeight--;
				return true;
			}
		}
		return false;
	}
	public static boolean changeLineHeight(boolean increase){
		if(increase){
			if(lineHeight<200){
				lineHeight++;
				return true;
			}
		}
		else{
			if(lineHeight>20){
				lineHeight--;
				return true;
			}
		}
		return false;
	}
	

	public static boolean changeWordGapValue(boolean increase) {
		if(increase){
			if(spaceBetweenWords.length()<40){
				spaceBetweenWords+=" ";
				return true;
			}
		}
		else{
			if(spaceBetweenWords.length()>4){
				spaceBetweenWords=spaceBetweenWords.substring(0, spaceBetweenWords.length()-1);
				return true;
			}
		}
		return false;
	}
	
	public static int getExtraHeight(){
		return extraHeight;
	}
	public static int getLineHeight(){
		return lineHeight;
	}
	
	public static int getWordGapValue() {
		return spaceBetweenWords.length();
	}
	
	private void problemOccured(int i) {
		System.err.println("Serious Problem-----------wordIndex: "+i);
		System.out.println("Rectangles :"+rectangles.size());
		System.out.println("Info words :"+infoOfWord.size());
		System.out.println("------------------");
		//System.exit(0);
	}
}