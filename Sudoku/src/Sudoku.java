
class Sudoku {
	private SudokuBoard sudokuBoard;
	public ColorImage boardImage; // para ver no PandionJ
	int[][] jogoInicial;
	public int[][]solucao;
	
	public Sudoku(String file,int dificuldade) {
		int[][] solucao=SudokuAux.lerFicheiros(file);
	int[][] jogoInicial=SudokuAux.dificuldadeJogo(dificuldade,solucao);
		sudokuBoard=new SudokuBoard(jogoInicial);

	boardImage=SudokuAux.grelhaFina(); 
		SudokuAux.grelhaNumeroscompleta(boardImage,jogoInicial);
	}
	void jogar(int x,int y,int numero){
	if(sudokuBoard.numeroSerZero(x,y)){
		SudokuAux.trocarNumero(x,y,numero,boardImage);
		if(sudokuBoard.getLinhaInvalida(x,numero)!=100){
		SudokuAux.pintarLinhaVermelha(x,boardImage);
		SudokuAux.pintarCiano(x,y,numero,boardImage);
		}
		if(sudokuBoard.getcolunaInvalida(y,numero)!=100){
			SudokuAux.pintarColunaVermelha(y,boardImage);
			SudokuAux.pintarCiano(x,y,numero,boardImage);
	}
		if(sudokuBoard.getCaixaInvalida(x,y,numero)!=100){
			SudokuAux.pintarCaixaVermelha(boardImage,x-x%3,y-y%3);
			SudokuAux.pintarCiano(x,y,numero,boardImage);
		}
		//if(numero!=solucao[x][y])
			//SudokuAux.pintarVermelho(x,y,numero,boardImage);
		
		else
		sudokuBoard.setNumero(numero,x,y);
	
	if(sudokuBoard.jogoAcabado())
		System.out.println("jogo resolvido");
		
		
	}
	}
	
	void reset(){
		sudokuBoard.setReset();
	}
	
	
	
	
}