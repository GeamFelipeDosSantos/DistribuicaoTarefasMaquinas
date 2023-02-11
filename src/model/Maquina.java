package model;

import java.util.ArrayList;

/**
 *
 * @author Geam
 */
public class Maquina {

    private int idMaquina;
    private float makespan;
    private ArrayList<Tarefa> tarefas = new ArrayList<>();

    public Maquina() {
    }

    public Maquina(int idMaquina, ArrayList<Tarefa> tarefas) {
        this.idMaquina = idMaquina;
        this.tarefas = tarefas;
    }
    
    
    @Override
   public boolean equals(Object o) {
      boolean result = false;
      if (this.getIdMaquina()== ((Maquina)o).getIdMaquina()) {
         result = true;
      }
      return result;
   } 

    
    
    /**
     * @return the tarefas
     */
    public ArrayList<Tarefa> getTarefas() {
        return tarefas;
    }

    /**
     * @param tarefas the tarefas to set
     */
    public void setTarefas(ArrayList<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    /**
     * @return the idMaquina
     */
    public int getIdMaquina() {
        return idMaquina;
    }

    /**
     * @param idMaquina the idMaquina to set
     */
    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    /**
     * @return the makespan
     */
    public float getMakespan() {
        return makespan;
    }

    /**
     * @param makespan the makespan to set
     */
    public void setMakespan(float makespan) {
        this.makespan = makespan;
    }

}
