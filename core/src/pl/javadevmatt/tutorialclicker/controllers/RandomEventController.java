package pl.javadevmatt.tutorialclicker.controllers;

import pl.javadevmatt.tutorialclicker.TutorialClickerGame;
import pl.javadevmatt.tutorialclicker.ui.BasicDialog;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class RandomEventController {

	private static final int RANDOM_TICK_INTERVAL = 5; // TODO: change after initial implementation

	private TutorialClickerGame game;
	private Stage stage;

	public RandomEventController(TutorialClickerGame game, Stage stage) {
		this.game = game;
		this.stage = stage;
		init();
	}

	private void init() {

		Timer.schedule(new Task() {

			@Override
			public void run() {
				if (MathUtils.randomBoolean()) {
					triggerRandomEvent();
				}
			}

		}, RANDOM_TICK_INTERVAL, RANDOM_TICK_INTERVAL);

	}

	protected void triggerRandomEvent() {

		int randomNumber = MathUtils.random(1, 3);

		switch (randomNumber) {
		case 1:
			gainMoneyEvent();
			break;
		case 2:
			loseMoneyEvent();
			break;
		case 3:
			gainPassiveIncomeEvent();
			break;
		default:
			break;
		}
	}

	private void triggerDialog(String text) {
		BasicDialog basicDialog = new BasicDialog();
		basicDialog.showDialog(stage, text);
	}

	private void gainPassiveIncomeEvent() {
		game.getScoreService().addPassiveIncome();
		triggerDialog("You gained passive income! Yayyy!");
	}

	private void gainMoneyEvent() {
		game.getScoreService().addPoints(123);
		triggerDialog("Free money! Yayyy!");
	}

	private void loseMoneyEvent() {
		game.getScoreService().addPoints(-123);
		triggerDialog("Pay taxes! You owl!");
	}

}
