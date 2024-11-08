package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap {
    private ArrayList<Traslado> _heap;
    private Comparator<Traslado> _comparador;
    private int tam;

    // Constructor que acepta un arreglo de Traslado y usa el algoritmo de Floyd (heapify)
    public Heap(Comparator<Traslado> comparador, Traslado[] elementos) {
        _heap = new ArrayList<>();
        _comparador = comparador;
        tam = elementos.length;

        // Agregar todos los elementos al heap y asignarles su posición
        for (int i = 0; i < tam; i++) {
            Traslado elemento = elementos[i];
            _heap.add(elemento);
            elemento.posicion = i;  // Asignar la posición correcta en el heap
        }

        // Aplicar el algoritmo de Floyd (heapify)
        for (int i = tam / 2 - 1; i >= 0; i--) {
            bajar(i);
        }
    }

    // Método para encolar un Traslado
    public void encolar(Traslado elemento) {
        _heap.add(elemento);  // Añadir el Traslado al heap
        elemento.posicion = tam;  // Asignar la posición actual en el heap
        tam++;
        subir(tam - 1);  // Mantener el orden del heap
    }

    // Método para subir un elemento en el heap
    private void subir(int indice) {
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            if (_comparador.compare(_heap.get(indice), _heap.get(indicePadre)) <= 0) {
                break;
            }
            swap(indice, indicePadre);
            
            indice = indicePadre;
        }
    }

    // Método para intercambiar dos elementos
    private void swap(int i, int j) {
        Traslado aux = _heap.get(i);
        _heap.set(i, _heap.get(j));
        _heap.set(j, aux);

        // Actualizar las posiciones de los elementos después del swap
        _heap.get(i).posicion = i;
        _heap.get(j).posicion = j;
    }

    // Método para obtener la raíz del heap
    public Traslado obtenerRaiz() {
        return _heap.get(0);
    }

    // Método para sacar la raíz del heap
    public Traslado sacarRaiz() {
        Traslado raiz = _heap.get(0);
        Traslado ultimoElemento = _heap.remove(tam - 1);
        tam--;

        if (tam > 0) {
            _heap.set(0, ultimoElemento);
            ultimoElemento.posicion = 0;  // Actualizar la posición del último elemento
            bajar(0);
        }

        return raiz;
    }

    // Método para bajar un elemento en el heap
    private void bajar(int indice) {
        while (true) {
            int indiceIzquierdo = 2 * indice + 1;
            int indiceDerecho = 2 * indice + 2;
            int indiceMayor = indice;

            if (indiceIzquierdo < tam &&
                _comparador.compare(_heap.get(indiceIzquierdo), _heap.get(indiceMayor)) > 0) {
                indiceMayor = indiceIzquierdo;
            }

            if (indiceDerecho < tam &&
                _comparador.compare(_heap.get(indiceDerecho), _heap.get(indiceMayor)) > 0) {
                indiceMayor = indiceDerecho;
            }

            if (indiceMayor == indice) {
                break;
            }
 
            swap(indice, indiceMayor);
            indice = indiceMayor;
        }
    }

    // Método para verificar si el heap está vacío
    public boolean estaVacio() {
        return tam == 0;
    }

    // Método para obtener el tamaño del heap
    public int tamano() {
        return tam;
    }

    public void eliminarElemento(Traslado elemento) {

        int indice = elemento.posicion;

        Traslado ultimoElemento = _heap.get(tam - 1);
        _heap.set(indice, ultimoElemento);
        ultimoElemento.posicion = indice;  

        _heap.remove(tam - 1);
        tam--;
        
        bajar(indice);
        
    }

    public Traslado obtenerElemento(int index) {
        if (index >= 0 && index < _heap.size()) {
            return _heap.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de los límites del heap");
        }
    }
    

}
