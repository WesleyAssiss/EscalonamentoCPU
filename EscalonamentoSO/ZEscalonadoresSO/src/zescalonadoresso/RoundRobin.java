package zescalonadoresso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class RoundRobin {
	// id do processo / quantidade de processos / quantum / valor do tempo de execução / ttempo médio/ turnaround
	private int id, quantProc, iQuantum, iBurst, wt, tr;
	// turnaround / tempo de execução
	private final int fIBurst; // Variável declarada como final pois recebe o valor do tempo de execução e da prioridade
	private String sQuantum, sBurst; // Strings que definem se o burst/tempo de execução serão manuais
	private Random random = new Random();//Caso o usuário digitar A. Cria números aleatórios.
	private List<RoundRobin> rr = new ArrayList<>(); //Criando Lista para armazenar os valores.

         //Quantidade de Processos
	public RoundRobin(int quantProc) {
		this.quantProc = quantProc;
		this.fIBurst = 0;
	}
       
        //Tempo de Execução, e id do Processo.
	public RoundRobin(int iBurst, int id) {
		this.iBurst = iBurst;
		this.id = id;
		this.fIBurst = iBurst;
	}
        //O Objetivo aqui é fazer os devidos cálculos, no Round Robin é que ele prioriza tanto o tempo de execução
        //ou seja, Ex: se p1 for = 10 e p2 = 1. Ele começa por p1 de 0 até 1, coloca p1 em espera, e inicializa p2, somando  tempo de p1 + p2;
        
        //É o tipo de escalonamento preemptivo mais simples e consiste em repartir uniformemente o tempo da CPU
        //entre todos os processos prontos para a execução. Os processos são organizados numa fila circular, 
        //alocando-se a cada um uma fatia de tempo da CPU, igual a um número inteiro de quanta.
	public void escalona() {
		int aux = 0; // recebe o total de iterações necessárias para consumir todos os processos 
		int menor = 0; // indice do processo que será executado
		sQuantum = JOptionPane.showInputDialog("Quantum manual ou automático? [m/A]");
		sQuantum = sQuantum.toLowerCase();// deixa letra minúscula para evitar problemas
		iQuantum = defineQuantum(sQuantum);
		sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase(); // deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { // instancia os processos
			id = i + 1; // soma sentinela "i" para não existir id 0
			iBurst = defineBurst(sBurst, id); // chama função para definição do burst manual / automático 
			RoundRobin p = new RoundRobin(iBurst, id);
			rr.add(p); // adiciona objeto a um ArrayList de processos
			aux += p.iBurst;
		}
		final int fQuantum = iQuantum;//Cada processo recebe uma fatia de tempo, esse tempo é chamado Time-Slice, também conhecido por Quantum
		for (int i = 0; i < aux; i++) {// instancia o total de iterações necessárias para consumir todos os processos
			imprime();
			System.out.println("Quantum: " + iQuantum);
			boolean executou = false;
			if(rr.get(menor).iBurst == 0)
				while(rr.get(menor).iBurst == 0) {
				menor++;
				if (menor == quantProc) 
				menor = 0;
			}
			for (int k = 0; k < quantProc; k++) {
				if (rr.get(k).iBurst > 0) {
					if (menor == quantProc) {
						menor = 0;
						iQuantum = fQuantum;
					}
					if (k != menor) {
						rr.get(k).wt++;
					}

					if (k == menor && !executou) {
						executou = true;
						rr.get(menor).iBurst--;
						iQuantum--;
						if (rr.get(menor).iBurst == 0)
							iQuantum = 0;
					}

					rr.get(k).tr++;

				}
			}
			
			if (iQuantum == 0) {
				iQuantum = fQuantum;
				menor++;
			}
			if (menor == quantProc) {
				menor = 0;
			}
		}
	}

	public void imprime() {
		// tempo total de espera / tempo total de turnaround
		float tWt = 0, trt = 0;
		System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround");
		for (RoundRobin rr : rr) { //loop For each pega os valores que estão armazenados no ArrayList
			tWt += rr.wt; // recebendo o tempo medio dos processos
			trt += rr.tr; // recebendo o turnaround dos processos
                        
                          //Criando uma tabela para a impressao com os seguintes resultados:
			System.out.print("P" + rr.id); //id do processo
			System.out.print("\t\t" + rr.fIBurst);// tempo de execução
			System.out.print("\t" + rr.iBurst);  // tempo de consumo
			System.out.print("\t" + rr.wt); //tempo médio
			System.out.println("\t\t" + rr.tr); //Turnaround
		}
		System.out.println("Tempo médio: " + (tWt) / quantProc);
		System.out.println("Turnaround: " + (trt) / quantProc);
	}
  //Pergunta ao usuário qual opcao ele deseja se é tempo de Quantum manual ou automática.
	public int defineQuantum(String opcao) {
		if (opcao.equals("m")) {
			iQuantum = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do quantum: "));
		} else if (opcao.equals("a")) {
			iQuantum = random.nextInt(50) + 1;
		}
		return iQuantum;

	}
  //Pergunta ao usuário qual opcao ele deseja se é tempo de execução manual ou  automática.
	public int defineBurst(String opcao, int id) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do tempo de execução do processo "+id+": "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}
}
