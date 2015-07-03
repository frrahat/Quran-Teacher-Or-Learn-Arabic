package QuranTeacher.RenderTexts;

import java.io.File;
import java.util.ArrayList;


import QuranTeacher.FilePaths;

/**
 * @author Rahat
 *@date 25-06-15
 */
public class TranslationTextInfoContainer {
	private static ArrayList<TranslationTextFile>transFiles;
	private static boolean initialized;
	
	public static void initializeTextsInfoContainer(){
		transFiles=new ArrayList<>();
		initialized=true;
		
		transFiles.add(new TranslationTextFile(FilePaths.EnglishTextFilePathYusuf, "English - Yusuf Ali"));
		transFiles.add(new TranslationTextFile(FilePaths.EnglishTextFilePathSahih, "English - Sahih International"));
		//transFiles.add(new TranslationTextFile(FilePaths.BengaliTextFilePath, "Bengali"));
		
		File storageFolder=new File(FilePaths.additionalTextsDir);
		if(!storageFolder.exists()){
			storageFolder.mkdirs();
		}
		else{
			File[] files=storageFolder.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].getName().endsWith(".txt")){
					transFiles.add(new TranslationTextFile(files[i]));
				}
			}
		}
	}
	
	public static ArrayList<TranslationTextFile> getAllTranslationTextFiles(){
		if(!initialized){
			initializeTextsInfoContainer();
		}
		return transFiles;
	}
	
	public static void addToTransFileList(File file){
		if(!initialized){
			initializeTextsInfoContainer();
		}
		TranslationTextFile t=new TranslationTextFile(file);
		for(int i=0;i<transFiles.size();i++){
			if(transFiles.get(i).getFileName().equals(t.getFileName())){
				return;
			}
		}
		transFiles.add(new TranslationTextFile(file));
	}
	
	public static TranslationTextFile getTransFile(int index){
		if(!initialized){
			initializeTextsInfoContainer();
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
		if(!initialized)
			initializeTextsInfoContainer();
		return transFiles.size();
	}
}
