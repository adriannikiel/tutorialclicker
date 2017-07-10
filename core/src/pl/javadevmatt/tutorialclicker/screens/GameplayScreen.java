package pl.javadevmatt.tutorialclicker.screens;

import pl.javadevmatt.tutorialclicker.TutorialClickerGame;
import pl.javadevmatt.tutorialclicker.controllers.FlyingObjectController;
import pl.javadevmatt.tutorialclicker.controllers.RandomEventController;
import pl.javadevmatt.tutorialclicker.entities.Player;
import pl.javadevmatt.tutorialclicker.service.PassiveIncomeService;
import pl.javadevmatt.tutorialclicker.ui.BasicDialog;
import pl.javadevmatt.tutorialclicker.ui.GameLabel;
import pl.javadevmatt.tutorialclicker.ui.IClickCallback;
import pl.javadevmatt.tutorialclicker.ui.PlayerButton;
import pl.javadevmatt.tutorialclicker.ui.ResetScoreButton;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameplayScreen extends AbstractScreen {

	private Image bgImg;
	private Player player;
	private Button playerButton, resetScoreButton;
	private Label scoreLabel;
	private FlyingObjectController flyingObjectController;
	private RandomEventController randomEventController;
	private PassiveIncomeService passiveIncomeService;

	public GameplayScreen(TutorialClickerGame game) {
		super(game);
	}

	@Override
	protected void init() {
		initBg();
		initPlayer();
		initPlayerButton();
		initResetScoreButton();
		initScoreLabel();
		initFlyingStuffController();
		startTheMusic();
		initPassiveIncomeService();
		initPassiveIncomeInfoDialog();
		initRandomEventController();
	}

	private void initRandomEventController() {
		randomEventController = new RandomEventController(game);
	}

	private void initPassiveIncomeInfoDialog() {
		if (passiveIncomeService.getPointsToAdd() > 0) {
			BasicDialog basicDialog = new BasicDialog();
			stage.addActor(basicDialog);
			basicDialog.initContent("Passive income gainded: " + passiveIncomeService.getPointsToAdd());
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		update();

		spriteBatch.begin();
		stage.draw();
		spriteBatch.end();
	}

	@Override
	public void pause() {
		super.pause();
		game.getScoreService().saveCurrentGamestamp();
	}

	private void update() {
		scoreLabel.setText("Score: " + game.getScoreService().getPoints());
		stage.act();
	}

	private void initPassiveIncomeService() {
		passiveIncomeService = new PassiveIncomeService(game.getScoreService());
		passiveIncomeService.start();
	}

	private void startTheMusic() {
		game.getSoundService().startPlayingMusic(true);
	}

	private void initFlyingStuffController() {
		flyingObjectController = new FlyingObjectController(game, stage);
	}

	private void initBg() {
		bgImg = new Image(new Texture("bg.png"));
		stage.addActor(bgImg);
	}

	private void initScoreLabel() {
		scoreLabel = new GameLabel();
		stage.addActor(scoreLabel);
	}

	private void initPlayer() {
		player = new Player();
		stage.addActor(player);
	}

	private void initPlayerButton() {
		playerButton = new PlayerButton(new IClickCallback() {
			@Override
			public void onClick() {
				player.reactOnClick();
				game.getScoreService().addPoint();
				game.getSoundService().playJumpSound();
			}
		});

		stage.addActor(playerButton);
	}

	private void initResetScoreButton() {
		resetScoreButton = new ResetScoreButton(new IClickCallback() {
			@Override
			public void onClick() {
				game.getScoreService().resetGameScore();
			}
		});

		stage.addActor(resetScoreButton);
	}

}
