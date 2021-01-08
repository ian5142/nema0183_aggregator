/*
 * Copyright (C) 2021 Ian Van Schaick
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nema0183_aggregator;

import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.logging.Level;
import java.util.logging.Logger;

//import java.util.ArrayList;
/**
 * This class contains the main method. It acts as a connector between the
 * various read and single write classes.
 *
 * @author Ian Van Schaick
 */
public class Nema0183_aggregator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayDeque<String> gpsSentences = new ArrayDeque<String>();
        ArrayDeque<String> aisSentences = new ArrayDeque<String>();
        ArrayDeque<String> headingSentences = new ArrayDeque<String>();
        ArrayDeque<String> sounderSentences = new ArrayDeque<String>();
        ArrayDeque<String> outputSentences = new ArrayDeque<String>();
//        ArrayList <RS232Control> devices = new ArrayList<RS232Control> (5);
        RS232Control gps = new RS232Control("COM32", 4800, true);
        RS232Control ais = new RS232Control("COM35", 34800, false);
        RS232Control heading = new RS232Control("COM37", 4800, false);
        RS232Control sounder = new RS232Control("COM39", 4800, false);
        RS232Control output = new RS232Control("COM41", 115200, false);
        gps.run();
//        gps.start();
        while (true) {
            System.out.println(gps.getLine());
//            gpsSentences.add(gps.getLine());
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Runnable gpsRun = () -> {
//            gpsSentences.add(gps.testRead2());
//        };
//        executor.submit(gpsRun);
//        executor.submit(() -> {
//            gpsSentences.add(gps.testRead2());
//        });
//        Runnable aisRun = () -> {
//            aisSentences.add(ais.testRead2());
//        };
//        executor.submit(aisRun);
//        
//        Runnable headingRun = () -> {
//            headingSentences.add(heading.testRead2());
//        };
//        executor.submit(headingRun);
//        
//        Runnable sounderRun = () -> {
//            sounderSentences.add(sounder.testRead2());
//        };
//        executor.submit(sounderRun);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
        }
//        executor.shutdown();
//        try {
//            executor.awaitTermination(1 * 10000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//        }
//        for (int i = 0 ; i < 5 ; i++) {
//            if (!(gpsSentences.isEmpty())) {
//                String line = gpsSentences.removeFirst();
//                System.out.println(line);
//                output.testWrite(line);
//            }
        }
//        try {
//            Thread.sleep(2 * 1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
//        for (int i = 0 ; i > 100 ; i++) {
//        while (true) {
//            String line = gps.testRead2();
//            gpsSentences.add(line);
//            gps.changePort("COM41", 115200, false);
//            gps.testWrite(line);
//            
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            for (int i = 0 ; (i < 8) && (!line.startsWith("$GPGLL")) ; i++ ) {
//                gps.changePort("COM32", 4800, true);
//                line = gps.testRead2();
//                sentences.add(line);
//                gps.changePort("COM41", 115200, false);
//                gps.testWrite(line);
//            System.out.print(line);
//            line = gps.testRead2();
//            System.out.print(line);
//            sentences.add(line);
//
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            gps.changePort("COM39", 4800, false);
//            line = gps.testRead2();
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
//            
//            gps.changePort("COM32", 4800, true);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Nema0183_aggregator.class.getName()).log(Level.SEVERE, null, ex);
//            }
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
//        ArrayList<String> portList = gps.findPort();

//        System.out.println("Ports: ");
//        portList.forEach(str -> {
//            System.out.println(str);
//        });
//    }
}
