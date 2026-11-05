package daa.search;

import java.util.ArrayList;
import java.util.List;

public class KMP {
    public static long counter = 0;

    public static int[] lps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0, i = 1;
        while (i < m) {
            if (p.charAt(i) == p.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) len = lps[len - 1];
                else { lps[i] = 0; i++; }
            }
        }
        return lps;
    }

    public static List<Integer> search(String t, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.isEmpty()) throw new IllegalArgumentException("Empty pattern");
        if (p.length() > t.length()) return res;

        int[] lps = lps(p);
        int i = 0, j = 0;
        while (i < t.length()) {
            counter++;
            if (t.charAt(i) == p.charAt(j)) {
                i++; j++;
                if (j == p.length()) {
                    res.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return res;
    }
}