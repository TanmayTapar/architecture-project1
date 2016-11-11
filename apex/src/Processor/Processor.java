package Processor;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import utilities.FileIO;
import static utilities.Constants.*; 
public class Processor {
	private static Integer PC = 000;
	private static Integer UPDATED_PC = 0;
	private static String[] lastTwoInstructions = new String[2];
	private static LinkedList<String> printQueue = new LinkedList<>();
	private static Queue<String> fetchDecodeLatch = new LinkedList<>();
	private static Map<String, Integer> REGISTER_FILE = new HashMap<>();
	private static Queue<String> decodeExecuteLatch = new LinkedList<>();
	private static Queue<String> executeMemoryLatch = new LinkedList<>();
	private static Queue<String> memoryWriteBackLatch = new LinkedList<>();
	private static List<String> instructionList = new ArrayList<String>(PC);
	private static Integer[] MEMORY_ARRAY = new Integer[4000];
	private static String lastFetchedInstruction;
	private static boolean isFetchDone, isDecodeDone, isExecuteDone, isMemoryDone, isWriteBackDone;
	private static boolean BRANCH_TAKEN = false;
	private static boolean JUMP_DETECTED = false;
	private static boolean INVALID_PC = false;
	private static boolean HALT_ALERT;
	private static boolean ZERO_FLAG;

	public static void init(File file) {
		System.out.println("\nSet PC to 4000");
		PC = 4000;

		instructionList = FileIO.loadFile(file, PC);
		System.out.println(instructionList.get(0));
		lastTwoInstructions[0] = lastTwoInstructions[1] = "";

		System.out.println("Initialize Memory...");
		for(int i = 0; i < MEMORY_ARRAY.length; i++)
			MEMORY_ARRAY[i] = 0;

		System.out.println("Initialize Registers...");
		for(String regName : REGISTER_FILE.keySet())
			REGISTER_FILE.put(regName, 0);

		System.out.println("Reset flags...");
		isFetchDone = isDecodeDone = isExecuteDone = isMemoryDone = isWriteBackDone = false;
		HALT_ALERT = false;
		BRANCH_TAKEN = false;
		JUMP_DETECTED = false;
		ZERO_FLAG = false;
		System.out.println("\nSimulator state intialized successfully");
	}

	public static void simulate(int cycleCount) {
		int cycle = 0;
		LinkedList<String> tempList = new LinkedList<>();
	//	while(cycle != cycleCount)
		{
		/*	if(INVALID_PC || (HALT_ALERT && isFetchDone && isDecodeDone && isExecuteDone && isMemoryDone && isWriteBackDone)) {
				
				break;
			}*/

			//doWriteBack();
			//doMemory();
		//	doExecute();
		//	doDecode();
			System.out.println("here");
			doFetch();

		//	while(!printQueue.isEmpty())
			//	tempList.add(printQueue.removeLast());

			cycle++;

		}
		printQueue.addAll(tempList);

	}
	private static void doFetch() {
		System.out.println("fetch");
		if(isFetchDone) {
			printQueue.add("Done");
		} else {
			if(!fetchDecodeLatch.isEmpty()) {
				printQueue.add(lastFetchedInstruction);
			} else if(PC == instructionList.size()) {
				isFetchDone = true;
				if(!isDecodeDone) {
					printQueue.add(lastFetchedInstruction);
				} else {
					printQueue.add("Done");
				}
			} else {
				if(PC > instructionList.size()) {
					System.out.println("Invalid PC value detected: " + PC);
					INVALID_PC = true;
				} else {
					String instruction = instructionList.get(PC++);
					System.out.println(instruction);
					fetchDecodeLatch.add(instruction);

					if(HALT_ALERT) {
						isFetchDone = true;
						String temp = fetchDecodeLatch.poll();
						instruction = SQUASH_INSTRUCTION + SPACE + temp;
						fetchDecodeLatch.add(instruction);
					}

					if(BRANCH_TAKEN || JUMP_DETECTED) {
						String temp = fetchDecodeLatch.poll();
						instruction = SQUASH_INSTRUCTION + SPACE + temp;
						fetchDecodeLatch.add(instruction);
						PC = UPDATED_PC;
						if(BRANCH_TAKEN)
							BRANCH_TAKEN = false;
						if(JUMP_DETECTED)
							JUMP_DETECTED = false;
					}

					lastFetchedInstruction = instruction;
					printQueue.add(instruction);
				//	System.out.println(printQueue.getLast());
				}
			}
		}
	}

	
}
