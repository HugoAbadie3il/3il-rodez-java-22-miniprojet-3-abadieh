package fr.rodez3il.pendu;

public class PenduController {
    private PenduModel model;
    private PenduView view;

    public PenduController(PenduModel model, PenduView view) {
        this.model = model;
        this.view = view;
        this.view.update();
    }

    public static void lancerPartie(){
        PenduModel model = new PenduModel();
        PenduView view = new PenduView(model);
        new PenduController(model, view);
    }

    public static void main(String[] args) {
        lancerPartie();
    }
}
