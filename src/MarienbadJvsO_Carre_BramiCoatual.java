/**
 * Jeu de Nim (variate Marienbad)
 * @author Lucien Carré, Thomas Brami Coatual
 */
class MarienbadJvsO_Carre_BramiCoatual {
	/**
	 * Procédure permettant de lancer le début du jeu
	 */
	void lancementJeu() {
		int idRejoue = 1;
		int lines;
		String player_name;
		int nbits = 0;
		
		while (idRejoue == 1) {
			
			System.out.println("Nouvelle partie");
			System.out.println();
			System.out.println("Les règles du jeu sont simple :");
			System.out.println("On prend n'importe quels nombres de batons sur une ligne");
			System.out.println("On gagne quand on prend le ou les derniers batons sur la table");
			System.out.println();
			
			//Permet de demander le nom du joueur
			player_name = SimpleInput.getString("Quel est votre nom ? ");
			System.out.println();
			System.out.println("Bienvenue " + player_name + " !");
			System.out.println();
			
			//Vérification du nombre de lignes de la partie (entre 2 et 15 inclus)
			do {
				lines = SimpleInput.getInt("Sur combien de lignes voulez-vous jouer (entre 2 et 15 lignes) ? ");
			} while (lines < 2 || lines > 15);
			
			//On cherche n tel que 2**n >= 2*lines-1
			nbits = (int) (Math.log(lines*2-1)/Math.log(2)) + 1; 

			//Création du tableau de jeu
			int[] tab = tableJeu(lines); 			
			
			//Lancement de la partie
			partieJeu(tab, player_name, nbits);
			
			//Demande de relance de partie
			do {
				idRejoue = SimpleInput.getInt("Voulez-vous rejouer ? Oui (1), non (0) ");
			} while (idRejoue != 1 && idRejoue != 0);	
		}			
	}





	/**
	 * Procédure permettant de lancer la partie
	 * @param tab le tableau de jeu
	 * @param player_name le nom du joueur
	 * @param nbits nombre de bits utilisé pour coder le plus grand nombre d'allumettes
	 */
	void partieJeu(int[] tab, String player_name, int nbits) {
		
		//Nombre d'allumettes restantes
		int somme = sommeTableJeu(tab); 
		
		boolean joueurJoue = true;
		int joueEnPremier = 1;
		int nivPC = 2;

		//Demande qui joue en premier
		do {
			joueEnPremier = SimpleInput.getInt("Qui joue en premier ? " + player_name + " (1) ou l'ordinateur (2) ");
		} while (joueEnPremier != 1 && joueEnPremier != 2);
		
		//Nom du joueur qui joue actuellement
		if (joueEnPremier == 2) {
			joueurJoue = false;
			System.out.println("L'ordinateur jouera en premier");
		} else {
			System.out.println("Vous jouerez en premier");
		}
		System.out.println();
		
		//Demande la difficulté de l'ordinateur
		do {
			nivPC = SimpleInput.getInt("Quel ordinateur voulez-vous utiliser ? Aléatoire (1) ou Intelligent (2) ");
		} while (joueEnPremier != 1 && joueEnPremier != 2);
		
		//Tant qu'il reste des allumettes on joue
		while (somme > 0) {
			System.out.println();
			affichageJeu(tab);

			if (joueurJoue) {
				manchePartie(tab, player_name);
			} else {
				if (nivPC == 1) {
					mancheOrdinateurAleatoire(tab);
				} else {
					mancheOrdinateurIntelligent(tab, nbits);
				}
			}
			
			//Nombre d'allumettes restantes
			somme = sommeTableJeu(tab); 
			
			//On inverse le tour (joueur, puis ordi, puis joueur, etc.)
			joueurJoue = !joueurJoue; 
		}
		
		//Affichage du tableau de jeu final
		System.out.println(); 
		affichageJeu(tab);
		
		if (!joueurJoue) {
			System.out.println("Bravo " + player_name + " tu as gagné !");
		} else {
			System.out.println("Dommage " + player_name + " tu as perdu !");
		}
	}
	
	
	
	
	
	/**
	 * Procédure permettant de créer un tableau de somme des binaires de tableau de jeu
	 * @param tableau_jeu le tableau de jeu
	 * @param tableau_somme le tableau au quel on met la somme des binaires
	 */
	void tableauJeuVersTableauSommeBinaire(int[] tableau_jeu, int[] tableau_somme) {
		for (int i = 0; i < tableau_jeu.length; i++) {
			for (int j = 0; j < tableau_somme.length; j++) {
				tableau_somme[j] += decimalVersTableauBinaire(tableau_jeu[i], tableau_somme.length)[j];
			}
		}
	}
	
	/**
	 * Procédure permettant de tester un appel de tableauJeuVersTableauSommeBinaire()
	 * @param tableau_jeu tableau du jeu
	 * @param tableau_somme tableau de la somme des binaire des valeurs de tableau_jeu
	 * @param result resultat attendu
	 **/
	void testCasTableauJeuVersTableauSommeBinaire(int[] tableau_jeu, int[] tableau_somme, int[] result) {
		
		// Affichage
		System.out.print ("tableauJeuVersTableauSommeBinaire(");
		displayTab(tableau_jeu);
		System.out.print(", ");
		displayTab(tableau_somme);
		System.out.print(") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		
		// Appel
		tableauJeuVersTableauSommeBinaire(tableau_jeu, tableau_somme);
		boolean resExec = tableId(tableau_somme, result);
		
		// Verification
		if (resExec){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}

	/**
	 * Procédure permettant de tester la méthode tableauJeuVersTableauSommeBinaire()
	 */
	void testTableauJeuVersTableauSommeBinaire() {
		System.out.println ();
		System.out.println ("*** TableauJeuVersTableauSommeBinaire()");

		int[] tab1 = {3,5,6};
		int[] tab2 = {1,4,2,9};
		int[] tab3 = {1,2};
		int[] tabSomme1 = new int[3];
		int[] tabSomme2 = new int[4];
		int[] tabSomme3 = new int[2];
		int[] tabResult1 = {2,2,2};
		int[] tabResult2 = {1,1,1,2};
		int[] tabResult3 = {1,1};

		testCasTableauJeuVersTableauSommeBinaire(tab1, tabSomme1, tabResult1);
		testCasTableauJeuVersTableauSommeBinaire(tab2, tabSomme2, tabResult2);
		testCasTableauJeuVersTableauSommeBinaire(tab3, tabSomme3, tabResult3);
	}
	
	
	
	
	
	/**
	 * Procédure permettant de lancer une manche de l'ordinateur aléatoire
	 * @param tab le tableau de jeu qu'on modifie
	 */
	void mancheOrdinateurAleatoire(int[] tab) {
		int nombre_a_retirer = 1;
		int ligne_ou_on_a_retire = 0;
		boolean aJoue = false;
		
		while (!aJoue) {
			int i = (int) (Math.random() * tab.length);
			if (tab[i] > 0) {
				nombre_a_retirer = (int) ((Math.random() * tab[i]) + 1);
				tab[i] -= nombre_a_retirer;
				ligne_ou_on_a_retire = i;
				aJoue = true;
			}
		}
		System.out.println("L'ordinateur a retiré " + nombre_a_retirer + " batons sur la ligne " + ligne_ou_on_a_retire);
	}
	
	
	
	
	
	/**
	 * Procédure permettant de lancer une manche de l'ordinateur intelligent
	 * @param tab le tableau de jeu qu'on modifie
	 * @param nbits nombre de bits utilisé pour coder le plus grand nombre d'allumettes
	 */
	void mancheOrdinateurIntelligent(int[] tab, int nbits) {
		int nombre_a_retirer = 1;
		int ligne_ou_on_a_retire = 0;
		boolean aJoue = false;

		int[] tableau_somme = new int[nbits]; // nombre de bits requis pour coder x lignes

		tableauJeuVersTableauSommeBinaire(tab, tableau_somme);

		System.out.print("tableau_somme: "); displayTab(tableau_somme);

		if (joueurActuelPeutGagner(tableau_somme)) { // si l'ordi peut gagner
			// on a pas trouvé mieux que de bruteforce un coup gagnant

			boolean coupEstGagnant = false;
			while (!coupEstGagnant && ligne_ou_on_a_retire < tab.length) {

				// on crée une copie du tableau du jeu pour ne pas l'affecter dans notre recherche de solution
				int[] tab_copie = new int[tab.length];
				for (int i = 0; i < tab.length; i++) {
					tab_copie[i] = tab[i];
				}

				// Vérifiez si on peut retirer des bâtons de la ligne actuelle
				if (nombre_a_retirer > tab_copie[ligne_ou_on_a_retire]) {
					ligne_ou_on_a_retire++;
					nombre_a_retirer = 1;
					if (ligne_ou_on_a_retire >= tab_copie.length) {
						break; // Plus de lignes à tester
					}
				}

				System.out.println("ligne_ou_on_a_retire: " + ligne_ou_on_a_retire);
				System.out.println("nombre_a_retirer: " + nombre_a_retirer);

				// si on tombe sur une ligne vide, on cherche à modifier la suivante
				tab_copie[ligne_ou_on_a_retire] -= nombre_a_retirer;


				// on crée un tableau binaire de la somme des lignes du tableau de jeu
				int[] somme_tab_solution = new int[nbits];
				tableauJeuVersTableauSommeBinaire(tab_copie, somme_tab_solution);
				System.out.print("somme_tab_solution: "); displayTab(somme_tab_solution);

				if (!joueurActuelPeutGagner(somme_tab_solution)) {
					System.out.print("Solution gagnante trouvée! "); System.out.print("somme_tab_solution: "); displayTab(somme_tab_solution); System.out.println();

					// on met à jour le tableau de jeu avec la solution trouvée
					for (int i = 0; i < tab.length; i++) {
						tab[i] = tab_copie[i];
					}


					coupEstGagnant = true;
				}

				nombre_a_retirer++;
			}


		} else {
			// on retire un nombre aléatoire de baton sur une ligne aléatoire

			while (!aJoue) {
				int i = (int) (Math.random() * tab.length);
				if (tab[i] > 0) {
					nombre_a_retirer = (int) ((Math.random() * tab[i]) + 1);
					tab[i] -= nombre_a_retirer;
					ligne_ou_on_a_retire = i;
					aJoue = true;
				}
			}
		}

		System.out.println("L'ordinateur a retiré " + nombre_a_retirer + " batons sur la ligne " + ligne_ou_on_a_retire);
	}





	/**
	 * Procédure permettant de lancer une manche et de modifier le tableau
	 * @param tab le tableau de jeu modifié lors de la manche
	 * @param nameActu le nom du joueur qui joue actuellement
	 */
	void manchePartie(int[] tab, String nameActu) {
		System.out.println();
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
	 * Méthode permettant d'afficher la table de jeu
	 * @param tab la table de jeu
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
	 * Méthode permettant de lancer une manche et de modifier le tableau
	 * @param tab le tableau de jeu modifié lors de la manche
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
		System.out.println();
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
	 * @return res: true si les tableaux sont identiques sinon false
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
		System.out.print(t[i] + "}");
	}





	/**
	 * Méthode permettant de savoir si le joueur actuelle peut gagner
	 * @param somme tableau avec la somme des binaires du tableau de jeu
	 * @return resultat boolean true si il peut gagner sinon false
	 */
	boolean joueurActuelPeutGagner(int[] somme) {
		boolean resultat = false;

		for (int i = 0; i < somme.length; i++) {
			if (somme[i] % 2 != 0) {
				resultat = true;
			}
		}

		return resultat;
	}
	
	/**
	 * Procédure permettant de tester un appel de joueurActuelPeutGagner()
	 * @param somme un tableau de somme binaire
	 * @param result resultat attendu
	 **/
	void testCasJoueurActuelPeutGagner(int[] somme, boolean result) {
		
		// Affichage
		System.out.print ("joueurActuelPeutGagner(");
		displayTab(somme);
		System.out.print(") \t= " + result + "\t : ");
		
		// Appel
		boolean resExec = joueurActuelPeutGagner(somme);
		
		// Verification
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}

	/**
	 * Procédure permettant de tester la méthode joueurActuelPeutGagner()
	 */
	void testJoueurActuelPeutGagner() {
		System.out.println ();
		System.out.println ("*** testJoueurActuelPeutGagner()");

		int[] tab1 = {1,3,4,2};
		int[] tab2 = {1,2,0};
		int[] tab3 = {4,2};
		int[] tab4 = {2,4,0};

		testCasJoueurActuelPeutGagner(tab1, true);
		testCasJoueurActuelPeutGagner(tab2, true);
		testCasJoueurActuelPeutGagner(tab3, false);
		testCasJoueurActuelPeutGagner(tab4, false);
	}





	/**
	 * Méthode permettant de coder un nombre en binaire dans un tableau
	 * @param number nombre à convertir
	 * @param nbits nombre de bits utilisé pour coder le plus grand nombre d'allumettes
	 * @return result tableau binaire du nombre
	 */
	int[] decimalVersTableauBinaire(int number, int nbits) {
		int[] puissances_de_deux = new int[nbits];
		for (int i = 0; i < nbits; i++) {
			puissances_de_deux[nbits - 1 - i] = (int) Math.pow(2, i);
		}
		

		int[] result = new int[puissances_de_deux.length];

		for (int i = 0 ; i < puissances_de_deux.length; i++) {
			if (number >= puissances_de_deux[i]) {
				number -= puissances_de_deux[i];
				result[i] = 1;
			} else {
				result[i] = 0;
			}
		}

		return result;
	}
	
	/**
	 * Procédure permettant de tester un appel de decimalVersTableauBinaire()
	 * @param number nombre à convertir
	 * @param nbits nombre de bits utilisé pour coder le plus grand nombre d'allumettes
	 * @param result resultat attendu
	 **/
	void testCasDecimalVersTableauBinaire(int number, int nbits, int[] result) {
		
		// Affichage
		System.out.print ("decimalVersTableauBinaire(" + number + ", " + nbits + ") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		
		// Appel
		int[] resExec = decimalVersTableauBinaire(number, nbits);
		
		// Verification
		if (tableId(resExec, result)){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}

	/**
	 * Procédure permettant de tester la méthode decimalVersTableauBinaire()
	 */
	void testDecimalVersTableauBinaire() {
		System.out.println ();
		System.out.println ("*** testJoueurActuelPeutGagner()");

		int[] tab1 = {1,1,0,0,0};
		int[] tab2 = {1,1,0,0};
		int[] tab3 = {1,0,0,1};
		int[] tab4 = {1,1};

		testCasDecimalVersTableauBinaire(24, 5, tab1);
		testCasDecimalVersTableauBinaire(12, 4, tab2);
		testCasDecimalVersTableauBinaire(9, 4, tab3);
		testCasDecimalVersTableauBinaire(3, 2, tab4);
	}
	
	
	
	

	void principal() {
		/*testTableJeu();
		testTableId();
		testSommeTableJeu();
		testStrId();
		testJoueurActuelPeutGagner();
		testDecimalVersTableauBinaire();
		testTableauJeuVersTableauSommeBinaire();*/
		
		lancementJeu();
	}
}
