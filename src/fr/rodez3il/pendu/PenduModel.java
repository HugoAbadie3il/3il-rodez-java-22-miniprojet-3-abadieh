package fr.rodez3il.pendu;

import java.util.HashSet;
import java.util.Set;

/**
 * Modèle du jeu du pendu
 */
public class PenduModel {
    private String motATrouver;
    private ReadWord rw;
    private StringBuilder motAffiche;
    private int tentativesRestantes;
    private Set<Character> lettresProposees;

    /**
     * Constructeur générant un mot aléatoire à trouver
     */
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

    /**
     * Vérifie si la lettre proposée est dans le mot à trouver et met à jour le mot affiché.
     * @param lettre la lettre envoyé par le joueur
     * @return true si la lettre est dans le mot, false sinon
     */
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

    /**
     * Ajoute la lettre proposée au set des lettres déjà proposées.
     * @param lettre la lettre envoyé par le joueur
     */
    private void ajoutLettreProposee(char lettre) {
        lettresProposees.add(lettre);
    }

    /**
     * Retourne la liste des lettres déjà proposées.
     * @return true si la lettre a déjà été proposée, false sinon
     */
    public Set<Character> getLettresProposees() {
        return lettresProposees;
    }

    /**
     * Vérification si la partie est gagnée.
     * @return true si la partie est gagnée, false sinon
     */
    public boolean partieGagnee() {
        return motAffiche.toString().equals(motATrouver);
    }

    /**
     * Vérification si la partie est perdue.
     * @return true si la partie est perdue, false sinon
     */
    public boolean partiePerdue() {
        return tentativesRestantes <= 0;
    }

    /**
     * Retourne le mot à afficher.
     * @return le mot à afficher
     */
    public String getMotAffiche() {
        return motAffiche.toString();
    }

    /**
     * Retourne le mot à trouver.
     * @return le mot à trouver
     */
    public String getMotATrouver() {
        return motATrouver;
    }

    /**
     * Retourne le nombre de tentatives restantes.
     * @return le nombre de tentatives restantes
     */
    public int getTentativesRestantes() {
        return tentativesRestantes;
    }
}
