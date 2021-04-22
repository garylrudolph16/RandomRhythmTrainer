import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.File;

public class Playback {

    public static void playback(String filename) {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(MidiSystem.getSequence(new File(filename)));
            sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
            sequencer.start();
            GUI.textArea.append("Playing file:\t" + filename + "\n");
            while(sequencer.isRunning()) {
                if(sequencer.isRunning()) {
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException ignore) {
                        break;
                    }
                } else {
                    break;
                }
            }
            sequencer.stop();
            sequencer.close();
        } catch (Exception e) {
            GUI.textArea.append("Unable to play back file\n");
        }
    }
}
