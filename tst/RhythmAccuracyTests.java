
import jm.music.data.Note;
import jm.music.data.Phrase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RhythmAccuracyTests {

    @Test
    public void testRhythms() {
        List<Long> notes = new ArrayList<Long>() {{
            add(0L);
            add(1000L);
            add(1500L);
            add(2000L);
        }};
        List<Double> playedRhythms = RhythmAccuracy.getRhythms(60.0, notes);
        assert playedRhythms.get(2) == 0.5;
    }

    @Test
    public void testAccuracy() {
        List<Double> rhythms = new ArrayList<Double>() {{
            add(0.5);
            add(0.5);
            add(1.0);
            add(0.5);
        }};
        Phrase phr = new Phrase(0.0);
        Note eighth = new Note(38, 0.5);
        phr.addNote(eighth);
        phr.addNote(eighth);
        phr.addNote(eighth);
        phr.addNote(eighth);
        List<Double> accuracies = RhythmAccuracy.getAccuracies(rhythms, phr);

        assert accuracies.get(2) == 0;
    }

}
