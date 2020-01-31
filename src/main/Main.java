package main;

import io.Window;
import scenes.CalenderSceneManager;

public class Main {

	private Window window;
	private CalenderSceneManager calender;
	
	public Main() {

		new EventHandler().init("saved.events");
		
		window = new Window();
		calender = new CalenderSceneManager();
		
		calender.initChangeSubSceneActions(window);
		calender.init(window);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}