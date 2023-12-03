import java.io.File;
import java.io.FileNotFoundException;
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
		imprime(prim);
	}
	private void imprime(Elo p){
		if(p == null)
			return;
		System.out.print("\n" + p.chave + " predecessores: " + p.contador + " sucessores: ");
		imprimeSucessores(p.listaSuc);
		imprime(p.prox);
	}
	private void imprimeSucessores(EloSuc q){

		if (q == null){
			System.out.print("NULL");
			return;
		}
		System.out.print(q.id.chave + " -> ");
		imprimeSucessores(q.prox);
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
		System.out.println("\n\nOrdenação Topológica:\n");
		ordena();
		return (n == 0);
	}

	//Você deve então usar esse método desenvolvido para gerar
	//grafos artificiais com os seguintes números de vértices V: 10, 20, 30, 40, 50, 100, 200,
	//500, 1.000, 5.000, 10.000, 20.000, 30.000, 50.000 e 100.000.



}