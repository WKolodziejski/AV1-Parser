package timing;

import java.io.*;
import java.util.*;

public class MainTMG {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {

            Map<String, List<File>> map = new HashMap<>();
            map.put("cq20.log", new ArrayList<>());
            map.put("cq32.log", new ArrayList<>());
            map.put("cq43.log", new ArrayList<>());
            map.put("cq55.log", new ArrayList<>());

            File folder = new File(args[0]);

            for (File dir : folder.listFiles())
                if (dir.isDirectory())
                    for (File file : dir.listFiles())
                        map.get(file.getName()).add(file);


            Map<String, Map<String, Long>> times = new HashMap<>();
            map.forEach((cq, files) -> times.put(cq, computeCQ(cq, files)));
            toCSV(times);
        }
    }

    private static Map<String, Long> computeCQ(String cq, List<File> files) {
        Map<String, Long> map = new HashMap<>();

        files.forEach(file -> processFile(file).forEach((k, v) -> map.merge(k, v, Long::sum)));

        System.out.print(cq);
        System.out.println(map);

        return map;
    }

    private static Map<String, Long> processFile(File file) {
        Map<String, Long> times = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                while (line.contains("Frame number:")) {
                    line = reader.readLine();
                }

                if (line.contains("(total:")) {
                    String[] tokens = line.trim().split("\\s+");

                    times.put(tokens[0], Long.valueOf(tokens[4]));
                }
            }

            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", file);
            e.printStackTrace();
        }

        return times;
    }

    private static void toCSV(Map<String, Map<String, Long>> map) {
        try {
            FileWriter writer = new FileWriter("timing.csv");
            StringBuilder header = new StringBuilder();

            Set<String> fields = new HashSet<>();
            map.forEach((s, m) -> fields.addAll(m.keySet()));

            header.append("CQ;");
            header.append(fields.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(":", "")
                    .replace(", ", ";"));
            header.append("\n");

            writer.append(header);

            map.forEach((k, map2) -> {
                StringBuilder line = new StringBuilder();
                line.append(k.replace(".log", ""));
                line.append(";");

                fields.forEach(s -> {
                    Long v = map2.get(s);

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
