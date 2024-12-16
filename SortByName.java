import java.util.Comparator;

public class SortByName implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
    }
}
