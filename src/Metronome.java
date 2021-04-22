import javax.sound.midi.*;
public class Metronome implements Runnable, MetaEventListener {
    private Sequencer sequencer;
    private double tempo;
    Thread t;

    Metronome(double tempo) {
        this.tempo = tempo;
        t = new Thread(this);
        GUI.textArea.append("New metronome: " + tempo + "\n");
        t.start();
    }
    @Override
    public void run() {
        try {
            openSequencer();
            Sequence seq = createSequence();
            startSequence(seq);
        } catch (Exception e) {
            GUI.textArea.append("Error creating sequencer\n");
        }
    }
    private void openSequencer() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
        } catch (Exception e) {
            GUI.textArea.append("Error opening sequencer\n");
        }
    }

    private Sequence createSequence() {
        try {
            Sequence seq = new Sequence(Sequence.PPQ, 1);
            Track track = seq.createTrack();

            ShortMessage msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
            MidiEvent evt = new MidiEvent(msg, 0);
            track.add(evt);

            addNoteEvent(track, 0);
            addNoteEvent(track, 1);
            addNoteEvent(track, 2);
            addNoteEvent(track, 3);

            msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
            evt = new MidiEvent(msg, 4);
            track.add(evt);
            return seq;
        } catch (InvalidMidiDataException ex) {
            return null;
        }
    }

    private void addNoteEvent(Track track, long tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 9, 37, 100);
        MidiEvent event = new MidiEvent(message, tick);
        track.add(event);
    }

    private void startSequence(Sequence seq) throws InvalidMidiDataException {
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM((float)tempo);
        sequencer.start();
    }

    @Override
    public void meta(MetaMessage message) {
        if (message.getType() != 47) {  // 47 is end of track
            return;
        }
        doLoop();
    }

    private void doLoop() {
        if (sequencer == null || !sequencer.isOpen()) {
            return;
        }
        sequencer.setTickPosition(0);
        sequencer.start();
        sequencer.setTempoInBPM((float)tempo);
    }

    public void stop() {
        sequencer = null;
    }
}



