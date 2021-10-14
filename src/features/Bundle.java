package features;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bundle extends Thread {

    public final Map<Integer, Integer> filtersFME = new HashMap<>();
    public final Map<Integer, Integer> filtersWME = new HashMap<>();
    public final Map<String, Integer> blocks = new HashMap<>();
    private final List<Features> features;
    private final String cq;

    private final String[] filtersFMEstr = {
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

    private final String[] filtersWMEstr = {
            "IDENTITY",
            "TRANSLATION",
            "ROTZOOM",
            "AFFINE"
    };

    public Bundle(String cq, List<Features> features) {
        this.cq = cq;
        this.features = features;
    }

    @Override
    public void run() {
        super.run();

        for (Features feature : features) {
            feature.filtersFME.forEach((k, v) -> filtersFME.merge(k, v, Integer::sum));
            feature.filtersWME.forEach((k, v) -> filtersWME.merge(k, v, Integer::sum));
            feature.blocks.forEach((k, v) -> blocks.merge(k, v, Integer::sum));
        }

        try {
            FileWriter writer = new FileWriter(cq + ".log");

            writer.append("---------INTERPOLATION---------\n");

            filtersFME.forEach((k, c) -> {
                try {
                    writer.append(filtersFMEstr[k] + "=" + c + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.append("-------------WARP-------------\n");

            filtersWME.forEach((k, c) -> {
                try {
                    writer.append(filtersWMEstr[k] + "=" + c + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.append("-------------BLOCK-------------\n");

            blocks.forEach((k, c) -> {
                try {
                    writer.append(k + "=" + c + "\n");
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
