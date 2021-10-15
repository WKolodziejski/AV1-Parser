package features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

            Map<String, List<Features>> jobs = new HashMap<>();
            CountDownLatch latch = new CountDownLatch(size);
            ExecutorService executor = Executors.newFixedThreadPool(size);

            for (File dir : folder.listFiles()) {
                if (dir.isDirectory()) {
                    String key = dir.getName();

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

            toCSV(bundles);
        }
    }

    private static void toCSV(List<Bundle> bundles) {
        try {
            FileWriter writer = new FileWriter("blocks.csv");
            StringBuilder header = new StringBuilder();

            Set<String> fields = new HashSet<>();
            bundles.forEach(bundle -> fields.addAll(bundle.blocks.keySet()));

            System.out.println(fields);

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
                line.append(bundle.cq);
                line.append(";");

                fields.forEach(s -> {
                    Integer v = bundle.blocks.get(s);

                    if (v != null) {
                        line.append(v);
                        line.append(";");
                    }
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
