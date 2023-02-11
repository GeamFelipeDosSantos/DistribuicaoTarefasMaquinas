package model;

/**
 *
 * @author Geam
 */
public class Tarefa {

    private int idTarefa;
    private float valorProcessamento;

    public Tarefa(int idTarefa, float valorProcessamento) {
        this.idTarefa = idTarefa;
        this.valorProcessamento = valorProcessamento;
    }

    public Tarefa() {
    }

    /**
     * @return the valorProcessamento
     */
    public float getValorProcessamento() {
        return valorProcessamento;
    }

    /**
     * @param valorProcessamento the valorProcessamento to set
     */
    public void setValorProcessamento(float valorProcessamento) {
        this.valorProcessamento = valorProcessamento;
    }

    /**
     * @return the idTarefa
     */
    public int getIdTarefa() {
        return idTarefa;
    }

    /**
     * @param idTarefa the idTarefa to set
     */
    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

}
