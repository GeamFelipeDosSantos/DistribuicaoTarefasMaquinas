package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import model.Maquina;
import model.Tarefa;
import util.ComparadorMaquinas;

/**
 *
 * @author Geam
 */
public class BalanceamentoBLM {

    private static int quantidadeMaquinas = 0;//quantidade de tarefas;
    private static int quantidadeTarefas = 0;//quantidade de maquinas;
    private static long tempoInicial = 0;
    private static long tempoFinal = 0;
    private static int iteracoes = 0;
    private static int replicacao = 0;

    public void minimizarTempoProcessamento(int replicacoes, int qtdMaquinas) {
        
        replicacao = replicacoes;
        //Cria estrutura das máquinas
        ArrayList<Maquina> maquinas = new ArrayList<>();
        quantidadeMaquinas = qtdMaquinas;
        quantidadeTarefas = gerarQuantidadeTarefas(quantidadeMaquinas);

        for (int i = 0; i < quantidadeMaquinas; i++) {

            ArrayList<Tarefa> tarefas = new ArrayList<>();

            for (int j = 0; j < quantidadeTarefas; j++) {
                tarefas.add(new Tarefa(j, gerarValorProcessamentoAleatorioTarefa()));
            }

            maquinas.add(new Maquina(i, tarefas));
        }

        maquinas = gerarMakespanMaquinas(maquinas);
        //System.out.println("Makespan inicial:" + encontrarMaquinaMaiorMakespan(maquinas).getMakespan());
        //imprimirCargaMaquinas(maquinas);

        tempoInicial = System.currentTimeMillis();

        do {
            iteracoes++;

            maquinas = equilibrarMakespan(maquinas);
            Collections.sort(maquinas, new ComparadorMaquinas());
            maquinas = gerarMakespanMaquinas(maquinas);

        } while (iteracoes < 5000);

        tempoFinal = System.currentTimeMillis();

        System.out.println("======================================================="
                + "\n"
                + "=======================================================");
        //imprimirMaquinasTarefas(maquinas);
        //imprimirCargaMaquinas(maquinas);
        imprimirResultados(maquinas);
        iteracoes = 0;
    }

    private ArrayList<Maquina> equilibrarMakespan(ArrayList<Maquina> maquinas) {

        Maquina maquinaMaiorMakespan = encontrarMaquinaMaiorMakespan(maquinas);
        Maquina maquinaMenorMakespan = encontrarMaquinaMenorMakespan(maquinas);

        Tarefa maiorTarefa = buscarMaiorTarefaMaquina(maquinaMaiorMakespan);
        maquinaMaiorMakespan.getTarefas().remove(maiorTarefa);

        for (int i = 0; i < maquinas.size(); i++) {

            if (maquinas.get(i).equals(maquinaMenorMakespan)) {

                maquinas.get(i).getTarefas().add(maiorTarefa);
            }

        }

        return maquinas;
    }

    private Maquina encontrarMaquinaMenorMakespan(ArrayList<Maquina> maquinas) {

        Maquina maquinaMenorMakespan = new Maquina();
        float menorMakespan = 9999999;

        for (Maquina maquina : maquinas) {
            if (maquina.getMakespan() < menorMakespan) {
                maquinaMenorMakespan = maquina;
                menorMakespan = maquina.getMakespan();
            }
        }

        return maquinaMenorMakespan;
    }

    private Maquina encontrarMaquinaMaiorMakespan(ArrayList<Maquina> maquinas) {

        Maquina maquinaMaiorMakespan = new Maquina();
        float menorMakespan = 0;

        for (Maquina maquina : maquinas) {
            if (maquina.getMakespan() > menorMakespan) {
                maquinaMaiorMakespan = maquina;
                menorMakespan = maquina.getMakespan();
            }
        }

        return maquinaMaiorMakespan;
    }

    private int gerarValorProcessamentoAleatorioTarefa() {

        Random valorAleatorio = new Random();
        int valor = valorAleatorio.nextInt(100) + 1;
        return valor;

    }

    private int gerarQuantidadeTarefas(int quantidadeMaquinas) {
        
        return (int) Math.pow(quantidadeMaquinas, gerarExpoenteAleatorioTarefa());
    }

    private float gerarExpoenteAleatorioTarefa() {

        Random valorAleatorio = new Random();

        float valor = (float) ((valorAleatorio.nextFloat()) + 1.5);

        if (valor % 2 == 0) {
            valor = 2;
        } else {
            valor = (float) 1.5;
        }        
        return valor;

    }

    private Tarefa buscarMaiorTarefaMaquina(Maquina maquina) {

        Tarefa maiorTarefa = new Tarefa(0, 0);

        for (int i = 0; i < maquina.getTarefas().size(); i++) {
            if (maquina.getTarefas().get(i).getValorProcessamento() > maiorTarefa.getValorProcessamento()) {
                maiorTarefa = maquina.getTarefas().get(i);
            }
        }

        return maiorTarefa;
    }

    private ArrayList<Maquina> gerarMakespanMaquinas(ArrayList<Maquina> maquinas) {

        for (Maquina maquina : maquinas) {
            float makespanCalculado = 0;
            for (Tarefa tarefa : maquina.getTarefas()) {
                makespanCalculado += tarefa.getValorProcessamento();
            }
            maquina.setMakespan(makespanCalculado);
        }

        return maquinas;

    }

    public void imprimirMaquinasTarefas(ArrayList<Maquina> maquinas) {

        for (Maquina maquina : maquinas) {
            for (Tarefa tarefa : maquina.getTarefas()) {
                System.out.println("Maquina: " + maquina.getIdMaquina() + " Makespan máquina: " + maquina.getMakespan() + " Tarefa: " + tarefa.getIdTarefa() + " Valor processamento tarefa: " + tarefa.getValorProcessamento());
            }
        }

    }

    public void imprimirCargaMaquinas(ArrayList<Maquina> maquinas) {

        for (Maquina maquina : maquinas) {
            System.out.println("Maquina: " + maquina.getIdMaquina() + " Makespan máquina: " + maquina.getMakespan() + " Qtd. tarefas: " + maquina.getTarefas().size());
        }

    }

    public void imprimirResultados(ArrayList<Maquina> maquinas) {

        System.out.println("\n\nHeurística: Busca local monótona."
                + "\nQuantidade de tarefas (n): " + quantidadeTarefas
                + "\nQuantidade de máquinas (m): " + quantidadeMaquinas
                + "\nReplicação: "+replicacao
                + "\nTempo: " + (tempoFinal - tempoInicial) + "ms"
                + "\nIterações: " + iteracoes
                + "\nValor: " + encontrarMaquinaMaiorMakespan(maquinas).getMakespan()
                + "\nParâmetro:");

    }

}
