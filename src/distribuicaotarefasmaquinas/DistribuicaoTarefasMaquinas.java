package distribuicaotarefasmaquinas;

import controller.BalanceamentoBLM;
import controller.BalanceamentoBLNM;

/**
 *
 * @author Geam
 */
public class DistribuicaoTarefasMaquinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("\"Heurística: Busca local monótona.\"");
        executarBalanceamentoBLM(10,10);
        executarBalanceamentoBLM(10,20);
        executarBalanceamentoBLM(10,50);
        System.out.println("\"Heurística: Busca local não-monótona.\"");
        executarBalanceamentoBLNM(10,10);
        executarBalanceamentoBLNM(10,20);
        executarBalanceamentoBLNM(10,50);
    }
    
    
    private static void executarBalanceamentoBLM(int qtdVezesExecutar, int qtdMaquinas){
        cabecalho();
        for (int i = 1; i < qtdVezesExecutar+1; i++) {
            
            new BalanceamentoBLM().minimizarTempoProcessamento(i,qtdMaquinas);
        }
    }
    private static void executarBalanceamentoBLNM(int qtdVezesExecutar, int qtdMaquinas){
        cabecalho();
        for (int i = 1; i < qtdVezesExecutar+1; i++) {
            new BalanceamentoBLNM().minimizarTempoProcessamento(i,qtdMaquinas);
        }
    }
    private static void cabecalho(){
        System.out.println(
                 "\nQuantidade de tarefas (n): "
                + "\tQuantidade de máquinas (m): " 
                + "\tReplicação: "
                + "\tTempo: " 
                + "\t"+ "\tIterações: " 
                + "\tValor: "
                + "\t"+  "\tParâmetro:" );
    }

}
