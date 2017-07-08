package pl.javadevmatt.tutorialclicker.service;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;

public class ScoreService {

	public final static String GAME_PREFS = "pl.javadevmatt.preclicker.prefs";
	public final static String GAME_SCORE = "pl.javadevmatt.preclicker.prefs.score";
	public final static String GAME_PASSIVE_INCOME = "pl.javadevmatt.preclicker.prefs.passiveincome";
	public final static String GAME_SAVED_TIMESTAMP = "pl.javadevmatt.preclicker.prefs.savedtimestamp";

	private Preferences prefs;

	private int points;
	private int passiveIncome;

	public ScoreService() {
		init();
	}

	private void init() {
		prefs = Gdx.app.getPreferences(GAME_PREFS);
		loadScore();
		loadPassiveIncome();
		calculateGainedPassiveIncome();
	}

	private void calculateGainedPassiveIncome() {

		long savedTimestamp = getSavedTimestamp();

		if (savedTimestamp > 0) {
			long millisPassed = TimeUtils.timeSinceMillis(savedTimestamp);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millisPassed);
			System.out.println("Passed seconds: " + seconds);
		} else {

		}
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
	}

	public int getPoints() {
		return points;
	}

	public int getPassiveIncome() {
		return passiveIncome;
	}

	private long getSavedTimestamp() {
		return prefs.getLong(GAME_SAVED_TIMESTAMP);
	}

	public void saveCurrentTimestamp() {
		prefs.putLong(GAME_SAVED_TIMESTAMP, TimeUtils.millis());
		prefs.flush();
	}
}
