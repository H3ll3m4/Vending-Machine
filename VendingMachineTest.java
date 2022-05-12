package vendingmachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	VendingMachine machine;

	@Test
	public void testCreateCoinSum() {
		CoinSum sum = new CoinSum(1,2,3,4,5,6,7,8);
		assertEquals(sum, sum.getSum());
	}
	
	@Test
	public void testGetTotalCoin() {
		CoinSum sum = new CoinSum(1,1,1,1,1,1,1,1);
		Integer total = sum.getTotalValue();
		assertEquals((Integer)total, (Integer)388);
	}
	
	@Before
	@Test
	public void testMachineInitialisation() {
		CoinSum initialFloat = new CoinSum(50,50,50,100,50,50,50,50);
		machine = new VendingMachine(initialFloat);
		assertEquals(machine.getHoldingChange(), initialFloat);
	}
	
	@Test
	public void testCoinRegistration() {
		CoinSum coinsReceived = new CoinSum(0,0,0,0,0,1,1,0);
		machine.insertCash(coinsReceived);
		assertEquals(machine.getMoneyInserted(), coinsReceived);
	}

	
	@Test
	public void testEnoughMoney() {
		CoinSum coins = new CoinSum(0,0,0,0,0,1,1,0);
		boolean result = machine.enoughMoney("Crisps", coins);
		assert(result);
	}
	
	@Test
	public void testNotEnoughMoney() {
		CoinSum coins = new CoinSum(0,0,0,0,1,0,0,0);
		boolean result = machine.enoughMoney("Crisps", coins);
		assert(!result);
	}
	
	@Test
	public void testExchangeSumWithRest() {
		// we provide 1.50
		CoinSum coinsLoaded = new CoinSum(0,0,0,0,0,1,1,0);
		// it cost 50 and  we give 100
		CoinSum rest = coinsLoaded.exchange(50,100);
		// we expect 50 back
		assertEquals(rest.coins[2], 1);
		assertEquals(rest.coins[1], 0);
		assertEquals(coinsLoaded.coins[2],2);
		assertEquals(coinsLoaded.coins[1],1);

	}
	
	@Test
	public void testExchangeSumWithNoRest() {
		CoinSum availableCoins = new CoinSum(1,0,0,0,0,1,1,1);
		CoinSum change = availableCoins.exchange(351,351);
		assertEquals(change.coins[0], 0);
		assertEquals(change.coins[5], 0);
		assertEquals(change.coins[6], 0);
		assertEquals(change.coins[7], 0);
		assertEquals(availableCoins.coins[0], 1);
		assertEquals(availableCoins.coins[1], 1);
		assertEquals(availableCoins.coins[2], 1);
		assertEquals(availableCoins.coins[7], 1);
	}
	
	@Test
	public void testReturnCorrectChange() {
		CoinSum coinsReceived = new CoinSum(0,0,0,0,0,0,0,1);
		machine.insertCash(coinsReceived);
		String choice = "Crisps";
		CoinSum change = machine.provideChange(choice,coinsReceived);
		assertEquals(change.coins[1],1);
		assertEquals(change.coins[2],1);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNotEnoughChangeThrowException() {
		CoinSum availableChange = new CoinSum(1,1,1,1,1,0,0,0);
		machine.setHoldingChange(availableChange);
		CoinSum coinsReceived = new CoinSum(0,0,0,0,0,0,0,1);
		String choice = "Crisps";
		machine.processOrder(choice,coinsReceived);	
		}
	
	
	@Test
	public void testNotEnoughFunds() {
		CoinSum availableCoins = new CoinSum(0,0,0,0,0,0,1,1);
		CoinSum change = availableCoins.exchange(351,351);
		assertEquals(change.coins[6], 0);
		assertEquals(change.coins[7], 0);
		assertEquals(availableCoins.coins[0], 1);	
	}
	
	@Test
	public void testProductSoldOut() {
		machine.inventory.put("Sweets", 0);
		assertTrue(machine.isSoldOut("Sweets"));
	}

	@Test
	public void testProductAvailable() {
		assertFalse(machine.isSoldOut("Sweets"));
	}
	
	@Test
	public void testDispenseProduct() {
		String product = "Sweets";
		int previousQuantity = machine.inventory.get(product);		
		machine.dispenseProduct(product);
		int remainingQuantity = machine.inventory.get(product);
		assertEquals(previousQuantity, remainingQuantity + 1 );
	}
	
	@Test
	public void testProductNotFound() {
		assertFalse(machine.itemExists("Snack"));
	}
	
	@Test
	public void testProductFound() {
		assertTrue(machine.itemExists("Sweets"));
	}	

	@Test
	public void testDisplayCatalogue() {
		machine.displayCatalogue();
	}
	
	@Test
	public void testShowCoins() {
		System.out.println(machine.getHoldingChange().showCoins());
	}
}