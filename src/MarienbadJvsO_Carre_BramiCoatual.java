/**
 * Jeu de Nim (variate Marienbad)
 * @author Lucien Carré, Thomas Brami Coatual
 */
class MarienbadJvsO_Carre_BramiCoatual {
	/**
	 * Procédure permettant de lancer le début du jeu
	 */
	void lancementJeu() {
		int lines;
		String player_name;
		System.out.println(); //Lisibilité du jeu
		player_name = SimpleInput.getString("Quel est votre nom ? ");
		System.out.println(); //Lisibilité du jeu
		System.out.println("Bienvenue " + player_name + " !");
		System.out.println("Vous jouez en premier, soyez prêt !");

		do {
			lines = SimpleInput.getInt("Sur combien de lignes voulez-vous jouer (entre 2 et 15 lignes) ? ");
		} while (lines < 2 || lines > 15);


		int nbits = (int) (Math.log(lines*2-1)/Math.log(2)) + 1; // on cherche n tel que 2**n >= 2*lines-1


		int[] tab = tableJeu(lines); //création du tableau de jeu
		partieJeu(tab, player_name, nbits); //appel de la procédure lançant une partie
	}





	/**
	 * Procédure permettant de lancer la partie
	 * @param tab le tableau de jeu
	 * @param player_name le nom du joueur 1
	 */
	void partieJeu(int[] tab, String player_name, int nbits) {
		int somme = sommeTableJeu(tab); //Nombre d'allumettes restantes

		boolean joueurJoue = true; // c'est toujours le joeur qui commence à jouer
		while (somme > 0) {
			System.out.println(); //Lisibilité du jeu


			affichageJeu(tab); //Affichage du tableau de jeu

			if (joueurJoue) {
				manchePartie(tab, player_name); //Lancement d'une manche
			} else {
				mancheOrdinateur(tab, nbits);
			}

			somme = sommeTableJeu(tab); //Nombre d'allumettes restantes


			joueurJoue = !joueurJoue; //on inverse le tour (joueur, puis ordi, puis joueur, etc.)
		}

		if (!joueurJoue) {
			System.out.println("Bravo " + player_name + " tu as gagné !");
		} else {
			System.out.println("Dommage " + player_name + " tu as perdu !");
		}
	}

	void tableauJeuVersTableauSommeBinaire(int[] tableau_jeu, int[] tableau_somme) {
		for (int i = 0; i < tableau_jeu.length; i++) {
			for (int j = 0; j < tableau_somme.length; j++) {
				tableau_somme[j] += decimalVersTableauBinaire(tableau_jeu[i], tableau_somme.length)[j];
			}
		}
	}

	void mancheOrdinateur(int[] tab, int nbits) {
		int nombre_a_retirer = 1;
		int ligne_ou_on_a_retire = 0;
		boolean aJoue = false;

		int[] tableau_somme = new int[nbits]; // nombre de bits réquis pour coder x lignes

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
		int nbbatons = SimpleInput.getInt("Combien de batons veux-tu retirer ?");
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
		System.out.print(t[i] + "}");
	}











	// true si le joueur dont c'est le tour peut gagner
	boolean joueurActuelPeutGagner(int[] somme) {
		boolean resultat = false;

		for (int i = 0; i < somme.length; i++) {
			if (somme[i] % 2 != 0) {
				resultat = true;
			}
		}

		return resultat;
	}


	/*
	**
	* @param number
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

	void principal() {
		testTableJeu();
		testTableId();
		testSommeTableJeu();
		testStrId();
		lancementJeu();
	}
}
