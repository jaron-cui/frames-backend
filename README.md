# frog-games
game

Dev Notes:

HTTP endpoints are to be used for top-level website interactions, such as joining or leaving games (POST) or retrieving player data or statistics like win rate (GET).

POST requests should only have a response body for synchronous data. So, for example, we have a POST endpoint for creating a game lobby. Currently, this endpoint only returns a status code. When the lobby is set up, the websocket sends a message to the player with lobby details.

The websocket endpoint should be used for game-level updates such as player input. Any asynchronous messages should be sent to users through the websocket.

So, we should stick to these two conventions:
1. HTTP for static/top level endpoints (/joinGame/...)
2. Websocket for receiving and sending game updates/asynchronous messages