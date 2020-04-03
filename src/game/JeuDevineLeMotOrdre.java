
package game;

import java.util.Random;

/**
 *
 * @author Aurel
 */
public class JeuDevineLeMotOrdre extends Jeu2{
    // nombre de lettres restantes a trouver dans le mot
    private int nbLettresRestantes;
    // timer de la partie
    private Chronometre chrono;
    
    public JeuDevineLeMotOrdre(){
        super();
    }

    @Override
    protected void demarrePartie(Partie partie) {
        
        System.out.println("DEMARRE PARTIE");
        // le joueur a 30 secondes
        this.chrono = new Chronometre(30000);
           
        char[] mot_caracteres = partie.getMot().toCharArray();
        // genere 1 coordonne [X = [0;100] Y = [0;100] ] sur ces interval
        Random r = new Random();
        int z;
        int y;
                    for (char c : mot_caracteres) {
                        System.out.println("Lettre :"+ c);
                        z = r.nextInt(101);
                        y = r.nextInt(101);
                        this.lettres.add(new Lettre(c, z, y));                 
                    }
       this.nbLettresRestantes = super.lettres.size();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
       System.out.println("APPLIQUE PARTIE");

        // Le temps est ecoule
        if(chrono.remainsTime() == false)
        {
            System.out.println("JEU TERMINE" + partie);
            this.chrono.stop();
            
        }
        // il reste du temps
        else{
            // Il reste des lettres a trouver
            if(this.nbLettresRestantes != 0)
            {
            boolean trouveLettre = tuxTrouveLettre();
            Lettre nextCarac = super.lettres.get(super.lettres.size() - this.nbLettresRestantes );
            System.out.print("[lettre : " + nextCarac.toString() + "] [distance ("+this.distance(nextCarac)+" ] [" + trouveLettre + ") temps("+this.chrono.remainsTime()+") \n");
                if(trouveLettre)
                {
                    this.nbLettresRestantes--;
                }
            }
            // Il n'y a plus de lettre a trouver
            else
            {
                System.out.print("PLUS DE LETTRES");
            }
          
        }
    }

    @Override
    protected void terminePartie(Partie partie) {
         System.out.println("TERMINE PARTIE");
        partie.setTrouve(nbLettresRestantes);
        partie.setTemps(chrono.timeSpent());
    
    }
    
    private boolean tuxTrouveLettre(){              
        Lettre courante = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
        if(super.collision(courante))
        {
            return true;
        }
        else 
            return false;
        
    }
    
    private int getNbLettresRestantes(){
        return this.nbLettresRestantes;
    }
    
    private int getTemps(){
        return chrono.getSeconds();
    }
}
