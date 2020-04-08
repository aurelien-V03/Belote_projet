package game;

import java.util.Random;

/**
 *
 * @author Aurel
 */
public class JeuDevineLeMotOrdre extends Jeu {

    // nombre de lettres restantes a trouver dans le mot
    private int nbLettresRestantes;
    // timer de la partie
    private Chronometre chrono;
    public JeuDevineLeMotOrdre() {
        super();
    }

    // Lancement du chronometre + instanciation des lettres de l'environnement
    @Override
    protected void demarrePartie(Partie partie) {
        System.out.println("DEMARRE PARTIE");
        
        // le joueur a 30 secondes pour trouver le mot
        this.chrono = new Chronometre(30000);

        
        char[] mot_caracteres = partie.getMot().toCharArray();
        // genere 1 coordonne [X = [0;100] Y = [0;100] ] sur ces interval
        Random r = new Random();
        int z;
        int y;
        for (char c : mot_caracteres) {
            z = r.nextInt(101);
            y = r.nextInt(101);
            this.lettres.add(new Lettre(c, z, y));
        }
        this.nbLettresRestantes = super.lettres.size();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
         super.timeInGame.modifyTextAndDisplay("Secondes restantes : " + (this.chrono.getSeconds() -  this.chrono.timeSpent()) );
        // Le temps est ecoule
        if (chrono.remainsTime() == false) {
            this.chrono.stop();
            partie.setTempsDispo(false); 

        } // il reste du temps
        else {
            // Il reste des lettres a trouver
            if (this.nbLettresRestantes != 0) {
               super.nextLetterToFind.modifyTextAndDisplay("Lettre suivante : "  + super.lettres.get(super.lettres.size() - this.nbLettresRestantes) );
                boolean trouveLettre = tuxTrouveLettre();
                Lettre nextCarac = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
               // System.out.print("[lettre : " + nextCarac.toString() + "] [distance (" + this.distance(nextCarac) + " ] [" + trouveLettre + ") temps(" + this.chrono.remainsTime() + ") \n");
                if (trouveLettre) {
                    this.nbLettresRestantes--;
                }
            } // Il n'y a plus de lettre a trouver
            else {
                partie.setMotTrouve(false);
            }

        }
    }

    // On met a jour les valeurs correspondant a la partie (lettres trouvees et temps passe)
    @Override
    protected void terminePartie(Partie partie) {
        System.out.println("TERMINE PARTIE");
       
        // On recupere les valeurs de la partie
        partie.setTrouve(nbLettresRestantes);
        partie.setTemps(chrono.timeSpent());
        
        // On efface les champs de texte de l'ecran
        this.timeInGame.modifyTextAndDisplay("");
        this.nextLetterToFind.modifyTextAndDisplay("");
    }

    // Renvoie vrai si le personnage Tux est en contact avec la lettres qu'il doit trouver
    private boolean tuxTrouveLettre() {
        Lettre courante = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
        if (super.collision(courante)) {
            return true;
        } else {
            return false;
        }

    }

    // retourne le nombre de lettres restantes a trouver
    private int getNbLettresRestantes() {
        return this.nbLettresRestantes;
    }

    // retourne le temps en secondes que dure la partie
    private int getTemps() {
        return chrono.getSeconds();
    }
    
    
}
