//Created by Lawrence Zhou on 12/23/2014


public class ticket      //Class of ticket
{
	private int TicketNumber;      //TicketNumber records the number of tickets left
	
	public ticket(int n)           //initialize the TicketNumber
	{
		TicketNumber=n;
	}
	
	public int getTicketNumber()   //get the value of TicketNumber
	{
		return TicketNumber;
	}
	
	public void addTicket()     //adding-ticket function
	{
		TicketNumber++;
	}
	
	public void sellTicket()    //selling-ticket function
	{
		TicketNumber--;
	}

}
