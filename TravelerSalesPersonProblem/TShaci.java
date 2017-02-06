import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class TShaci {
	static Stack stackL = new Stack();
	static int indexL = 0;
	static int totVert;
	static int[][] dag;
	static int[][] tpSortFinal;
	// the array mapping vertices to their indegrees (I[])
	static int[] iVert;
	static int rowFinal = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// expect argument
		if (args.length != 1) {
			System.err.println("Enter file as arg");
			System.exit(0);
		}
		// file name as argument
		File text = new File(args[0]);

		// ArrayList to store each line of the input file
		ArrayList<String> inputArray = new ArrayList();
		// read file
		try {
			Scanner scannerInput = new Scanner(text);
			while (scannerInput.hasNextLine()) {
				inputArray.add(scannerInput.nextLine());
			}
		} catch (Exception e) {
			System.out.printf("Exception : %s", e);
		}

		// first line of input is n
		totVert = Integer.parseInt(inputArray.get(0));

		// nxn array to store dag
		dag = new int[totVert][totVert];

		for (int i = 0; i < totVert; i++) {
			String arr[] = (inputArray.get(i + 1)).split(" ");
			System.out.println();
			for (int j = 0; j < totVert; j++) {
				for (int k = 0; k < arr.length; k++) {
					if (!arr[k].equals("NL")) {
						if (Integer.parseInt(arr[k]) == j) {
							dag[i][j] = 1;
							break;
						}
					} else {
						dag[i][j] = 0;
					}
				}
			}
		}

		// print values for debuging
		for (int i = 0; i < totVert; i++) {
			for (int j = 0; j < totVert; j++) {
				System.out.print(dag[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("\n");

		// the array mapping vertices to their indegrees (I[])
		iVert = new int[totVert];

		// first update of iVert[]
		for (int i = 0; i < totVert; i++) {
			for (int j = 0; j < totVert; j++) {
				if (dag[i][j] == 1) {
					iVert[j]++;
				}
			}
		}

		// print iVert for debugging
		for (int i = 0; i < iVert.length; i++) {
			System.out.print(iVert[i] + " ");
		}
		System.out.println();

		// the set of indegree 0 vertices in G (L)
		int[] l_sort = new int[totVert];
	
		// initialize with -1 => meaning empty
		for (int i = 0; i < l_sort.length; i++) {
			l_sort[i] = -1;
		}

		// find vertices with indegree = 0
		int indx = 0;
		for (int i = 0; i < iVert.length; i++) {
			if (iVert[i] == 0) {
				l_sort[indx] = i;
				indx++;
			}
		}
		
		System.out.println();
		// print for debuging
		for (int i = 0; i < l_sort.length; i++) {
			System.out.print(l_sort[i] + " ");
		}
	}
}