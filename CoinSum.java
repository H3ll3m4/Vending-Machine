package vendingmachine;


public class CoinSum {
	int[] coinValues = {200,100,50,20,10,5,2,1};
	int[] coins = new int[8];
	
	public CoinSum(int onep, int twop, int fivep, int tenp, int twentyp, int fiftyp, int onePound, int twoPounds) {
		coins[0] = twoPounds;
		coins[1] = onePound;
		coins[2] = fiftyp;
		coins[3] = twentyp;
		coins[4] = tenp;
		coins[5] = fivep;
		coins[6] = twop ;
		coins[7] = onep;
	}
	
	public CoinSum getSum() {
		return this;
	}
	
	public Integer getTotalValue() {
		Integer total = 0;
		for (int i = 0; i < coins.length; i++) {
			total += coins[i]*coinValues[i];
		}
        return total;
	}
	
	public CoinSum exchange(int price, int totalReceived) {
		CoinSum possibleSum = new CoinSum(0,0,0,0,0,0,0,0);
		int amountToReturn = totalReceived - price;
		//Specific case where we enter exactly the amount
		if (amountToReturn == 0) {
			return possibleSum;
		}
		// We're going to decompose the price in coins to add it to vending machine
		for (int i = 0; i < coinValues.length; i++) {
			if (amountToReturn > 0){
				float division = amountToReturn / coinValues[i];
				//The amount due can be returned with this type of coin
				if (division >= 1) {
					int coinsNeeded = Math.round(division);
					// We check the change available has enough coin
					if (coinsNeeded <= this.coins[i]){
						// We remove the coins that were used
						int removeValue = coinsNeeded * coinValues[i];
						//If there is enough money
						if (amountToReturn >= removeValue){
							amountToReturn -= removeValue;
							possibleSum.coins[i] = coinsNeeded;
							this.coins[i] += coinsNeeded;

						}						
					}
				}
			}
		}
		// We haven't decompose the price fully
		if (amountToReturn > 0) {
			return null;
		}
		return possibleSum;
	}
	
	public String showCoins() {
		StringBuilder strCoins = new StringBuilder();
		for (int i = 0; i < coinValues.length; i++) {
			String coinQuantity = Integer.toString(coins[i]);
			float coinValue = (float) coinValues[i]/100 ;
			String coinValueStr = String.format("%.02f", coinValue);
			strCoins.append( coinQuantity + " of £" + coinValueStr);
			strCoins.append("\n");
		}
		return strCoins.toString();
	}
}
