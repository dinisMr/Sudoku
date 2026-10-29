import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class SudokuAux {
	//	1. Validar se uma matriz de inteiros 9x9 representa um Sudoku válido, sendo que o
	//	valor zero representa uma posição não jogada.
	static final int size=9;
	public static int[][]tab = {
			{1,2,3,4,5,6,7,8,9},
			{4,5,6,7,8,9,1,2,3},
			{7,8,9,1,2,3,4,5,6},
			{2,3,4,5,6,7,8,9,1},
			{5,6,7,8,9,1,2,3,4},
			{8,9,1,2,3,4,5,6,7},
			{3,4,5,6,7,8,9,1,2},
			{6,7,8,9,1,2,3,4,5},
			{9,1,2,3,4,5,6,7,8}
	};
	// {1,2,3,4,5,6,7,8,9},{4,5,6,7,8,9,1,2,3},{7,8,9,1,2,3,4,5,6},{2,3,4,5,6,7,8,9,1},{5,6,7,8,9,1,2,3,4},{8,9,1,2,3,4,5,6,7},{3,4,5,6,7,8,9,1,2},{6,7,8,9,1,2,3,4,5},{9,1,2,3,4,5,6,7,8}
	//ver as linhas 
	static boolean linhaValida(int [][]tabuleiro,int numero,int linha){
		for(int i=0;i!=size;i++)
			if(tabuleiro[linha][i]==numero)
				return true;
		return false;
	}
	//ver colunas
	static boolean colunaValida(int [][]tabuleiro,int numero,int coluna){
		for(int i=0;i!=size;i++)
			if(tabuleiro[i][coluna]==numero)
				return true;
		return false;
	}
	//ver caixas
	static boolean caixaValida(int [][]tabuleiro,int numero,int linha,int coluna){
		int linha1Caixa=linha-linha%3;
		int coluna1Caixa=coluna-coluna%3;

		for(int i=linha1Caixa;i!=linha1Caixa+3;i++)
			for(int j=coluna1Caixa;j!=coluna1Caixa+3;j++)
				if(tabuleiro[i][j]==numero)
					return true;
		return false;
	}
	//nesta juntamos ser valido na linha na coluna e na caixa, assim sabemos se é possível colocar este número  
	static boolean numeroValidoNaPosicao(int [][]tabuleiro,int numero,int linha,int coluna){
		return !linhaValida(tabuleiro,numero,linha) && 
				!colunaValida(tabuleiro,numero,coluna) &&
				!caixaValida(tabuleiro,numero,linha,coluna);
	}

	static boolean matrizPossivel(int[][]tabuleiro){
		for(int linha=0;linha<size;linha++){ 
			for(int coluna=0;coluna<size;coluna++){
				if(tabuleiro[linha][coluna]==0){
					for(int numeroTentar=1;numeroTentar<=size;numeroTentar++){
						if(numeroValidoNaPosicao(tabuleiro,numeroTentar,linha,coluna)){
							tabuleiro[linha][coluna]=numeroTentar;

							if(matrizPossivel(tabuleiro)){
								return true;
							}
								else{
								tabuleiro[linha][coluna]=0;
						}
				}
			}
		return false;
			}
		}
		}
	return true;
	}

	//	2. Gerar uma matriz de jogo dada uma solução completa válida (sem zeros),
	//	alterando-a de forma a que parte dos números da mesma sejam substituídos por
	//	zero. Deverá ser fornecida uma percentagem que determina qual a proporção de
	//	posições a zerar.

	static int[][] dificuldadeJogo(int dificuldade,int[][]tabuleiro){
		if(dificuldade<=0||dificuldade>=100)
			throw new IllegalArgumentException ("percentagem tem que ser entre ]0,100[");
		for(int i=0;i!=tabuleiro.length;i++)
			for(int j=0;j!=tabuleiro[0].length;j++)
				if((int)(Math.random()*100)<dificuldade)
					tabuleiro[i][j]=0;
		return tabuleiro;
	}

//		3. Produzir uma String com o conteúdo de uma matriz de inteiros.
//		{{1,2,3},{4,5,6}} → “1 2 3
//		4 5 6”
	static String conteudoMatrix(){
		String resultado = "";
		for(int i=0;i!=tab.length;i++) {
			for(int j=0;j!=tab.length;j++) {
				resultado+=tab[i][j] + " ";
			}
			resultado += "\n" + " ";
		}
		return resultado;
	}

	//	4. Produzir uma imagem a cores com o desenho do tabuleiro Sudoku a partir de uma
	//	matrix de inteiros 9x9. Poderá ser útil desenvolver procedimentos auxiliares para
	//	abordar partes do objetivo (p.e. desenho de grelha, desenho de números).
	static int pixeis=6*Param.GRID_THICKNESS+2*Param.SEGMENTE_GRID_THICKNESS+9*Param.SQUARE_SIZE;

	static ColorImage grelhaFina(){

		ColorImage img=new ColorImage(pixeis,pixeis,Param.SQUARE_BACKCOLOR);
		for(int i=0;i!=pixeis;i++)
			for(int j=0;j!=pixeis;j++){
				if(i%(pixeis/9)==0||i==pixeis/3+1||i==2*pixeis/3+1){
					img.setColor(i,j,Param.SEGMENT_GRID_COLOR);
					img.setColor(j,i,Param.SEGMENT_GRID_COLOR);
				}
			}
		return img;
	}
	static ColorImage tabImage=grelhaFina();//serve para facilitar a criação do projeto


	static void grelhaNumeroXY(int x,int y,int numero,ColorImage img){
		if (numero!=0)
			img.drawCenteredText(x*(Param.SQUARE_SIZE+1)+Param.SQUARE_SIZE/2,y*(Param.SQUARE_SIZE+1)+Param.SQUARE_SIZE/2,String.valueOf(numero),Param.LETTER_SIZE,Param.TEXTCOLOR);

		return;
	}

	static void grelhaNumeroscompleta(ColorImage img,int[][]tabuleiro){
		for(int i=0;i!=9;i++)
			for(int j=0;j!=9;j++)
				grelhaNumeroXY(i,j,tabuleiro[i][j],img);
		return;
	}	


	//5
	static void trocarNumero(int PointX, int PointY, int newNumber, ColorImage img) { 
		
		for(int x = img.getWidth() / 9 * PointX + 1; x < (img.getWidth() / 9) * (PointX + 1); x++) {   
			for(int y = img.getWidth() / 9 * PointY + 2; y <(img.getWidth() / 9) * (PointY + 1); y++) {
				
				img.setColor(x,y,Param.SQUARE_BACKCOLOR);
			
			
			}
		}
			
		String s = Integer.toString(newNumber);
		
		int x = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointX;
		int y = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointY;
		
		img.drawCenteredText(x,y, s, Param.LETTER_SIZE,Param.TEXTCOLOR);
		
		return;
	}
	
	
	
	
	//	6. Dada uma imagem resultante de (4), marcar uma linha do tabuleiro com contorno
	//	vermelho (para sinalizar que está inválida), fornecendo o índice da linha.

	static void pintarLinhaVermelha(int linha,ColorImage img){
		for(int j=0;j!=pixeis;j++){
			img.setColor(pixeis/9*linha,j,Param.PLAY_TEXTCOLOR);
			img.setColor(pixeis/9*linha+pixeis/9,j,Param.PLAY_TEXTCOLOR);
		}
		return;
	}

	//	7. Análogo a (6), mas para colunas.	
	static void pintarColunaVermelha(int coluna,ColorImage img){
		for(int i=0;i!=pixeis;i++){
			img.setColor(i,pixeis/9*coluna,Param.PLAY_TEXTCOLOR);
			img.setColor(i,pixeis/9*coluna+pixeis/9,Param.PLAY_TEXTCOLOR);
		}
		return;
	}

	//	8. Dada uma imagem resultante de (4), marcar o segmento com um contorno vermelho
	//	(para sinalizar que está inválido), fornecendo o índice do segmento. Um segmento
	//	corresponde a um dos nove quadrados 3x3.
	static void pintarCaixaVermelha(ColorImage img,int linha,int coluna){
		for(int i=coluna*pixeis/3;i!=coluna*pixeis/3+pixeis/3;i++)
			for(int j=linha*pixeis/9;j!=linha*pixeis/9+pixeis/3;j++){
				img.setColor(linha*(pixeis/9),j,Param.PLAY_TEXTCOLOR);
				img.setColor(linha*(pixeis/9)+pixeis/3,j,Param.PLAY_TEXTCOLOR);
				img.setColor(i,coluna*(pixeis/9),Param.PLAY_TEXTCOLOR);
				img.setColor(i,coluna*(pixeis/9)+pixeis/3,Param.PLAY_TEXTCOLOR);
			}
		return;
	}
	static int[][] copiar(int[][] m){
		int[][] matrizCopiada=new int[m.length][m[0].length];
		for(int i=0;i!=m.length;i++)
			for(int j=0;j!=m[0].length;j++)
				matrizCopiada[i][j]=m[i][j];
		return matrizCopiada;	
	}

	static int contarZeros(int[][]m){
		int contador=0;
		for(int i=0;i!=m.length;i++)
			for(int j=0;j!=m[0].length;j++)
				if(m[i][j]==0)
					contador++;
		return contador;
	}
	
	 static int[][] lerFicheiros(String file) {
	int[][] tabuleiro=new int[9][9];
		 try {
			Scanner sudokuScanner = new Scanner(new File(file));
			while (sudokuScanner.hasNextLine()) {
				int sb = 0;
				for (int i = 0; i < tabuleiro.length; i++) {
					for (int j = 0; j < tabuleiro[i].length; j++) {
						sb = sudokuScanner.nextInt();
						tabuleiro[j][i] = sb;
					}
				}
			}
			sudokuScanner.close();
			System.out.println(tabuleiro);
		}
		catch (FileNotFoundException e) {
			System.err.println("erro na abertura");
		}
		return tabuleiro;
	}
	
	 
	 static void pintarCiano(int PointX, int PointY, int numeroIgual, ColorImage img) { 
			
			for(int x = img.getWidth() / 9 * PointX + 1; x < (img.getWidth() / 9) * (PointX + 1); x++) {   
				for(int y = img.getWidth() / 9 * PointY + 1; y <(img.getWidth() / 9) * (PointY + 1); y++) {
					
					img.setColor(x,y,Param.PLAYED_NUMBER_BACKCOLOR);
				
				
				}
			}
				
			String s = Integer.toString(numeroIgual);
			
			int x = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointX;
			int y = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointY;
			
			img.drawCenteredText(x,y, s, Param.LETTER_SIZE,Param.PLAY_TEXTCOLOR);
			
			return;
		}
	 
	 static void copiarImagem(ColorImage img,ColorImage imagemACopiar){
		 ColorImage imgACopiada=new ColorImage(img.getWidth(),img.getHeight());
		 for(int i=0;i!=img.getWidth();i++)
				for(int j=0;j!=img.getHeight();j++)
		 imgACopiada.setColor(i,j,img.getColor(i,j));
		 return;	 
		 
	 }
	 
	 
	 static void pintarVermelho(int PointX, int PointY, int numeroIgual, ColorImage img) { 
			
			for(int x = img.getWidth() / 9 * PointX + 1; x < (img.getWidth() / 9) * (PointX + 1); x++) {   
				for(int y = img.getWidth() / 9 * PointY + 1; y <(img.getWidth() / 9) * (PointY + 1); y++) {
					
					img.setColor(x,y,Param.SQUARE_BACKCOLOR);
				
				
				}
			}
				
			String s = Integer.toString(numeroIgual);
			
			int x = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointX;
			int y = Param.SQUARE_SIZE / 2 + img.getWidth()/ 9 * PointY;
			
			img.drawCenteredText(x,y, s, Param.LETTER_SIZE,Param. PLAY_TEXTCOLOR );
			
			return;
		}
	 
	 
}