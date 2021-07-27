package bdbr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BDBR {

    /*
        first arg:  input/output folder
        second arg: video variation
        example: D:/videos alt5
     */
    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {
            File folder = new File(args[0]);
            File[] dirs = folder.listFiles();

            if (dirs != null)
                for (File d : dirs)
                    assemble(new Sequence(d), args[1]);
        }
    }

    /*
        this function collects the psnr and bitrate from log files
        then makes a python script to calculate the bdbr
     */
    private static void assemble(Sequence sequence, String variation) {
        try {
            Map<Integer, Details> oriMap = sequence.getDetails("original");
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
