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

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Ian Van Schaick
 */
public class NEMADateUpdate {
    Year year;
    String day;
    String month;
    public NEMADateUpdate () {
        year = Year.now(); 
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter dayformat = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthformat = DateTimeFormatter.ofPattern("MM");
        day = myDateObj.format(dayformat);
        month = myDateObj.format(monthformat);
    }
    
    protected String dateUpdate(String line) {
        String Unedited = line;
        String Updated = "";
//        System.out.print("Unedited" + Unedited);
//        System.out.println("Length2: " + Unedited.length());
        if (Unedited.startsWith("$GPZDA,") && (Unedited.length() == 34)) {
            String [] list = Unedited.split(",");
//            System.out.println("Length: " + list.length);
////            if (list.length == 7) {
                list[2] = day;
                list[3] = month;
                list[4] = year.toString();
                int index = 0;
                int length = list.length;
                for (String line2 : list) {
//                    System.out.println("Line2: " + line2);
                    if (index + 1 == length) {
                        Updated += line2;
                    }
                    else {
                        Updated += line2 + ",";
                    }
                    index++;
                }
                String [] list2 = Updated.split("\\*");
                list2 [1] = calculateChksum(list2 [0]);
                Updated = list2[0] + "*" + list2[1];
//            }
//            
        }
        else {
            Updated = Unedited;
        }
        
//        System.out.print("Updated " + Updated);
        return Updated;
    }
    
    /**
     * Calculates the checksum from a given String body
     *
     * @param body The string to calculate a checksum for
     * @return The checksum in String format
     */
    private String calculateChksum(String line) {
        long sum = 0;
        ArrayList<String> bodyArray = toHex(line);

        long hexBody = 0;
        for (int i = 0; i < bodyArray.size(); i++) {
            long l = Long.parseLong(bodyArray.get(i) + "", 16);
            hexBody += l;
        }

        sum += hexBody;
        sum = sum % 256;

        String checksum = Long.toHexString(sum);
        checksum = checksum.toUpperCase();

//        System.out.println("This is the final sum in hex: " + checksum);
        return checksum;
    }
    
    /**
     * Converts a String to a hexadecimal value
     *
     * @param arg The string to be converted
     * @return The string in hexadecimal format
     */
    private ArrayList<String> toHex(String arg) {
        String s;
        s = String.format("%040x", new BigInteger(1, arg.getBytes(StandardCharsets.US_ASCII)));
        ArrayList<String> hexBody = new ArrayList<>(s.length());

        char[] hexbody2 = s.toCharArray();

        int length = s.length();

        for (int i = 0; i < length;) {
            hexBody.add(s.substring(i, i + 2));
            i += 2;
        }
        return hexBody;
    }
}
