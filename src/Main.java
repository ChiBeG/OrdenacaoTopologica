import java.io.FileNotFoundException;
import java.util.Random;

public class Main
{
	public static void main(String[] args){
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


		int [] tamanhos = {10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 30000, 50000, 100000};
		Random random = new Random();
		int quantidadeArestas, maxArestas;


		for (int quantidadeVertices : tamanhos){
			maxArestas = (quantidadeVertices * (quantidadeVertices - 1))/2;
			quantidadeArestas = random.nextInt(quantidadeVertices, maxArestas);
			for (int i = 0; i < 10; i++){
				ord.geraGrafo(quantidadeVertices, quantidadeArestas);
				// Marca tempo inicial.
				if (!ord.executa())
					System.out.println("\nO conjunto não é parcialmente ordenado");
				else
					System.out.println("\nO conjunto é parcialmente ordenado.");
				// Marca tempo final.
			}
		}




	}
}
