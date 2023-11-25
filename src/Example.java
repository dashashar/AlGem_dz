import java.util.Arrays;
public class Example {
    public static void main(String[] args){
        Matrix matrix1 = Matrix.makeMatrix();
        matrix1.printMatrix();
        matrix1.transMatrix().printMatrix();
        System.out.println(matrix1.findRank());

        Matrix matrix2 = Matrix.makeMatrix();
        matrix2.printMatrix();

        matrix1.multiMatrix(matrix2);


//        double[][] matr={{1,2,1,2,2},{3,3,2,-1,2},{9,10,5,-1,-2},{1,2,-1,-1,2},{8,9,4,0,2}};
//        Matrix matrix = new Matrix(matr,5,5);
//        double[][] free={{3},{0},{0},{-3},{-3}};
//        Matrix freeCof = new Matrix(free,5,1);
//        matrix.solve(freeCof);
    }
}
