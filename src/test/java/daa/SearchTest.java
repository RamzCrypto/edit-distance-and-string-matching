package daa;

import daa.search.KMP;
import daa.search.NaiveSearch;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    @Test
    public void testReferenceValues() {
        assertArrayEquals(new int[]{0, 1, 2, 0, 1, 2, 3, 3}, KMP.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4}, KMP.lps("ABABCABAB"));

        assertEquals(List.of(10), KMP.search("ABABDABACDABABCABAB", "ABABCABAB"));
        assertEquals(List.of(10), NaiveSearch.search("ABABDABACDABABCABAB", "ABABCABAB"));

        assertEquals(List.of(0, 1, 2, 3), KMP.search("AAAAA", "AA"));
        assertEquals(List.of(0, 1, 2, 3), NaiveSearch.search("AAAAA", "AA"));

        assertEquals(List.of(0, 2, 4), KMP.search("ABABABA", "ABA"));
        assertEquals(List.of(0, 2, 4), NaiveSearch.search("ABABABA", "ABA"));
    }

    @Test
    public void testEdgeCases() {
        assertThrows(IllegalArgumentException.class, () -> KMP.search("abc", ""));
        assertThrows(IllegalArgumentException.class, () -> NaiveSearch.search("abc", ""));
        assertTrue(KMP.search("abc", "abcd").isEmpty());
        assertTrue(NaiveSearch.search("abc", "abcd").isEmpty());
    }

    @Test
    public void testRandomTexts() {
        Random rnd = new Random(42);
        for (int i = 0; i < 100; i++) {
            String t = gen(rnd, 50);
            String p = gen(rnd, 5);
            List<Integer> k = KMP.search(t, p);
            List<Integer> n = NaiveSearch.search(t, p);
            assertEquals(n, k, "Mismatch for T=" + t + " P=" + p);
        }
    }

    private String gen(Random rnd, int max) {
        int len = rnd.nextInt(max) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rnd.nextInt(2)));
        return sb.toString();
    }
}