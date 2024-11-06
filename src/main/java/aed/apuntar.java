package aed;

public class apuntar {
    
    public void puntero(Traslado[] traslados1, Traslado[] traslados2) {

        int length = traslados1.length;

        for (int i = 0; i < length; i++) {
            traslados1[i].puntero = traslados2[i]; 
        }
        
        for (int j = 0; j < length; j++) {
            traslados2[j].puntero = traslados1[j]; 
        }

    }

}
