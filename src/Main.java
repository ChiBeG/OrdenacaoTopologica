import java.io.FileNotFoundException;

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




	}
}
