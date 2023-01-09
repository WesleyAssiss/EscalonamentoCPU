package zescalonadoresso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//Shorted Jobs First//
public class Duling implements Comparable<Duling> {
    //quantidade de processos /Tempo de Execução / Tempo médio / turnaround /prioridade

    private int quantProc, iBurst, wt, tt, priority;
    private String sBurst, prioridade;// String que definem se o burst/tempo de execução e prioridade serão manuais
    private int id;// id do processo 
    private Random random = new Random();//Caso o usuário digitar A. Cria números aleatórios.
    private List<Duling> duling = new ArrayList<>();//Criando Lista para armazenar os valores.

    //Quantidade de Processos
    public Duling(int quantProc) {
        //identificando a variavel local da classe
        this.quantProc = quantProc;
    }

    //Tempo de Execução, e id do Processo e prioridade;
    public Duling(int iBurst, int id, String sBurst, int priority, String prioridade) {
        //identificando a variavel local da classe
        this.iBurst = iBurst;
        this.id = id;
        this.sBurst = sBurst;
        this.priority = priority;
        this.prioridade = prioridade;
    }
    //O Objetivo aqui é fazer os devidos cálculos, no Duling o primeiro processo que executa é o com 
    //prioridade maior, no caso o 1 e assim pro diante.

    public void escalona() {
        int j = -1;
        sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
        sBurst = sBurst.toLowerCase();// deixa letra minúscula para evitar problemas

        prioridade = JOptionPane.showInputDialog("Prioridade manual ou automático? [m/A] ");
        prioridade = prioridade.toLowerCase();// deixa letra minúscula para evitar problemas

        for (int i = 0; i < quantProc; i++) {// instancia os processos

            iBurst = defineBurst(sBurst);// chama função para definição do tempo de execução manual / automático 
            priority = definePrioridade(prioridade);// chama função para definição da prioridade manual / automático 
            id = i + 1;// soma  "i" para não existir id 0  
            Duling p = new Duling(iBurst, id, sBurst, priority, prioridade);
            duling.add(p); // adiciona objeto a um ArrayList de processos

        }

        Collections.sort(duling);// Ordena processos por maior prioridade (no caso 1). 
        for (int i = 0; i < quantProc; i++) {
            if (i == 0) {
                duling.get(i).tt = duling.get(i).iBurst;
            } else {
                duling.get(i).tt = duling.get(j).tt + duling.get(i).iBurst;
                duling.get(i).wt = duling.get(j).iBurst + duling.get(j).wt;
            }
            j++;
        }
    }

    //Imprimindo o resultado
    public void imprime() {
        // tempo total de espera / tempo total de turnaround
        int tWt = 0, tTt = 0;
        System.out.println("Process\tBurst\tWaiting Time\tTurnaround\tPriority");
        for (Duling duling : duling) { //loop For each pega os valores que estão armazenados no ArrayList
            tWt += duling.wt; // recebendo o tempo medio dos processos
            tTt += duling.tt; // recebendo o turnaround dos processos

            //Criando uma tabela para a impressao com os seguintes resultados:      
            System.out.print("P" + duling.id); //id do processo
            System.out.print("\t" + duling.iBurst);// tempo de execução
            System.out.print("\t" + duling.wt);//tempo médio
            System.out.print("\t\t" + duling.tt);//Turnaround
            System.out.println("\t\t" + duling.priority);//prioridade
        }
        System.out.println("Tempo médio: " + (tWt) / quantProc);
        System.out.println("Turnaround: " + (tTt) / quantProc);
    }
    //Pergunta ao usuário qual opcao ele deseja se é tempo de execução  manual ou  automática.

    public int defineBurst(String opcao) {
        if (opcao.equals("m")) {
           iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do tempo de execução "+(id+1)+": "));
        } else if (opcao.equals("a")) {
            iBurst = random.nextInt(50) + 1;
        }
        return iBurst;
    }
    //Pergunta ao usuário qual opcao ele deseja se a prioridade é manual ou  automática.

    public int definePrioridade(String opcao) {
        if (opcao.equals("m")) {
            priority = Integer.valueOf(JOptionPane.showInputDialog("Tamanho da prioridade: "+(id+1)+": "));
        } else if (opcao.equals("a")) {
            priority = random.nextInt(50) + 1;
        }
        return iBurst;
    }

    @Override
    public int compareTo(Duling p) {//sobescrição do metodo compareTo para organizar o ArrayList com o atributo tempo de execução
        if (iBurst < p.iBurst) {
            return -1;
        } else if (iBurst > p.iBurst) {
            return 1;
        } else {
            return 0;
        }
    }
}
