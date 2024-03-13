package fr.rodez3il.pendu;

/**
 * Classe contrôlleur du modèle MVC permettant de gérer le jeu du pendu.
 */
public class PenduController {
    private PenduModel model;
    private PenduView view;

    /**
     * Constructeur du contrôlleur.
     * @param model le modèle
     * @param view la vue
     */
    public PenduController(PenduModel model, PenduView view) {
        this.model = model;
        this.view = view;
        this.view.update();
    }

    /**
     * Méthode pour lancer une partie.
     */
    public static void lancerPartie(){
        PenduModel model = new PenduModel();
        PenduView view = new PenduView(model);
        new PenduController(model, view);
    }

    public static void main(String[] args) {
        lancerPartie();
    }
}
