import java.util.Scanner;

// node class used in both trees
class node
{
    node left;
    node right;
    String name;
    int num;
    String address;

    // node can be accessed in other classes
    public node(String name, int num, String addr)
    {
        left = null;
        right = null;
        this.name = name;
        this.num = num;
        this.address = addr;
    }
}


// creates class that sorts based on names
class nameTree
{
    Scanner in = new Scanner(System.in).useDelimiter("\n1"); // scanner object for input
    public static node root;
    String name;
    int num;
    String addr;

    nameTree()
    {
        root = null;
    }

    // method to insert a number into the phonebook
    public void insert() {
        Scanner in = new Scanner(System.in).useDelimiter("\n"); // new Scanner instance pointing to input stream
        int flag = 0; // integer flag
        int character; // user input char
        node pointer, checker;

        do {
            System.out.println("\n Enter a name: "); // prompt user to enter a name
            name = in.next();

            System.out.println("\n Enter a phone number: "); // prompt user to enter a phone number
            num = in.nextInt();

            checker = root;

            // search if the number is already in the tree
            while (checker != null) { // while any has a value
                if (checker.num == num) { // compareTo() will return 0 if the two numbers are the same
                    flag = 1; // used to indicate numbers are the same
                    break;
                }
                else if (checker.num > num) { // compareTo() will return something less than 0 if the string is smaller than num
                    checker = checker.left; // check the left of the tree
                }
                else {
                    checker = checker.right; // check the right of the tree
                }
            }

            if (flag == 1) { // if number is already in the tree
                System.out.println("\n This num is already in the phonebook, please enter a different num."); // print this error message
                num = in.nextInt(); // user enters a different number
            }

            System.out.println("\n Enter an address: "); // prompt user to enter an address
            addr = in.next();

            node temp = new node(name, num, addr);
            if (root == null) {
                root = temp;
            } else {
                pointer = root;
                while (flag == 0) {
                    if ((temp.name).compareTo(pointer.name) > 0) {
                        if (pointer.right != null) {
                            pointer = pointer.right;
                        } else {
                            pointer.right = temp;
                            flag = 1;
                        }
                    }
                    if ((temp.name).compareTo(pointer.name) < 0) {
                        if (pointer.left != null) {
                            pointer = pointer.left;
                        } else {
                            pointer.left = temp;
                            flag = 1;
                        }
                    }
                }
            }

            flag = 0;
            System.out.println("Do you want to add another?(0/1)"); // asks user if they want to add another number
            character = in.nextInt();
        }while(character == 1);
    }

    // search method
    public void search()
    {
        int flag = 0;
        node pointer;
        System.out.println("\nEnter a name to be searched: "); // asks user to enter a name
        String key = in.next();
        pointer = root;
        while(pointer != null)
        {
            if((pointer.name).compareTo(key) == 0) // if pointer.name and key are the same
            {
                flag = 1;
                System.out.println("Person Found"); // inform user person has been found
                System.out.println("" + pointer.name + "\t" + pointer.num + "\t" + pointer.address); // prints the name number and address of that person
                break;
            }
            if((pointer.name).compareTo(key) > 0)
            {
                pointer = pointer.left;
            }
            else
            {
                pointer = pointer.right;
            }
        }
        if(flag == 0) //  if the person they search for does not exist
        {
            System.out.println("Person Not Found"); // print this error message
        }
    }


    // delete method
    public void delete()
    {
        int flag = 0;
        node pointer = null;
        node parent = null;
        System.out.println("Enter the name of a contact you would like to delete:"); // asks user to input name of contact they want to delete
        Scanner in = new Scanner(System.in);
        String key = in.next();
        pointer = root;
        while(pointer != null)
        {
            if((pointer.name).compareTo(key) == 0) // checks to see if name is in the tree
            {
                flag = 1;
                break;
            }
            if((pointer.name).compareTo(key) > 0) // if name is not in the tree and less than key
            {
                parent = pointer;
                pointer = pointer.left; // put to left of tree
            }
            else
            {
                parent = pointer;
                pointer = pointer.right; //  put to right of tree
            }
        }
        System.out.println(pointer.name);
        if(flag == 1)
        {
            if(pointer.left != null && pointer.right == null)
            {
                if(parent.left == pointer)
                {
                    parent.left = pointer.left;
                }
                else
                {
                    parent.right = pointer.left;
                }
            }
            if(pointer.left == null && pointer.right != null)
            {
                if(parent.left == pointer)
                {
                    parent.left = pointer.right;
                }
                else
                {
                    parent.right = pointer.right;
                }
            }
            if(pointer.left != null && pointer.right != null)
            {
                node p;
                p = pointer.left;
                System.out.println("pointer= " + pointer.name);
                while(p.right != null)
                {
                    parent = p;
                    p = p.right;
                }
                pointer.name = p.name;
                pointer.num = p.num;
                if(p.left != null)
                {
                    parent.right = p.left;
                }
                else
                {
                    pointer.left= null;
                }
            }
            if(pointer.left == null && pointer.right == null)
            {
                if(parent.left == pointer)
                {
                    parent.left = null;
                }
                else
                {
                    parent.right = null;
                }
            }
        }
    }

}



// creates class that sorts based on numbers
class numTree
{
    Scanner in = new Scanner(System.in);
    public static node root;
    String name;
    int num;
    String addr;
    int character;

    numTree()
    {
        root = null;
    }


    // method to insert a number into the phonebook
    public void insert() {
        Scanner in = new Scanner(System.in).useDelimiter("\n"); // separate inputs based on newline
        int flag = 0; // initialises an integer flag = 0
        int character;
        node pointer, any;

        do {
            System.out.println("\n Enter a name: "); // asks user to enter a name
            name = in.next();
            System.out.println("\n Enter a phone number: "); // asks user to enter a phone number
            num = in.nextInt();
            any = root;

            while (any != null) { // while any has a value
                if (any.num == num) { // compareTo() will return 0 if the two numbers are the same
                    flag = 1; // will be used in the if statement below
                    break;
                }
                if (any.num > num) { // compareTo() will return something less than 0 if the string is smaller than num
                    any = any.left; // puts num to left of the tree
                }
                else {
                    any = any.right; // puts num to the right of the tree
                }
            }

            if (flag == 1) { // if two numbers are the same
                System.out.println("\n This num is already in the phonebook, please enter a different num."); // print this error message
                num = in.nextInt(); // user enters a different number
            }

            System.out.println("\n Enter an address: "); // asks user to enter an address
            addr = in.next();

            node temp = new node(name, num, addr);
            if (root == null) {
                root = temp;
            }
            else {
                pointer = root;
                while (flag == 0) {
                    if (temp.num > pointer.num) {
                        if (pointer.right != null) {
                            pointer = pointer.right;
                        }
                        else {
                            pointer.right = temp;
                            flag = 1;
                        }
                    }
                    if (temp.num < pointer.num) {
                        if (pointer.left != null) {
                            pointer = pointer.left;
                        }
                        else {
                            pointer.left = temp;
                            flag = 1;
                        }
                    }
                }
            }
            flag = 0;
            System.out.println("Do you want to add another?(0/1)"); // asks user if they want to add another number
            character = in.nextInt();
        }while(character == 1);
    }


    // search method
    public void search()
    {
        int flag = 0;
        node pointer;
        System.out.println("\nEnter a number to be searched: "); // asks user to enter a name
        int key = in.nextInt();
        pointer = root;

        while(pointer != null)
        {
            if(pointer.num == key) // if pointer.num and key are the same
            {
                flag = 1;
                System.out.println("Person Found"); // inform user person has been found
                System.out.println("\t" + pointer.name + "\t" + pointer.num + "\t" + pointer.address); // prints the name number and address of that person
                break;
            }
            if(pointer.num > key)
            {
                pointer = pointer.left;
            }
            else
            {
                pointer = pointer.right;
            }
        }
        if(flag == 0) //  if the person they search for does not exist
        {
            System.out.println("Person Not Found"); // print this error message
        }
    }

    // delete method
    public void delete()
    {
        int flag = 0;
        node pointer = null;
        node parent = null;
        System.out.println("Enter the number of a contact you would like to delete:");
        Scanner in = new Scanner(System.in);
        int key = in.nextInt();
        pointer = root;

        while(pointer != null)
        {
            if(pointer.num == key) // checks to see if name is in the tree
            {
                flag = 1;
                break;
            }
            if(pointer.num > key) // if name is not in the tree and less than key
            {
                parent = pointer;
                pointer = pointer.left; // put to left of tree
            }
            else
            {
                parent = pointer;
                pointer = pointer.right; //  put to right of tree
            }
        }

        System.out.println(pointer.name);
        if(flag == 1)
        {
            if(pointer.left != null && pointer.right == null)
            {
                if(parent.left == pointer)
                {
                    parent.left = pointer.left;
                }
                else
                {
                    parent.right = pointer.left;
                }
            }
            if(pointer.left == null && pointer.right != null)
            {
                if(parent.left == pointer)
                {
                    parent.left = pointer.right;
                }
                else
                {
                    parent.right = pointer.right;
                }
            }
            if(pointer.left != null && pointer.right != null)
            {
                node p;
                p = pointer.left;
                System.out.println("pointer= " + pointer.name);
                while(p.right != null)
                {
                    parent = p;
                    p = p.right;
                }
                pointer.name = p.name;
                pointer.num = p.num;
                if(p.left != null)
                {
                    parent.right = p.left;
                }
                else
                {
                    pointer.left= null;
                }
            }
            if(pointer.left == null && pointer.right == null)
            {
                if(parent.left == pointer)
                {
                    parent.left = null;
                }
                else
                {
                    parent.right = null;
                }
            }
        }
    }

}


// sell interface when you run the file in terminal
public class phonebook
{
    public static void display(node root)
    {
        if(root != null)
        {
            display(root.left); // prints left side of the tree
            System.out.println("" + root.name + "\t" + root.num + "\t" + root.address); // prints root of the tree
            display(root.right); //  prints right side of the tree
        }
    }

    public static void main(String s[])
    {
        numTree bt = new numTree();

        Scanner in = new Scanner(System.in);
        int num;
        int ch1;

        do {
            System.out.println("\n1. Insert \n2. Search \n3. Delete \n4. Quit"); // asks user which function they would like to use
            System.out.println("Enter your choice: ");
            num = in.nextInt();
            switch (num) {
                case 1 -> {
                    bt.insert();
                    display(bt.root);
                }
                case 2 -> bt.search();
                case 3 -> bt.delete();
                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
            System.out.println("Do you want to continue?(0/1)");
            ch1 = in.nextInt();
        }
        while(ch1==1);
    }
}

