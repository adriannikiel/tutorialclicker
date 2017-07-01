package pl.javadevmatt.tutorialclicker.screens;

import pl.javadevmatt.tutorialclicker.TutorialClickerGame;
import pl.javadevmatt.tutorialclicker.entities.Player;
import pl.javadevmatt.tutorialclicker.ui.IClickCallback;
import pl.javadevmatt.tutorialclicker.ui.PlayerButton;
import pl.javadevmatt.tutorialclicker.ui.ResetScoreButton;
import pl.javadevmatt.tutorialclicker.ui.ScoreLabel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameplayScreen extends AbstractScreen {

	private Texture bgTexture;
	private Player player;
	private Button playerButton, resetScoreButton;
	private Label scoreLabel;

	public GameplayScreen(TutorialClickerGame game) {
		super(game);
	}

	@Override
	protected void init() {
		bgTexture = new Texture("bg.png");
		initPlayer();
		initPlayerButton();
		initResetScoreButton();
		initScoreLabel();
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
				game.addPoint();
			}
		});

		stage.addActor(playerButton);
	}

	private void initResetScoreButton() {
		resetScoreButton = new ResetScoreButton(new IClickCallback() {
			@Override
			public void onClick() {
				game.resetGameScore();
			}
		});

		stage.addActor(resetScoreButton);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		update();

		spriteBatch.begin();
		spriteBatch.draw(bgTexture, 0, 0);
		spriteBatch.end();

		spriteBatch.begin();
		stage.draw();
		spriteBatch.end();
	}

	private void update() {
		scoreLabel.setText("Score: " + game.getPoints());
		stage.act();
	}

}
