package game;

/**
 * With a given board it finds all possible combo of Game tokens
 * that are next to each other in a pattern of N for each player 
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: Oct 24, 2013 - creation (jkidney)
 */

import java.util.*;

import players.Player;

public class GameTokenComboFinder 
{
  private ArrayList<GameTokenCombo> player1 = new ArrayList<GameTokenCombo>();
  private ArrayList<GameTokenCombo> player2 = new ArrayList<GameTokenCombo>();
  private ConnectNBoard board = null;
  private int comboLength;
	
  public GameTokenComboFinder(ConnectNBoard board, int comboLength)
  {
	this.board = board;  
	this.comboLength = comboLength;
	player1 = getAllCombosFromEachToken(Player.PLAYER_ONE);
	player2 = getAllCombosFromEachToken(Player.PLAYER_TWO);
  }
 
   
  public ArrayList<GameTokenCombo> getPlayer1Combos() { return player1; }


  public ArrayList<GameTokenCombo> getPlayer2Combos() { return player2; }


/**
   * Checks to see if the given player has won the game. A win is determined by
   * having at least one combo of length comboLength 
   * @param whichPlayer the player to check the win for
   * @param comboLength the length of a winning combo
   * @return true if the player has won false otherwise
   */
public boolean didPlayerWin(Player whichPlayer, int comboLength)
  {
	  boolean result = false;
	  ArrayList<GameTokenCombo> checkList = null;
      checkList = (whichPlayer == Player.PLAYER_ONE) ? player1 : player2;
	  
      for(GameTokenCombo combo : checkList)
    	  if(combo.size() >= comboLength) 
    	  { 
    		  result = true; 
    		  break; 
    	  }
    		
	  return result;
  }
  
  /**
   * Returns an array list that contains all possible combos for the given player
   * @return
   */
  private ArrayList<GameTokenCombo> getAllCombosFromEachToken(Player whichPlayer)
  {
  	ArrayList<GameTokenCombo> combos = new ArrayList<GameTokenCombo>();
  	ArrayList<GameToken> playerTokens = null;
  	
  	if(whichPlayer == Player.PLAYER_ONE)
  		playerTokens = board.getPlayer1Tokens();
  	else
  		playerTokens = board.getPlayer2Tokens();
  	
 
  	for(GameToken current: playerTokens)
  		combos.addAll( checkForPossibleCombosAt(current) );
  		    	  
  	return filterCombos(combos);
  }

  /**
   * Generates all the offset values to use when checking for possible combos 
   * @return 
   */
private int[][][] generateOffSets()
  {
	  int offsets[][][] = new int[8][comboLength][2]; // [# paths to check][# points to check][#row col offset]
  
	  for(int i=1; i <= comboLength; i++)
	  {
		  
		  offsets[0][i-1][0] =      i; offsets[0][i-1][1] =      0; // strait above current position
		  offsets[1][i-1][0] = (-1*i); offsets[1][i-1][1] =      0; // strait below current position
		  offsets[2][i-1][0] =      0; offsets[2][i-1][1] =      i; // strait to the right of the current position
		  offsets[3][i-1][0] =      0; offsets[3][i-1][1] = (-1*i); // strait to the left of the current position
		  
		  //Possible diagonal paths
		  offsets[4][i-1][0] =      i; offsets[4][i-1][1] =      i; 
		  offsets[5][i-1][0] = (-1*i); offsets[5][i-1][1] = (-1*i); 
		  offsets[6][i-1][0] = (-1*i); offsets[6][i-1][1] =      i; 
		  offsets[7][i-1][0] =      i; offsets[7][i-1][1] = (-1*i); 
	  }
	  	  
   return offsets;
  }
  
  /**
   * checks for all possible combos from the given location
   * @param location the location to check
   * @return the list of found combos
   */
private ArrayList<GameTokenCombo> checkForPossibleCombosAt(GameToken location)
  {
  	ArrayList<GameTokenCombo> combos = new ArrayList<GameTokenCombo>();
  	
  	//offsets to check for combos
  	int offsets[][][] = generateOffSets();
  
  	for(int[][] offsetPath : offsets)
  	{
  		GameTokenCombo result = checkPath(location, offsetPath);
  		if(result != null)
  			combos.add(result);
  	}
  	
  	return combos;
  }

  /**
   * checks for a combo along a given plat of offsets
   * @param location the location to check from
   * @param offsets the set of offsets to check at
   * @return any combo that was found 
 */
private  GameTokenCombo checkPath(GameToken location, int[][] offsets)
  {
  	GameTokenCombo result = new GameTokenCombo();
  	
  	result.add(location);
  	for(int offset[] : offsets)
  	{
  		int nrow = location.getRow() + offset[0];
  		int ncol = location.getCol() + offset[1];
  		
  		GameToken checkLoc = board.getTokenAt(nrow, ncol);
  		
  		if(checkLoc != null) // make sure a token is at the given location
  		{
  			//make sure the token is the current players token
  			if(checkLoc.getWhichPlayer() == location.getWhichPlayer())
  			{
  				result.add(checkLoc);
  			}
  			else // Token from different player found so exit loop
  			{
  			  break;
  			}
  		}
  		else // no token at location so stop
  			break;
  	}

  	//if result list is only size one then return null, otherwise return the
  	//combo found.
  	if(result.size() == 1)
  	{
  		result.clear();
  		result = null;
  	}
  	
  	return result; 
  }


  /**
   * filters out duplicate combos 
   * @param allCombos al combos that were found
   * @return a new filtered list of combos
   */
  @SuppressWarnings("unchecked")
private ArrayList<GameTokenCombo> filterCombos(ArrayList<GameTokenCombo> allCombos)
  {
	  ArrayList<GameTokenCombo> duplicateList = (ArrayList<GameTokenCombo>) allCombos.clone();
	 
	  for(int i=0; i < allCombos.size()-1; i++)
	  {
		  GameTokenCombo c1 = allCombos.get(i);	
		 for(int j=i+1; j < allCombos.size(); j++ )
		 {
			 GameTokenCombo c2 = allCombos.get(j);
			 if(c1.containsSubCombo(c2))
			     duplicateList.remove(c2);
		 }
	  }
	  
	  allCombos.clear();
	  return duplicateList;
  }
  

  
@Override
public String toString() 
{
	String nl = System.getProperty("line.separator");
	String result = "";
	
	result = "All Combos"+nl+"{"+nl;
	
	result += nl + "\tPlayer 1:"+nl;
	for(GameTokenCombo combo : player1)
	     result += "\t\t" + combo + nl;
			
	result += nl+ "\tPlayer 2:"+nl;
	for(GameTokenCombo combo : player2)
	     result += "\t\t" + combo + nl;
	
	result += nl + "}" + nl;
	return result; 
}
  
  
  
}
