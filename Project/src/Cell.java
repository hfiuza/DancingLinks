
public class Cell {
	Cell R, L, U, D; //U and D might be instances of column
	Column C; //instance of class column
	int i, j;
	public Cell(Column C, int i, int j){
		R = L = U = D = null;
		this.C = C;
		this.i = i;
		this.j = j;
	}
}
