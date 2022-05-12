package vendingmachine;
import java.io.Console;

public class Main {
	
	public static void main(String[] args) {
		CoinSum initialFloat = new CoinSum(50,50,50,100,50,50,50,50);
		VendingMachine machine = new VendingMachine(initialFloat);
		Console console = System.console();
		if (console == null) {
			System.out.println("No console available");
			return;
		}
		
		while (true) {
			System.out.println("\nWelcome to the vending machine simulator my dear patron, "
					+ "please make your choice to the following delicateseen");
			machine.displayCatalogue();
			try {
				String choice = console.readLine("What would you like?");
				int twopound = Integer.parseInt(console.readLine("Enter the number of 2£ you would like to insert : "));
				int onepound = Integer.parseInt(console.readLine("Enter the number of 1£ you would like to insert : "));
				int fiftyp = Integer.parseInt(console.readLine("Enter the number of 50p you would like to insert : "));
				int twentyp = Integer.parseInt(console.readLine("Enter the number of 20p you would like to insert : "));
				int tenp = Integer.parseInt(console.readLine("Enter the number of 10p you would like to insert : "));
				int fivep = Integer.parseInt(console.readLine("Enter the number of 5p you would like to insert : "));
				int twop = Integer.parseInt(console.readLine("Enter the number of 2p you would like to insert : "));
				int onep = Integer.parseInt(console.readLine("Enter the number of 1p you would like to insert : "));	
				CoinSum coinsReceived = new CoinSum(onep, twop,fivep,tenp,twentyp,fiftyp,onepound,twopound);
				String change = machine.processOrder(choice, coinsReceived);
				System.out.println("Thank you for your order. It has been processed. "
						+ "Please find change below : " + change 
						+ "\nIt was a pleasure to serve you.");
			}catch(IllegalArgumentException | IllegalStateException e) {
				System.out.println("My apologies, I ran into a problem:");
				System.out.println(e.getMessage());
				System.out.println("Please find the inserted money back to you.");
			}
		}
	}

}
