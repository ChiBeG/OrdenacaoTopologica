import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrdenacaoTopologica
{
	private class Elo
	{
		/* Identificação do elemento. */
		public int chave;
		
		/* Número de predecessores. */
		public int contador;
		
		/* Aponta para o próximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
	}
	
	private class EloSuc
	{
		/* Aponta para o elo que é sucessor. */
		public Elo id;
		
		/* Aponta para o próximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
	}


	/* Ponteiro (referência) para primeiro elemento da lista. */
	private Elo prim;
	
	/* Número de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* Método responsável pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada) throws FileNotFoundException {
		File entrada = new File(nomeEntrada);
		Scanner leitor = new Scanner(entrada);
		Pattern padrao = Pattern.compile("(\\d+)\\s*<\\s*(\\d+)");
		Matcher matcher;
		int x, y;
		Elo eloX, eloY;
		while (leitor.hasNextLine()) {
			String linha = leitor.nextLine();
			matcher = padrao.matcher(linha);
			if (matcher.find()){
				x = Integer.parseInt(matcher.group(1));
				y = Integer.parseInt(matcher.group(2));
				eloX = insere(x);
				eloY = insere(y);
				insereAresta(eloX, eloY);
			}
		}
			leitor.close();
	}

	private Elo insere(int chave){
		Elo novo = new Elo(chave, 0, null, null);
		if (prim == null){
			prim = novo;
			n++;
			return prim;
		}
		Elo aux = prim;
		while ((aux.prox != null) && (aux.chave != chave)){
			aux = aux.prox;
		}
		if (aux.chave == chave)
			return aux;
		aux.prox = novo;
		n++;
		return aux.prox;
	}


	private void insereAresta(Elo predecessor, Elo sucessor){
		EloSuc eloListaSuc = new EloSuc();

		eloListaSuc.id = sucessor;
		if (predecessor.listaSuc != null){
			eloListaSuc.prox = predecessor.listaSuc;
		}
		predecessor.listaSuc = eloListaSuc;

		sucessor.contador++;
	}


	
	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		System.out.println("Debug:");
		Elo p = prim;
		EloSuc q;
		while (p != null){
			System.out.print(p.chave + " predecessores: " + p.contador + " sucessores: ");
			q = p.listaSuc;
			while (q != null){
				System.out.print(q.id.chave + " -> ");
				q = q.prox;
			}
			System.out.print("NULL\n");
			p = p.prox;

		}
	}

	public void ordena(){

		System.out.println("\nOrdenação Topológica:\n");
		Elo p = prim;
		prim = null;
		Elo q;

		while (p != null){
			q = p;
			p = q.prox;
			if (q.contador == 0){
				q.prox = prim;
				prim = q;
			}
		}


		q = prim;
		EloSuc t;
		Elo fimLista = prim;

		assert fimLista != null;
		while (fimLista.prox != null)
			fimLista = fimLista.prox;



		while (q != null){
			System.out.print(q.chave + " ");
			n--;
			t = q.listaSuc;
			while (t != null){
				t.id.contador--;
				if (t.id.contador == 0){
					t.id.prox = null;
					fimLista.prox = t.id;
					fimLista = fimLista.prox;
				}
				q.listaSuc = q.listaSuc.prox;
				t = q.listaSuc;
			}
			prim = q.prox;
			q = prim;
		}
	}

	/* Método responsável por executar o algotritmo. */
	public boolean executa()
	{
		debug();
		ordena();
		return (n == 0);
	}


	public void geraGrafo(int quantidadeVertices, long quantidadeArestas, double probabilidadeArestas){
		System.out.println("\nRealizando geração do grafo de " + quantidadeVertices + " vértices e " + quantidadeArestas + " arestas...");
		prim = null;

		for (int i = 1; i <= quantidadeVertices; i++)
			insere(i);

		Random random = new Random();


		double probabilidadeAleatoria;

		Elo origem, destino;
		int chaveOrigem, chaveDestino;
		long numeroAtualArestas = 0;

		do{
//			// Algoritmo 1:
//			chaveOrigem = random.nextInt(1, quantidadeVertices+1);
//			chaveDestino = random.nextInt(1, quantidadeVertices+1);
//			//System.out.printf(chaveOrigem + " < " + chaveDestino);
//			if (chaveOrigem != chaveDestino){
//				origem = insere(chaveOrigem);
//				destino = insere(chaveDestino);
//				if (!existeAresta(origem, destino)){
//					insereAresta(origem, destino);
//					if (detectaCiclo(destino)){
//						origem.listaSuc = origem.listaSuc.prox;
//						destino.contador--;
//					}
//					else{
//						numeroAtualArestas++;
//					}
//				}
//			}
			// Algoritmo 2:
			for (origem = prim; origem != null; origem = origem.prox){
				for (destino = prim; destino != null; destino = destino.prox){
					if (origem != destino){
						probabilidadeAleatoria = random.nextDouble(0, 1);
						if (probabilidadeAleatoria <= probabilidadeArestas){
							if (!existeAresta(origem, destino)){
								insereAresta(origem, destino);
								if (detectaCiclo(destino)) {
									origem.listaSuc = origem.listaSuc.prox;
									destino.contador--;
								}
								else {
									numeroAtualArestas++;
									//System.out.print(origem.chave + " < " + destino.chave + " ");
								}
							}
						}
					}
				}
			}
		}while (numeroAtualArestas < quantidadeArestas);
	}

	private boolean existeAresta(Elo origem, Elo destino){
		EloSuc sucessor = origem.listaSuc;
		while (sucessor != null){
			if (sucessor.id.equals(destino))
				return true;
			sucessor = sucessor.prox;
		}
		return false;
	}

	private boolean detectaCiclo(Elo origem){
		boolean[] visitado = new boolean[n];
		boolean[] recursao = new boolean[n];
		return detectaCiclo(origem, visitado, recursao);
	}
	private boolean detectaCiclo(Elo n, boolean[] visitado, boolean[] recursao){
		if (!visitado[n.chave-1]){
			visitado[n.chave-1] = true;
			recursao[n.chave-1] = true;

			EloSuc sucessor = n.listaSuc;
			while(sucessor != null){
				if (!visitado[sucessor.id.chave-1] && detectaCiclo(sucessor.id, visitado, recursao)){
					return true;
				}
				else if (recursao[sucessor.id.chave-1]){
					return true;
				}
				sucessor = sucessor.prox;
			}
		}
		recursao[n.chave-1] = false;
		return false;
	}

	public void clonaGrafo(OrdenacaoTopologica original){
		Elo n = original.prim;
		EloSuc t;
		Elo predecessor, sucessor;
		while (n != null){
			predecessor = this.insere(n.chave);
			t = n.listaSuc;
			while (t != null){
				sucessor = this.insere(t.id.chave);
				insereAresta(predecessor, sucessor);
				t = t.prox;
			}
			n = n.prox;
		}

	}

}