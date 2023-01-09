package zescalonadoresso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//First-Come, First-Served//
public class FCFS {
                  //quantidade de processos /Tempo de Execução / Tempo médio / turnaround 
	private int quantProc, iBurst, wt, tt;
        private int id;// id do processo 
	private String sBurst;// String que definem se o burst/tempo de execução serão manuais
	private Random random = new Random(); //Caso o usuário digitar A. Cria números aleatórios.
	private List<FCFS> fcfs = new ArrayList<>();//Criando Lista para armazenar os valores.

        //Quantidade de Processos
	public FCFS(int quantProc) {
             //identificando a variavel local da classe
		this.quantProc = quantProc;
	}

        //Tempo de Execução, e id do Processo.
	public FCFS(int iBurst, String sBurst,int id) {
            //identificando a variavel local da classe
		this.iBurst = iBurst;
		this.sBurst = sBurst;
                this.id = id;
	}

        //O Objetivo aqui é fazer os devidos cálculos, no FCFS o primeiro que chega é o primeiro que sai
        //e assim por diante, na mesma lógica da Fila.
	public void escalona() {
		int j =  -1;
		sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase();// deixa letra minúscula para evitar problemas
		for(int i = 0; i < quantProc; i++) {// instancia os processos
			id = i+1;  // soma  "i" para não existir id 0                    
			iBurst = defineBurst(sBurst);// chama função para definição do tempo de execução manual / automático 
			FCFS p = new FCFS(iBurst, sBurst,id);
			fcfs.add(p); // adiciona objeto a um ArrayList de processos
                        
			if(i == 0) {
				p.tt = p.iBurst;
			}else {
				p.tt = fcfs.get(j).tt+p.iBurst;//incremento do tempo de turnaround  + tempo de execução para que todos os processos tenham valor da sentinela "i" e a execução do algoritmo seja correta
				p.wt = fcfs.get(j).iBurst+fcfs.get(j).wt;//incremento do tempo de execução + tempo medio para que todos os processos tenham valor da sentinela "i" e a execução do algoritmo seja correta
			}
			j ++;
		}
	}

        //Imprimindo o resultado
	public void imprime() {
                // tempo total de espera / tempo total de turnaround
		int tWt = 0,tTt = 0;
		System.out.println("Processo\tBurst\tWaiting Time\tTurnaround");
		for (FCFS fcfs : fcfs) {//loop For each pega os valores que estão armazenados no ArrayList
			tWt += fcfs.wt; // recebendo o tempo medio dos processos
			tTt += fcfs.tt; // recebendo o turnaround dos processos
                        
                        //Criando uma tabela para a impressao com os seguintes resultados:            
			System.out.print("P" + fcfs.id); //id do processo
			System.out.print("\t\t" + fcfs.iBurst);// tempo de execução
			System.out.print("\t" + fcfs.wt);//tempo médio
			System.out.println("\t\t" + fcfs.tt);//Turnaround
		}
		System.out.println("Tempo médio: "+(tWt)/quantProc);
		System.out.println("Turnaround: "+(tTt)/quantProc);
	}

        //Pergunta ao usuário qual opcao ele deseja se é tempo de execução  manual ou  automática.
	public int defineBurst(String opcao) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do tempo de execução "+id+": "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}
}
