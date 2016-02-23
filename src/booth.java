//Created by Lawrence Zhou on 12/23/2014


public class booth extends Thread    //Class booth extends thread, used to sell tickets
{
    final int MinRestTime = 2;   //The least times of a booth has to rest is 2
    final int RestTime = 1000;   //The time of resting is 1000
    
	private ticket t = null;
	private int  BoothSerialNumber;
	private int RestCount;      //RestCount records the number of tickets left
	private boolean LeastRest;  //LeastRest represents if a booth has rested enough times which is we set before
	private boolean NewCustomer;//NewCustomer represents if there is a new customer
	private boolean RestState;  //RestState represents if the booth is taking a rest
	
	public booth(){}
	
	public booth(ticket ti,int bn)
	{
		this.t = ti;
		this.RestCount = 0;
		this.BoothSerialNumber = bn;
		this.LeastRest = false;
		this.NewCustomer = false;
		this.RestState = true;
	}
	
	public void run()
	{
		while(t.getTicketNumber() > 0)
		{
			if(NewCustomer == true)        //if there comes a new customer
			{
				rest();
				if(t.getTicketNumber() == 0)
					break;
				synchronized(t)
				{
				    t.sellTicket();
			        System.out.println("Booth" + getBoothSerialNumber() + " has sold one ticket, " + t.getTicketNumber() + "left.");
			        NewCustomer = false;
			    }
			}		
		}
		
		if(RestCount < MinRestTime)      //if the booth hasn't rested least times
		{    
			LeastRest = true;
			rest();
		}
		
	}
	
	public int getBoothSerialNumber()
	{
		return BoothSerialNumber;
	}
	
	public void newCustomer()   //When comes a new customer, set NewCustomer true
	{
		NewCustomer = true;
	}
	
	public boolean isBoothRest()    //get the state of a booth, whether working or resting
	{
		return RestState;
	}
	
	public void rest()
	{
		
        int torest = (int)(Math.random() * 9);       //Using random to decide whether to rest
		if(torest == 1 && RestCount < 2 || LeastRest == true)  //the condition that a booth has to rest
		{
			RestState = false;
			System.out.println("Booth" + getBoothSerialNumber() + " has a rest!");
			try
			{
				Thread.sleep(RestTime);     //Resting takes a while
			}
			catch ( InterruptedException exception ){}
            
			RestState = true;                 //back to work
			System.out.println("Booth" + getBoothSerialNumber() + "comes back to work!");
			RestCount++;
		}
	}
	
}




class reAddTicket extends Thread           //Class reAddTicket extends thread too, used to add tickets when the tickets left is MinTicketNumber
{
    final int ReserveTicket = 50;  //The number of tickets reserved is 50
    final int AddTime = 100;       //The time of adding a ticket is 100
    
	private ticket t = null;

	public reAddTicket(){}
	public reAddTicket(ticket ti)
    {
		this.t = ti;
	}

	public void run()
	{
		for(int i = ReserveTicket ; i > 0 ; i--)
		{
			synchronized(t)
			{
				t.addTicket();
				System.out.println("One ticket is re-added! " + t.getTicketNumber() + " ticket" + " left. ");
			}
			try
			{
				Thread.sleep(AddTime);         //Re-adding a ticket takes a while
			}        
			catch ( InterruptedException exception ){}
		}

	}
}
