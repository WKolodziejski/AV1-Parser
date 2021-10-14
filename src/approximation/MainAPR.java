package approximation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class MainAPR {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No file received");
        else
            approximate(read(new File(args[0])));
    }

    private static final List<Integer> c = Arrays.asList(128, 108, 84, 64, 44, 32, 20, 16, 8, 0);
    private static final List<Integer> p = Arrays.asList(32, 16, 8, 4, 2, 0);

    private static void approximate(int[][] filters) {
        //int i = 8;
        for (int i = 0; i < 96; i++) {

        int[] orig = new int[4];
        orig[0] = filters[i][0] + filters[i][1] + filters[i][2];
        orig[3] = filters[i][5] + filters[i][6] + filters[i][7];
        orig[1] = filters[i][3];
        orig[2] = filters[i][4];

        int[] apr = new int[4];
        apr[0] = tap(orig[0], p);
        apr[3] = tap(orig[3], p);
        apr[1] = tap(orig[1], c);
        apr[2] = tap(orig[2], c);

        //System.out.println(i + " :\t" + Arrays.toString(orig) + " | " + weight(orig));
        //System.out.println(i + " :\t" + Arrays.toString(apr) + " | " + weight(apr));

        //System.out.print("Resid:\t[");

        //System.out.print(Math.abs(Math.abs(orig[0]) - Math.abs(apr[0])) + ", ");
        //System.out.print(Math.abs(Math.abs(orig[1]) - Math.abs(apr[1])) + ", ");
        //System.out.print(Math.abs(Math.abs(orig[2]) - Math.abs(apr[2])) + ", ");
        //System.out.print(Math.abs(Math.abs(orig[3]) - Math.abs(apr[3])) + "]\n");

            balance(apr, orig);

            //if (!(isValid(apr) && weight(apr) == 128))
                System.out.println(i + ": " + Arrays.toString(apr) + " | " + weight(apr));
        }
    }

    private static int tap(int v, List<Integer> vs) {
        int s = 1;

        if (v < 0)
            s = -1;

        v = Math.abs(v);

        for (int i = 1; i < vs.size(); i++) {
            if (v >= vs.get(i)) {
                int t1 = vs.get(i);
                int t2 = vs.get(i - 1);
                int d1 = Math.abs(v - t1);
                int d2 = Math.abs(v - t2);
                return (d1 < d2 ? t1 : t2) * s;
            }
        }

        return -1;
    }

    private static boolean isValid(int[] filter) {
       return   (p.contains(filter[0]) || p.contains(-filter[0]))
        &&      c.contains(filter[1])
        &&      c.contains(filter[2])
        &&      (p.contains(filter[3]) || p.contains(-filter[3]));
    }

    private static int[] balance(int[] apr, int[] orig) {
        int s;
        int j = 0;
        int[] last = new int[4];

        do {
            copy(last, apr);

            s = weight(apr);
            int d = Math.abs(128 - s);
            int m = 128;
            int t = 0;

            for (int i = 0; i < 4; i++) {
                int r = Math.abs(Math.abs(orig[i]) - Math.abs(apr[i]));
                if (r < m) {
                    m = r;
                    t = i;
                }
            }

            int o1 = tap(apr[t] + d, c);
            int o2 = tap(apr[t] - d, c);

            int r1 = Math.abs(Math.abs(orig[t]) - Math.abs(o1));
            int r2 = Math.abs(Math.abs(orig[t]) - Math.abs(o2));

            //System.out.println("TAP: " + t);
            //System.out.println("Opt 1: " + o1);
            //System.out.println("Opt 2: " + o2);
            //System.out.println("Res 1: " + r1);
            //System.out.println("Res 2: " + r2);

            if (r1 < r2) {
                if (t == 1 || t == 2)
                    apr[t] = tap(o1, c);
                else
                    apr[t] = tap(o1, p);
            } else {
                if (t == 1 || t == 2)
                    apr[t] = tap(o2, c);
                else
                    apr[t] = tap(o2, p);
            }

            //System.out.println("\t\t" + Arrays.toString(apr) + " | " + s);

            //if (!isValid(apr)) {
                s = weight(apr);
                d = Math.abs(128 - s);

                for (int i = 0; i < 4; i++) {
                    int x = Math.abs(apr[i]);
                    if (x <= d) {
                        t = i;

                        if (x == d)
                            break;
                    }
                }

                if (s > 128) {
                    if (apr[t] > 0)
                        apr[t] += d;
                    else
                        apr[t] -= d;
                } else {
                    if (apr[t] > 0)
                        apr[t] -= d;
                    else
                        apr[t] += d;
                }

                apr[t] = tap(apr[t], c);

            //}

            s = weight(apr);

            //System.out.println("\t\t" + Arrays.toString(apr));


        } while ((s != 128 || !isValid(apr)) && ++j < 10);


        return apr;
    }

    private static boolean equals(int[] v1, int[] v2) {
        for (int i = 0; i < 4; i++)
            if (v1[i] != v2[i])
                return false;

        return true;
    }

    private static void copy(int[] v1, int[] v2) {
        System.arraycopy(v2, 0, v1, 0, 4);
    }

    private static int weight(int[] filter) {
        int s = 0;
        for (int f : filter)
            s += f;

        return s;
    }

    private static double log(int x) {
        return Math.log(x) / Math.log(2);
    }

    private static int[][] read(File file) {
        int[][] filters = new int[96][8];
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

                //System.out.println(Arrays.toString(filters[i]));

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
