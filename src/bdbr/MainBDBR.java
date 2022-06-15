package bdbr;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainBDBR {

    /*
        first arg: folder
        second arg: reference folder
        third args: list of folders to process
        example: D:/videos original alt1 alt2 ... altn
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Wrong number of argments");
            return;
        }

        File folder = new File(args[0]);
        File[] dirs = folder.listFiles();
        List<Sequence> sequences = new ArrayList<>();
        List<String> variations = new ArrayList<>();

        if (dirs == null)
            return;

        for (File d : dirs) {
            if (!d.getName().contains(".") && !d.getName().equals("__pycache__")) {
                sequences.add(new Sequence(d));
                variations.addAll(Arrays.asList(args).subList(2, args.length));
            }
        }

        assemble(sequences, variations, args[0], args[1]);
    }

    /*
        this function collects the psnr and bitrate from log files
        then makes a python script to calculate the bdbr
     */
    private static void assemble(List<Sequence> sequences, List<String> variations, String path, String reference) {
        try {
            FileWriter writer = new FileWriter(path + "\\bdbr.py");

            writer.append("from bjontegaard_metric import *\n");

            for (Sequence sequence : sequences) {
                writer.append("print ('\\n---" + sequence.getName() + "---')\n");

                for (String variation : variations) {
                    writer.append("print ('\\t" + variation + "')\n");

                    Map<Integer, Details> oriMap = sequence.getDetails(reference);
                    Map<Integer, Details> altMap = sequence.getDetails(variation);

                    if (oriMap == null || altMap == null) {
                        System.err.println("Logs missing for " + sequence.getName() + "_" + variation);
                        return;
                    }

                    List<Details> oriList = new ArrayList<>(oriMap.values());
                    List<Details> altList = new ArrayList<>(altMap.values());

                    if (oriList.size() != altList.size()) {
                        System.err.println("Logs don't have the same size");
                        return;
                    }

                    String r1 = "R1 = np.array([";
                    String psnr1 = "PSNR1 = np.array([";

                    String r2 = "R2 = np.array([";
                    String psnr2 = "PSNR2 = np.array([";

                    long t1 = 0;
                    long t2 = 0;

                    for (int i = 0; i < oriList.size(); i++) {
                        r1 = r1.concat(oriList.get(i).getBps() + ", ");
                        psnr1 = psnr1.concat(oriList.get(i).getPsnrs()[0] + ", ");

                        r2 = r2.concat(altList.get(i).getBps() + ", ");
                        psnr2 = psnr2.concat(altList.get(i).getPsnrs()[0] + ", ");

                        t1 += oriList.get(i).getTime();
                        t2 += altList.get(i).getTime();
                    }

                    r1 = r1.substring(0, r1.length() - 2).concat("])\n");
                    psnr1 = psnr1.substring(0, psnr1.length() - 2).concat("])\n");

                    r2 = r2.substring(0, r2.length() - 2).concat("])\n");
                    psnr2 = psnr2.substring(0, psnr2.length() - 2).concat("])\n");

                    t1 /= oriList.size();
                    t2 /= altList.size();

                    writer.append(r1);
                    writer.append(psnr1);

                    writer.append(r2);
                    writer.append(psnr2);

                    writer.append("print ('\\t\\tbdbr = ', BD_RATE(R1, PSNR1, R2, PSNR2, 1))\n");

                    float time = 1 - (float) t1 / t2;

                    writer.append("print ('\\t\\ttime =  " + time * 100f + "')\n");
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//D:\ViTech\Process tcc_lmeds_nod tcc_mlesac_nod tcc_msac_nod tcc_ransac_nod