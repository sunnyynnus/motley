package sap;

import sap.Hint.Beverage;
import sap.Hint.Cigar;
import sap.Hint.Color;
import sap.Hint.HousePosition;
import sap.Hint.Nationality;
import sap.Hint.Pet;



/**
 * @author sunny
 *
 * Its resembles PersonHouse Combination
 * not only Person // need to change name
 */
public class Person {
	private HousePosition housePosition;
	private Color color;
	private Nationality nationality;
	private Beverage beverage;
	private Cigar cigar;
	private Pet pet;

	public Person(HousePosition housePosition, Color color,
			Nationality nationality, Beverage beverage, Cigar cigar, Pet pet) {
		this.housePosition= housePosition;
		this.color= color;
		this.nationality= nationality;
		this.beverage= beverage;
		this.cigar= cigar;
		this.pet= pet;
	}
	public HousePosition getHousePosition() {
		return housePosition;
	}

	public Color getColor() {
		return color;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public Cigar getCigar() {
		return cigar;
	}

	public Pet getPet() {
		return pet;
	}

	@Override
	public String toString(){
		return housePosition.toString()+"\t"+color.toString()+"\t"+nationality.toString()+"\t"+
				 beverage.toString()+"\t" + cigar.toString()+"\t" + pet.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null || !(o instanceof Person)){
			return false;
		}
		Person person=(Person) o;
		return (housePosition==person.housePosition) && (color==person.color) && (nationality==person.nationality) 
		 && (beverage==person.beverage) && (cigar==person.cigar) && (pet==person.pet);
	}
	
	@Override
	public int hashCode(){
		return housePosition.hashCode()+color.hashCode()+nationality.hashCode()+beverage.hashCode()
				+cigar.hashCode()+pet.hashCode();
	}
	/*
	 *  return true if none of the characteristic matches
	 *  else false
	 */
	public boolean uniqueCheck(Person person){
		return (housePosition!=person.housePosition) && (color!=person.color) && (nationality!=person.nationality) 
				 && (beverage!=person.beverage) && (cigar!=person.cigar) && (pet!=person.pet);
	}
	
}