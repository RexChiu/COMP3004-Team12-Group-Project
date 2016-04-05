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
	Test Driver										The folder of Test Driver


Summarizes What we had done for the project:
	Completed Documentation For the Project of Ivanhoe
	Completed Test Plan for corresponding Test Procedure of Ivanhoe
		All action cards were implemented excepted the "Adapt"
		All tournaments were evaluted.
		All scenarios were tested.
	Our project does not include any AI Strategies.
	Test Driver for each corresponding Test Plan that were evaluted.
	Unit Test is only done for the Test Plan, not including Bells and whistles.
	Bells and Whistles:
		GUI feature implemented including special feature:
			Right Click on the face-up Card will demonstrated the Large view of the card.
		Roubust Networking implemented for handling loss of a player
			Server shut down if any of players quit and send message to rest of the players
		Roubustness of game implemented for player cannot play out of the turns
			Rule Engine verify the incoming message for player ID
		
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

		Robustness Play Card and End Turn:
			Both actions of Play Card and End Turn would automatically be evaluated by the Rules Engine. In another word, the action will be affect if and only if it their turn, and the action is valid.
		The UI will automatically update the client if it receives a message from the server. In another word, the UI is always up to time whenever the current state is. For example, when a player chooses a tournament color, it will automatically update it to everybody.

Execuction Process:

