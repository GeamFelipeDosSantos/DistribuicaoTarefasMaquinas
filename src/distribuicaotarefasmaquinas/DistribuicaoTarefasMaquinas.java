package distribuicaotarefasmaquinas;

import controller.BalanceamentoBLM;

/**
 *
 * @author Geam
 */
public class DistribuicaoTarefasMaquinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        executarBalanceamentoBLM(10,10);
        executarBalanceamentoBLM(10,20);
        executarBalanceamentoBLM(10,50);
    }
    
    
    private static void executarBalanceamentoBLM(int qtdVezesExecutar, int qtdMaquinas){
        
        for (int i = 1; i < qtdVezesExecutar+1; i++) {
            new BalanceamentoBLM().minimizarTempoProcessamento(i,qtdMaquinas);
        }
    }

}
