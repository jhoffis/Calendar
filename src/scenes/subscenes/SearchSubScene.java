package scenes.subscenes;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

import elements.Button;
import elements.Event;
import elements.EventLister;
import elements.RegularButton;
import elements.TextInput;
import scenes.SceneChangeAction;
import scenes.SubScenes;
import scenes.whole_scenes.Scene;

/**
 * Simple interaction for the user to search for events within a timeframe only
 * to let it be posted upon a eventlister. In this case that is the eventlister
 * that dateSidebar has.
 * 
 * @author Jens Benz
 *
 */
public class SearchSubScene extends SubScene {

	private Button[] elements;
	private SceneChangeAction sceneChange;
	private EventLister eventLister;

	public SearchSubScene() {
		super(SubScenes.SEARCH);
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

		String fromTitle = "From date: hh:mm-dd/mm/yyyy";
		String toTitle = "To date: hh:mm-dd/mm/yyyy";

		TextInput fromDateInput = new TextInput(fromTitle);
		TextInput toDateInput = new TextInput(toTitle);

		RegularButton confirm = new RegularButton("Search");
		RegularButton goBack = new RegularButton("Return");

		elements = new Button[4];
		elements[0] = fromDateInput;
		elements[1] = toDateInput;
		elements[2] = confirm;
		elements[3] = goBack;

		for (Button elem : elements) {
			elem.setFromPoint(fromX, fromY);
		}

		int inputHeight = h / 20;
		int inputWidth = w * 2 / 3;
		int margin = inputHeight / 2;
		int buttonHeight = h / 10;
		int buttonWidth = buttonHeight * 16 / 9;

		fromDateInput.setPlacement(0, 0, inputWidth, inputHeight);
		toDateInput.setPlacement(0, inputHeight + margin, inputWidth, inputHeight);
		goBack.setPlacement(0, inputHeight * 2 + margin * 2, buttonWidth, buttonHeight);
		confirm.setPlacement(buttonWidth + margin, inputHeight * 2 + margin * 2, buttonWidth, buttonHeight);

		confirm.addClickAction((ActionEvent e) -> {
			try {

				LocalDateTime from = LocalDateTime.parse(fromDateInput.getText(), Event.FORMATTER);
				LocalDateTime to = LocalDateTime.parse(toDateInput.getText(), Event.FORMATTER);

				eventLister.show(from, to);

			} catch (Exception ex) {
				fromDateInput.setTitle("WRONG INPUT! " + fromTitle);
				toDateInput.setTitle("WRONG INPUT! " + toTitle);
			}
		});

		goBack.addClickAction((ActionEvent e) -> {
			sceneChange.change(SubScenes.TILES);
		});
	}

	public void setEventLister(EventLister eventLister) {
		this.eventLister = eventLister;
	}

	@Override
	public void update() {

	}

}
