package fr.rodez3il.pendu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

/**
 * Classe représentant la vue du jeu du Pendu.
 */
public class PenduView extends JFrame {
    private JLabel motLabel;
    private JLabel tentativesLabel;
    private JTextField lettreField;
    private JTextArea lettresProposees;

    public PenduView(String motAffiche, int tentativesRestantes, Set<Character> lettresActuelles) {
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200); // Taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        motLabel = new JLabel(motAffiche);
        topPanel.add(motLabel);
        add(topPanel, BorderLayout.NORTH);

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
        tentativesLabel = new JLabel("Tentatives restantes : " + tentativesRestantes);
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
        if (model.partieGagnee()) {
            JOptionPane.showMessageDialog(this, "Félicitations, vous avez gagné !");
            System.exit(0);
        } else if (model.partiePerdue()) {
            JOptionPane.showMessageDialog(this, "Désolé, vous avez perdu. Le mot était : " + model.getMotATrouver());
            System.exit(0);
        }
    }

    public void validerLettre(boolean trouve) {
        String lettre = lettreField.getText().toUpperCase();
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            // boolean trouve = model.estLettreDansMot(lettre.charAt(0));
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
