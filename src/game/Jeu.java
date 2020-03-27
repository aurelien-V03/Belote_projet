/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;
import test.TestDico;

/**
 *
 * @author valleta
 */
public abstract class Jeu {

    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;    
    public ArrayList<Lettre> lettres;

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
        profil = new Profil("");
    }

    public void execute() {
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue(new Partie("","",1));

        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }

    public void joue(Partie partie) {
        System.out.println("Chargement du dico : ");
           Dico dictionnaire = new Dico("");
        try {
            dictionnaire.lireDictionnaireDOM("src/game/", "dictionnaire.xml");
        } catch (SAXException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        String mot = dictionnaire.getMotDepuisListeNiveaux(1);
        System.out.println("Mot a chercher = " + mot);
        char[] mot_caracteres = mot.toCharArray();
        int z = 10;
        int y = 10;
        for(char c :mot_caracteres)
        {
            this.lettres.add(new Lettre(c,z,y));
            z += 10;
            y += 10;
        }
       

        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);

        // Instancie un Tux
        this.tux = new Tux(env, room);

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

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
    
    
    // Attention prendre le scale en compte
    public boolean collision(Lettre letter)
    {
        if(distance(letter) > 0)
        {
            return false;
        }
        else
            return true;
    }
    
    public double distance(Lettre letter)
    {
        double x_tux = this.tux.getX();
        double y_tux = this.tux.getY();
        
        double x_letter = letter.getX();
        double y_letter = letter.getY();
        
        double distance_x =  Math.pow(x_letter - x_tux, 2);
        double distance_y =  Math.pow(y_letter - y_tux, 2);
        double distance = Math.sqrt(distance_x + distance_y);
        
         return distance;
    }

}
