/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nema0183_aggregator;

import java.util.ArrayList;

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
        serialReader reader1 = new serialReader();
        ArrayList<String> portList = reader1.findPort();
        
        System.out.println("Ports: ");
        for (String str : portList) {
            System.out.println(str);
        }
    }
    
}
