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

    // ASCII art du pendu
    private final String[] penduASCII = {
            "      _____",
            "     |     |",
            "     |     O",
            "     |    /|\\",
            "     |     |",
            "     |    / \\",
            "  ___|___",
    };

    public PenduView(PenduModel model) {
        this.model = model;
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200); // Taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        motLabel = new JLabel(model.getMotAffiche());
        topPanel.add(motLabel);
        add(topPanel, BorderLayout.NORTH);

        // Panel pour afficher le pendu
        penduLabel = new JLabel();
        add(penduLabel, BorderLayout.WEST);

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

        // Indique dans un panneau à droite les lettres déjà proposées
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

        setMinimumSize(new Dimension(300, 200));

        setVisible(true);
    }

    private void redimensionnerTexte() {
        // Calculer la taille du texte en fonction de la taille de la fenêtre
        int tailleTexte = getWidth() / 25;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, tailleTexte);
        motLabel.setFont(font);
        lettreField.setFont(font);
        tentativesLabel.setFont(font);
        lettresProposees.setFont(font);
    }

    public void update() {
        motLabel.setText(model.getMotAffiche());
        tentativesLabel.setText("Tentatives restantes : " + model.getTentativesRestantes());
        mettreAJourLettresProposees(model.getLettresProposees());
        afficherPendu();
        if (model.partieGagnee() || model.partiePerdue()) {
            if (retry() == JOptionPane.YES_OPTION) {
                this.dispose();
                PenduController.lancerPartie();
            } else {
                System.exit(0);
            }
        }
    }

    private void afficherPendu() {
        StringBuilder penduBuilder = new StringBuilder("<html>");
        int i = model.getTentativesRestantes();
        while (i < 0) {
            String ligne = penduASCII[i];
            penduBuilder.append(ligne).append("<br>");
            i++;
        }
        penduBuilder.append("</html>");
        penduLabel.setText(penduBuilder.toString());
    }

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

    public void validerLettre() {
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

    // Méthode pour mettre à jour les lettres déjà proposées
    public void mettreAJourLettresProposees(Set<Character> lettresProposees) {
        StringBuilder lettresBuilder = new StringBuilder();
        for (char lettre : lettresProposees) {
            lettresBuilder.append(lettre).append("\n");
        }
        this.lettresProposees.setText("Lettres déjà proposées :\n" + lettresBuilder.toString());
    }
}
