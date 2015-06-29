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
import QuranTeacher.Preferences.AnimationPreferences;
import QuranTeacher.Preferences.WordByWordFontPref;
import QuranTeacher.WordInformation.SegmentColors;
import QuranTeacher.WordInformation.WordInformation;
import QuranTeacher.Preferences.deltaPixelProperty;


public class Animation extends JPanel {

	/**
	 * The main animation is created here
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private final int maxSentences=30;
	
	protected Point currentDisplayPoint, startPoint;
	protected AnimationPreferences animPreferences;//protected : to know from animPanel if download image is enabled
	private WordByWordFontPref wordByWordFontPref;
	protected int deltaPixel=deltaPixelProperty.initialDeltaPixel;
	
	protected int width;//sentence width
	protected int height;
	
	private String ExtraSpaceString="    ";//initial extra spacing is 4 spaces
	private String spaceBetweenWords="    ";//minimum spaces between the words
	
	private int distanceCovered;
	private int wordIndexToDisplay;
	private int totalLinesDisplayed;
	private int currentLine;//current displaying line
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
	private String[] sentences;//main text splits to multiple sentences tracking word width
	
	private Font animFont;

	private FontMetrics fontMetrics;
	
	public static List<Rectangle>rectangles;//to draw borders around a word after it's been displayed
	
	protected final Timer timer;//animation timer
	
	protected int timeGapElapsed;//time total slept after one ayah display finished
	int restingTimeGap;
	
	private boolean isWaqf=false;
	private int totalWaqfFound;
	
	private boolean showPopUpInfoBox;
	/*String bismillah="\u0628\u0650\u0633\u0652\u0645\u0650 "
			+ "\u0627\u0644\u0644\u0651\u064e\u0647\u0650 \u0627\u0644"
			+ "\u0631\u0651\u064e\u062d\u0652\u0645\u064e\u0670\u0646"
			+ "\u0650 \u0627\u0644\u0631\u0651\u064e\u062d\u0650\u064a\u0645\u0650";*/
			
	protected String displayText="Go.";//not set to null,to avoid null pointer exception
	protected List<WordInformation> infoOfWord;
	protected Image[] wordImages;

	private boolean ayahDisplayFinished;
	
	private Color wbwMeaningColor;//word meaning font color
	private Color wbwTrnslitrtionColor;
	private Font wbwFont;//word meaning font
	private FontMetrics wbwFontMetrics;//for word meaning font

	private boolean downloadImagesEnabled;

	private static boolean animatePartialWord;

	
	public Animation() {
		
		startPoint=new Point(0,100);
		currentDisplayPoint=new Point(0,100);
		
		words=displayText.split(" ");
		
		sentences=new String[maxSentences];
		sentences[currentLine]=ExtraSpaceString+words[0];
		
		animationRunning=false;
		ayahDisplayFinished=false;
		animatePartialWord=true;
		
		animPreferences=PreferencesDialog.getAnimPreferences();
		wordByWordFontPref=PreferencesDialog.getWbWFontPref();
		
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
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(bgColor);
		g.setColor(fgColor);
		//g.setFont(font);
		if(animationRunning)
			drawAnimation(g);
		else
			drawFixedDisplay(g);
	}

	protected void step() {
		
		if(!animationRunning)//paused or ayah Display Finished
		{
			repaint();
			timeGapElapsed++;
			if(timeGapElapsed>=restingTimeGap)
				goToNextStep();
			return;
		}
		if(distanceCovered<width)
		{
			distanceCovered+=deltaPixel;
		}
		
		else//a word animation finished
		{
			if(!ayahDisplayFinished)//not all words yet been displayed
			{
				if(!isWaqf)
				{
					rectangles.add(new Rectangle
							(startPoint.x-width,currentDisplayPoint.y-height,
						fontMetrics.stringWidth(words[wordIndexToDisplay]),height+15));
					//System.out.println("Rectangles added for"+ words[wordIndexToDisplay]);
				}
			
				if(wordIndexToDisplay<words.length-1)//more words to display
				{
					wordIndexToDisplay++;
					//System.out.println(wordIndexToDisplay);
					String wordNextToDisplay=words[wordIndexToDisplay];
					
					//checking if waqf occured
					isWaqf=false;
					if(wordNextToDisplay.length()==1)
					{	
						//chrecters unicode achieved from: http://jrgraphix.net/research/unicode_blocks.php?block=12
						int k=wordNextToDisplay.charAt(0);
						if((k>='\u0610' && k<='\u0615') || (k>='\u06D6' && k<='\u06ED'))
						{
							isWaqf=true;
							totalWaqfFound++;
							//System.out.println(wordNextToDisplay);
						}
					}
					
					if(!isWaqf)//this word is not an waqf
					{
						calculateExtraSpace();
						//System.out.println("Extra space for :"+wordNextToDisplay);
					}
					
					int checkWidth=fontMetrics.stringWidth
							(spaceBetweenWords+ExtraSpaceString+wordNextToDisplay);
					
					if(distanceCovered+checkWidth>=getBounds().width-30)//word may cross display area bound
					{
						totalLinesDisplayed++;//lines to avoid animating
						
						//distanceCovered=0;//return to start mode
						distanceCovered=
								(spaceBetweenWords.length()-1+ExtraSpaceString.length())*spaceWidth;
						
						currentDisplayPoint.y+=lineHeight*2;//go to next line
						
						currentLine=totalLinesDisplayed;//new sentence index
						
						sentences[currentLine]=
								spaceBetweenWords+ExtraSpaceString+wordNextToDisplay;//new sentence at next line
					}
					
					else//word can be printed simply in display area
					{
						sentences[currentLine]+=spaceBetweenWords+ExtraSpaceString+wordNextToDisplay;//in the same line, next word
						distanceCovered+=(spaceBetweenWords.length()-1)*spaceWidth;
					}
		
				}
				else
					ayahDisplayFinished=true;//all words has been displayed
			}
			else//no more words to display, ayahDisplayFinished is true
			{
				
				if(distanceCovered<width+50)//pass times to last red boundary disappears
					distanceCovered+=deltaPixel;//others wise last red boundary can't be seen
				else
				{
					animationRunning=false;
					timeGapElapsed=0;
				}
			}
		}
		repaint();
	}
	
	
	protected void goToNextStep() {
		//do nothing
	}

	private void drawAnimation(Graphics g)
	{
		startPoint.x=this.getBounds().width-10;
		g.setFont(animFont);
		
		width=fontMetrics.stringWidth(sentences[currentLine]);
				
		for(int i=0;i<totalLinesDisplayed;i++)
		{
			g.drawString(sentences[i],startPoint.x-fontMetrics.stringWidth(sentences[i]), 
					startPoint.y+2*lineHeight*i-scrollY);
		}
		g.drawString(sentences[currentLine],
				startPoint.x-width,currentDisplayPoint.y-scrollY);
		
		//draw scrollbar in right position
		scrollbarPosY=(int) ((getBounds().getHeight()*scrollY)/currentDisplayPoint.y);
			
		g.fillRect(startPoint.x+5, scrollbarPosY, 5, 30);
		
		//WORD HIDING under a rectangle
		if(animatePartialWord)
			HidePartial(g);
		
		drawMeaningOfWord(g);
		
		g.setColor(Color.RED);
		if(!rectangles.isEmpty())
		{
			//System.out.println("total rectangles :"+rectangles.size());
			Rectangle rect;
			
			if(showPopUpInfoBox)
			{
				rect=rectangles.get(rectangles.size()-1);
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
		
		if(currentDisplayPoint.y-scrollY+lineHeight+150>getBounds().height)
		{
			scrollY+=scrollDelta;
		}
	}
	
	
	private void HidePartial(Graphics g) {
		g.setColor(bgColor);
		//g.setColor(new Color(240, 240, 200));
		//startX -5 and width +5 added for TWA effect--> displaying of the letter TWA
		//height+=extraHeight for displaying ZER
		//all is the fontmetrics problem, unsolved
		g.fillRect(startPoint.x-width-10, 
				currentDisplayPoint.y-height-scrollY, 
				width-distanceCovered+10, height+extraHeight);
	}

	private void drawFixedDisplay(Graphics g) 
	{
		g.setFont(animFont);
		//drawAyah
		for(int i=0;i<totalLinesDisplayed;i++)
		{
			g.drawString(sentences[i],startPoint.x-fontMetrics.stringWidth(sentences[i]),
					startPoint.y+2*lineHeight*i-scrollY);
		}
		g.drawString(sentences[currentLine],
				startPoint.x-width,currentDisplayPoint.y-scrollY);
		
		//draw scrollbar

		int scrollingPosY=(int) ((getBounds().getHeight()*scrollY)/currentDisplayPoint.y);
			
		g.fillRect(startPoint.x+5, scrollingPosY, 5, 30);
		
		//WORD HIDING under a rectangle
		if(animatePartialWord)
			HidePartial(g);
		
		drawMeaningOfWord(g);
		
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
			index=rectangles.size()-1;
		
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
			
			//partsOf speeches
			String[] details=infoOfWord.get(index).partsOfSpeechDetails;
			String[] segColors=infoOfWord.get(index).segmentColors;
			
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
			}

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
	
	
	private void drawMeaningOfWord(Graphics g)
	{
		if(rectangles.size()==0)
			return;
		
		if(infoOfWord==null)
			return;
		
		g.setFont(wbwFont);
		
		int writeX,writeY;
		Rectangle rect;
		
		for(int i=0;i<rectangles.size();i++)
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
	}

	protected void resetDisplay()
	{
		distanceCovered=0;
		wordIndexToDisplay=0;
		
		totalLinesDisplayed=0;
		currentLine=0;
		
		totalWaqfFound=0;
		
		currentDisplayPoint.y=startPoint.y;
		
		words=displayText.split(" ");
		
		fontMetrics=getFontMetrics(animFont);
		
		calculateExtraSpace();
		sentences[currentLine]=ExtraSpaceString+words[0];
		
		//lineHeight=getFontMetrics(animFont).getHeight()+20;
		scrollY=0;
		
		rectangles.clear();
		mouseFocusedOn=-1;
		
		animationRunning=true;
		ayahDisplayFinished=false;
		
		//mouseFocusedOn=-1;//unimportant
	}
	
	private void calculateExtraSpace() 
	{
		if(infoOfWord.size()>(wordIndexToDisplay-totalWaqfFound))
		{
			int stringWidth1=wbwFontMetrics.stringWidth
					(infoOfWord.get(wordIndexToDisplay-totalWaqfFound).transLiteration);
			int stringWidth2=wbwFontMetrics.stringWidth
					(infoOfWord.get(wordIndexToDisplay-totalWaqfFound).meaning);
			
			int extraStringWidth=0;
			if(stringWidth1>stringWidth2)
			{
				extraStringWidth=stringWidth1-fontMetrics.stringWidth
					(words[wordIndexToDisplay]);
			}
			else
				extraStringWidth=stringWidth2-fontMetrics.stringWidth
				(words[wordIndexToDisplay]);
				
			ExtraSpaceString="";
			//System.out.println(words[wordIndexToDisplay]);
			int k=0;
			while(k<extraStringWidth)
			{
				ExtraSpaceString+=" ";
				k+=spaceWidth;
			}
			distanceCovered+=k;
		}
		else
		{
			problemOccured(wordIndexToDisplay);
		}
	}

	protected void setFirstSentence(String sentence)
	{//reset display is called before calling this function
		totalLinesDisplayed=1;
		currentLine=1;
		sentences[1]=sentences[0];//what has been initialized in resetDisplay()

		sentences[0]=sentence;//=bismillah
		
		currentDisplayPoint.y+=lineHeight*2;
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
		
		fontMetrics=getFontMetrics(animFont);
		lineHeight=fontMetrics.getHeight()+20;
		height=fontMetrics.getHeight();
		extraHeight=fontMetrics.getDescent()+20;
		spaceWidth=fontMetrics.stringWidth(" ");
		
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
				downloadImagesEnabled);
	}
	
	public WordByWordFontPref getWbWFontPref()
	{
		return new WordByWordFontPref(
				"wbwFont.preferences",wbwTrnslitrtionColor,wbwMeaningColor,wbwFont);
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
	
	public static int getExtraHeight(){
		return extraHeight;
	}
	public static int getLineHeight(){
		return lineHeight;
	}
	
	private void problemOccured(int i) {
		System.err.println("Serious Problem-----------wordIndex: "+i);
		System.out.println("Rectangles :"+rectangles.size());
		System.out.println("Info words :"+infoOfWord.size());
		System.out.println("------------------");
		//System.exit(0);
	}
}