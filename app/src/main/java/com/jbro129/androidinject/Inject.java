package com.jbro129.androidinject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by jbro129 on 4/3/2018.
 */

public class Inject
{
    private static RandomAccessFile raf;

    public static void InjectNow(String lib)
    {
        try {
            raf = new RandomAccessFile(lib, "rw");


            // hex can have spaces
            Inject.write(0x1234, "0100A0E3");
            Inject.write(0x1234, "01 00 A0 E3");

            // offset can be hexadecimal, decimal, binary etc
            Inject.write(0x1234, "0100A0E3"); // hexadeciaml
            Inject.write(4660, "01 00 A0 E3"); // decimal
            Inject.write(0b1001000110100, "01 00 A0 E3"); // binary

            // works with Arm
            Inject.write(0x1234, "01 00 A0 E3");

            // and Elf
            Inject.write(0x1234, "01 20");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static byte[] Hex2b(String hex) {
        if(hex.contains(" ")) {
            hex = hex.replace(" ", "");
        }

        if(hex == null) {
            throw new IllegalArgumentException("hex == null");
        } else if(hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected hex string: " + hex);
        } else {
            byte[] result = new byte[hex.length() / 2];

            for(int i = 0; i < result.length; ++i) {
                int d1 = decodeHexDigit(hex.charAt(i * 2)) << 4;
                int d2 = decodeHexDigit(hex.charAt(i * 2 + 1));
                result[i] = (byte)(d1 + d2);
            }

            return result;
        }
    }

    private static int decodeHexDigit(char paramChar)
    {
        if ((paramChar >= '0') && (paramChar <= '9')) {
            return paramChar - '0';
        }
        if ((paramChar >= 'a') && (paramChar <= 'f')) {
            return paramChar - 'a' + 10;
        }
        if ((paramChar >= 'A') && (paramChar <= 'F')) {
            return paramChar - 'A' + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + paramChar);
    }


    /*
    * String offset = Offset to write to
    * String hex = The ARM or Thumb hex to write;
    *
    * */
    private static void write(int offset, String hex)
    {
        try
        {
            raf.seek(offset);
            raf.write(Hex2b(hex));
            return;
        }
        catch (IOException paramString1)
        {
            paramString1.printStackTrace();
        }
    }
}
