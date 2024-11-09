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
            return Integer.compare(t2.timestamp, t1.timestamp); 
        }
    };
    public Comparator<Ciudad> POR_SUPERAVIT = new Comparator<Ciudad>() {
        @Override
        public int compare(Ciudad c1, Ciudad c2) {
            return Integer.compare(c1.superavit, c2.superavit); 
        }
    };
}



