/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nema0183_aggregator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.*; // Java Simple Serial Connector, the library that contains the serial methods
import static nema0183_aggregator.RS232Control.serialPort;

/**
 *
 * @author Ian Van Schaick
 */
public class RS232Control {

    static SerialPort serialPort;
    String portName;
    static long portOpen;
    StringBuilder message;
    Boolean receivingMessage;
    SerialPortReader reader;
    String readLine;
    Boolean acknowledge;
    int baud;
    boolean gpsData;
    NEMADateUpdate gpsUpdate;
    String lineSep;

    /**
     * 
     * @param portNum
     * @param portbaud
     * @param gps 
     */
    public RS232Control(String portNum, int portbaud, boolean gps) {
        gpsData = gps;
        if (gpsData == true) {
            gpsUpdate = new NEMADateUpdate ();
        }
        portName = portNum;
        baud = portbaud;
        serialPort = new SerialPort(portName);
        message = new StringBuilder();
        receivingMessage = false;
        reader = new SerialPortReader();
        readLine = "";
        acknowledge = false;
        lineSep = System.getProperty("line.separator");
        openP();
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
    protected ArrayList<String> findPort() {
//        System.out.println("List of COM ports:");
        String[] portNames = SerialPortList.getPortNames();
        ArrayList<String> portList = new ArrayList<>();
        for (String portName1 : portNames) {
//            System.out.println(portName1);
            portList.add(portName1);
        }
//        System.out.println(SerialPort.BAUDRATE_9600 + "");
//        
//        System.out.println(SerialPort.BAUDRATE_4800 + "");
//        
//        System.out.println(SerialPort.BAUDRATE_38400 + "");
//        
//        System.out.println(SerialPort.BAUDRATE_115200 + "");
//        
//        System.out.println("What COM port are you using?");
//        System.out.println("Please type it in how it appears above.");
//        Scanner sc = new Scanner(System.in);
//        String port = "";
//        if (sc.hasNext()) {
//            port = sc.next();
//        } else {
//
//        }
        return portList;
    }
    
    /**
     * Checks if the serial port is connected
     * @return Returns true if any of the serial ports found using getPortNames() 
     * matches the portName global variable (what ever the user types in when 
     * findPort() is called).
     */
    protected boolean serialConnected () {
        boolean connected = false;
        String[] portNames = SerialPortList.getPortNames();
        for (String portName1 : portNames) {
            if (portName1.equals(portName) ) {
                connected = true;
//                System.out.println("Connected successfully to serial port: " + portName);
            }
            else {
//                System.out.println("Can not connect to serial port: " + portName);
            }
        }
        return connected;
    }
    
    protected void changePort (String portNum, int portbaud, boolean gps) {
        close();
        gpsData = gps;
        if (gpsData == true) {
            gpsUpdate = new NEMADateUpdate ();
        }
        portName = portNum;
        baud = portbaud;
        serialPort = new SerialPort(portName);
        message = new StringBuilder();
        receivingMessage = false;
        reader = new SerialPortReader();
        readLine = "";
        acknowledge = false;
        lineSep = System.getProperty("line.separator");
        openP();
    }
    
    /**
     * Opens a COM port at the specified settings (baudrate 8N1)
     * Can throw an error opening the port
     */
    private void openP() {
        try {
            serialPort.openPort();
            serialPort.setParams(baud,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            int mask = SerialPort.MASK_RXCHAR;
            serialPort.setEventsMask(mask);
            serialPort.addEventListener(reader);
            serialPort.setRTS(false);
            serialPort.setDTR(false);
            acknowledge = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("There is an error opening port т: " + ex);
        }
    }
    
    /**
     * Closes the serial port, can throw a SerialPortException error.
     *
     * @return
     */
    private boolean close() {
        boolean success = false;
        try {
            serialPort.closePort();
            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    /**
     * Opens the serial port. Tries to read a string from the serial port.
     * Closes the serial port.
     *
     * @return Returns the byte array read from the serial port.
     */
    protected byte [] testRead() {
        byte [] readArray = null;
        ArrayList <byte []> readList = new ArrayList <byte []> ();
        for (int i = 0; i < 100; i++) {
            byte [] tempArray = null;
            try {
                readList.add(serialPort.readBytes(10) );
            } catch (SerialPortException ex) {
                Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String line = new String (readArray);
        System.out.println(line);
//        if (gpsData == true) {
//            readArray = gpsUpdate.dateUpdate(readArray);
//        }
        return readArray;
    }
    
    /**
     * Opens the serial port. Tries to read a string from the serial port.
     * Closes the serial port.
     *
     * @return Returns the byte array read from the serial port.
     */
    protected String testRead2() {
        String line = "";
        ArrayList <String> readList = new ArrayList <String> ();
        boolean lineFin = false;
        for (int i = 0; i < 100 && (!lineFin); i++) {
            try {
                //            byte [] tempArray = null;
                //                readList.add(serialPort.readBytes(8) );
                line =  line + serialPort.readString(1);
            } catch (SerialPortException ex) {
                Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (line.endsWith(lineSep)) {
                lineFin = true;
            }
        if (gpsData == true) {
            line = gpsUpdate.dateUpdate(line);
        }
    }
        return line;
    }
    
    /**
     * Writes the String message to the serial port
     *
     * @param message The string to write to the serial port
     * @return Returns true if the write was successful
     */
    protected boolean testWrite(String message) {
        boolean success = false;
        
        try {
//            openP();
            if ( (!message.isBlank() ) && message.startsWith("$") ) {
                success = serialPort.writeString(message);
            }
//            serialPort.closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    /**
     * Writes the String message to the serial port
     *
     * @param message The string to write to the serial port
     * @return Returns true if the write was successful
     */
    protected boolean testWrite(byte [] message) {
        boolean success = false;
        
        try {
//            openP();
            serialPort.writeBytes(message);
            success = true;
//            serialPort.closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(RS232Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
} // End of RS232Control class

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
        if (event.isRXCHAR() && event.getEventValue() == 10) {
            try {
//                String line = serialPort.readString(event.getEventValue());
                byte buffer[] = serialPort.readBytes(10);
//                    acknowledgeStr + acknowledgeStr + 
//                System.out.println("serialEvent: " + line);
//                if (line.contains((char) 0x6 + "")) {
//                    acknowledge2 = true;
//                    System.out.println("Acknowledged");
//
//                } else {
//                    acknowledge2 = false;
//                }
//                    System.out.println("Received response: " + readLine);

            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }
    
    /**
     * Prints out the message read from the serial port
     *
     * @param message
     */
    protected void processMessage(String message) {
//        System.out.println(message);
    }
}
