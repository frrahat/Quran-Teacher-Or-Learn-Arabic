package QuranTeacher.Model;

/**
 * @author Rahat
 * @since June 11, 2016
 */
public class HitCommand {
	
	/*public static final String Attrbt_Indctr_Text="text";
	public static final String Attrbt_Indctr_Color="color";
	public static final String Attrbt_Indctr_FontSize="fontSize";*/
	
	private String subtext;
	private String boxText;
	private int remLastWordVal;
	private int subtextTargetWordEndIndex;
	
	private int totalCommands;
	
	
	public HitCommand(String hitString) {
		this.totalCommands=0;
		this.remLastWordVal=0;
		this.subtextTargetWordEndIndex=-1;
		
		/*if(hitString==null)
			return;*/
		if(hitString.length()==0)
			return;
		if(hitString.charAt(0)!='%')
			return;

		String commStrings[]=hitString.split("%");
		totalCommands=commStrings.length;
		
		for(int i=1;i<commStrings.length;i++){
			String commandString=commStrings[i];
			
			//subtext command catching
			if(commandString.startsWith(HitString.COMMAND_STRNG_SUBTEXT)){//subTextOn
				String subtext=commandString.substring(commandString.indexOf('=')+1);
				this.subtext=subtext.replace(HitString.LINE_SEPARATOR, "%").replace('%', '\n');
			}
			else if(commandString.startsWith(HitString.COMMAND_STRNG_SUBTEXT_END_WORD_INDX)){
				int value=Integer.parseInt(
						commandString.substring(commandString.indexOf('=')+1).trim());
				this.subtextTargetWordEndIndex=value;
			}
			//remove command catching
			else if(commandString.startsWith(HitString.COMMAND_STRNG_REMOVE_LAST_WORDS)){
				int value=Integer.parseInt(
						commandString.substring(commandString.indexOf('=')+1).trim());
				this.remLastWordVal=value;
			}
			else if(commandString.startsWith(HitString.COMMAND_STRNG_INFO_BOX)){
				String infotext=commandString.substring(commandString.indexOf('=')+1);
				this.boxText=infotext.replace(HitString.LINE_SEPARATOR, "%").replace('%', '\n');
			}
			else{
				System.out.println("Commandstring parsing failure : "+commandString);
			}
		}
	}

	public int getTotalCommands(){
		return totalCommands;
	}
	
	public String getSubtext() {
		return subtext;
	}

	public String getBoxText() {
		return boxText;
	}

	public int getRemLastWordVal() {
		return remLastWordVal;
	}

	public int getSubtextTargetWordEndIndex() {
		return subtextTargetWordEndIndex;
	}

	public void setSubtextTargetWordEndIndex(int subtextTargetWordEndIndex) {
		this.subtextTargetWordEndIndex = subtextTargetWordEndIndex;
	}
	
	
}
