package features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

public class Features extends Thread {

    public final Map<String, Map<String, Integer>> filters = new TreeMap<>();
    //private final Map<String, Integer> blocks = new HashMap<>();
    //private final CountDownLatch latch;
    private final File file;

    private final String[] fme = {
            "REGULAR",
            "SMOOTH",
            "SHARP",
            "BILINEAR",
            "SHARP2",
            "ALL",
            "SWITCHABLE_FILTERS",
            "SWITCHABLE",
            "EXTRA_FILTERS",
            "INVALID"
    };

    public Features(File file) {
        this.file = file;
        //this.latch = latch;
        filters.put("H", new TreeMap<>());
        filters.put("V", new TreeMap<>());
    }

    /*public Map<String, Integer> getFeature(Bundle.Feature feature) {
        switch (feature) {
            case FILTER:
                return filters;
            case BLOCKS:
                return blocks;
        }
        return null;
    }*/

    @Override
    public void run() {
        super.run();

        String[] tokens = null;
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                tokens = line.replace("(", "").replace(")", "").replace(",", "").split("\\s+");

                try {
                    String f1 = fme[Integer.parseInt(tokens[1])];
                    String f2 = fme[Integer.parseInt(tokens[2])];

                    Integer c1 = filters.get("H").get(f1);
                    Integer c2 = filters.get("V").get(f2);

                    if (c1 == null)
                        filters.get("H").put(f1, 1);
                    else
                        filters.get("H").put(f1, ++c1);

                    if (c2 == null)
                        filters.get("V").put(f2, 1);
                    else
                        filters.get("V").put(f2, ++c2);
                } catch (Exception e) {
                    System.err.println(line);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.\n", file);
            e.printStackTrace();
        }

        //latch.countDown();
    }

}
