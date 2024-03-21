import java.io.*;
import edu.duke.*;
import java.util.*;
import java.time.*;
class expense{
	LocalDate date;
	categories category;
	long inc;
	boolean isToday;
	long totalAmount;
	long lastDateAmount;
	expense(long inc, String date, long lastAmount){
		this.inc = inc;
		totalAmount = lastAmount;
		category = new categories();
		this.date = LocalDate.parse(date);
		isToday = this.date.isBefore(LocalDate.now());
	}
	void showLastDateDetail(){
		System.out.println("\nYesterday expense: "+lastDateAmount);
	}
	void showTodayDetail(){
		System.out.println("\nToday's expense: "+totalAmount);
	}
	void checkMonth(){
		if(LocalDate.now().getMonthValue() != date.getMonthValue()){
			category.food = 0;
			category.grocery = 0;
			category.travel = 0;
			category.personal = 0;
			category.savings = 0;
			category.others = 0;
			category.bills = 0;
		}
	}
	void checkTdy(){
		if(isToday){
			this.category.setTodayFood(0);
			this.category.setTodayGrocery(0);
			this.category.setTodayPersonal(0);
			this.category.setTodayBills(0);
			this.category.setTodayTravel(0);
			this.category.setTodaySavings(0);
			this.category.setTodayOthers(0);
			isToday = false;
		}
	}
	void amntInc(long amount, int category){
		switch(category){
		case 1:
			this.category.addTodayFood(amount);
			break;
		case 2:
			this.category.addTodayGrocery(amount);
			break;
		case 3:
			this.category.addTodayPersonal(amount);
			break;
		case 4:
			this.category.addTodayBills(amount);
			break;
		case 5:
			this.category.addTodayTravel(amount);
			break;
		case 6:
			this.category.addTodaySavings(amount);
			break;
		case 7:
			this.category.addTodayOthers(amount);
			break;
		default:
			System.out.println("Enter the valid category");
		}
	}
	void setIncome(long inc){
		this.inc = inc;
	}
	void autoSuggest(){
		System.out.println("\n          Suggestion");
		System.out.println("=============================");
		System.out.println("Food            : "+String.format("%.2f",Math.floor(inc * 0.1)));
		System.out.println("Grocery         : "+String.format("%.2f",Math.floor(inc * 0.15)));
		System.out.println("Travel          : "+String.format("%.2f",Math.floor(inc * 0.1)));
		System.out.println("Rents and bills : "+String.format("%.2f",Math.floor(inc * 0.2)));
		System.out.println("Personal        : "+String.format("%.2f",Math.floor(inc * 0.15)));
		System.out.println("Savings         : "+String.format("%.2f",Math.floor(inc * 0.2)));
		System.out.println("Others          : "+String.format("%.2f",Math.floor(inc * 0.1)));
		System.out.println("=============================");
	}
	boolean checkLimitAmount(long limit, long amount){
		Scanner getInput = new Scanner(System.in);
		int trackingNum = 0;
		if(limit <= amount && limit != 0){
			System.out.println("Amount is exceeding the limit. Do you want to continue?\n1. Yes\n2. No");
			trackingNum = getInput.nextInt();
			switch(trackingNum){
			case 1:
				break;
			default:
				return false;
			}
		}
		return true;
	}
	void addAmount(){
		Scanner getInput = new Scanner(System.in);
		long amount = 0;
		int trackingNum = 0;
		// adding food expense
		loop1:
		while(true){
			System.out.println("\nWhich one do you want to add?\n1. Food\n2. Grocery\n3. Personal\n4. Rent and bills\n5. Travel\n6. Saving\n7. Others");
			trackingNum = getInput.nextInt();
			if(trackingNum < 8 && trackingNum > 0){
				System.out.println("Enter the amount:");
				amount = getInput.nextLong();
				if(checkAmount(amount)){
					switch(trackingNum){
					case 1:
						if(checkLimitAmount(category.Limitfood, category.food+amount)){
							category.setFood(amount);
							amntInc(amount,trackingNum);
						}
						break;
					// adding grocery expense
					case 2:
						if(checkLimitAmount(category.Limitgrocery, category.grocery+amount)){
							category.setGrocery(amount);
							amntInc(amount,trackingNum);
						}
						break;
					// travel expense:
					case 3:
						if(checkLimitAmount(category.Limittravel,category.travel+amount)){
							category.setTravel(amount);
							amntInc(amount,trackingNum);
						}
						break;
					case 4:
					// rent and bill expense:
						if(checkLimitAmount(category.Limitbills, category.bills+amount)){
							category.setBills(amount);
							amntInc(amount,trackingNum);
						}
						break;
					case 5:
					// personal expense
						if(checkLimitAmount(category.Limitpersonal, category.personal+amount)){
							category.setPersonal(amount);
							amntInc(amount,trackingNum);
						}
						break;
					case 6:
					// savings
						if(checkLimitAmount(category.Limitsavings, category.savings+amount)){
							category.setSavings(amount);
							amntInc(amount,trackingNum);
						}
						break;
					case 7:
						if(checkLimitAmount(category.Limitothers, category.others+amount)){
							category.setOthers(amount);
							amntInc(amount,trackingNum);
						}
						break;
					default:
						break loop1;
					}
				}
				else{
					System.out.println("Give valid amount.(It is not added to your expense)");
				}
			}
			category.showExpenses();
			viewBalance();
			System.out.println("\nDo you want to add again: \n1. Yes\n2. No");
			trackingNum = getInput.nextInt();
			switch(trackingNum){
			case 1:
				break;
			default:
				break loop1;
			}
		}
	}
	void updateCheck(long amount, long checkAmount, int category){
		System.out.println(amount+","+checkAmount);
		if(amount > checkAmount){
			amntInc(amount - checkAmount, category);
		}
	}
	void updateExpense(){
		Scanner getInput = new Scanner(System.in);
		int trackingNum = 0;
		long amount = 0;
		category.showExpenses();
		loop1:
		while(true){
			System.out.println("\nWhich one do you want to update?\n1. Food\n2. Grocery\n3. Personal\n4. Rent and bills\n5. Travel\n6. Saving\n7. Others");
			trackingNum = getInput.nextInt();
			if(trackingNum < 8 && trackingNum > 0){
				System.out.println("Enter the amount:");
				amount = getInput.nextLong();
			}
			if(checkAmount(amount)){
				switch(trackingNum){
				case 1:
					if(checkLimitAmount(category.Limitfood, category.food+amount)){
						updateCheck(amount, category.getFood(), trackingNum);
						category.resetFood(amount);
					}
					break;
				case 2:
					if(checkLimitAmount(category.Limitgrocery, category.grocery+amount)){
						updateCheck(amount, category.getGrocery(), trackingNum);
						category.resetGrocery(amount);
					}
					break;
				case 3:
					if(checkLimitAmount(category.Limitpersonal, category.personal+amount)){
						updateCheck(amount, category.getPersonal(), trackingNum);
						category.resetPersonal(amount);
					}
					break;
				case 4:
					if(checkLimitAmount(category.Limitbills, category.bills+amount)){
						updateCheck(amount, category.getBills(), trackingNum);
						category.resetBills(amount);
					}
					break;
				case 5:
					if(checkLimitAmount(category.Limittravel, category.travel+amount)){
						updateCheck(amount, category.getTravel(), trackingNum);
						category.resetTravel(amount);
					}
					break;
				case 6:
					if(checkLimitAmount(category.Limitsavings, category.savings+amount)){
						updateCheck(amount, category.getSavings(), trackingNum);
						category.resetSavings(amount);
					}
					break;
				case 7:
					if(checkLimitAmount(category.Limitothers, category.others+amount)){
						updateCheck(amount, category.getOthers(), trackingNum);
						category.resetOthers(amount);
					}
					break;
				default:
					break loop1;
				}
				System.out.println("\u001B[32mAmount successfully updated.\u001B[0m");
				category.showExpenses();
				viewBalance();
				System.out.println("\nDo you want to update again: \n1. Yes\n2. No");
				trackingNum = getInput.nextInt();
				switch(trackingNum){
				case 1:
					break;
				default:
					break loop1;
				}
			}
			else{
				System.out.println("\u001B[31mEnter valid amount");
			}
		}
	}
	boolean checkAmount(long amnt){
		if(amnt >= 0){
			return true;
		}
		return false;
	}
	void viewBalance(){
		long balanceAmount = inc - category.food - category.grocery - category.travel - category.personal - category.savings - category.others - category.bills;
		if(balanceAmount < 0){
			System.out.println("Your balance is: \u001B[31m"+balanceAmount+"\u001B[0m");
		}
		else{
			System.out.println("Your balance is: \u001B[32m"+balanceAmount+"\u001B[0m");
		}
		totalSpent();
	}
	void totalSpent(){
		long spentAmount = category.food + category.grocery + category.travel + category.personal + category.savings + category.others + category.bills;
		if(spentAmount < inc){
			System.out.println("Your amount spent is: \u001B[32m"+spentAmount+"\u001B[0m");
		}
		else{
			System.out.println("Your amount spent is: \u001B[31m"+spentAmount+"\u001B[0m");
		}
	}
}
class categories{
	LocalDate month;
	LocalDate date;
	long food;
	long grocery;
	long travel;
	long bills;
	long others;
	long personal;
	long savings;
	long Limitfood;
	long Limitgrocery;
	long Limittravel;
	long Limitbills;
	long Limitothers;
	long Limitpersonal;
	long Limitsavings;
	long Todayfood;
	long Todaygrocery;
	long Todaytravel;
	long Todaybills;
	long Todayothers;
	long Todaypersonal;
	long Todaysavings;
	categories(){
		this(0,0,0,0,0,0,0);
	}
	categories(long food, long grocery, long travel, long bills, long personal, long others, long savings){
		this.food = food;
		this.grocery = grocery;
		this.travel = travel;
		this.bills = bills;
		this.others = others;
		this.savings = savings;
		this.personal = personal;
	}
	void setAll(long food, long grocery, long travel, long bills, long personal, long others, long savings){
		this.food = food;
		this.grocery = grocery;
		this.travel = travel;
		this.bills = bills;
		this.savings = savings;
		this.others = others;
		this.personal = personal;
	}
	void setFood(long food){
		this.food += food;
	}
	void setGrocery(long grocery){
		this.grocery += grocery;
	}
	void setTravel(long travel){
		this.travel += travel;
	}
	void setBills(long bills){
		this.bills += bills;
	}
	void setPersonal(long personal){
		this.personal += personal;
	}
	void setOthers(long others){
		this.others += others;
	}
	void setSavings(long savings){
		this.savings += savings;
	}
	void addTodayFood(long food){
		this.Todayfood += food;
	}
	void addTodayGrocery(long grocery){
		this.Todaygrocery += grocery;
	}
	void addTodayTravel(long travel){
		this.Todaytravel += travel;
	}
	void addTodayBills(long bills){
		this.Todaybills += bills;
	}
	void addTodayPersonal(long personal){
		this.Todaypersonal += personal;
	}
	void addTodayOthers(long others){
		this.Todayothers += others;
	}
	void addTodaySavings(long savings){
		this.Todaysavings += savings;
	}
	void setTodayFood(long food){
		this.Todayfood = food;
	}
	void setTodayGrocery(long grocery){
		this.Todaygrocery = grocery;
	}
	void setTodayTravel(long travel){
		this.Todaytravel = travel;
	}
	void setTodayBills(long bills){
		this.Todaybills = bills;
	}
	void setTodayPersonal(long personal){
		this.Todaypersonal = personal;
	}
	void setTodayOthers(long others){
		this.Todayothers = others;
	}
	void setTodaySavings(long savings){
		this.Todaysavings = savings;
	}
	void setLimitFood(long foodLimit){
		Limitfood = foodLimit;
	}
	void setLimitgrocery(long groceryLimit){
		Limitgrocery = groceryLimit;
	}
	void setLimitTravel(long travelLimit){
		Limittravel = travelLimit;
	}
	void setLimitbills(long billsLimit){
		Limitbills = billsLimit;
	}
	void setLimitothers(long othersLimit){
		Limitothers = othersLimit;
	}
	void setLimitpersonal(long personalLimit){
		Limitpersonal = personalLimit;
	}
	void setLimitsavings(long savingsLimit){
		Limitsavings = savingsLimit;
	}
	void showLimits(){
		System.out.println("\n          Limit Details");
		System.out.println("=================================");
		System.out.println("Limit for food           : "+Limitfood);
		System.out.println("Limit for grocery        : "+Limitgrocery);
		System.out.println("Limit for travel         : "+Limittravel);
		System.out.println("Limit for personal       : "+Limitpersonal);
		System.out.println("Limit for rent and bills : "+Limitbills);
		System.out.println("Limit for other expenses : "+Limitothers);
		System.out.println("Limit for savings        : "+Limitsavings);
		System.out.println("=================================");
	}
	void setLimits(){
		Scanner getInput = new Scanner(System.in);
		int trackingNum = 0;
		long amount = 0;
		loop1:
		while(true){
			showLimits();
			System.out.println("\nWhich one do you want to set?\n1. Food\n2. Grocery\n3. Personal\n4. Rent and bills\n5. Travel\n6. Saving\n7. Others\n8. Back to home");
			trackingNum = getInput.nextInt();
			if(trackingNum != 8){
				System.out.println("Enter the amount:");
				amount = getInput.nextLong();
				if(checkAmount(amount)){
					switch(trackingNum){
					case 1:
						setLimitFood(amount);
						break;
					case 2:
						setLimitgrocery(amount);
						break;
					case 3:
						setLimitpersonal(amount);
						break;
					case 4:
						setLimitbills(amount);
						break;
					case 5:
						setLimitTravel(amount);
						break;
					case 6:
						setLimitsavings(amount);
						break;
					case 7:
						setLimitothers(amount);
						break;
					default:
						break loop1;
					}
					System.out.println("\u001B[32mAmount successfully added.\u001B[0m");
				}
				else{
					System.out.println("\u001B[31mInvalid Amount\u001B[0m");
				}
			}
			else{
				break loop1;
			}
		}
	}
	boolean checkAmount(long amount){
		if(amount > 0 ){
			return true;
		}
		return false;
	}
	void resetFood(long food){
		this.food = food;
	}
	void resetGrocery(long grocery){
		this.grocery = grocery;
	}
	void resetTravel(long travel){
		this.travel = travel;
	}
	void resetBills(long bills){
		this.bills = bills;
	}
	void resetPersonal(long personal){
		this.personal = personal;
	}
	void resetOthers(long others){
		this.others = others;
	}
	void resetSavings(long savings){
		this.savings = savings;
	}
	long getFood(){
		return food;
	}
	long getSavings(){
		return savings;
	}
	long getGrocery(){
		return grocery;
	}
	long getTravel(){
		return travel;
	}
	long getBills(){
		return bills;
	}
	long getPersonal(){
		return personal;
	}
	long getOthers(){
		return others;
	}
	void showExpenses(){
		System.out.println("\n                    Details");
		System.out.println("================================================================================");
		System.out.println("Categories      |   Total Spent          |  Today spent       | Monthly Limit");
		System.out.println("================================================================================");
		System.out.println("Food            |   Rs."+ limitChecker(food,"food")+spaceLeave(food+"")+"Rs."+Todayfood+spaceLeave(Todayfood+"")+Limitfood);
		System.out.println("Grocery         |   Rs."+ limitChecker(grocery,"grocery")+spaceLeave(grocery+"")+"Rs."+Todaygrocery+spaceLeave(Todaygrocery+"")+Limitgrocery);
		System.out.println("Travel          |   Rs."+ limitChecker(travel,"travel")+spaceLeave(travel+"")+"Rs."+Todaytravel+spaceLeave(Todaytravel+"")+Limittravel);
		System.out.println("Rents and bills |   Rs."+ limitChecker(bills,"bills")+spaceLeave(bills+"")+"Rs."+Todaybills+spaceLeave(Todaybills+"")+Limitbills);
		System.out.println("Personal        |   Rs."+ limitChecker(personal,"personal")+spaceLeave(personal+"")+"Rs."+Todaypersonal+spaceLeave(Todaypersonal+"")+Limitpersonal);
		System.out.println("Savings         |   Rs."+ limitChecker(savings,"savings")+spaceLeave(savings+"")+"Rs."+Todaysavings+spaceLeave(Todaysavings+"")+Limitsavings);
		System.out.println("Others          |   Rs."+ limitChecker(others,"others")+spaceLeave(others+"")+"Rs."+Todayothers+spaceLeave(Todayothers+"")+Limitothers);
		System.out.println("================================================================================");
	}
	String spaceLeave(String amount){
		String space = "";
		for(int num = 0; num < 20-amount.length(); num++){
			space+=" ";
		}
		return space;
	}
	String limitChecker(long amount, String category){
		String output = "";
		switch(category){
		case "food":
			if(Limitfood == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitfood){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "grocery":
			if(Limitgrocery == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitgrocery){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "travel":
			if(Limittravel == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limittravel){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "bills":
			if(Limitbills == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitbills){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "personal":
			if(Limitpersonal == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitpersonal){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "savings":
			if(Limitsavings == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitsavings){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		case "others":
			if(Limitothers == 0){
				output += "\u001B[34m"+amount+"\u001B[0m";
			}
			else if(amount < Limitothers){
				output += "\u001B[32m"+amount+"\u001B[0m";
			}
			else{
				output += "\u001B[31m"+amount+"\u001B[0m";
			}
			break;
		}
		return output;
	}
}
class user{
	expense myExpense;
	// update the values to the file
	void setValues(FileResource file, String userName, String password, user User){
		try{
			StringBuffer wholeString = new StringBuffer("");
			FileWriter writer = new FileWriter("Username.txt");
			for(String currLine: file.lines()){
				if(currLine.contains(userName)){
					wholeString.append(userName+","+password+","+User.myExpense.inc+",--,"+User.myExpense.category.food+","+User.myExpense.category.travel+","+User.myExpense.category.grocery+","+User.myExpense.category.personal+","+User.myExpense.category.others+","+User.myExpense.category.bills+","+User.myExpense.category.savings+","+User.myExpense.category.Limitfood+","+User.myExpense.category.Limittravel+","+User.myExpense.category.Limitgrocery+","+User.myExpense.category.Limitpersonal+","+User.myExpense.category.Limitothers+","+User.myExpense.category.Limitbills+","+User.myExpense.category.Limitsavings+","+LocalDate.now()+","+User.myExpense.totalAmount+","+User.myExpense.category.Todayfood+","+User.myExpense.category.Todaygrocery+","+User.myExpense.category.Todaypersonal+","+User.myExpense.category.Todaybills+","+User.myExpense.category.Todaytravel+","+User.myExpense.category.Todaysavings+","+User.myExpense.category.Todayothers+"\n");
				}
				else{
					wholeString.append(currLine+"\n");
				}
			}
			writer.write(wholeString.toString());
			writer.close();
		}
		catch(Exception e){
			System.out.println("Error 404");
		}
	}
	boolean userLogin(FileResource file, String userName, String password){
		boolean output = false;
		for(String currLine: file.lines()){
			String []userDetails = currLine.split(",");
			if(userDetails[0].equals(userName)){
				if(userDetails[1].equals(password)){
					output = true;
					break;
				}
			}
		}
		return output;
	}
	boolean userSignin(FileResource file, String userName, String password, long salary){
		if(userLogin(file, userName, password)){
			try{
				FileWriter writer = new FileWriter("Username.txt", true);
				writer.write("\n"+userName+","+password+","+salary+",--,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"+LocalDate.now()+",0,0,0,0,0,0,0,0");
				writer.close();
				return true;
			}
			catch(IOException e){
				System.out.println("Error 404");
			}
		}
		return false;
	}
	long conLong(String num){
		return Long.parseLong(num);
	}
	public static void main(String ...args){
		System. out. print("\033[H\033[2J");
		int trackerNum = 0;
		Scanner getInput = new Scanner(System.in);
		user person1 = new user();
		String name = "";
		String pass = "";
		long amount = 0;
		FileResource f = null;
		ArrayList<String> details = new ArrayList<String>();
		System.out.println("███████╗██╗  ██╗██████╗ ███████╗███╗   ██╗███████╗███████╗    ████████╗██████╗  █████╗  ██████╗██╗  ██╗███████╗██████╗"); 
		System.out.println("██╔════╝╚██╗██╔╝██╔══██╗██╔════╝████╗  ██║██╔════╝██╔════╝    ╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝██╔══██╗");
		System.out.println("█████╗   ╚███╔╝ ██████╔╝█████╗  ██╔██╗ ██║███████╗█████╗         ██║   ██████╔╝███████║██║     █████╔╝ █████╗  ██████╔╝");
		System.out.println("██╔══╝   ██╔██╗ ██╔═══╝ ██╔══╝  ██║╚██╗██║╚════██║██╔══╝         ██║   ██╔══██╗██╔══██║██║     ██╔═██╗ ██╔══╝  ██╔══██╗");
		System.out.println("███████╗██╔╝ ██╗██║     ███████╗██║ ╚████║███████║███████╗       ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗███████╗██║  ██║");
		System.out.println("╚══════╝╚═╝  ╚═╝╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝       ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝");                                                                         
		try{
			File checkFile = new File("Username.txt");
			checkFile.createNewFile();
		}
		catch(Exception e){
			System.out.println("Error in creating file");
		}
		f = new FileResource("Username.txt");
		// sign-in code--
		loop1:
		while(true){
			trackerNum = 0;
			try{
				System.out.println("\n================");
				System.out.println("|| 1. Log-in  ||");
				System.out.println("|| 2. Sign-up ||");
				System.out.println("================");
				trackerNum = getInput.nextInt();
			}
			catch(InputMismatchException e){
				throw new InputMismatchException("Wrong input. Enter 1 or 2");
				// System.out.println(trackerNum);
				// System.out.println("Wrong input. Enter 1 or 2");
			}
			if(trackerNum <= 2 && trackerNum > 0){
				System.out.println("||Enter user name:  ||");
				getInput.nextLine();
				name = getInput.nextLine();
				System.out.println("||Enter password:   ||");
				pass = getInput.nextLine();
			}
			switch(trackerNum){
			// Log-in code --
			case 1:
				if(person1.userLogin(f, name, pass)){
					System.out.println("\u001B[32m"+"Log-in successful"+"\u001B[0m");
					break loop1;
				}
				else{
					System.out.println("\u001B[31mInvalid input\u001B[0m");
				}
				break;
			// Sign-in code --
			case 2:
				while(true){
					try{
						System.out.println("\nEnter the amount of salary");
						amount = getInput.nextLong();
					}
					catch(InputMismatchException e){
						System.out.println("Enter valid amount");
					}
					if(amount > 100){
						break;
					}
					else{
						System.out.println("Enter valid salary");
					}
				}
				if(person1.userSignin(f, name, pass, amount)){
					System.out.println("\u001B[32m"+"Sign-in successful"+"\u001B[0m");
					f = new FileResource("Username.txt"); 
					break loop1;
				}
				else{
					System.out.println("\u001B[31mInvalid input\u001B[0m");
				}
			}
		}
		for(String currLine: f.lines()){
			if(currLine.contains(name)){
				details.addAll(Arrays.asList(currLine.split(",")));
			}
		}
		// Setting salary --
		// previous details are available retrive it and set it.
		person1.myExpense = new expense(person1.conLong(details.get(2)), details.get(18),person1.conLong(details.get(19)));
		person1.myExpense.category.setFood(person1.conLong(details.get(4)));
		person1.myExpense.category.setTravel(person1.conLong(details.get(5)));
		person1.myExpense.category.setGrocery(person1.conLong(details.get(6)));
		person1.myExpense.category.setPersonal(person1.conLong(details.get(7)));
		person1.myExpense.category.setOthers(person1.conLong(details.get(8)));
		person1.myExpense.category.setBills(person1.conLong(details.get(9)));
		person1.myExpense.category.setSavings(person1.conLong(details.get(10)));
		person1.myExpense.category.setLimitFood(person1.conLong(details.get(11)));
		person1.myExpense.category.setLimitTravel(person1.conLong(details.get(12)));
		person1.myExpense.category.setLimitgrocery(person1.conLong(details.get(13)));
		person1.myExpense.category.setLimitpersonal(person1.conLong(details.get(14)));
		person1.myExpense.category.setLimitothers(person1.conLong(details.get(15)));
		person1.myExpense.category.setLimitbills(person1.conLong(details.get(16)));
		person1.myExpense.category.setLimitsavings(person1.conLong(details.get(17)));
		person1.myExpense.category.setTodayFood(person1.conLong(details.get(20)));
		person1.myExpense.category.setTodayGrocery(person1.conLong(details.get(21)));
		person1.myExpense.category.setTodayPersonal(person1.conLong(details.get(22)));
		person1.myExpense.category.setTodayBills(person1.conLong(details.get(23)));
		person1.myExpense.category.setTodayTravel(person1.conLong(details.get(24)));
		person1.myExpense.category.setTodaySavings(person1.conLong(details.get(25)));
		person1.myExpense.category.setTodayOthers(person1.conLong(details.get(26)));
		person1.myExpense.checkMonth();
		person1.myExpense.checkTdy();
		// listing out the features
		loop2:
		while(true){
			System.out.println("\n1. View expenses\n2. Update expense\n3. Add expense\n4. Set limits\n5. Suggest expense\n6. Change salary\n7. Exit");
			trackerNum = getInput.nextInt();
			switch(trackerNum){
			case 1:
				person1.myExpense.category.showExpenses();
				person1.myExpense.viewBalance();
				break;
			case 2:
				person1.myExpense.updateExpense();
				break;
			case 3:
				person1.myExpense.addAmount();
				break;
			case 4:
				person1.myExpense.category.setLimits();
				break;
			case 5:
				person1.myExpense.autoSuggest();
				break;
			case 6:
				while(true){
					try{
						System.out.println("\nEnter the amount of salary");
						amount = getInput.nextLong();
					}
					catch(InputMismatchException e){
						throw new InputMismatchException("Enter the valid salary");
					}
					if(amount > 100){
						person1.myExpense.setIncome(amount);
						System.out.println("\u001B[32mSalary updated successfully\u001B[0m");
						break;
					}
					else{
						System.out.println("\u001B[31mEnter valid salary\u001B[0m");
					}
				}
				break;
			default:
				person1.setValues(f, name, pass, person1);
				break loop2;
			}	
		}
	}
}