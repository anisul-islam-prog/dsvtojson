package dsvtojson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;


public class ToJson {
	
	
	public String lineToJson(String[] headers, String delimiter, String str) {
		String[] tokens = str.split(delimiter);
		Stack<String> processed = new Stack<String>();
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].startsWith("\"")) {
				String tempStr = "";
				for (int j = i; j < tokens.length; j++) {
					if (tokens[j].endsWith("\"")) {
						tempStr += tokens[j];
						break;
					} else {
						tempStr += tokens[i];
						tempStr += delimiter;
						i++;
					}
				}
				processed.push(tempStr);
			} else {
				processed.push(tokens[i]);
			}
		}
		StringBuilder jsonString = new StringBuilder("{}");
		for (int i = headers.length - 1; i >= 0; i--) {
			String value = processed.pop();
			String tempJStr = "";
			if (!value.isEmpty()) {
				if (i == headers.length - 1) {
					tempJStr = String.format("\"%s\": \"%s\"", headers[i], value);
				} else if (i == headers.length - 2) {
					SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
					List<String> sdfformates = new ArrayList<String>();
					sdfformates.add("dd-MM-yyyy");
					sdfformates.add("yyyy/MM/dd");
					sdfformates.add("yyyy-MM-dd");
					Date date = null;
					SimpleDateFormat sdf=null;
			        try {
			            if(value.contains("/"))
			            {
			               sdf  = new SimpleDateFormat("yyyy/MM/dd");

			            }
			            else
			            {
			               sdf  = new SimpleDateFormat("dd-MM-yyyy");

			            }
			            date = sdf.parse(value);
			            
			        } catch (ParseException exp) {
			            exp.printStackTrace();
			        }
					String finalDateString = myFormat.format(date);
					tempJStr = String.format("\"%s\": \"%s\",", headers[i], finalDateString);
				} else {
					tempJStr = String.format("\"%s\": \"%s\",", headers[i], value);
				}
			}
			jsonString.insert(1, tempJStr);
		}
		return jsonString.toString();
	}

	public String ConvertToJsonl(String[] headers, String filePath, String delimiter, String outDir) {
		String outFile = outDir + "\\output.jsonl";
		File file = new File(outFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				file.delete();
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int isHeader = 0;
		try (BufferedReader in = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
			String str;
			while ((str = in.readLine()) != null) {
				if (isHeader == 0) {
					headers = str.split(delimiter);
					isHeader++;
				} else {
//				System.out.println(str);
					String json= this.lineToJson(headers, delimiter, str);
					try (FileWriter writer = new FileWriter(outFile, true);
							BufferedWriter bw = new BufferedWriter(writer)) {

						bw.write(json);
						bw.newLine();

					} catch (IOException e) {
						System.err.format("IOException: %s%n", e);
					}
//					System.out.println("File Created.");
				}
			}
		} catch (IOException e) {
			System.out.println("File Read Error");
			e.printStackTrace();
		}
		return outFile;
	}
}
