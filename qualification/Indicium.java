/* Problem: https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/0000000000209aa0
Indicium means "trace" in Latin. In this problem we work with Latin squares and matrix traces.

A Latin square is an N-by-N square matrix in which each cell contains one of N different values, such that no value is repeated within a row or a column. In this problem, we will deal only with "natural Latin squares" in which the N values are the integers between 1 and N.

The trace of a square matrix is the sum of the values on the main diagonal (which runs from the upper left to the lower right).

Given values N and K, produce any N-by-N "natural Latin square" with trace K, or say it is impossible. For example, here are two possible answers for N = 3, K = 6. In each case, the values that contribute to the trace are underlined.

2 1 3   3 1 2
3 2 1   1 2 3
1 3 2   2 3 1

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each consists of one line containing two integers N and K: the desired size of the matrix and the desired trace.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is IMPOSSIBLE if there is no answer for the given parameters or POSSIBLE otherwise. In the latter case, output N more lines of N integers each, representing a valid "natural Latin square" with a trace of K, as described above.

Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
N ≤ K ≤ N2.

Test set 1 (Visible Verdict)
T = 44.
2 ≤ N ≤ 5.

Test set 2 (Hidden Verdict)
1 ≤ T ≤ 100.
2 ≤ N ≤ 50.

Sample

Input
 	
Output
 
2
3 6
2 3

  
Case #1: POSSIBLE
2 1 3
3 2 1
1 3 2
Case #2: IMPOSSIBLE

  
Sample Case #1 is the one described in the problem statement.

Sample Case #2 has no answer. The only possible 2-by-2 "natural Latin squares" are as follows:

1 2   2 1
2 1   1 2
These have traces of 2 and 4, respectively. There is no way to get a trace of 3.*/



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//Solved only test set 1
public class Task5 {
    boolean isDone = false;
    static int[][] resultMatrix = null;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer testCases = Integer.parseInt(reader.readLine());

        for (int i = 1; i <= testCases; i++) {
            String[] number = reader.readLine().split(" ");
            int N = Integer.parseInt(number[0]);
            int k = Integer.parseInt(number[1]);
            Indicium indicium = new Indicium();
            boolean result = indicium.solve(N, k);
            StringBuilder sb = new StringBuilder();
            sb.append("Case #" + i + ": " + (result ? "POSSIBLE" : "IMPOSSIBLE"));
            System.out.println(sb.toString());
            if (result) {
                for (int j = 0; j < N; j++) {
                    for (int l = 0; l < N; l++) {
                        System.out.print(resultMatrix[j][l]);
                        if (l != N-1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    private boolean solve(int N, int k) {
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = i + 1;
        }
        diagonalBacktrack(new ArrayList<>(), nums, k, 0, N);
        return isDone;
    }

    private void diagonalBacktrack(List<Integer> tempList, int[] nums, int remain, int start, int remainPlace) {
        if (isDone) return;
        if (remain < 0 || remainPlace < 0) return;
        else if (remain == 0 && remainPlace == 0) {
            solveLatinSquare(tempList);
            if (isDone) return;
        } else {
            for (int i = start; i < nums.length; i++) {
                tempList.add(nums[i]);
                diagonalBacktrack(tempList, nums, remain - nums[i], i, remainPlace - 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    private void solveLatinSquare(List<Integer> diagonal) {
        int N = diagonal.size();
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            matrix[i][i] = diagonal.get(i);
        }
        isDone = latinSquareBacktrack(matrix);
        resultMatrix = matrix;
    }

    private boolean latinSquareBacktrack(int[][] board) {
        int N = board.length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    for (int c = 1; c <= N; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; 

                            if (latinSquareBacktrack(board))
                                return true; 
                            else
                                board[i][j] = 0; 
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int c) {
        int N = board.length;
        for (int i = 0; i < N; i++) {
            if (board[i][col] != 0 && board[i][col] == c) return false; 
            if (board[row][i] != 0 && board[row][i] == c) return false; 
        }
        return true;
    }
}
