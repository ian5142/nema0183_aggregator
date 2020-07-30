/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nema0183_aggregator;

import java.util.Scanner;
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
    Boolean acknowledge;

    public serialReader() {
        portName = "/dev/ttyUSB0";
        serialPort = new SerialPort(portName);
        message = new StringBuilder();
        receivingMessage = false;
        reader = new SerialPortReader();
        readLine = "";
        acknowledge = false;
    }

    /**
     * Finds the serial port to be used, in Windows type COM1, for example In
     * Linux, type /dev/pts/3 for example. The custom USB-RS232 device, using a
     * MCP2200, is on /dev/ttyACM0/
     * All serial ports may not be listed.
     *
     * @return The serial port name in String format, used to open and close the
     * port
     */
    private String findPort() {
        System.out.println("List of COM ports:");
        String[] portNames = SerialPortList.getPortNames();
        for (String portName1 : portNames) {
            System.out.println(portName1);
        }
        
        System.out.println("What COM port are you using?");
        System.out.println("Please type it in how it appears above.");
        Scanner sc = new Scanner(System.in);
        String port = "";
        if (sc.hasNext()) {
            port = sc.next();
        } else {

        }
        return port;
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
        boolean acknowledge2;
        if (event.isRXCHAR() && event.getEventValue() == 10) {
            try {
                String line = serialPort.readString(event.getEventValue());
//                    acknowledgeStr + acknowledgeStr + 
                System.out.println("serialEvent: " + line);
                if (line.contains((char) 0x6 + "")) {
                    acknowledge2 = true;
                    System.out.println("Acknowledged");

                } else {
                    acknowledge2 = false;
                }
//                    System.out.println("Received response: " + readLine);

            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }
}
