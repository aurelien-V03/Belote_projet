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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

    // @cheminFichierDico = chemin du dictionnaire XML
    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        this.listeNiveau1 = new ArrayList<String>();
        this.listeNiveau2 = new ArrayList<String>();
        this.listeNiveau3 = new ArrayList<String>();
        this.listeNiveau4 = new ArrayList<String>();
        this.listeNiveau5 = new ArrayList<String>();

    }

    // Méthode qui permet de piocher aleatoirement un mot danns une liste de niveau "niveau"
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

    // ajoute le mot "mot" dans la liste de niveau "niveau"
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

    // Verifie que le niveau passé soit bien dans l'interval des valeurs [1;5]
    private int verifieNiveau(int niveau) {
        int aretourner = niveau;
        if (niveau < 1 || niveau > 5) {
            niveau = 1;
        }

        return aretourner;
    }

    // Extrait un mot aleatoirement dans une liste
    private String getMotDepuisListe(ArrayList<String> list) {
        Random r = new Random();
        if (list.size() > 0) {
            return list.get(r.nextInt(list.size() - 1));
        } else {
            return "ANIMAL";
        }
    }

      public void lireDictionnaireDOM(String path, String filename) throws SAXException, IOException {

        DOMParser parser = new DOMParser();
        parser.parse(path + "" + filename);
        Document racine = parser.getDocument();

        // récupère la liste des éléments nommés tr:pos
        NodeList posList = racine.getElementsByTagName( "mot");
            System.out.println("nombre de mot =   "+ posList.getLength());

        for (int i = 0; i < posList.getLength(); i++) {
            Element mot = (Element) posList.item(i);
            String mot_text = mot.getTextContent();
            int mot_niveau =  Integer.parseInt(mot.getAttribute("niveau"));
            System.out.println(mot_text  + "("+mot_niveau  +")");
            
            switch(mot_niveau)
            {
                case 1:
                    this.listeNiveau1.add(mot_text);
                    break;
                case 2:
                     this.listeNiveau2.add(mot_text);
                    break;
                case 3:
                     this.listeNiveau3.add(mot_text);
                    break;
                case 4:
                     this.listeNiveau4.add(mot_text);
                    break;
                case 5:
                     this.listeNiveau5.add(mot_text);
                    break;
            }
            
        }}
   
}
