import java.util.Arrays;
public class Example {
    public static void main(String[] args){
        double[][] matr={{1,2,3,6,3},{2,4,5,4,7},{4,8,12,24,5},{2,4,6,12,6}};
        Matrix matrix = new Matrix(matr,4,5);
        matrix.gauss_view(matrix).printMatrix();
    }
}
