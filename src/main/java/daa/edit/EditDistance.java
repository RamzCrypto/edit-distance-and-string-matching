package daa.edit;

public class EditDistance {
    public static long counter = 0;

    // A. Recursive
    public static int recursive(String a, String b) {
        return rec(a, b, a.length(), b.length());
    }

    private static int rec(String a, String b, int i, int j) {
        counter++;
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) return rec(a, b, i - 1, j - 1);
        int insert = rec(a, b, i, j - 1);
        int delete = rec(a, b, i - 1, j);
        int replace = rec(a, b, i - 1, j - 1);
        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    // B. Table (Bottom-Up)
    public static int table(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) { counter++; dp[i][0] = i; }
        for (int j = 0; j <= m; j++) { counter++; dp[0][j] = j; }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                counter++;
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
            }
        }
        return dp[n][m];
    }

    // C. Two Rows (O(m) memory)
    public static int twoRows(String a, String b) {
        int n = a.length(), m = b.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        for (int j = 0; j <= m; j++) { counter++; prev[j] = j; }
        for (int i = 1; i <= n; i++) {
            curr[0] = i; counter++;
            for (int j = 1; j <= m; j++) {
                counter++;
                if (a.charAt(i - 1) == b.charAt(j - 1)) curr[j] = prev[j - 1];
                else curr[j] = 1 + Math.min(prev[j], Math.min(curr[j - 1], prev[j - 1]));
            }
            int[] temp = prev; prev = curr; curr = temp;
        }
        return prev[m];
    }
}