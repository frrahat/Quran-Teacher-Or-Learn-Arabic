package QuranTeacher.RenderAnimation;

/**
 * @author Rahat
 *	@date 27-Aug-2015
 */
public class CoordinatedWord{
	private int id;
	private String word;
	private int startX;
	private int lineIndex;
	private int wordWidth;
	private int extraWidth;
	
	public CoordinatedWord(String newWord,int id,int wordWidth, int extraWidth){
		this.word=newWord;
		this.id=id;
		this.wordWidth=wordWidth;
		this.extraWidth=extraWidth;
	}
	public int getId(){
		return id;
	}
	public String getWord(){
		return word;
	}
	public int getStartX(){
		return startX;
	}
	public int getLineIndex(){
		return lineIndex;
	}
	public int getWordWidth(){
		return wordWidth;
	}
	public int getExtraWidth(){
		return extraWidth;
	}
	public void setCoordinate(int startX, int lineIndex){
		this.startX=startX;
		this.lineIndex=lineIndex;
	}
}
