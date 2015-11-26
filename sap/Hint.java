package sap;


import java.util.*;

/**
 *  Hint class is rules repository
 *  Enum classes are also defined inside it-can be made outer class since used in other class also
 * @author sunny
 *
 */

public class Hint {
	
	Map<HousePosition, Object>  housePositionMap= new EnumMap<>(HousePosition.class);
	Map<Color, Object>  colorMap= new EnumMap<>(Color.class);
	Map<Nationality, Object>  nationalityMap= new EnumMap<>(Nationality.class);
	Map<Beverage, Object>  beverageMap= new EnumMap<>(Beverage.class);
	Map<Cigar, Object>  cigarMap= new EnumMap<>(Cigar.class);
	Map<Pet, Object>  petMap= new EnumMap<>(Pet.class);

	
	public Hint(){
		//1. the Brit lives in the red house
		colorMap.put(Color.RED, Nationality.BRIT);
		nationalityMap.put(Nationality.BRIT, Color.RED);
		//2. the Swede keeps dogs as pets
		nationalityMap.put(Nationality.SWEDE, Pet.DOGS);
		petMap.put(Pet.DOGS, Nationality.SWEDE);
		//3. the Dane drinks tea
		nationalityMap.put(Nationality.DANE, Beverage.TEA);
		beverageMap.put(Beverage.TEA, Nationality.DANE);
		//4. the green house's owner drinks coffee
		colorMap.put(Color.GREEN, Beverage.COFFEE);
		beverageMap.put(Beverage.COFFEE, Color.GREEN);
		//5. the person who smokes Pall Mall rears birds
		cigarMap.put(Cigar.PALLMALL, Pet.BIRDS);
		petMap.put(Pet.BIRDS, Cigar.PALLMALL);
		//6. the owner of the yellow house smokes Dunhill
		colorMap.put(Color.YELLOW, Cigar.DUNHILL);
		cigarMap.put(Cigar.DUNHILL, Color.YELLOW);
		//7. the man living in the center house drinks milk
		housePositionMap.put(HousePosition.THREE, Beverage.MILK);
		beverageMap.put(Beverage.MILK, HousePosition.THREE);
		//8. the Norwegian lives in the first house
		nationalityMap.put(Nationality.NORWEGIAN, HousePosition.ONE);
		housePositionMap.put(HousePosition.ONE, Nationality.NORWEGIAN);
		//9. the owner who smokes BlueMaster drinks beer
		cigarMap.put(Cigar.BLUEMASTER, Beverage.BEER);
		beverageMap.put(Beverage.BEER, Cigar.BLUEMASTER);
		//10. the German smokes Prince
		nationalityMap.put(Nationality.GERMAN, Cigar.PRINCE);
		cigarMap.put(Cigar.PRINCE, Nationality.GERMAN);
		//11. the Norwegian lives next to the blue house
		housePositionMap.put(HousePosition.TWO, Color.BLUE);
		colorMap.put(Color.BLUE, HousePosition.TWO);
	}
	
	// neighbour rules
	

	/**
	 * 
	 * //1. the green house is on the left of the white house
	 * //colorMap.put(Color.GREEN, Color.WHITE.LEFT);
	 * 
	 */
	boolean greenWhiteHouseCheck(Person p0, Person p1, Person p2,
			Person p3, Person p4){
	
		if(p0.getColor()==Color.WHITE){
			return false;
		}
		if(p1.getColor()==Color.WHITE){
			if(p0.getColor()!=Color.GREEN){
				return false;
			}
		}
		if(p2.getColor()==Color.WHITE){
			if(p1.getColor()!=Color.GREEN){
				return false;
			}
		}
		if(p3.getColor()==Color.WHITE){
			if(p2.getColor()!=Color.GREEN){
				return false;
			}
		}
		if(p4.getColor()==Color.WHITE){
			if(p3.getColor()!=Color.GREEN){
				return false;
			}
		}
		return true;
	}

	//2. the man who smokes blends lives next to the one who keeps cats
	//	petMap.put(Pet.CATS.NEXT, Cigar.BLENDS);
	boolean catBlendsCheck(Person p0, Person p1, Person p2,
			Person p3, Person p4){
		if(p0.getCigar()== Cigar.BLENDS){
			if(p1.getPet()!= Pet.CATS){
				return false;
			}
		}
		if(p1.getCigar()== Cigar.BLENDS){
			if(p0.getPet()!= Pet.CATS && p2.getPet()!= Pet.CATS){
				return false;
			}
		}
		if(p2.getCigar()== Cigar.BLENDS){
			if(p1.getPet()!= Pet.CATS && p3.getPet()!= Pet.CATS){
				return false;
			}
		}
		if(p3.getCigar()== Cigar.BLENDS){
			if(p2.getPet()!= Pet.CATS && p4.getPet()!= Pet.CATS){
				return false;
			}
		}
		if(p4.getCigar()== Cigar.BLENDS){
			if(p3.getPet()!= Pet.CATS){
				return false;
			}
		}
		return true;
	}
	
	//3. the man who keeps horses lives next to the man who smokes Dunhill
	//cigarMap.put( Cigar.DUNHILL.NEXT, Pet.HORSES);
	boolean horseDunhillCheck(Person p0, Person p1, Person p2,
			Person p3, Person p4){
	
		if(p0.getCigar()== Cigar.DUNHILL){
			if(p1.getPet()!= Pet.HORSES){
				return false;
			}
		}
		if(p1.getCigar()== Cigar.DUNHILL){
			if(p0.getPet()!= Pet.HORSES && p2.getPet()!= Pet.HORSES){
				return false;
			}
		}
		if(p2.getCigar()== Cigar.DUNHILL){
			if(p1.getPet()!= Pet.HORSES && p3.getPet()!= Pet.HORSES){
				return false;
			}
		}
		if(p3.getCigar()== Cigar.DUNHILL){
			if(p2.getPet()!= Pet.HORSES && p4.getPet()!= Pet.HORSES){
				return false;
			}
		}
		if(p4.getCigar()== Cigar.DUNHILL){
			if(p3.getPet()!= Pet.HORSES){
				return false;
			}
		}
		return true;
	}
	
	// 4. the Norwegian lives next to the blue house
	//	nationalityMap.put(Nationality.NORWEGIAN.NEXT, Color.BLUE);
	boolean norwegianColorCheck(Person p0, Person p1, Person p2,
			Person p3, Person p4){
	
		if(p0.getColor()== Color.BLUE){
			if(p1.getNationality()!= Nationality.NORWEGIAN){
				return false;
			}
		}
		if(p1.getColor()== Color.BLUE){
			if(p0.getNationality()!= Nationality.NORWEGIAN && p2.getNationality()!= Nationality.NORWEGIAN){
				return false;
			}
		}
		if(p2.getColor()== Color.BLUE){
			if(p1.getNationality()!= Nationality.NORWEGIAN && p3.getNationality()!= Nationality.NORWEGIAN){
				return false;
			}
		}
		if(p3.getColor()== Color.BLUE){
			if(p2.getNationality()!= Nationality.NORWEGIAN && p4.getNationality()!= Nationality.NORWEGIAN){
				return false;
			}
		}
		if(p4.getColor()== Color.BLUE){
			if(p3.getNationality()!= Nationality.NORWEGIAN){
				return false;
			}
		}
		return true;
	}

	//5. the man who smokes blend has a neighbor who drinks water
	//	cigarMap.put(Cigar.BLENDS.NEXT, Beverage.WATER);
	boolean blendsWaterCheck(Person p0, Person p1, Person p2,
			Person p3, Person p4){
		if(p0.getCigar()== Cigar.BLENDS){
			if(p1.getBeverage()!= Beverage.WATER){
				return false;
			}
		}
		if(p1.getCigar()== Cigar.BLENDS){
			if(p0.getBeverage()!= Beverage.WATER && p2.getBeverage()!= Beverage.WATER){
				return false;
			}
		}
		if(p2.getCigar()== Cigar.BLENDS){
			if(p1.getBeverage()!= Beverage.WATER && p3.getBeverage()!= Beverage.WATER){
				return false;
			}
		}
		if(p3.getCigar()== Cigar.BLENDS){
			if(p2.getBeverage()!= Beverage.WATER && p4.getBeverage()!=Beverage.WATER){
				return false;
			}
		}
		if(p4.getCigar()== Cigar.BLENDS){
			if(p3.getBeverage()!= Beverage.WATER){
				return false;
			}
		}
		return true;
	}
	

	// all characteristics defined as enums
	static enum HousePosition{
		ONE (1), 
		TWO (2), 
		THREE (3), 
		FOUR (4), 
		FIVE (5);
		
		private int housePosition;
		
		HousePosition(int housePosition){
			this.housePosition= housePosition;
		}
		
		@Override
		public String toString(){
			return String.valueOf(housePosition);
		}
		
		public int getValue(){
			return housePosition;
		}
	}

	static enum Color{
		RED("RED"), 
		GREEN("GREEN"),
		WHITE("WHITE"), 
		YELLOW("YELLOW"), 
		BLUE("BLUE");
		
		private String color;
		
		Color(String color) {
			this.color= color;
		}
		
		@Override
		public String toString(){
			return color;
		}
	}

	static enum Nationality{
		BRIT("BRIT"), 
		SWEDE("SWEDE"),
		DANE("DANE"),
		NORWEGIAN("NORWEGIAN"),
		GERMAN("GERMAN");
		
		private String nationality;
		
		Nationality(String nationality) {
			this.nationality= nationality;
		}
		
		@Override
		public String toString(){
			return nationality;
		}
	}

	static enum Beverage{
		TEA("TEA"),
		COFFEE("COFFEE"),
		MILK("MILK"),
		BEER("BEER"),
		WATER("WATER");
		
		private String beverage;
		
		Beverage(String beverage) {
			this.beverage= beverage;
		}
		
		@Override
		public String toString(){
			return beverage;
		}
	}

	static enum Cigar{
		PALLMALL("PALLMALL"),
		DUNHILL("DUNHILL"), 
		BLENDS("BLENDS"),
		BLUEMASTER("BLUEMASTER"),
		PRINCE("PRINCE");
		
		private String cigar;
		
		Cigar(String cigar) {
			this.cigar= cigar;
		}
		
		@Override
		public String toString(){
			return cigar;
		}
		
	}

	static enum Pet{
		DOGS("DOGS"), 
		BIRDS("BIRDS"), 
		CATS("CATS"), 
		HORSES("HORSES"), 
		FISH("FISH");
		private String pet;
		
		Pet(String pet) {
			this.pet= pet;
		}
		
		@Override
		public String toString(){
			return pet;
		}
	}	
}
