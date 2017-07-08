package pl.javadevmatt.tutorialclicker.screens;

import pl.javadevmatt.tutorialclicker.TutorialClickerGame;
import pl.javadevmatt.tutorialclicker.controllers.FlyingObjectController;
import pl.javadevmatt.tutorialclicker.entities.Player;
import pl.javadevmatt.tutorialclicker.service.PassiveIncomeService;
import pl.javadevmatt.tutorialclicker.ui.IClickCallback;
import pl.javadevmatt.tutorialclicker.ui.PlayerButton;
import pl.javadevmatt.tutorialclicker.ui.ResetScoreButton;
import pl.javadevmatt.tutorialclicker.ui.ScoreLabel;

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
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		update();

		spriteBatch.begin();
		stage.draw();
		spriteBatch.end();
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
		scoreLabel = new ScoreLabel();
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
