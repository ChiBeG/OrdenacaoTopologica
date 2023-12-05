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


		int [] tamanhos = {10, 20, 30, 40, 50, 100, 200, 500 , 1000, 5000, 10000, 20000, 30000, 50000, 100000};
		long [] tamanhoArestas = {20, 100, 300, 400, 700, 2000, 4000, 10000, 50000, 250000, 500000, 1000000, 1500000, 5000000, 10000000};
		Random random = new Random();
		int quantidadeVertices;
		long quantidadeArestas, tempoInicial, tempoFinal, tempoTotal;
		PrintWriter log = new PrintWriter("log.txt");

		for (int i = 0; i < tamanhos.length; i++){
			quantidadeVertices = tamanhos[i];
			quantidadeArestas = tamanhoArestas[i];
			log.println("Grafo de " + quantidadeVertices + " e " + quantidadeArestas + " vertices - tempos (nano): ");
			tempoTotal = 0;
			for (int j = 0; j < 10; j++){
				ord.geraGrafo(quantidadeVertices, quantidadeArestas);
				tempoInicial = System.nanoTime();
				if (!ord.executa())
					System.out.println("\nO conjunto não é parcialmente ordenado");
				else
					System.out.println("\nO conjunto é parcialmente ordenado.");
				tempoFinal = System.nanoTime();
				tempoTotal += tempoFinal - tempoInicial;
			}
			log.println(tempoTotal);

		}
		log.close();




	}
}
