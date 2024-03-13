package fr.rodez3il.pendu;

import org.junit.Assert;
import org.junit.Test;

// Test class for the model
public class ModelTest {

    // Test a letter that is not in the word
    @Test
    public void ModelWrongLetter() {
        PenduModel pm = new PenduModel();
        char c = 'a';
        while (pm.getMotATrouver().contains(c + "")) {
            c++;
        }
        Assert.assertFalse(pm.estLettreDansMot(c));
    }

    // Test a letter that is in the word
    @Test
    public void ModelRightLetter() {
        PenduModel pm = new PenduModel();
        String expected = pm.getMotATrouver();
        Assert.assertTrue(pm.estLettreDansMot(expected.charAt(0)));
    }

    // Test the win condition
    @Test
    public void ModelWin() {
        PenduModel pm = new PenduModel();
        String expected = pm.getMotATrouver();
        for (int i = 0; i < expected.length(); i++) {
            pm.estLettreDansMot(expected.charAt(i));
        }
        Assert.assertTrue(pm.partieGagnee());
    }

    // Test the lose condition
    @Test
    public void ModelLose() {
        PenduModel pm = new PenduModel();
        while (pm.getTentativesRestantes() > 0) {
            // Z n'est dans aucun mot
            pm.estLettreDansMot('z');
        }
        Assert.assertTrue(pm.partiePerdue());
    }

    // Test the game not being finished
    @Test
    public void ModelNotFinished() {
        PenduModel pm = new PenduModel();
        Assert.assertFalse(pm.partieGagnee() || pm.partiePerdue());
    }
}
