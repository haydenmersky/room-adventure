
// Java Room Adventure
// Jadon Newton, Alex Orgeron, Nicholas Sanders, Hayden Mersky
// SEE README

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
    Room courtyard = new Room("Courtyard");
    Room barracks = new Room("Barracks");
    Room armory = new Room("Armory");
    Room throneroom = new Room("Throne Room");
    Room guardtower = new Room("Guard Tower");
    Room gate = new Room("Gate");
   

    // Courtyard setup
    String[] courtyardExitDirections = {"north", "east", "south", "west"};
    Room[] courtyardExitDestinations = {throneroom, barracks, gate, guardtower};
    String[] courtyardItems = {"Arrows", "Dead_Knight", "Training_Dummy", "Banners"};
    String[] courtyardItemDescriptions = {"Arrows fired by a sieging army litter the ground.", 
                                          "A knight lies dead in the grass with arrows piercing his body; his hands still wrap around the hilt of a sword.",
                                          "An old and battered training dummy, an arrow remains stuck in the bullseye.",
                                          "2 banners flank a massive iron gate; you can't recall what lord they represent."};
    String[] courtyardGrabbables = {"Sword"};
    courtyard.setExitDirections(courtyardExitDirections);
    courtyard.setExitDestinations(courtyardExitDestinations);
    courtyard.setItems(courtyardItems);
    courtyard.setItemDescriptions(courtyardItemDescriptions);
    courtyard.setGrabbables(courtyardGrabbables);

    // Barracks setup
    String[] barracksExitDirections = {"east", "west"};
    Room[] barracksExitDestinations = {armory, courtyard}; 
    String[] barracksItems = {"Bunk_Beds", "Iron_Chest", "Rug"};
    String[] barracksItemDescriptions = {"Bunk beds line the walls, it appears at least 10 knights called this barracks home.",
                                         "At the foot of each bed lies an iron chest, but you notice one already slight ajar. Upon opening the chest you find a key (take key).",
                                         "A simple, dirt-covered rug covers most of the floor."};
    String[] barracksGrabbables = {"Key"};
    barracks.setExitDirections(barracksExitDirections);
    barracks.setExitDestinations(barracksExitDestinations);
    barracks.setItems(barracksItems);
    barracks.setItemDescriptions(barracksItemDescriptions);
    barracks.setGrabbables(barracksGrabbables);

    // Armory setup
    String[] armoryExitDirections = {"west"};
    Room[] armoryExitDestinations = {barracks}; 
    String[] armoryItems = {"Weapon_Rack", "Wall_Map", "Armor_Rack"};
    String[] armoryItemDescriptions = {"A weapon rack is bolted to the wall; while mostly empty, it still contains a couple swords identical to the one in the courtyard.", 
                                       "A map affixed to the wall appears to give a layout of the castle. \n\t\t Throne Room\n\n Guard Tower \tCourtyard \t Barracks \tArmory\n\n\t\t Gate",
                                       "An armor rack is bolted to the wall. Most sets are empty, but one full set of ornate, gold-trimmed plate armor still remains."};
    String[] armoryGrabbables = {"Armor"};
    armory.setExitDirections(armoryExitDirections);
    armory.setExitDestinations(armoryExitDestinations);
    armory.setItems(armoryItems);
    armory.setItemDescriptions(armoryItemDescriptions);
    armory.setGrabbables(armoryGrabbables);

    // Throne Room setup
    String[] throneroomExitDirections = {"north", "east"};
    Room[] throneroomExitDestinations = {courtyard, armory}; 
    String[] throneroomItems = {"Fireplace", "Rug"};
    String[] throneroomItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] throneroomGrabbables = {"map"};
    throneroom.setExitDirections(throneroomExitDirections);
    throneroom.setExitDestinations(throneroomExitDestinations);
    throneroom.setItems(throneroomItems);
    throneroom.setItemDescriptions(throneroomItemDescriptions);
    throneroom.setGrabbables(throneroomGrabbables);

    // Guard Tower setup
    String[] guardtowerExitDirections = {"north", "east"};
    Room[] guardtowerExitDestinations = {courtyard, armory}; 
    String[] guardtowerItems = {"Fireplace", "Rug"};
    String[] guardtowerItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] guardtowerGrabbables = {"map"};
    guardtower.setExitDirections(guardtowerExitDirections);
    guardtower.setExitDestinations(guardtowerExitDestinations);
    guardtower.setItems(guardtowerItems);
    guardtower.setItemDescriptions(guardtowerItemDescriptions);
    guardtower.setGrabbables(guardtowerGrabbables);

    // Gate setup
    String[] gateExitDirections = {"north", "east"};
    Room[] gateExitDestinations = {courtyard, armory}; 
    String[] gateItems = {"Fireplace", "Rug"};
    String[] gateItemDescriptions = {"A cozy fireplace", "A soft rug"};
    String[] gateGrabbables = {"map"};
    gate.setExitDirections(gateExitDirections);
    gate.setExitDestinations(gateExitDestinations);
    gate.setItems(gateItems);
    gate.setItemDescriptions(gateItemDescriptions);
    gate.setGrabbables(gateGrabbables);

    currentRoom = courtyard; // Start in room 1
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