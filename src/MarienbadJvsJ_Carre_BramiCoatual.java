/**
* Jeu de Nim (variate Marienbad)
* @author Lucien Carré, Thomas Brami Coatual
*/
class MarienbadJvsJ_Carre_BramiCoatual {
    void principal() {
		/*testTableJeu();
		testTableId();
		testSommeTableJeu();
		testStrId();*/
		
		lancementJeu();
    }
    
    
    
    
    
    /**
    * Procédure permettant de lancer le début du jeu
    */
    void lancementJeu() {
		int idRejoue = 1;
		String player1;
		String player2;
		
		while (idRejoue == 1) {
			
			System.out.println("Nouvelle partie");
			System.out.println();
			System.out.println("Les règles du jeu sont simple :");
			System.out.println("On prend n'importe quels nombres de batons sur une ligne");
			System.out.println("On gagne quand on prend le ou les derniers batons sur la table");
			System.out.println();
						
			player1 = SimpleInput.getString("Quel est le nom du premier joueur ? ");
			player2 = SimpleInput.getString("Quel est le nom du deuxième joueur ? ");
			
			//Vérification de pseudos de player différents
			while (strId(player1, player2)) {
				System.out.println("Deux joueurs ne peuvent pas avoir le meme nom");
				player2 = SimpleInput.getString("Quel est le nom du deuxième joueur ? ");
			}
			
			System.out.println();
			System.out.println("Bienvenue " + player1 + " et " + player2 + " !");
			System.out.println();
			
			//Lancement de la partie
			partieJeu(player1, player2);
			
			//Demande de relance de partie
			do {
				idRejoue = SimpleInput.getInt("Voulez-vous rejouer ? Oui (1), non (0) ");
			} while (idRejoue != 1 && idRejoue != 0);
		}
    }
    
    
    
    
    
    /**
    * Procédure permettant de lancer la partie
    * @param tab le tableau de jeu
    * @param player1 le nom du joueur 1
    * @param player2 le nom du joueur 2
    */
    void partieJeu(String player1, String player2) {
		String nameActu = player1;
		int somme = 1;
		int joueEnPremier = 0;
		int lines;
		
		//Demande qui joue en premier
		do {
			joueEnPremier = SimpleInput.getInt("Qui joue en premier ? " + player1 + " (1) ou " + player2 + " (2) ");
		} while (joueEnPremier != 1 && joueEnPremier != 2);
		
		//Vérification du nombre de lignes de la partie (entre 2 et 15 inclus)
		do {
			lines = SimpleInput.getInt("Sur combien de lignes voulez-vous jouer (entre 2 et 15 lignes) ? ");
		} while (lines < 2 || lines > 15);
		int[] tab = tableJeu(lines);
		somme = sommeTableJeu(tab);
		
		//Nom du joueur qui joue actuellement
		if (joueEnPremier == 2) {
			nameActu = player2;
			System.out.println(player2 + " jouera en premier");
		} else {
			System.out.println(player1 + " jouera en premier");
		}
		
		//Tant qu'il reste des allumettes
		while (somme > 0) {
			
			//Lisibilité du jeu
			System.out.println(); 
			affichageJeu(tab);
			System.out.println();
			manchePartie(tab, nameActu);
			somme = sommeTableJeu(tab);
			
			//Changement de joueur actuelle à condition qu'il reste des batons
			if (nameActu == player1 && somme != 0) {
				nameActu = player2;
			} else if (nameActu == player2 && somme != 0){
				nameActu = player1;
			}
		}
		
		//Lisibilité du jeu
		System.out.println(); 
		affichageJeu(tab);
		System.out.println("Victoire du joueur " + nameActu);
		System.out.println();
	}
	
	
	
	
	
	/**
    * Procédure permettant de lancer une manche et de modifier le tableau
    * @param tab le tableau de jeu modifié lors de la manche
    * @param nameActu le nom du joueur qui joue actuellement
    */
	void manchePartie(int[] tab, String nameActu) {
		System.out.println("A toi de jouer " + nameActu);
		int line = SimpleInput.getInt("A quel ligne veux-tu retirer des batons ? ");
		
		//Boucle vérifiant si la ligne existe ou n'est pas vide
		while (line < 0 || line > tab.length - 1 || tab[line] == 0) { 
			if (line < 0 || line > tab.length - 1) {
				line = SimpleInput.getInt("Cette ligne n'existe pas, à quel ligne veux-tu retirer des batons ? ");
			} else {
				line = SimpleInput.getInt("Cette ligne est vide, à quel ligne veux-tu retirer des batons ? ");
			}
		}
		
		int nbbatons = SimpleInput.getInt("Combien de batons veux-tu retirer ? ");
		
		//Boucle vérifiant si le nombre de batons n'est pas trop élevé par rapport au nombre de batons sur la ligne ou si il est trop faible
		while (tab[line] - nbbatons < 0 || nbbatons < 1) { 
			if (nbbatons < 1) {
				nbbatons = SimpleInput.getInt("Le nombre de batons rentrés est inférieur ou égale à 0, combien de batons veux-tu retirer ? ");
			} else {
				nbbatons = SimpleInput.getInt("Le nombre de batons rentrés est trop grand, combien de batons veux-tu retirer ? ");
			}
		}
		
		//Réduction du nombre de batons par le nombre de batons sélectionnés sur la ligne sélectionné par le joueur
		tab[line] -= nbbatons; 
	}
	
	
	
	
	
	/**
    * Méthode permettant d'afficher le tableau de jeu
    * @param t la table de jeu
    */
    void affichageJeu(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
			int cpt = 0;
			
			if (i > 9) {
				System.out.print(i + " :");
			} else {
				System.out.print(i + " : ");
			}
			
            while (cpt < tab[i]) {
				System.out.print(" |");
				cpt++;
            }
            System.out.println();
        }
    }
	
	
	
	
	
	/**
	* Méthode permettant de tester si deux chaines de caractères sont identiques
	* @param str1 une chaine de caractère
	* @param str2 une chaine de caractère
	**/
	boolean strId(String str1, String str2) {
		boolean res = true;
		
		if (str1.length() != str2.length()) {
			res = false;
		} else {
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) {
					res = false;
				}
			}
		}
		return res;
	}
	
	/**
	* Procédure permettant de tester un appel de strId()
	* @param str1 une chaine de caractère
	* @param str2 une chaine de caractère
	* @param result resultat attendu
	**/
	void testCasTableId(String str1, String str2, boolean result) {
		
		// Affichage
		System.out.print ("strId(" + str1 + ", " + str2 + ") \t= " + result + "\t : ");
		
		// Appel
		boolean resExec = strId(str1, str2);
		
		// Verification
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	* Procédure permettant de tester la méthode strId()
	*/
	void testStrId() {
		System.out.println ();
		System.out.println ("*** testStrId()");
		
		String str1 = "ab";
		String str2 = "ac";
		String str3 = "abc";
		
		testCasTableId(str1, str1, true);
		testCasTableId(str1, str2, false);
		testCasTableId(str1, str3, false);
		testCasTableId(str3, str1, false);
	}
	
	
	
	
	
	/**
    * Méthode permettant de faire la somme des allumettes dans le tableau
    * @param tab le tableau de jeu
    * @return somme la somme des batons du tableau de jeu
    */
    int sommeTableJeu(int[] tab) {
		int somme = 0;
		for (int i = 0; i < tab.length; i++) {
			somme += tab[i];
		}
		return somme;
	}
	
	/**
	* Procédure permettant de tester un appel de sommeTableJeu()
	* @param tab un tableau
	* @param result resultat attendu
	**/
	void testCasSommeTableJeu(int[] tab, int result) {
		
		// Affichage
		System.out.print ("sommeTableJeu(");
		displayTab(tab);
		System.out.print(") \t= " + result + "\t : ");
		
		// Appel
		int resExec = sommeTableJeu(tab);
		
		// Verification
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	* Procédure permettant de tester la méthode sommeTableJeu()
	*/
	void testSommeTableJeu() {
		System.out.println ();
		System.out.println ("*** testSommeTableJeu()");
		
		int[] tab1 = {1,3};
		int[] tab2 = {1,2,78};
		int[] tab3 = {1,3,5,6,0,7};
		
		testCasSommeTableJeu(tab1, 4);
		testCasSommeTableJeu(tab2, 81);
		testCasSommeTableJeu(tab3, 22);
	}	
	

    
    
    
    /**
    * Méthode permettant de créer le tableau de jeu
    * @param lines le nombre de lignes du tableau de jeu (entre 2 et 15)
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
	* Procédure permettant de tester un appel de tableJeu()
	* @param lines le nombre de lignes du tableau
	* @param result resultat attendu
	**/
	void testCasTableJeu(int lines, int[] result) {
		
		// Affichage
		System.out.print ("tableJeu(" + lines + ") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		
		// Appel
		int[] resExec = tableJeu(lines);
		
		// Verification
		if (tableId(resExec, result)){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	* Procédure permettant de tester la méthode tableJeu()
	*/
	void testTableJeu() {
		System.out.println ();
		System.out.println ("*** testTableJeu()");
		
		int[] res1 = {1,3};
		int[] res2 = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29};
		int[] res3 = {1,3,5,7,9};
		
		testCasTableJeu(2, res1);
		testCasTableJeu(15, res2);
		testCasTableJeu(5, res3);
	}
    
    
    
    
    
    /**
	* Méthode permettant de tester si deux tableaux sont identiques
	* @param tab1 un tableau
	* @param tab2 un tableau
	**/
	boolean tableId(int[] tab1, int[] tab2) {
		boolean res = true;
		
		if (tab1.length != tab2.length) {
			res = false;
		} else {
			for (int i = 0; i < tab1.length; i++) {
				if (tab1[i] != tab2[i]) {
					res = false;
				}
			}
		}
		return res;
	}
	
	/**
	* Procédure permettant de tester un appel de tableId()
	* @param tab1 un tableau
	* @param tab2 un tableau
	* @param result resultat attendu
	**/
	void testCasTableId(int[] tab1, int[] tab2, boolean result) {
		
		// Affichage
		System.out.print ("tableId(");
		displayTab(tab1);
		System.out.print(", ");
		displayTab(tab2);
		System.out.print(") \t= " + result + "\t : ");
		
		// Appel
		boolean resExec = tableId(tab1, tab2);
		
		// Verification
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	* Procédure permettant de tester la méthode tableId()
	*/
	void testTableId() {
		System.out.println ();
		System.out.println ("*** testTableId()");
		
		int[] tab1 = {1,3};
		int[] tab2 = {1,2};
		int[] tab3 = {1,3,5};
		
		testCasTableId(tab1, tab1, true);
		testCasTableId(tab1, tab2, false);
		testCasTableId(tab1, tab3, false);
		testCasTableId(tab3, tab1, false);
	}
	
    
    
    
    
	/**
	* Procédure permettant d'afficher un tableau
	* @param t tableau d’entiers
	*/
	void displayTab(int[] t) {
		int i = 0;
		System.out.print("{");
		while (i<t.length-1) {
			System.out.print(t[i] + ",");
			i++;
		}
		if (t.length > 0) {
			System.out.print(t[i] + "}");
		} else {
			System.out.print("}");
		}
	}
    
}
