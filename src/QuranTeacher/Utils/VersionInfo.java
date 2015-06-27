package QuranTeacher.Utils;

/**
 * @author Rahat
 * 28-06-2015
 */
public class VersionInfo {
	private String versionString;
	private String releaseDateString;
	private String downloadLink;
	private String message;
	
	
	
	public VersionInfo(String[] text,int line) {
		versionString=text[0];
		releaseDateString=text[1];
		downloadLink=text[2];
		message="";
		for(int i=3;i<line;i++){
			message+=text[i]+"\n";
		}
	}
	
	public boolean isNewerThan(String oldVersionString){
		if(this.versionString.compareTo(oldVersionString)>0)
			return true;
		return false;
	}
	
	public String getVersionString() {
		return versionString;
	}
	
	public String getReleaseDateString() {
		return releaseDateString;
	}

	public String getDownloadLink() {
		return downloadLink;
	}
	public String getMessage() {
		return message;
	}
	
	
}
