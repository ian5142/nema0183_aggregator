/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nema0183_aggregator;

import jssc.*; // Java Simple Serial Connector, the library that contains the serial methods
import static nema0183_aggregator.serialReader.serialPort;

/**
 *
 * @author Ian Van Schaick
 */
public class serialReader {

    static SerialPort serialPort;
    String portName;
    static long portOpen;
    StringBuilder message;
    Boolean receivingMessage;
    SerialPortReader reader;
    String readLine;

    public serialReader() {
        portName = "/dev/ttyUSB0";
        serialPort = new SerialPort(portName);
        message = new StringBuilder();
        receivingMessage = false;
        reader = new SerialPortReader();
        readLine = "";
    }
}

/**
 * In this class must implement the method serialEvent, through it we learn
 * about events that happened to our port. But we will not report on all events
 * but only those that we put in the mask. In this case the arrival of the data
 * and change the status lines CTS and DSR
 */
class SerialPortReader implements SerialPortEventListener {

    /**
     * Reads the data bit by bit from the serial port Can throw a
     * SerialPortException error
     *
     * @param event
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        boolean acknowledge;
        if (event.isRXCHAR() && event.getEventValue() == 10) {
            try {
                String line = serialPort.readString(event.getEventValue());
//                    acknowledgeStr + acknowledgeStr + 
                System.out.println("serialEvent: " + line);
                if (line.contains((char) 0x6 + "")) {
                    acknowledge = true;
                    System.out.println("Acknowledged");

                } else {
                    acknowledge = false;
                }
//                    System.out.println("Received response: " + readLine);

            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }
}
