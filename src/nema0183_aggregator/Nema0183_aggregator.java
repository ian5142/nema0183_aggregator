/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nema0183_aggregator;

import java.util.ArrayDeque;

import java.util.logging.Level;
import java.util.logging.Logger;

//import java.util.ArrayList;

/**
 * This class contains the main method. It acts as a connector between the various read and single write classes.
 * @author Ian Van Schaick
 */
public class Nema0183_aggregator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayDeque <String> sentences = new ArrayDeque <String> ();
//        ArrayList <RS232Control> devices = new ArrayList<RS232Control> (5);
        RS232Control gps = new RS232Control("COM32", 4800, true);
//        RS232Control ais = new RS232Control("COM35", 34800, false);
//        RS232Control heading = new RS232Control("COM37", 4800, false);
//        RS232Control sounder = new RS232Control("COM39", 4800, false);
//        RS232Control output = new RS232Control("COM41", 115200, false);
//        for (int i = 0 ; i > 100 ; i++) {
        while (true) {
            String line = gps.testRead2();
            sentences.add(line);
            gps.changePort("COM41", 115200, false);
            gps.testWrite(line);
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0 ; (i < 8) && (!line.startsWith("$GPGLL")) ; i++ ) {
                gps.changePort("COM32", 4800, true);
                line = gps.testRead2();
                sentences.add(line);
                gps.changePort("COM41", 115200, false);
                gps.testWrite(line);
    //            System.out.print(line);
    //            line = gps.testRead2();
    //            System.out.print(line);
    //            sentences.add(line);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            gps.changePort("COM39", 4800, false);
            line = gps.testRead2();
            sentences.add(line);
            gps.changePort("COM41", 115200, false);
            gps.testWrite(line);
            
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            gps.changePort("COM32", 4800, true);
//            sentences.add(line);
//            gps.changePort("COM41", 115200, false);
//            gps.testWrite(line);
//            
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            gps.changePort("COM32", 4800, true);
//            sentences.add(line);
//            gps.changePort("COM41", 115200, false);
//            gps.testWrite(line);
//            gps.changePort("COM41", 115200, false);
//            for (String line2 : sentences) {
//                line2 = gps.testRead2();
//            }
            
            gps.changePort("COM32", 4800, true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
            }
//                    sentences.removeFirst() );
//            
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            line = ais.testRead2();
//            System.out.print(line);
//            sentences.addFirst(line);
//            boolean b = output.testWrite("Hello World!" + (char) 0xD + (char) 0x10);
//            System.out.println("Output: " + b);
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            line = heading.testRead2();
//            System.out.print(line);
//            sentences.addFirst(line);
//            output.testWrite(sentences.removeFirst() );
//            
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            line = sounder.testRead2();
//            System.out.print(line);
//            sentences.addFirst(line);
//            output.testWrite(sentences.removeFirst() );
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
//        ArrayList<String> portList = gps.findPort();
        
//        System.out.println("Ports: ");
//        portList.forEach(str -> {
//            System.out.println(str);
//        });
    }
    
}
