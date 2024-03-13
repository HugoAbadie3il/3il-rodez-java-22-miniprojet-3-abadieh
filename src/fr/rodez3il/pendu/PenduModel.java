package fr.rodez3il.pendu;

import java.util.HashSet;
import java.util.Set;

public class PenduModel {
    private String motATrouver;
    private ReadWord rw;
    private StringBuilder motAffiche;
    private int tentativesRestantes;
    private Set<Character> lettresProposees;

    public PenduModel() {
        this.rw = new ReadWord();
        this.motATrouver = rw.getMot().toUpperCase();
        this.motAffiche = new StringBuilder();
        this.lettresProposees = new HashSet<>();
        for (int i = 0; i < motATrouver.length(); i++) {
            char c = motATrouver.charAt(i);
            if (Character.isLetter(c)) {
                motAffiche.append("_");
            } else {
                motAffiche.append(c);
            }
        }
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
        ajoutLettreProposee(lettre);
        return trouve;
    }

    private void ajoutLettreProposee(char lettre) {
        lettresProposees.add(lettre);
    }

    public Set<Character> getLettresProposees() {
        return lettresProposees;
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
