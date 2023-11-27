import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Matrix{
    double[][] array;
    int rowsCount;
    int colsCount;

    public Matrix(double[][] array,int rowsCount, int colsCount) {
        this.array = array;
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
    }

    public static Matrix makeMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Сколько строк будет у матрицы?");
        int row = Integer.parseInt(sc.next());
        System.out.println("Сколько столбцов будет у матрицы?");
        int col = Integer.parseInt(sc.next());
        double[][] newArray = new double[row][col];

        System.out.println("Если вы хотите ввести матрицу поэлементно, нажмите 1.\nЕсли вы хотите ввести матрицу построчно, нажмите 2.");
        int way = Integer.parseInt(sc.next());

        if (way == 1) {
            System.out.println("Окей, будем вводить поэлементно.");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.println("Ведите элемент матрицы с индексом: " + (i + 1) + " " + (j + 1));
                    newArray[i][j] = Integer.parseInt(sc.next());
                }
            }
        }
        else if (way == 2) {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Окей, будем вводить построчно.");
            for (int i = 0; i < row; i++) {
                System.out.println("Ведите строку: " + (i + 1));
                sc2.reset();
                String line = sc2.nextLine();
                List<String> data = new ArrayList<>(List.of(line.split(" ")));
                data.removeAll(Arrays.asList("", null));
                if (data.size() != col) {
                    throw new RuntimeException("Неверное количество элементов");
                }
                for (int j = 0; j < col; j++) {
                    newArray[i][j] = Double.parseDouble(data.get(j));
                }
            }
        } else {
            throw new RuntimeException("Некорректный ввод");
        }
        return new Matrix(newArray, row, col);
    }
    public void printMatrix(){
        System.out.println("Matrix ("+ rowsCount + "*" + colsCount + "):");
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }
    public Matrix multiMatrix(Matrix other){
        if (colsCount==other.rowsCount){
            System.out.println("Окей, эти матрицы можно умножить.");
            double[][] newMatrix = new double[rowsCount][other.colsCount];
            for (int i=0;i<rowsCount;i++){
                for (int j=0;j<other.colsCount;j++){
                    for (int k=0; k<colsCount; k++){
                        newMatrix[i][j]+=array[i][k]*other.array[k][j];
                    }
                }
            }
            return new Matrix(newMatrix,newMatrix.length,newMatrix[0].length);
        }
        else if (other.colsCount==rowsCount){
            System.out.println("Упс, эти матрицы не получится умножить в таком порядке.\nНо можно поменять их местами и все получится.\nСогласен умножать в обратном порядке?");
            Scanner sc=new Scanner(System.in);
            String agree=sc.nextLine();
            if (agree.equals("да") || agree.equals("д") || agree.equals("yes") || agree.equals("y")){
                System.out.println("Окей, сейчас умножим.");
                double[][] newMatrix = new double[other.rowsCount][colsCount];
                for (int i=0;i<other.rowsCount;i++){
                    for (int j=0;j<colsCount;j++){
                        for (int k=0; k<rowsCount; k++){
                            newMatrix[i][j]+=other.array[i][k]*array[k][j];
                        }
                    }
                }
                return new Matrix(newMatrix,newMatrix.length,newMatrix[0].length);
            }
            System.out.println("Окей, тогда не будем ничего умножать.");
            return null;
        }
        else{
            System.out.println("Упс, эти матрицы нельзя перемножить.");
            return null;
        }
    }

    public Matrix gauss_view(Matrix m){
        double[][] arr = new double[m.rowsCount][m.colsCount];
        for (int i = 0; i < m.rowsCount; i++) {
            for (int j = 0; j < m.colsCount; j++) {
                arr[i][j] = m.array[i][j];
            }
        }
        int rank = Math.min(m.rowsCount,m.colsCount);
        for (int row = 0; row < rank; row++) {
            if (arr[row][row] != 0) {
                for (int col = 0; col < m.rowsCount; col++) {
                    if (col != row) {
                        double multiplier = arr[col][row] / arr[row][row];
                        arr[col][row]=0;
                        for (int i = row + 1; i < rank; i++) {
                            arr[col][i] -= multiplier * arr[row][i];
                        }
                    }
                }
            }
            else {
                boolean nonZero=false;
                for (int i = row + 1; i < m.rowsCount; i++) {
                    if (arr[i][row] != 0) {
                        double[] temp = arr[row];
                        arr[row] = arr[i];
                        arr[i] = temp;
                        nonZero=true;
                        break;
                    }
                }
            }
        }
        return new Matrix(arr,m.rowsCount,m.colsCount);
    }
    public int findRank() {
        double[][] m = new double[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                m[i][j] = array[i][j];
            }
        }
        Matrix matrix=new Matrix(m,rowsCount,colsCount);
        matrix=gauss_view(matrix);
        int rank = rowsCount;
        for (int i = 0; i < rowsCount; i++) {
            int c = 0;
            for (int j = 0; j < colsCount; j++) {
                if (matrix.array[i][j] == 0) {
                    c++;
                }
            }
            if (c == colsCount) {
                rank--;
            }
        }
        return rank;
    }
    public double determinant(){
        if(array.length == 2) {
            double det = 0;
            det += array[0][0]*array[1][1];
            det -= array[0][1]*array[1][0];
            return det;
        }
        double result = 0;
        for (int i = 0; i < rowsCount; i++) {
            double[][] arr = new double[rowsCount - 1][rowsCount - 1];
            for(int j = 1; j < rowsCount; j++) {
                for (int k = 0; k < rowsCount; k++) {
                    if (k < i) {
                        arr[j - 1][k] = array[j][k];
                    } else if (k > i) {
                        arr[j - 1][k - 1] = array[j][k];
                    }
                }
            }
            Matrix temporary = new Matrix(arr, arr.length, arr.length);
            result += array[0][i] * Math.pow(-1, i) * temporary.determinant();
        }
        return result;
    }
    public Matrix transMatrix(){
        double[][] matrixT=new double[colsCount][rowsCount];
        for (int i=0; i<rowsCount; i++){
            for (int j=0; j<colsCount; j++){
                matrixT[j][i]=array[i][j];
            }
        }
        return new Matrix(matrixT,matrixT.length,matrixT[0].length);
    }
    public Matrix solve(Matrix freeCoefficients){
        double[][] matrix=new double[rowsCount][colsCount];
        for (int i=0; i<rowsCount; i++){
            for (int j=0; j<rowsCount; j++){
                matrix[i][j]=array[i][j];
            }
        }
        if (this.determinant()==0) {
            System.out.println("Матрица необратима");
            return null;
        }
        else{
            System.out.println("Матрица обратима");
        }
        double[][] extended=new double[rowsCount][colsCount+1];
        for (int i=0; i<rowsCount; i++){
            for (int j=0; j<colsCount; j++){
                extended[i][j]=array[i][j];
            }
            extended[i][colsCount]=freeCoefficients.array[i][0];
        }
        Matrix extMatrix=new Matrix(extended, extended.length, extended[0].length+1);
        if (this.findRank()!=extMatrix.findRank()){
            return null;
        }
        double[][] E = new double[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; i++)
            for (int j = 0; j < colsCount; j++) {
                E[i][j] = 0;
                if (i == j)
                    E[i][j] = 1;
            }
        double divider;
        double multiplier;
        for (int k = 0; k < rowsCount; k++) {
            divider = matrix[k][k];
            for (int j = 0; j < colsCount; j++) {
                matrix[k][j] /= divider;
                E[k][j] /= divider;
            }
            for (int i = k + 1; i < rowsCount; i++) {
                multiplier = matrix[i][k];
                for (int j = 0; j < rowsCount; j++) {
                    matrix[i][j] -= matrix[k][j] * multiplier;
                    E[i][j] -= E[k][j] * multiplier;
                }
            }
        }
        for (int k = rowsCount - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                multiplier = matrix[i][k];
                for (int j = 0; j < rowsCount; j++) {
                    matrix[i][j] -= matrix[k][j] * multiplier;
                    E[i][j] -= E[k][j] * multiplier;
                }
            }
        }
        double[][] unknowns = new double[rowsCount][freeCoefficients.colsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < freeCoefficients.colsCount; j++) {
                for (int k = 0; k < colsCount; k++) {
                    unknowns[i][j] += E[i][k] * freeCoefficients.array[k][j];
                }
            }
        }
        return new Matrix(unknowns,unknowns.length,unknowns[0].length);
    }
}
