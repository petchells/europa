# JScythe - Scythe Board Game Daemon

A Java 25 daemon application that plays the board game Scythe and responds to API requests.

## Backstory

The ashes from the first Great War still darken the snow in 1920s Europa. The capitalistic city-state known simply as "The Factory," which fueled the war with heavily armored mechs, has closed its doors, drawing the attention of several nearby countries. With characters from five factions converging onto a small but highly desired swath of land, who will gain fame and fortune by establishing their empire as the leader of Eastern Europa?

## Features

- REST API server with client/server architecture
- Game sessions stored in SQLite database
- Simple API key authentication for players and admin
- Admin endpoints to create games and distribute player keys
- Heartbeat endpoint with RFC3339 UTC timestamps
- Support for 5 playable factions
- Turn-based gameplay with human and AI players

## Board and Territories

The Scythe board consists of 25 hexagonal locations:

### Playable Territories (16)

Each territory is a hex labeled with one of 6 terrain types. The terrain type determines what resources are available and how the territory functions.

#### Primary Terrain Types (5)

These are the five main terrain types found throughout the board:

- **FARM** - Agricultural land producing food resources
- **FOREST** - Wooded terrain with timber resources
- **MOUNTAIN** - Rocky terrain with metal/mineral resources
- **TUNDRA** - Cold frozen wasteland with limited resources
- **VILLAGE** - Populated settlements with diverse benefits

#### Special Terrain Type (1)

- **FACTORY** - The central Factory location (special)

### Special Locations (9)

Nine additional locations on the board including lakes and The Factory, which are not playable territories.

### Home Bases

Home bases are distinct from playable territories and represent each faction's base of operations. Key rules and notes:

- **Not a Territory**: Home bases are separate board locations and do not count as playable territories for control or resource scoring.
- **Ownership**: A home base may be assigned to a faction (owner) or remain unassigned/neutral. Ownership can be changed via the API for setup, testing, or special effects.
- **Expansion Placeholders**: Some home bases may act as expansion placeholders; they are modelled but may not have in-game effects until expansions are implemented.
- **Gameplay Use**: Home bases can be used for setup positions, faction-specific bonuses, or to track special state; concrete rule effects depend on optional modules and expansions.

API Endpoints for Home Bases:

- **GET** `/api/board/homebase/{id}` - Get information about a specific home base (id-based)
- **GET** `/api/board/homebases` - Get all home bases on the board
- **POST** `/api/board/homebase/{id}/owner?factionName=F` - Assign a faction owner to a home base (use faction enum name)

Examples:

- Fetch all home bases: `GET /api/board/homebases`
- Set home base 1 to Rusviet: `POST /api/board/homebase/1/owner?factionName=RUSVIET`

### Territory State

Each territory tracks:

- **Terrain type** - The hex terrain classification (primary or special)
- **Control** - Which faction currently controls it (if any)
- **Structures** - Whether a structure has been built there
- **Resources** - Current resource token count on the territory

### Tokens and Pieces

- **1 Action Token** - Used to track which action the player selected on their turn
- **1 Popularity Token** - Tracks position on the Popularity Track (0-18)
- **1 Power Token** - Tracks position on the Power Track (0+)
- **6 Star Tokens** - Awarded for completing achievements (6 needed to win)
- **4 Structure Tokens** - Used to build structures on the board
- **4 Recruit Tokens** - Used to recruit soldiers and bolster strength
- **4 Mech Tokens** - Used to deploy war mechas on the board
- **1 Character Miniature (Piece)** - The player's leader/representative on the board
- **8 Worker Tokens** - Used to place workers on territories for resource production
- **8 Technology Cubes** - Used to advance technology on the player's mat

These components are managed throughout the game and track the player's development and progress toward victory.

### Core Components

- **Game Board** - 25 locations with 16 playable territories
- **Resource Tokens** - Wood, Oil, Food, and Metal tokens for resource management
- **Encounter Tokens** - 12 tokens representing special encounters on the board

### Card Decks (105 total cards)

- **Combat Cards** - 42 cards for resolving combat encounters
- **Objective Cards** - 23 secret objective cards for players to complete
- **Encounter Cards** - 28 cards for board encounters and events
- **Factory Cards** - 12 cards representing Factory events

### Bonus and Tracking

- **Structure Bonus Tiles** - 6 location-specific bonus tiles for structure placement rewards
- **Player Mats** - 5 unique faction player mats with action rows
- **Progress Tracks** - Popularity, Power, and other advancement tracks

## End-Game Scoring

The game's primary objective is to accumulate the greatest fortune by the end. A typical winning fortune is around **$75**.

### Fortune Calculation

Total Fortune = Coins from game + Scoring bonuses

Scoring is based on:

1. **Stars placed** (1 coin each, affected by popularity)
2. **Territories controlled** (1 coin each, affected by popularity)
3. **Resources controlled** (1 coin per 2 resources, affected by popularity)
4. **Structure bonuses** (fixed coins based on structure placement)

### Popularity Multiplier

Your popularity level affects how many coins you earn from stars, territories, and resources:

| Popularity | Coins Per Achievement |
| ---------- | --------------------- |
| 0-3        | 1 coin                |
| 4-7        | 2 coins               |
| 8-11       | 3 coins               |
| 12-15      | 4 coins               |
| 16-18      | 5 coins               |

Example: If you have 6 stars and 16 popularity, you earn 6 × 5 = 30 coins from stars alone.

### Coin Tracking

Coins can be accumulated during gameplay through:

- Trading and commerce actions
- Special bonus cards
- Resource conversion

End-game scoring adds coins based on your final board state.

A player wins when they place their 6th star on the Triumph Track. Stars can be earned by accomplishing any of the following:

- **Complete all 6 upgrades**: Fully upgrade your faction
- **Deploy all 4 mechs**: Place all mechanical units on the board
- **Build all 4 structures**: Construct all available structures
- **Enlist all 4 recruits**: Recruit the full complement of soldiers
- **Have all 8 workers on the board**: Deploy your entire workforce
- **Reveal 1 completed objective card**: Complete a secret objective
- **Win combat**: Up to 2 stars can be earned from victorious battles (1 per win)
- **Have 18 popularity**: Reach maximum popularity
- **Have 16 power**: Accumulate 16 power

### Player Progress Tracking

Each player's progress is tracked across:

- Upgrades (0-6)
- Mechs (0-4)
- Structures (0-4)
- Recruits (0-4)
- Workers (0-8)
- Combat wins (0-2)
- Popularity (0+)
- Power (0+)
- Objective completion status

## Factions and Player Mats

At the beginning of the game, each player chooses a player mat, which represents their faction for the entire game. There are 5 factions, each with a unique player mat that defines their faction-specific abilities, mecha unit, and available actions. Each player mat has 4 action rows with different combinations of 2 actions each.

### The Five Factions

When a player selects a player mat, they play as that faction:

- **Nordic** - The viking traders with mercantile prowess. Actions emphasize Gain and trading with Move, Produce, Recruit. Actions: Gain & Gain, Move & Gain, Produce & Gain, Recruit & Bolster

- **Saxony** - The industrial machine of precision and efficiency. Actions include Move, Produce, Upgrade, Bolster. Actions: Move & Produce, Move & Produce, Upgrade & Move, Produce & Bolster

- **Polania** - The agricultural heartland with steady growth. Actions include Move, Produce, Bolster, Gain, Build. Actions: Move & Produce, Move & Bolster, Gain & Produce, Build & Bolster

- **Crimea** - The nomadic warriors with rapid mobility. Actions emphasize Move with Gain, Produce, Bolster. Actions: Move & Gain, Move & Gain, Move & Produce, Move & Bolster

- **Rusviet** - The red bear of communism with devastating military strength. Actions include Move, Bolster, Gain, Recruit. Actions: Move & Bolster, Move & Bolster, Gain & Gain, Recruit & Bolster

Each faction has:

- A unique mecha unit
- 4 available action rows (player chooses one action row per turn)
- Faction-specific bonuses and abilities
- 4 tokens (mechs, structures, recruits) to deploy during the game

## Game Sessions and Authentication

### Admin Workflow

1. **Create a Game Session** (admin only):

   ```
   POST /api/admin/game
   Headers: Authorization: Bearer <admin-key>
   Response: { "sessionId": "uuid", "adminKey": "key" }
   ```

2. **Configure the Game** (admin only):

   ```
   POST /api/admin/game/{sessionId}/configure
   Headers: Authorization: Bearer <admin-key>
   Body: { "players": [...] }
   ```

3. **Generate Player API Keys** (admin only):

   ```
   POST /api/admin/game/{sessionId}/player/{playerPosition}/key
   Headers: Authorization: Bearer <admin-key>
   Response: { "playerPosition": 1, "apiKey": "player-key" }
   ```

4. **Send Player Keys to Human Players**:
   Distribute each player's API key securely out-of-band.

### Player Workflow

All player API requests require the API key:

```
Authorization: Bearer <player-api-key>
```

Example: Get player components

```
GET /api/player/1/components
Headers: Authorization: Bearer <player-api-key>
```

### Admin-Only Endpoints

- **POST** `/api/admin/game` - Create a new game session
- **POST** `/api/admin/game/{sessionId}/configure` - Configure game players and factions
- **POST** `/api/admin/game/{sessionId}/player/{playerPosition}/key` - Generate API key for a player
- **GET** `/api/admin/game/{sessionId}` - Get session info and player key mappings

## Prerequisites

- Java 25 or higher
- Maven 3.8.0 or higher

## Building

```bash
mvn clean package
```

## Running

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

## API Endpoints

### Heartbeat

- **GET** `/api/heartbeat`
- Returns the current server timestamp in RFC3339 UTC format
- Response: `{ "timestamp": "2026-02-01T12:34:56Z" }`

### Faction Management

- **GET** `/api/faction/{factionName}/mat`
- Get the player mat for a specific faction
- Example: `/api/faction/RUSVIET/mat`
- Response: PlayerMat with faction abilities and actions

- **GET** `/api/faction/all`
- Get list of all available factions
- Response: Array of Faction enums

### Board Management

- **GET** `/api/board/territory/{index}`
- Get information about a specific territory hex
- Response: Territory object with terrain type, control, structures, and resources

- **GET** `/api/board/territories`
- Get all territories on the board
- Response: Array of all Territory objects

- **POST** `/api/board/territory/{index}/control?factionName=RUSVIET`
- Set which faction controls a territory
- Response: Updated Territory

- **POST** `/api/board/territory/{index}/structure`
- Mark a territory as having a structure built
- Response: Updated Territory

- **POST** `/api/board/territory/{index}/remove-structure`
- Remove a structure from a territory
- Response: Updated Territory

- **POST** `/api/board/territory/{index}/resources?count=N`
- Set the number of resource tokens on a territory
- Response: Updated Territory

## Game Flow

The game is turn-based. When a player's turn ends, play passes to the left (the next player in position order). Players configured as NONE are skipped.

### Current Turn Flow

1. Player is configured via `/api/game/configure` - first active player starts
2. Get current player via `/api/game/current-player`
3. Player makes their move
4. End turn via `/api/game/end-turn` - advances to next active player
5. Repeat steps 2-4

### Game Configuration

- **POST** `/api/game/configure`
- Configure players for the game (1-5 players, each as HUMAN, CPU, or NONE)
- Starts the game with the first active player
- Request body:
  ```json
  {
    "players": [
      { "position": 1, "type": "HUMAN", "name": "Alice", "faction": "SAXON" },
      { "position": 2, "type": "CPU", "name": "Bot1", "faction": "RUSVIET" },
      { "position": 3, "type": "NONE", "name": "Empty", "faction": null },
      { "position": 4, "type": "HUMAN", "name": "Bob", "faction": "POLANIA" },
      { "position": 5, "type": "CPU", "name": "Bot2", "faction": "NORDIC" }
    ]
  }
  ```
- Response: Current Game state with starting player

- **GET** `/api/game/configuration`
- Get the current game configuration
- Response: Current GameConfiguration or 404 if not started

- **GET** `/api/game/current-player`
- Get the player whose turn it is
- Response: Current Player or 404 if no game started

- **GET** `/api/game/next-player`
- Get the player who will go next
- Response: Next Player or 404 if no game started

- **POST** `/api/game/end-turn`
- End the current player's turn and advance to the next active player
- Response: New current Player or 400 if no game started

- **POST** `/api/game/reset`
- Reset the game
- Response: 200 OK

### Player Progress

- **GET** `/api/player/{playerPosition}/progress`
- Get a player's current progress and earned stars
- Response: PlayerProgress object

- **POST** `/api/player/{playerPosition}/upgrade`
- Record a player completing an upgrade
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/mech`
- Record a player deploying a mech
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/structure`
- Record a player building a structure
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/recruit`
- Record a player enlisting a recruit
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/worker`
- Record a player deploying a worker
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/combat-win`
- Record a player winning in combat (max 2)
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/complete-objective`
- Record a player completing an objective
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/popularity?value=N`
- Set a player's popularity level
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/power?value=N`
- Set a player's power level
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/coins?amount=N`
- Add coins to a player's fortune
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/territories?count=N`
- Set the number of territories a player controls
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/resources?count=N`
- Set the number of resources a player controls
- Response: Updated PlayerProgress

- **POST** `/api/player/{playerPosition}/structure-bonus?bonus=N`
- Add structure placement bonus coins
- Response: Updated PlayerProgress

- **GET** `/api/player/{playerPosition}/end-game-score`
- Calculate the player's end-game fortune based on current board state
- Response: EndGameScore with breakdown:
  ```json
  {
    "coinsEarned": 30,
    "starCoins": 12,
    "territoryCoins": 10,
    "resourceCoins": 8,
    "structureBonus": 15,
    "coinsFromGame": 20,
    "totalFortune": 65
  }
  ```

### Player Components Management

- **GET** `/api/player/{playerPosition}/components`
- Get all player component information (tokens, pieces, track positions)
- Response: PlayerComponents with token counts and track positions

- **POST** `/api/player/{playerPosition}/components/place-star`
- Place a star token (consume from player's available stars)

- **POST** `/api/player/{playerPosition}/components/place-structure`
- Place a structure token on the board

- **POST** `/api/player/{playerPosition}/components/place-recruit`
- Place a recruit token

- **POST** `/api/player/{playerPosition}/components/place-mech`
- Deploy a mech token

- **POST** `/api/player/{playerPosition}/components/place-worker`
- Place a worker token

- **POST** `/api/player/{playerPosition}/components/use-tech-cube`
- Use a technology cube for advancement

- **POST** `/api/player/{playerPosition}/components/use-action-token`
- Track action token usage for the turn

- **POST** `/api/player/{playerPosition}/components/set-popularity?position=N`
- Set the player's position on the Popularity Track (0-18)

- **POST** `/api/player/{playerPosition}/components/move-popularity?amount=N`
- Move the player's popularity token by the specified amount

- **POST** `/api/player/{playerPosition}/components/set-power?position=N`
- Set the player's position on the Power Track

- **POST** `/api/player/{playerPosition}/components/move-power?amount=N`
- Move the player's power token by the specified amount

- **POST** `/api/player/{playerPosition}/components/place-character`
- Place the player's character miniature on the board

- **POST** `/api/player/{playerPosition}/components/remove-character`
- Remove the player's character miniature from the board

The game automatically detects when a player reaches 6 stars and marks them as the winner.

## Players

Players can be configured as:

- **HUMAN**: Controlled by a human player
- **CPU**: Controlled by computer AI
- **NONE**: Not participating in the game

A valid game must have 1 to 5 active players.

## Database

Game state is stored in a local SQLite database (`jscythe.db`). The schema is automatically created on first run.

## Project Structure

```
src/
├── main/
│   ├── java/com/jscythe/
│   │   ├── JScytheApplication.java
│   │   └── api/
│   │       └── HeartbeatController.java
│   └── resources/
│       └── application.properties
└── test/
```
