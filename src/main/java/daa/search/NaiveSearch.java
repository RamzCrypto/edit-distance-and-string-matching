package daa.search;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {
    public static long counter = 0;

    public static List<Integer> search(String t, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.isEmpty()) throw new IllegalArgumentException("Empty pattern");
        if (p.length() > t.length()) return res;

        int n = t.length(), m = p.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m) {
                counter++;
                if (t.charAt(i + j) != p.charAt(j)) break;
                j++;
            }
            if (j == m) res.add(i);
        }
        return res;
    }
}