package pl.javadevmatt.tutorialclicker.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreService {

	public final static String GAME_PREFS = "pl.javadevmatt.preclicker.prefs";
	public final static String GAME_SCORE = "pl.javadevmatt.preclicker.prefs.score";
	public final static String GAME_PASSIVE_INCOME = "pl.javadevmatt.preclicker.prefs.passiveincome";

	private Preferences prefs;

	private int points;
	private int passiveIncome;

	public ScoreService() {
		init();
	}

	private void init() {
		prefs = Gdx.app.getPreferences(GAME_PREFS);
		loadScore();
	}

	private void loadScore() {
		points = prefs.getInteger(GAME_SCORE);
	}

	private void loadPassiveIncome() {
		passiveIncome = prefs.getInteger(GAME_PASSIVE_INCOME);
	}

	public void addPoints(int pointsToAdd) {
		points += pointsToAdd;
		updateSaveScoreAndPassiveIncomeInPrefs();
	}

	public void addPoint() {
		points++;
		updateSaveScoreAndPassiveIncomeInPrefs();
	}

	public void resetGameScore() {
		points = 0;
		passiveIncome = 0;
		updateSaveScoreAndPassiveIncomeInPrefs();
	}

	private void updateSaveScoreAndPassiveIncomeInPrefs() {
		prefs.putInteger(GAME_SCORE, points);
		prefs.putInteger(GAME_PASSIVE_INCOME, passiveIncome);
		prefs.flush();
	}

	public void addPassiveIncome() {
		passiveIncome++;
		updateSaveScoreAndPassiveIncomeInPrefs();
		System.out.println("passive income: " + passiveIncome);
	}

	public int getPoints() {
		return points;
	}
}
