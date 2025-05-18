
import java.util.Scanner;

public class RoomAdventure { // Main class containing game logic

    // Class variables
    private static Room currentRoom; // The room the player is currently in
    private static String[] inventory = {null, null, null, null, null}; // Player inventory slots
    private static String status; // Message to display after each action

    // Constants
    final private static String DEFAULT_STATUS = 
        "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include 'go', 'look', and 'take'."; // Default error message

    private static void handleGo(String noun) {
        String[] exitDirections = currentRoom.getExitDirections();
        Room[] exitDestinations = currentRoom.getExitDestinations();
        status = "I don't see that room.";
        for (int i = 0; i < exitDirections.length; i++){
            if (noun.equals(exitDirections[i])){
                currentRoom = exitDestinations[i];
                status = "Changed Room";
            }
        }
    }

private static void handleLook(String noun){
    String[] items = currentRoom.getItems();
    String[] itemDescriptions = currentRoom.getItemDescriptions();
    status = "I don't see that item.";
    for (int i = 0; i < items.length; i++){
        if (noun.equals(items[i])){
            status = itemDescriptions[i];
        }
    }
}

private static void handleTake(String noun){
    String[] grabbables = currentRoom.getGrabbables();
    status = "I can't grab that.";
    for (String item : grabbables){
        if (noun.equals(item)){
            for (int j = 0; j < inventory.length; j++){
                if (inventory[j] == null){
                    inventory[j] = noun;
                    status = "Added it to inventory";
                    break;
                }
            }
        }
    }
}

private static void setupGame(){
    Room room1 = new Room("Room 1");
    Room room2 = new Room("Room 2");
    Room room3 = new Room("Room 3");
    Room room4 = new Room("Room 4");
   

    // Room 1 setup
    String[] room1ExitDirections = {"east", "south"};
    Room[] room1ExitDestinations = {room2, room4};
    String[] room1Items = {"chair", "desk"};
    String[] room1ItemDescriptions = {"A wooden chair", "A sturdy desk"};
    String[] room1Grabbables = {"key"};
    room1.setExitDirections(room1ExitDirections);
    room1.setExitDestinations(room1ExitDestinations);
    room1.setItems(room1Items);
    room1.setItemDescriptions(room1ItemDescriptions);
    room1.setGrabbables(room1Grabbables);

    // Room 2 setup
    String[] room2ExitDirections = {"west", "south"};
    Room[] room2ExitDestinations = {room1, room3}; 
    String[] room2Items = {"Fireplace", "Rug"};
    String[] room2ItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] room2Grabbables = {"map"};
    room2.setExitDirections(room2ExitDirections);
    room2.setExitDestinations(room2ExitDestinations);
    room2.setItems(room2Items);
    room2.setItemDescriptions(room2ItemDescriptions);
    room2.setGrabbables(room2Grabbables);

    // Room 3 setup
    String[] room3ExitDirections = {"north", "west"};
    Room[] room3ExitDestinations = {room2, room4}; 
    String[] room3Items = {"Fireplace", "Rug"};
    String[] room3ItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] room3Grabbables = {"map"};
    room3.setExitDirections(room3ExitDirections);
    room3.setExitDestinations(room3ExitDestinations);
    room3.setItems(room3Items);
    room3.setItemDescriptions(room3ItemDescriptions);
    room3.setGrabbables(room3Grabbables);

    // Room 4 setup
    String[] room4ExitDirections = {"north", "east"};
    Room[] room4ExitDestinations = {room1, room3}; 
    String[] room4Items = {"Fireplace", "Rug"};
    String[] room4ItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] room4Grabbables = {"map"};
    room4.setExitDirections(room4ExitDirections);
    room4.setExitDestinations(room4ExitDestinations);
    room4.setItems(room4Items);
    room4.setItemDescriptions(room4ItemDescriptions);
    room4.setGrabbables(room4Grabbables);

    currentRoom = room1; // Start in room 1
}

public static void main(String[] args) {
    setupGame();

    while (true){
        System.out.println(currentRoom.toString()); // Print current room
        System.out.print("Inventory: "); // Print status message

        for (int i = 0; i < inventory.length; i++){
            System.out.print(inventory[i] + " "); // Print inventory
        }

        System.out.println("\nWhat would you like to do?"); // Prompt for action
        Scanner s = new Scanner(System.in);
        String input = s.nextLine(); // Read user input
        String[] words = input.split(" "); // Split input into words

        if (words.length != 2){ // Check if input is valid
            status = DEFAULT_STATUS; // Print default error message
            continue; // Skip to next iteration
        }

        String verb = words[0]; // First word is the verb
        String noun = words[1]; // Second word is the noun

        switch (verb) { // Handle different verbs
            case "go":
                handleGo(noun); // Move to another room
                break;
            case "look":
                handleLook(noun); // Look at an item
                break;
            case "take":
                handleTake(noun); // Take an item
                break;
            default:
                status = DEFAULT_STATUS; // Print default error message
        }

        System.out.println(status); // Print status message
        
        }   
        
    } // End of main method

} // End of RoomAdventure class

class Room { // Represents a game room
    private String name; // Room name
    private String[] exitDirections; // Directions you can go
    private Room[] exitDestinations; // Rooms reached by each direction
    private String[] items; // Items visible in the room
    private String[] itemDescriptions; // Descriptions for those items
    private String[] grabbables; // Items you can take

    public Room(String name){ // Constructor
        this.name = name; // Set the room's name
    }

    public void setExitDirections(String[] exitDirections){ // Setter for exits
        this.exitDirections = exitDirections;
    }

    public String[] getExitDirections(){ // Getter for exits
        return exitDirections;
    }

    public void setExitDestinations(Room[] exitDestinations){ // Setter for exits
        this.exitDestinations = exitDestinations;
    }

     public Room[] getExitDestinations(){ // Getter for exit destinations
        return exitDestinations;
    }

    public void setItems(String[] items){ // Setter for items
        this.items = items;
    }

    public String[] getItems(){ // Getter for items
        return items;
    }

    public void setItemDescriptions(String[] itemDescriptions){ // Setter for item descriptions
        this.itemDescriptions = itemDescriptions;
    }

    public String[] getItemDescriptions(){ // Getter for item descriptions
        return itemDescriptions;
    }

    public void setGrabbables(String[] grabbables){ // Setter for grabbables
        this.grabbables = grabbables;
    }

    public String[] getGrabbables(){ // Getter for grabbables
        return grabbables;
    }

    @Override
    public String toString(){ // Custom print for the room
        String result = "\nLocation: " + name; // Show room name
        result += "\nYou See: "; // List items
        for (String item: items){ // Loop items
            result += item + " "; // Append each item
        }
        result += "\nExits: "; //List exits
        for (String direction: exitDirections){ // Loop exits
            result += direction + " "; // Append each direction
        }
        return result + "\n"; //Return full description
    }
} // End of Room class