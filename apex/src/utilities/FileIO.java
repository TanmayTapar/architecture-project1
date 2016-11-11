package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	public static List<String> loadFile(File file, int programCounter) {
		BufferedReader buffReader = null;
		List<String> instructionList = new ArrayList<>();
		try {
			buffReader = new BufferedReader(new FileReader(file));
			String instruction;
			//intialize
			if(programCounter > 0) {
				for(int i = 0; i < programCounter; i++)
					instructionList.add(i, null);
			}
			while((instruction = buffReader.readLine()) != null) {
				instruction=instruction.replaceAll("#","");
				instruction=instruction.replaceAll(",","");
				System.out.println(instruction);
				
				instructionList.add(programCounter++, instruction);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		
	 {
			try {
				buffReader.close();
			} catch (IOException e) {
				System.out.println("Error in closing Instance!!!");
			}
		}
		return instructionList;
	}
}

