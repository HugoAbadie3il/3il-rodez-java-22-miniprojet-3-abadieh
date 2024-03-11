package fr.rodez3il.pendu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ReadWord {
    private static final Path cheminSrc = Paths.get("mots.txt");

    private String mot;
    private String def;
    private long graine;
    private Random rand;

    public ReadWord(long graine) {
        this.graine = graine;
        this.rand = new Random(graine);
        readWord();
    }

    public ReadWord() {
        this.rand = new Random();
        readWord();
    }

    public void readWord() {
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

    public String getMot() {
        return this.mot;
    }

    public String getDef() {
        return this.def;
    }
}
