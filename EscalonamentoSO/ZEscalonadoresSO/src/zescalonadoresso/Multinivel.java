package zescalonadoresso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class Multinivel implements Comparable<Multinivel> {
	private int id, quantProc, iQuantum, iBurst, wt, tr; // quantidade de processos / valor do burst // waiting/ time /
	// turnaround / tempo de chegada
	private final int fIBurst; // Variáveis declaradas como final pois recebem o valor recebido do tempo de
								// chegada e do burst
	private String sQuantum, sBurst; // Strings que definem se o burst/tempo de chegada manuais
	private Random random = new Random();//Caso o usuário digitar A. Cria números aleatórios.
	private List<Multinivel> multinivel = new ArrayList<>();//Criando Lista para armazenar os valores.

         //Quantidade de Processos
	public Multinivel(int quantProc) {
		this.quantProc = quantProc;
		this.fIBurst = 0;
	}
       //Tempo de Execução, e id do Processo.
	public Multinivel(int iBurst, int id) {
		this.iBurst = iBurst;
		this.id = id;
		this.fIBurst = iBurst;
	}

        //Múltiplas Filas é um tipo de algoritmo de escalonamento, no qual são usadas filas de processos. 
        // Cada fila tem um determinado nível de prioridade. Sendo um dos mais antigos agendadores de prioridade,
        //estava presente no CTSS (Compatible Time-Sharing System - Sistema Compatível de Divisão por Tempo).
	public void escalona() { // recebe o total de iterações necessárias para consumir todos os processos
		
		int menor = 0; // definir qual é o processo com menor tempo de execução 
		sQuantum = JOptionPane.showInputDialog("Quantum manual ou automático? [m/A]");
		sQuantum = sQuantum.toLowerCase();
		iQuantum = defineQuantum(sQuantum);
		sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase(); // deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { // instancia os processos
			iBurst = defineBurst(sBurst); // chama função para definição do tempo de execução manual / automático 
			id = i + 1; // soma sentinela "i" para não existir id 0
			Multinivel p = new Multinivel(iBurst, id);
			multinivel.add(p); // adiciona objeto a um lista de processos
		}
		final int fQuantum = iQuantum;
		for (int i = 0; i < fQuantum * quantProc; i++) {
			imprime();
			System.out.println("Quantum: " + iQuantum);
			boolean executou = false;
			for (int k = 0; k < quantProc; k++) {
				if (multinivel.get(k).iBurst > 0) {
					if (menor == quantProc) {
						break;
					}
					if (k != menor) {
						multinivel.get(k).wt++;
					}

					if (k == menor && !executou) {
						executou = true;
						multinivel.get(menor).iBurst--;
						iQuantum--;
						if (multinivel.get(menor).iBurst == 0)
							iQuantum = 0;
					}

					multinivel.get(k).tr++;

				}
			}

			if (iQuantum == 0) {
				iQuantum = fQuantum;
				menor++;
			}
			if (menor == quantProc) {
				break;
			}
		}
		int aux = 0;
		for (int i = 0; i < quantProc; i++) {
			aux += multinivel.get(i).iBurst;
		}
		// Após a finalização do quantum por todos os processos (uma vez apenas), os que
		// restaram irão ser consumidos com o SJF
		menor = 0;
		Collections.sort(multinivel);
		for (int i = 0; i < aux; i++) {
			imprime();
			boolean executou = false;
			for (int k = 0; k < quantProc; k++) {
				if(multinivel.get(menor).iBurst == 0 && !executou) {
					menor ++;
				}
				if(menor == quantProc)
					menor = 0;
				if (multinivel.get(k).iBurst > 0) {

					if (k != menor){
						multinivel.get(k).wt++;
					}
					if (k == menor && !executou) {
						multinivel.get(k).iBurst--;
						executou = true;
					} 
					multinivel.get(k).tr++;
				}
				
			}
		}
	}

	public void imprime() {
		// tempo total de espera / tempo total de turnaround
		float tWt = 0, trt = 0;
		System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround");
		for (Multinivel multinivel : multinivel) {
			tWt += multinivel.wt;
			trt += multinivel.tr;
			System.out.print("P" + multinivel.id);//id do processo
			System.out.print("\t\t" + multinivel.fIBurst);//tempo de execução
			System.out.print("\t" + multinivel.iBurst);// tempo de consumo
			System.out.print("\t" + multinivel.wt);//tempo médio
			System.out.println("\t\t" + multinivel.tr);//Turnaround
		}
		System.out.println("Tempo médio: " + (tWt) / quantProc);
		System.out.println("Turnaround: " + (trt) / quantProc);
	}
 //Pergunta ao usuário qual opcao ele deseja se é Quantum  manual ou  automático.
	public int defineQuantum(String opcao) {
		if (opcao.equals("m")) {
			iQuantum = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do quantum: "));
		} else if (opcao.equals("a")) {
			iQuantum = random.nextInt(50) + 1;
		}
		return iQuantum;

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

	@Override
	public int compareTo(Multinivel p) {//sobescrição do metodo compareTo para organizar o ArrayList com o atributo tempo de execução
		if (iBurst < p.iBurst) {
			return -1;
		} else if (iBurst > p.iBurst) {
			return 1;
		} else {
			return 0;
		}
	}
}
