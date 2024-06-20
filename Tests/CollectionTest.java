import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

import static org.junit.Assert.*;

public class CollectionTest {

    Collection collection;
    Scanner scanner;


    @org.junit.Before
    public void setUp() throws Exception {
        collection = new Collection();
        List<String> library = new ArrayList<>();
        String temp = "Drones,The Sufferer and the Witness,7,2006,3:01,Tim McIlrath,false,21";
        library.add(temp);
        collection.scanner = new Scanner(System.in);
        collection.createCollection(library);
    }



    @org.junit.Test
    public void addSong() throws Exception {
        collection.addSong();
        assertEquals("Gethsemane",collection.songs[1].title);
        assertEquals("The Unraveling",collection.songs[1].album);
        assertEquals(18,collection.songs[1].track);
        assertEquals(2001,collection.songs[1].year);
        assertEquals("2:30",collection.songs[1].length);
        assertEquals("Tim McIlarth",collection.songs[1].writer);
        assertEquals(false,collection.songs[1].single);
        assertEquals(2,collection.songCount);
    }

    @org.junit.Test
    public void removeSong() throws Exception {
        assertEquals(1,collection.songCount);
        collection.removeSong();
        assertEquals(0,collection.songCount);
    }

    @org.junit.Test
    public void editSong() throws Exception {
        collection.editSong();
        assertEquals(2002, collection.songs[0].year);
        collection.editSong();
        assertEquals(4,collection.songs[0].track);
    }

    @org.junit.Test
    public void makeSingle() throws Exception {
        collection.makeSingle();
        assertEquals(false,collection.songs[0].single);
        collection.makeSingle();
        assertEquals(true,collection.songs[0].single);
    }
}