package dsvtojson;

import java.util.Scanner;

public class DsvToJson {

	private static String[] headers;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "";
		String delimiter = "";
		String outDir = "";
		if (args.length == 0 || args == null) {
			System.out.println("No arguements are passed. Please Give the following inputs.");
			Scanner sc = new Scanner(System.in);
			System.out.println("Please Enter the file with location:");
			filePath = sc.nextLine();
			System.out.println("Please Enter the delimiter:");
			delimiter = sc.nextLine();
			System.out.println("Please Enter the output directory:");
			outDir = sc.nextLine();
			sc.close();
		} else {
			if (!(args == null) && args.length < 4) {
				filePath = args[0];
				delimiter = args[1];
				outDir = args[2];
			} else {
				System.out.println("One or more of the three arguments are missing");
				System.exit(0);
			}

		}
//		System.out.println(filePath);
//		System.out.println(delimiter);
//		System.out.println(outDir);
		String outDir2 = outDir.replace("\"", "");
		ToJson tj = new ToJson();
		String finalOutDir = tj.ConvertToJson(headers, filePath, delimiter, outDir2);
		System.out.println(finalOutDir);
//		for (String s : headers) {
//			System.out.println(s);
//		}

	}

}
