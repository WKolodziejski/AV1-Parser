package psnr;

import java.io.File;

public class BDBR {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {
            File folder = new File(args[0]);
            File[] dirs = folder.listFiles();

            if (dirs != null)
                for (File d : dirs)
                    Utils.createFile(new Sequence(d));
        }
    }

}
