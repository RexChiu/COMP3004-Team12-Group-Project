Author:     Rex Chiu, Hector Zhang
Student#:   100904495, 100947935
Date:       Wed. April 6
Instructor: Jean-Pierre Corriveau

Source Files:
	readme.txt: 									Introduction of Project
	Ivanhoe - Final Report.docx:					The doc version of the final report
	Ivanhoe - Final Report.pdf:						The pdf version of the final report
	Iteration 2 Correction Grid.xlsx:				The correction grid of the project
	Images:											The folder of images
		Class Responsibility Collaborator Models:	The CRC card for Ivanhoe
		Class Diagram.pdf 							PDF of the class diagram for game logic
		Ivanhoe Rule Book.pdf						PDF of the rule book for Ivanhoe
		Use Case Diagram.pdf 						PDF of the user case diagram for Ivanhoe
		Rule Book Highlighted:						The folder of Rule Book highlighted
		UML 2.0:									The folder of UML2.0
	JARS											The folder of the jar version project for evaluating
		ServerIvanhoe.jar							The jar version of the program which is the server of the game
		ClientIvanhoe.jar 							The jar version of the program which is the client of the game
	Test Driver										The folder of Test Driver
		ClientIvanhoe.jar 							The Test driver of client which represent the player
		1.Unhorse.jar 								The Test driver of demonstrate how the Unhorse work
		2.ChangeWeapon.jar 							The Test Driver of demonstrate how the Change Weapon work
		3.DropWeapon.jar 							The Test Driver of demonstrate how the Drop Weapon work
		4.BreakLance.jar 							The Test Driver of demonstrate how the BreakLance work
		5.Riposte.jar 								The Test Driver of demonstrate how the Riposte work
		6.Dodge.jar 								The Test Driver of demonstrate how the Dodge work
		7.Retreat.jar 								The Test Driver of demonstrate how the Retreat work
		8.KnockDown.jar 							The Test Driver of demonstrate how the Knock Down work
		9.Outmaneuver.jar 							The Test Driver of demonstrate how the Outmaneuver work
		10.Charge.jar 								The Test Driver of demonstrate how the Charge work
		11.CounterCharge.jar 						The Test Driver of demonstrate how the Counter-Charge work
		12.Disgrace.jar 							The Test Driver of demonstrate how the Disgrace work
		13.Outwit.jar 								The Test Driver of demonstrate how the Outwit work
		14.ShieldStunned.jar 						The Test Driver of demonstrate how the Shield and Stunned work

Summarizes What we had done for the project:
	Completed Documentation For the Project of Ivanhoe
	Completed Test Plan for corresponding Test Procedure of Ivanhoe
		All action cards were implemented excepted the "Adapt"
		All tournaments were evaluted.
		All scenarios were tested.
	Our project does not include any AI Strategies.
	Unit Test is only done for the Test Plan, not including Bells and whistles.
	Bells and Whistles:
		GUI feature implemented including special feature:
			Right Click on the face-up Card will demonstrated the Large view of the card.
		Roubust Networking implemented for handling loss of a player
			Server shut down if any of players quit and send message to rest of the players
		Roubustness of game implemented for player cannot play out of the turns
			Rule Engine verify the incoming message for player ID
		Tournament Panel in teh central of the UI: 
			Display players order, status and display total
	Test Driver:
		The Test Plan for all action cards that were evaluted:
		
Application Introduction:
	ServerIvanhoe:
		Start:
			Server could start the game and waiting for the client join
			Server Start is not valid if the server is already running
		Stop:
			Server could terminate the game and shut down the server
			Serevr Stop is not valid if the server is not running
		Setting:
			Number of player:	The textfield for modify number of player
			IP:					The textfield for modify the IP
			Port:				The textfield for modify the Port
			Confirm:			It is only confirmed if the number of player is between 2 and 5
			Cancel:				Cancel the setting of Network
	
	ClientIvanhoe:
		Menu: Client -> Client Join:
			Client could jion the server if and only if the server is running
			Client Join is not valid if the server is not running
		Menu: Client -> Client Quit:
			Client could quit the server in any time if the server is running
			Client Quit is not valid if the server is not running
		Menu: Setting -> Edit Network:
			Number of player:	The textfield for modify number of player
			IP:					The textfield for modify the IP
			Port:				The textfield for modify the Port
			Confirm:			It is only confirmed if the number of player is between 2 and 5
			Cancel:				Cancel the setting of Network

	Player(ClientIvanhoe):
		When the game is running. The player will be able to play the game by clicking a card in their hand and it is able to play the selected the card. The player is limited to selecting one card at a time, but can play as many cards as they want per turn. After the card is selected the player needs click the "Play Card" button to update the action to the Rule Engine in the server. Eveytime the player wants to end their turn, they need to click the "End Turn" button. Apart of Play Card and End Turn, There are some dialog pop-ups such as, select tournament color, play or withdraw, choose tokens, and etc.

		Player could check what the tournament color is in the center.

		Robustness Play Card and End Turn:
			Both actions of Play Card and End Turn would automatically be evaluated by the Rules Engine. In another word, the action will be affect if and only if it their turn, and the action is valid.
		The UI will automatically update the client if it receives a message from the server. In another word, the UI is always up to time whenever the current state is. For example, when a player chooses a tournament color, it will automatically update it to everybody.

Execuction Process:
	Run ServerIvanhoe.jar file either double click or running in the terminal(Recommanded).
	Run ClientIvanhoe.jar file either double click or running in the terminal(Recommanded).
	The number of the ClientIvanhoe would depend on how many player will be join into the game


	In Local PC:
		Server in the ServerIvanhoe could click Start -> Yes to start the game.
		Player in the ClientIvanhoe could click Menu: Client -> Client Join to join the game.

	Over the Networking:
		Server in the ServerIvanhoe could click Setting to modify the Networking Setting
		Server in the ServerIvanhoe could click Start -> Yes to start the game.
		Player in the ClientIvanhoe could click Menu: Setting -> Edit Network to modify the IP address and Port
		Player in the ClientIvanhoe could click Menu: Client -> Client Join to join the game.


	When all player wase joined to the game the server will automatically start the game and the player will receive the message to start the game.


	When the game is runing:
		Playing the card will be required to selected the card and click play card to play the card if its current player's turn
		Player some action card will require to selected target player's display or target player's name("Info")
		** If some action is not selected the target player or display cards or the status, it will be automaticall cout no vaild
		There are two status in from the each player, they will be used to represent the status of shield and stunned.


	Test Driver:
		Running 1.Unhorse.jar as the server and Run 2 ClientIvanhoe as the players or 5 if needed.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose purple tournament color.
			First player could just choose to play the game.
			First player need play one tournament color card or supporter card
			And after that First player could play action card Unhorse
			There will be a dialog to pop up to the First Player to choose what color will be change to red, blue or yellow
			And the tournament color will corresponding to change the choose color to all player


		Running 2.ChangeWeapon.jar as the server and Run 2 ClientIvanhoe as the players or 5 if needed.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose red, blue or yellow tournament color.
			First player could just choose to play the game.
			First player need play one tournament color card or supporter card
			And after that First player could play action card Change Weapon
			There will be a dialog to pop up to the First Player to choose what color will be change tournament color to  a different one of red, blu or yewllo
			And the tournament color will corresponding to change the choose color to all player

		Running 3.DropWeapon.jar as the server and Run 2 ClientIvanhoe as the players or 5 if needed.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose red, blue, or yellow tournament color.
			First player could just choose to play the game.
			First player need play one tournament color card or supporter card
			And after that First player could play action card Drop Weapon
			And the tournament color will change to green to all player

		Running 4.BreakLance.jar as the server and Run 2 ClientIvanhoe as the players or 5 if needed.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose purple tournament color.
			First player could just choose to play the game.
			First player need play purple card or supporter card
			and First player click end turn
			Second player will choose play and play many purple cards
			After than second player end the turn
			First player choose to play the tournament
			First player select the Break Lance, and the target ID(Info) and click play card
			Then it will immediatly remove all purple cards from his display

		Running 5.Riposte.jar as the server and Run 2 ClientIvanhoe as the players or 5 if needed.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose any tournament color.
			First player could just choose to play the game.
			First player need play tournament color card or supporter card
			Then First player click end turn
			Second player will choose play and play any tournament cards or supportter card
			After than second player click End Turn
			First player choose to play the tournament
			First player select Riposte, and the target's ID(Info) and click play card
			Then it will immediatly get the last cards from his display to the hand

		Running 6.Dodge.jar as the server and Run 2 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Dodge will be able play
			Select the Dodge, select the target's display's card and select play card
			Then it will discard the card to deadwood.

		Running 7.Retreat.jar as the server and Run 2 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Retrate will be able play
			Select the Retreat, select the own display's card and select play card
			Then player will take the card back.

		Running 8.KnockDown.jar as the server and Run 2 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Knock Down will be able play
			Select the Knock Down, select the own display's ID(Info) and select play card
			Then it would draw at random one card fron target's hand.

		Running 9.Outmaneuver.jar as the server and Run 2-5 ClientIvanhoe as the players.
			All Players(ClientIvanhoe) click Client Join to join the game.
			First player must choose any tournament color.
			First player could just choose to play the game.
			First player need play tournament color card or supporter card
			Then First player click end turn
			and rest player to play the game and untill
			the First player choose to play the tournament
			First player select Outmaneuver, then it will immediatly remove the last cards played on their display

		Running 10.Charge.jar as the server and Run 2-5 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Charge will be able play
			First player play Charge and identify the lowest value card throught all displays. All players must dicard all cards of this value from their displays

		Running 11.CounterCharge.jar as the server and Run 2-5 ClientIvanhoe as the players.
			Similar to prevesious and after the action card CounterCharge will be able play
			First player play CounterCharge and identify the lowest value card throught all displays. All players must dicard all cards of this value from their displays

		Running 12.Disgrace.jar as the server and Run 2-5 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Disgrace will be able play
			First player play Disgrace and Each player must remove all his supporters from his display.

		Running 13.Outwit.jar as the server and Run 2-5 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Outwit will be able play
			First player play Outwit and Each player must remove all his supporters from his display.

		Running 14.ShieldStunned.jar as the server and Run 2-5 ClientIvanhoe as the players.
			Similar to prevesious and after the action card Shield or Stunned will be able play
			First player play Stunned, and slected taget's ID(Info), or play the Shield and selct Play Card to update the status.