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

	int[] decimalVersTableauBinaire(int number) {
		int[] puissances_de_deux = {4, 2, 1};
		int[] result = new int[puissances_de_deux.length];

		int i = 0;
		while (number != 0) {
			if (number >= puissances_de_deux[i]) {
				number -= puissances_de_deux[i];
				result[i] = 1;
			} else {
				result[i] = 0;
			}

			i++;
		}

		return result;
	}

	void principal() {
		int nombre_de_colones = 4;
		int[] tableau_somme = new int[3]; // nombre de puissances de deux

		for (int i = 1; i < 2*nombre_de_colones; i+=2) {
			for (int j = 0; j < decimalVersTableauBinaire(i).length; j++) {
				tableau_somme[j] += decimalVersTableauBinaire(i)[j];
			}
		}

		displayTab(tableau_somme);

	}
}
