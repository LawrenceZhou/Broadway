//Created by Lawrence Zhou on 12/23/2014

//*******************description*******************//
//This project is designed to simulate the process of selling tickets, and we set that the location is Broadway.
//It succeeds to complete the tasks as below:
//1.Broadway is going to sell 100 tickets (including 50 unreserved tickets and 50 reserved tickets) in 3 booths;
//2.Broadway first let 3 booths sell 50 unreserved tickets;
//3.Use random to simulate the process of a customer choosing a booth;
//4.When the unreserved tickets are 10 left, Broadway starts to add 50 reserved tickets to sell, with re-adding the tickets and selling tickets simultaneously;
//5.Use random to simulate the rest of ticket seller, each ticket seller should rest at least twice;
//6.Display the number of tickets left as selling.


public class BroadwayTester
{
    final static int UnreservedTicket = 50;    //The number of tickets unreserved is 50
    final static int ChangeBoothTime = 100;    //The time of changing booth is 100 time units
    final static int CustomerFrequency = 200;  //The frequency of customer coming is 200 time units
    final static int MinTicketNumber = 10;     //The minimum number of tickets is 10, and we start the reAddTicket thread when the number of tickets is down to 10
    
	public static void main(String[] args)
	{
		System.out.println("Broadway sale begins! ");
		
		ticket t = new ticket(UnreservedTicket);
		booth booth1 = new booth(t,1);
		booth booth2 = new booth(t,2);
		booth booth3 = new booth(t,3);
		reAddTicket r = new reAddTicket(t);
		
		booth1.start();
		booth2.start();
		booth3.start();
		
		int flag = 1;  //Flag is used to confirm reAddTicket is started only once
		while(t.getTicketNumber() != 0)
		{
			
			if(t.getTicketNumber() == MinTicketNumber + 1 && flag == 1)   //If the number of tickets is down to MinTicketNumber, start the reAddTicket thread
			{
				flag = 0;
				r.start();
			}
			
			int new_customer = (int)(Math.random() * 3);      //Use random to determine the booth a new customer arrives at
			switch(new_customer)
			{
			case 0:
				if (booth1.isBoothRest())    //If booth1 is working
				{
					booth1.newCustomer();
					break;
				}
				else             //If booth1 is resting, the customer shall go to booth2
				{
					try
					{
						Thread.sleep(ChangeBoothTime);
					}
					catch ( InterruptedException exception ){}
				}
			case 1:
				if (booth2.isBoothRest())       //If booth2 is working
				{
					booth2.newCustomer();
				    break;
				}
				else        //If booth2 is resting, the customer shall go to booth3
				{
					try
					{
						Thread.sleep(ChangeBoothTime);
					}
					catch ( InterruptedException exception ){}
				}
			case 2:
				if (booth3.isBoothRest())        //If booth3 is working
				{
					booth3.newCustomer();
				    break;
				}
				else
				{
					try
					{
						Thread.sleep(ChangeBoothTime);
					}
					catch ( InterruptedException exception ){}
				}
			}

			try
			{
				Thread.sleep(CustomerFrequency);       //There comes a new customer
			}
			catch ( InterruptedException exception ){}
		}
		
		System.out.println("We've sold out! ");
	}
}
