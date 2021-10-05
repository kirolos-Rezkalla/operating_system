import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class OS_Milestone2 {
	
	// milestone 2
	// This is our main memory
	public static memoryCell[] memory = new memoryCell[300];
	public static int pId;
	public static QueueObj processes = new QueueObj(3);
	
	public static void populateMemory(String s){
		if(s.equals("Program 1")){
			pId=1;
			memoryCell cell95 = new memoryCell("Process Id", pId);
			memory[95]=cell95;
			
			memoryCell cell96 = new memoryCell("Process State", "Not Running");
			memory[96]=cell96;
			
			memoryCell cell97 = new memoryCell("PC", 0);
			memory[97]=cell97;
		
			memoryCell cell98 = new memoryCell("Min", 0);
			memory[98]=cell98;
			
			memoryCell cell99 = new memoryCell("Max", 99);
			memory[99]=cell99;
		}
		else if(s.equals("Program 2")){
			pId=2;
			memoryCell cell195 = new memoryCell("Process Id", pId);
			memory[195]=cell195;
			
			memoryCell cell196 = new memoryCell("Process State", "Not Running");
			memory[196]=cell196;
			
			memoryCell cell197 = new memoryCell("PC", 100);
			memory[197]=cell197;
		
			memoryCell cell198 = new memoryCell("Min", 100);
			memory[198]=cell198;
			
			memoryCell cell199 = new memoryCell("Max", 199);
			memory[199]=cell199;
		}
		else{ 
			pId=3;
			memoryCell cell295 = new memoryCell("Process Id", pId);
			memory[295]=cell295;
			
			memoryCell cell296 = new memoryCell("Process State", "Not Running");
			memory[296]=cell296;
			
			memoryCell cell297 = new memoryCell("PC", 200);
			memory[297]=cell297;
		
			memoryCell cell298 = new memoryCell("Min", 200);
			memory[298]=cell298;
			
			memoryCell cell299 = new memoryCell("Max", 299);
			memory[299]=cell299;
		}
	}
	
	public static void fetchInstructions(String s) throws IOException{
		
		File file = new File("src\\"+s+".txt");
	
		BufferedReader br = new BufferedReader(new FileReader(file));
		  
		String st;
		while ((st = br.readLine()) != null){
			insertIntoMemory2("",st);
			}
		}
		
	//This is the System Call for inserting data into the memory:
	public static void insertIntoMemory2(Object variable, String data){
		memoryCell cell = new memoryCell(variable, data);
		System.out.println();
		System.out.println("This is the printing for inserting new data in memory: ");
		if(variable.equals("")){
			int start = (pId*100)-100 ;
			int end = (pId*100) - 50;
			
			for(int i=start;i<end;i++){
				if(memory[i]==null){
					memory[i] = cell;
					System.out.println("Index = "+i+" Word inserted in this memory cell: " + memory[i].data);
					break;
				}
			}
		}
		else{
			int start = (pId*100) - 50;
			int end = (pId*100) - 5;
			
			for(int i=start;i<end;i++){
				if(memory[i]==null){
					memory[i] = cell;
					System.out.println("Index = "+i+" Word inserted in this memory cell: "+memory[i].variable+" = "+memory[i].data);
					break;
				}
			}
		}
		
		
	
	}
	
	//This is the Interpreter:
	public static void parser(String s){
		ArrayList<String> arr = new ArrayList<String>();
		String[] line = s.split(" ");
		for(int i=0; i<line.length;i++){
			arr.add(line[i]);
		}
		try {
			readInstruction(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//This is the process that will call the System Calls:
	public static void readInstruction(ArrayList<String> arr) throws IOException{
		int i=0;
			switch(arr.get(i)){
			  case "print":
				  try {
					print(arr.get(i+1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  break;
			  case  "assign":
				  if(arr.get(i+2).equals("input")){
					  String  input = input();
					  assign(arr.get(i+1),input);
					  break;
				  }
				  else if(arr.get(i+2).equals("readFile")){
					  String read_file = readFile(arr.get(i+3));
					  assign(arr.get(i+1),read_file);
					  break;
				  }
				  else{
					  assign(arr.get(i+1),arr.get(i+2));
					  break;
				  }
			  case  "add":
				  add(arr.get(i+1),arr.get(i+2));  
				  break;
			  case  "writeFile":
				  writeFile(arr.get(i+1),arr.get(i+2));
				  break;
			  case "readFile":
				  readFile(arr.get(i+1));
				  break;
			  default:
				  	}
		}
	
	
	//These are the System Calls of the OS:
	public static String input(){
		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream 
		String str = sc.nextLine(); 
		return str;
	}
	
	public static void print(String x) throws IOException{
		String returnX = readFromMemory2(x);
		if(!returnX.equals("Data does not exist in memory!")){
			System.out.println(returnX);
		}
		else{
			System.out.println(x);
		}
		
	}
	
	public static String readFile(String x) throws IOException{
		String value="";
		String str = "";
		Boolean flag=false;
		String returnX = readFromMemory2(x);
		if(!returnX.equals("Data does not exist in memory!")){
			value = returnX;
			flag = true;
		}
		else{
			System.out.println("File does not exist!!");
		}
		if(flag == true){
			File file = new File("src\\"+value+".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null){
				str= str + line + "\n";
			}
			}
		return str;
		}
		
	public static void writeFile(String x,String y) throws IOException{
		String returnX = readFromMemory2(x);
		String returnY = readFromMemory2(y);
		if(!returnX.equals("Data does not exist in memory!")){
			FileWriter myWriter = new FileWriter("src\\"+returnX+".txt");
		      myWriter.write(returnY);
		      myWriter.close();
		      System.out.println("Data inserted successfully!");
		}
		
		else{
			System.out.println("Please enter file name");
			String input = input();
			assign(x, input);
			FileWriter myWriter = new FileWriter("src\\"+input+".txt");
		    myWriter.write(returnY);
		    myWriter.close();
		    System.out.println("Data inserted successfully!");
		}
		}
		
	public static void add(String x, String y){
		String newValue="";
		int valueX = 0;
		int valueY = 0;
		String returnX = readFromMemory2(x);
		String returnY = readFromMemory2(y);
		if(!returnX.equals("Data does not exist in memory!") && !returnY.equals("Data does not exist in memory!")){
			valueX = Integer.parseInt(returnX);
			valueY = Integer.parseInt(returnY);
			int newValueInt = valueX + valueY;
			newValue = newValueInt+"";
			replaceDataInMemory2(x, newValue);
		}
		else{
			System.out.println("Operation cannot be executed!");
		}
	}
	
	public static void assign(String x, String y){
		insertIntoMemory2(x,y);
	}
	
	//This is the System Call for replacing data variables and PCB in the memory:
	public static void replaceDataInMemory2(Object x, String y){
		int start = (pId*100) - 50;
		int end = (pId*100);
		for(int i=start;i<end;i++){
			if(memory[i]!=null){
				if(memory[i].variable.equals(x) ){
					memory[i].data = y;
					System.out.println("Index = "+i+" Word replaced in this memory cell: "+memory[i].variable+" = "+memory[i].data);
				}
			}
		}
	}
	
	//This is the System Call for reading instruction lines from the memory:
	public static String readFromMemory(int x){
		System.out.println();
		System.out.println("This is the printing for reading instructions from the memory:");
		
		if(memory[x] != null){
			System.out.println("Index = "+x+" Instruction to be parsed: "+memory[x].data);
		  return (String) memory[x].data;
		}
		else{
			return "No instruction found!";
			}
	}
	
	//This is the System Call for reading data variables and PCB from the memory:
	public static String readFromMemory2(Object x){
		String returnValue ="";
		boolean flag = false;
		int start = (pId*100) - 50;
		int end = (pId*100);
		System.out.println();
		System.out.println("This is the printing for reading data from the memory:");
		
		for(int i=start;i<end;i++){
			if(memory[i]!=null){
				if(memory[i].variable.equals(x)){
					flag=true;
					returnValue = returnValue + memory[i].data;
					System.out.println("Index = "+i+" Word read from memory cell: "+memory[i].variable+" = "+memory[i].data);
				}
			}
		}
		if(flag == true){
			return returnValue;
		}
		else{
			returnValue = "Data does not exist in memory!";
			return returnValue;
		}
		}
	
	public static boolean isPopulated(String process){
		int id=0;
		if(process.equals("Program 1")){
			id=1;
		}
		else if(process.equals("Program 2")){
			id=2;
		}
		else{
			id=3;
		}
		int indexInMemory = (id*100) - 5;	
		if(memory[indexInMemory]==null)
			return false;
		else 
			return true;
	}
	
	public static void scheduler(){
		while(!processes.isEmpty()){
			processes.printQueue();
			String process = (String) processes.dequeue();
			if(process.equals("Program 1")){
				pId=1;
			}
			else if(process.equals("Program 2")){
				pId=2;
			}
			else{
				pId=3;
			}
			System.out.println();
			System.out.println("Process ID chosen by the scheduler to run:");
			System.out.println("ID = "+pId);
			System.out.println();
			if(!isPopulated(process)){
			  populateMemory(process);
			  //replaceDataInMemory2("Process State", "Running");
			  try {
					fetchInstructions(process);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  int start = (pId*100) - 100;
			  int end = (pId*100);
			  System.out.println();
			  System.out.println("Memory..");
			  System.out.println();
			  for(int i=start;i<end;i++){
				  
				  if(memory[i] != null){
					  String result="";
					  if(memory[i].equals("")){
						  result+=memory[i].data;
					  }
					  else{
						  result = memory[i].variable + " = "+memory[i].data;
					  }
					  System.out.println("Index = "+i+" Data in memory cell "+result);
				  }
			  }
			  System.out.println("..........");
			  System.out.println();
			}
			replaceDataInMemory2("Process State", "Running");
			int pc=Integer.parseInt(readFromMemory2("PC"));
			boolean flag = true; 
			int newPC=pc;
			int quanta=0;
			for(int i=pc;i<pc+2;i++){
				String instruction = readFromMemory(i);
				if(!instruction.equals("No instruction found!")){
					parser(instruction);
					quanta++;
				    newPC += 1;
					replaceDataInMemory2("PC", newPC + "");
				}
				else{
					flag=false;
					replaceDataInMemory2("Process State", "Not Running");
					break;
				}
			}
			System.out.println();
			System.out.println("Process ID finished running:");
			System.out.println("ID = "+pId);
			System.out.println("Process with ID = "+pId+ " took " +quanta+" quanta");
			System.out.println();
			if(flag){
				if(!readFromMemory(pc+2).equals("No instruction found!")){
					processes.enqueue(process);
					replaceDataInMemory2("Process State", "Not Running");
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		processes.enqueue("Program 1");
		processes.enqueue("Program 2");
		processes.enqueue("Program 3");
		scheduler();
	
	}
}
