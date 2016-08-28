package filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by eden on 28/08/2016.
 */
public class UI {

    Scanner scanner = new Scanner(System.in);
    FileSystem fileSystem;

    public UI(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    private void addFile(){
        String fileName = parseFileName();
        if (fileName == null) return;

        String directory = parseDirName();
        System.out.println("Enter the size of " + fileName +" : ");
        String size = scanner.nextLine();

        if (isPathValid(fileName, directory)){
            try {
                int sizeInt = Integer.parseInt(size);
                fileSystem.addFile(directory, fileName, sizeInt);
                System.out.println("File Added.");
            } catch (NumberFormatException e){
                System.out.println("Size must be numeric. Failed to add new file. try again... ");
            }
        }
        else{
            System.out.println(String.format("Error: File name '%s' is already in use or '%s' is not a directory", fileName, directory));
        }

    }

    private String parseDirName() {
        System.out.println("Enter parent directory name (or 'root' if you have not added any): ");
        return scanner.nextLine();
    }

    private void addDirectory(){
        String newDir = parseFileName();
        if (newDir == null) return;
        String parentDir = parseDirName();

        if(isPathValid(newDir, parentDir)){
            fileSystem.addDirectory(parentDir, newDir);
            System.out.println("Directory Added.");
        }
        else{
            System.out.println(String.format("Error: Directory name '%s' is already in use or '%s' is not a directory", newDir, parentDir));
        }

    }
    private boolean isPathValid(String newFile, String parent){

        return (!fileSystem.isNameTaken(newFile) && fileSystem.isDirectory(parent));
    }

    private String parseFileName() {
        System.out.println("Enter new file/directory name: ");
        String fileName = scanner.nextLine();
        if (fileName.length() > 32){
            System.out.println("Error: file name cannot be more than 32.");
            return null;
        }
        return fileName;
    }

    private void delete(){
        System.out.println("Enter file name to remove its parent directory: ");
        String fileName = scanner.nextLine();

        if (fileSystem.isNameTaken(fileName) && !fileSystem.isDirectory(fileName)){
            if(fileSystem.delete(fileName)){
                System.out.println(String.format("Parent directory of %s was removed successfully", fileName));
            }
        }
        else{
            System.out.println(String.format("Error:'%s' is not a file or it could not be found.", fileName));

        }

    }

    private void showFileSystem(){
        fileSystem.showFileSystem();
        System.out.println("\n");
    }

    private void showActions(){

        List<String> actions = new ArrayList<>();

        actions.add("Add a file to existing directory");
        actions.add("Create new directory");
        actions.add("Delete a file (and its parent directory)");
        actions.add("Show all file system");

        System.out.print("---------------------------------------------------------------\n");

        int index = 1;
        for (String action : actions) {
            System.out.println(index + ". " + action);
            index++;
        }
        System.out.print("Enter the action number you would like to perform (q to quit) : ");


    }

    public void start(){



        System.out.print("-------------------------- WELCOME ----------------------------\n");

        while (true) {

            showActions();

            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("Exit!");
                break;
            }

            System.out.println("You have selected: " + input);
            System.out.print("---------------------------------------------------------------\n");

            switch (input) {
                case "1": // add file
                    addFile();
                    break;
                case "2": // add directory
                    addDirectory();
                    break;
                case "3": // remove parent directory of a file
                    delete();
                    break;
                case "4": // show all file system
                    showFileSystem();
                    break;
                default:
                    System.out.println("No such input. Try again");
                    break;

            }
        }
        scanner.close();
    }


}
