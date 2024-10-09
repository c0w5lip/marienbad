/**
* Jeu de Nim (variate Marienbad)
* @author Lucien Carré, Thomas Brami Coatual
*/
class Multiplayer {
    /**
    * Méthode permettant de créer la table de jeu
    * @return res un tableau représentant des batons
    */
    int[][] tableJeu() {
        int lines;
        do {
            lines = SimpleInput.getInt("Choisissez votre nombre de lignes (entre 3 et 15 lignes) : ");
        } while (lines < 3 || lines > 15);
        
        int[][] res = new int[lines][];
        int batons = 1;
        for (int i = 0; i < res.length;i++) {
            res[i] = new int[batons];
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = 1;
            }
            batons += 2;
        }
        return res;
    }

    /**
    * méthode permettant d'afficher la table de jeu
    * @param t la table de jeu
    */
    void affichageJeu(int[][] t) {
        for (int i=0; i < t.length; i++) {
            System.out.print(i + " :");
            for (int j=0; j < t[i].length; j++) {
                if (t[i][j] == 1) {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
    }

    void principal() {
        int[][] tab = tableJeu();
        affichageJeu(tab);
    }
}
