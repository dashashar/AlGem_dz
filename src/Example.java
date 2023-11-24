import java.util.Arrays;
public class Example {
    public static void main(String[] args){
//        double[][] matr1=new double[0][0];
//        Matrix matrix1 = new Matrix(matr1,0,0);
//        matrix1.makeMatrix();
//        matrix1.printMatrix();
//
//        double[][] matr2=new double[0][0];
//        Matrix matrix2 = new Matrix(matr2,0,0);
//        matrix2.makeMatrix();
//        matrix2.printMatrix();
//
//        matrix1.multiMatrix(matrix2);
//        matrix1.transMatrix();

        double[][] matr={{1,2,1,2,2},{3,3,2,-1,2},{9,10,5,-1,-2},{1,2,-1,-1,2},{8,9,4,0,2}};
        Matrix matrix = new Matrix(matr,5,5);
        double[][] free={{3},{0},{0},{-3},{-3}};
        Matrix freeCof = new Matrix(free,5,1);
        matrix.solve(freeCof);
    }
}
