import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.List;

public class StrokeListener implements NativeKeyListener {
    private int numNotes;
    private List<Long> playedNotes = new ArrayList<>();

    StrokeListener(int numNotes) {
        this.numNotes = numNotes;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("A")) {
            long time = System.currentTimeMillis();
            if (playedNotes.size() < numNotes) {
                playedNotes.add(time);
                GUI.textArea.append(String.format("Left %d %n\n", time));
            }
        } else if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("L")) {
            long time = System.currentTimeMillis();
            if (playedNotes.size() < numNotes) {
                playedNotes.add(time);
                GUI.textArea.append(String.format("Right %d %n\n", time));
            }

        }
        GUI.textArea.append(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()) + "\n");
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    protected List<Long> getPlayedNotes() {
        return playedNotes;
    }

}
