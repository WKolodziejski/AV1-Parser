package vhdl;

import psnr.Sequence;

import java.io.File;

public class VHDL {

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("No folder received");
        else {
            new Assembler(Utils.read(new File(args[0])));
        }
    }

}
