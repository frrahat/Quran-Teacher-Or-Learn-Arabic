package QuranTeacher.RenderTexts;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.naming.ldap.InitialLdapContext;

import QuranTeacher.FilePaths;

/**
 * @author Rahat
 *@date 25-06-15
 */
public class TranslationTextInfoContainer {
	private static ArrayList<TranslationTextFile>transFiles;
	private static boolean initialized;
	
	public static void initializeTextsContainer(){
		transFiles=new ArrayList<>();
		
		InputStream inB=TranslationTextFile.class.getResourceAsStream(FilePaths.BengaliTextFilePath);
		InputStream inE=TranslationTextFile.class.getResourceAsStream(FilePaths.EnglishTextFilePath);
		
		transFiles.add(new TranslationTextFile(inE, "English"));
		transFiles.add(new TranslationTextFile(inB,"Bengali"));
		
		initialized=true;
	}
	
	public static ArrayList<TranslationTextFile> getAllTranslationTextFiles(){
		if(!initialized){
			initializeTextsContainer();
		}
		return transFiles;
	}
	
	public static void addToTransFileList(File file){
		if(!initialized){
			initializeTextsContainer();
		}
		transFiles.add(new TranslationTextFile(file));
	}
	
	public static TranslationTextFile getTransFile(int index){
		if(!initialized){
			initializeTextsContainer();
		}
		return transFiles.get(index);
	}
	
	public static boolean isInitialized(){
		return initialized;
	}
	
	public static String[] getAllFileNames(boolean withNone){
		int total=transFiles.size();
		if(withNone){
			total++;
		}
		
		String[] result=new String[total];
		
		for(int i=0;i<transFiles.size();i++){
			result[i]=transFiles.get(i).getFileName();
		}
		
		if(withNone){
			result[total-1]="NONE";
		}
		
		return result;
	}

	public static int getSize() {
		return transFiles.size();
	}
}
