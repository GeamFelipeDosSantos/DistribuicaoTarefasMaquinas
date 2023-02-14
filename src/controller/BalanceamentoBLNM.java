package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Maquina;
import model.Tarefa;
import util.ComparadorMaquinas;

/**
 *
 * @author Geam
 */
public class BalanceamentoBLNM {

    private static int quantidadeMaquinas = 0;//quantidade de tarefas;
    private static int quantidadeTarefas = 0;//quantidade de maquinas; 
    
    private static int iteracoes = 0;
    private static double pertubacaoGlobal = 0;
    private static int replicacao = 0;
    private static long tempoInicial = 0;
    private static long tempoFinal = 0;    

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
            Random valorAleatorio = new Random();
            double valor = valorAleatorio.nextDouble();   
            maquinas = equilibrarMakespan(maquinas,valor);
            Collections.sort(maquinas, new ComparadorMaquinas());
            maquinas = gerarMakespanMaquinas(maquinas);

        } while (iteracoes < 1000);

        tempoFinal = System.currentTimeMillis();

       
        //imprimirMaquinasTarefas(maquinas);
        //imprimirCargaMaquinas(maquinas);
        imprimirResultados(maquinas);
        iteracoes = 0;
    }

    private ArrayList<Maquina> equilibrarMakespan(ArrayList<Maquina> maquinas, double pertubacao) {
        
        //80 - bagunca
        double quantidadeMovimento = maquinas.size() * pertubacao;
        pertubacaoGlobal = pertubacao;
        //20 - normal
        
        ArrayList<Maquina> maquinasPertubacao = new ArrayList<>();
        ArrayList<Maquina> maquinasRestante = new ArrayList<>();
        
         for (int i = 0; i < maquinas.size(); i++) {
                // 0 1 2 3 4
            if (i <= quantidadeMovimento ) {                
                maquinasPertubacao.add(maquinas.get(i));                    
            }
        } 
        maquinas.clear();
        maquinas.addAll(AjustarMaskspeanMaquina(maquinasPertubacao));
        maquinas.addAll(maquinasRestante);
        
        return maquinas;
    }
    
    private ArrayList<Maquina> AjustarMaskspeanMaquina(ArrayList<Maquina> maquinas) {

        Maquina maquinaMaiorMakespan = encontrarMaquinaMaiorMakespan(maquinas);
        Maquina maquinaMenorMakespan = encontrarMaquinaMenorMakespan(maquinas);
        
            Tarefa maiorTarefa = buscarMaiorTarefaMaquina(maquinaMaiorMakespan);
            maquinaMaiorMakespan.getTarefas().remove(maiorTarefa);
            
            maquinas.remove(maquinaMaiorMakespan);
            maquinas.add(maquinaMaiorMakespan);
            
            for (int i = 0; i < maquinas.size(); i++) {

                if (maquinas.get(i).equals(maquinaMenorMakespan)) {

                    maquinas.get(i).getTarefas().add(maiorTarefa);
                    
                    break;
                }   
            } 
            
        return maquinas;
    }
   

    private Maquina encontrarMaquinaMenorMakespan(ArrayList<Maquina> maquinas) {

        Maquina maquinaMenorMakespan = new Maquina();
        float menorMakespan = 999999999;

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
        //System.out.println("Número gerado: " + valor);
        return valor;

    }

    private int gerarQuantidadeTarefas(int quantidadeMaquinas) {

        //Random valorAleatorio = new Random();
        //int valor = valorAleatorio.nextInt(4);
        //return (int) Math.pow(quantidadeMaquinas, valor);
        return (int) Math.pow(quantidadeMaquinas, gerarExpoenteAleatorioTarefa());
    }

    private float gerarExpoenteAleatorioTarefa() {

        Random valorAleatorio = new Random();
        int valor = (valorAleatorio.nextInt()) ;

        if (valor % 2 == 0) {
            return 2;
        } else {
            return (float)1.5;
        }  

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
        
         System.out.println(
                 "\t" + quantidadeTarefas
                +"\t"+ "\t"+"\t"+ "\t"+ "\t"+   quantidadeMaquinas
                +"\t"+ "\t"+ replicacao
                +"\t"+ "\t"+  (tempoFinal - tempoInicial) + "ms"
                +"\t"+"\t"+   iteracoes
                +"\t"+ "\t"+  Math.round(encontrarMaquinaMaiorMakespan(maquinas).getMakespan()/ maquinas.size())
                +"\t"+"\t"+   pertubacaoGlobal);

    }
}
