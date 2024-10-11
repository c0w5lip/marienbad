import javax.sound.midi.SysexMessage;

/**
* @author
*/

class MarienbadJvsO_Carre_BramiCoatual {
	void displayTab(int[] t){
		int i = 0;
		System.out.print("{");
		while(i<t.length-1){
			System.out.print(t[i] + ",");
			i=i+1;
		}
		System.out.println(t[i]+"}");
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
		int nombre_de_lignes = 13;

		int valeur_derniere_ligne = nombre_de_lignes*2-1;

		int nbits = (int) (Math.log(valeur_derniere_ligne)/Math.log(2)) + 1;
		System.out.println("nbits = " + nbits);

		int[] tableau_somme = new int[nbits]; // nombre de bits réquis pour coder x lignes

		for (int i = 1; i <= nombre_de_lignes*2; i+=2) {

			System.out.print(i + ": "); displayTab(decimalVersTableauBinaire(i, nbits));


			for (int j = 0; j < nbits; j++) {
				tableau_somme[j] += decimalVersTableauBinaire(i, nbits)[j];
			}
		}

		displayTab(tableau_somme);
	}
}
