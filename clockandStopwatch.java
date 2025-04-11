import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class ClockWithStopwatch extends JFrame {
    private JLabel clockLabel;
    private JLabel stopwatchLabel;
    private JButton startButton, stopButton, resetButton, lapButton;
    private Timer stopwatchTimer, clockTimer;
    private long startTime = 0;
    private long elapsedTime = 0;
    private boolean isRunning = false;
    private Queue<String> lapTimes = new LinkedList<>();
    private JTextArea lapTextArea;

    public ClockWithStopwatch() {
        setTitle("Clock & Stopwatch");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Clock Panel
        JPanel clockPanel = new JPanel();
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 32));
        clockPanel.add(clockLabel);
        add(clockPanel, BorderLayout.NORTH);

        // Stopwatch Panel
        JPanel stopwatchPanel = new JPanel(new BorderLayout());
        stopwatchLabel = new JLabel("00:00:00.000");
        stopwatchLabel.setFont(new Font("Arial", Font.BOLD, 24));
        stopwatchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stopwatchPanel.add(stopwatchLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        lapButton = new JButton("Lap");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(lapButton);

        stopwatchPanel.add(buttonPanel, BorderLayout.CENTER);

        // Lap Times Panel
        lapTextArea = new JTextArea(10, 30);
        lapTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(lapTextArea);
        stopwatchPanel.add(scrollPane, BorderLayout.SOUTH);

        add(stopwatchPanel, BorderLayout.CENTER);

        // Initialize timers
        initClock();
        initStopwatch();

        // Button actions
        startButton.addActionListener(e -> startStopwatch());
        stopButton.addActionListener(e -> stopStopwatch());
        resetButton.addActionListener(e -> resetStopwatch());
        lapButton.addActionListener(e -> recordLapTime());
    }

    private void initClock() {
        clockTimer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            clockLabel.setText(sdf.format(new Date()));
        });
        clockTimer.start();
    }

    private void initStopwatch() {
        stopwatchTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
                updateStopwatchLabel(elapsedTime);
            }
        });
    }

    private void updateStopwatchLabel(long time) {
        long hours = time / 3600000;
        long minutes = (time % 3600000) / 60000;
        long seconds = (time % 60000) / 1000;
        long millis = time % 1000;

        String formattedTime = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
        stopwatchLabel.setText(formattedTime);
    }

    private void startStopwatch() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            stopwatchTimer.start();
            isRunning = true;
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            stopwatchTimer.stop();
            isRunning = false;
        }
    }

    private void resetStopwatch() {
        stopwatchTimer.stop();
        elapsedTime = 0;
        startTime = 0;
        isRunning = false;
        stopwatchLabel.setText("00:00:00.000");
        lapTimes.clear();
        lapTextArea.setText("");
    }

    private void recordLapTime() {
        if (isRunning) {
            String lapTime = stopwatchLabel.getText();
            lapTimes.add(lapTime);
            
            // Keep only the last 10 lap times (using queue to manage size)
            if (lapTimes.size() > 10) {
                lapTimes.remove();
            }
            
            // Update lap times display
            StringBuilder lapDisplay = new StringBuilder();
            int lapNumber = 1;
            for (String lap : lapTimes) {
                lapDisplay.append("Lap ").append(lapNumber++).append(": ").append(lap).append("\n");
            }
            lapTextArea.setText(lapDisplay.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClockWithStopwatch app = new ClockWithStopwatch();
            app.setVisible(true);
        });
    }
}