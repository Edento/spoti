package filesystem;

import java.util.HashMap;

/**
 * Created by eden on 28/08/2016.
 */
public class Tree extends Node{

    private HashMap<String, Node> children = new HashMap<>();


    public Tree(String name, Tree parent) {

        super(name, 0, parent);
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }

    public void print(){
        System.out.println("Directory { Name: " + name + ", Created: " + created + " }");
    }
}
