// Logique du jeu : choix du mot, vérification, analyse des lettres)
import java.util.List;


public class Wordle 
{
    private String motATrouver; //mot à trouver
    private int essaisRestants; //essais restants, max 6 essais
    private List<String> motsPossibles; //liste des mots possibles depuis le fichier
    private boolean finDePartie; //indique si la partie est terminée

    public Wordle(List<String> motsPossibles)
    {
        this.motATrouver = choisirMot(motsPossibles);
        this.essaisRestants = 6;
        this.finDePartie = false;
    }
    

}