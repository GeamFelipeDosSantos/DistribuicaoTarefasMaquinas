
package util;

import java.util.Comparator;
import model.Maquina;

/**
 *
 * @author Geam
 */
public class ComparadorMaquinas implements Comparator<Maquina> {

    public int compare(Maquina m1, Maquina m2) {
        if (m1.getIdMaquina() < m2.getIdMaquina()) {
            return -1;
        } else if (m1.getIdMaquina() > m2.getIdMaquina()) {
            return +1;
        } else {
            return 0;
        }
    }

}
