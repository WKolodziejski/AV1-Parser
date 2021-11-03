package features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static features.Bundle.Feature.BLOCKS;
import static features.Bundle.Feature.FILTER;

public class MainFTR {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {
            File folder = new File(args[0]);

            int size = 0;

            for (File dir : folder.listFiles())
                if (dir.isDirectory())
                    size += dir.listFiles().length;

            Map<Integer, List<Features>> jobs = new TreeMap<>();
            CountDownLatch latch = new CountDownLatch(size);
            ExecutorService executor = Executors.newFixedThreadPool(size);

            for (File dir : folder.listFiles()) {
                if (dir.isDirectory()) {
                    Integer key = Integer.valueOf(dir.getName().replace("cq", ""));

                    jobs.putIfAbsent(key, new ArrayList<>());

                    for (File file : dir.listFiles())
                        jobs.get(key).add(new Features(file, latch));
                }
            }

            jobs.forEach((k, j) -> j.forEach(executor::execute));
            executor.shutdown();

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Bundle> bundles = new ArrayList<>();
            CountDownLatch latch2 = new CountDownLatch(jobs.size());
            ExecutorService executor2 = Executors.newFixedThreadPool(jobs.size());

            jobs.forEach((k, j) -> {
                Bundle bundle = new Bundle(k, j, latch2);
                bundles.add(bundle);
                executor2.execute(bundle);
            });

            executor2.shutdown();

            try {
                latch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            toCSV(bundles, BLOCKS, "blocks.csv");
            toCSV(bundles, FILTER, "filters.csv");
        }
    }

    private static void toCSV(List<Bundle> bundles, Bundle.Feature feature, String name) {
        try {
            FileWriter writer = new FileWriter(name);
            StringBuilder header = new StringBuilder();

            Set<String> fields = new HashSet<>();
            bundles.forEach(bundle -> fields.addAll(bundle.getFeature(feature).keySet()));

            fields.forEach(k -> bundles.forEach(bundle -> bundle.getFeature(feature).putIfAbsent(k, 0)));

            System.out.println(fields);

            Map<Integer, Integer> sums = new HashMap<>();

            bundles.forEach(bundle -> {
                AtomicReference<Integer> sum = new AtomicReference<>(0);

                fields.forEach(s -> {
                    Integer v = bundle.getFeature(feature).get(s);

                    if (v != null)
                        sum.accumulateAndGet(v, Integer::sum);
                });

                sums.put(bundle.cq, sum.get());
            });

            header.append("CQ;");
            header.append(fields.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(":", "")
                    .replace(", ", ";"));
            header.append("\n");

            writer.append(header);

            bundles.forEach(bundle -> {
                StringBuilder line = new StringBuilder();
                line.append("cq").append(bundle.cq).append(";");

                fields.forEach(s -> {
                    Integer v = bundle.getFeature(feature).get(s);

                    if (v != null) {
                        line.append(BigDecimal.valueOf(((double) v / (double) sums.get(bundle.cq)) * 100)
                                .toPlainString().replace(".", ","));
                    } else {
                        line.append(0);
                    }
                    line.append(";");
                });

                line.append("\n");

                try {
                    writer.append(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
