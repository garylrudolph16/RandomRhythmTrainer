import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class RhythmAccuracy {

    protected static List<Double> getRhythms(double tempo, List<Long> notes) {

        for (int i = 0; i < notes.size(); i++) {
            GUI.textArea.append(String.format("Note %d was played at %d\n", i, notes.get(i)));
        }

        List<Double> playedRhythms = new ArrayList<>();
        for (int i = 1; i < notes.size(); i++) {
            double millisPerBeat = (60 * 1000 / tempo); //milliseconds per minute divided by beats per minute
            playedRhythms.add((notes.get(i) - notes.get(i - 1)) / millisPerBeat);
        }

        return playedRhythms;
    }

    protected static List<Double> getAccuracies(List<Double> playedRhythms, Phrase phr) {
        int note = 0;
        int measure = 0;
        double beat = 0.0;
        for (int i = 0; i < playedRhythms.size(); i++) {
            GUI.textArea.append(String.format("Note %d of measure %d had rhythm value of %f\n", note, measure, playedRhythms.get(i)));
            note++;
            beat += phr.getNote(i).getRhythmValue();
            if (beat >= phr.getNumerator()) {
                measure++;
                note = 0;
                beat = 0;
            }
        }

        List<Double> rhythmDifference = new ArrayList<>();
        for (int i = 0; i < playedRhythms.size(); i++) {
            rhythmDifference.add(playedRhythms.get(i) - phr.getNote(i).getRhythmValue());
        }
        note = 0;
        measure = 0;
        beat = 0.0;
        for (int i = 0; i < rhythmDifference.size(); i++) {
            if (rhythmDifference.get(i) < 0) {
                GUI.textArea.append(String.format("Note %d of measure %d was early by %f beats\n", note, measure, Math.abs(rhythmDifference.get(i))));
            } else if (rhythmDifference.get(i) > 0) {
                GUI.textArea.append(String.format("Note %d of measure %d was late by %f beats\n", note, measure, rhythmDifference.get(i)));
            } else {
                GUI.textArea.append(String.format("Note %d of measure %d was in time\n", note, measure));
            }
            note++;
            beat += phr.getNote(i).getRhythmValue();
            if (beat >= phr.getNumerator()) {
                measure++;
                note = 0;
                beat = 0;
            }
        }

        List<Double> rhythmAccuracy = new ArrayList<>();
        for (int i = 0; i < playedRhythms.size(); i++) {
            double ratio = playedRhythms.get(i) / phr.getNote(i).getRhythmValue();
            double error = Math.abs(ratio - 1);
            rhythmAccuracy.add(1 - error);
        }
        return rhythmAccuracy;

    }

    protected static void runAccuracyTest(String filename) {
        Score score = new Score();

        try {
            Read.midi(score, filename);
        } catch (Exception e) {
            GUI.textArea.append("Could not read file input\n");
        }

        if (score.size() == 0) {
            return;
        }

        Phrase phr = score.getPart(0).getPhrase(0);

        Metronome met = startMetronome(score.getTempo());
        List<Long> playedNotes = collectNotes(phr.length());
        met.stop();

        List<Double> playedRhythms = getRhythms(score.getTempo(), playedNotes);
        List<Double> rhythmAccuracy = getAccuracies(playedRhythms, phr);


        GUI.textArea.append(String.format("Average accuracy: %f\n", rhythmAccuracy.stream().mapToDouble(val -> val).average().orElse(0.0)));

    }

    private static List<Long> collectNotes(int length) {
        StrokeListener listener = new StrokeListener(length);
        List<Long> playedNotes = new ArrayList<>();

        GlobalScreen.addNativeKeyListener(listener);
        while (playedNotes.size() < length) {
            playedNotes = listener.getPlayedNotes();
        }
        GlobalScreen.removeNativeKeyListener(listener);

        return playedNotes;
    }

    private static Metronome startMetronome(double tempo) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            GUI.textArea.append("There was a problem registering the native hook.\n");
            GUI.textArea.append(ex.getMessage()+ "\n");

            System.exit(1);
        }

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        return new Metronome(tempo);
    }
}
