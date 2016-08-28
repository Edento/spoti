package filesystem;

import java.util.*;

/**
 * Created by eden on 28/08/2016.
 */
public class FileSystem{

    public static String ROOT_DIR = "root";
    private Map<String, Node> all = new HashMap<>();
    private Tree root;


    public FileSystem() {
        // parent of root is null
        this.root = new Tree(ROOT_DIR, null);
    }


    public boolean addFile(String parentDirName, String fileName, int fileSize) {

        Tree parent = findDirectoryByName(parentDirName);

        if (parent != null) {

            // create new file (node)
            Node newNode = new Node(fileName, fileSize, parent);
            // assign the new node to its parent
            parent.getChildren().put(fileName, newNode);

            // add to indexed map
            all.put(fileName, newNode);

        }

        // parent doesnt exist
        return false;
    }

    public boolean addDirectory(String parentDirName, String dirName) {

        Tree parent = findDirectoryByName(parentDirName);

        if (parent != null) {
            // create new folder
            Tree newTree = new Tree(dirName, parent);
            // add this folder to the parent
            parent.getChildren().put(dirName, newTree);
            // add to indexed map
            all.put(dirName, newTree);
        }
        // parent dir doesnt exist
        return false;
    }

    public boolean delete(String name) {

        Node current = findFileByName(name);
        if (current == null) {
            System.out.print("File doesnt exist");
            return false;
        }
        Tree parent = current.getParent();

        if (parent == root) {
            // cant remove root
            System.out.println("Root cannot be removed");
            return false;
        }

        // remove the parent folder of 'name'
        Tree grandpa = parent.getParent();
        grandpa.getChildren().remove(parent.getName());

        removeAllChildrenFromIndex(parent);      // remove children from index
        all.remove(parent.getName());   // remove from actual tree

        return true;
    }

    public void showFileSystem() {

        showFileSystem(root,0);


    }

    public void initFileSystem() {

        // add root folder to indexed files/dirs
        all.put(ROOT_DIR, root);

    }

    private void showFileSystem(Node node, int depth) {

        Node current = node;
        String dashes="--";

        for(int i=0; i<depth;i++){
            dashes = dashes + "--";
        }
        System.out.print(dashes);
        current.print(); // print either file or folder
        Map<String, Node> children;


        if ((current instanceof Tree)) {
            // a tree (folder)
            children = ((Tree) current).getChildren();

            depth++;
            for (Map.Entry<String, Node> entry : children.entrySet()) {
                showFileSystem(entry.getValue(), depth);
            }

        }

    }

    private void removeAllChildrenFromIndex(Node parent) {

        Node current = parent;

        Map<String, Node> children;

        if ((current instanceof Tree)) {

            children = ((Tree) current).getChildren();
            for (Map.Entry<String, Node> entry : children.entrySet()) {
                removeAllChildrenFromIndex(entry.getValue());
            }
        }

        all.remove(current.getName());


    }

    private Tree findDirectoryByName(String name) {

        Tree self = null;

        if (all.containsKey(name) && (all.get(name) instanceof Tree)) {
            self = (Tree) all.get(name);
        }

        return self; // if null - doesnt exist

    }

    private Node findFileByName(String fileName) {

        Node current = null;

        if (all.containsKey(fileName)) {
            current = all.get(fileName);
        } else {
            System.out.print("File doesnt exists...");
        }

        return current;

    }

    public boolean isNameTaken(String name){

        return all.containsKey(name);
    }

    public boolean isDirectory(String name){

        if (isNameTaken(name)){
            return (all.get(name) instanceof Tree); // is a directory
        }
        return false;
    }

}
