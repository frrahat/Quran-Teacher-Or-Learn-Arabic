/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Interfaces;

public interface UserInputListener {
	/*Listener for user input in the displayPanel*/
	void pauseStateChanged(boolean paused);
	void speedChanged(boolean upCalled);
}
