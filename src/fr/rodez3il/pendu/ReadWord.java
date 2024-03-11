package fr.rodez3il.pendu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe permettant de tirer un mot et sa définition au hasard dans un fichier texte (dans ce cas le fichiers est mots.txt)..
 */
public class ReadWord {
    private static final Path cheminSrc = Paths.get("mots.txt");

    private String mot;
    private String def;
    private long graine;
    private Random rand;

    /**
     * Constructeur avec une graine pour le générateur de nombres aléatoires.
     * @param graine la graine pour le générateur de nombres aléatoires
     */
    public ReadWord(long graine) {
        this.graine = graine;
        this.rand = new Random(graine);
        readWord();
    }

    /**
     * Constructeur sans graine pour le générateur de nombres aléatoires.
     */
    public ReadWord() {
        this.rand = new Random();
        readWord();
    }

    /**
     * Méthode pour lire un mot et sa définition au hasard dans un fichier texte.
     */
    private void readWord() {
        List<String> mots = new ArrayList<>();
        List<String> defs = new ArrayList<>();

        try (Scanner sc = new Scanner(cheminSrc)) {

            while (sc.hasNextLine()) {
                String ligne = sc.nextLine();
                String[] jetons = ligne.split(" ");
                String mot = jetons[0];
                int i = 1;
                String def = "";
                while (i < jetons.length) {
                    def += jetons[i] + " ";
                    i++;
                }
                mots.add(mot);
                defs.add(def.substring(0, def.length() - 1));
            }

            int index = rand.nextInt(mots.size());
            this.mot = mots.get(index);
            this.def = defs.get(index);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour obtenir le mot.
     * @return le mot
     */
    public String getMot() {
        return this.mot;
    }

    /**
     * Méthode pour obtenir la définition du mot.
     * @return la définition du mot
     */
    public String getDef() {
        return this.def;
    }
}
