/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.util.ArrayList;

/**
 *
 * @author valleta
 */
public class Jeu {

    private Env env;
    private Room room;
    private Profil profil;

    private ArrayList<Lettre> lettres;

    public Jeu() {
        this.lettres = new ArrayList<Lettre>();
        env = new Env();

        // Instancie une Room
        room = new Room();

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil();
    }

    public void execute() {
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue(new Partie());

        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }

    public void joue(Partie partie) {

        this.lettres.add(new Lettre('a', 10, 10));
        this.lettres.add(new Lettre('b', 20, 20));
        this.lettres.add(new Lettre('c', 30, 30));
        this.lettres.add(new Lettre( 60, 30));

        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);

        // Instancie un Tux
        Tux tux = new Tux(env, room);

        for (Lettre l : lettres) {
            env.addObject(l);
        }
        env.addObject(tux);

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

    }

    protected void demarrePartie(Partie partie) {

    }

    protected void appliqueRegles(Partie partie) {

    }

    protected void terminePartie(Partie partie) {

    }

}
