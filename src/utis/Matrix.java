package utis;
/**
 * ����
 * @author ��ΰ��
 *
 */
public class Matrix {
	/**
	 * ����ת��
	 * 
	 * @param matrix
	 *            ԭ����
	 * @param n
	 *            ����(n*n)�ĸ߻��
	 * @return ת�ú�ľ���
	 */
	public static double[][] transposingMatrix(double[][] matrix, int n) {
		double nMatrix[][] = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				nMatrix[i][j] = matrix[j][i];
			}
		}
		return nMatrix;
	}
	/**
	 * �������
	 * @param A ����A
	 * @param B ����B
	 * @param n ����Ĵ�Сn*n
	 * @return �������
	 */
	public static double[][] matrixMultiply(double[][] A, double[][] B, int n) {
		double nMatrix[][] = new double[n][n];
		double t = 0.0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				t = 0;
				for(int k=0; k<n; k++) {
					t += A[i][k]*B[k][j];
				}
				nMatrix[i][j] = t;			}
		}
		return nMatrix;
	}
}
