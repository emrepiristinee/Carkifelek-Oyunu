import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		int Countriescapacity = 0;

		File capacityfile = new File("countries.txt");
		Scanner capacityscanner = new Scanner(capacityfile);
		while (capacityscanner.hasNextLine()) { // find capacity the file
			capacityscanner.nextLine();
			Countriescapacity++;
		}
		capacityscanner.close();

		Stack mainStack = new Stack(Countriescapacity);
		Stack tempStack = new Stack(Countriescapacity);
		Stack takenStack = new Stack(Countriescapacity);

		File CountriesFile = new File("countries.txt");
		Scanner CountriesScanner = new Scanner(CountriesFile); // add countries to the mainStack for sort algorithms
		while (CountriesScanner.hasNextLine()) {
			mainStack.push(CountriesScanner.nextLine());
		}
		CountriesScanner.close();

		while (!(mainStack.isEmpty())) {
			String element = (String) mainStack.pop();
			while (!(mainStack.isEmpty())) {
				String element2 = (String) mainStack.peek();
				for (int i = 0; i < element.length(); i++) { // checks all letters
					if (element.charAt(i) > element2.charAt(i)) { // comparing letter of two word
						tempStack.push(mainStack.pop()); // country went to tempStack
						break;
					} else if (element2.contains(element)) {
						tempStack.push(mainStack.pop());
						break;
					} else if (element.contains(element2)) {
						tempStack.push(mainStack.pop());
						break;
					} else if (element.charAt(i) == element2.charAt(i)) // if letters are the same, next letters
						continue;
					else if (element.charAt(i) < element2.charAt(i)) {
						tempStack.push(element); // old country went to tempStack
						element = (String) mainStack.pop(); // new country became new country
						break;
					}
				}
			}
			while (!(tempStack.isEmpty())) {
				mainStack.push(tempStack.pop()); // add all words to mainStack
			}
			takenStack.push(element); // add element to clean stack
		}

		Queue Q1 = new Queue(999);
		Queue Q2 = new Queue(999);
		String NameScorePlayer = "";
		File HighScoreTableFile = new File("HighScoreTable.txt");
		Scanner ScoreScanner = new Scanner(HighScoreTableFile);
		Stack NameStack = new Stack(999);
		Stack ScoreStack = new Stack(999);
		Stack TempNameStack = new Stack(999);
		Stack TempScoreStack = new Stack(999);

		while (ScoreScanner.hasNextLine()) {
			NameScorePlayer = NameScorePlayer + ScoreScanner.nextLine() + " "; // read participants and points in file
		}
		ScoreScanner.close();
		Object[] ArrayNameScorePlayer = (NameScorePlayer).split(" "); // split operation
		for (int i = 0; i < ArrayNameScorePlayer.length; i++) { // places names and points in two different stack
			if (i % 2 == 0) {
				NameStack.push(ArrayNameScorePlayer[i]);
			} else {
				int intValue = Integer.parseInt((String) ArrayNameScorePlayer[i]);
				ScoreStack.push(intValue);
			}
		}

		Stack AlphabetStack = new Stack(26);
		Stack AlphabetTempStack = new Stack(26);
		File AlphabetFile = new File("alphabet.txt");
		Scanner AlphabetScanner = new Scanner(AlphabetFile);
		while (AlphabetScanner.hasNextLine()) {
			AlphabetStack.push(AlphabetScanner.nextLine());
		}
		AlphabetScanner.close();

		Random rand = new Random();
		int CountryRandom = rand.nextInt(Countriescapacity + 1); // create a random number between 0 and 195
		String countryWord = "";
		int CountryCounter = 1; // counter of country
		while (!takenStack.isEmpty()) { // find selected country
			if (CountryCounter == CountryRandom) { // did it reach the number?
				System.out.println("Randomly generated number: " + CountryCounter);
				countryWord = (String) takenStack.pop(); // puts country into string
			}
			takenStack.pop(); // delete country if it is not the country we're looking for
			CountryCounter++; // to find the right place
		}

		for (int i = 0; i < countryWord.length(); i++) {
			Q1.enqueue(countryWord.toUpperCase(Locale.ENGLISH).charAt(i)); // adds the letters into to the queue
		}

		String letter = "";
		int letterCapacity = 26;
		String SearchedWord = "";
		int score = 0, step = 1, scoreMethod = 0;
		Boolean writetoscreen = false;
		int Q1Size = Q1.size();

		System.out.println();
		System.out.print("Word:");
		for (int i = 0; i < Q1Size; i++) { // write Q2 to the screen
			Q2.enqueue("-");
			System.out.print(" " + Q2.peek()); // while array is empty, already "-" all members of array
		}

		System.out.print(" \t Step: " + step + " \tScore: " + score + "\tABCDEFGHIJKLMNOPQRSTUVWXYZ"); // write the
																										// alphabet just
																										// once like
																										// this

		for (int i = 0; i < 26; i++) {
			int LetterRandom = rand.nextInt(letterCapacity); // to chooses random letter
			int LetterCounter = 0;

			while (!AlphabetStack.isEmpty()) { // finds randomly selected letter
				if (LetterCounter == LetterRandom) {
					letter = (String) AlphabetStack.pop();
					if (AlphabetStack.isEmpty()) {
						break;
					}
				}
				AlphabetTempStack.push(AlphabetStack.pop());
				LetterCounter++;
			}
			int howmanyletter = 0;
			for (int j = 0; j < Q1Size; j++) { // is the searched letter in the word?
				SearchedWord = Q1.dequeue().toString();
				Q1.enqueue(SearchedWord.toUpperCase());

				if (SearchedWord.toUpperCase(Locale.ENGLISH).equalsIgnoreCase(letter)) {
					writetoscreen = true;
					for (int l = 0; l < Q1Size; l++) { // compare letter by letter
						if (j == l) {
							Q2.dequeue();
							Q2.enqueue(SearchedWord);
							howmanyletter++;
						} else
							Q2.enqueue(Q2.dequeue());
					}
				}
			}

			if (writetoscreen) {
				System.out.print("\nWheel: ");
				int PointRandom = rand.nextInt(8);
				scoreMethod = PointMethod(PointRandom); // shows which of the score player has earned

				if (scoreMethod == 6) { // if the random number is 6, wins Double Money
					score *= 2;
					System.out.println("Double Money");
				} else if (scoreMethod == 7) { // if the random number is 7, loses all of the money
					score = 0;
					System.out.println("Bankrupt");
				} else {
					if (howmanyletter > 1) {
						System.out.println(scoreMethod);
						scoreMethod = howmanyletter * scoreMethod;
						score += scoreMethod;
					} else {
						System.out.println(scoreMethod);
						score += scoreMethod;
					}
				}

				System.out.print("Guess: " + letter);
				System.out.print("\nWord: ");

				for (int k = 0; k < Q2.size(); k++) {
					System.out.print(Q2.peek() + " ");// write the country on the screen
					Q2.enqueue(Q2.dequeue());
				}

				step++;
				System.out.print("\t Step: " + step + " \tScore: " + score + "\t");

				while (!AlphabetTempStack.isEmpty()) {
					System.out.print(AlphabetTempStack.peek()); // write the alphabet on the screen
					AlphabetStack.push(AlphabetTempStack.pop());
				}
				writetoscreen = false; // checks writing on the screen
			}

			while (!AlphabetTempStack.isEmpty()) {
				AlphabetStack.push(AlphabetTempStack.pop());
			}

			letterCapacity--; // alphabet decreases one letter per turn

			if (letterCapacity == 0) // break, if alphabet is over
				break;
		}

		System.out.println("\n\nYou win $" + score + "\n");

		// FileWriter
		String name = "Emre";
		ScoreStack.push(score);
		NameStack.push(name);

		while (!ScoreStack.isEmpty()) { // High score table sort algorithms
			int tempIntScore = (int) ScoreStack.pop(); // pop out the first element
			String tempStringName = (String) NameStack.pop(); // pop out the first element

			// while temporary stack is not empty and top of stack is greater than temp
			while (!TempScoreStack.isEmpty() && (int) TempScoreStack.peek() > tempIntScore) {
				// pop from temporary stack and push it to the input stack
				ScoreStack.push(TempScoreStack.pop());
				NameStack.push(TempNameStack.pop());
			}
			TempScoreStack.push((int) tempIntScore); // push temp in temporary of stack
			TempNameStack.push(tempStringName); // push temp in temporary of stack
		}

		FileWriter fw = new FileWriter("HighScoreTable.txt"); // writes new High Score Table in .txt
		int size = TempScoreStack.size();
		for (int i = 0; i < size; i++) { // writes new high score table in file
			name = (String) TempNameStack.pop();
			int scoreV2 = (int) TempScoreStack.pop();
			fw.write(name + " " + scoreV2 + "\n");
			System.out.println(name + " " + scoreV2);
		}
		fw.close();

	}

	public static int PointMethod(int rand) { // Score
		int a = 0;
		switch (rand) {
		case 0:
			a = 10;
			break;
		case 1:
			a = 50;
			break;
		case 2:
			a = 100;
			break;
		case 3:
			a = 250;
			break;
		case 4:
			a = 500;
			break;
		case 5:
			a = 1000;
			break;
		case 6:
			a = 6;
			break;
		case 7:
			a = 7;
			break;
		}
		return a;
	}

}
