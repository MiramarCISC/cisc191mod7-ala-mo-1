package edu.sdccd.cisc191.server;

/**
 * Tracks server-wide match statistics shared by many gRPC request threads.
 */
public class MatchStatistics {

    // TODO 9: Replace these counters with a thread-safe design.
    // Recommended: AtomicInteger joinedMatchCount and AtomicInteger completedMatchCount.
    private int joinedMatchCount = 0;
    private int completedMatchCount = 0;
    private int rankedJoins = 0;
    private int casualJoins = 0;

    private final java.util.Map<String, Integer> joinsByDifficulty = new java.util.HashMap<>();

    /**
     * TODO 9: Make this update thread-safe.
     */
    public synchronized void recordJoin() {
        joinedMatchCount++;
    }

    /**
     * TODO 9: Make this update thread-safe.
     */
    public synchronized void recordCompletion() {
        completedMatchCount++;
    }

    public synchronized int getJoinedMatchCount() {
    return joinedMatchCount;
    }
    
    public synchronized int getRankedJoins() {
        return rankedJoins;
    }
    
    public synchronized int getCasualJoins() {
        return casualJoins;
    }

    public synchronized int getCompletedMatchCount() {
        return completedMatchCount;
    }

    public synchronized int getJoinCountForDifficulty(String difficulty) {
    String joinCountDifficulty;
    
    if (difficulty == null || difficulty.trim().isEmpty()) { joinCountDifficulty = "Normal"; }
    else { joinCountDifficulty = difficulty.trim(); }

    return joinsByDifficulty.getOrDefault(joinCountDifficulty, 0);
}

    /**
     * TODO 9: Return a readable, thread-safe statistics summary.
     *
     * Expected format:
     * Server stats: 3 joined, 2 completed
     */
    public synchronized String buildStatusLine() {
        return "Server stats: " + joinedMatchCount
            + " joined, " + completedMatchCount + " completed";
    }
}
