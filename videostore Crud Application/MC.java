

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sairam
 * 
 */

public class MC {
	private static final int START_ACTOR = 0; 
	private static final String OUTPUT_FILE_NAME = "./output.txt";
	private static final String INPUT_FILE_NAME = "./input.txt";
	
	private int numOfActor; 
	private Actor[] actorList; //To store available actors
	private float[][]acqMatrix;//Array for acquaint weight; adjacent matrix in graph term
	private int intendedActor;  //Actor who is be invited.
	private InvitationCost[] invitaCost;  //store the cheapest invitation chain
	/*
	*Temp Variables to store data
	*
	*
	*/
	private int currActor=0;  
	private float accumuCost=0; 
	
	public MC() {}
   /*Represent the object actor and its invitation cost. 
   *
   *
   *
   **/
	
	class Actor {
		int name;    
		float cost;  //stores the invitation cost
		boolean invited; 
		public Actor(int i, float cost) {
			this.name =i;
			this.cost = cost;
			this.invited = false;
		}
		public String toString() {
			return name+"|"+cost+"|"+invited;
		}
	}

	/* To track the invitation cost
	*
	*/
	
	class InvitationCost {
		float cost;   //total cost to invite intended actor, may include indirect cost 
		int srcInvite;// indirect invitation source actor
		public InvitationCost(float cost, int srcInvite) {
			this.cost = cost;
			this.srcInvite = srcInvite;
		}
		public String toString() {return srcInvite+"|"+cost;}
	}

	
	/*To read file contents.
	*
	*
	*
	*/
	private void readFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
			
			String tmp;
			List<String> data = new ArrayList<>();
			while ((tmp = br.readLine()) != null) {
				data.add(tmp);
			}

			numOfActor = Integer.parseInt(data.get(0));
			actorList = new Actor[numOfActor];
			tmp = data.get(1);// invitation cost
			String[] in = tmp.split("[ ]");
			for (int i = 0; i < in.length; i++) {
				float cost = Float.parseFloat(in[i]);
				actorList[i] = new Actor(i, cost);
			}
			
			int a = 0;
			acqMatrix = new float[numOfActor][numOfActor];
			for (int i = 2; i < numOfActor+2; i++) {
				tmp = data.get(i);
				String[] wei = tmp.split("[ ]");
				for (int j = 0; j < wei.length; j++) {
					acqMatrix[a][j] = Float.parseFloat(wei[j]);
				}
				a++;
			}
			
			//WARN starts from one (1).
			intendedActor = Integer.parseInt(data.get(numOfActor + 2));
			invitaCost = new InvitationCost[numOfActor];
			
		} catch (IOException e) {
			System.out.println("incorrect data in input file.");
			e.printStackTrace();
		}
	}

	
	 // This method is used to find the minimum cost of the invitation
	private void findMinimumCost(int startActor) {
		
		actorList[startActor].invited = true;
		//construct and initialize all the actors into invitation chain 
		for (int i = 0; i < numOfActor; i++) {
			float startCost = actorList[startActor].cost;
			float degree = acqMatrix[startActor][i];
			float aCost = actorList[i].cost;
			float totalCost = startCost + getiAskCost(aCost, degree);
			invitaCost[i] = new InvitationCost(totalCost, startActor);
		}
		
		int nActor = 1;//start from the second actor, since the initial actor is already considered.
		//find the minimum cost with Dijkstra algorithm
		while (nActor < numOfActor) {
			currActor = getLeastCost();
			accumuCost = invitaCost[currActor].cost;
			
			actorList[currActor].invited = true; //mark actor as invited
			nActor++;
			calculateCost();
		}

		printResult();
		
		
	}
	
	
	 /*
	 *  To output the invitation cost. 
	 *
	 */
	private void printResult () {
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(OUTPUT_FILE_NAME)));) {

			float cost = invitaCost[intendedActor-1].cost;
			String chain;
			if ((intendedActor-1) == START_ACTOR) {
				//for direct invitation
				chain = intendedActor + ""; 
			} else { 
	           //for indirect invitation
				chain = getInviteChain(intendedActor-1);
			}

			bw.write(Float.toString(cost));
			bw.newLine();
			bw.write(chain);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getInviteChain(int intendActor) {
		
		if (intendActor == START_ACTOR) {
			return (START_ACTOR+1)+""; //add 1 to convert to 1 based naming
		} else {
			int src = invitaCost[intendActor].srcInvite;
			return getInviteChain(src) + " " + (intendActor+1);
		}
		
	}
	private void calculateCost() {
		int otherActor =1;
		while (otherActor < numOfActor) {
			if (!actorList[otherActor].invited) {
				
				float degree = acqMatrix[currActor][otherActor];
				float oCost = actorList[otherActor].cost;
				float iaskOtherCost = getiAskCost(oCost, degree) + accumuCost;
				
				float otherCurrCost = invitaCost[otherActor].cost;
				
				if (iaskOtherCost < otherCurrCost) {
					invitaCost[otherActor].srcInvite = currActor;
					invitaCost[otherActor].cost = iaskOtherCost;
				}
			}
			
			otherActor++;
		}
	}
	private float getiAskCost(float init, float acq) {
		return init * (1 - acq);
	}

	 
	 
	 /*To find the minimum cost require to invite the actor and which actor should be invited next
	 *
	 *
	 */
	private int getLeastCost() {
		float minCost = Float.POSITIVE_INFINITY;
		int actorIdx = 0;
		for (int i = 0; i < numOfActor; i++) {
			if (!actorList[i].invited && invitaCost[i].cost < minCost) {
				minCost = invitaCost[i].cost;
				actorIdx = i;
			}
		} 
		return actorIdx;
	}
	
	private void printMatrix(float[][] data) {		
		for (float[] fs : data) {
			for (float f : fs) {
				System.out.print("\t"+f +" ");
			}
			System.out.println();
		}
	}
	
	private void invitaActor(String fileName) {
		readFile(fileName);
		findMinimumCost(START_ACTOR);
	}
	
	
	public static void main(String[] args) {
		MC run = new MC();
		try {
			if (args.length == 1) {
				run.invitaActor(args[0]);
			} else {
				run.invitaActor(INPUT_FILE_NAME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
