# Documentación de Pruebas: `GestorJuegos`

Este documento describe las pruebas de caja blanca y caja negra diseñadas e implementadas para los métodos `registrarLoteJuegos` y `venderJuego` de la clase `GestorJuegos`. La información se ha extraído del diseño en `flujo.drawio` y de los casos de prueba implementados en los archivos `GestorJuegosTestBlanca.java` y `GestorJuegosTestNegra.java`.

## 1. Pruebas de Caja Blanca (`registrarLoteJuegos`)

El método `registrarLoteJuegos` fue analizado mediante pruebas de caja blanca. Para ello, se elaboró un grafo de flujo identificando los nodos y las decisiones clave del algoritmo.

### 1.1. Complejidad Ciclomática
La complejidad ciclomática $V(G)$ se calculó utilizando tres métodos:
- **Aristas - Nodos + 2**: $V(G) = 25 - 19 + 2 = 8$
- **Regiones del grafo**: $V(G) = 8$
- **Nodos predicado + 1**: $V(G) = 7 + 1 = 8$

Esto nos indica que existen 8 caminos independientes que deben ser cubiertos en las pruebas.

### 1.2. Caminos Independientes y Diseño de Casos de Prueba
A continuación se detallan los caminos identificados en el diseño (`flujo.drawio`) y el caso de prueba asociado implementado en JUnit (`GestorJuegosTestBlanca.java`):

| Camino | Secuencia de Nodos (resumen) | Descripción / Condición | Caso de Prueba (JUnit) |
|--------|------------------------------|-------------------------|-------------------------|
| **C1** | A->B->C->D->Z | Error por longitudes de arrays desiguales. Retorna `-1`. | `cp1()` |
| **C2** | A->B->C->E->D->Z | Error por lote de códigos vacío (`length == 0`). Retorna `-1`. | `cp2()` |
| **C3** | A->B->C->E->F->G->H->J->Z | Error por encontrar una cantidad negativa en el lote. Retorna `-2`. | `cp3()` |
| **C4** | A->B->C->E->F->G->K->L->Z | Error por superar el límite de stock máximo permitido. Retorna `-3`. | `cp4()` |
| **C5** | A->B->C->E->F->G->K->M->Q->Z | Lote vacío o procesado que no produce registros nuevos (ej. cantidad 0). Retorna `0`. | `cp5()` |
| **C6** | A->B->C->E->F->G->K->M->N->O->P->M->Q->Z | El código de juego no existe: se crea una nueva entrada en el mapa. | `cp6()` |
| **C7** | A->B->C->E->F->G->H->I->G->H->J->Z | Actualización de cantidades y control de flujos de error en medio del bucle. | `cp7()` |
| **C8** | A->B->C->E->F->G->K->M->N->Ñ->P->M->Q->Z | El código ya existe: se actualiza sumando las nuevas unidades al stock. Retorna registros. | `cp8()` |

*(Nota: Las secuencias de nodos corresponden a la representación visual en el archivo `flujo.drawio`. Los métodos de prueba aseguran la cobertura de los caminos según la complejidad definida).*


## 2. Pruebas de Caja Negra (`venderJuego`)

Para el método `venderJuego` se aplicó la técnica de diseño de pruebas de caja negra, analizando particiones de equivalencia, valores límite y conjetura de errores.

### 2.1. Condiciones Evaluadas
Se tuvieron en cuenta las siguientes restricciones funcionales indicadas en el requerimiento:
- **Cantidad:** No puede ser $\le 0$, y no puede ser mayor al stock actual del juego.
- **Código:** Debe tener exactamente 6 caracteres (3 primeras letras mayúsculas, 3 últimos dígitos).

### 2.2. Diseño de Casos de Prueba

El diseño de las pruebas funcionales se refleja en la siguiente tabla de casos y su correspondiente implementación en `GestorJuegosTestNegra.java`:

| Caso de Prueba | Clase / COD cubierto | Condiciones de entrada | Resultado esperado | Descripción en `GestorJuegosTestNegra.java` |
|----------------|----------------------|------------------------|--------------------|---------------------------------------------|
| **CP_V_01** | V1, V2, V3, V4 | Código: `"ABC123"`, Cant: `5` (Stock: 10) | Retorna `5` | `testVentaExitosa_CP_V_01()`: Venta exitosa (Escenario típico, datos correctos y stock suficiente). |
| **CP_NV_01** | NV1, V2, V3 | Código: `"AB12"`, Cant: `1` | Retorna `0` | `testCodigoInvalido_CP_NV_01()`: Código mal formado (no cumple el formato, lanza `IllegalArgumentException` y retorna 0). |
| **CP_NV_02** | V1, NV2, V3 | Código: `"NON999"`, Cant: `1` | Retorna `-1` | `testJuegoNoExiste_CP_NV_02()`: Formato de código válido pero el juego no está registrado en el mapa de stock. |
| **CP_NV_03** | V1, V2, V3, NV4 | Código: `"ABC123"`, Cant: `20` (Stock: 10) | Retorna `-2` | `testStockInsuficiente_CP_NV_03()`: Intento de vender más cantidad de la que hay en stock actualmente. |
| **CP_NV_04** | V1, V2, NV3 | Código: `"ABC123"`, Cant: `-5` | Retorna `0` | `testCantidadNegativa_CP_NV_04()`: Cantidad negativa inválida (lanza `IllegalArgumentException` y retorna 0). |

---

Tanto las pruebas de caja blanca como las de caja negra han sido correctamente documentadas e implementadas con JUnit, garantizando la cobertura y validación necesarias de ambos métodos principales de la clase `GestorJuegos`.