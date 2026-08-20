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
        if (motPropose.equals(motATrouver)) 
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

    public String analyserMot(String motPropose)
    {
        char[] res = new char[motATrouver.length()];
        boolean[] lettresUtilisees = new boolean[motATrouver.length()];
        
        // marquer les lettres correctes (vert)
        for (int i = 0; i < motATrouver.length(); i++) 
        {
            if (motPropose.charAt(i) == motATrouver.charAt(i)) 
            {
                res[i] = 'V'; // V pour vert
                lettresUtilisees[i] = true;
            } 
            else 
            {
                res[i] = ' '; // pas encore déterminé
            }
        }

        // marquer les lettres présentes mais mal placées (jaune)
        for (int i = 0; i < motATrouver.length(); i++) 
        {
            if (res[i] == ' ') // si la lettre n'est pas encore marquée
            {
                for (int j = 0; j < motATrouver.length(); j++) 
                {
                    if (!lettresUtilisees[j] && motPropose.charAt(i) == motATrouver.charAt(j)) 
                    {
                        res[i] = 'J'; // J pour jaune
                        lettresUtilisees[j] = true;
                        break;
                    }
                }
            }
        }

        // marquer les lettres absentes (gris)
        for (int i = 0; i < motATrouver.length(); i++) 
        {
            if (res[i] == ' ') 
            {
                res[i] = 'G'; // G pour gris
            }
        }

        return new String(res);
    }

    public int getEssaisRestants() 
    {
        return essaisRestants;
    }

    public boolean isFinDePartie() 
    {
        return finDePartie;
    }    

    public String getMotATrouver() 
    {
        return motATrouver;
    }

        public static void main(String[] args)
    {
        // Charger les mots depuis le fichier
        List<String> motsPossibles = new ArrayList<>();
        try 
        {
            BufferedReader lecture = new BufferedReader(new FileReader("mots.txt"));
            String ligne;
            while ((ligne = lecture.readLine()) != null) 
            {
                motsPossibles.add(ligne.trim().toUpperCase());
            }
            lecture.close();
        } catch (IOException e) 
        {
            System.out.println("Erreur : " + e.getMessage());
            return;
        }
        
        // Créer une instance du jeu
        Wordle jeu = new Wordle(motsPossibles);
        
        // Créer un scanner pour lire les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BIENVENUE DANS WORDLE ===");
        System.out.println("Devinez le mot de 5 lettres en 6 essais maximum !");
        System.out.println();
        
        // Boucle de jeu
        while (!jeu.isFinDePartie()) 
        {
            System.out.print("Essai " + (7 - jeu.getEssaisRestants()) + "/6 - Entrez un mot : ");
            String motPropose = scanner.nextLine().trim().toUpperCase();
            
            // Vérifier que le mot fait 5 lettres
            if (motPropose.length() != 5) 
            {
                System.out.println("Le mot doit contenir exactement 5 lettres !");
                continue;
            }
            
            // Vérifier que le mot existe dans la liste
            if (!motsPossibles.contains(motPropose)) 
            {
                System.out.println("Ce mot n'existe pas dans le dictionnaire !");
                continue;
            }
            
            // Essayer le mot
            boolean gagne = jeu.essayerMot(motPropose);
            
            // Analyser le mot
            String analyse = jeu.analyserMot(motPropose);
            
            // Afficher le résultat
            System.out.print("Résultat : ");
            for (int i = 0; i < analyse.length(); i++) 
            {
                char c = analyse.charAt(i);
                if (c == 'V') 
                {
                    System.out.print("V");  // Vert
                } 
                else if (c == 'J') 
                {
                    System.out.print("J");  // Jaune
                } 
                else 
                {
                    System.out.print("G");  // Gris
                }
            }
            System.out.println();
            
            // Vérifier si gagné
            if (gagne) 
            {
                System.out.println("BRAVO ! Vous avez trouvé le mot : " + jeu.getMotATrouver());
            } 
            else if (jeu.isFinDePartie()) 
            {
                System.out.println("PERDU ! Le mot était : " + jeu.getMotATrouver());
            } 
            else 
            {
                System.out.println("Il vous reste " + jeu.getEssaisRestants() + " essai(s).");
                System.out.println();
            }
        }
        
        scanner.close();
    }

}