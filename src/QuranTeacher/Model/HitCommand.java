package QuranTeacher.Model;

import java.awt.Color;

/**
 * @author Rahat
 * @since June 11, 2016
 */
public class HitCommand {
	
	public static final String Attrbt_Indctr_Text="text";
	public static final String Attrbt_Indctr_Color="color";
	public static final String Attrbt_Indctr_FontSize="fontSize";
	
	private String text;
	private Color color;
	private float fontSize;
	public static HitCommand parseHitString(String commandString){
		HitCommand hitCommand=new HitCommand();
		HitString hitString=new HitString();
		String attrbtParts[]=commandString.split("&&");
		for(int i=0;i<attrbtParts.length;i++){
			String s=attrbtParts[i];
			String value=s.substring(s.indexOf('=')+1);
			if(s.startsWith(Attrbt_Indctr_Text)){
				hitCommand.text=value;
			}
			else if(s.startsWith(Attrbt_Indctr_Color)){
				try{
					hitCommand.color=Color.decode(value);
				}catch(NumberFormatException ne){
					ne.printStackTrace();
				}
			}
			else if(s.startsWith(Attrbt_Indctr_FontSize)){
				try{
					hitCommand.fontSize=Float.parseFloat(value);
				}catch(NumberFormatException ne){
					ne.printStackTrace();
				}
			}
		}
		
		return hitCommand;
	}
	public String getText() {
		return text;
	}
	public Color getColor() {
		return color;
	}
	public float getFontSize() {
		return fontSize;
	}
	
	
}
