package fr.rodez3il.pendu;

public class PenduController {
    private PenduModel model;
    private PenduView view;

    public PenduController(PenduModel model, PenduView view) {
        this.model = model;
        this.view = view;
        this.view.update();
    }

    public static void main(String[] args) {
        ReadWord rw = new ReadWord(141);
        PenduModel model = new PenduModel(rw.getMot());
        PenduView view = new PenduView(model);
        new PenduController(model, view);
    }
}
