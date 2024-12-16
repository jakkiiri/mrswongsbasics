

public class Score implements Comparable<Score>{
    private long time;
    private String name;

    public Score (String name, long time) {
        this.time = time;
        this.name = name;
    }

    public int compareTo(Score o) {
        return Long.compare(this.time, o.getTime());
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + ": " + Long.toString(time);
    }
}
