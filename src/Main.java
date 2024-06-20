import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*
        Michael N. Mitchell, CEN-3024C-31950, June 12, 2024
        Main. This acts as the start of the program. It is where the collection objects is created
        that serves as the main catalyst for the project
         */

        Collection collection = new Collection();
        collection.intro();
    }
}

class Collection {

    /*
        Michael N. Mitchell, CEN-3024C-31950, June 12 2024
        Collection. This class contains an array with all the song objects within.
        The purpose of this class is to be the main way to access and change the program.
         */
    Song[] songs;
    Scanner scanner;
    File file;
    String filename;
    int songCount;
    int newId;

    void createCollection(List<String> library){

        Song[] temp = new Song[1]; //creates a temporary array for the Song objects

        for (String eachstring : library){
            String[] current = eachstring.split(","); //splits apart each line in file by the comma
            //System.out.println(eachstring);
            Song latest = new Song(current[0], current[1], Integer.parseInt(current[2]), Integer.parseInt(current[3]),
                    current[4], current[5], Boolean.parseBoolean(current[6]), Integer.parseInt(current[7]));
            temp[temp.length-1] = latest; //adds new song to the end of the temporary array
            newId = latest.id + 1; //iterates id
            temp = Arrays.copyOf(temp, temp.length+1); //copies contents of array to bigger array
            songs = temp; //copies temp array to the main songs array
            songCount++; //updates the counter
        }
    }

    void intro(){
        /* Introduction function. This is where the program begins as it checks for the
        * file and moves to the main user interface*/
        scanner = new Scanner(System.in); //creates scanner for user input
        boolean nameStatus = false; //will only become true when file is found that exists
        file = null;
        filename = null;
        System.out.println("Welcome to the Rise Against Library Management System!\n");
        while (!nameStatus) //loops until a real file is input
        {
            System.out.println("Please input a file name ");
            filename = scanner.nextLine();
            file = new File(filename);
            if (!file.exists()) //if file not found, lets user know and gives some tips
            {
                System.out.println("File does not exist.");
                System.out.println("Make sure you're typing the name correctly and including the extension.");
                System.out.println("Example. If it ends in .txt, include the .txt\n");
            } else {
                System.out.println("File found\n");
                nameStatus = true; //if found, becomes true so while loop can be exited.
            }
        }
        List<String> listOfStrings = new ArrayList<String>(); //creates a list of strings to save file into

        try {
            listOfStrings = Files.readAllLines(Paths.get(filename));
            //receives all lines from file. Each line being a different string
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createCollection(listOfStrings);

        interaction(); //opens the main user interface
        scanner.close(); //closes scanner as there is no more need for input
    }

    void interaction() {
        // the interaction function.
        // this function is here to allow the user to see and make choices related to the library
        // It takes in a list of strings that contain the library, a scanner, and a string with the filename
        // To other functions it sends out the list of strings containing the library and the scanner.

        String input = null;
        while(!Objects.equals(input, "q")){
            //program will loop continuously until an error or user inputs a quit option.
            System.out.println("\nThe current songs in the library are: ");
            for(int i = 0; i < songCount; i++) //displays each entry of library file.
            {
                songs[i].display();
            }
            System.out.println();

            //explains the user options
            System.out.println("Please type the letter and hit enter for the action you wish to do!");
            System.out.println("A: Add a song to library");
            System.out.println("R: Remove a song from library");
            System.out.println("E: Edit a song in Library");
            System.out.println("S: Make a song a single");
            System.out.println("Q: Exit program");
            input = scanner.nextLine();
            switch (input)
            //will take user input and attempt to match with one of the options to interact with library
            {
                case "E":
                case "e":
                    editSong(); //calls function to edit songs
                    break;
                case "A":
                case "a":
                    addSong(); //calls function to add songs
                    break;
                case "Q":
                case "q":
                    break; //ends program
                case "S":
                case "s":
                    makeSingle(); //calls function to make single
                    break;
                case "R":
                case "r":
                    removeSong(); //calls function to remove songs
                    break;

            }
            FileWriter writer = getFileWriter(); //calls function to write updated library to file
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private FileWriter getFileWriter() {
        // the get file writer function
        // this function was made to save the updated library to the text file
        // it receives the list of strings with the library entries and the string with the file name
        // the function returns to the filewriter
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0; i < songCount; i++) {
            try {
                writer.write(songs[i].toString() + System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return writer;
    }

    public void addSong(){
        boolean commaCheck = false;
        String songTitle = null;
        String songAlbum = null;
        String songTrack = null;
        String songYear = null;
        String songLength = null;
        String songWriter = null;

        System.out.println("There are " + songCount + " songs in the library.");

        System.out.println("NOTE: Do not input any commas");
        while(!commaCheck)
        // to avoid errors, will not allow user to have a comma in entry.
        // Will not continue until no commas found in inputs.
        {
            System.out.println("Please enter the name of the song you wish to add");
            songTitle = scanner.nextLine();
            System.out.println("Please enter the album of the song you wish to add");
            songAlbum = scanner.nextLine();
            System.out.println("Please enter the track number of the song you wish to add");
            songTrack = scanner.nextLine();
            System.out.println("Please enter the year the song was made");
            songYear = scanner.nextLine();
            System.out.println("Please enter the minutes of the song");
            String minuteLength = scanner.nextLine();
            System.out.println("Please enter the seconds of the song");
            String secondLength = scanner.nextLine();
            System.out.println("Please enter the writer of the song");
            songWriter = scanner.nextLine();
            try{
                if((Integer.parseInt(minuteLength) >= 0) && (Integer.parseInt(minuteLength) < 60) &&
                        (Integer.parseInt(secondLength) < 60) && (Integer.parseInt(secondLength) >= 0)){
                    if(secondLength.length() < 2)
                        secondLength = "0" + secondLength;
                    songLength = minuteLength + ":" + secondLength;
                }
                else
                    songLength = ",";

                if(!songTitle.contains(",") && !songAlbum.contains(",") && !songTrack.contains(",") && !songYear.contains(",")
                        && !songLength.contains(",") && !songWriter.contains(",")) {
                    commaCheck = true; //will only continue if user has no commas
                }
                else{
                    System.out.println("At least one input has a comma, please retry without the comma");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input. Please try again");
            }
        }

        try{
        Song latest = new Song(songTitle, songAlbum, Integer.parseInt(songTrack), Integer.parseInt(songYear), songLength, songWriter, false, newId);
        Song[] temp = songs;
        temp[temp.length-1] = latest;
        songs = Arrays.copyOf(temp, temp.length+1);
        newId = latest.id + 1;
        songCount++;
        }

        catch(Exception e){
            System.out.println("The input is not correct. Please try again.");
        }
    }

    public void removeSong(){
        System.out.println("Please enter the name or id number of the song you wish to remove");
        boolean found = false;
        boolean confirmed = false;
        String answer = null;
        int j = 0;
        String searchQuery = scanner.nextLine();
        for(int i = 0; i < songCount; i++) {
            try {
                if ((songs[i].id == Integer.parseInt(searchQuery))) {
                    found = true; //converts user input to int and checks ids
                    j = i;
                }
            }
                catch (Exception e){
                    if((songs[i].title.equals(searchQuery))) {
                    found = true; //checks titles using the string user input
                    j = i;
                }
            }
        }
        if(!found)
            System.out.println("Unable to find the song you wish to remove. Please check your spelling");
        if(found) {
            System.out.println("Is " + songs[j].title + " the song you wish to remove? (Y/N)");
            answer = scanner.nextLine();
            if(answer.equals("Y") || answer.equals("y")) {
                confirmed = true;
            }
            else{
                System.out.println("Understood. Returning to main screen.");
            }
        }
        if(confirmed){
            System.out.println("Are you sure? (Y/N)");
            String doubleCheck = scanner.nextLine();
            if(doubleCheck.equals("Y") || doubleCheck.equals("y")) {
                System.out.println("Confirmed. Removing song.");
                for(int i = j; i < songCount-1; i++) {
                    songs[i] = songs[i+1];
                }
                songCount--;
                System.out.println("Song has been removed.");
            }
        }
    }

    public void editSong(){
        System.out.println("Please enter the name or id number of the song you wish to edit");
        boolean found = false;
        boolean confirmed = false;
        String answer = null;
        int j = 0;
        String searchQuery = scanner.nextLine();
        for(int i = 0; i < songCount; i++) {
            try {
                if ((songs[i].id == Integer.parseInt(searchQuery))) {
                    found = true;
                    j = i;
                }
            }
            catch (Exception e){
                if((songs[i].title.equals(searchQuery))) {
                    found = true;
                    j = i;
                }
            }
        }
        if(!found)
            System.out.println("Unable to find the song you wish to edit. Please check your spelling");
        if(found) {
            System.out.println("Is " + songs[j].title + " the song you wish to edit? (Y/N)");
            answer = scanner.nextLine();
            if(answer.equals("Y") || answer.equals("y")) {
                confirmed = true;
            }
            else{
                System.out.println("Understood. Returning to main screen.");
            }
        }
        if(confirmed){
            System.out.println("What would you like to change?");
            System.out.println("1, Title\n2, Album\n3, Track Number\n4, Year\n5, Length\n6, Writer\n");
            int changeOption = scanner.nextInt();
            scanner.nextLine();
            switch(changeOption){
                case 1:
                    System.out.println("Current title: " + songs[j].title);
                    System.out.println("Please type a new title: ");
                    answer = scanner.nextLine();
                    songs[j].title = answer;
                    break;
                case 2:
                    System.out.println("Current album: " + songs[j].album);
                    System.out.println("Please type a new album: ");
                    answer = scanner.nextLine();
                    songs[j].album = answer;
                    break;
                case 3:
                    System.out.println("Current track number: " + songs[j].track);
                    System.out.println("Please type a new track number: ");
                    answer = scanner.nextLine();
                    try{
                    songs[j].track = Integer.parseInt(answer);}
                    catch(Exception e){
                        System.out.println("Invalid track number");
                    }
                    break;
                case 4:
                    System.out.println("Current year: " + songs[j].year);
                    System.out.println("Please type a new year: ");
                    answer = scanner.nextLine();
                    try {
                        songs[j].year = Integer.parseInt(answer);
                    }
                    catch(Exception e){
                        System.out.println("Invalid year");
                    }
                    break;
                case 5:
                    System.out.println("Current length: " + songs[j].length);
                    System.out.println("How many minutes is the song?: ");
                    answer = scanner.nextLine();
                    String minuteTemp = answer;
                    System.out.println("How many seconds is the song? (0-59): ");
                    answer = scanner.nextLine();
                    String secondTemp = answer;
                    boolean isInt = false;
                    try{
                        if((Integer.parseInt(minuteTemp) >= 0) && (Integer.parseInt(minuteTemp) < 60)
                                && (Integer.parseInt(secondTemp) > 60) && (Integer.parseInt(secondTemp) <= 0 ))
                            isInt = true;
                    }
                    catch (Exception e){
                        System.out.println("Invalid input. Please input a valid number for each.");
                    }
                    if(isInt){
                        songs[j].length = minuteTemp + ":" + secondTemp;
                    }
                    else
                        System.out.println("Incorrect input. Please try again.");
                    break;
                case 6:
                    System.out.println("Current writer: " + songs[j].writer);
                    System.out.println("Please type a new writer name: ");
                    answer = scanner.nextLine();
                    songs[j].writer = answer;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + changeOption);
            }
        }
    }

    public void makeSingle(){
        {
            System.out.println("Please enter the name or id number of the song you wish to make a single");
            boolean found = false;
            boolean confirmed = false;
            String answer = null;
            int j = 0;
            String searchQuery = scanner.nextLine();
            for(int i = 0; i < songCount; i++) {
                try {
                    if ((songs[i].id == Integer.parseInt(searchQuery))) {
                        found = true;
                        j = i;
                    }
                }
                catch (Exception e){
                    if((songs[i].title.equals(searchQuery))) {
                        found = true;
                        j = i;
                    }
                }
            }

            if(!found)
                System.out.println("Unable to find the song you wish to make a single. Please check your spelling");
            if(found) {
                if (!songs[j].single) {
                    System.out.println("Is " + songs[j].title + " the song you wish to make a single? (Y/N)");
                    answer = scanner.nextLine();
                    if(answer.equals("Y") || answer.equals("y")) {
                        songs[j].single = true;
                    }
                    else{
                        System.out.println("Understood. Returning to main screen.");
                    }
                } else {
                    System.out.println(songs[j].title + " is already a single. Would you like to remove single status? (Y/N)");
                    answer = scanner.nextLine();
                    if(answer.equals("Y") || answer.equals("y")) {
                        songs[j].single = false;
                    }
                    else{
                        System.out.println("Understood. Returning to main screen.");
                    }
                }
            }
        }
    }




}

class Song {
    /*
        Michael N. Mitchell, CEN-3024C-31950, June 12 2024
        Song. This class contains the song information. One of these is made for each individual song
        that is listed in the file.
         */
    public String title;
    public String album;
    public int track;
    public int year;
    public String length;
    public String writer;
    public boolean single;
    public int id;

    Song(String title, String album, int track, int year, String length, String writer, boolean single, int id){
        this.title = title;
        this.album = album;
        this.track = track;
        this.year = year;
        this.length = length;
        this.writer = writer;
        this.single = single;
        this.id = id;
    }

    public void display(){
        String status = "Non-Single";
        if(single)
            status = "Single";
        System.out.println(title + ", " + album + ", " + track + ", " + year + ", " + length + ", " + writer + ", " + status + ", " + id);
    }

    public String toString(){
        return(title + "," + album + "," + track + "," + year + "," + length + "," + writer + "," + single + "," + id);
    }

}

