package vhdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Utils {

    public static int[][] read(File file) {
        int[][] filters = new int[96][4];
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

}
