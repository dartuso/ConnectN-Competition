package gamePlayerTesters;
/**
 * Used to keep track of game results from multiple game plays
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */
public class Results implements Comparable<Results>
{
	private int wins = 0;
	private int loss = 0;
	private int tie  = 0;
	private String name;

	public Results(String name) { this.name = name; }
	
	public void incWin() { wins++; } 
	public void incLoss() { loss++; } 
	public void incTie() { tie++; }
	@Override
	public String toString() {
		return String.format("results [wins=%3d, loss=%3d, tie=%3d] : %s",wins,loss,tie,name);
	}
	
	@Override
	public int compareTo(Results other) 
	{
		int result = 0;
		
		     if(wins < other.wins ) result =  1;
		else if(wins > other.wins ) result = -1;
		else 
		{
		     if(tie < other.tie ) result =  1;
		else if(tie > other.tie ) result = -1;
		   else
		   {
			     if(loss > other.loss ) result = 1;
				    else if(loss < other.loss ) result = -1;
		   }
		}
				
		return result;
	} 

	
	
}