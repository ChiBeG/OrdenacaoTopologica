package MedirTempo;

import MedirTempo.Medicao;

public class Main {
    public static void main(String []args) {
        int tamanho = 1000;
        Medicao medicao = new Medicao(tamanho);

        long t0, t1, tempoProcessamento;

        // Medição Linear
        t0 = System.currentTimeMillis();
        medicao.linear();
        t1 = System.currentTimeMillis();
        tempoProcessamento = t1 - t0;
        System.out.println("Tempo linear: " + tempoProcessamento);

        // Medição Quadrática
        t0 = System.currentTimeMillis();
        medicao.quadratico();
        t1 = System.currentTimeMillis();
        tempoProcessamento = t1 - t0;
        System.out.println("Tempo quadratico: " + tempoProcessamento);

        // Medição Cúbica
        t0 = System.currentTimeMillis();
        medicao.cubico();
        t1 = System.currentTimeMillis();
        tempoProcessamento = t1 - t0;
        System.out.println("Tempo cubico: " + tempoProcessamento);

    }
}