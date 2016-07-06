package sic.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilidadesTest {

    @Test
    public void shouldEncriptarConMD5() {
        String md51 = Utilidades.encriptarConMD5("Test");
        String md52 = Utilidades.encriptarConMD5("Test");
        String md53 = Utilidades.encriptarConMD5("TesT");
        assertEquals(md52, md51);
        assertNotEquals(md51, md53);
    }

}
