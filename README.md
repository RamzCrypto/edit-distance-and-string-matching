# DAA Assignment 4: Dynamic Programming and String Algorithms

**Author:** Bazaraly Ramazan
**Group:** SE-2526

## Project Overview
This project implements and benchmarks:
- **Edit Distance:** Recursive, full bottom-up Table, and memory-optimized Two-Rows versions.
- **String Search:** Naive search and Knuth-Morris-Pratt (KMP) with the LPS array.

The experiment evaluates time complexity and exact operation counts (cell updates / char comparisons) across different input sizes and worst-case scenarios.

## How to Build
```bash
mvn clean compile
How to Run Tests
bash
mvn test
(Tests include exact reference values and random cross-checks between all versions).

How to Run the Experiment
bash
mvn exec:java -Dexec.mainClass="daa.Experiment"
This generates results/results.csv with all the benchmark data.

How to Generate Plots
bash
mvn exec:java -Dexec.mainClass="daa.Plotter"
This generates three .png charts in the results/plots/ directory.

