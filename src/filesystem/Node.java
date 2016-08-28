package filesystem;

import java.util.Date;

/**
 * Created by eden on 28/08/2016.
 */

public class Node {

    protected String name;
    protected Date created;
    private int size;
    protected Tree parent;

    public Node(String name, int size, Tree parent) {
        this.name = name;
        this.size = size;
        this.parent = parent;
        this.created = new Date();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Tree getParent() {
        return parent;
    }

    public void print(){
        System.out.println("File { Name: "+name+", size: "+size+", Created: " + created + " }");
    }
}
