package daa;

import daa.edit.EditDistance;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {
    @Test
    public void testReferenceValues() {
        assertEquals(3, EditDistance.recursive("kitten", "sitting"));
        assertEquals(3, EditDistance.table("kitten", "sitting"));
        assertEquals(3, EditDistance.twoRows("kitten", "sitting"));

        assertEquals(2, EditDistance.table("flaw", "lawn"));
        assertEquals(5, EditDistance.table("intention", "execution"));
        assertEquals(3, EditDistance.table("abc", ""));
        assertEquals(0, EditDistance.table("abc", "abc"));
    }

    @Test
    public void testRandomStrings() {
        Random rnd = new Random(42);
        for (int i = 0; i < 100; i++) {
            String a = gen(rnd);
            String b = gen(rnd);
            int r = EditDistance.recursive(a, b);
            int t = EditDistance.table(a, b);
            int tr = EditDistance.twoRows(a, b);
            assertEquals(r, t, "Recursive and Table mismatch for " + a + " and " + b);
            assertEquals(t, tr, "Table and TwoRows mismatch for " + a + " and " + b);
        }
    }

    private String gen(Random rnd) {
        int len = rnd.nextInt(10);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rnd.nextInt(3)));
        return sb.toString();
    }
}