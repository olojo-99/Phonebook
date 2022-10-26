import java.util.Scanner;

// Node class
class Node {
    String name;
    String addr;
    String number;
    Node left;
    Node right;

    public Node(String name, String addr, String number) { // Node constructor
        this.name = name;
        this.addr = addr;
        this.number = number;
        left = null;
        right = null;
    }
}


// binary tree class
class BinaryTree {
    public Node root; // creates a node that contains contact info and children

    BinaryTree() {
        root = null;
    }


    // add records sorted by name
    public void nameInsert(String name, String addr, String number){
        int i = 0; // used for traversing tree
        Node temp = new Node(name, addr, number);

        if (root == null) {
            root = temp;
        }

        else {
            // loop until null node is found
            while (i == 0) {
                if ((temp.name).compareTo(root.name) > 0) {
                    if (root.right != null) { // if right child has a node
                        root = root.right;
                    }

                    else {
                        root.right = temp; // set the null node to the input node
                        i = 1; // exit loop
                    }
                }

                if (((temp.name).compareTo(root.name) < 0)) {
                    if (root.left != null) {
                        root = root.left;
                    }

                    else {
                        root.left = temp;
                        i = 1;
                    }
                }
            }
        }
    }


    // Searching by number rather than name
    public void numInsert(String name, String addr, String number){
        int i = 0;
        Node temp = new Node(name, addr, number);

        if (root == null) {
            root = temp;
        }

        else {
            while (i == 0) {
                if ((temp.number).compareTo(root.number) > 0) {
                    if (root.right != null) {
                        root = root.right;
                    }

                    else {
                        root.right = temp;
                        i = 1;
                    }
                }

                if (((temp.number).compareTo(root.number) < 0)) {
                    if (root.left != null) {
                        root = root.left;
                    }

                    else {
                        root.left = temp;
                        i = 1;
                    }
                }
            }
        }
    }


    // Searches the name tree for contact name
    public void searchName(String name) {
        int i = 0;

        // loop until end of tree
        while (root != null){
            if (root.name.compareTo(name) == 0) {
                i = 1;
                System.out.println("\nRECORD FOUND");

                System.out.println("NAME | ADDRESS | NUMBER");
                System.out.println(root.name + " | " + root.addr + " | " + root.number);

                break;
            }

            if ((root.name).compareTo(name) > 0) { // if the name does not match but has a value less than the current name it goes left
                root = root.left;
            }

            else { // if it has a greater value it goes right
                root = root.right;
            }
        }

        if (i == 0){ // if the while loop breaks and no record has been found returns "Record not found" to the user
            System.out.println("RECORD NOT FOUND");
        }
    }


    // Searches tree for contact number
    public void searchNum(String num){
        int i = 0;
        while (root != null){
            if (root.number.compareTo(num) == 0) { // works the same as searchName but compares the numbers rather than the names
                i = 1;
                System.out.println("\nRECORD FOUND");

                System.out.println("NAME | ADDRESS | NUMBER");
                System.out.println(root.name + " | " + root.addr + " | " + root.number);

                break;
            }
            if ((root.number).compareTo(num) > 0) {
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
        current = root;

        while (current != null) { // iterate until end of tree
            if ((current.name).compareTo(name) > 0) {
                previous = current;
                current = current.left;
            }

            else if ((current.name).compareTo(name) < 0) {
                previous = current;
                current = current.right;
            }

            if ((current.name).compareTo(name) == 0) {
                i = 1; // exit loop if found
                break;
            }
        }

        // found node
        if (i == 1){
            if (current.left != null && current.right == null) { // left children check for node

                // left of parent node
                if (previous.left == current) {
                    previous.left = current.left; // set the parent node left child to current node left child
                    current = null; // delete the current node
                }

                else {
                    previous.right = current.left; // set the parent node right child to current node left child
                    current = null;
                }
            }
            if (current.left == null && current.right != null) { // checks if the node has right children
                if (previous.left == current) {
                    previous.left = current.right;
                    current = null;
                }

                else {
                    previous.right = current.right;
                    current = null;
                }
            }
            if (current.left != null && current.right != null) { // checks if the node has left and right children
                Node p;
                p = current.left;

                while (p.right != null) {
                    previous = p;
                    p = p.right;
                }

                current.name = p.name;
                current.number = p.number;

                if (p.left != null) {
                    previous.right = p.left;
                }

                else {
                    current.left = null;
                }
            }
            if (current.left == null && current.right == null) { // no children
                if (previous.left == current) { // checks to see if the current node is left or right child
                    previous.left = null; // delete node
                }

                else {
                    previous.right = null;
                }
            }
        }

        if (i == 0) {
            System.out.println("RECORD NOT FOUND");
        }
    }


    // delete contact number from number tree
    public void deleteNum(String search){
        int i = 0;

        Node current, previous = null;
        current = root;

        while (current != null) {
            if ((current.number).compareTo(search) > 0) {
                previous = current;
                current = current.left;
            }

            else if ((current.number).compareTo(search) < 0) {
                previous = current;
                current = current.right;
            }

            if ((current.number).compareTo(search) == 0) {
                i = 1;
                break;
            }
        }

        if (i == 1){
            if (current.left != null && current.right == null) {
                if (previous.left == current) {
                    previous.left = current.left;
                    current = null;
                }

                else {
                    previous.right = current.left;
                    current = null;
                }
            }
            if (current.left == null && current.right != null) {
                if (previous.left == current) {
                    previous.left = current.right;
                    current = null;
                }

                else {
                    previous.right = current.right;
                    current = null;
                }
            }
            if (current.left != null && current.right != null) {
                Node p;
                p = current.left;

                while (p.right != null) {
                    previous = p;
                    p = p.right;
                }

                current.name = p.name;
                current.number = p.number;
                if (p.left != null) {
                    previous.right = p.left;
                }

                else {
                    current.left = null;
                }
            }

            if (current.left == null && current.right == null) {
                if (previous.right == current) {
                    previous.right = null;
                }

                else {
                    previous.left = null;
                }
            }
        }

        if (i == 0) {
            System.out.println("RECORD NOT FOUND");
        }
    }

    public void inorder(Node root){ // prints out the binary tree in order
        if(root != null){
            inorder(root.left);
            System.out.println(root.name + " " + root.addr + " " + root.number);
            inorder(root.right);
        }
    }

    public void display(){ // call inorder func during runtime for user without node arg
        inorder(root);
    }
}


public class phonebook {
    public static void main(String[] args) {

        BinaryTree nameTree = new BinaryTree(); // creates the binary trees needed to store the information
        BinaryTree numTree = new BinaryTree();
        Scanner in = new Scanner(System.in); // creates a scanner to detect the users input

        int i, j;
        do {
            System.out.println("\n 1. Add \n 2. Search Name \n 3. Search Number \n 4. Display Contacts \n 5. Delete Contact \n 6. Quit");
            System.out.println("Choose an option");
            Scanner userInput = new Scanner(System.in);

            i = in.nextInt();
            switch (i) {

                case 1: // gets the user to enter a name to be searched
                    System.out.println("\nEnter the Name: ");

                    String insertName = userInput.nextLine();
                    System.out.println("\nEnter the Address: ");

                    String insertAddr = userInput.nextLine();
                    System.out.println("\nEnter the Phone Number: ");

                    String insertNum = userInput.nextLine();
                    nameTree.nameInsert(insertName, insertAddr, insertNum);
                    numTree.numInsert(insertName, insertAddr, insertNum);
                    break;

                case 2: // gets the user to enter a name, address, and number to create a new record in the system
                    System.out.println("\nEnter the Name: ");
                    String searchName = userInput.nextLine();
                    nameTree.searchName(searchName);
                    break;

                case 3: // gets the user to enter a number to be searched
                    System.out.println("\nEnter the Number: ");
                    String searchNum = userInput.nextLine();
                    numTree.searchNum(searchNum);
                    break;

                case 4: // prints out all records in the system
                    System.out.println("NAMES |  ADDRESSES | NUMBERS");
                    nameTree.display();
                    break;

                case 5: // gets the user to enter the name and number of the record that is to be deleted
                    System.out.println("\nEnter the name to be removed");
                    String deleteName = userInput.nextLine();

                    System.out.println("\nEnter the associated number");
                    String deleteNumber = userInput.nextLine();

                    numTree.deleteNum(deleteNumber);
                    nameTree.deleteName(deleteName);
                    break;

                case 6: // quits the program
                    System.out.println("Quitting");
                    System.exit(0);

                default:
                    break;
            }

            System.out.println("");
            System.out.println("Continue? (1 = Yes / 0 = No)");
            j = in.nextInt();

        } while (j==1);
    }
}
