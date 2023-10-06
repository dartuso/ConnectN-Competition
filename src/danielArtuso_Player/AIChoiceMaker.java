package danielArtuso_Player;

/**
 * AI Game Player
 * 
 * @author Daniel Artuso
 * @version 2 
 * 
 *      Created: Oct 24, 2013
 * Last Updated: November 5, 2017
 */

import players.ConnectNChoiceMaker;
import players.Player;
import game.*;

public class AIChoiceMaker implements ConnectNChoiceMaker {


	/**
	 * This method will return what column to drop the GameToken in for the game of
	 * ConnectN. The choice must be a valid column, otherwise the players turn is
	 * forfeit. The method is given a duplicate copy of the current game board.
	 * 
	 * @param board
	 *            copy of the current game board
	 * @param whichPlayerAmI
	 *            indicator of which player in the game you are
	 * @param otherPlayer
	 *            indicator of which player the other entity is in the game
	 * @param winLength
	 *            the win length for a combo
	 * @return the column to place the players GameToken in
	 */
	public int playTurn(ConnectNBoard board, Player whichPlayerAmI, Player otherPlayer, int winLength) {
		int depth = 2;
		int bestValue = Integer.MIN_VALUE;
		int col = 0;
		
		for (int i = 0; i < board.numCols(); i++) {
			if (!board.isColFull(i)) {
				ConnectNBoard minimaxBoard = new ConnectNBoard(board);
				minimaxBoard.addGameToken(whichPlayerAmI, i);
//				switch comments to used minimax
//				int v = minimax(minimaxBoard, whichPlayerAmI, otherPlayer, winLength, false,
//				 depth);
				int v = alphabeta(minimaxBoard, whichPlayerAmI, otherPlayer, winLength, false, depth, Integer.MIN_VALUE,
						Integer.MAX_VALUE);
				bestValue = Math.max(bestValue, v);
				if (bestValue == v) {
					col = i;
				}
			}
		}
		return col;
	}


	/* Alphabeta pruning algorithm currently in use with eval function*/
	public int alphabeta(ConnectNBoard board, Player me, Player other, int winLength, boolean isMaximizingPlayer,
			int depth, int alpha, int beta) {
		GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);
		if (depth == 0 || finder.didPlayerWin(me, winLength) || finder.didPlayerWin(other, winLength)
				|| finder.didPlayerWin(Player.NEITHER, winLength)) {
			return eval(board, winLength, me, other);
		}
		if (isMaximizingPlayer) {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < board.numCols(); i++) {
				if (!board.isColFull(i)) {
					ConnectNBoard copyBoard = new ConnectNBoard(board);
					copyBoard.addGameToken(me, i);
					v = Math.max(v, alphabeta(copyBoard, me, other, winLength, false, depth - 1, alpha, beta));
					alpha = Math.max(alpha, v);
					if (beta <= alpha) {
						break;
					}
				}
			}
			return v;
		} else {
			int v = Integer.MAX_VALUE;
			for (int i = 0; i < board.numCols(); i++) {
				if (!board.isColFull(i)) {
					ConnectNBoard copyBoard = new ConnectNBoard(board);
					copyBoard.addGameToken(other, i);
					v = Math.min(v, alphabeta(copyBoard, me, other, winLength, true, depth - 1, alpha, beta));
					beta = Math.min(beta, v);
					if (beta <= alpha) {
						break;
					}
				}
			}
			return v;
		}
	}
	
	/* minimax search algorithm, not in use but included for posterity */
	public int minimax(ConnectNBoard board, Player me, Player other, int winLength, boolean isMaximizingPlayer,
			int depth) {
		GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);
		if (depth == 0 || finder.didPlayerWin(me, winLength) || finder.didPlayerWin(other, winLength)
				|| finder.didPlayerWin(Player.NEITHER, winLength)) {
			return eval(board, winLength, me, other);
		}
		if (isMaximizingPlayer) {
			int bestValue = Integer.MIN_VALUE;
			for (int i = 0; i < board.numCols(); i++) {
				if (!board.isColFull(i)) {
					ConnectNBoard copyBoard = new ConnectNBoard(board);
					copyBoard.addGameToken(me, i);
					int v = minimax(copyBoard, me, other, winLength, false, depth - 1);
					bestValue = Math.max(bestValue, v);
				}
			}
			return bestValue;
		} else {
			int bestValue = Integer.MAX_VALUE;
			for (int i = 0; i < board.numCols(); i++) {
				if (!board.isColFull(i)) {
					ConnectNBoard copyBoard = new ConnectNBoard(board);
					copyBoard.addGameToken(other, i);
					int v = minimax(copyBoard, me, other, winLength, true, depth - 1);
					bestValue = Math.min(bestValue, v);
				}
			}
			return bestValue;
		}
	}

	/* first iteration eval, currently in use */
	public int eval(ConnectNBoard board, int winLength, Player me, Player other) {
		GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);
		if (finder.didPlayerWin(me, winLength)) {
			return Integer.MAX_VALUE;
		} else if (finder.didPlayerWin(other, winLength)) {
			return Integer.MIN_VALUE;
		} 

		
		//Get largest amounts of combos and return the largest amount - with positive or negative based if it is mine or not  
		if (me == Player.PLAYER_ONE) {
			if (finder.getPlayer1Combos().size() > finder.getPlayer2Combos().size()) {
				return finder.getPlayer1Combos().size();
			} else {
				return -finder.getPlayer2Combos().size();
			}
		} else {
			if (finder.getPlayer1Combos().size() > finder.getPlayer2Combos().size()) {
				return -finder.getPlayer1Combos().size();
			} else {
				return finder.getPlayer2Combos().size();
			}
		}
	}
	
	/* second iteration included for posterity */
	public int unusedEval(ConnectNBoard board, int winLength, Player me, Player other) {
		GameTokenComboFinder finder = new GameTokenComboFinder(board, winLength);

		if (finder.didPlayerWin(me, winLength)) {
			return Integer.MAX_VALUE;
		} 
		if (finder.didPlayerWin(other, winLength)) {
			return Integer.MIN_VALUE;
		} 
		
		int p1score = 0;
		int p2score = 0;
		for (GameTokenCombo combo : finder.getPlayer1Combos()) {
			p1score = (int) (p1score + (Math.pow(10, combo.size())));
		}
		for (GameTokenCombo combo : finder.getPlayer2Combos()) {
			p2score = p2score + (int) (Math.pow(10, combo.size()));
		}
		if (me == Player.PLAYER_ONE) {
			return (p1score - p2score);
		} else {
			return (p2score - p1score);
		}
		
		
	}
	
	
	/**
	 * Called if multiple games are being played and values should be reset
	 */
	@Override
	public void reset() {

	}

	/**
	 * Gives the name used when displaying information about this specific choice
	 * maker
	 * 
	 * @return
	 */
	public String getName() {
		return "Daniel Artuso";
	}
}
