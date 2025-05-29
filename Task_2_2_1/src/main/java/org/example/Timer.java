package org.example;

/**
 * Class for representing time of simulation.
 */
public  class Timer {
    private int time;

    /**
     * Simple constructor.
     */
    Timer() {
        time = 8*60;
    }


    /**
     * Get time.
     *
     * @return value of time
     */
    public int getTimeInt() {
        return time;
    }


    /**
     * Get string representation of time.
     *
     * @return time in string representation
     */
    public String getTimeString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append((time/60)%24).append(":");
        if (time %60 < 10) {
            builder.append("0").append(time % 60);
        }
        else {
            builder.append(time%60);
        }
        return builder.append("]").toString();
    }

    /**
     * Converse num(should represent time) to string.
     *
     * @param num number to represent in string
     * @return string representation of time
     */
    public static String timeToString(int num) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append((num/60)%24).append(":");
        if (num %60 < 10) {
            builder.append("0").append(num % 60);
        }
        else {
            builder.append(num%60);
        }
        return builder.append("]").toString();
    }

    /**
     * Increment time.
     */
    public void incTime() {
        time++;
    }
}
