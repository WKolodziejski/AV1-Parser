package features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Features extends Thread {

    private final Map<String, Integer> filters = new HashMap<>();
    private final Map<String, Integer> blocks = new HashMap<>();
    private final CountDownLatch latch;
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

    public Features(File file, CountDownLatch latch) {
        this.file = file;
        this.latch = latch;
    }

    public Map<String, Integer> getFeature(Bundle.Feature feature) {
        switch (feature) {
            case FILTER:
                return filters;
            case BLOCKS:
                return blocks;
        }
        return null;
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.replace("(", "").replace(")", "").replace(",", "").split("\\s+");
                String blockSize = tokens[0];

                String f1 = "H_" + fme[Integer.parseInt(tokens[1])];
                String f2 = "V_" + fme[Integer.parseInt(tokens[2])];

                Integer c1 = filters.get(f1);
                Integer c2 = filters.get(f2);

                if (c1 == null)
                    filters.put(f1, 1);
                else
                    filters.put(f1, ++c1);

                if (c2 == null)
                    filters.put(f2, 1);
                else
                    filters.put(f2, ++c2);

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

        latch.countDown();
    }

}
