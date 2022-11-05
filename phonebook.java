import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

// Reference - https://github.com/PRITI24/Phonebook-management-system-using-binary-search-tree/blob/master/Phone.java

//Node Class
class Node {
    String name;
    String addr;
    String number;
    Node left;
    Node right;

    // Node constructor
    public Node(String name, String addr, String number) {
        this.name = name;
        this.addr = addr;
        this.number = number;
        left = null;
        right = null;
    }
}


// binary tree class
class BinaryTree {

    public Node root; // creates a node that can be accessed by all the commands

    BinaryTree() {
        root = null; // set root node to null initially
    }

    // add records sorted by name
    public void nameInsert(String name, String addr, String number)
    {
        int i = 0; // used for traversing tree
        Node temp = new Node(name, addr, number); // new node with parameter values

        // set new node to null if empty tree
        if (root == null) {
            root = temp;
        }

        else {
            Node tnode = root;

            // loop until null node is found
            while (i == 0)
            {
                if ((temp.name).compareTo(tnode.name) > 0){
                    if (tnode.right != null) { // if right child has a node
                        tnode = tnode.right;
                    } else {
                        tnode.right = temp; // set the null node to the input node
                        i = 1; // exit loop
                    }
                }

                // same process but for left nodes
                if (((temp.name).compareTo(tnode.name) < 0)) {
                    if (tnode.left != null) {
                        tnode = tnode.left;
                    }
                    else {
                        tnode.left = temp;
                        i = 1;
                    }
                }
            }
        }
    }


    // Searching by number rather than name
    public void numInsert(String name, String addr, String number)
    {
        int i = 0;
        Node temp = new Node(name, addr, number);

        if (root == null) {
            root = temp;
        }

        else {
            Node tnode = root;
            while (i == 0)
            {
                if ((temp.number).compareTo(tnode.number) > 0) {

                    if (tnode.right != null){
                        tnode = tnode.right;
                    }

                    else {
                        tnode.right = temp;
                        i = 1;
                    }
                }

                if (((temp.number).compareTo(tnode.number) < 0)) {
                    if (tnode.left != null) {
                        tnode = tnode.left;
                    }
                    else {
                        tnode.left = temp;
                        i = 1;
                    }
                }
            }
        }
    }


    // Searches the name tree for contact name
    public String nameSearch(String name, boolean show){
        int i = 0;

        // loop until end of tree
        while (root != null){
            // checking name parameter against node name
            if (root.name.compareTo(name) == 0)
            {
                i = 1;

                if (show) {
                    // print details for match
                    System.out.println("\n======RECORD FOUND======");
                    System.out.println("Name: " + root.name);
                    System.out.println("Address: " + root.addr);
                    System.out.println("Number: " + root.number);
                    System.out.println("==========================");
                }
                return root.number;
//                break;
            }

            else if ((root.name).compareTo(name) > 0) {
                // traverse left if name is less than current
                root = root.left;
            }
            else {
                // traverse toward the right
                root = root.right;
            }
        }

        if (i == 0 && show) {
            // means the record is not present
            System.out.println("RECORD NOT FOUND");
        }

        return null;
    }


    // Searches tree for contact number
    public void numSearch(String num){
        int i = 0;

        while (root != null){

            if (root.number.compareTo(num) == 0)
            {
                i = 1;

                System.out.println("\n======RECORD FOUND======");
                System.out.println("Name: " + root.name);
                System.out.println("Address: " + root.addr);
                System.out.println("Number: " + root.number);
                System.out.println("==========================");
                break;
            }

            else if ((root.number).compareTo(num) > 0) {
                root = root.left;
            }

            else {
                root = root.right;
            }
        }

        if (i == 0){
            System.out.println("RECORD NOT FOUND");
        }
    }


    // delete contact name from tree
    public void deleteName(String name){
        int i = 0;
        Node current, previous = null;
        current = root; // curr node becomes root node

        // loop until end of tree
        while (current != null)
        {
            // compare parameters against curr node
            if ((current.name).compareTo(name) > 0) {
                previous = current; // traverse toward left
                current = current.left;
            }

            else if ((current.name).compareTo(name) < 0) {
                previous = current; // traverse toward right
                current = current.right;
            }

            else {
                i = 1; // record has been found
//                String currNum = current.number; // get the associated number
//                deleteNum(currNum); // delete the associated number
                break; // exit loop and finish removal of name
            }
        }

        // found node
        if (i == 1){
            // left children check for node
            if (current.left != null && current.right == null)
            {
                if (previous == null){
                    root = current.left;
                }
                else if (previous.left == current) { // curr node is the left node of the previous node
                    previous.left = current.left; // assign the previous node left child to the current node left child
                    current = null; // remove the current node
                }
                else {
                    previous.right = current.left; // assign the previous node right child to the current node left child
                    current = null;
                }
            }

            // node has children on the right
            else if (current.left == null && current.right != null) {
                if (previous == null){ // root node has no previous node so assign the only child to be the new root
                    root = current.right;
                }
                else if (previous.left == current) {
                    previous.left = current.right;
                    current = null;
                }
                else {
                    previous.right = current.right;
                    current = null;
                }
            }
            // node has children on the left and right
            else if (current.left != null && current.right != null) {
                Node p;
                p = current.left;

                while (p.right != null) { // traverse right subtree
                    previous = p;
                    p = p.right;
                }
                current.name = p.name;
                current.addr = p.addr;
                current.number = p.number;

                if (p.left != null) {
                    previous.right = p.left;
                }
                else {
                    current.left = null;
                }
            }
            // node has no children
            else if (current.left == null && current.right == null) {
                if (previous == null){
                    // no children from the root node so set it to null
                    root = null;
                }
                // curr node is to the left or right of the previous node
                else if (previous.left == current) {
                    previous.left = null; // remove the node
                }

                else {
                    previous.right = null;
                }
            }
        }
        if (i == 0) {
            System.out.println("NAME NOT FOUND");
        }
    }

    // delete contact number from number tree
    public void deleteNum(String num){
        int i = 0;
        Node current, previous = null;
        current = root;

        while (current != null) {
            if ((current.number).compareTo(num) > 0) {
                previous = current;
                current = current.left;
            }
            else if ((current.number).compareTo(num) < 0) {
                previous = current;
                current = current.right;
            }
            if ((current.number).compareTo(num) == 0) {
                i = 1;
                break;
            }
        }
        if (i == 1){
            if (current.left != null && current.right == null) {
                if (previous == null){
                    root = current.left;
                }
                else if (previous.left == current) {
                    previous.left = current.left;
                    current = null;
                }
                else {
                    previous.right = current.left;
                    current = null;
                }
            }
            else if (current.left == null && current.right != null) {
                if (previous == null){
                    root = current.right;
                }
                else if (previous.left == current) {
                    previous.left = current.right;
                    current = null;
                }
                else {
                    previous.right = current.right;
                    current = null;
                }
            }
            else if (current.left != null && current.right != null) {
                Node p;
                p = current.left;
                while (p.right != null) {
                    previous = p;
                    p = p.right;
                }
                current.name = p.name;
                current.addr = p.addr;
                current.number = p.number;
                if (p.left != null) {
                    previous.right = p.left;
                }
                else {
                    current.left = null;
                }
            }
            else if (current.left == null && current.right == null) {
                if (previous == null){
                    root = null;
                }
                else if (previous.left == current) {
                    previous.left = null;
                }
                else {
                    previous.right = null;
                }
            }
        }

        if (i == 0) {
            System.out.println("NUMBER NOT FOUND");
        }
    }


    // prints the binary tree in order
    public void inorder(Node root){
        if(root != null)
        {
            inorder(root.left);
            System.out.println("\n========Contact========");
            System.out.println("Name: " + root.name);
            System.out.println("Address: " + root.addr);
            System.out.println("Number: " + root.number);
            System.out.println("=======================");
            inorder(root.right);
        }
    }

    // call inorder func during runtime for user without needing node parameter
    public void display(){
        inorder(root);
    }
}


public class phonebook {
    public static void main(String[] args) {

        BinaryTree nameTree = new BinaryTree(); // name tree to sort by name
        BinaryTree numTree = new BinaryTree(); // num tree to sort by num
        Scanner in = new Scanner(System.in); // scanner object for reading input
        String i;
        int j;

        System.out.println("|==============================|");
        System.out.println("|=====>>>JAVA PHONEBOOK<<<=====|");
        System.out.println("|==============================|");

        do {
            System.out.println("MENU");
            System.out.println("|1. Add\n|2. Delete\n|3. Name/Number Search\n|4. List Contacts\n|5. Test Inputs\n|6. Quit");

            i = in.next();
            switch (i) {

                // add contacts
                case "1":
                    Scanner insertInput = new Scanner(System.in);
                    System.out.println("\nEnter the Name: ");
                    String insertName = insertInput.nextLine();
                    System.out.println("\nEnter the Address: ");
                    String insertAddr = insertInput.nextLine();
                    System.out.println("\nEnter the Phone Number: ");
                    String insertNumber = insertInput.nextLine();
                    nameTree.nameInsert(insertName, insertAddr, insertNumber);
                    numTree.numInsert(insertName, insertAddr, insertNumber);
                    break;

                // delete contacts
                case "2":
                    Scanner deleteInput = new Scanner(System.in);
                    System.out.println("Enter the Name to be deleted: ");
                    String deleteName = deleteInput.nextLine();
//                    System.out.println("Enter the associated Number: ");
//                    String deleteNumber = deleteInput.nextLine();
//                    numTree.deleteNum(deleteNumber);
                    String deleteNum = numTree.nameSearch(deleteName, true); // no search result display

                    if (deleteNum != null) {
                        numTree.deleteNum(deleteNum); // delete from name tree
                        nameTree.deleteName(deleteName); // delete from num tree
                    }
                    break;

                // search by name or number
                case "3":
                    Scanner ch = new Scanner(System.in);
                    System.out.println("1 = Name Search / 2 = Number Search");
                    String select = ch.next(); // prompt user for option selection

                    switch(select) {
                        case "1": // name search
                            Scanner nameSearchInput = new Scanner(System.in);
                            System.out.println("\nEnter the Name: ");
                            String sName = nameSearchInput.nextLine();
                            nameTree.nameSearch(sName, true); // display details too
                            break;

                        case "2": // number search
                            Scanner numSearchInput = new Scanner(System.in);
                            System.out.println("\nEnter the Number: ");
                            String sNum = numSearchInput.nextLine();
                            numTree.numSearch(sNum);
                            break;

                        default:
                            System.out.println("Invalid Search Option");
                            break;
                    }
                    break;

                // print current contact list
                case "4":
                    nameTree.display();
                    break;

                // create test inputs
                case "5":
                    try {
                        File inputs = new File("test_data.txt");
                        Scanner fileReader = new Scanner(inputs);
                        while (fileReader.hasNextLine()){
                            // collect inputs in order of test file
                            String fileName = fileReader.nextLine();
                            String fileNumber = fileReader.nextLine();
                            String fileAddress = fileReader.nextLine();

                            // add test values to tree
                            nameTree.nameInsert(fileName, fileAddress, fileNumber);
                            numTree.numInsert(fileName, fileAddress, fileNumber);

                        }
                        fileReader.close();
                    }
                    catch (FileNotFoundException e){
                        System.out.println("The file could not be found.");
                    }

                    // display the tree
                    nameTree.display();

                    break;


                // exit loop and end program
                case "6":
                    System.out.println("Quitting");
                    System.exit(0);

                default:
                    System.out.println("Invalid Menu Option");
                    j = 0;
                    break;
            }
            System.out.println("");
            j = 1; // continue to accept more commands
        } while (j == 1);
    }
}
