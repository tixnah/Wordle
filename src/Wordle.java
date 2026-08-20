// Logique du jeu : choix du mot, vérification, analyse des lettres)
import java.util.*;
import java.io.*;


public class Wordle 
{
    //mot à trouver
    private String motATrouver; 

    //essais restants, max 6 essais
    private int essaisRestants; 

    //liste des mots possibles depuis le fichier
    private List<String> motsPossibles; 

     //indique si la partie est terminée
    private boolean finDePartie;

    /** Constructeur de la classe Wordle */
    public Wordle(List<String> motsPossibles)
    {
        // charger les mots depuis mots.txt
        this.motsPossibles = motsPossibles;

        // choisir un mot aléatoire dans la liste
        Random aleatoire = new Random();
        int index = aleatoire.nextInt(motsPossibles.size());
        motATrouver = motsPossibles.get(index);

        

        // initialiser les essais restants à 6
        this.essaisRestants = 6;

        // initialiser la fin de partie à false
        this.finDePartie = false;
    }

    /** Charge les mots depuis le fichier mots.txt */
    private void chargerMots()
    {
        // lit le fichiers mots.txt et stocke chaque mot dans la liste motsPossibles
        motsPossibles = new ArrayList<>();

        try 
        {
            BufferedReader lecture = new BufferedReader(new FileReader("mots.txt"));
            String ligne;
            while ((ligne = lecture.readLine()) != null) 
            {
                motsPossibles.add(ligne.trim());
            }
            lecture.close();
        } catch (IOException e) 
        {
            System.out.println("Erreur lors de la lecture du fichier mots.txt : " + e.getMessage());
        }

    }

    public boolean essayerMot(String motPropose)
    {
        if (motPropose == motATrouver) 
        {
            finDePartie = true;
            return true;
        } 
        else 
        {
            essaisRestants--;
            if (essaisRestants == 0) 
            {
                finDePartie = true;
            }
            return false;
        }
    }
    

}