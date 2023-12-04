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
		while (leitor.hasNextLine()) {
			String linha = leitor.nextLine();
			matcher = padrao.matcher(linha);
			if (matcher.find()){
				x = Integer.parseInt(matcher.group(1));
				y = Integer.parseInt(matcher.group(2));
				inserePar(x, y);
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
	private void inserePar(int chavePredecessor, int chaveSucessor){
		Elo eloPredecessor = insere(chavePredecessor);
		Elo eloSucessor = insere(chaveSucessor);
		EloSuc eloListaSuc = new EloSuc();

		eloListaSuc.id = eloSucessor;
		if (eloPredecessor.listaSuc != null){
			eloListaSuc.prox = eloPredecessor.listaSuc;
		}
		eloPredecessor.listaSuc = eloListaSuc;

		eloSucessor.contador++;

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

	// Métodos recursivos de impressão descartados:
	private void imprimeRecursivo(Elo p){
		if(p == null)
			return;
		System.out.print("\n" + p.chave + " predecessores: " + p.contador + " sucessores: ");
		imprimeSucessoresRecursivo(p.listaSuc);
		imprimeRecursivo(p.prox);
	}
	private void imprimeSucessoresRecursivo(EloSuc q){

		if (q == null){
			System.out.print("NULL");
			return;
		}
		System.out.print(q.id.chave + " -> ");
		imprimeSucessoresRecursivo(q.prox);
	}

	private void ordena(){

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
		System.out.println("\nOrdenação Topológica:");
		ordena();
		return (n == 0);
	}

	//Você deve então usar esse método desenvolvido para gerar
	//grafos artificiais com os seguintes números de vértices V: 10, 20, 30, 40, 50, 100, 200,
	//500, 1.000, 5.000, 10.000, 20.000, 30.000, 50.000 e 100.000.

	public void geraGrafo(int quantidadeVertices){
		System.out.println("Realizando geração do grafo de " + quantidadeVertices + " nós...");
		prim = null;

		for (int i = 1; i <= quantidadeVertices; i++){ // Insere os vértices - O(n)
			insere(i);
		}

		Random random = new Random();
		int maxArestas = quantidadeVertices * 2;//(quantidadeVertices * (quantidadeVertices - 1))/2;
		int quantidadeTotalArestas = random.nextInt(quantidadeVertices, maxArestas);

		double probabilidadeArestas = 0.2;
		double probabilidadeAleatoria;

		int origem, destino;
		int numeroAtualArestas = 0;

		System.out.println("Quantidade sorteada de arestas: " + quantidadeTotalArestas);
		do{
			for (origem = 1; origem <= quantidadeVertices; origem++){
				for (destino = 1; destino <= quantidadeVertices; destino++){
					if (origem != destino){
						probabilidadeAleatoria = random.nextDouble();
						if (probabilidadeAleatoria <= probabilidadeArestas){
							inserePar(origem, destino);
							numeroAtualArestas++;
						}
					}
				}
			}
		}while (numeroAtualArestas < quantidadeTotalArestas);
	}

	private boolean detectaCiclo(){
		return false;
	}

	public long medirTempo(int quantidadeVertices) {
		long t0, t1;
		long totalProcessamento = 0;
		long processamentoIndividual;

		for(int i = 0; i < 10; i++) {
			t0 = System.currentTimeMillis();
			geraGrafo(quantidadeVertices);
			// falta o método executa() aqui
			t1 = System.currentTimeMillis();

			processamentoIndividual = t1 - t0;
			totalProcessamento += processamentoIndividual;
		}

		return totalProcessamento / 10;
	}
}