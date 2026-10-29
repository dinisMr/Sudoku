class SudokuBoard {
	int[][] jogo ;
	final int[][] inicial;
	int[][]historico=new int[81][2];
	int numeroJogadas=0;
	
	//construtor
	SudokuBoard(int[][] tabuleiro){
		jogo=tabuleiro;
		inicial=SudokuAux.copiar(jogo);
		if(tabuleiro.length!=9||tabuleiro[0].length!=9)
		throw new IllegalArgumentException ("dimensões do tabuleiro erradas");
		if(!SudokuAux.matrizPossivel(inicial))
			throw new IllegalArgumentException("Matriz não é valida");
	}
	int getNumero(int x,int y){  //funciona
		return jogo[y][x];
	}
	boolean numeroSerZero(int x,int y){
		if(jogo[x][y]==0)
			return true;
					return false;
	}
	
	void setNumero(int numero,int x,int y){ //funciona
		if(numero<1||numero>9)
		throw new IllegalArgumentException("numero tem que ser entre 0 e 9");
		if(jogo[x][y]!=0)
			throw new IllegalArgumentException("o quadrado tem que estar a branco");
		jogo[y][x]=numero;

		historico[numeroJogadas][0]=x;
		historico[numeroJogadas][1]=y;
		numeroJogadas++;
		
		jogoAcabado();
		
	}
	void setNumeroAleatorio(int x,int y){
		int nr=(int)(Math.random()*10);
		jogo[y][x]=nr;
	if(!SudokuAux.numeroValidoNaPosicao(jogo,nr,x,y))
		setNumeroAleatorio(x,y);
	}
	
	void setReset(){
		for(int i=0;i!=9;i++)
			for(int j=0;j!=9;j++)
				jogo[i][j]=inicial[i][j];
	}
	
	int getLinhaInvalida(int linha,int numero){
	if(SudokuAux.linhaValida(jogo,numero,linha))
	return linha;
	else
		return 100;
	}
	int getcolunaInvalida(int coluna,int numero){
		if(SudokuAux.colunaValida(jogo,numero,coluna))
		return coluna;
		else
			return 100;
	}
	int getCaixaInvalida(int linha,int coluna,int numero){
		if(SudokuAux.caixaValida(jogo,numero,linha,coluna))
	return linha & coluna;
		else
			return 100;
	}
	
	void undo(){
		numeroJogadas--;
		jogo[historico[numeroJogadas][1]][historico[numeroJogadas][0]]=0;
	}
	
	boolean jogoAcabado(){
		for(int i=0;i!=9;i++)
			for(int j=0;j!=9;j++)
	if(jogo[i][j]==0)
		return false;
		return true;
	}
	int[][] matrizInical(){
		return inicial;
	}
			
	static void test(int[][]m){
		SudokuBoard SB=new SudokuBoard(m);
		return;
	}
}