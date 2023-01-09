package zescalonadoresso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

//Shorted Jobs First//
public class SJF implements Comparable<SJF> {
                  //quantidade de processos /Tempo de Execução / Tempo médio / turnaround 
	private int quantProc, iBurst, wt, tt;
	private String sBurst; // String que definem se o burst/tempo de chegada serão manuais
	private int id; // id do processo
	private Random random = new Random();  //Caso o usuário digitar A. Cria números aleatórios.
	private List<SJF> sjf = new ArrayList<>(); //Criando Lista para armazenar os valores.

        //Quantidade de Processos
	public SJF(int quantProc) {
		this.quantProc = quantProc;
	}

        //Tempo de Execução, e id do Processo.
	public SJF(int iBurst,int id, String sBurst) {
		this.iBurst = iBurst;
		this.id = id;
		this.sBurst = sBurst;
	}

         //O Objetivo aqui é fazer os devidos cálculos, no SJF o que tem menor número de tempo de chegada.     
	public void escalona() {
		int j = -1;
		sBurst = JOptionPane.showInputDialog("Tempo de execução manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase();// deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { // instancia os processos
			iBurst = defineBurst(sBurst); // chama função para definição do tempo de chegada manual / automático 
			id = i+1; // soma  "i" para não existir id 0   
			SJF p = new SJF(iBurst,id, sBurst);
			sjf.add(p); // adiciona objeto a um ArrayList de processos
		}
                //java.util.Collections.sort() método está presente na classe java.util.Collections.
                //Ele é usado para classificar os elementos presentes na lista especificada de Coleção
                //em ordem crescente. Ele funciona de forma semelhante ao método java.util.Arrays.sort(), 
                //mas é melhor do que pode classificar os elementos do Array, bem como a lista vinculada,
                //a fila e muitos outros presentes nele.
		Collections.sort(sjf);//pega os valores do tempo de chegada do menor para o maior.
		for (int i = 0; i < quantProc; i++) {// instancia os processos
			if(i == 0) {
				sjf.get(i).tt = sjf.get(i).iBurst;
			}else {
				sjf.get(i).tt = sjf.get(j).tt+sjf.get(i).iBurst;
				sjf.get(i).wt = sjf.get(j).iBurst+sjf.get(j).wt;
			}
			j ++;
		}
	}

	public void imprime() {
             // tempo total de espera / tempo total de turnaround
		int tWt = 0, tTt = 0;
		System.out.println("Processo\tBurst\tWaiting Time\tTurnaround");
		for (SJF sjf : sjf) { //loop For each pega os valores que estão armazenados no ArrayList
			tWt += sjf.wt; // recebendo o tempo medio dos processos
			tTt += sjf.tt; // recebendo o turnaround dos processos
                        
                        //Criando uma tabela para a impressao com os seguintes resultados:
			System.out.print("P" + sjf.id); //id do processo
			System.out.print("\t\t" + sjf.iBurst);// tempo de chegada
			System.out.print("\t" + sjf.wt);//tempo médio
			System.out.println("\t\t" + sjf.tt); //Turnaround
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
        //A anotação @override é uma forma de garantir que estamos na verdade sobrescrevendo
        //um método e não criando um novo.
	@Override
	public int compareTo(SJF p) {//sobescrição do metodo compareTo para organizar o ArrayList com o atributo tempo de execução
		if (iBurst < p.iBurst) {
			return -1;
		} else if (iBurst > p.iBurst) {
			return 1;
		} else {
			return 0;
		}
	}
}
