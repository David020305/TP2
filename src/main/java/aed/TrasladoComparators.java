package aed;

import java.util.Comparator;

public class TrasladoComparators {

    public Comparator<Traslado> POR_GANANCIA_NETA = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado t1, Traslado t2) {
            return Integer.compare(t1.gananciaNeta, t2.gananciaNeta);  
        }
    };

    public Comparator<Traslado> POR_TIMESTAMP = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado t1, Traslado t2) {
            return Integer.compare(-t1.timestamp, -t2.timestamp); 
        }
    };
}



