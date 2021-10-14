package features;

import java.io.File;
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

            jobs.forEach((k, j) -> new Bundle(k, j).start());
        }
    }

}
