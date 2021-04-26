package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class CodiceFiscaleUtil {
	/**
	 * Metodo che permette la genereazione dei 3 caratteri del codice fiscale a
	 * partire dal nome o dal cognome
	 */
	public static String nomeCognome(String input) {
		String output = new String();
		input.toUpperCase();
		int counter=0;
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (ch != 'A' && ch != 'E' && ch != 'I' && ch != 'O' && ch != 'U' && ch!='Y' && counter<3) {
				output = output + ch;
				counter++;
			}
		}
		if (output.length() < 3 && input.length() < 3) {
			while (output.length() != 3) {
				output = output + 'X';
			}
		} else if (output.length() < 3) {
			boolean trovato = false;
			for (int i = 0; i < input.length(); i++) {
				char ch = input.charAt(i);
				if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
					if (trovato == false) {
						output = output + ch;
						trovato = true;
					}
				}
			}
		}
		return output;
	}

	/**
	 * Metodo che permette il calcolo della data di nascita con 2 cifre che
	 * corrispondono all'anno e una lettera per il mese di nascita
	 */
	public static String dataDiNascita(String input) {
		String output = new String();
		String anno = new String();
		String mese = new String();
		anno = input.substring(2, 4);
		if (input.substring(5, 7).equals("01"))
			mese = "A";
		else if (input.substring(5, 7).equals("02"))
			mese = "B";
		else if (input.substring(5, 7).equals("03"))
			mese = "C";
		else if (input.substring(5, 7).equals("04"))
			mese = "D";
		else if (input.substring(5, 7).equals("05"))
			mese = "E";
		else if (input.substring(5, 7).equals("06"))
			mese = "H";
		else if (input.substring(5, 7).equals("07"))
			mese = "L";
		else if (input.substring(5, 7).equals("08"))
			mese = "M";
		else if (input.substring(5, 7).equals("09"))
			mese = "P";
		else if (input.substring(5, 7).equals("10"))
			mese = "R";
		else if (input.substring(5, 7).equals("11"))
			mese = "S";
		else if (input.substring(5, 7).equals("12"))
			mese = "T";
		output = anno + mese;
		return output;
	}

	/**
	 * Metodo che permette il del giorno di nascita con Si prendono le due cifre del
	 * giorno di nascita (se � compreso tra 1 e 9 si pone uno zero come prima
	 * cifra); per i soggetti di sesso femminile, a tale cifra va sommato il numero
	 * 40. In questo modo il campo contiene la doppia informazione giorno di nascita
	 * e sesso. Avremo pertanto la seguente casistica: i maschi avranno il giorno
	 * con cifra da 01 a 31, mentre per le donne la cifra relativa al giorno sar� da
	 * 41 a 71.
	 */
	public static String giornoDiNascita(String input, String sesso) {
		String output = new String();
		String giorno = input.substring(8);
		// conversione in caso di un uomo
		if (sesso.equals("M"))
			output = giorno;
		// conversione in caso di una donna
		else {
			Integer di = Integer.parseInt(giorno);
			di += 40;
			giorno = di.toString();
			output = giorno;
		}
		return output;
	}

	/**
	 * Metodo che data una lista di comuni
	 */
	public static String comuneDiNascita(ArrayList<Comune> comuni, String input) {
		String output = new String();
		// inserisco un boolean perch� mi serve per controllare se il comune che si sta
		// cercando � presente nella lista di comuni
		boolean trovato = false;
		// controllo sulle corrispondenze dei nomi per estrapolare il carattere
		for (int i = 0; i < comuni.size(); i++) {
			if (comuni.get(i).getNome().equals(input)) {
				output = comuni.get(i).getCodice();
				trovato = true;
			}
		}
		if (trovato)
			return output;
		else
			return "Errore comune non presente";
	}
	/**
	 * Metdo di suppoto al metodo per la generazione del codice di controllo
	 */
	public static String codiceSenzaControllo(String cognome, String nome, String dataDiNascita, String giornoDiNascita,
			String comuneDiNascita) {
		return cognome + nome + dataDiNascita + giornoDiNascita + comuneDiNascita;
	}

	/**
	 * Metodo per la creazione del carattere di controllo esso si suddivide in altri
	 * 3 metodi private che sonodi supporto e sono: carattereDiControlloPari,
	 * carattereDiControlloDispari, carattereDiControlloRisultato, che come
	 * suggeriscono i nomi ritornano i primi due dei valori interi da sommare e
	 * dividere per 23 mentre il terzo permette la conversione del modulo della
	 * divisione nel carattere di controllo
	 */
	public static String carattereDiControllo(String input) {
		String output = new String();
		float pari = 0, dispari = 0, risultato = 0;

		for (int i = 0; i < input.length(); i++) {
			// per i caratteri pari
			if (i % 2 == 0)
				dispari = dispari + carattereDiControlloDispari(input.charAt(i));
			// per i caratteri dispari
			else
				pari = pari + carattereDiControlloPari(input.charAt(i));
		}
		System.out.println(pari);
		System.out.println(dispari);
		risultato = (pari + dispari) % 26;
		output = output + carattereDiControlloRisultato(risultato);
		return output;
	}

	private static float carattereDiControlloDispari(char input) {
		int num = 0;
		if (input == '0')
			num = 1;
		else if (input == '1')
			num = 0;
		else if (input == '2')
			num = 5;
		else if (input == '3')
			num = 7;
		else if (input == '4')
			num = 9;
		else if (input == '5')
			num = 13;
		else if (input == '6')
			num = 15;
		else if (input == '7')
			num = 17;
		else if (input == '8')
			num = 19;
		else if (input == '9')
			num = 21;
		else if (input == 'A')
			num = 1;
		else if (input == 'B')
			num = 0;
		else if (input == 'C')
			num = 5;
		else if (input == 'D')
			num = 7;
		else if (input == 'E')
			num = 9;
		else if (input == 'F')
			num = 13;
		else if (input == 'G')
			num = 15;
		else if (input == 'H')
			num = 17;
		else if (input == 'I')
			num = 19;
		else if (input == 'J')
			num = 21;
		else if (input == 'K')
			num = 2;
		else if (input == 'L')
			num = 4;
		else if (input == 'M')
			num = 18;
		else if (input == 'N')
			num = 20;
		else if (input == 'O')
			num = 11;
		else if (input == 'P')
			num = 3;
		else if (input == 'Q')
			num = 6;
		else if (input == 'R')
			num = 8;
		else if (input == 'S')
			num = 12;
		else if (input == 'T')
			num = 14;
		else if (input == 'U')
			num = 16;
		else if (input == 'V')
			num = 10;
		else if (input == 'W')
			num = 22;
		else if (input == 'X')
			num = 25;
		else if (input == 'Y')
			num = 24;
		else if (input == 'Z')
			num = 23;
		return num;
	}

	private static float carattereDiControlloPari(char input) {
		int num = 0;
		if (input == '0')
			num = 0;
		else if (input == '1')
			num = 1;
		else if (input == '2')
			num = 2;
		else if (input == '3')
			num = 3;
		else if (input == '4')
			num = 4;
		else if (input == '5')
			num = 5;
		else if (input == '6')
			num = 6;
		else if (input == '7')
			num = 7;
		else if (input == '8')
			num = 8;
		else if (input == '9')
			num = 9;
		else if (input == 'A')
			num = 0;
		else if (input == 'B')
			num = 1;
		else if (input == 'C')
			num = 2;
		else if (input == 'D')
			num = 3;
		else if (input == 'E')
			num = 4;
		else if (input == 'F')
			num = 5;
		else if (input == 'G')
			num = 6;
		else if (input == 'H')
			num = 7;
		else if (input == 'I')
			num = 8;
		else if (input == 'J')
			num = 9;
		else if (input == 'K')
			num = 10;
		else if (input == 'L')
			num = 11;
		else if (input == 'M')
			num = 12;
		else if (input == 'N')
			num = 13;
		else if (input == 'O')
			num = 14;
		else if (input == 'P')
			num = 15;
		else if (input == 'Q')
			num = 16;
		else if (input == 'R')
			num = 17;
		else if (input == 'S')
			num = 18;
		else if (input == 'T')
			num = 19;
		else if (input == 'U')
			num = 20;
		else if (input == 'V')
			num = 21;
		else if (input == 'W')
			num = 22;
		else if (input == 'X')
			num = 23;
		else if (input == 'Y')
			num = 24;
		else if (input == 'Z')
			num = 25;
		return num;
	}

	private static char carattereDiControlloRisultato(float input) {
		char val = 0;
		if (input == 0)
			val = 'A';
		else if (input == 1)
			val = 'B';
		else if (input == 2)
			val = 'C';
		else if (input == 3)
			val = 'D';
		else if (input == 4)
			val = 'E';
		else if (input == 5)
			val = 'F';
		else if (input == 6)
			val = 'G';
		else if (input == 7)
			val = 'H';
		else if (input == 8)
			val = 'I';
		else if (input == 9)
			val = 'J';
		else if (input == 10)
			val = 'K';
		else if (input == 11)
			val = 'L';
		else if (input == 12)
			val = 'M';
		else if (input == 13)
			val = 'N';
		else if (input == 14)
			val = 'O';
		else if (input == 15)
			val = 'P';
		else if (input == 16)
			val = 'Q';
		else if (input == 17)
			val = 'R';
		else if (input == 18)
			val = 'S';
		else if (input == 19)
			val = 'T';
		else if (input == 20)
			val = 'U';
		else if (input == 21)
			val = 'V';
		else if (input == 22)
			val = 'W';
		else if (input == 23)
			val = 'X';
		else if (input == 24)
			val = 'Y';
		else if (input == 25)
			val = 'Z';
		return val;
	}
}