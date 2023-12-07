import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException {
		OrdenacaoTopologica ord = new OrdenacaoTopologica();


		String nomeEntrada = "entrada.txt";

		try {
			ord.realizaLeitura(nomeEntrada);
			if(!ord.executa())
				System.out.println("\nO conjunto não é parcialmente ordenado.");
			else
				System.out.println("\nO conjunto é parcialmente ordenado.");
		} catch (FileNotFoundException e) {
			System.out.println("Erro na leitura do arquivo: " + e.getMessage());
		}


		int [] tamanhos = {/*10, 20, 30, 40, 50, 100, 200, 500 , 1000, 5000, 10000, */20000, 30000, 50000, 100000};
		double [] probabilidades = {/*0.03, 0.03, 0.03, 0.03, 0.03, 0.2, 0.2, 0.2, 0.3, 0.6, */0.7, 0.75, 0.8, 0.9, 0.97};
		Random random = new Random();
		int quantidadeVertices;
		long quantidadeArestas, maxArestas, tempoInicial, tempoFinal, tempoTotal;
		double probabilidadeArestas;
		PrintWriter log = new PrintWriter("log.txt");
		OrdenacaoTopologica clone;

		for (int i = 0; i < tamanhos.length; i++){
			quantidadeVertices = tamanhos[i];
			probabilidadeArestas = probabilidades[i];
			maxArestas = ((long)quantidadeVertices * ((long)quantidadeVertices-1))/2;
			quantidadeArestas = random.nextLong(quantidadeVertices, maxArestas);
			log.println("Grafo de " + quantidadeVertices + " vertices e " + quantidadeArestas + " arestas - tempos (nano): ");
			log.flush();
			tempoTotal = 0;
			ord.geraGrafo(quantidadeVertices, quantidadeVertices + 14581847, probabilidadeArestas);
			for (int j = 0; j < 10; j++){
				clone = new OrdenacaoTopologica();
				clone.clonaGrafo(ord);
				tempoInicial = System.nanoTime();
				clone.ordena();
				tempoFinal = System.nanoTime();
				tempoTotal += tempoFinal - tempoInicial;
			}
			tempoTotal /= 10;
			log.println(tempoTotal);
			log.flush();

		}
		log.close();








	}
}
