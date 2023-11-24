import java.util.Arrays;
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

    void makeMatrix(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Сколько строк будет у матрицы?");
        int rows=sc.nextInt();
        System.out.println("Сколько столбцов будет у матрицы?");
        int cols=sc.nextInt();
        double[][] newArray=new double[rows][cols];

        System.out.println("Если вы хотите ввести матрицу поэлементно, нажмите 1.\nЕсли вы хотите ввести матрицу построчно, нажмите 2.");
        int way = sc.nextInt();

        if (way==1) {
            System.out.println("Окей, будем вводить поэлементно.");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.println("Ведите элемент матрицы с индексом: "+(i+1)+" "+(j+1));
                    newArray[i][j] = sc.nextInt();
                }
            }
        }
        else if (way==2){
            System.out.println("Окей, будем вводить построчно.");
            for (int i = 0; i < rows; i++) {
                System.out.println("Ведите строку: "+(i+1));
                for (int j = 0; j < cols; j++) {
                    newArray[i][j] = sc.nextInt();
                }
            }
        }
        this.array = newArray;
        this.rowsCount = rows;
        this.colsCount = cols;
    }
    void printMatrix(){
        System.out.println("Matrix ("+ rowsCount + "*" + colsCount + "):");
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }
    double[][] multiMatrix(Matrix other){
        System.out.println("Давай попробуем умножить матрицы.");
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
            return newMatrix;
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
                return newMatrix;
            }
            System.out.println("Окей, тогда не будем ничего умножать.");
            return null;
        }
        else{
            System.out.println("Упс, эти матрицы нельзя перемножить.");
            return null;
        }
    }
//    int rank(){
//
//    }
    double determinant(){
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
    double[][] transMatrix(){
        double[][] matrixT=new double[rowsCount][colsCount];
        for (int i=0; i<rowsCount; i++){
            for (int j=0; j<colsCount; j++){
                matrixT[j][i]=array[i][j];
            }
        }
        return matrixT;
    }
    double[][] solve(Matrix freeCoefficients){
        double[][] matrix=new double[rowsCount][colsCount];
        for (int i=0; i<rowsCount; i++){
            for (int j=0; j<rowsCount; j++){
                matrix[i][j]=array[i][j];
            }
        }
        if (this.determinant()==0) {
            System.out.println("Матрица необратима. Невозможно найти решение СЛАУ");
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
            for (int j = 0; j < rowsCount; j++) {
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
        return unknowns;
    }
}
