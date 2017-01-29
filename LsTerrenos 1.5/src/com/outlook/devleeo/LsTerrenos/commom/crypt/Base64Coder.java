package com.outlook.devleeo.LsTerrenos.commom.crypt;

public class Base64Coder
{
    private static char[] map1;
    private static byte[] map2;
    
    static {
        Base64Coder.map1 = new char[64];
        int i = 0;
        for (char c = 'A'; c <= 'Z'; ++c) {
            Base64Coder.map1[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            Base64Coder.map1[i++] = c;
        }
        for (char c = '0'; c <= '9'; ++c) {
            Base64Coder.map1[i++] = c;
        }
        Base64Coder.map1[i++] = '+';
        Base64Coder.map1[i++] = '/';
        Base64Coder.map2 = new byte[128];
        for (i = 0; i < Base64Coder.map2.length; ++i) {
            Base64Coder.map2[i] = -1;
        }
        for (i = 0; i < 64; ++i) {
            Base64Coder.map2[Base64Coder.map1[i]] = (byte)i;
        }
    }
    
    public static String encodeString(final String s) {
        return new String(encode(s.getBytes()));
    }
    
    public static char[] encode(final byte[] in) {
        return encode(in, in.length);
    }
    
    public static char[] encode(final byte[] in, final int iLen) {
        final int oDataLen = (iLen * 4 + 2) / 3;
        final int oLen = (iLen + 2) / 3 * 4;
        final char[] out = new char[oLen];
        int i0;
        int i;
        int i2;
        int o0;
        int o;
        int o2;
        int o3;
        for (int ip = 0, op = 0; ip < iLen; i0 = (in[ip++] & 0xFF), i = ((ip < iLen) ? (in[ip++] & 0xFF) : 0), i2 = ((ip < iLen) ? (in[ip++] & 0xFF) : 0), o0 = i0 >>> 2, o = ((i0 & 0x3) << 4 | i >>> 4), o2 = ((i & 0xF) << 2 | i2 >>> 6), o3 = (i2 & 0x3F), out[op++] = Base64Coder.map1[o0], out[op++] = Base64Coder.map1[o], out[op] = ((op < oDataLen) ? Base64Coder.map1[o2] : '='), ++op, out[op] = ((op < oDataLen) ? Base64Coder.map1[o3] : '='), ++op) {}
        return out;
    }
    
    public static String decodeString(final String s) {
        return new String(decode(s));
    }
    
    public static byte[] decode(final String s) {
        return decode(s.toCharArray());
    }
    
    public static byte[] decode(final char[] in) {
        int iLen = in.length;
        if (iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (iLen > 0 && in[iLen - 1] == '=') {
            --iLen;
        }
        final int oLen = iLen * 3 / 4;
        final byte[] out = new byte[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            final int i0 = in[ip++];
            final int i = in[ip++];
            final int i2 = (ip < iLen) ? in[ip++] : 'A';
            final int i3 = (ip < iLen) ? in[ip++] : 'A';
            if (i0 > 127 || i > 127 || i2 > 127 || i3 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            final int b0 = Base64Coder.map2[i0];
            final int b = Base64Coder.map2[i];
            final int b2 = Base64Coder.map2[i2];
            final int b3 = Base64Coder.map2[i3];
            if (b0 < 0 || b < 0 || b2 < 0 || b3 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            final int o0 = b0 << 2 | b >>> 4;
            final int o = (b & 0xF) << 4 | b2 >>> 2;
            final int o2 = (b2 & 0x3) << 6 | b3;
            out[op++] = (byte)o0;
            if (op < oLen) {
                out[op++] = (byte)o;
            }
            if (op >= oLen) {
                continue;
            }
            out[op++] = (byte)o2;
        }
        return out;
    }
}
