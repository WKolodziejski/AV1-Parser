package vhdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Assembler {
    private final Map<Integer, String> a0;
    private final Map<Integer, String> a1;
    private final Map<Integer, String> a2;

    public Assembler(int[][] filters) {
        a0 = new HashMap<>();
        a0.put(0, "000");
        a0.put(2, "001");
        a0.put(-2, "001");
        a0.put(4, "010");
        a0.put(-4, "010");
        a0.put(8, "011");
        a0.put(-8, "011");
        a0.put(16, "100");
        a0.put(-16, "100");
        a0.put(32, "101");
        a0.put(-32, "101");

        a1 = new HashMap<>();
        a1.put(8, "000000");
        a1.put(-8, "000000");
        a1.put(16, "000001");
        a1.put(-16, "000001");
        a1.put(20, "000010");
        a1.put(-20, "000010");
        a1.put(32, "000011");
        a1.put(-32, "000011");
        a1.put(44, "000100");
        a1.put(-44, "000100");
        a1.put(64, "000101");
        a1.put(-64, "000101");
        a1.put(84, "000110");
        a1.put(-84, "000110");
        a1.put(108, "000111");
        a1.put(-108, "000111");
        a1.put(128, "001001");
        a1.put(-128, "001001");

        a2 = new HashMap<>();
        a2.put(8, "000000");
        a2.put(-8, "000000");
        a2.put(16, "000001");
        a2.put(-16, "000001");
        a2.put(20, "000010");
        a2.put(-20, "000010");
        a2.put(32, "000011");
        a2.put(-32, "000011");
        a2.put(44, "000100");
        a2.put(-44, "000100");
        a2.put(64, "000101");
        a2.put(-64, "000101");
        a2.put(84, "000110");
        a2.put(-84, "000110");
        a2.put(108, "000111");
        a2.put(-108, "000111");
        a2.put(0, "001001");

        assemble(filters);
    }

    private void assemble(int[][] filters) {
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

            for (int i = 0; i < 96; i++) {
                if (i % 16 == 0) {
                    writer.append("------------------------- Filtro " + f + "------------------------------\n\n");
                    f++;
                }

                String bin = Integer.toBinaryString(i);
                bin = String.format("%8s", bin).replaceAll(" ", "0");

                writer.append("\t\t\twhen \"" + bin + "\" => --" + Arrays.toString(filters[i]) + "\n");

                writer.append("\t\t\t\tS0 <= \"" + a0.get(filters[i][0]) + "\";\n");

                writer.append("\t\t\t\tsinalA0 <= " + (filters[i][0] < 0 ? "'1'" : "'0'") + ";\n");

                writer.append("\t\t\t\tS1 <= \"" + a1.get(filters[i][1]) + "\";\n");

                writer.append("\t\t\t\tS2 <= \"" + a2.get(filters[i][2]) + "\";\n");

                writer.append("\t\t\t\tS3 <= \"" + a0.get(filters[i][3]) + "\";\n");

                writer.append("\t\t\t\tsinalA1 <= " + (filters[i][0] < 0 ? "'1'" : "'0'") + ";\n\n");
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

}
