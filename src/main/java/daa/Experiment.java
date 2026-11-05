package daa;

import daa.edit.EditDistance;
import daa.search.KMP;
import daa.search.NaiveSearch;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Experiment {
    public static void main(String[] args) throws Exception {
        new java.io.File("results").mkdirs();
        PrintWriter csv = new PrintWriter(new FileWriter("results/results.csv"));
        csv.println("algorithm,input,n,time_ms,operations,result");

        System.out.println("Starting Edit Distance tests...");
        runEditDistance(csv);
        csv.flush(); // Принудительно сохраняем на диск

        System.out.println("Starting String Search tests...");
        runStringSearch(csv);
        csv.flush();

        csv.close();
        System.out.println("Experiment done! Check results/results.csv");
    }

    private static void runEditDistance(PrintWriter csv) {
        int[] sizes = {10, 20, 40, 80};
        Random rnd = new Random(42);

        for (int n : sizes) {
            String a = generateString(rnd, n);
            String b = generateString(rnd, n);

            // Рекурсия (только до 15, иначе будет считать вечность)
            if (n <= 15) {
                EditDistance.counter = 0;
                long t0 = System.nanoTime();
                int res = EditDistance.recursive(a, b);
                long t1 = System.nanoTime();
                csv.printf("EditRecursive,random,%d,%d,%d,%d%n", n, (t1-t0)/1_000_000, EditDistance.counter, res);
                System.out.println("EditRecursive n=" + n + " done");
            }

            // Таблица
            EditDistance.counter = 0;
            long t0 = System.nanoTime();
            int resT = EditDistance.table(a, b);
            long t1 = System.nanoTime();
            csv.printf("EditTable,random,%d,%d,%d,%d%n", n, (t1-t0)/1_000_000, EditDistance.counter, resT);
            System.out.println("EditTable n=" + n + " done");

            // Две строки
            EditDistance.counter = 0;
            t0 = System.nanoTime();
            int res2 = EditDistance.twoRows(a, b);
            t1 = System.nanoTime();
            csv.printf("EditTwoRows,random,%d,%d,%d,%d%n", n, (t1-t0)/1_000_000, EditDistance.counter, res2);
            System.out.println("EditTwoRows n=" + n + " done");
        }
    }

    private static void runStringSearch(PrintWriter csv) {
        int[] sizes = {1000, 5000, 10000, 50000};
        Random rnd = new Random(42);

        for (int n : sizes) {
            System.out.println("Running Search size n=" + n + "...");
            // 1. RANDOM TEXT
            String tRand = generateString(rnd, n);
            String pRand = generateString(rnd, 10);

            NaiveSearch.counter = 0;
            long t0 = System.nanoTime();
            NaiveSearch.search(tRand, pRand);
            long t1 = System.nanoTime();
            csv.printf("Naive,random,%d,%d,%d,0%n", n, (t1-t0)/1_000_000, NaiveSearch.counter);
            csv.flush();

            KMP.counter = 0;
            t0 = System.nanoTime();
            KMP.search(tRand, pRand);
            t1 = System.nanoTime();
            csv.printf("KMP,random,%d,%d,%d,0%n", n, (t1-t0)/1_000_000, KMP.counter);
            csv.flush();

            // 2. WORST CASE TEXT (AAAAAA... vs AAAB)
            String tWorst = "A".repeat(n);
            String pWorst = "A".repeat(9) + "B";

            NaiveSearch.counter = 0;
            t0 = System.nanoTime();
            NaiveSearch.search(tWorst, pWorst);
            t1 = System.nanoTime();
            csv.printf("Naive,worst,%d,%d,%d,0%n", n, (t1-t0)/1_000_000, NaiveSearch.counter);
            csv.flush();

            KMP.counter = 0;
            t0 = System.nanoTime();
            KMP.search(tWorst, pWorst);
            t1 = System.nanoTime();
            csv.printf("KMP,worst,%d,%d,%d,0%n", n, (t1-t0)/1_000_000, KMP.counter);
            csv.flush();
        }
    }

    private static String generateString(Random rnd, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rnd.nextInt(4))); // a, b, c, d
        return sb.toString();
    }
}