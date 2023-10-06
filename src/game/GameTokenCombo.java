package game;
import java.util.ArrayList;

/**
 * Represents a single combo of connected game tokens  
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */

public class GameTokenCombo 
{
  private ArrayList<GameToken> combo = new ArrayList<GameToken>();
  
  public void add(GameToken token) { combo.add(token); }
  public int size() { return combo.size(); }
  public void clear() { combo.clear(); }
  public GameToken get(int index) { return combo.get(index); }
  
  public boolean contains(GameToken cont)
  {
	  return combo.contains(cont);
  }
  
  public boolean containsSubCombo(GameTokenCombo c2)
	  {
		  boolean result = false;
		  int matchCount = 0;
		  
		  if(size() >= c2.size())
		  {				  
			  for(int i = 0; i < size(); i++ )
			  {
				  if( c2.contains( get(i) ))
					  matchCount++;
			  }
		  
			  if(matchCount == c2.size())
				  result = true;
		  
			  
		  }
		  
		  return result;
	  }
  
  @Override
public String toString() {
	return "[combo=" + combo + "]";
}
  
  
}

