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

    static ArrayList<Maquina> maquinas = new ArrayList<>();

    public void minimizarTempoProcessamento() {

        //Cria estrutura das máquinas
        //ArrayList<Maquina> maquinas = new ArrayList<>();
        int quantidadeMaquinas = 10;
        int quantidadeTarefas = 31;

        for (int i = 0; i < quantidadeMaquinas; i++) {

            ArrayList<Tarefa> tarefas = new ArrayList<>();

            for (int j = 0; j < quantidadeTarefas; j++) {
                tarefas.add(new Tarefa(j, gerarValorProcessamentoAleatorioTarefa()));
            }

            maquinas.add(new Maquina(i, tarefas));
        }
        System.out.println("Equilibrar makespan esquerda  para Direita ");
        int i = 0;
        do {
            i++;
            maquinas = gerarMakespanMaquinas(maquinas);
            maquinas = equilibrarMakespan(/*maquinas*/);
            Collections.sort(maquinas, new ComparadorMaquinas());
            imprimirCargaMaquinas(maquinas);
        } while (i < 1000);

        /*System.out.println("Equilibrar makespan  Direita para esquerda ");
        i = 0;
        do {
            i++;
            maquinas = gerarMakespanMaquinas(maquinas);
            maquinas = equilibrarMakespanDE(/*maquinas);
            Collections.sort(maquinas, new ComparadorMaquinas());
            imprimirCargaMaquinas(maquinas);
        } while (i < 1000);
         */
        // imprimirMaquinasTarefas(maquinas);
        // imprimirCargaMaquinas(maquinas);
    }

    private ArrayList<Maquina> equilibrarMakespanED(/*ArrayList<Maquina> maquinas*/) {

        Maquina maquinaMaiorMakespan = maquinas.get(0);
        Maquina maquinaMenorMakespan = maquinas.get(1);

        for (int i = 0; i < maquinas.size() - 1; i++) {

            if (maquinas.get(i).getMakespan() > maquinas.get(i + 1).getMakespan()) {

                maquinaMaiorMakespan = maquinas.get(i);
                maquinaMenorMakespan = maquinas.get(i + 1);

                maquinas.remove(maquinas.get(i));
                maquinas.remove(maquinas.get(i));

                System.out.println("Maquina maior makespan: " + maquinaMaiorMakespan.getIdMaquina());
                System.out.println("Maquina menor makespan: " + maquinaMenorMakespan.getIdMaquina());

                Tarefa maiorTarefa = buscarMaiorTarefaMaquina(maquinaMaiorMakespan);

                System.out.println("Maior tarefa: " + maiorTarefa.getIdTarefa() + " Processamento: " + maiorTarefa.getValorProcessamento());

                maquinaMenorMakespan.getTarefas().add(maiorTarefa);
                maquinaMaiorMakespan.getTarefas().remove(maiorTarefa);

                if (!maquinaMaiorMakespan.equals(maquinaMenorMakespan)) {
                    maquinas.add(maquinaMaiorMakespan);
                    maquinas.add(maquinaMenorMakespan);
                }

                //Sai do laço
                i = maquinas.size();
            }

        }

        return maquinas;
    }

    private ArrayList<Maquina> equilibrarMakespanDE(/*ArrayList<Maquina> maquinas*/) {

        Maquina maquinaMaiorMakespan = maquinas.get(0);
        Maquina maquinaMenorMakespan = maquinas.get(1);

        for (int i = 0; i < maquinas.size() - 1; i++) {

            if (maquinas.get(i).getMakespan() < maquinas.get(i + 1).getMakespan()) {

                maquinaMaiorMakespan = maquinas.get(i + 1);
                maquinaMenorMakespan = maquinas.get(i);

                maquinas.remove(maquinas.get(i));
                maquinas.remove(maquinas.get(i));

                System.out.println("Maquina maior makespan: " + maquinaMaiorMakespan.getIdMaquina());
                System.out.println("Maquina menor makespan: " + maquinaMenorMakespan.getIdMaquina());

                Tarefa maiorTarefa = buscarMaiorTarefaMaquina(maquinaMaiorMakespan);

                System.out.println("Maior tarefa: " + maiorTarefa.getIdTarefa() + " Processamento: " + maiorTarefa.getValorProcessamento());

                maquinaMenorMakespan.getTarefas().add(maiorTarefa);
                maquinaMaiorMakespan.getTarefas().remove(maiorTarefa);

                if (!maquinaMaiorMakespan.equals(maquinaMenorMakespan)) {
                    maquinas.add(maquinaMaiorMakespan);
                    maquinas.add(maquinaMenorMakespan);
                }

                //Sai do laço
                i = maquinas.size();
            }

        }

        return maquinas;
    }

    private ArrayList<Maquina> equilibrarMakespan(/*ArrayList<Maquina> maquinas*/) {

        Maquina maquinaMaiorMakespan = maquinas.get(0);
        Maquina maquinaMenorMakespan = maquinas.get(1);

        for (int i = 0; i < maquinas.size() - 1; i++) {
            for (int j = 0; j < maquinas.size() - 1; j++) {

                if (!maquinas.get(i).equals(maquinas.get(j))) {

                    if (maquinas.get(i).getMakespan() > maquinas.get(j).getMakespan()) {

                        maquinaMaiorMakespan = maquinas.get(i);
                        maquinaMenorMakespan = maquinas.get(j);

                        maquinas.remove(maquinas.get(i));
                        maquinas.remove(maquinas.get(j));

                        System.out.println("Maquina maior makespan: " + maquinaMaiorMakespan.getIdMaquina());
                        System.out.println("Maquina menor makespan: " + maquinaMenorMakespan.getIdMaquina());

                        Tarefa maiorTarefa = buscarMaiorTarefaMaquina(maquinaMaiorMakespan);

                        System.out.println("Maior tarefa: " + maiorTarefa.getIdTarefa() + " Processamento: " + maiorTarefa.getValorProcessamento());

                        maquinaMenorMakespan.getTarefas().add(maiorTarefa);
                        maquinaMaiorMakespan.getTarefas().remove(maiorTarefa);

                        //if (!maquinaMaiorMakespan.equals(maquinaMenorMakespan)) {
                        maquinas.add(maquinaMaiorMakespan);
                        maquinas.add(maquinaMenorMakespan);
                        //}

                        //Sai do laço
                        j = maquinas.size();
                        //Sai do laço
                        i = maquinas.size();
                    }
                }

            }

        }

        return maquinas;
    }

    private int gerarValorProcessamentoAleatorioTarefa() {

        Random aleatorio = new Random();
        int valor = aleatorio.nextInt(100) + 1;
        //System.out.println("Número gerado: " + valor);
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

    private Maquina atualizarTarefasMaquina(Maquina maquina, Tarefa tarefaRemover) {

        Maquina novaMaquina = new Maquina();
        ArrayList<Tarefa> novaListaTarefas = new ArrayList<>();
        for (Tarefa tarefa : maquina.getTarefas()) {
            if (tarefa == tarefaRemover) {
                maquina.getTarefas().remove(tarefa);
                novaMaquina = maquina;
            }
        }
        return novaMaquina;
    }

    private ArrayList<Maquina> excluirMaquinaDesatualizada(Maquina maquina, ArrayList<Maquina> maquinas) {

        maquinas.remove(maquina);
        return maquinas;
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

    /*private ArrayList<Maquina> ordenarMaquinas(ArrayList<Maquina> maquinas) {
        
        
        
        ArrayList<Maquina> maquinasOrdenadas = new ArrayList<>();
        int menorIdMaquina = 99;
        
        for (int i = 0; i < maquinas.size(); i++) {
            if(menorIdMaquina > maquinas.get(i).getIdMaquina()){
                menorIdMaquina = maquinas.get(i).getIdMaquina();
                trocarPosicoesMaquinas(maquinas);
                i = maquinas.size();
            }
        }
        
        return maquinasOrdenadas;
        
    }*/
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

}
