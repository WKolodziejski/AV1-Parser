package vhdl;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Warped {

    /*
        the parameter is a text file containing a list of filters
        separated by spaces, one filter per line
     */
    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No file received");
        else
            assemble(read(new File(args[0])));
    }

    /*
        this function reads the filters array and mounts the vhdl script
     */
    private static void assemble(int[][] filters) {
        try {
            FileWriter writer = new FileWriter("Escolha.vhd");
            writer.append("library ieee;\n" +
                    "use ieee.std_logic_1164.all;\n" +
                    "use IEEE.std_logic_signed.all;\n" +
                    "use ieee.numeric_std.all; \n\n" +
                    "entity Escolha is\n" +
                    "\tport(vet_sel: in std_logic_vector(7 downto 0);\n" +
                    "\t\tS0, S3: out std_logic_vector(2 downto 0);\n" +
                    "\t\tS1, S2: out std_logic_vector(5 downto 0);\n" +
                    "\t\tsinalA0, sinalA1: out std_logic\n" +
                    "\t\t);\n" +
                    "end Escolha;\n\n\n" +
                    "architecture comportamento of Escolha is\n" +
                    "\tsignal aux8: signed (1 downto 0);\n" +
                    "\tbegin\n" +
                    "\tprocess (vet_sel)\n" +
                    "\tbegin\n\n" +
                    "\t\tcase vet_sel is\n\n");

            int f = 1;

            for (int i = 0; i < 192; i++) {
                if (i % 64 == 0) {
                    writer.append("------------------------- Filtro " + f + "------------------------------\n\n");
                    f++;
                }

                writer.append("\t\t\twhen \"" + numToBin(i) + "\" => --" + Arrays.toString(filters[i]) + "\n");

                writer.append("\t\t\t\tS0 <= \"" + numToBin(filters[i][0]) + "\";\n");
                writer.append("\t\t\t\tS1 <= \"" + numToBin(filters[i][1]) + "\";\n");
                writer.append("\t\t\t\tS2 <= \"" + numToBin(filters[i][2]) + "\";\n");
                writer.append("\t\t\t\tS3 <= \"" + numToBin(filters[i][3]) + "\";\n");
                writer.append("\t\t\t\tS4 <= \"" + numToBin(filters[i][4]) + "\";\n");
                writer.append("\t\t\t\tS5 <= \"" + numToBin(filters[i][5]) + "\";\n");
                writer.append("\t\t\t\tS6 <= \"" + numToBin(filters[i][6]) + "\";\n");
                writer.append("\t\t\t\tS7 <= \"" + numToBin(filters[i][7]) + "\";\n");

                writer.append("\t\t\t\tsinalA0 <= " + (filters[i][0] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA1 <= " + (filters[i][1] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA2 <= " + (filters[i][2] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA3 <= " + (filters[i][3] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA4 <= " + (filters[i][4] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA5 <= " + (filters[i][5] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA6 <= " + (filters[i][6] < 0 ? "'1'" : "'0'") + ";\n");
                writer.append("\t\t\t\tsinalA7 <= " + (filters[i][7] < 0 ? "'1'" : "'0'") + ";\n\n");
            }

            writer.append("\t\t\twhen others =>\n" +
                    "\t\t\t\taux8 <= \"00\";\n\t\t\n" +
                    "\t\tend case;\n\n" +
                    "\tend process;\n\n" +
                    "end comportamento;");

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String numToBin(int n) {
        String bin = Integer.toBinaryString(Math.abs(n));
        return String.format("%8s", bin).replaceAll(" ", "0");
    }

    /*
        this function reads the text file and mounts an array of filters
     */
    private static int[][] read(File file) {
        int[][] filters = new int[192][8];
        int i = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] coefs = line.split("\\s+");
                int j = 0;

                for (String coef : coefs) {
                    if (coef.isEmpty())
                        continue;

                    filters[i][j] = Integer.parseInt(coef);
                    j++;
                }

                System.out.println(Arrays.toString(filters[i]));

                i++;
            }

            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", file);
            e.printStackTrace();
        }

        return filters;
    }

    /*
        this section contains the maps for the filter taps
     */

}
