import jm.JMC;
import jm.gui.cpn.GrandStave;
import jm.gui.cpn.PhraseViewer;
import jm.music.data.*;
import jm.util.*;

import java.awt.*;

public final class RandomPatterns implements JMC{

    protected static void randomPatterns(double tempo, int length, int num, int denom, String filename, int instrument){
        GUI.textArea.append("Tempo " + tempo + "\t length " + length + "\n");
        Score score = new Score("Random Rhythms");
        score.setTempo(tempo);
        Part inst = new Part("New Part", instrument, 2);
        Phrase phr = new Phrase(0.0);
        phr.setTempo(tempo);
        score.setTimeSignature(num, denom);

        int index;
        double[][] patterns =
                {{2.0, HALF_NOTE},
                {2.0, DOTTED_EIGHTH_NOTE, DOTTED_EIGHTH_NOTE, EIGHTH_NOTE},
                {2.0, DOTTED_EIGHTH_NOTE, DOTTED_EIGHTH_NOTE, EIGHTH_NOTE},
                {1.0, EIGHTH_NOTE, EIGHTH_NOTE},
                {1.0, QUARTER_NOTE},
                {1.0, EIGHTH_NOTE_TRIPLET, EIGHTH_NOTE_TRIPLET, EIGHTH_NOTE_TRIPLET},
                {2.0, QUARTER_NOTE_TRIPLET, QUARTER_NOTE_TRIPLET, QUARTER_NOTE_TRIPLET},
                {1.0, SIXTEENTH_NOTE, SIXTEENTH_NOTE, SIXTEENTH_NOTE, SIXTEENTH_NOTE},
                {1.0, EIGHTH_NOTE, SIXTEENTH_NOTE, SIXTEENTH_NOTE},
                {1.0, SIXTEENTH_NOTE, EIGHTH_NOTE, SIXTEENTH_NOTE},
                {1.0, SIXTEENTH_NOTE, SIXTEENTH_NOTE, EIGHTH_NOTE}};

        int curr = 0;
        int beats = length * ((num * 4)/ denom);
        while (curr < beats) {

            index = (int)(Math.random()*10);

            while (curr + patterns[index][0] > beats) {
                index = (int)(Math.random()*10);
            }
            GUI.textArea.append("x = " + index + "\n");
            // create notes for the chosen pattern to the phrase
            for (short j=1; j<patterns[index].length; j++) {
                Note note = new Note(60, patterns[index][j]);
                phr.addNote(note);
            }
            curr += patterns[index][0];
        }

        // add the phrase to an instrument and that to a score
        inst.addPhrase(phr);
        score.addPart(inst);

        // create a MIDI file of the score
        Write.midi(score, filename);

        PhraseViewer view = new PhraseViewer(new Frame());
        GrandStave stave = new GrandStave(phr);
        view.showPhrase(stave, phr, 0, 0);

    }
}