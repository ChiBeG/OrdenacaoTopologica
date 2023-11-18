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
		try{
			File entrada = new File(nomeEntrada);
			Scanner leitor = new Scanner(entrada);
			Pattern padrao = Pattern.compile("(\\d+)\\s*<\\s*(\\d+)")	;
			Matcher matcher;
			int x, y;
			while (leitor.hasNextLine()) {
				String linha = leitor.nextLine();
				matcher = padrao.matcher(linha);
				System.out.println(linha);
				if (matcher.find()){
					x = Integer.parseInt(matcher.group(1));
					y = Integer.parseInt(matcher.group(2));
					inserePar(x, y);
				}
			}
			leitor.close();
		}catch (FileNotFoundException e){
			System.out.println("Erro na leitura do arquivo de entrada!");
		}
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
		System.out.println("Debug");
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
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{
		debug();
		return false;
	}
}