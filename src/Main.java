import java.io.FileNotFoundException;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException {
		OrdenacaoTopologica ord = new OrdenacaoTopologica();

		String nomeEntrada = "entrada.txt";

		ord.realizaLeitura(nomeEntrada);

		if(!ord.executa())
			System.out.println("\nO conjunto não é parcialmente ordenado.");
		else
			System.out.println("\nO conjunto é parcialmente ordenado.");
	}
}
