package org.ed05_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorJuegosTestBlanca {
    private GestorJuegos tienda;

    @BeforeEach
    public void setUp() {
        tienda = new GestorJuegos();
    }

    @Test
    public void cp1() {
        String[] codigos = {"ABC123", "XYZ456"};
        int[] unidades = {10};
        assertEquals(-1, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp2() {
        String[] codigos = {};
        int[] unidades = {};
        assertEquals(-1, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp3() {
        String[] codigos = {"ABC123"};
        int[] unidades = {-5};
        assertEquals(-2, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp4() {
        tienda.registrarLoteJuegos(new String[]{"OLD001"}, new int[]{195});
        String[] codigos = {"NEW002"};
        int[] unidades = {10};
        assertEquals(-3, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp5() {
        // C5: Registramos 10 primero
        tienda.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});

        // Registramos 0 unidades: debe retornar 0
        String[] codigos = {"ABC123"};
        int[] unidades = {0};
        assertEquals(0, tienda.registrarLoteJuegos(codigos, unidades));

        // Verificamos de forma indirecta: si registramos 5 más y devuelve 5,
        // es que el stock seguía en 10 y no ha habido errores raros.
        assertEquals(5, tienda.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{5}));
    }

    @Test
    public void cp6() {
        String[] codigos = {"ABC123"};
        int[] unidades = {10};
        assertEquals(10, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp7() {
        tienda.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});
        String[] codigos = {"ABC123"};
        int[] unidades = {5};
        assertEquals(5, tienda.registrarLoteJuegos(codigos, unidades));
    }

    @Test
    public void cp8() {
        tienda.registrarLoteJuegos(new String[]{"AAA111"}, new int[]{10});
        String[] codigos = {"AAA111", "BBB222"};
        int[] unidades = {5, 5};
        assertEquals(10, tienda.registrarLoteJuegos(codigos, unidades));
    }
}