package QuranTeacher.Interfaces;

import QuranTeacher.Utils.VersionInfo;

public interface UpdateActivityReturnListener{
	public void nextToDo(boolean wasDownloadSuccess, VersionInfo newVersionInfo);
}
