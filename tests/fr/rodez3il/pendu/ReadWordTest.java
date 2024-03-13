package fr.rodez3il.pendu;

import org.junit.Assert;
import org.junit.Test;

// Test class for the ReadWord class
public class ReadWordTest {

    // Test the word not being null
    @Test
    public void testReadWordNotNull() {
        ReadWord rw = new ReadWord();
        Assert.assertNotNull(rw.getMot());
    }

    // Test a word with a specific seed
    @Test
    public void testReadWordEasy() {
        ReadWord rw = new ReadWord(10389);
        Assert.assertEquals("AGILE", rw.getMot().toUpperCase());
    }

    // Test a def with a specific seed linking well to the word.
    @Test
    public void testReadWordDef() {
        ReadWord rw = new ReadWord(10389);
        Assert.assertEquals("processus de développement où tout est possible, sauf les délais", rw.getDef().toLowerCase());
    }
}
