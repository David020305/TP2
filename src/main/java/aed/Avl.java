package aed;

class AVL {
    private class Nodo {
        int _valor;
        Nodo izquierdo, derecho;
        int altura;

        public Nodo(int _valor) {
            this._valor = _valor;
            this.altura = 1;
        }
    }

    private Nodo raiz;

    public void agregar(int valor) {
        raiz = insertar(raiz, valor);
    }

    public Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo._valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (valor > nodo._valor) {
            nodo.derecho = insertar(nodo.derecho, valor);
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        int fb = factorDeBalanceo(nodo);

        if (fb > 1 && valor < nodo.izquierdo._valor) {
            return rotarDerecha(nodo);
        }

        if (fb < -1 && valor > nodo.derecho._valor) {
            return rotarIzquierda(nodo);
        }

        if (fb > 1 && valor > nodo.izquierdo._valor) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (fb < -1 && valor < nodo.derecho._valor) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public void borrar(int valor) {
        raiz = eliminar(raiz, valor);
    }

    private Nodo eliminar(Nodo nodo, int valor) {
        if (nodo == null) {
            return nodo;
        }

        if (valor < nodo._valor) {
            nodo.izquierdo = eliminar(nodo.izquierdo, valor);
        } else if (valor > nodo._valor) {
            nodo.derecho = eliminar(nodo.derecho, valor);
        } else {
            if (nodo.izquierdo == null || nodo.derecho == null) {
                Nodo temp = nodo.izquierdo != null ? nodo.izquierdo : nodo.derecho;
                return temp;
            } else {
                Nodo temp = encontrarMin(nodo.derecho);
                nodo._valor = temp._valor;
                nodo.derecho = eliminar(nodo.derecho, temp._valor);
            }
        }

        actualizarAltura(nodo);
        int fb = factorDeBalanceo(nodo);

        if (fb > 1 && factorDeBalanceo(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }

        if (fb < -1 && factorDeBalanceo(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }

        if (fb > 1 && factorDeBalanceo(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (fb < -1 && factorDeBalanceo(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private Nodo encontrarMin(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    private int factorDeBalanceo(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private void actualizarAltura(Nodo nodo) {
        if (nodo != null) {
            int alturaIzquierda = altura(nodo.izquierdo);
            int alturaDerecha = altura(nodo.derecho);
            nodo.altura = (alturaIzquierda > alturaDerecha) ? 1 + alturaIzquierda : 1 + alturaDerecha;
        }
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        y.izquierdo = x.derecho;
        x.derecho = y;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        x.derecho = y.izquierdo;
        y.izquierdo = x;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    public Nodo getRaiz() {
        return raiz;
    }
}
