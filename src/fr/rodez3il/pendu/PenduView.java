package fr.rodez3il.pendu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

/**
 * Classe représentant la vue du jeu du Pendu.
 */
public class PenduView extends JFrame {
    private PenduModel model;
    private JLabel motLabel;
    private JLabel penduLabel;
    private JLabel tentativesLabel;
    private JTextField lettreField;
    private JTextArea lettresProposees;
    private JLabel difficulteLabel;

    // ASCII art du pendu
    private final String[] penduASCII = {
            "      _____",
            "     |      |",
            "     |     O",
            "     |     /|\\",
            "     |      |",
            "     |     / \\",
            " ___|___",
    };

    /**
     * Constructeur de la vue du jeu du Pendu.
     * @param model le modèle du jeu
     */
    public PenduView(PenduModel model) {
        this.model = model;
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500); // Taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setLayout(new BorderLayout());

        // Panel pour afficher le mot à trouver
        JPanel topPanel = new JPanel();
        motLabel = new JLabel(model.getMotAffiche());
        topPanel.add(motLabel);
        add(topPanel, BorderLayout.NORTH);

        // Panel difficulté
        JPanel difficultePanel = new JPanel();
        difficulteLabel = new JLabel();
        difficulteLabel.setText("Difficulté max, pas de timer");
        difficultePanel.add(difficulteLabel);
        add(difficultePanel, BorderLayout.CENTER);

        // Panel pour afficher le pendu
        JPanel penduPanel = new JPanel();
        penduLabel = new JLabel();
        afficherPendu();
        penduPanel.add(penduLabel);
        add(penduPanel, BorderLayout.WEST);

        // Panel pour entrer une lettre
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        lettreField = new JTextField(1);
        bottomPanel.add(lettreField);
        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validerLettre();
            }
        });
        bottomPanel.add(validerButton);
        tentativesLabel = new JLabel("Tentatives restantes : " + model.getTentativesRestantes());
        bottomPanel.add(tentativesLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Panneau pour afficher les lettres déjà proposées
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        lettresProposees = new JTextArea();
        lettresProposees.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(lettresProposees);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Écouteur de redimensionnement de la fenêtre
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionnerTexte();
            }
        });

        // Définition d'une taille minimale
        setMinimumSize(new Dimension(600,500));

        setVisible(true);
    }

    /**
     * Méthode pour redimensionner le texte en fonction de la taille de la fenêtre.
     */
    private void redimensionnerTexte() {
        // Calculer la taille du texte en fonction de la taille de la fenêtre
        int tailleTexte = getWidth() / 25;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, tailleTexte);
        motLabel.setFont(font);
        lettreField.setFont(font);
        tentativesLabel.setFont(font);
        lettresProposees.setFont(font);
    }

    /**
     * Méthode pour mettre à jour la vue.
     */
    public void update() {
        // Mise à jour des éléments de la vue
        motLabel.setText(model.getMotAffiche());
        tentativesLabel.setText("Tentatives restantes : " + model.getTentativesRestantes());
        mettreAJourLettresProposees(model.getLettresProposees());
        afficherPendu();

        // Gestion de la fin de partie
        if (model.partieGagnee() || model.partiePerdue()) {
            if (retry() == JOptionPane.YES_OPTION) {
                this.dispose();
                PenduController.lancerPartie();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Méthode pour afficher le pendu ligne par ligne.
     */
    private void afficherPendu() {
        StringBuilder penduBuilder = new StringBuilder("<html>");
        int i = 0;
        while (i < 7 - model.getTentativesRestantes()) {
            String ligne = penduASCII[i];
            penduBuilder.append(ligne).append("<br>");
            i++;
        }
        penduBuilder.append("</html>");
        // Remplacer les espaces par des &nbsp; pour que le texte soit bien affiché
        penduLabel.setText(penduBuilder.toString().replace(" ", "&nbsp;"));
    }

    /**
     * Méthode pour demander à l'utilisateur s'il veut rejouer par l'utilisation d'une boîte de dialogue.
     * @return la réponse de l'utilisateur
     * @throws IllegalStateException si la partie n'est pas terminée
     */
    private int retry() throws IllegalStateException{
        JOptionPane fenetreRetry = new JOptionPane();
        String message;
        if (model.partieGagnee()) {
            message = "Félicitations, vous avez gagné !";
        } else if (model.partiePerdue()){
            message = "Désolé, vous avez perdu. Le mot était : " + model.getMotATrouver() + ".";
        }
        else {
            throw new IllegalStateException("La partie n'est pas terminée.");
        }
        message += "\nVoulez-vous rejouer ?";

        return fenetreRetry.showConfirmDialog(this, message, "Fin de partie", JOptionPane.YES_NO_OPTION);

    }

    /**
     * Méthode pour valider une lettre proposée par l'utilisateur.
     */
    public void validerLettre() {
        // Récupérer la lettre proposée par l'utilisateur, mise en majuscule pour gérer la case.
        String lettre = lettreField.getText().toUpperCase();
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            boolean trouve = model.estLettreDansMot(lettre.charAt(0));

            if (!trouve) {
                JOptionPane.showMessageDialog(this, "La lettre n'est pas dans le mot !");
            }
            update();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une seule lettre !");
        }
        lettreField.setText("");
    }

    /**
     * Méthode pour mettre à jour les lettres déjà proposées.
     * @param lettresProposees l'ensemble des lettres déjà proposées
     */
    public void mettreAJourLettresProposees(Set<Character> lettresProposees) {
        StringBuilder lettresBuilder = new StringBuilder();
        for (char lettre : lettresProposees) {
            lettresBuilder.append(lettre).append("\n");
        }
        this.lettresProposees.setText("Lettres déjà proposées :\n" + lettresBuilder.toString());
    }
}
