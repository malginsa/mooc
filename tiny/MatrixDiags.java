public class MatrixDiags
{
	private int[][] matrix;

	public MatrixDiags(int rows, int cols) {
		matrix = new int [rows][cols];
	}

	public void Print() {
		int rows = this.matrix.length;
		int cols = this.matrix[0].length;
		System.out.println("\nMatrix's size is  rows = " + rows + "  cols = " + cols);
		for(int row = 0; row < this.matrix.length; row++) {
			for(int col = 0; col < this.matrix[0].length; col++) {
				System.out.print(this.matrix[row][col] + " ");
			}
			System.out.println();
		}
	}

	public void FillOnes() {
		int X = this.matrix[0].length;
		int Y = this.matrix.length;
		if(X > Y) {
			int Xratio = X / Y;
			for(int y = 0; y < Y; y++)
				for(int n = 0; n <= Xratio; n++) {
					int x = y + n * Y;
					if( x < X )
						this.matrix[ y ][ x ] = 1;
					x = (n+1)*Y - y - 1;
					if( x < X )
						this.matrix[ y ][ x ] = 1;
				}
			}
		else {
			int Yratio = Y / X;
			for(int x = 0; x < X; x++)
				for(int n = 0; n <= Yratio; n++) {
					int y = x + n * X;
					if( y < Y )
						this.matrix[ y ][ x ] = 1;
					y = (n+1)*X - x - 1;
					if( y < Y )
						this.matrix[ y ][ x ] = 1;
				}
			}
		}

	public static void main(String[] args) {
		MatrixDiags matrix = new MatrixDiags(7,24);
		matrix.FillOnes();
		matrix.Print();
		matrix = new MatrixDiags(27,14);
		matrix.FillOnes();
		matrix.Print();
	}
}

