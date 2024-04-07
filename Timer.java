public class Timer {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            stopTime = System.currentTimeMillis();
            running = false;
        }
    }

    public double getTimeElapsedSeconds() {
        long elapsedTimeMillis = getTimeElapsedMillis();
        return elapsedTimeMillis / 1000.0; // Convert milliseconds to seconds
    }

    private long getTimeElapsedMillis() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}


