package app;

import message.Message;

public class DataFormat {

	public static void main(String[] args) {
	
		// Data Format for apply Button pressed
		Message message = new Message();
		message.getHeader().sender = "Player ID";
		message.getHeader().receiver = "Ivanhoe";
		message.getHeader().state = 0;
		message.getHeader().type = "Play Card";
		message.getBody().addField("Selected Hand Index", "Index");
		message.getBody().addField("Selected Display Index", "Index or Shield or Stunned");
		message.getBody().addField("Selected Target", "NULL or ID");
		message.getBody().addField("Selected Target Display Index", "index or Shield or Stunned");		
		// Fixing the Selected Card Index and Target Display Index
		
		// Server RE:
		// All players are joined
		// Setup
		//   Call RE setup
		//   Update display to all players
		//   send first player selects color 
		
		// Handle
		//   Pass message to RE
		//   get Response from RE
		
		// Check if response not null
		//   update display to all players

		
		// RE:
		// Setup:
		//   initialize deck
		//   initialize hands
		//   initialize playerOrder
		//   initialize and store first player
		//   deal card to first player
		//   update store to select color
		
		// Deal Card:
		//   check deck is empty refill by discards deck and shuffle
		//   deal card to player
		
		// Select Color:
		//   update tournament colors
		//   update tournament color for all display
		//   update state to play or withdraw
		//   return data of play or withdraw
				
		// Play or Withdraw
		//   check player's choice
		//   if play
		//     update state to play card
		//     return null;
		//   else withdraw
		//     set player to withdraw
		//     decrement remaining player
		// 
		//     if remaining player is only one left
		//       check winner(helper function)
		//     else
		//       increment current player until non withdraw player (do while loop)
		//       deal card to current player
		
		// check winner(helper function)
		//     win if only one left
		//       if tournament color if purple
		//     	  update state to win tournament 
		//        return data of win tournament with possible tokens
		//     else
		//       check current player with his tokens
		//       if current player wins
		//         update state to game over
		//         return data of game over
		//       else
		//         update state to start tournament 
		//         return data of start tournament		
		
		// play card
		//  limit the first player cannot play action card as first card
		//  get player message
		//  if selected card is simple card
		//    add card to display and remove from hand
		//  else if selected card is action card
		//    if action card is valid
		//      handle action cards (many cases)
		
		// end turn
		//   if the total value of current player display is not highest 
		//     set player to withdraw
		//     decrement player left
		//     check winner(helper function)
		//   do while loop until non withdraw player
		//     increment current player
		//     deal card
		//   update state to player or withdraw
		//   return data of play or withdraw
		
		// win tournament
		//   get select color from current player
		//   add token to current player
		//   check current player with his tokens
		//   if current player wins
		//     update state to game over
		//     return data of game over
		//   else
		//     update state to select color 
		//     return data of select color			
	}

}
