package nu.mrpi.game.backend.server.model;

import nu.mrpi.game.backend.server.Settings;

import java.util.*;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LevelScoreBoard {
    private final LinkedList<UserScore> scoreboard = new LinkedList<>();

    public Map<Integer, Integer> getLevelScoreBoardAsMap() {
        Map<Integer, Integer> scoreboardMap = new LinkedHashMap<>(scoreboard.size());

        synchronized (scoreboard) {
            for (UserScore userScore : scoreboard) {
                scoreboardMap.put(userScore.userId, userScore.score);
            }
        }

        return scoreboardMap;
    }

    public void updateUserScore(int userId, int score) {
        synchronized (scoreboard) {
            int currentUserScore = getUserScore(userId);
            boolean hasCurrentScore = currentUserScore != -1;

            if (hasCurrentScore) {
                if (score > currentUserScore) {
                    replaceCurrentScore(userId, score);
                }
            } else if (scoreboard.size() < Settings.MAX_SCORES_PER_LEVEL) {
                addScore(userId, score);
            } else {
                replaceOthersScoreIfHigher(userId, score);
            }
        }
    }

    private void addScore(int userId, int score) {
        scoreboard.add(new UserScore(userId, score));
        sortScores();
    }

    private void replaceCurrentScore(int userId, int score) {
        for (int i = 0, scoreboardSize = scoreboard.size(); i < scoreboardSize; i++) {
            UserScore userScore = scoreboard.get(i);
            if (userScore.userId == userId) {
                scoreboard.set(i, new UserScore(userId, score));
            }
        }
        sortScores();
    }

    private void replaceOthersScoreIfHigher(int userId, int score) {
        if (getLowestScore() < score) {
            scoreboard.removeLast();
            addScore(userId, score);
        }
    }

    private int getUserScore(int userId) {
        for (UserScore userScore : scoreboard) {
            if (userScore.userId == userId) {
                return userScore.score;
            }
        }
        return -1;
    }

    private int getLowestScore() {
        if (scoreboard.size() > 0) {
            return scoreboard.getLast().score;
        }
        return -1;
    }

    private void sortScores() {
        if (scoreboard.size() > 1) {
            Collections.sort(scoreboard);
        }
    }
}
