package aed;

class AVL {
    private class Nodo {
        int _valor; // Atributo que almacena el valor del nodo
        Nodo izquierdo, derecho;
        int altura;

        public Nodo(int _valor) { // Constructor
            this._valor = _valor; // Inicializa el valor
            this.altura = 1; // Nuevo nodo es inicialmente una hoja
        }
    }

    private Nodo raiz;

    // Método para agregar un nuevo valor
    public void agregar(int valor) {
        raiz = insertar(raiz, valor);
    }

    // Método de inserción recursivo
    public Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo._valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (valor > nodo._valor) {
            nodo.derecho = insertar(nodo.derecho, valor);
        } else {
            return nodo; // El valor ya existe
        }

        // Actualiza la altura del nodo ancestro
        actualizarAltura(nodo);

        // Obtiene el factor de balance del nodo ancestro
        int fb = factorDeBalanceo(nodo);

        // Si el nodo se vuelve desbalanceado, hay 4 casos

        // Caso Izquierda Izquierda
        if (fb > 1 && valor < nodo.izquierdo._valor) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha Derecha
        if (fb < -1 && valor > nodo.derecho._valor) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda Derecha
        if (fb > 1 && valor > nodo.izquierdo._valor) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        // Caso Derecha Izquierda
        if (fb < -1 && valor < nodo.derecho._valor) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        // Retorna el nodo (sin cambios)
        return nodo;
    }

    // Método para eliminar un valor
    public void borrar(int valor) {
        raiz = eliminar(raiz, valor);
    }

    // Método de eliminación recursivo
    private Nodo eliminar(Nodo nodo, int valor) {
        // Caso base: el árbol está vacío
        if (nodo == null) {
            return nodo;
        }

        // Encuentra el nodo a eliminar
        if (valor < nodo._valor) {
            nodo.izquierdo = eliminar(nodo.izquierdo, valor);
        } else if (valor > nodo._valor) {
            nodo.derecho = eliminar(nodo.derecho, valor);
        } else {
            // Este es el nodo a eliminar
            if (nodo.izquierdo == null || nodo.derecho == null) {
                Nodo temp = null;
                if (temp == nodo.izquierdo) {
                    temp = nodo.derecho;
                } else {
                    temp = nodo.izquierdo;
                }

                if (temp == null) {
                    return null; // No tiene hijos
                } else {
                    return temp; // Un solo hijo
                }
            } else {
                // Nodo con dos hijos: obtiene el sucesor inorden (el más pequeño en el subárbol derecho)
                Nodo temp = encontrarMin(nodo.derecho);
                nodo._valor = temp._valor; // Copia el valor del sucesor
                nodo.derecho = eliminar(nodo.derecho, temp._valor); // Elimina el sucesor
            }
        }

        // Actualiza la altura del nodo ancestro
        actualizarAltura(nodo);

        // Obtiene el factor de balance del nodo ancestro
        int fb = factorDeBalanceo(nodo);

        // Si el nodo se vuelve desbalanceado, hay 4 casos

        // Caso Izquierda Izquierda
        if (fb > 1 && factorDeBalanceo(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha Derecha
        if (fb < -1 && factorDeBalanceo(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda Derecha
        if (fb > 1 && factorDeBalanceo(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        // Caso Derecha Izquierda
        if (fb < -1 && factorDeBalanceo(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        // Retorna el nodo (sin cambios)
        return nodo;
    }

    // Método para encontrar el nodo con el valor mínimo en un subárbol
    private Nodo encontrarMin(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    // Obtener la altura de un nodo
    private int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    // Obtener el factor de balance (fb) de un nodo
    private int factorDeBalanceo(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Actualizar la altura de un nodo de forma recursiva
    private void actualizarAltura(Nodo nodo) {
        if (nodo != null) {
            int alturaIzquierda = altura(nodo.izquierdo);
            int alturaDerecha = altura(nodo.derecho);
            if (alturaIzquierda > alturaDerecha) {
                nodo.altura = 1 + alturaIzquierda;
            } else {
                nodo.altura = 1 + alturaDerecha;
            }
        }
    }

    // Rotación a la derecha
    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        y.izquierdo = x.derecho;
        x.derecho = y;

        // Actualiza las alturas
        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    // Rotación a la izquierda
    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        x.derecho = y.izquierdo;
        y.izquierdo = x;

        // Actualiza las alturas
        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    // Método para obtener la raíz (para pruebas)
    public Nodo getRaiz() {
        return raiz;
    }
}
