/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;
import test.TestDico;

public abstract class Jeu2 {

    // Une énumération pour définir les choix de l'utilisateur
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    public ArrayList<Lettre> lettres;
    public Dico dictionnaire;

    private final Room menuRoom;
    EnvText textNomJoueur;
    EnvText textMenuQuestion;

    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    EnvText textMenuJeu4;

    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;

    public Jeu2() {
        this.lettres = new ArrayList<Lettre>();
        env = new Env();

        // Instancie une Room
        room = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil("src/XML/default.xml");

        // Dictionnaire
        System.out.println("Chargement du dico : ");
        dictionnaire = new Dico("");
        try {
            dictionnaire.lireDictionnaireDOM("src/game/", "dictionnaire.xml");
        } catch (SAXException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Textes affichés à l'écran
        textMenuQuestion = new EnvText(env, "Voulez vous ?", 200, 300);
        textMenuJeu1 = new EnvText(env, "1. Commencer une nouvelle partie ?", 250, 280);
        textMenuJeu2 = new EnvText(env, "2. Charger une partie existante ?", 250, 260);
        textMenuJeu3 = new EnvText(env, "3. Sortir de ce jeu ?", 250, 240);
        textMenuJeu4 = new EnvText(env, "4. Quitter le jeu ?", 250, 220);
        textNomJoueur = new EnvText(env, "Choisissez un nom de joueur : ", 200, 300);
        textMenuPrincipal1 = new EnvText(env, "1. Charger un profil de joueur existant ?", 250, 280);
        textMenuPrincipal2 = new EnvText(env, "2. Créer un nouveau joueur ?", 250, 260);
        textMenuPrincipal3 = new EnvText(env, "3. Sortir du jeu ?", 250, 240);
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    /**
     * Teste si une code clavier correspond bien à une lettre
     *
     * @return true si le code est une lettre
     */
    private Boolean isLetter(int codeKey) {
        return codeKey == Keyboard.KEY_A || codeKey == Keyboard.KEY_B || codeKey == Keyboard.KEY_C || codeKey == Keyboard.KEY_D || codeKey == Keyboard.KEY_E
                || codeKey == Keyboard.KEY_F || codeKey == Keyboard.KEY_G || codeKey == Keyboard.KEY_H || codeKey == Keyboard.KEY_I || codeKey == Keyboard.KEY_J
                || codeKey == Keyboard.KEY_K || codeKey == Keyboard.KEY_L || codeKey == Keyboard.KEY_M || codeKey == Keyboard.KEY_N || codeKey == Keyboard.KEY_O
                || codeKey == Keyboard.KEY_P || codeKey == Keyboard.KEY_Q || codeKey == Keyboard.KEY_R || codeKey == Keyboard.KEY_S || codeKey == Keyboard.KEY_T
                || codeKey == Keyboard.KEY_U || codeKey == Keyboard.KEY_V || codeKey == Keyboard.KEY_W || codeKey == Keyboard.KEY_X || codeKey == Keyboard.KEY_Y
                || codeKey == Keyboard.KEY_Z;
    }

    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getLetter(int letterKeyCode) {
        Boolean shift = false;
        if (this.env.getKeyDown(Keyboard.KEY_LSHIFT) || this.env.getKeyDown(Keyboard.KEY_RSHIFT)) {
            shift = true;
        }
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_SUBTRACT || letterKeyCode == Keyboard.KEY_MINUS)) {
            letterStr = "-";
        } else {
            letterStr = Keyboard.getKeyName(letterKeyCode);
        }
        if (shift) {
            return letterStr.toUpperCase();
        }
        return letterStr.toLowerCase();
    }

    /**
     * Permet de saisir le nom d'un joueur et de l'affiche à l'écran durant la
     * saisie
     *
     * @return le nom du joueur au format String
     */
    private String getNomJoueur() {
        textNomJoueur.modifyTextAndDisplay("Choisissez un nom de joueur : ");

        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modifyTextAndDisplay("Choisissez un nom de joueur : " + nomJoueur);
            }
        }
        textNomJoueur.destroy();
        return nomJoueur;
    }

    /**
     * Menu principal
     *
     * @return le choix du joueur
     */
    private MENU_VAL menuPrincipal() {
        System.out.println("menu principal");
        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu principal
        textMenuQuestion.display();
        textMenuPrincipal1.display();
        textMenuPrincipal2.display();
        textMenuPrincipal3.display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
            System.out.println("touche = " + touche);

        }

        // efface le menu
        textMenuQuestion.clean();
        textMenuPrincipal1.clean();
        textMenuPrincipal2.clean();
        textMenuPrincipal3.clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                System.out.println("Nom du joueur choisi : " + nomJoueur);
                // charge le profil de ce joueur si possible
                if (profil.charge(nomJoueur)) {
                    // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                    System.out.println("Nom du joueur VALIDE : " + nomJoueur + "PROFIL = " + profil);
                    choix = menuJeu();

                } else {
                    // sinon continue (et boucle dans ce menu)
                    System.out.println("Nom du joueur NON VALIDE : " + nomJoueur);
                    choix = MENU_VAL.MENU_CONTINUE;

                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur);
                // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                // le choix est de sortir du jeu (quitter l'application)
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    /**
     * Menu de jeu
     *
     * @return le choix du joueur une fois la partie terminée
     */
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);

            // affiche le menu de jeu
            textMenuQuestion.display();
            textMenuJeu1.display();
            textMenuJeu2.display();
            textMenuJeu3.display();
            textMenuJeu4.display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // efface le menu
            textMenuQuestion.clean();
            textMenuJeu1.clean();
            textMenuJeu2.clean();
            textMenuJeu3.clean();
            textMenuJeu4.clean();

            // restaure la room du jeu
            env.setRoom(room);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico

                    int toucheNiveau = this.getNiveauChoix();
                    String mot = dictionnaire.getMotDepuisListeNiveaux(toucheNiveau);

                    System.out.println("Mot a chercher = " + mot);
                    char[] mot_caracteres = mot.toCharArray();
                    int z = 10;
                    int y = 10;
                    for (char c : mot_caracteres) {
                        this.lettres.add(new Lettre(c, z, y));
                        z += 10;
                        y += 10;
                    }

                    // .......... dico ...........
                    // crée un nouvelle partie
                    System.out.println("KEY 1");

                    partie = new Partie(java.time.LocalDate.now().toString(), mot, 1);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil .........
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante

                    // demander de fournir une date    (format jj/mm/aaaa)
                    String regex = "^(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])-[0-9]{4}$";
                    //Creating a pattern object
                    Pattern pattern = Pattern.compile(regex);
                    //Matching the compiled pattern in the String
                    Matcher matcher;
                    boolean bool = false;

                    this.textMenuPrincipal1.modifyTextAndDisplay("Date de votre partie : (aaaa/mm/jj)");

                    // recupere la date que l'utilisateur veut
                    String date = "";
                    date = this.textMenuPrincipal1.lire(true);
                       

                    this.textMenuPrincipal1.clean();

                    // tenter de trouver une partie à cette date
                    Partie partieJouable = null;
                    boolean partieJouableFound = false;
                    
                    for(Partie p : this.profil.getParties())
                    {
                        try{
                            SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
                            Date datePartie = dateformat.parse(p.getDate());
                            Date dateSouhaitee = dateformat.parse(date);
                            // partie et date demandee MEME jour
                            if(datePartie.compareTo(dateSouhaitee) == 0)
                            {
                              System.out.println("DATE  JOUR = " + datePartie.getTime() + " --- "  + dateSouhaitee.getTime());
                               System.out.println("\t\tPartie = " + p);
                                partieJouable = p;
                                partieJouableFound = true;
                            }
                            // jour different
                            else
                            {
                                 System.out.println("DATE DIFF JOUR = " + datePartie.getTime() + " --- "  + dateSouhaitee.getTime());

                            }                                                
                        }catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }

                    }
              
                    // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                    if(partieJouableFound)
                    {
                        
                    }
                    else
                    {
                        
                    }
                    
                    
                    
                    // joue
                    joue(partieJouable);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil ........
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    // permet au joueur de saisir un niveau entre 1 et 5 
    private int getNiveauChoix() {
        int toucheNiveau = 0;
        textMenuJeu1.modifyTextAndDisplay("Choix du niveau (1-5) : ");
        toucheNiveau = 0;
        while (toucheNiveau < 1 || toucheNiveau > 5) {
            toucheNiveau = env.getKey();
            toucheNiveau -= 1;
            env.advanceOneFrame();
            System.out.println("touche niveau = " + toucheNiveau);
        }

        textMenuJeu1.clean();
        return toucheNiveau;
    }

    public void joue(Partie partie) {

        // affiche 5 secondes la lettre a l'ecran 
        Chronometre c = new Chronometre(5000);
        this.textMenuPrincipal1.modifyTextAndDisplay(partie.getMot());
        this.textMenuPrincipal2.modifyTextAndDisplay((5 - c.timeSpent()) + " seonces restantes");
        while (c.remainsTime()) {
            env.advanceOneFrame();
            this.textMenuPrincipal2.modifyTextAndDisplay((5 - c.timeSpent()) + " seonces restantes");

        }
        textMenuPrincipal1.clean();
        textMenuPrincipal2.clean();
        // Instancie un Tux
        this.tux = new Tux(env, room);
        env.addObject(tux);

        for (Lettre l : lettres) {
            env.addObject(l);
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
        tux.deplace();
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }

            tux.deplace();

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            // ... (sera complété plus tard) ...
            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
        System.out.println("Partie = " + partie);

    }

// Permet de verifier si la lettre 'letter' entre en collision avec le personnage 'Tux'
    public boolean collision(Lettre letter) {
        if (distance(letter) > 2) {
            return false;
        } else {
            return true;
        }
    }

    // calcul la distance entre 'Tux' et 'Letter'
    public double distance(Lettre letter) {
        double x_tux = this.tux.getZ();
        double y_tux = this.tux.getX();

        double x_letter = letter.getZ();
        double y_letter = letter.getX();

        double distance_x = Math.pow(x_letter - x_tux, 2);
        double distance_y = Math.pow(y_letter - y_tux, 2);
        double distance = Math.sqrt(distance_x + distance_y);

        return distance;
    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
}
