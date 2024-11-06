package aed;

import java.util.Comparator;

public class TrasladoComparators {

    public Comparator<Traslado> POR_GANANCIA_NETA = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado t1, Traslado t2) {
            return Integer.compare(t2.gananciaNeta, t1.gananciaNeta);  
        }
    };

    public Comparator<Traslado> POR_TIMESTAMP = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado t1, Traslado t2) {
            return Integer.compare(t2.timestamp, t1.timestamp); 
        }
    };
}



