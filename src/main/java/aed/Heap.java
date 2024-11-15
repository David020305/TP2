package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> _heap;
    private Comparator<T> _comparador;
    private int tam;



    // Constructor que acepta un arreglo de Traslado y usa el algoritmo de Floyd (heapify)
    public Heap(Comparator<T> comparador, T[] elementos) {

        _heap = new ArrayList<>();
        _comparador = comparador;
        tam = elementos.length;

        // Agregar todos los elementos al heap y asignarles su posición
        for (int i = 0; i < tam; i++) {
            T elemento = elementos[i];
            _heap.add(elemento);

            actualizarposicion(elemento,i);
            //elemento.posicion = i;  // Asignar la posición correcta en el heap
        }

        // Aplicar el algoritmo de Floyd (heapify)
        for (int i = tam / 2 - 1; i >= 0; i--) { // O(T)
            bajar(i,_heap.get(i));
            actualizarposicion(_heap.get(i), i);
        }
    }
    public void cambiarSuperavit (int indice, T elemento){
        //eliminarElemento(indece);
        //encolar(elmento);

        _heap.set(indice, elemento);
        subir(indice, elemento);
        bajar(indice, elemento);
        actualizarposicion(elemento, indice);
        
    }

    private void actualizarposicion(T elemento, int pos){ //O(1)
        
        if (elemento.getClass() == Traslado.class){
            Traslado trasla = (Traslado) elemento; // Cast 
            trasla.posicion = pos;
        }
        else{
            Ciudad ciud = (Ciudad) elemento; // Cast 
            ciud.posicion = pos;
        }
    }

    // Método para encolar un Traslado
    public void encolar(T elemento) { // O(log n)
        _heap.add(elemento);  // Añadir el Traslado al heap
        actualizarposicion(elemento,tam);  // Asignar la posición actual en el heap
        tam++;
        subir(tam - 1, _heap.get(tam-1));  // Mantener el orden del heap
    }

    public void eliminarElemento(int indice) { // O(log n)
        T ultimoElemento = _heap.get(tam - 1);
        _heap.set(indice, ultimoElemento);
  
        actualizarposicion(ultimoElemento, indice);

        bajar(indice, _heap.get(indice));

        _heap.remove(tam - 1);
        tam--;
        
    }

    // Método para subir un elemento en el heap
    private void subir(int indice, T elemento) {
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            if (_comparador.compare(_heap.get(indice), _heap.get(indicePadre)) < 0) {
                break;
            } else if (_comparador.compare((_heap.get(indice)), _heap.get(indicePadre)) == 0) {
                if (elemento.getClass() == Traslado.class) {
                    Traslado tras = (Traslado) elemento;
                    Traslado padre = (Traslado) _heap.get(indicePadre);
                    if (tras.id > padre.id) {
                        break;
                    }
                } else {
                    Ciudad ciu = (Ciudad) elemento;
                    Ciudad padre = (Ciudad) _heap.get(indicePadre);
                    if (ciu.id > padre.id) {
                        break;
                    }
                }
            }
            swap(indice, indicePadre);

            indice = indicePadre;
        }
    }

    // Método para intercambiar dos elementos
    private void swap(int i, int j) {
        T aux = _heap.get(i);
        _heap.set(i, _heap.get(j));
        _heap.set(j, aux);

        // Actualizar las posiciones de los elementos después del swap
        actualizarposicion(_heap.get(i),i);
        actualizarposicion(_heap.get(j),j);
    }

    // Método para obtener la raíz del heap
    public T obtenerRaiz() {
        return _heap.get(0);
    }

    // Método para sacar la raíz del heap
    public T sacarRaiz() {
        T raiz = _heap.get(0);
        T ultimoElemento = _heap.remove(tam - 1);
        tam--;

        if (tam > 0) {
            _heap.set(0, ultimoElemento);
            actualizarposicion(ultimoElemento,0);
            
            bajar(0, _heap.get(0));
        }

        return raiz;
    }

    // Método para bajar un elemento en el heap

    /*private void bajar(int indice, T elemento) {
        while (true) {
            int indiceIzquierdo = 2 * indice + 1;
            int indiceDerecho = 2 * indice + 2;
            int indiceMayor = indice;

            if (indiceIzquierdo < tam &&
                _comparador.compare(_heap.get(indiceIzquierdo), _heap.get(indiceMayor)) >= 0) {
                    if (_comparador.compare(_heap.get(indiceIzquierdo), _heap.get(indiceMayor)) == 0) {
                        if (elemento.getClass() == Traslado.class) {
                            Traslado tras = (Traslado) _heap.get(indiceMayor);;
                            Traslado hijoizq = (Traslado) _heap.get(indiceIzquierdo);
                            if (tras.id > hijoizq.id) {
                                indiceMayor = indiceIzquierdo;
                            }
                        } else {
                            Ciudad ciu = (Ciudad) _heap.get(indiceMayor);;
                            Ciudad hijoizq = (Ciudad) _heap.get(indiceIzquierdo);
                            if (ciu.id > hijoizq.id) {
                                indiceMayor = indiceIzquierdo;
                            }
                        }
                    } else {
                        indiceMayor = indiceIzquierdo;
                    }
            }
            if (indiceDerecho < tam &&
                _comparador.compare(_heap.get(indiceDerecho), _heap.get(indiceMayor)) >= 0) {
                    if (_comparador.compare(_heap.get(indiceDerecho), _heap.get(indiceMayor)) == 0) {
                        if (elemento.getClass() == Traslado.class) {
                            Traslado tras = (Traslado) _heap.get(indiceMayor);
                            Traslado hijoder = (Traslado) _heap.get(indiceDerecho);
                            if (tras.id > hijoder.id) {
                                indiceMayor = indiceDerecho;
                            }
                        } else {
                            Ciudad ciu = (Ciudad) _heap.get(indiceMayor);
                            Ciudad hijoder = (Ciudad) _heap.get(indiceDerecho);
                            if (ciu.id > hijoder.id) {
                                indiceMayor = indiceDerecho;
                            }
                        }
                    } else {
                        indiceMayor = indiceDerecho;
                    }
            }

            if (indiceMayor == indice) {
                break;
            }
 
            swap(indice, indiceMayor);
            indice = indiceMayor;
        }
    }*/
     
    private void bajar(int indice, T elemento) {
        while (true) {
            int indiceIzquierdo = 2 * indice + 1;
            int indiceDerecho = 2 * indice + 2;
            int indiceMayor = indice;
    
            if (indiceIzquierdo < tam) {
                indiceMayor = obtenerIndiceMayor(indiceMayor, indiceIzquierdo, elemento);
            }
    
            if (indiceDerecho < tam) {
                indiceMayor = obtenerIndiceMayor(indiceMayor, indiceDerecho, elemento);
            }
    
            if (indiceMayor == indice) {
                break;
            }
    
            swap(indice, indiceMayor);
            indice = indiceMayor;
        }
    }

    private int obtenerIndiceMayor(int actual, int candidato, T elemento) {
        int comparacion = _comparador.compare(_heap.get(candidato), _heap.get(actual));
        
        if (comparacion > 0) {
            return candidato;
        } else if (comparacion == 0) {
            if (elemento.getClass() == Traslado.class) {
                Traslado actualElem = (Traslado) _heap.get(actual);
                Traslado candidatoElem = (Traslado) _heap.get(candidato);
                if (actualElem.id > candidatoElem.id) {
                    return candidato;
                } else {
                    return actual;
                }
            } else if (elemento.getClass() == Ciudad.class) {
                Ciudad actualElem = (Ciudad) _heap.get(actual);
                Ciudad candidatoElem = (Ciudad) _heap.get(candidato);
                if (actualElem.id > candidatoElem.id) {
                    return candidato;
                } else {
                    return actual;
                }
            }
        }
        return actual;
    }
    

    // Método para obtener el tamaño del heap
    public int tamano() {
        return tam;
    }
    //( NO SE USA EN EL PROGRAMA)// solo para imprimir
    public T obtenerElemento(int index) {
        return _heap.get(index);
    }
    

}
