package scenes.subscenes;

import java.awt.event.ActionEvent;

import elements.Button;
import elements.Event;
import elements.RegularButton;
import elements.TextInput;
import main.EventHandler;
import scenes.SceneChangeAction;
import scenes.SubScenes;
import scenes.whole_scenes.Scene;

/**
 * Simple interaction where the user can write a new events content and when it should be.
 * 
 * @author Jens Benz
 *
 */
public class CreateSubScene extends SubScene {

	private Button[] elements;
	private SceneChangeAction sceneChange;
	
	public CreateSubScene() {
		super(SubScenes.CREATE);
	}

	@Override
	public void setSubSceneChangeAction(SceneChangeAction sceneChange) {
		this.sceneChange = sceneChange;
	}

	@Override
	public void addElementsToScene(Scene scene) {
		for (Button elem : elements) {
			scene.addComponent(elem);
		}
	}

	@Override
	public void removeElementsToScene(Scene scene) {
		for (Button elem : elements) {
			scene.removeComponent(elem);
		}
	}

	@Override
	public void render(Scene ontoScene) {
		for (Button elem : elements) {
			elem.render(ontoScene);
		}
	}

	/**
	 * Create the appropiate textfields and buttons and place them within the subscene
	 */
	@Override
	public void init() {

		String titleTitle = "Title of event";
		String fromTitle = "From date: hh:mm-dd/mm/yyyy";
		String toTitle = "To date: hh:mm-dd/mm/yyyy";

		TextInput titleInput = new TextInput(titleTitle);
		TextInput fromDateInput = new TextInput(fromTitle);
		TextInput toDateInput = new TextInput(toTitle);

		RegularButton confirm = new RegularButton("Confirm");
		RegularButton goBack = new RegularButton("Return");

		elements = new Button[5];
		elements[0] = titleInput;
		elements[1] = fromDateInput;
		elements[2] = toDateInput;
		elements[3] = confirm;
		elements[4] = goBack;

		for (Button elem : elements) {
			elem.setFromPoint(fromX, fromY);
		}

		int inputHeight = h / 20;
		int inputWidth = w * 2 / 3;
		int margin = inputHeight / 2;
		int buttonHeight = h / 10;
		int buttonWidth = buttonHeight * 16 / 9;

		titleInput.setPlacement(0, 0, inputWidth, inputHeight);
		fromDateInput.setPlacement(0, inputHeight + margin, inputWidth, inputHeight);
		toDateInput.setPlacement(0, inputHeight * 2 + margin * 2, inputWidth, inputHeight);
		goBack.setPlacement(0, inputHeight * 3 + margin * 3, buttonWidth, buttonHeight);
		confirm.setPlacement(buttonWidth + margin, inputHeight * 3 + margin * 3, buttonWidth, buttonHeight);

		confirm.addClickAction((ActionEvent e) -> {
			try {
				Event event = new Event(titleInput.getText(), fromDateInput.getText(), toDateInput.getText());
				EventHandler.add(event);
				sceneChange.change(SubScenes.TILES);
			} catch (Exception ex) {
				titleInput.setTitle("WRONG INPUT! " + titleTitle);
				fromDateInput.setTitle("WRONG INPUT! " + fromTitle);
				toDateInput.setTitle("WRONG INPUT! " + toTitle);
			}
		});
		
		goBack.addClickAction((ActionEvent e) -> {
			sceneChange.change(SubScenes.TILES);
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
