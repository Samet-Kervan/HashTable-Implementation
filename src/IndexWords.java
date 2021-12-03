import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class IndexWords {
	private final String DELIMITERS = "[-+=" +

		        " " +        //space

		        "\r\n " +    //carriage return line fit

				"1234567890" + //numbers

				"’'\"" +       // apostrophe

				"(){}<>\\[\\]" + // brackets

				":" +        // colon

				"," +        // comma

				"‒–—―" +     // dashes

				"…" +        // ellipsis

				"!" +        // exclamation mark

				"." +        // full stop/period

				"«»" +       // guillemets

				"-‐" +       // hyphen

				"?" +        // question mark

				"‘’“”" +     // quotation marks

				";" +        // semicolon

				"/" +        // slash/stroke

				"⁄" +        // solidus

				"␠" +        // space?   

				"·" +        // interpunct

				"&" +        // ampersand

				"@" +        // at sign

				"*" +        // asterisk

				"\\" +       // backslash

				"•" +        // bullet

				"^" +        // caret

				"¤¢$€£¥₩₪" + // currency

				"†‡" +       // dagger

				"°" +        // degree

				"¡" +        // inverted exclamation point

				"¿" +        // inverted question mark

				"¬" +        // negation

				"#" +        // number sign (hashtag)

				"№" +        // numero sign ()

				"%‰‱" +      // percent and related signs

				"¶" +        // pilcrow

				"′" +        // prime

				"§" +        // section sign

				"~" +        // tilde/swung dash

				"¨" +        // umlaut/diaeresis

				"_" +        // underscore/understrike

				"|¦" +       // vertical/pipe/broken bar

				"⁂" +        // asterism

				"☞" +        // index/fist

				"∴" +        // therefore sign

				"‽" +        // interrobang

				"※" +          // reference mark

		        "]";
	private String readFileName, searchFileName;
	private DIBHashTable<String,String> table;
	public void start() {
		readFileName = "story.txt";
		searchFileName = "search.txt";
		Scanner sc = new Scanner(System.in);
		Double loadFactor = 0.00;
		System.out.println("What load factor do you want to use?\n A: 0.5	B: 0.7");
		while (true) {
			System.out.print("Option: ");
			String input = sc.nextLine();
			input = input.toLowerCase();
			if (input.equals("a")) {
				loadFactor = 0.5;
				break;
			}
			else if (input.equals("b")) {
				loadFactor = 0.7;
				break;
			}
			else {
				System.out.println();
				System.out.println("Please enter 'A' or 'B' ");
			}
		}
		while(true) {
			System.out.println();
			System.out.println("What hash function do you want to use? A:PAF   B:YHF");
			System.out.print("Option: ");
			String input = sc.nextLine();
			input = input.toLowerCase();
			if (input.equals("a")) {
				table = new DIBHashTablePAF<String,String>(loadFactor);
				break;
			}
			else if (input.equals("b")) {
				table = new DIBHashTableYHF<String,String>(loadFactor);
				break;
			}
			else {
				System.out.println("Please enter A or B.");
			}
		}
		long indexTimeStart = System.currentTimeMillis();
		setTable();
		long indexTimeEnd = System.currentTimeMillis();
		search();
		sc.close();
		System.out.println("Indexing  time is " + (indexTimeEnd - indexTimeStart) + (" (time in millisecons)"));
	}
	private void setTable() {//Reads the file and inserts it word by word in the table
		try {
			File file = new File(readFileName);
			Scanner reader = new Scanner(file);
			String inputStream;
			while(reader.hasNextLine()) {
				inputStream = reader.nextLine();
				inputStream = inputStream.toLowerCase(Locale.ENGLISH);
				String[] words = inputStream.split(DELIMITERS);
				for (int i = 0; i < words.length; i++) {
					if (!words[i].equals("")) {
						table.put(words[i], words[i]);//Both key and value are word that has been read
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {//If the file does not exists
			System.out.println("Files are not complete. Try again with all files intact");
			System.exit(1);
		}
	}
	private void search() {//Reads the file and searches its content word by word in table.
							//Than prints searched words integer key value, current index,
							//count value and time spent for searching this word.
		try {
			File file = new File(searchFileName);
			Scanner reader = new Scanner(file);
			String wordForSearch;
			long minTime = 100000000, maxTime = 0, sumTime = 0;
			int searchedWords = 0;
			System.out.println();
			while(reader.hasNextLine()) {
				wordForSearch = reader.nextLine();
				wordForSearch = wordForSearch.toLowerCase(Locale.ENGLISH);
				if (!wordForSearch.equals("")) {
					Object[] values = table.valueGet(wordForSearch);
					searchedWords++;
					sumTime += (long) values[4];
					if ((long) values[4] > maxTime) {
						maxTime = (long) values[4];
					}
					if ((long) values[4] < minTime) {
						minTime = (long) values[4];
					}
					if (values[3] != null) {
						System.out.println("Search: " + values[0] + "\nKey: " + values[1] + "\nCount: " +
								values[2] + "\nIndex: " + values[3] + "\nTime for search: " + values[4] +("\n"));
					}
					else {
						System.out.println("Search: " + values[0] + "\nKey: " + values[1] + "\nCount: " +
								values[2] + "\nIndex: Does not exists in the table\n");
					}
				}
			}
			long averageTime = sumTime / searchedWords;
			System.out.println("Average time: " + averageTime + "\nMinimum time: " + minTime + 
								"\nMaximum time: " + maxTime + "\n(Times in nano seconds)");
			System.out.println("Total collision: " + table.getCollisionCount());
			System.out.println("Total elements: " + table.n);
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("search.txt does not exist.");
			System.exit(1);
		}
	}
}
