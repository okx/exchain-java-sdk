package io.cosmos.util;

import java.io.IOException;
import java.io.OutputStream;

public final class OutputUtil {

    public static void writeByte(OutputStream out, byte data) throws IOException {
        out.write(data);
    }

    public static void writeLong(OutputStream out, long data) throws IOException {
        for (int i = 0; i < 8; i++) {
            out.write((byte) data);
            data >>= 8;
        }
    }

    public static void writeVarint(OutputStream out, long data) throws IOException {
        byte[] buf = new byte[9];
        int n = putUvarint(buf, data);
        out.write(buf, 0, n);
    }

    public static void writeVarstr(OutputStream out, byte[] str) throws IOException {
        writeVarint(out, str.length);
        out.write(str);
    }

    private static int putUvarint(byte[] buf, long x) {
        int i = 0;
        while (x >= 0x80) {
            buf[i] = (byte)(x | 0x80);
            x >>= 7;
            i++;
        }
        buf[i] = (byte)x;
        return i + 1;
    }
}
