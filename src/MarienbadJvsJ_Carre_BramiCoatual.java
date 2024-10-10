/**
* Jeu de Nim (variate Marienbad)
* @author Lucien Carré, Thomas Brami Coatual
*/
class MarienbadJvsJ_Carre_BramiCoatual {
    void principal() {
        lancementJeu();
    }
    
    void lancementJeu() {
		int lines;
		String player1, player2;
		player1 = SimpleInput.getString("Quel est le nom du joueur jouant en premier : ");
		player2 = SimpleInput.getString("Quel est le nom du deuxième joueur : ");
        do {
            lines = SimpleInput.getInt("Choisissez votre nombre de lignes (entre 2 et 15 lignes) : ");
        } while (lines < 2 || lines > 15);
        int[] tab = tableJeu(lines);
        partieJeu(tab, player1, player2);
        
    }
    
    
    
    void partieJeu(int[] tab, String player1, String player2) {
        String nameActu = player1;
        int somme = sommeLigneJeu(tab);
        while (somme > 0) {
			affichageJeu(tab);
			manchePartie(tab, nameActu);
			if (nameActu == player1) {
				nameActu = player2;
			} else {
				nameActu = player1;
			}
			somme = sommeLigneJeu(tab);
		}
		System.out.println("Victoire au joueur " + nameActu);
	}
	
	
	void manchePartie(int[] tab, String nameActu) {
		System.out.println("A toi de jouer " + nameActu);
		int line = SimpleInput.getInt("A quel ligne veux-tu retirer des batons ?");
		while (line < 0 || line > tab.length - 1 || tab[line] == 0) {
			if (line < 0 || line > tab.length - 1) {
				line = SimpleInput.getInt("Cette ligne n'existe pas, à quel ligne veux-tu retirer des batons ?");
			} else {
				line = SimpleInput.getInt("Cette ligne est vide, à quel ligne veux-tu retirer des batons ?");
			}
		}
		int nbbatons = SimpleInput.getInt("Combien de batons veux-tu retirer ?");
		while (tab[line] - nbbatons < 0) {
			nbbatons = SimpleInput.getInt("Le nombre de batons rentrés est trop grand, combien de batons veux-tu retirer ?");
		}
		tab[line] -= nbbatons;
	}
	
	
	
    int sommeLigneJeu(int[] tab) {
		int somme = 0;
		for (int i = 0; i < tab.length; i++) {
			somme += tab[i];
		}
		return somme;
	}
	

    
    
    /**
    * Méthode permettant de créer la table de jeu
    * @return res un tableau représentant des batons
    */
    int[] tableJeu(int lines) {
        int[] res = new int[lines];
        int batons = 1;
        for (int i = 0; i < res.length;i++) {
            res[i] += batons;
            batons += 2;
        }
        return res;
    }
    
    

    /**
    * méthode permettant d'afficher la table de jeu
    * @param t la table de jeu
    */
    void affichageJeu(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
			int cpt = 0;
            System.out.print(i + " :");
            while (cpt < tab[i]) {
				System.out.print(" |");
				cpt++;
            }
            System.out.println();
        }
    }
}
