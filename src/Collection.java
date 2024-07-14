
import DBHelper.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * Collection
 * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
 * The logic portion of the program. Creates a "collection" of songs using the database
 * and also updates the database with any changes.
 */
public class Collection {
    /**
     * Collection Constructor
     * Creates a Collection object
     */
    public Collection(){}

    boolean found = false;
    boolean confirmed = false;
    String answer = null;
    int editNext; //used to determine next part of the edit function
    int remNext; //used to determine next part of the remove function
    int singNext; //used to determine next part of the single function
    int addNext; //used to determine next part of the add function
    Song[] songs; //container of song objects
    Scanner scanner;
    File file;
    String filename;
    int songCount; //used to keep track of current amount of songs
    int newId; //latest id to be assigned to a song
    boolean cont;
    String userInput; //what is taken from the gui
    String codeOutput; //whats to be passed to the gui
    int next = 0;
    String songTitle;
    String songAlbum;
    String songTrack;
    String songYear;
    String minuteLength;
    String secondLength;
    String songLength;
    String songWriter;
    int j = 0; //place holder variable
    String doubleCheck = null;
    String searchQuery = null;
    String minuteTemp = null;
    String query = null;
    String url = null;
    RiseAgainst db1 = null;

    /**
     * Collection createCollection
     * Takes the resultset of the database and creates an array of
     * song objects with each song being a different row of the
     * database.
     * @param resultSet passes in results from the database
     * @throws SQLException will catch if error with resultset
     */
    public void createCollection(ResultSet resultSet) throws SQLException
    {

        Song[] temp = new Song[1]; //creates a temporary array for the Song objects
        newId = 1;
        while (resultSet.next()){
            Song latest = new Song(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4),
                    resultSet.getString(5), resultSet.getString(6), resultSet.getBoolean(7), resultSet.getInt(8));
            temp[temp.length-1] = latest; //adds new song to the end of the temporary array
            if(latest.id > newId)
                newId = latest.id + 1; //iterates id
            temp = Arrays.copyOf(temp, temp.length+1); //copies contents of array to bigger array
            songs = temp; //copies temp array to the main songs array
            songCount++; //updates the counter
        }
    }

    /**
     * Collection setUserInput
     * Takes the input based from the gui by the controller and sets that
     * equal to the Collection's userinput variable
     * @param userInput takes in a string of what the user input
     */
    public void setUserInput(String userInput)
    {
        this.userInput = userInput;
    }

    /**
     * Collection choices
     * Takes the "next" variable and uses it to decide which part of the
     * program to call next.
     */
    public void choices()
       {
        switch(next){
            case 0:
                introInstru();
                break;
            case 1:
                colIntro();
                break;
            case 2:
                displayLibrary();
                break;
            case 3:
                interaction();
                break;
            case 4:
                editSong();
                break;
            case 5:
                addSong();
                break;
            case 6:
                quit();
                break;
            case 7:
                makeSingle();
                break;
            case 8:
                removeSong();
                break;
        }
    }

    /**
     * Collection introInstru
     * Introduction that asks for user to give filepath input
     */
    public void introInstru()
       {
        codeOutput = "\nWelcome to the Rise Against Database Management System!\nPlease type in a file path.";
        next = 1;
    }

    /**
     * Collection colIntro
     * Establishes connection with the database.
     */
    public void colIntro()
       {
        cont = false;

        url = userInput;
        query = "Select * FROM RiseAgainst";

        try{
            //if connection is established, will create a song array of the database entries
            Connection con = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            createCollection(resultSet);
            db1 = new RiseAgainst(url); //RiseAgainst object made to allow easier access to database
            next = 2;

           codeOutput = "\nSuccessfully connected!";
        }
        catch(SQLException e){
            codeOutput = "\nUnable to connect. Please double check file path.";
        }

    }

    /**
     * Collection quit
     * Shuts down the program
     */
    public void quit()
    {
        System.exit(0);
    }

    /**
     * Collection displayLibrary
     * Will go through the array of songs and display each song and the user menu options
     */
    public void displayLibrary()
    {
        codeOutput = ("\nThe current songs in the library are: \n\n");
        for(int i = 0; i < songCount; i++) //displays each songs.
        {
            codeOutput += (songs[i].toString() + "\n");
        }
        //explains the user options
        codeOutput += ("\nPlease type the letter and hit enter for the action you wish to do!\n" +
                "A: Add a song to library\n" + "R: Remove a song from library\n" + "E: Edit a song in Library\n" +
                "S: Make a song a single\n" + "Q: Exit program\n" + "\n");
        next = 3;
    }

    /**
     * Collection interaction
     * Will look at user input on the menu and update place variables so when collection
     * called again, will move to that corresponding function
     */
    public void interaction() {

        answer = null;
        found = false;

        switch (userInput)
        //will take user input and attempt to match with one of the options to interact with library
        {
            case "E":
            case "e":
                codeOutput = "\nGoing to edit songs now. Click to continue.";
                 next = 4; //calls function to edit songs
                editNext = 1;
                 break;
            case "A":
            case "a":
                codeOutput = "\nGoing to add songs now. Click to continue.";
                 next = 5; //calls function to add songs
                addNext = 1;
                 break;
            case "Q":
            case "q":
                codeOutput = "\nGoing to quit now. Click to continue.";
                next = 6; //calls function to quit
                break;
            case "S":
            case "s":
                codeOutput = "\nGoing to single now. Click to continue.";
                 next = 7; //calls function to make a single
                singNext = 1;
                 break;
            case "R":
            case "r":
                codeOutput = "\nGoing to remove now. Click to continue.";
                 next = 8; //calls function to remove songs
                remNext = 1;
                 break;
            }
        }

    /**
     * Collection addSong
     * Creates a song object, puts song object in the collection and creates a corresponding
     * row in the database.
     */
    public void addSong()
    {

        boolean commaCheck = false;

        codeOutput = ("\nThere are " + songCount + " songs in the library.\nNOTE: Do not input any commas");
        switch (addNext) {
            case 1:
                nullifyTemps();
                codeOutput += ("\nPlease enter the name of the song you wish to add");
                addNext = 2;
                break;
            case 2:
                songTitle = userInput;
                codeOutput += ("\nPlease enter the album of the song you wish to add");
                addNext = 3;
                break;
            case 3:
                songAlbum = userInput;
                codeOutput += ("\nPlease enter the track number of the song you wish to add");
                addNext = 4;
                break;
            case 4:
                songTrack = userInput;
                try{
                    if(Integer.parseInt(songTrack) > 0)  //makes sure it's a real number
                    {
                        codeOutput += ("\nPlease enter the year the song was made");
                        addNext = 5;
                    }
                }
                catch(Exception e){
                    codeOutput = ("\nInput was incorrect. Please input a valid track number. No letters or symbols.");
                }
                break;
            case 5:
                songYear = userInput;
                try{
                    if(Integer.parseInt(songYear) > 0) //makes sure it's a real year
                    {
                        codeOutput += ("\nPlease enter the minutes of the song");
                        addNext = 6;
                    }
                }
                catch(Exception e){
                    codeOutput = ("\nInput was incorrect. Please enter a valid year. No letters or symbols.");
                }
                break;
            case 6:
                minuteLength = userInput;
                try {
                    if ((Integer.parseInt(minuteLength) >= 0) && (Integer.parseInt(minuteLength) < 60))
                    //makes sure the minutes are between 0 and 59
                    {
                        addNext = 7;
                        codeOutput += ("\nPlease enter the seconds of the song"); }
                    else {
                        codeOutput = "\nPlease enter minutes between 0 and 59";
                        addNext = 5;
                        minuteLength = null;
                    }
                } catch (NumberFormatException e) {
                    codeOutput = ("\nInvalid input. Please try again");
                    addNext = 5;
                    minuteLength = null;
                }
                break;
            case 7:
                secondLength = userInput;
                try {
                    if ((Integer.parseInt(secondLength) >= 0) && (Integer.parseInt(secondLength) < 60))
                    //makes sure the seconds are between 0 and 59
                    {
                        if (secondLength.length() < 2) {
                            secondLength = "0" + secondLength;
                        }
                        codeOutput += ("\nPlease enter the writer of the song");
                        songLength = minuteLength + ":" + secondLength;
                        addNext = 8;
                    } else {
                        codeOutput = "\nPlease enter seconds between 0 and 59";
                        secondLength = null;
                    }
                } catch (NumberFormatException e) {
                    codeOutput = ("\nInvalid input. Please try again");
                    secondLength = null;
                }
                break;
            case 8:
                songWriter = userInput;
                if (!songTitle.contains(",") && !songAlbum.contains(",") && !songTrack.contains(",") && !songYear.contains(",")
                        && !songLength.contains(",") && !songWriter.contains(",")) {
                    commaCheck = true;
                } else {
                    codeOutput = ("\nAt least one input has a comma, please retry without the comma");
                    addNext = 1;
                    next = 2;
                    nullifyTemps();
                }

                if(commaCheck) {
                    try {
                        Song latest = new Song(songTitle, songAlbum, Integer.parseInt(songTrack), Integer.parseInt(songYear), songLength, songWriter, false, newId);
                        Song[] temp = songs;
                        temp[temp.length - 1] = latest;
                        songs = Arrays.copyOf(temp, temp.length + 1);
                        newId = latest.id;
                        songCount++;
                        db1.insert(songTitle,songAlbum,Integer.parseInt(songTrack),Integer.parseInt(songYear),
                                songLength,songWriter,false,newId);
                        //creates a new song object and adds song to the database
                        newId++;
                        codeOutput = "\nSong added! Please press the button to continue.\n";
                        nullifyTemps(); //sets the temp values to null for future use
                        next = 2;
                        addNext = 1;
                    } catch (Exception e) {
                        codeOutput = ("\nThe input is not correct. Please try again.\nClick to continue.\n");
                        addNext = 1;
                        next = 2;
                        nullifyTemps();
                    }
                }
                break;
            }
        }

    /**
     * Collection nullifyTemps
     * Takes the temporary song variables and sets then all to null.
     */
    public void nullifyTemps()
        /*
         * Collection nullifyTemps
         * Takes the temporary song variables and sets then all to null.
         * Arguments:
         *          None
         * Returns:
         *          None
         */
    {
        songTitle = null;
        songAlbum = null;
        songTrack = null;
        songYear = null;
        minuteLength = null;
        secondLength = null;
        songLength = null;
        songWriter = null;
    }

    /**
     * Collection removeSong
     * Removes a song from the collection and removes corresponding row from database
     */
    public void removeSong()
    {
        switch(remNext){
            case 1:
                codeOutput = ("\nPlease enter the name or id number of the song you wish to remove");
                remNext = 2;
                break;
            case 2:
                searchQuery = userInput;
                found = false;
                searchFor(searchQuery);
                if(!found) {
                    codeOutput = ("\nUnable to find the song you wish to remove. Please check your spelling");
                    remNext = 1;
                    next = 2;
                }
                else {
                    codeOutput = ("\nIs " + songs[j].title + " the song you wish to remove? (Y/N)");
                    remNext = 3;
                }
                break;
            case 3:
                answer = userInput;
                if(answer.equals("Y") || answer.equals("y")) {
                    confirmed = true;
                    codeOutput = ("\nAre you sure? (Y/N)");
                    remNext = 4;
                }
                else{
                    codeOutput = ("\nUnderstood. Returning to main screen.");
                    remNext = 1;
                    next = 2;
                }
                break;
            case 4:
                doubleCheck = userInput;
                if(confirmed){
                    if(doubleCheck.equals("Y") || doubleCheck.equals("y")) {
                        codeOutput = ("\nConfirmed. Removing song.");
                        db1.delete("ID", String.valueOf(songs[j].id));
                        //finds corresponding id within the database
                        for(int i = j; i < songCount-1; i++) {
                            songs[i] = songs[i+1];
                        }
                        remNext = 5;
                    }
                    else{
                        remNext = 1;
                        next = 2;
                        codeOutput = ("\nUnderstood. Returning to main screen.");
                    }
                }
                else{
                    remNext = 1;
                    next = 1;
                    codeOutput = ("\nUnderstood. Returning to main screen.");
                }
                break;
            case 5:
                songCount--;
                codeOutput = ("\nSong has been removed. Click the button to continue.");
                next = 2;
                remNext = 1;
                break;
        }
    }

    /**
     * Collection searchFor
     * Looks through the collection for a song with a corresponding id or title
     * @param searchQuery takes in a string of what is being looked for
     */
    private void searchFor(String searchQuery)
    {
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
    }

    /**
     * Collection editSong
     * Edits a song object and then updates the corresponding row of the database
     */
    public void editSong()
    {
        int changeOption = 0;
        switch(editNext) {
            case 1:
                minuteTemp = null;
                codeOutput = ("\nPlease enter the name or id number of the song you wish to edit");
                editNext = 2;
                break;
            case 2:
                searchQuery = userInput;
                searchFor(searchQuery);
                if(!found) {
                    codeOutput = ("\nUnable to find the song you wish to edit. Please check your spelling");
                    editNext = 1;
                    next = 2;
                }
                else{
                    codeOutput = ("\nIs " + songs[j].title + " the song you wish to edit? (Y/N)");
                    editNext = 3;
                }
                break;
            case 3:
                answer = userInput;
                if(answer.equals("Y") || answer.equals("y")) {
                    confirmed = true;
                }
                if(confirmed) {
                    codeOutput = ("\nWhat would you like to change?\n1, Title\n2, Album\n3, Track Number\n4, Year\n5, Length\n6, Writer\n");
                    editNext = 4;
                }
                else{
                    codeOutput = ("\nUnderstood. Returning to main screen.");
                    editNext = 1;
                    next = 2;
                }
                break;
            case 4:
                changeOption = Integer.parseInt(userInput);
                switch(changeOption){
                    case 1:
                        codeOutput=("\nCurrent title: " + songs[j].title + "\nPlease type a new title: ");
                        editNext = 5;
                        break;
                    case 2:
                        codeOutput= ("\nCurrent album: " + songs[j].album + "\nPlease type a new album: ");
                        editNext = 6;
                        break;
                    case 3:
                        codeOutput = ("\nCurrent track number: " + songs[j].track + "\nPlease type a new track number: ");
                        editNext = 7;
                        break;
                    case 4:
                        codeOutput = ("\nCurrent year: " + songs[j].year + "\nPlease type a new year: ");
                        editNext = 8;
                        break;
                    case 5:
                        codeOutput = ("\nCurrent length: " + songs[j].length + "\nHow many minutes is the song?: ");
                        editNext = 9;
                        break;
                    case 6:
                        codeOutput = "\nCurrent writer: " + songs[j].writer + "\nPlease type a new writer: ";
                        editNext = 11;
                        break;
                    default:
                        throw new IllegalStateException(codeOutput = ("\nUnexpected value: " + changeOption +
                                ".\nPlease click to return to the menu."));
                }
                break;
            case 5:
                answer = userInput;
                db1.update("Title",answer,"ID",Integer.toString(songs[j].id));
                songs[j].title = answer;
                codeOutput = "\nUpdated title. Please click to continue.";
                editNext = 12;
                break;
            case 6:
                answer = userInput;
                db1.update("Album",answer,"ID",Integer.toString(songs[j].id));
                songs[j].album = answer;
                editNext = 12;
                codeOutput = "\nUpdated album. Please click to continue.";
                break;
            case 7:
                answer = userInput;
                try{
                    db1.update("Track",answer,"ID",Integer.toString(songs[j].id));
                    songs[j].track = Integer.parseInt(answer);
                    editNext = 12;
                    codeOutput = "\nUpdated track. Please click to continue.";
                }
                catch(Exception e){
                    codeOutput = ("\nInvalid track number. Please click to return to menu.");
                    next = 2;
                    editNext = 1;
                }
                break;
            case 8:
                answer = userInput;
                try {
                    db1.update("Year",answer,"ID",Integer.toString(songs[j].id));
                    songs[j].year = Integer.parseInt(answer);
                    editNext = 12;
                    codeOutput = "\nUpdated year. Please click to continue.";
                }
                catch(Exception e){
                    codeOutput = ("\nInvalid year. Please click to return to menu.");
                    next = 2;
                    editNext = 1;
                }
                break;
            case 9:
                answer = userInput;
                minuteTemp = answer;
                codeOutput = ("\nHow many seconds is the song? (0-59): ");
                editNext = 10;
                break;
            case 10:
                answer = userInput;
                String secondTemp = answer;
                boolean isInt = false;
                try{
                    if((Integer.parseInt(minuteTemp) >= 0) && (Integer.parseInt(minuteTemp) < 60)
                            && (Integer.parseInt(secondTemp) < 60) && (Integer.parseInt(secondTemp) >= 0 ))
                        isInt = true;
                    if(isInt){
                        db1.update("Length",minuteTemp + ":" + secondTemp,"ID",Integer.toString(songs[j].id));
                        songs[j].length = minuteTemp + ":" + secondTemp;
                        codeOutput = "\nUpdated length. Please click to continue.";
                        editNext = 12;
                    }
                    else {
                        codeOutput = ("\nIncorrect input. Please try again.");
                        editNext = 1;
                        next = 2;
                    }
                }
                catch (Exception e){
                    codeOutput = ("\nInvalid input. Please input a valid number for each.\nClick to return to menu.");
                    editNext = 1;
                    next = 2;
                }
                break;
            case 11:
                answer = userInput;
                db1.update("Writer",answer,"ID",Integer.toString(songs[j].id));
                songs[j].writer = answer;
                codeOutput = "\nUpdated writer. Please click to continue.";
                editNext = 12;
                break;
            case 12:
                codeOutput = ("\n" + songs[j].toString() + " are the current values of the song." +
                "\nClick to return to the menu.");
                editNext = 1;
                next = 2;
                break;
            default: {
                throw new IllegalStateException(codeOutput = ("\nUnexpected value: " + editNext +
                        ".\nPlease click to return to the menu."));
            }
        }
    }

    /**
     * Collection makeSingle
     * Turns a song object into a single or undoes its single status
     */
    public void makeSingle()
    {
        {
            switch(singNext) {
                case 1:
                    codeOutput = ("\nPlease enter the name or id number of the song you wish to make a single");
                    found = false;
                    confirmed = false;
                    answer = null;
                    j = 0;
                    singNext = 2;
                    break;
                case 2:
                    String searchQuery = userInput;
                    searchFor(searchQuery);
                    if(!found) {
                        codeOutput = ("\nUnable to find the song you wish to make a single. Please check your spelling");
                        singNext = 1;
                        next = 2;
                    }
                    else {
                        if (!songs[j].single) {
                            codeOutput = ("\nIs " + songs[j].title + " the song you wish to make a single? (Y/N)");
                        }
                        else{
                            codeOutput = ("\n" + songs[j].title + " is already a single. Would you like to remove single status? (Y/N)");
                            }
                        singNext = 3;
                    }
                    break;
                case 3:
                    answer = userInput;
                    if(answer.equals("Y") || answer.equals("y")) {
                        songs[j].single = !songs[j].single;
                        if (songs[j].single) {
                            db1.update("Single","true","Title",songs[j].title);
                            codeOutput = "\n" + songs[j].title + " was made a single";
                        }
                        else {
                            db1.update("Single","false","Title",songs[j].title);
                            codeOutput = "\n" + songs[j].title + " single status was removed.";

                        }
                        codeOutput += "\nClick to continue.";
                    }
                    else{
                        codeOutput = ("\nUnderstood. Click to return to main screen.");
                    }
                    singNext = 1;
                    next = 2;
                    break;
            }
        }
    }


}

class Song {
    /**
     * Song
     * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
     * A song is an object that represents each row of the database.
     */
    public String title;
    public String album;
    public int track;
    public int year;
    public String length;
    public String writer;
    public boolean single;
    public int id;

    /**
     * Song Constructor
     * Creates a song object
     * @param title String passed into constructor
     * @param album String passed into constructor
     * @param track int passed into constructor
     * @param year int passed into constructor
     * @param length String passed into constructor
     * @param writer String passed into constructor
     * @param single boolean passed into constructor
     * @param id int passed into constructor
     */
    Song(String title, String album, int track, int year, String length, String writer, boolean single, int id)
    {
        this.title = title;
        this.album = album;
        this.track = track;
        this.year = year;
        this.length = length;
        this.writer = writer;
        this.single = single;
        this.id = id;
    }

    /**
     * Song toString
     * Converts the song object into a string that consists of all the parts with a comma between each.
     * @return String of all the items in a specific song object
     */
    public String toString()
    {
        return(title + "," + album + "," + track + "," + year + "," + length + "," + writer + "," + single + "," + id );
    }

}
