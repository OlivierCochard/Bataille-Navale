package game;

// Imports
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class VueBatailleNavale extends JFrame implements ActionListener {

  // Initialisation des variables utilisées dans le code
  private Plateau plateauJoueur;
  private Plateau plateauAdversaire;
  private JPanel panelAdversaire;
  private JPanel panelJoueur;
  private JButton[][] boutonsAdversaire;
  private JButton[][] boutonsJoueur;
  private JLabel message;
  private boolean tourJoueur;
  private Bateau bateau;

  // Dimensions du plateau de jeu (par défaut = 10)
  private int DIMENSIONS = 10;

  /**
  Constructeur de la classe VueBatailleNavale.
  @param plateauJoueur le plateau de jeu du joueur
  @param plateauAdversaire le plateau de jeu de l'adversaire
  */
  public VueBatailleNavale(Plateau plateauJoueur, Plateau plateauAdversaire) {
    super("BATAILLE NAVALE");

    this.plateauJoueur = plateauJoueur;
    this.plateauAdversaire = plateauAdversaire;
    this.panelAdversaire = new JPanel(new GridLayout(DIMENSIONS, DIMENSIONS));
    this.panelJoueur = new JPanel(new GridLayout(DIMENSIONS, DIMENSIONS));
    this.boutonsAdversaire = new JButton[DIMENSIONS][DIMENSIONS];
    this.boutonsJoueur = new JButton[DIMENSIONS][DIMENSIONS];
    this.message = new JLabel("");
    this.tourJoueur = true;

    // Initialisation des grilles de jeu
    int[][] grilleBateauxJoueur = plateauJoueur.GetGrilleBateaux();
    int[][] grilleTirsJoueur = plateauJoueur.GetGrilleTirs().GetGrille();
    int[][] grilleTirsAdversaire = plateauAdversaire.GetGrilleTirs().GetGrille();

    // Création des boutons pour le plateau joueur
    for (int i = 0; i < DIMENSIONS; i++) {
      for (int j = 0; j < DIMENSIONS; j++) {
        JButton bouton = new JButton();
        bouton.setPreferredSize(new Dimension(30, 30));
        bouton.setActionCommand(i + " " + j + " joueur");
        bouton.addActionListener(this);
        boutonsJoueur[i][j] = bouton;
        panelJoueur.add(bouton);
      }
    }

    // Création des boutons pour le plateau adversaire
    for (int i = 0; i < DIMENSIONS; i++) {
      for (int j = 0; j < DIMENSIONS; j++) {
        JButton bouton = new JButton();
        bouton.setPreferredSize(new Dimension(30, 30));
        bouton.setActionCommand(i + " " + j + " adversaire");
        bouton.addActionListener(this);
        boutonsAdversaire[i][j] = bouton;
        boutonsAdversaire[i][j].setBackground(Color.BLUE);
        panelAdversaire.add(boutonsAdversaire[i][j]);
      }
    }

    // Affichage des bateaux du joueur
    for (Bateau bateau : plateauJoueur.GetListeBateaux()) {
      int x = bateau.GetPosX();
      int y = bateau.GetPosY();
      int orientation = bateau.GetOrientation();
      int taille = bateau.GetTaille();
      for (int i = 0; i < taille; i++) {
        if (orientation == 0) {
          grilleBateauxJoueur[x + i][y] = bateau.GetValeur();
        }
        else {
          grilleBateauxJoueur[x][y + i] = bateau.GetValeur();
        }
      }
    }

    // Affichage de la grille de tirs du joueur
    for (int i = 0; i < DIMENSIONS; i++) {
      for (int j = 0; j < DIMENSIONS; j++) {
        boutonsJoueur[i][j].setBackground(Color.BLUE);
        boutonsJoueur[i][j].setEnabled(false);
      }
    }

    // Création de la fenêtre de jeu
    JPanel panelPrincipal = new JPanel(new GridLayout(1,2));
    panelJoueur.setBorder(new EmptyBorder(10,10,10,10));
    panelAdversaire.setBorder(new EmptyBorder(10,10,10,10));
    panelPrincipal.add(panelJoueur);
    panelPrincipal.add(panelAdversaire);
    JPanel panelMessage = new JPanel();
    panelMessage.add(message);
    this.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    this.getContentPane().add(panelMessage, BorderLayout.SOUTH);
    this.setSize(800, 400);
    this.setVisible(true);
  }

  // Action éxécutée à chaque clic sur bouton
  public void actionPerformed(ActionEvent e) {
    String[] action = e.getActionCommand().split(" ");
    int x = Integer.parseInt(action[0]);
    int y = Integer.parseInt(action[1]);
    if (action[2].equals("adversaire")) {
      if (!plateauAdversaire.Tirer(x, y)) {
        message.setText("Vous avez déjà tiré ici !");
        return;
      }
      int resultatTir = plateauAdversaire.GetValeurGrilleBateaux(x, y);
      // Teste si un bateau est touché ou non
      if (resultatTir == 0) {
        boutonsAdversaire[x][y].setBackground(Color.GRAY);
        message.setText("Raté !");
      }
      else {
        boutonsAdversaire[x][y].setBackground(Color.RED);
        message.setText("Touché !");
        if (plateauAdversaire.EstCoule(x, y)) {
          message.setText("Coulé !");
          afficheCoule(x, y, plateauAdversaire, boutonsAdversaire);
        }
      }
      boutonsAdversaire[x][y].setEnabled(false);
      // Vérifie si tous les bateaux sont coulés ou non
      if (plateauAdversaire.EstFini()) {
        message.setText("Vous avez gagné !");
        desactiverBoutons();
      }
      else {
        tourJoueur = false;
        jouerCoupAdversaire();
      }
    }
  }

  // Méthode pour faire jouer un coup à l'adversaire
  public void jouerCoupAdversaire() {
    Random rand = new Random();
    int x = rand.nextInt(DIMENSIONS);
    int y = rand.nextInt(DIMENSIONS);
    while (!plateauJoueur.Tirer(x, y)) {
      x = rand.nextInt(DIMENSIONS);
      y = rand.nextInt(DIMENSIONS);
    }
    int resultatTir = plateauJoueur.GetValeurGrilleBateaux(x, y);
    if (resultatTir == 0) {
      boutonsJoueur[x][y].setBackground(Color.GRAY);
    }
    else {
      boutonsJoueur[x][y].setBackground(Color.RED);
      if (plateauJoueur.EstCoule(x, y)) {
        afficheCoule(x, y, plateauJoueur, boutonsJoueur);
      }
    }
    boutonsJoueur[x][y].setEnabled(false);
    if (plateauJoueur.EstFini()) {
      message.setText("L'adversaire a gagné !");
      desactiverBoutons();
    }
    tourJoueur = true;
  }

  // Méthode pour désactiver tous les boutons
  public void desactiverBoutons() {
    for (int i = 0; i < DIMENSIONS; i++) {
      for (int j = 0; j < DIMENSIONS; j++) {
        boutonsAdversaire[i][j].setEnabled(false);
        boutonsJoueur[i][j].setEnabled(false);
      }
    }
  }

  /**
  Lorsqu'un bateau est coulé celui-ci s'affiche en noir sur l'interface.
  @param x l'abscisse de la case choisie
  @param y l'ordonnée de la case choisie
  @param plateau le plateau de jeu du joueur ou de l'adversaire
  @param tabButton le tableau de boutons du plateau entré en paramètre
  */
  public void afficheCoule(int x, int y, Plateau plateau, JButton[][] tabButton) {
    for (Bateau b : plateau.GetListeBateaux()) {
      if (plateau.GetValeurGrilleBateaux(x, y) == b.GetValeur()) {
        bateau = b;
        break;
      }
    }
    for (int i = 0; i < DIMENSIONS; i++) {
      for (int j = 0; j < DIMENSIONS; j++) {
        if (plateau.GetValeurGrilleBateaux(i, j) == bateau.GetValeur()) {
          tabButton[i][j].setBackground(Color.BLACK);
        }
      }
    }
  }
}
