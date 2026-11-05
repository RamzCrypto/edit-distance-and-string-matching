package daa;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Plotter {
    public static void main(String[] args) throws Exception {
        new File("results/plots").mkdirs();

        XYSeriesCollection editDataset = new XYSeriesCollection();
        XYSeriesCollection worstDataset = new XYSeriesCollection();
        XYSeriesCollection randomDataset = new XYSeriesCollection();

        Map<String, XYSeries> editMap = new LinkedHashMap<>();
        Map<String, XYSeries> worstMap = new LinkedHashMap<>();
        Map<String, XYSeries> randomMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("results/results.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] r = line.split(",");
                String alg = r[0];
                String input = r[1];
                int n = Integer.parseInt(r[2]);
                long ops = Long.parseLong(r[4]);

                if (alg.startsWith("Edit")) {
                    editMap.computeIfAbsent(alg, k -> new XYSeries(k)).add(n, ops);
                } else if (input.equals("worst")) {
                    worstMap.computeIfAbsent(alg, k -> new XYSeries(k)).add(n, ops);
                } else if (input.equals("random")) {
                    randomMap.computeIfAbsent(alg, k -> new XYSeries(k)).add(n, ops);
                }
            }
        }

        for (var s : editMap.values()) editDataset.addSeries(s);
        for (var s : worstMap.values()) worstDataset.addSeries(s);
        for (var s : randomMap.values()) randomDataset.addSeries(s);

        saveChart(editDataset, "Edit Distance Operations", "n", "Operations", "results/plots/edit_distance_ops.png");
        saveChart(worstDataset, "String Search: Worst Case", "n (text length)", "Comparisons", "results/plots/worst_case.png");
        saveChart(randomDataset, "String Search: Random Input", "n (text length)", "Comparisons", "results/plots/random_case.png");

        System.out.println("Plots generated in results/plots/");
    }

    private static void saveChart(XYSeriesCollection ds, String title, String x, String y, String path) throws Exception {
        JFreeChart chart = ChartFactory.createXYLineChart(title, x, y, ds);
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(new XYLineAndShapeRenderer(true, true));

        // Логарифмическая шкала для наглядности
        plot.setDomainAxis(new LogarithmicAxis(x));
        plot.setRangeAxis(new LogarithmicAxis(y));

        ChartUtils.saveChartAsPNG(new File(path), chart, 800, 600);
    }
}