package sap;


import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sap.Hint.Beverage;
import sap.Hint.Cigar;
import sap.Hint.Color;
import sap.Hint.HousePosition;
import sap.Hint.Nationality;
import sap.Hint.Pet;


/**
 * 
 * @author sunny
 *
 *  all characteristics have 1:1 mapping 
 *  need to find a mapping which contains Pet.FISH
 *  Total Possible Combinations= 5!^5= 120*120*120*120*120
 *  So, not doing superset expansion
 *  Used brute force algo. with short-circuit conditions
 */
public class NewtonRiddle {
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);// i tried for 40 on cloud for un-optimized code
	private static Hint hint;
	
	public NewtonRiddle(){
		hint = new Hint();
	}
	
	public static void main(String[] args) {
		NewtonRiddle newtonRiddle= new NewtonRiddle();
		System.out.println(newtonRiddle.getFishOwner());
		threadPool.shutdown();
	}

	/**
	 *  
	 * @return Nationality of Person who own fish
	 */
	public Nationality getFishOwner(){
		List<Set<Person>>  allPossibleTableList= new ArrayList<>();
		// generate 5^6 combinations
		for(HousePosition housePosition: HousePosition.values()){
			Set<Person> personList = new HashSet<>();
			for(Color color: Color.values()){
				for(Nationality nationality: Nationality.values()){
					for(Beverage beverage: Beverage.values()){
						for(Cigar cigar: Cigar.values()){
							for(Pet pet: Pet.values()){
								if(checkIfOkForDiectRules(housePosition, color, nationality, beverage, cigar, pet)){
									Person p= new Person(housePosition, color, nationality, beverage, cigar, pet);
									if(checkIfOKForNeighbourRules(p)){
										personList.add(p);
									}
								}
							}
						}
					}
				}
			}
			allPossibleTableList.add(personList);
		}
		Person person= checkForNeighboursAndGetPerson(allPossibleTableList);
		return person.getNationality();
	}
	
	// 1.the man who smokes blends lives next to the one who keeps cats
	// 2. the man who smokes blend has a neighbor who drinks water
	// 3. the man who keeps horses lives next to the man who smokes Dunhill
	private boolean checkIfOKForNeighbourRules(Person p) {
		if ((p.getCigar()==Cigar.BLENDS && (p.getPet()== Pet.CATS || p.getBeverage()== Beverage.WATER)) ||
				(p.getCigar()==Cigar.DUNHILL && p.getPet()== Pet.HORSES)){
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param allPossibleTableList after performing several conditions
	 * @return Person which owns Fish
	 */
	private Person checkForNeighboursAndGetPerson(List<Set<Person>> allPossibleTableList) {
		
		List<Future<List<Person>>> futureList= new ArrayList<>();
		// use multiple thread to use more cores >40
		for(Person p0: allPossibleTableList.get(0)){
			Future<List<Person>> future= threadPool.submit(new Callable<List<Person>>() {
				@Override
				public List<Person> call() {
					return checkForNeighbours(p0 ,allPossibleTableList);
				}});
			
			futureList.add(future);
		}
		for(Future<List<Person>> future: futureList){
			try {
				List<Person> table= future.get();
				if(table!=null){
					return getAppropriatePerson(table);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	private Person getAppropriatePerson(List<Person> table) {
		for(Person p: table){
			if(p.getPet()== Pet.FISH){
				return p;
			}
		}
		return null;
	}

	private List<Person> checkForNeighbours(Person p0 , List<Set<Person>> allPossibleTableList){
		
		for(Person p1: allPossibleTableList.get(1)){
			for(Person p2: allPossibleTableList.get(2)){
				for(Person p3: allPossibleTableList.get(3)){
					for(Person p4: allPossibleTableList.get(4)){
						if(checkIfUnique(p0, p1, p2, p3, p4) && checkIfNeighBoursOK(p0, p1, p2, p3, p4)){
							return Arrays.asList(p0, p1, p2, p3, p4);
						}
					}
				}
			}
		}
		return null;
	}

	private boolean checkIfUnique(Person p0, Person p1, Person p2, Person p3,
			Person p4) {
		return p0.uniqueCheck(p1) && p0.uniqueCheck(p2) && p0.uniqueCheck(p3) && p0.uniqueCheck(p4)
				&& p1.uniqueCheck(p2) && p1.uniqueCheck(p3) && p1.uniqueCheck(p4)
				&& p2.uniqueCheck(p3) && p2.uniqueCheck(p4) 
				&& p3.uniqueCheck(p4);
	}

	private boolean checkIfNeighBoursOK(Person p0, Person p1, Person p2,
			Person p3, Person p4) {
		return hint.greenWhiteHouseCheck(p0, p1, p2, p3, p4) && hint.catBlendsCheck(p0, p1, p2, p3, p4) && 
				hint.horseDunhillCheck(p0, p1, p2, p3, p4) && hint.norwegianColorCheck(p0, p1, p2, p3, p4)  && 
				hint.blendsWaterCheck(p0, p1, p2, p3, p4);
	}
	
	
	/**
	 * 
	 * @param givenHints
	 * @param housePosition
	 * @param color
	 * @param nationality
	 * @param beverage
	 * @param cigar
	 * @param pet
	 * @return
	 */
	private boolean checkIfOkForDiectRules(HousePosition housePosition, 
			Color color, Nationality nationality, Beverage beverage,
				Cigar cigar, Pet pet){
		return checkIfOk(hint.housePositionMap.get(housePosition), housePosition, 
				color, nationality, beverage, cigar, pet) && checkIfOk(hint.colorMap.get(color), housePosition, 
						color, nationality, beverage, cigar, pet) && checkIfOk(hint.nationalityMap.get(nationality), housePosition, 
								color, nationality, beverage, cigar, pet) && checkIfOk(hint.beverageMap.get(beverage), housePosition, 
										color, nationality, beverage, cigar, pet) && checkIfOk(hint.cigarMap.get(cigar), housePosition, 
												color, nationality, beverage, cigar, pet) && checkIfOk(hint.petMap.get(pet), housePosition, 
														color, nationality, beverage, cigar, pet);
		
		
	}
	
	/**
	 * 
	 * @param obj
	 * @param housePosition
	 * @param color
	 * @param nationality
	 * @param beverage
	 * @param cigar
	 * @param pet
	 * @return
	 */
	private boolean checkIfOk(Object obj, HousePosition housePosition, 
			Color color, Nationality nationality, Beverage beverage,
			Cigar cigar, Pet pet){
		if(obj != null){
			if(obj instanceof HousePosition){
				if(!housePosition.equals(obj)){
					return false;
				}
			}
			if(obj instanceof Color){
				if(!color.equals(obj)){
					return false;
				}
			}
			if(obj instanceof Nationality){
				if(!nationality.equals(obj)){
					return false;
				}
			}
			if(obj instanceof Beverage){
				if(!beverage.equals(obj)){
					return false;
				}
			}
			if(obj instanceof Cigar){
				if(!cigar.equals(obj)){
					return false;
				}
			}
			if(obj instanceof Pet){
				if(!pet.equals(obj)){
					return false;
				}
			}
		}
		return true;
	}

}