// Updated RoomAdventure with Inventory as a class
import java.util.Scanner;

public class RoomAdventure { // Main class containing game logic

    // Class variables
    private static Room currentRoom; // The room the player is currently in
    private static Inventory inventory = new Inventory(); // Player inventory
    private static String status; // Message to display after each action

    // Constants
    final private static String DEFAULT_STATUS = 
        "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include 'go', 'look', and 'take'."; // Default error message

    private static void handleGo(String noun) {
        String[] exitDirections = currentRoom.getExitDirections();
        Room[] exitDestinations = currentRoom.getExitDestinations();
        status = "I don't see that room.";
        for (int i = 0; i < exitDirections.length; i++){
            if (noun.equalsIgnoreCase(exitDirections[i])){
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
            if (noun.equalsIgnoreCase(items[i].replace("_", " ")) || noun.equalsIgnoreCase(items[i])){
                status = itemDescriptions[i];
            }
        }
    }

    private static void handleTake(String noun){
        String[] grabbables = currentRoom.getGrabbables();
        status = "I can't grab that.";
        for (String item : grabbables){
            if (noun.equalsIgnoreCase(item.replace("_", " ")) || noun.equalsIgnoreCase(item)){
                if (inventory.addItem(item)){
                    status = "Added it to inventory";
                } else {
                    status = "Inventory full.";
                }
                break;
            }
        }
    }

    private static void setupGame(){
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");

        String[] room1ExitDirections = {"east", "west"};
        Room[] room1ExitDestinations = {room2};
        String[] room1Items = {"chair", "desk"};
        String[] room1ItemDescriptions = {"A wooden chair", "A sturdy desk"};
        String[] room1Grabbables = {"key"};
        room1.setExitDirections(room1ExitDirections);
        room1.setExitDestinations(room1ExitDestinations);
        room1.setItems(room1Items);
        room1.setItemDescriptions(room1ItemDescriptions);
        room1.setGrabbables(room1Grabbables);

        String[] room2ExitDirections = {"west"};
        Room[] room2ExitDestinations = {room1}; 
        String[] room2Items = {"Fireplace", "Rug"};
        String[] room2ItemDescriptions = {"A cozy fireplace", "A soft rug"};
        String[] room2Grabbables = {"map"};
        room2.setExitDirections(room2ExitDirections);
        room2.setExitDestinations(room2ExitDestinations);
        room2.setItems(room2Items);
        room2.setItemDescriptions(room2ItemDescriptions);
        room2.setGrabbables(room2Grabbables);

        currentRoom = room1; // Start in room 1
    }

    public static void main(String[] args) {
        setupGame();

        while (true){
            System.out.println(currentRoom.toString()); // Print current room
            System.out.print("Inventory: "); // Print status message

            System.out.println(inventory); // Print inventory

            System.out.println("\nWhat would you like to do?"); // Prompt for action
            Scanner s = new Scanner(System.in);
            String input = s.nextLine(); // Read user input
            String[] words = input.split(" ", 2); // Split input into 2 words

            if (words.length != 2){ // Check if input is valid
                status = DEFAULT_STATUS; // Print default error message
                System.out.println(status);
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

class Inventory { // Represents the player's inventory
    private String[] items = new String[5]; // Inventory slots

    public boolean addItem(String item){ // Adds an item to inventory
        for (int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                return true;
            }
        }
        return false; // Inventory full
    }

    @Override
    public String toString(){ // Returns string representation of inventory
        String result = "";
        for (String item : items){
            result += (item == null ? "-" : item) + " "; // Show item or placeholder
        }
        return result.trim(); // Trim trailing space
    }
} // End of Inventory class
