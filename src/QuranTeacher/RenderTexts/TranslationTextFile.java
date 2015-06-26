package QuranTeacher.RenderTexts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Rahat
 *	@Date 25-06-15
 */
public class TranslationTextFile {
	private InputStream inputStream;
	private String fileName;
	private String previewText;
	
	
	
	public TranslationTextFile(InputStream in,String fileName){
		this.inputStream=in;
		this.fileName=fileName;		
		setPreviewText(in);
	}
	
	
	
	public TranslationTextFile(File file){
		try {
			this.inputStream=new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String name=file.getName();
		int k=name.lastIndexOf('.');
		if(k!=0){
			name=name.substring(0, k);
		}
		this.fileName=name;
		
		InputStream in=null;
		
		try {
			in=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			//unexpected if it occurs
			e.printStackTrace();
		}
		
		setPreviewText(in);
		
	}
	
	
	public void setPreviewText(InputStream in){
		
		this.previewText="No Preview Text Found";
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		try {
			String text=reader.readLine();//get first line
			this.previewText=text;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}



	public String getFileName() {
		return fileName;
	}



	public String getPreviewText() {
		return previewText;
	}
	
	
}
