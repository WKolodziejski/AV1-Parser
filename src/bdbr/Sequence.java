package bdbr;

import java.io.File;
import java.util.*;

public class Sequence {

    private final File folder;
    private final Map<String, Map<Integer, Details>> stats;

    public Sequence(File folder) {
        this.folder = folder;
        this.stats = new HashMap<>();

        File[] dirs = folder.listFiles();

        if (dirs != null)
            for (File dir: dirs) {
                String name = dir.getName();

                File subFolder = new File(dir.getPath());
                File[] logs = subFolder.listFiles();

                if (logs != null) {
                    Map<Integer, Details> map = new TreeMap<>();

                    for (File log : logs) {
                        String logName = log.getName();

                        if (logName.contains(".log")) {
                            int cq = Integer.parseInt(logName.substring(logName.lastIndexOf("cq") + 2, logName.indexOf(".")));
                            map.put(cq, new Details(log));
                        }
                    }

                    stats.put(name, map);
                }
            }
        else
            System.err.println("Folder is empty");
    }

    public String getName() {
        return folder.getName();
    }

    public File getFolder() {
        return folder;
    }

    public Map<Integer, Details> getDetails(String key) {
        return stats.get(key);
    }

    @Override
    public String toString() {
        return "psnr.Sequence{" +
                "name='" + getName() + '\'' +
                ", stats=" + stats +
                '}';
    }
}
