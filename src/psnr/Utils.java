package psnr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {
    public static final String ORIGINAL = "original";
    public static final String ORIGINAL2 = "v2";
    public static final String ALT1 = "alt1";
    public static final String ALT2 = "alt2";
    public static final String ALT3 = "alt3";
    public static final String ALT4 = "alt4";
    public static final String ALT5 = "alt5";

    public static String read(File file) {
        String s = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null)
                s = line;

            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", file);
            e.printStackTrace();
        }

        return s;
    }

    public static void createFile(Sequence sequence) {
        //createFileForVariation(sequence, ALT1);
        //createFileForVariation(sequence, ALT2);
        //createFileForVariation(sequence, ALT3);
        //createFileForVariation(sequence, ALT4);
        createFileForVariation(sequence, ALT5);
    }

    public static void printPSNR(Sequence sequence) {
        Map<Integer, Details> oriMap = sequence.getDetails(ORIGINAL);
        List<Details> oriList = new ArrayList<>(oriMap.values());

        float sum = 0;

        for (Details d : oriList) {
            sum += d.getPsnrs()[0];
        }

        System.out.println(sum);
    }

    private static void createFileForVariation(Sequence sequence, String variation) {
        try {
            Map<Integer, Details> oriMap = sequence.getDetails(ORIGINAL);
            Map<Integer, Details> altMap = sequence.getDetails(variation);

            if (oriMap != null && altMap != null) {
                String path = sequence.getFolder().getParent();

                FileWriter writer = new FileWriter(path + "\\" + sequence.getName() + "_" + variation + ".py");

                writer.append("from bjontegaard_metric import *\n");
                writer.append("print ('" + sequence.getName()+ "_" + variation + "')\n");

                List<Details> oriList = new ArrayList<>(oriMap.values());
                List<Details> altList = new ArrayList<>(altMap.values());

                if (oriList.size() == altList.size()) {
                    String r1 = "R1 = np.array([";
                    String psnr1 = "PSNR1 = np.array([";

                    String r2 = "R2 = np.array([";
                    String psnr2 = "PSNR2 = np.array([";

                    for (int i = 0; i < oriList.size(); i++) {
                        r1 = r1.concat(oriList.get(i).getBps() + ", ");
                        psnr1 = psnr1.concat(oriList.get(i).getPsnrs()[0] + ", ");

                        r2 = r2.concat(altList.get(i).getBps() + ", ");
                        psnr2 = psnr2.concat(altList.get(i).getPsnrs()[0] + ", ");
                    }

                    r1 = r1.substring(0, r1.length() - 2).concat("])\n");
                    psnr1 = psnr1.substring(0, psnr1.length() - 2).concat("])\n");

                    r2 = r2.substring(0, r2.length() - 2).concat("])\n");
                    psnr2 = psnr2.substring(0, psnr2.length() - 2).concat("])\n");

                    writer.append(r1);
                    writer.append(psnr1);

                    writer.append(r2);
                    writer.append(psnr2);

                    writer.append("print (BD_RATE(R1, PSNR1, R2, PSNR2, 1))");

                    writer.flush();
                    writer.close();
                } else {
                    System.err.println("Logs don't have the same size");
                }
            } else {
                System.err.println("Logs missing for " + sequence.getName() + "_" + variation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
