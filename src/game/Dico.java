/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Aurel
 */
public class Dico {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;

    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        this.listeNiveau1 = new ArrayList<String>();
        this.listeNiveau2 = new ArrayList<String>();
        this.listeNiveau3 = new ArrayList<String>();
        this.listeNiveau4 = new ArrayList<String>();
        this.listeNiveau5 = new ArrayList<String>();

    }

    public String getMotDepuisListeNiveaux(int niveau) {
        String aretourner = "";
        niveau = this.verifieNiveau(niveau);
        switch (niveau) {
            case 1:
                aretourner += this.getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                aretourner += this.getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                aretourner += this.getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                aretourner += this.getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                aretourner += this.getMotDepuisListe(listeNiveau5);
                break;
        }
        return aretourner;
    }

    public void ajouteMotADico(int niveau, String mot) {
        switch (this.verifieNiveau(niveau)) {
            case 1:
                this.listeNiveau1.add(mot);
                break;
            case 2:
                this.listeNiveau2.add(mot);
                break;
            case 3:
                this.listeNiveau3.add(mot);
                break;
            case 4:
                this.listeNiveau4.add(mot);
                break;
            case 5:
                this.listeNiveau5.add(mot);
                break;
        }
    }

    public String getCheminFichierDico() {
        return "";
    }

    private int verifieNiveau(int niveau) {
        int aretourner = niveau;
        if (niveau < 1 || niveau > 5) {
            niveau = 1;
        }

        return aretourner;
    }

    private String getMotDepuisListe(ArrayList<String> list) {
        Random r = new Random();
        if (list.size() > 0) {
            return list.get(r.nextInt(list.size() - 1));
        } else {
            return "ANIMAL";
        }
    }

    public void lireDictionnaireDOM(String path, String filename){
        try {
            DOMParser parser = new DOMParser();
            // parse le document XML correspondant au fichier filename dans le chemin path
            parser.parse(path + "" + filename);
            Document doc = parser.getDocument();
            System.out.println("nom de la racine " + doc.getNodeName());
        } catch (SAXException ex) {
        } catch (IOException ex) {
        }
    }
}
