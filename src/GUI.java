import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private static JTextField filenameField = new JTextField(25);
    private static JTextField lengthField = new JTextField(25);
    private static JTextField tempoField = new JTextField(25);
    private static JTextField numField = new JTextField(25);
    private static JTextField denomField = new JTextField(25);
    private static JTextField rhythmField = new JTextField(25);
    private static JTextField instrumentField = new JTextField(25);
    private static JFrame window = new JFrame();
    private static JPanel output = new JPanel();
    private static JPanel content = new JPanel();
    public static JTextArea textArea = new JTextArea();

    private static class AccuracyHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changePane(output);
            RhythmAccuracy.runAccuracyTest(filenameField.getText());
        }
    }

    private static class RandomHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changePane(output);
            RandomPatterns.randomPatterns(Double.parseDouble(tempoField.getText()),
                Integer.parseInt(lengthField.getText()),
                Integer.parseInt(numField.getText()),
                Integer.parseInt(denomField.getText()), filenameField.getText(), Integer.parseInt(instrumentField.getText()));
        }
    }

    private static class CustomHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changePane(output);
            CustomPattern.customPattern(Double.parseDouble(tempoField.getText()),
                    Integer.parseInt(lengthField.getText()),
                    Integer.parseInt(numField.getText()),
                    Integer.parseInt(denomField.getText()),
                    rhythmField.getText().split(","), filenameField.getText(), Integer.parseInt(instrumentField.getText()));
        }
    }

    private static class PlaybackHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changePane(output);
            Playback.playback(filenameField.getText());
        }
    }

    private static class HalfHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (rhythmField.getText().length() != 0) {
                rhythmField.setText(rhythmField.getText() + ",2.0");
            } else {
                rhythmField.setText("2.0");
            }
        }
    }

    private static class QuarterHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (rhythmField.getText().length() != 0) {
                rhythmField.setText(rhythmField.getText() + ",1.0");
            } else {
                rhythmField.setText("1.0");
            }
        }
    }

    private static class EighthHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (rhythmField.getText().length() != 0) {
                rhythmField.setText(rhythmField.getText() + ",0.5");
            } else {
                rhythmField.setText("0.5");
            }
        }
    }

    private static class TripletHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (rhythmField.getText().length() != 0) {
                rhythmField.setText(rhythmField.getText() + ",0.333");
            } else {
                rhythmField.setText("0.333");
            }
        }
    }

    private static class SixteenthHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (rhythmField.getText().length() != 0) {
                rhythmField.setText(rhythmField.getText() + ",0.25");
            } else {
                rhythmField.setText("0.25");
            }
        }
    }

    private static class DeleteHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = rhythmField.getText();
            for (int i = text.length() - 1; i >= 0; i--) {
                if (text.charAt(i) == ',') {
                    rhythmField.setText(text.substring(0, i));
                    return;
                }
            }
            rhythmField.setText("");

        }
    }

    private static class BackHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changePane(content);
            textArea.setText("");
        }
    }

    private static void changePane(JPanel pane) {
        window.setContentPane(pane);
        window.setVisible(true);
    }

    public static void main(String[] args) {

        content.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Rhythm Helper");
        titleLabel.setPreferredSize(new Dimension(240, 30));
        titleLabel.setHorizontalAlignment(0);
        content.add(titleLabel, BorderLayout.PAGE_START);

        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(200, 80));
        content.add(textPanel, BorderLayout.LINE_START);

        JLabel filenameLabel = new JLabel("Filename");
        filenameLabel.setPreferredSize(new Dimension(200, 30));
        filenameLabel.setHorizontalAlignment(4);
        textPanel.add(filenameLabel);

        JLabel tempoLabel = new JLabel("Tempo");
        tempoLabel.setPreferredSize(new Dimension(200, 30));
        tempoLabel.setHorizontalAlignment(4);
        textPanel.add(tempoLabel);

        JLabel lengthLabel = new JLabel("Length");
        lengthLabel.setPreferredSize(new Dimension(200, 30));
        lengthLabel.setHorizontalAlignment(4);
        textPanel.add(lengthLabel);

        JLabel numLabel = new JLabel("Numerator");
        numLabel.setPreferredSize(new Dimension(200, 30));
        numLabel.setHorizontalAlignment(4);
        textPanel.add(numLabel);

        JLabel denomLabel = new JLabel("Denominator");
        denomLabel.setPreferredSize(new Dimension(200, 30));
        denomLabel.setHorizontalAlignment(4);
        textPanel.add(denomLabel);

        JLabel customLabel = new JLabel("Custom Rhythms");
        customLabel.setPreferredSize(new Dimension(200, 30));
        customLabel.setHorizontalAlignment(4);
        textPanel.add(customLabel);

        JLabel instrumentLabel = new JLabel("Instrument");
        instrumentLabel.setPreferredSize(new Dimension(200, 30));
        instrumentLabel.setHorizontalAlignment(4);
        textPanel.add(instrumentLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 120));
        content.add(buttonPanel, BorderLayout.LINE_END);

        JPanel rhythmButtonPanel = new JPanel();
        rhythmButtonPanel.setPreferredSize(new Dimension(600, 120));
        content.add(rhythmButtonPanel, BorderLayout.PAGE_END);

        JButton accuracyButton = new JButton("Run accuracy test");
        ActionListener listener = new AccuracyHandler();
        accuracyButton.addActionListener(listener);
        buttonPanel.add(accuracyButton);

        JButton randomButton = new JButton("Create Random Pattern");
        listener = new RandomHandler();
        randomButton.addActionListener(listener);
        buttonPanel.add(randomButton);

        JButton customButton = new JButton("Create Custom Pattern");
        listener = new CustomHandler();
        customButton.addActionListener(listener);
        buttonPanel.add(customButton);

        JButton playbackButton = new JButton("Playback Pattern");
        listener = new PlaybackHandler();
        playbackButton.addActionListener(listener);
        buttonPanel.add(playbackButton);

        JButton halfButton = new JButton("Half");
        listener = new HalfHandler();
        halfButton.addActionListener(listener);
        rhythmButtonPanel.add(halfButton);

        JButton quarterButton = new JButton("Quarter");
        listener = new QuarterHandler();
        quarterButton.addActionListener(listener);
        rhythmButtonPanel.add(quarterButton);

        JButton eighthButton = new JButton("Eighth");
        listener = new EighthHandler();
        eighthButton.addActionListener(listener);
        rhythmButtonPanel.add(eighthButton);

        JButton tripletButton = new JButton("Triplet");
        listener = new TripletHandler();
        tripletButton.addActionListener(listener);
        rhythmButtonPanel.add(tripletButton);

        JButton sixteenthButton = new JButton("Sixteenth");
        listener = new SixteenthHandler();
        sixteenthButton.addActionListener(listener);
        rhythmButtonPanel.add(sixteenthButton);

        JButton deleteButton = new JButton("Delete");
        listener = new DeleteHandler();
        deleteButton.addActionListener(listener);
        rhythmButtonPanel.add(deleteButton);

        JPanel panelForTextFields = new JPanel();
        panelForTextFields.setPreferredSize(new Dimension(100, 70));
        content.add(panelForTextFields, BorderLayout.CENTER);

        filenameField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(filenameField);
        tempoField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(tempoField);
        lengthField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(lengthField);
        numField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(numField);
        denomField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(denomField);
        rhythmField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(rhythmField);
        instrumentField.setPreferredSize(new Dimension(70, 30));
        panelForTextFields.add(instrumentField);

        output.setLayout(new BorderLayout());

        titleLabel = new JLabel("Output");
        titleLabel.setPreferredSize(new Dimension(240, 30));
        titleLabel.setHorizontalAlignment(0);
        output.add(titleLabel, BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane(textArea);
        output.add(scrollPane);

        JButton backButton = new JButton("Back");
        listener = new BackHandler();
        backButton.addActionListener(listener);
        output.add(backButton, BorderLayout.LINE_END);

        window.setContentPane(content);
        window.setSize(720,480);
        window.setLocation(100,100);
        window.setVisible(true);


    }

}
