package Main;

import java.io.File;
import java.util.Scanner;

import Processor.Processor;

import static utilities.Constants.*;
import static utilities.DisplayUtilities.displaySimulatorMenu;
public class ApexSimulator {

	public static void main(String[] args) {
	
	
		if(args.length == 0) {
			System.out.println("Instruction file name absent!!!");
			System.exit(1);
		}

		Scanner scan = new Scanner(System.in);
		File file = new File(args[0]);
		
		while(true) {
			displaySimulatorMenu();
			int option = scan.nextInt();

			switch (option) {
			case INITIALIZE:
				Processor.init(file);
				break;

			case SIMULATE:
	
				System.out.print("Enter number of cycles : ");
				int cycleCount = scan.nextInt();
				Processor.simulate(cycleCount);
				break;

			case DISPLAY:
				//APEXProcessor.displaySimulationResult();
				break;

			case EXIT:
				scan.close();
				System.exit(0);
				break;
			

			default:
				break;
			}
		
	 }
	
	}		
}