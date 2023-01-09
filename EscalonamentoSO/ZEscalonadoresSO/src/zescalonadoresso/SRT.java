package zescalonadoresso;
// Private = apenas as instancias da classe podem vizualizá-la.
//Private Final =  depois de incializada o seu valor não poderá mais ser modificado.
//Static = seu valor será único, ou seja sempre o mesmo

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//Shortest-Remaining-Time-First
public class SRT implements Comparable<SRT> {
    //quantidade de processos / valor do burst / waiting time / turnaround / tempo de chegada

    private int quantProc, iBurst, wt, tr, tc;

    private final int tfc, fIBurst; //Variáveis declaradas como final pois recebem o valor recebido da prioridade e do tempo de execução
    private String sBurst, sTc; //Strings que definem se o burst/tempo de execução e prioridade serão manuais
    private int id; //id do processo
    private Random random = new Random(); //Caso o usuário digitar A. Cria números aleatórios.
    private List<SRT> srtf = new ArrayList<>(); //Criando Lista para armazenar os valores.

    //Quantidade de Processos
    public SRT(int quantProc) {
        this.quantProc = quantProc;
        this.tfc = 0; //instanciando como 0 a prioridade;
        this.fIBurst = 0; //instanciando como 0 o tempo de execução;
    }

    public SRT(int iBurst, int id, int tc, String sBurst) {
        this.iBurst = iBurst;
        this.id = id;
        this.tc = tc;
        this.sBurst = sBurst;
        this.tfc = tc;
        this.fIBurst = iBurst;
    }

    public void escalona() {
        int aux = 0; //recebe o total de iterações necessárias para consumir todos os processos 
        int menor = 0; //definir qual é o processo com menor burst 
        sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
        sBurst = sBurst.toLowerCase();// deixa letra minúscula para evitar problemas

        sTc = JOptionPane.showInputDialog("Prioridade manual ou automático? [m/A] ");
        sTc = sTc.toLowerCase();// deixa letra minúscula para evitar problemas
        for (int i = 0; i < quantProc; i++) { //instancia os processos 
            iBurst = defineBurst(sBurst); //chama função para definição do burst manual / automático 
            
            tc = defineTc(sTc);
            id = i + 1; //soma sentinela "i" para não existir id 0
            SRT p = new SRT(iBurst, id, tc, sBurst);
            srtf.add(p); //adiciona objeto a um ArrayList de processos
            aux += p.iBurst + p.tc;
        }
        for (int i = 0; i < aux; i++) {
            Collections.sort(srtf); // Ordena processos por menor tempo de execução 
            imprime(); // Exibe passo a passo para demonstrar o consumo dos processos
            if (srtf.get(menor).iBurst == 0) //avança sentinela "menor" quando o processo for consumido
            {
                menor++;
            }
            if (menor == quantProc) {
                break;
            }
            boolean executou = false; //flag para determinar quando processo com menor burst executou
            for (int k = 0; k < quantProc; k++) { //varre os processos para consumo e determinação do tempo de espera
/* Enquando o tempo de chegada no for alcançado pela sentinela "i" ou caso o processo estiver consumido, não é necessário executar outras verificações*/
                if (srtf.get(k).iBurst > 0) {
                    //consome processo com menor burst 
                    if (k == menor) {
                        srtf.get(menor).iBurst--;
                        executou = true;

                    }
                    //outros processos aguardam para serem executados e seu tempo de espera aumenta
                    if (k != menor && executou) {
                        srtf.get(k).wt++;
                    }
                    //execução de outros processos quando o processo com menor burst não é alcançado pela sentinela "i"
                    if (k != menor && !executou) {
                        srtf.get(k).iBurst--;
                    }
                    //incremento da prioridade para que todos os processos tenham valor da sentinela "i" e a execução do algoritmo seja correta
                    srtf.get(k).tc++;
                    //definição do turnaround
                    srtf.get(k).tr++;
                }
            }
        }
    }

    public void imprime() {
        //tempo total de espera / tempo total de turnaround
        float tWt = 0, trt = 0;
        System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround\tTempo Chegada");
        for (SRT srtf : srtf) { //loop For each pega os valores que estão armazenados no ArrayList
            tWt += srtf.wt; // recebendo o tempo medio dos processos
            trt += srtf.tr; // recebendo o turnaround dos processos

            //Criando uma tabela para a impressao com os seguintes resultados:   
            System.out.print("P" + srtf.id); //id do processo
            System.out.print("\t\t" + srtf.fIBurst); //tempo de execução
            System.out.print("\t" + srtf.iBurst);// tempo de consumo
            System.out.print("\t" + srtf.wt);//tempo médio
            System.out.print("\t\t" + srtf.tr); //Turnaround
            System.out.println("\t\t" + srtf.tfc); // tempo total de turnaround
        }
        System.out.println("Tempo médio: " + (tWt) / quantProc);
        System.out.println("Turnaround: " + (trt) / quantProc);
    }
    //Pergunta ao usuário qual opcao ele deseja se é tempo de execução  manual ou  automática.

    public int defineBurst(String opcao) {
        if (opcao.equals("m")) {
            iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do tempo de execução " + (id + 1) + ": "));
        } else if (opcao.equals("a")) {
            iBurst = random.nextInt(50) + 1;
        }
        return iBurst;
    }
    //Pergunta ao usuário qual opcao ele deseja se a prioridade manual ou automática.

    public int defineTc(String opcao) {
        if (opcao.equals("m")) {
            tc = Integer.valueOf(JOptionPane.showInputDialog("Prioridade " + (id + 1) + ": "));
        } else if (opcao.equals("a")) {
            tc = random.nextInt(50) + 1;
        }
        return tc;
    }

    //sobescrição do metodo compareTo para organizar o ArrayList com o atributo tempo de execução
    @Override
    public int compareTo(SRT p) {
        if (iBurst < p.iBurst) {
            return -1;
        } else if (iBurst > p.iBurst) {
            return 1;
        } else {
            return 0;
        }
    }
}
