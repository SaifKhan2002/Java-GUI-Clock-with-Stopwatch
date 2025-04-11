# Clock and Stopwatch Application

A Java GUI application that combines a digital clock with a stopwatch featuring lap time functionality.

## Features

- **Digital Clock**: Displays current time in 24-hour format (HH:MM:SS)
- **Stopwatch**: Accurate to milliseconds (HH:MM:SS.mmm)
- **Lap Times**: Records up to 10 lap times with queue-based management
- **Controls**:
  - Start/Pause stopwatch
  - Reset stopwatch and lap times
  - Record lap times while running

## Technical Details

- **Data Structures**: Uses Queue (LinkedList implementation) for efficient lap time management
- **Algorithms**: Efficient time calculations using modulo arithmetic
- **GUI**: Built with Java Swing
- **Precision**: Stopwatch updates every 10ms for accurate timing

## How to Run

1. Ensure you have Java JDK installed (version 8 or higher recommended)
2. Compile the Java file:
   ```bash
   javac ClockWithStopwatch.java


#Run the application
java ClockWithStopwatch


###
Future Enhancements

Add 12-hour format option for the clock

Implement lap time statistics (fastest/slowest/average)

Add sound notifications

Save lap times to file


