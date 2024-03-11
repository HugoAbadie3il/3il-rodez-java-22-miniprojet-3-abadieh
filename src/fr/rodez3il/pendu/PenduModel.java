package fr.rodez3il.pendu;

public class PenduModel {
    private String motATrouver;
    private StringBuilder motAffiche;
    private int tentativesRestantes;

    public PenduModel(String mot) {
        this.motATrouver = mot.toUpperCase();
        this.motAffiche = new StringBuilder("_".repeat(mot.length()));
        this.tentativesRestantes = 7;
    }

    public boolean estLettreDansMot(char lettre) {
        boolean trouve = false;
        for (int i = 0; i < motATrouver.length(); i++) {
            if (motATrouver.charAt(i) == lettre) {
                motAffiche.setCharAt(i, lettre);
                trouve = true;
            }
        }
        if (!trouve) {
            tentativesRestantes--;
        }
        return trouve;
    }

    public boolean partieGagnee() {
        return motAffiche.toString().equals(motATrouver);
    }

    public boolean partiePerdue() {
        return tentativesRestantes <= 0;
    }

    public String getMotAffiche() {
        return motAffiche.toString();
    }

    public String getMotATrouver() {
        return motATrouver;
    }

    public int getTentativesRestantes() {
        return tentativesRestantes;
    }
}
