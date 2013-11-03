package nu.mrpi.game.backend.server.model;

import java.util.*;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ReadOptimizedLevelScoreBoard implements LevelScoreBoard {
    private static final int MAX_SCORES_PER_LEVEL = 15;

    private Map<Integer, Integer> scores = new LinkedHashMap<>(15);

    @Override
    public void updateUserScore(int userId, int score) {
        Integer currentUserScore = scores.get(userId);
        if (currentUserScore == null || score > currentUserScore) {

            if (scores.size() < MAX_SCORES_PER_LEVEL) {
                scores.put(userId, score);
                sortScores();
            }

        }
    }

    private void sortScores() {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(scores.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b){
                return b.getValue().compareTo(a.getValue());
            }
        });

        scores.clear();

        for (Map.Entry<Integer, Integer> entry : entries) {
            scores.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<Integer, Integer> getLevelScoreBoardAsMap() {
        return scores;
    }
}
