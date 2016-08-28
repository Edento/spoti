package filesystem;

import java.util.Scanner;

/**
 * Created by eden on 28/08/2016.
 */


public class Runner {

    public static void main(String[] args) {

        FileSystem fileSystem = new FileSystem();
        fileSystem.initFileSystem();

        UI userInterface = new UI(fileSystem); // handles all request to the tree
        userInterface.start();

    }

}
