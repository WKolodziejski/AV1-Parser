package vhdl;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FME {

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
        initMaps();

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

            for (int i = 0; i < 48; i++) {
                if (i % 8 == 0) {
                    writer.append("\n------------------------- Filtro " + f + "------------------------------\n\n");
                    f++;
                }

                String bin = Integer.toBinaryString(i);
                bin = String.format("%8s", bin).replaceAll(" ", "0");

                writer.append("\t\t\twhen \"" + bin + "\" => --" + Arrays.toString(filters[i]) + "\n");

                int j = 0;

                for ( ; j < (filters[0].length / 2) - 1; j++) {
                    writer.append("\t\t\t\tS" + j + " <= \"" + p.get(filters[i][j]) + "\";\n");
                }

                writer.append("\t\t\t\tS" + j + " <= \"" + c.get(filters[i][j++]) + "\";\n");
                writer.append("\t\t\t\tS" + j + " <= \"" + c.get(filters[i][j++]) + "\";\n");

                for ( ; j < filters[0].length; j++) {
                    writer.append("\t\t\t\tS" + j + " <= \"" + p.get(filters[i][j]) + "\";\n");
                }

                for (j = 0 ; j < (filters[0].length / 2) - 1; j++) {
                    writer.append("\t\t\t\tsinalA" + j + " <= " + (filters[i][j] < 0 ? "'1'" : "'0'") + ";\n");
                }

                for (j = (filters[0].length / 2) + 1; j < filters[0].length; j++) {
                    writer.append("\t\t\t\tsinalA" + j + " <= " + (filters[i][j] < 0 ? "'1'" : "'0'") + ";\n");
                }
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

    /*
        this function reads the text file and mounts an array of filters
     */
    private static int[][] read(File file) {
        int[][] filters = new int[48][8];
        int i = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] coefs = line.split("\\s+");
                int j = 0;

                if (coefs.length == 0)
                    continue;

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
    private final static Map<Integer, String> p = new HashMap<>();
    private final static Map<Integer, String> c = new HashMap<>();

    private static void initMaps() {
        p.put(0, "000");
        p.put(2, "001");
        p.put(-2, "001");
        p.put(4, "010");
        p.put(-4, "010");
        p.put(8, "011");
        p.put(-8, "011");
        p.put(16, "100");
        p.put(-16, "100");
        p.put(32, "101");
        p.put(-32, "101");

        c.put(8, "000000");
        c.put(-8, "000000");
        c.put(16, "000001");
        c.put(-16, "000001");
        c.put(20, "000010");
        c.put(-20, "000010");
        c.put(32, "000011");
        c.put(-32, "000011");
        c.put(44, "000100");
        c.put(-44, "000100");
        c.put(64, "000101");
        c.put(-64, "000101");
        c.put(84, "000110");
        c.put(-84, "000110");
        c.put(108, "000111");
        c.put(-108, "000111");
        c.put(128, "001001");
        c.put(-128, "001001");
        c.put(0, "001001");
    }

}
