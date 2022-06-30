package org.banking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BankApp {

	ArrayList<UserAccount> users=new ArrayList<UserAccount>();
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	static UserAccount user=null,rUser=null;
	public BankApp()
	{
		users.add(new UserAccount(101,9876545,"Rohan Shah",5000,"Mumbai",987656401,"u101"));
		users.add(new UserAccount(102,9876546,"Prasad Gavandhe",7000,"Pune",987656402,"u102"));
		users.add(new UserAccount(103,9876547,"Prapti Patel",9000,"Nagar",987656403,"u103"));
		users.add(new UserAccount(104,9876548,"Priti Jain",10000,"Nashik",987656404,"u104"));
	}
	
	
	synchronized public boolean login() throws NumberFormatException, IOException
	{
		
		System.out.println("Enter UserID:");
		long userId=Integer.parseInt(br.readLine());
		System.out.println("Enter Password:");
		String password=br.readLine();
		int flag=0;
		for(UserAccount u:users)
		{
			if(u.getUserId()==userId &&   u.getPassword().equals(password))
			{   
				flag=1;
				user=u;
			}
		}
		if(flag==0)
			return false;
		else
			return true;
	}
	
	synchronized public void transferFund() throws Exception
	{
		String ch=null;
		do{
			try {
				System.out.println("====================================================================");
				System.out.println("\n\t1)Transfer funds to other account of same bank\r\n"
						+ "\t2)Transfer funds to other account of another bank\r\n"
						+ "\t3)Exit");
				System.out.println("Enter your choice:");
				int choice=Integer.parseInt(br.readLine());
				double amount;
				long rAccount;
				switch(choice)
				{
					case 1:	
						System.out.println("Enter Receiver Account No. :");
						rAccount=Long.parseLong(br.readLine());
						int f=0;
						for(UserAccount u:users)
						{
							if(u.getAccountId()==rAccount)
							{   
								rUser=u;
								f=1;
							}
						}
						if(f==1)
						{
							System.out.println("Enter Amount:");
							amount=Double.parseDouble(br.readLine());
							if(user.getUserBalance()>amount)
							{	
								rUser.setUserBalance(rUser.getUserBalance()+amount);
								user.setUserBalance(user.getUserBalance()-amount);
								System.out.println("Funds transfered Successfully..!");
							}	
							else
								throw new Exception("Failed..!:Insufficient balance");
						}
						else
							throw new Exception("User does not exist..!");
						break;
		case 2:
			System.out.println("Enter Receiver Account No. :");
			rAccount=Long.parseLong(br.readLine());
			System.out.println("Enter Amount:");
			amount=Double.parseDouble(br.readLine());
			if(user.getUserBalance()>amount)
			{	
				user.setUserBalance(user.getUserBalance()-amount);
				System.out.println("Funds transfered Successfully..!");
			}
			else
				throw new Exception("failed..!:insufficient balance");
			break;
		case 3:
			ch="n";
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
		if(choice!=3)
		{
		System.out.println("Do you want to transfer funds?(Y->Yes / N->No)");
		ch=br.readLine();
		}
		}
		catch(IllegalArgumentException e)
		{
			ch="y";
			System.out.println(e);
		}
		}while(ch.equals("Y")|| ch.equals("y"));
	}
	
	synchronized public void showDetail()
	{
		System.out.println("====================================================================");
		System.out.println("Account Details :");
		System.out.println(user.toString());
		
	}
	
	synchronized public void updateDetail() throws NumberFormatException, IOException
	{
		String c = null;
		int flag = 0;
		do {
		try {
		System.out.println("====================================================================");
		System.out.println("What you want to upadte:");
		System.out.println("  1)Name \n  2)Address\n  3)Contact Number \n  4)Password\n  5)Exit");
		System.out.println("Enter your choice:");
		int UpdateChoice=Integer.parseInt(br.readLine());
		String s;
		switch (UpdateChoice) {
			case 1:
					System.out.println("Enter name to update:");
					s=br.readLine();
					user.setUserName(s);
					flag=1;
					break;
			case 2:
					System.out.println("Enter address to update:");
					s=br.readLine();
					user.setUserAddress(s);
					flag=1;
					break;
			case 3:
					System.out.println("Enter Contact Number to update:");
					s=br.readLine();
					user.setContactNumber(Long.parseLong(s));
					flag=1;
					break;
			case 4:
					System.out.println("Enter Password to update:");
					s=br.readLine();
					user.setPassword(s);
					flag=1;
					break;
			case 5:
					c="n";
					break;
			default:
					throw new IllegalArgumentException("Unexpected value: " + UpdateChoice);
		}
		if(UpdateChoice!=5)
		{	
		System.out.println("Do you want to update anything else?(Y->Yes / N->No)");
		c=br.readLine();
		}
		}
		catch(IllegalArgumentException e)
		{
			c="y";
			System.out.println(e);
		}
		}while(c.equals("Y")|| c.equals("y"));
		if(flag==1)
		{
		System.out.println("====================================================================");
		System.out.println("Details Updated Successfully...!");
		}	
	}
	
	synchronized public void withdraw() throws NumberFormatException, IOException,Exception {  
        double amt; 
        System.out.println("====================================================================");
        System.out.println("Account Balance :"+user.getUserBalance());
        System.out.println("Enter the amount you want to withdraw: ");  
        amt = Double.parseDouble(br.readLine());
        if(user.getUserBalance()>amt)
        	user.setUserBalance(user.getUserBalance()-amt); 
        else
        	throw new Exception("Unsufficient Balance..!");
        checkBalance();
    }
	synchronized public void deposit() throws NumberFormatException, IOException {  
        double amt; 
        System.out.println("====================================================================");
        System.out.println("Enter the amount you want to deposit: ");  
        amt = Double.parseDouble(br.readLine()); 
        user.setUserBalance(user.getUserBalance()+amt); 
        checkBalance();
    }
	synchronized public void checkBalance()
	{
		System.out.println("====================================================================");
		System.out.println("Current Available Balance :"+user.getUserBalance());
		

	}
	
	
	
	public static void main(String[] args) throws Exception {
		BankApp b=new BankApp();
		System.out.println("\n====================================================================");
		System.out.println("----------------------- Bank Application ---------------------------");
		System.out.println("====================================================================");
		int ch;
		String mainChoice = null; 
		
		do
		{
		try {
			System.out.println("\t\t1)Login.\r\n"
					+ "\t\t2)Exit.");
			System.out.println("Enter Your Choice :");
			ch=Integer.parseInt(br.readLine());
			switch (ch) {
			case 1: {
	
				if(b.login())
				{
					System.out.println("====================================================================");
					System.out.println("                     Welcome Back "+b.user.getUserName()+"..!");
					System.out.println("\nUser ID :"+b.user.getAccountId()+"\t\t\t\tBalance :"+b.user.getUserBalance());
					String c=null;
					do
					{
						try {
						System.out.println("====================================================================");
						System.out.println("Dashbord:");
						System.out.println("\t\t 1)Fund Transfer.\r\n"
								+ "\t\t 2)Show Account Details.\r\n"
								+ "\t\t 3)Update account Details.\r\n"
								+ "\t\t 4)Withdraw Amount.\r\n"
								+ "\t\t 5)Deposit Amount.\r\n"
								+ "\t\t 6)Check Balance.\r\n"
								+ "\t\t 7)Logout.");
						System.out.println("====================================================================");
						System.out.println("Enter your choice:");
						int choice=Integer.parseInt(br.readLine());
						
						switch(choice)
						{
							case 1: 
								    b.transferFund();
								    b.checkBalance();
								    break;
							case 2:
									b.showDetail();
									break;
							case 3:
									b.updateDetail();
									break;
							case 4:
									b.withdraw();
									break;
							case 5:
									b.deposit();
									break;
							case 6:	
									b.checkBalance();
									break;
							case 7:
								 	c="n";
									break;
						    default:
						    	throw new Exception("Wrong choice!!"); 		   
						
						}
						if(choice!=7)
						{
						System.out.println("====================================================================");	
						System.out.println("Do you want to continue?(Y->Yes / N->No)");
						c=br.readLine();
						}
						}
						catch(Exception e)
						{
							c="y";
							System.out.println(e);
						}
					}while(c.equals("Y")|| c.equals("y"));
						
					System.out.println("====================================================================");
					System.out.println("Bye...");
					System.out.println("====================================================================");
				}			  
				else
				{
					throw new Exception("User not authenticated!!");
				}
				break;
			}
			case 2:
				mainChoice="n";
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + ch);
			}
			if(ch!=7)
			{
			System.out.println("====================================================================");	
			System.out.println("Do you want to continue?(Y->Yes / N->No)");
			mainChoice=br.readLine();
			}
		}
		catch(IllegalArgumentException e)
		{
			mainChoice="Y";
			System.out.println(e);
		}
		catch(Exception e)
		{
			mainChoice="Y";
			System.out.println(e);
		}
		}while(mainChoice.equals("Y")|| mainChoice.equals("y"));
	}
}
	
