
// Java Room Adventure
// Jadon Newton, Alex Orgeron, Nicholas Sanders, Hayden Mersky
// AI FOR DEBUGGING AND SYNTAX HELP
// SEE README
import java.util.Scanner;


import java.io.Serializable;

class Inventory implements Serializable {
    private String[] items;

    public Inventory(int size) {
        items = new String[size];
    }
    
    // End of RoomAdventure class

    public boolean addItem(String item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false; // Inventory full
    }

    public boolean removeItem(String item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].equalsIgnoreCase(item)) {
                items[i] = null;
                return true;
            }
        }
        return false; // Item not found
    }

    public boolean contains(String item) {
        for (String i : items) {
            if (i != null && i.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public void display() {
        System.out.print("Inventory: ");
        for (String item : items) {
            if (item != null) {
                System.out.print(item + " ");
            }
        }
        System.out.println();
    }

    public String[] getItems() {
        return items;
    }
}

public class RoomAdventure { // Main class containing game logic

    // Class variables
    private static Room currentRoom; // The room the player is currently in
    private static Inventory inventory = new Inventory(5); // Player inventory slots
    private static String status; // Message to display after each action
    private static Player player = new Player(10, 2);
    private static Enemy mad_king = new Enemy(50, 15);

    // Constants
    final private static String DEFAULT_STATUS = 
        "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include 'go', 'look', and 'take', 'equip', and 'use'."; // Default error message
 
        public void displayWelcomeMessage() {
        System.out.println("Welcome to the Room Adventure Game!");
        System.out.println("You are in a mysterious castle. Explore, find items, and defeat the Mad King!");
        System.out.println("Type 'help' for a list of commands.");
    }
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
        if (noun.equalsIgnoreCase(items[i])){
            status = itemDescriptions[i];
            if (noun.equalsIgnoreCase("Man") && currentRoom.name.equals("Outside Gate")){
                System.out.println(status);
                startCombat(player, mad_king);
            }
        }
    }
}

private static void handleTake(String noun){
    String[] grabbables = currentRoom.getGrabbables();
    status = "I can't grab that.";
    for (String item : grabbables){
        if (noun.equalsIgnoreCase(item)){
            for (int j = 0; j < 5; j++){
                if (inventory.addItem(noun)) {
                    status = "Added it to inventory";
                    break;
                }
            }
        }
    }
}

private static void handleEquip(Player player, String noun, Inventory inventory){
    status = "I can't equip that.";
    boolean found = false; // Allows it to stop when found so it doesn't tell you 4 times that you don't have it

    for (String equippable: inventory.getItems()){ // For item in inventory
        if (equippable != null && equippable.equalsIgnoreCase(noun)){ // If it isn't null and it equals what you typed
            player.equip(noun); // Equip it
            status = "You equipped the " + noun + ".";
            found = true; // Change found to stop from repeating you don't have it
            System.out.println("New Health: " + player.getHealth()); // Print new health
            System.out.println("New Damage: " + player.getAttack()); // Print new damage
            break;

        }  
    
    } if (found == false){ // If you don't have it
            status = ("You don't have a " + noun + "."); // Don't equip
        }
    
}

private static void handleUse(String noun) {
    if (inventory.contains(noun)) {
        if (noun.equalsIgnoreCase("Potion")) {
            player.increaseHealth(10); // Increase health by 10
            inventory.removeItem(noun); // Remove from inventory since it's used
            status = "You used a Potion and restored 10 health.";
        } else if (noun.equalsIgnoreCase("Gem")) {
            player.increaseHealth(20); // Increase health by 20
            inventory.removeItem(noun); // Remove from inventory since it's used
            status = "You used the Gem and restored 20 health.";
        } else {
            status = "You can't use " + noun + ".";
        }
    } else {
        status = "You don't have a " + noun + " to use.";
    }
}

private static void handleHelp(){
    System.out.println("Valid commands:");
    System.out.println("go [direction]  - Move to another location.");
    System.out.println("look [item]     - Look at an item to get a description of it.");
    System.out.println("take [item]     - Take an item and add it to your inventory.");
    System.out.println("equip [item]    - Equip an item from your inventory.");
    System.out.println("help            - Lists all possible actions.");
    System.out.println("quit            - Quit the game.");
}

private static void handleQuit(){
    System.exit(0);
}

private static void setupGame(){
    Room courtyard = new Room("Courtyard");
    Room barracks = new Room("Barracks");
    Room armory = new Room("Armory");
    Room throneroom = new Room("Throne Room");
    Room guardtower = new Room("Guard Tower");
    Room gate = new Room("Outside Gate");

    // Courtyard setup
    String[] courtyardExitDirections = {"north", "east", "south", "west"};
    Room[] courtyardExitDestinations = {throneroom, barracks, gate, guardtower};
    String[] courtyardItems = {"Arrows", "Dead_Knight", "Training_Dummy", "Banners", "Potion"};
    String[] courtyardItemDescriptions = {"Arrows fired by a sieging army litter the ground.", 
                                          "A knight lies dead in the grass with arrows piercing his body; his hands still wrap around the hilt of a sword.",
                                          "An old and battered training dummy, an arrow remains stuck in the bullseye.",
                                          "2 banners flank a massive iron gate; you can't recall what lord they represent.",
                                          "A small potion that seems to glow slightly, restorative powers are rumored.",};
    String[] courtyardGrabbables = {"Sword", "Potion"};
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
                                       "An armor rack is bolted to the wall. Most sets are empty, but one full set of ornate, gold-trimmed plate armor still remains. In its chest lies an empty socket."};
    String[] armoryGrabbables = {"Armor"};
    armory.setExitDirections(armoryExitDirections);
    armory.setExitDestinations(armoryExitDestinations);
    armory.setItems(armoryItems);
    armory.setItemDescriptions(armoryItemDescriptions);
    armory.setGrabbables(armoryGrabbables);

    // Throne Room setup
    String[] throneroomExitDirections = {"south"};
    Room[] throneroomExitDestinations = {courtyard}; 
    String[] throneroomItems = {"Throne", "Carpet", "Painting", "Potion"};
    String[] throneroomItemDescriptions = {"A gold and silver throne covered in sprawling and intricate patterns. It seems to have been inhabited very recently.",
                                           "An ornate carpet sprawls from the entrance to the foot of the throne. It seems to bear the same patterns as the banners.",
                                           "A massive painting covers most of the wall, seemingly depicting the royal family.",
                                           "A small potion that seems to glow slightly, restorative powers are rumored."};
    String[] throneroomGrabbables = {"Potion"};
    throneroom.setExitDirections(throneroomExitDirections);
    throneroom.setExitDestinations(throneroomExitDestinations);
    throneroom.setItems(throneroomItems);
    throneroom.setItemDescriptions(throneroomItemDescriptions);
    throneroom.setGrabbables(throneroomGrabbables);

    // Guard Tower setup
    String[] guardtowerExitDirections = {"east"};
    Room[] guardtowerExitDestinations = {courtyard}; 
    String[] guardtowerItems = {"Spyglass, Creaky_Board"};
    String[] guardtowerItemDescriptions = {"Picking up the spyglass, you look beyond the gate. All of the knights lay on the ground, their weapons beside them. Strangely though, when looking into one's helmet you realize all of the armor is empty.",
                                           "You investigate the board, realizing it's lose. Upon prying it up, you find a deep blue gem; as you pick it up, it seems to hum with power (take gem)."};
    String[] guardtowerGrabbables = {"Gem"};
    guardtower.setExitDirections(guardtowerExitDirections);
    guardtower.setExitDestinations(guardtowerExitDestinations);
    guardtower.setItems(guardtowerItems);
    guardtower.setItemDescriptions(guardtowerItemDescriptions);
    guardtower.setGrabbables(guardtowerGrabbables);

    // Gate setup
    String[] gateExitDirections = {};
    Room[] gateExitDestinations = {}; 
    String[] gateItems = {"Man"};
    String[] gateItemDescriptions = {"The gate quickly slams shut behind you.\nYou see a man with a sword ahead of you, a black mist seems to be slowly crawling up his body. He emanates a royal presence, urging you to submit.\n\n"};
    String[] gateGrabbables = {null};
    gate.setExitDirections(gateExitDirections);
    gate.setExitDestinations(gateExitDestinations);
    gate.setItems(gateItems);
    gate.setItemDescriptions(gateItemDescriptions);
    gate.setGrabbables(gateGrabbables);

    currentRoom = courtyard; // Start in courtyard
}

public static void startCombat(Player player, Enemy enemy){
    Scanner scanner = new Scanner(System.in);
    System.out.println("He rushes you!");

    while (player.isAlive() && enemy.isAlive()){
        System.out.println("Choose:\n1: Attack\n2: Submit");
        String input = scanner.nextLine(); // Read user input

        if (input.equalsIgnoreCase("1")){
            // Player attacks
            enemy.takeDamage(player.getAttack());

            if (!enemy.isAlive()){
                System.out.println("The Mad King crumples to the ground in front of you, defeated.\nYou begin to follow the road, searching for civilization.\n ---------------------GAME OVER--------------------- ");
                System.exit(0);
            }

            // Enemy attacks back
            player.takeDamage(enemy.getAttack());
            if (!player.isAlive()){
                System.out.println("The Mad King's blade pierces your skin and you fall to the ground.\nThe black mist begins to spread from your wound, and you feel it begin to corrupt you before everything fades.\n ---------------------GAME OVER--------------------- ");
                System.exit(0);
            }
            
        }   else if (input.equalsIgnoreCase("2")){
            System.out.println("The Mad King's blade pierces your skin and you fall to the ground.\nThe black mist begins to spread from your wound, and you feel it begin to corrupt you before everything fades.\n ---------------------GAME OVER--------------------- ");
            System.exit(0);
        }   else {
            System.out.println("Invalid Input.");
        }
    }
}

public static void main(String[] args) {
    setupGame();
    while (true) {
        System.out.println(currentRoom.toString()); // Print current room
        inventory.display(); // Use Inventory class to display inventory

        System.out.println("\nWhat would you like to do?"); // Prompt for action
        Scanner s = new Scanner(System.in);
        String input = s.nextLine(); // Read user input
        String[] words = input.split(" "); // Split input into words

        if (words.length != 2) { // Check if input is valid
            if (words[0].equals("quit")) {
                handleQuit();
                continue;
            } else if (words[0].equals("help")) {
                handleHelp();
                continue;
            } else {
                status = DEFAULT_STATUS; // Print default error message
                continue; // Skip to next iteration
            }
        }

        String verb = words[0]; // First word is the verb
        String noun = words[1]; // Second word is the noun

        switch (verb.toLowerCase()) { // Handle different verbs
            case "go":
                handleGo(noun.toLowerCase()); // Move to another room
                break;
            case "look":
                handleLook(noun.toLowerCase()); // Look at an item
                break;
            case "equip":
                handleEquip(player, noun.toLowerCase(), inventory); // Equip an item
                break;
            case "take":
                handleTake(noun.toLowerCase()); // Take an item
                break;
            case "use":
                handleUse(noun.toLowerCase());
                break;
            case "help":
                handleHelp();
                break;
            case "quit":
                handleQuit();
                break;
            default:
                status = DEFAULT_STATUS; // Print default error message
        }

        System.out.println(status); // Print status message
        
        }   
        
    } // End of main method

} // End of RoomAdventure class

class Room { // Represents a game room
    public String name; // Room name
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

class Player {

    private int health = 10; // Health without armor = 10
    private int attack = 2; // Damage without weapon = 2
    public boolean isAlive = true; // Start alive

    // Constructor
    public Player(int health, int attack){
        this.health = health;
        this.attack = attack;
    }

    // Allows you to equip the 3 equippables, granting health or damage
    public void equip(String item){
        if (item.equalsIgnoreCase("Sword")){
            this.attack += 3;
        }
        else if (item.equalsIgnoreCase("Armor")) {
            this.health += 10;
        }
        else if (item.equalsIgnoreCase("Gem")) {
            this.health += 30;
            this.attack += 5;
        }
    }

    // Function to take damage and print current health
    public void takeDamage(int dmg){
        this.health -= dmg;
        System.out.println("You took " + dmg + " damage.");
        System.out.println("Health: " + this.health);
    }

    public void increaseHealth(int amount){
        this.health += amount;
        System.out.println("You restored "+ amount + " health.");
        System.out.println("Current Health: " + this.health);
    }

    // Checks if you're alive
    public boolean isAlive(){
        return health > 0;
    }

    // Getters for attack and health
    public int getAttack() {
        return this.attack;
    }

    public int getHealth() {
        return this.health;
    }
} // End of player class

class Enemy {

    private String name = "Mad King";
    private int health = 50; // Health without armor = 10
    private int attack = 15; // Damage without weapon = 2
    public boolean isAlive = true; // Start alive

    // Constructor
    public Enemy(int health, int attack){
        this.health = health;
        this.attack = attack;
    }

    // Function to take damage and print current health
    public void takeDamage(int dmg){
        this.health -= dmg;
        System.out.println(name + " takes " + dmg + " damage.");
        System.out.println("Health: " + this.health + "\n");
    }

    // Checks if you're alive
    public boolean isAlive(){
        return health > 0;
    }

    // Getters for attack, health, and name
    public int getAttack() {
        return (int)(Math.random() * 5) + 8;
    }

    public int getHealth() {
        return this.health;
    }

    public String getName() {
        return this.name;
    }


}