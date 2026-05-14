package edu.sdccd.cisc191.model;

public class MatchViewModel {
    private String matchId;
    private final Player player = new Player("Player");
    private final Player opponent = new Player("Opponent");
    private boolean matchOver;
    private String winnerName = "";

    // TODO 7: Make this shared counter thread-safe.
    // Use either an AtomicInteger field or synchronized methods so background tasks cannot lose updates.
    private int completedMatchCount = 0;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public boolean isMatchOver() {
        return matchOver;
    }

    public void setMatchOver(boolean matchOver) {
        this.matchOver = matchOver;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName == null ? "" : winnerName;
    }

    public synchronized int getCompletedMatchCount() {
        return completedMatchCount;
    }

    /**
     * TODO 7: Complete this method using thread-safe programming.
     *
     * This model may be updated after JavaFX background tasks finish. Make sure concurrent
     * calls do not lose completed-match updates. You may use synchronized methods or an
     * AtomicInteger.
     *
     * Requirements:
     * - Increase the completed match count exactly once per call.
     * - Store the winner name using the existing null-safe setter.
     * - Mark the match as over.
     * - Protect shared state from race conditions.
     */
    public synchronized void recordCompletedMatchThreadSafely(String winnerName) {
        completedMatchCount++;
        setWinnerName(winnerName);
        matchOver = true;
    }

    public boolean hasJoinedMatch() {
        return matchId != null && !matchId.isBlank();
    }

    public boolean canPlayMatch() {
        return hasJoinedMatch() && !matchOver;
    }

    /**
     * TODO 2: Complete this MVC helper.
     *
     * Return a short summary for the bottom of the JavaFX screen.
     * Expected format:
     * Match match-001: Ada vs Bot (Hard, ranked)
     *
     * Requirements:
     * - Use "No match" when matchId is null or blank.
     * - Use the current player and opponent names from this model.
     * - Use "Normal" when difficulty is null or blank.
     * - Use "ranked" when ranked is true, otherwise "casual".
     */
    public String buildMatchSummary(String difficulty, boolean ranked) {
        if (getMatchId() == null || getMatchId().isEmpty()) { return "No match"; }
        
        String matchDifficulty;
        String matchRank;

        if (difficulty == null || difficulty.trim().isEmpty()) { matchDifficulty = "Normal"; }
        else { matchDifficulty = difficulty.trim(); }

        if (ranked == true) { matchRank = "ranked";} 
        else { matchRank = "casual"; }
        
        return ("Match " + getMatchId() + ": "
            + getPlayer().getName() + " vs " + getOpponent().getName()
            + " (" + matchDifficulty + ", " + matchRank + ")");
    }

    public synchronized void resetLocalState() {
        matchId = null;
        player.setName("Player");
        opponent.setName("Opponent");
        matchOver = false;
        winnerName = "";
        completedMatchCount = 0;
    }
}
