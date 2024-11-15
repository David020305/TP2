package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

 
    void assertSetEquals(int[] arr1, int[] arr2) {
        assertEquals(arr1.length, arr2.length, "Los arreglos no tienen el mismo tamaño.");
        for (int e1 : arr1) {
            boolean encontrado = false;
            for (int e2 : arr2) {
                if (e1 == e2) {
                    encontrado = true;
                    break;
                }
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + Arrays.toString(arr2));
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_con_mas_ganancia_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void despachar_mas_viejo_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_mas_viejo_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void despachar_mixtos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void agregar_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 1, 10001, 5),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }
    //--------------------------------------------------------------------------------------------------------------------
    @Test
    void mayor_perdida_sin_despachar(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)), sis.ciudadesConMayorPerdida());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)), sis.ciudadesConMayorGanancia());
    }

    @Test
    void mayor_perdida_dos_con_igual_valor(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 6, 2, 90, 2),
            new Traslado(5, 4, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(2);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 2)), sis.ciudadesConMayorPerdida());
    }
@Test
    void mayor_perdida_uno_id_menor(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 6, 2, 100, 2),
            new Traslado(5, 4, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void mayor_perdida_ids_no_contiguos(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(45, 3, 4, 1, 7),
            new Traslado(101, 6, 5, 40, 6),
            new Traslado(13, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(98, 3, 4, 100, 3),
            new Traslado(37, 6, 2, 100, 2),
            new Traslado(22, 4, 1, 100, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 2, 4)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void mayor_perdida_despacho_todos_mismo_valor(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 1, 20, 7),
            new Traslado(7, 6, 2, 20, 6),
            new Traslado(6, 5, 3, 20, 5),
            new Traslado(2, 2, 4, 20, 4),
            new Traslado(3, 3, 5, 20, 3),
            new Traslado(4, 6, 6, 20, 2),
            new Traslado(5, 4, 0, 20, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(0, 6, 5)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(4);

        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)), sis.ciudadesConMayorPerdida());
    }
    @Test
    void n_mayor_a_cant_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(10);

        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void eliminar_todos_los_traslados_del_sistema_por_mas_antiguos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        assertSetEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, sis.despacharMasAntiguos(7));
        assertSetEquals(new int[]{}, sis.despacharMasRedituables(5));

    }
    @Test
    void eliminar_todos_los_traslados_del_sistema_por_mas_redituables() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        assertSetEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, sis.despacharMasRedituables(7));
        assertSetEquals(new int[]{}, sis.despacharMasAntiguos(5));

    }
    @Test
    void eliminar_en_partes_los_traslados_del_sistema() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        assertSetEquals(new int[]{5, 6, 7}, sis.despacharMasRedituables(3));
        assertSetEquals(new int[]{1, 2, 3, 4}, sis.despacharMasAntiguos(4));

    }
    @Test
    void eliminar_cero_traslados_del_sistema() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        assertSetEquals(new int[]{}, sis.despacharMasRedituables(0));
        assertSetEquals(new int[]{}, sis.despacharMasAntiguos(0));

    }
    
    @Test
    void superavit_iguales() {
        
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 6, 3, 10, 7),
            new Traslado(2, 5, 3, 10, 6),
            new Traslado(3, 2, 3, 10, 5),
            new Traslado(4, 1, 3, 10, 4),
            new Traslado(5, 4, 3, 10, 3),
            new Traslado(6, 0, 3, 10, 2),
        };

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasRedituables(2);
        assertEquals(5, sis.ciudadConMayorSuperavit());

        sis.despacharMasRedituables(2);
        assertEquals(1, sis.ciudadConMayorSuperavit());

        sis.despacharMasRedituables(2);
        assertEquals(0, sis.ciudadConMayorSuperavit());
    }

}
