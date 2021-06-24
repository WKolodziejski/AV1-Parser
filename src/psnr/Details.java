package psnr;

import java.io.File;
import java.util.Arrays;

public class Details {

    private int bps;
    private int time;
    private float[] psnrs;

    public Details(File log) {
        String s = Utils.read(log);

        if (!s.isEmpty()) {
            this.bps = parseBps(s);
            this.time = parseTime(s);
            this.psnrs = parsePSNR(s);
        } else {
            System.err.println("Log is empty");
        }
    }

    private int parseBps(String s) {
        s = s.substring(0, s.indexOf("bps") - 1);
        s = s.substring(s.lastIndexOf(" ") + 1);
        return Integer.parseInt(s);
    }

    private int parseTime(String s) {
        s = s.substring(0, s.indexOf("ms") - 1);
        s = s.substring(s.lastIndexOf(" ") + 1);
        return Integer.parseInt(s);
    }

    private float[] parsePSNR(String s) {
        float[] psnrs = new float[5];

        s = s.substring(s.indexOf(")") + 2);

        for (int i = 0; i < 5; i++) {
            psnrs[i] = Float.parseFloat(s.substring(0, s.indexOf(" ")));
            s = s.substring(s.indexOf(" ") + 1);
        }

        return psnrs;
    }

    public int getBps() {
        return bps;
    }

    public int getTime() {
        return time;
    }

    public float[] getPsnrs() {
        return psnrs;
    }

    @Override
    public String toString() {
        return "psnr.Details{" +
                "bps=" + bps +
                ", time=" + time +
                ", psnrs=" + Arrays.toString(psnrs) +
                '}';
    }

}
