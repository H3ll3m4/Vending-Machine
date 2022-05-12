package vendingmachine;

import java.util.HashMap;
import java.util.Map.Entry;

public class VendingMachine {
	CoinSum changeAvailable;
	CoinSum moneyReceived;
	HashMap<String,Integer> catalogue = new HashMap<>();
	HashMap<String,Integer> inventory = new HashMap<>();
	
	
	public VendingMachine(CoinSum initialFloat) {
		// Initialise the amount of money available for change
		changeAvailable = initialFloat;
		// Initialise the catalogue with the prices
		catalogue.put("Crisps", 50);
		catalogue.put("Sweets", 75);
		catalogue.put("Biscuits", 95);
		catalogue.put("Cheese", 100);
		catalogue.put("Caviar", 200);
		// Initialise the inventory with the quantity of food available
		inventory.put("Crisps", 50);
		inventory.put("Sweets", 50);
		inventory.put("Biscuits", 50);
		inventory.put("Cheese", 50);
		inventory.put("Caviar", 50);	
		}
	
	public CoinSum getHoldingChange(){
		return changeAvailable;
	}
	
	public void setHoldingChange(CoinSum newAmount){
		changeAvailable = newAmount;
	}
	
	public CoinSum getMoneyInserted(){
		return moneyReceived;
	}

	public void insertCash(CoinSum coinsReceived) {
		this.moneyReceived = coinsReceived;
	}

	public boolean enoughMoney(String choice, CoinSum coinsReceived) {
		Integer totalReceived = coinsReceived.getTotalValue();
		Integer price = catalogue.get(choice);
		if (price > totalReceived ){
			return false;
		} else {
			return true;
		}
	}

	public CoinSum provideChange(String choice, CoinSum coinsReceived) {
		Integer totalReceived = coinsReceived.getTotalValue();
		Integer price = catalogue.get(choice);
		CoinSum change = changeAvailable.exchange(price,totalReceived);
		return change;
	}
	
	public String processOrder(String choice, CoinSum coinsReceived) {
		if (!itemExists(choice)) {
			throw new IllegalArgumentException("This choice is not available in the catalogue.");
		}
		if (isSoldOut(choice)) {
			throw new IllegalStateException("This choice is not available in the inventory of this machine.");
		}
		if (enoughMoney(choice,coinsReceived)) {
			CoinSum change = provideChange(choice,coinsReceived);
			if (change == null) {
				throw new IllegalStateException("Not enough change");
			}else {
				dispenseProduct(choice);
				return change.showCoins();
			}
		}else {
			throw new IllegalArgumentException("Not enough funds, please gather more coins and reiterate your order");
		}
	}
	
	public void displayCatalogue() {
		for (Entry<String, Integer> set: catalogue.entrySet()) {
			float price = (float) set.getValue()/100 ;
			String priceString = String.format("%.02f", price);
			System.out.println(set.getKey() + " for £" + priceString);
		}
	}
		
	public boolean isSoldOut(String choice) {
		int quantity = inventory.get(choice);
		return quantity < 1;
	}
	
	public void dispenseProduct(String choice) {
		int quantity = inventory.get(choice);
		inventory.put(choice, quantity -1);
	}
	
	public boolean itemExists(String choice) {
		return catalogue.containsKey(choice);
	}
}
