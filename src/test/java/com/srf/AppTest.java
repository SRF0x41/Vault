package com.srf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    //@Test
    public void testFileAnalyser(){
        FileAnalyzer fa = new FileAnalyzer();

        // Test bytes
        assertEquals("50 B", fa.convertBytes(50));

        // Test kilobytes (converted)
        assertEquals("5.02 KB", fa.convertBytes(5142));

        // Test megabytes (converted)
        assertEquals("5.02 MB", fa.convertBytes(5255000));

        // Test gigabytes (converted)
        assertEquals("5.02 GB", fa.convertBytes(5375000000L));

        // Test zero bytes
        assertEquals("0 B", fa.convertBytes(0));

        // Test edge case for KB boundary
        assertEquals("1 KB", fa.convertBytes(1024));

        // Test edge case for MB boundary
        assertEquals("1 MB", fa.convertBytes(1048576));

        // Test edge case for GB boundary
        assertEquals("1 GB", fa.convertBytes(1073741824L));

        // Test large file size
        assertEquals("105.27 GB", fa.convertBytes(112589990684262L));
    }
}
