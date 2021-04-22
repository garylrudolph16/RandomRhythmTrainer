import jm.JMC;
import jm.constants.Instruments;
import jm.gui.cpn.GrandStave;
import jm.gui.cpn.PhraseViewer;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;

import javax.sound.midi.Instrument;
import java.awt.*;

public class CustomPattern implements JMC {

    protected static void customPattern(double tempo, int length, int num, int denom, String[] notes, String filename, int instrument){
        GUI.textArea.append("Tempo " + tempo + "\t length " + length + "\n");
        Score score = new Score("Random Rhythms");
        score.setTempo(tempo);
        Part inst = new Part("New Part", instrument, 2);
        Phrase phr = new Phrase(0.0);
        phr.setTempo(tempo);
        score.setTimeSignature(num, denom);

        for (String noteValue : notes) {
            Note note = new Note(38, Double.parseDouble(noteValue));
            phr.addNote(note);
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
