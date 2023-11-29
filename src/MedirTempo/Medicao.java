package MedirTempo;

public class Medicao {
    int n;

    public Medicao(int tamanho)
    {
        n = tamanho;
    }

    public void linear() {
        long total = 0;

        for(int i = 0; i < n; i++)
            total += i * i;
    }

    public void quadratico() {
        long total = 0;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                total += i*j;
            }
        }
    }

    public void cubico() {
        long total = 0;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < n; k++) {
                    total += i*j*k;
                }
            }
        }
    }
}