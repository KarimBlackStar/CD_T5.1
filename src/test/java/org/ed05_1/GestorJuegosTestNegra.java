package org.ed05_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorJuegosTestNegra {
    private GestorJuegos tienda;

    @BeforeEach
    public void setUp() {
        tienda = new GestorJuegos();
        // Preparamos un escenario con stock para las pruebas válidas e insuficientes
        tienda.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});
    }

    @Test
    public void testVentaExitosa_CP_V_01() {
        // CP_V_01: Datos correctos y stock suficiente
        assertEquals(5, tienda.venderJuego("ABC123", 5));
    }

    @Test
    public void testCodigoInvalido_CP_NV_01() {
        // CP_NV_01: El código no cumple el formato (salta catch y devuelve 0)
        assertEquals(0, tienda.venderJuego("AB12", 1));
    }

    @Test
    public void testJuegoNoExiste_CP_NV_02() {
        // CP_NV_02: El formato de código es válido pero no existe en la tienda
        assertEquals(-1, tienda.venderJuego("NON999", 1));
    }

    @Test
    public void testStockInsuficiente_CP_NV_03() {
        // CP_NV_03: Intentamos vender 20 unidades cuando solo registramos 10 en el setUp
        assertEquals(-2, tienda.venderJuego("ABC123", 20));
    }

    @Test
    public void testCantidadNegativa_CP_NV_04() {
        // CP_NV_04: Cantidad inválida (salta catch y devuelve 0)
        assertEquals(0, tienda.venderJuego("ABC123", -5));
    }
}