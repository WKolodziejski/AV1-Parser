package features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MainFTR {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {
            File folder = new File(args[0]);

            Map<String, Map<Integer, List<Features>>> jobs = new TreeMap<>();

            for (File dir : folder.listFiles()) {
                if (dir.isDirectory()) {
                    Integer cq = Integer.valueOf(dir.getName().replace("cq", ""));

                    for (File file : dir.listFiles()) {
                        String res = file.getName();
                        String w = res.substring(0, res.lastIndexOf('x'));
                        w = w.substring(w.lastIndexOf('_') + 1);
                        String h = res.substring(res.lastIndexOf('x') + 1);
                        h = h.substring(0, h.indexOf('_'));
                        res = w + "x" + h;

                        if (res.equals("3840x2160"))
                            res = "4096x2160";

                        jobs.putIfAbsent(res, new TreeMap<>());
                        Map<Integer, List<Features>> map = jobs.get(res);

                        map.putIfAbsent(cq, new ArrayList<>());
                        map.get(cq).add(new Features(file));
                    }
                }
            }

            jobs.forEach((res, map) -> map.forEach((cq, job) -> job.forEach(Thread::start)));
            jobs.forEach((res, map) -> map.forEach((cq, job) -> job.forEach(features -> {
                try {
                    features.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })));

            Map<String, List<Bundle>> bundles = new TreeMap<>();

            jobs.forEach((res, map) -> map.forEach((cq, job) -> {
                Bundle bundle = new Bundle(cq, job);
                bundles.putIfAbsent(res, new ArrayList<>());
                bundles.get(res).add(bundle);
                bundle.start();
            }));

            bundles.forEach((res, b) -> {
                b.forEach(bundle -> {
                    try {
                        bundle.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                toCSV(b, res);
            });
        }
    }

    private static void toCSV(List<Bundle> bundles, String name) {
        processCSV(bundles, name, "H");
        processCSV(bundles, name, "V");
    }

    private static void processCSV(List<Bundle> bundles, String name, String o) {
        try {
            FileWriter writer = new FileWriter(name + "_" + o + ".csv");
            StringBuilder header = new StringBuilder();

            Set<String> fields = new HashSet<>();
            bundles.forEach(bundle -> fields.addAll(bundle.filters.get(o).keySet()));

            fields.forEach(k -> bundles.forEach(bundle -> bundle.filters.get(o).putIfAbsent(k, 0)));

            System.out.println(fields);

            Map<Integer, Integer> sums = new TreeMap<>();

            bundles.forEach(bundle -> {
                AtomicReference<Integer> sum = new AtomicReference<>(0);

                fields.forEach(s -> {
                    Integer v = bundle.filters.get(o).get(s);

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
            header.append(";");
            header.append("\n");

            writer.append(header);

            bundles.forEach(bundle -> {
                StringBuilder line = new StringBuilder();
                line.append("cq").append(bundle.cq).append(";");

                fields.forEach(s -> {
                    Integer v = bundle.filters.get(o).get(s);

                    if (v != null) {
                        line.append(BigDecimal.valueOf(((double) v / (double) sums.get(bundle.cq)) * 100).toPlainString());
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
