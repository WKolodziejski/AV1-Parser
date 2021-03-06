package features;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Bundle extends Thread {

    public final Map<String, Map<String, Integer>> filters = new TreeMap<>();
    //private final Map<String, Integer> blocks = new HashMap<>();
    private final List<Features> features;
    //private final CountDownLatch latch;
    public final Integer cq;

    public Bundle(Integer cq, List<Features> features) {
        this.cq = cq;
        this.features = features;
        //this.latch = latch;
    }

    /*public Map<String, Integer> getFeature(Feature feature) {
        switch (feature) {
            case FILTER:
                return filters;
            case BLOCKS:
                return blocks;
        }
        return null;
    }

    public enum Feature {
        FILTER, BLOCKS
    }*/

    /*private void prepareHash(Feature feature) {
        Set<String> keys = new HashSet<>();
        features.forEach(f -> keys.addAll(f.getFeature(feature).keySet()));
        keys.forEach(k -> getFeature(feature).putIfAbsent(k, 0));

        features.forEach(f -> f.getFeature(feature).forEach((k, c) -> {
            Integer i = getFeature(feature).get(k);
            i += c;
            getFeature(feature).put(k, i);
        }));
    }*/

    private void prepareMap(String o) {
        filters.put(o, new TreeMap<>());

        Set<String> keys = new HashSet<>();
        features.forEach(f -> keys.addAll(f.filters.get(o).keySet()));
        keys.forEach(k -> filters.get(o).putIfAbsent(k, 0));

        features.forEach(f -> f.filters.get(o).forEach((k, c) -> {
            Integer i = filters.get(o).get(k);
            i += c;
            filters.get(o).put(k, i);
        }));
    }

    @Override
    public void run() {
        super.run();

        /*prepareHash(Feature.BLOCKS);
        prepareHash(Feature.FILTER);*/

        prepareMap("H");
        prepareMap("V");

        /*try {
            FileWriter writer = new FileWriter(cq + ".log");

            filters.forEach((k, c) -> {
                try {
                    writer.append(k).append("=").append(String.valueOf(c)).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            blocks.forEach((k, c) -> {
                try {
                    writer.append(k).append("=").append(String.valueOf(c)).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //latch.countDown();
    }

}
