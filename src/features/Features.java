package features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Features extends Thread {

    public final Map<Integer, Integer> filtersFME = new HashMap<>();
    public final Map<Integer, Integer> filtersWME = new HashMap<>();
    public final Map<String, Integer> blocks = new HashMap<>();
    private final CountDownLatch latch;
    private final File file;

    public Features(File file, CountDownLatch latch) {
        this.file = file;
        this.latch = latch;

        filtersFME.put(0, 0);
        filtersFME.put(1, 0);
        filtersFME.put(2, 0);
        filtersFME.put(3, 0);
        filtersFME.put(4, 0);
        filtersFME.put(5, 0);

        filtersWME.put(0, 0);
        filtersWME.put(1, 0);
        filtersWME.put(2, 0);
        filtersWME.put(3, 0);
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                String blockSize = tokens[1];

                if (tokens[0].equals("T")) {
                    Integer f1 = Integer.valueOf(tokens[2]);
                    Integer f2 = Integer.valueOf(tokens[3]);

                    Integer c1 = filtersFME.get(f1);
                    Integer c2 = filtersFME.get(f2);

                    filtersFME.put(f1, ++c1);
                    filtersFME.put(f2, ++c2);

                } else if (tokens[0].equals("W")) {
                    Integer m = Integer.parseInt(tokens[2]);

                    Integer c = filtersWME.get(m);

                    filtersWME.put(m, ++c);

                } else {
                    System.err.println("Unknown motion type");
                }

                Integer b = blocks.get(blockSize);

                if (b == null)
                    blocks.put(blockSize, 1);
                else
                    blocks.put(blockSize, ++b);
            }

            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", file);
            e.printStackTrace();
        }

        //System.out.println(filtersFME);
        //System.out.println(filtersWME);
        //System.out.println(blocks);

        latch.countDown();
    }

}
