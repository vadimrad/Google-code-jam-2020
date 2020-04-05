/* Problem: https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/000000000020993c
Vestigium means "trace" in Latin. In this problem we work with Latin squares and matrix traces.

The trace of a square matrix is the sum of the values on the main diagonal (which runs from the upper left to the lower right).

An N-by-N square matrix is a Latin square if each cell contains one of N different values, and no value is repeated within a row or a column. In this problem, we will deal only with "natural Latin squares" in which the N values are the integers between 1 and N.

Given a matrix that contains only integers between 1 and N, we want to compute its trace and check whether it is a natural Latin square. To give some additional information, instead of simply telling us whether the matrix is a natural Latin square or not, please compute the number of rows and the number of columns that contain repeated values.

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each starts with a line containing a single integer N: the size of the matrix to explore. Then, N lines follow. The i-th of these lines contains N integers Mi,1, Mi,2 ..., Mi,N. Mi,j is the integer in the i-th row and j-th column of the matrix.

Output
For each test case, output one line containing Case #x: k r c, where x is the test case number (starting from 1), k is the trace of the matrix, r is the number of rows of the matrix that contain repeated elements, and c is the number of columns of the matrix that contain repeated elements.

Limits
Test set 1 (Visible Verdict)
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ≤ T ≤ 100.
2 ≤ N ≤ 100.
1 ≤ Mi,j ≤ N, for all i, j.

Sample

Input
 	
Output
 
3
4
1 2 3 4
2 1 4 3
3 4 1 2
4 3 2 1
4
2 2 2 2
2 3 2 3
2 2 2 3
2 2 2 2
3
2 1 3
1 3 2
1 2 3

  
Case #1: 4 0 0
Case #2: 9 4 4
Case #3: 8 0 2

  
In Sample Case #1, the input is a natural Latin square, which means no row or column has repeated elements. All four values in the main diagonal are 1, and so the trace (their sum) is 4.

In Sample Case #2, all rows and columns have repeated elements. Notice that each row or column with repeated elements is counted only once regardless of the number of elements that are repeated or how often they are repeated within the row or column. In addition, notice that some integers in the range 1 through N may be absent from the input.

In Sample Case #3, the leftmost and rightmost columns have repeated elements.

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Vestigium {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer testCases = Integer.parseInt(reader.readLine());

        for (int k = 1; k <= testCases; k++) {
            Integer n = Integer.parseInt(reader.readLine());
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] line = reader.readLine().split(" ");
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }

            int[] result = solve(matrix, n);
            StringBuilder sb = new StringBuilder();

            sb.append("Case #" + k + ": " + result[0] + " " + result[1] + " " + result[2]);
            System.out.println(sb.toString());
        }
    }

    public static int[] solve(int[][] matrix, int n) {
        int[] result = new int[3];
        //int[] col = new int[n]; // 0 - none, 1 - exist, -1 - exclude col
        //int[] row = new int[n];
       //int[] col = new int[n];
        Map<Integer, HashSet<Integer>> col = new HashMap<>();
        Set<Integer> row = new HashSet<>();
        boolean hasDuplicate = false;
        boolean[] column = new boolean[n];

        for (int i = 0; i < n; i++) {
            result[0] += matrix[i][i];
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               if (row.contains(matrix[i][j])) {
                   hasDuplicate = true;
               }
               row.add(matrix[i][j]);
               if (col.get(j) == null) {
                   col.put(j, new HashSet<>());

               }
               if (col.get(j).contains(matrix[i][j])) {
                   column[j] = true;
               }

               col.get(j).add(matrix[i][j]);

            }
            if (hasDuplicate) {
                result[1]++;
            }
            hasDuplicate = false;
            row = new HashSet<>();
        }

        for (boolean b : column) {
            if (b) result[2]++;
        }

        return result;
    }

}
