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
            for (File d: dirs) {
                String n = d.getName();

                File subFolder = new File(d.getPath());
                File[] logs = subFolder.listFiles();

                if (logs != null) {
                    Map<Integer, Details> map = new TreeMap<>();

                    for (File l : logs) {
                        String sn = l.getName();

                        if (sn.contains(".log")) {
                            int cq = Integer.parseInt(sn.substring(sn.lastIndexOf("cq") + 2, sn.indexOf(".")));
                            map.put(cq, new Details(l));
                        }
                    }

                    stats.put(n.substring(n.lastIndexOf("_") + 1), map);
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
