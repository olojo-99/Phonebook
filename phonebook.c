#include <stdio.h> 
#include <string.h> 
#include <stdlib.h>


/*Create phone book entry structure.*/
typedef struct pbentry {   
    char name[50];  
    char phone[20];
    char address[30];
}Entry;


/*Create tree node structure.*/
struct tree_node {    
    Entry data;     
    struct tree_node *left;     
    struct tree_node *right;
};   


/*Function Prototypes*/
struct tree_node *name_insert(struct tree_node *p, Entry e); 
struct tree_node *num_insert(struct tree_node *p, Entry e); 

struct tree_node *create_node (struct tree_node *q, struct tree_node *r, Entry e); 
struct tree_node *delete_name (struct tree_node *p, char n[]);
struct tree_node *delete_num (struct tree_node *p, char n[]);
struct tree_node *findmin(struct tree_node *p);

struct tree_node *name_search(struct tree_node *p, char n[]);
struct tree_node *num_search(struct tree_node *p, char n[]);

void print_tree(struct tree_node *p);
void clean_stdin(void);

struct tree_node **test(struct tree_node *nametree, struct tree_node *numtree, Entry e);


// Main function
int main(void)
{
    int option = 0; /*Variable for option selection.*/    
    Entry e;  /*phone book entry*/

    struct tree_node *pname; // = malloc(sizeof(struct tree_node)); /*name tree node*/    
    struct tree_node *pnum; // = malloc(sizeof(struct tree_node)); /*num tree node*/
    pname = NULL;
    pnum = NULL;

    char name[50]; /* Used for deletions and searching */
    char num[20];
    // char temp; // used to clear input stream for space delimitted inputs

    printf("|===========================|\n");
    printf("|=====>>>C PHONEBOOK<<<=====|\n");
    printf("|===========================|\n");

    /*Return to menu after each instruction until the user quits.*/    
    while (option != 7) {

    /*Show user the option menu.*/        
        printf("\nMENU\n");         
        printf("|1. Add\n");         
        printf("|2. Delete\n");                 
        printf("|3. Name Search\n");
        printf("|4. Number Search\n");         
        printf("|5. List\n");
        printf("|6. Test Inputs\n");        
        printf("|7. Quit\n"); 

        /*Get option from the user.*/        
        printf("\nPlease select an option: ");         
        scanf("%d", &option);   

        /*If option is 1 (Add):*/    
        if (option == 1) {            

            
            printf("Please enter the name: ");
            clean_stdin();   
            fgets(e.name, 50, stdin);
            // removing the trailing new line character from the string (IMPORTANT)
            e.name[strcspn(e.name, "\n")] = 0;

            printf("Please enter the phone number: ");             
            fgets(e.phone, 20, stdin);
            e.phone[strcspn(e.phone, "\n")] = 0;           

            printf("Please enter the address: ");          
            fgets(e.address, 50, stdin);
            e.address[strcspn(e.address, "\n")] = 0;

            /*Insert name*/           
            pname = name_insert(pname, e);

            /*Insert number*/        
            pnum = num_insert(pnum, e);

            /*Confirm node creation.*/            
            printf("Contact for %s added successfully.\n\n", e.name);
        }


        /*If option is 2 (Delete):*/                
        else if (option == 2) {
            clean_stdin();
            printf("Please enter the name: ");
            fgets(name, 50, stdin);
            name[strcspn(name, "\n")] = 0;
            

            /* Find num associated with name */
            struct tree_node *p = malloc(sizeof(struct tree_node));
            p = name_search(pname, name);
            


            
            if (p) {
                char name_temp[50];
                char num_temp[30];

                strcpy(name_temp, p->data.name);
                strcpy(num_temp, p->data.phone);
                /*Delete a node from name tree*/
                pname = delete_name(pname, name_temp);

                /*Delete the associated num from num tree*/
                pnum = delete_num(pnum, num_temp); 
            }
            
        }


        /*If option is 3 (Name Search):*/                
        else if (option == 3) {          
            clean_stdin();
            printf("Please enter the name: ");             
            fgets(name, 50, stdin);
            name[strcspn(name, "\n")] = 0;

            /*Search for a node.*/            
            struct tree_node *p = malloc(sizeof(struct tree_node));
            p = name_search(pname, name);
            if (p){
                printf("Entry found: \n");
                printf("%s, %s, %s\n\n", p->data.name, p->data.phone, p->data.address);
            }   
        }


        /*If option is 4 (Number Search):*/                
        else if (option == 4) {          
            clean_stdin();
            printf("Please enter the number: ");             
            fgets(num, 20, stdin);
            num[strcspn(num, "\n")] = 0;

            /*Search for a node.*/
            struct tree_node *p = malloc(sizeof(struct tree_node));         
            p = num_search(pnum, num);
            if (p){
                printf("Entry found: \n");
                printf("%s, %s, %s\n\n", p->data.name, p->data.phone, p->data.address);
            }       
        }


        /*If option is 5 (List):*/              
        else if (option == 5) {
            printf("Name tree: \n");        
            print_tree(pname);
            printf("\n\n");
            printf("Phone tree: \n");
            print_tree(pnum);
            printf("\n\n");
        }   


        /* Test inputs */
        else if (option == 6) {
            struct tree_node **tree_pointers = test(pname, pnum, e);
            pname = tree_pointers[0];
            pnum = tree_pointers[1];
        } 


        /*If option is 6 (Quit):*/              
        else if (option == 7) {
            printf("Exiting.\n");           
            break; /*End the program.*/       
        }


        /*If the user does not select an existing option.*/            
        else {            
            /*Print error message.*/            
            printf("Invalid option entered.\n\n");
            break; // user may have entered character - can't assign to int      
        } 

    // End of option while loop  
    }    
        
    // End of function
    return 0; 
}



/*Adds a node to the tree.*/
struct tree_node *num_insert(struct tree_node *p, Entry e) 
{     
    /*If there is no root:*/ 
    if (p == NULL) {       
        /*Create a root.*/       
        p = create_node(NULL, NULL, e);    
    }

    /*If there is a root, and the entry belongs before the root:*/    
    else if (strcmp(e.phone, p->data.phone) < 0) {      
        /*Add before root.*/       
        p->left = num_insert(p->left, e);     
    }

    /*If there is a root, and the entry belongs after the root:*/    
    else if (strcmp(e.phone, p->data.phone) > 0) {    
        /*Add after root.*/    
        p->right = num_insert(p->right, e);   
    }

    /*Return revised tree.*/ 
    return p; 
}



/*Adds a node to the tree.*/
struct tree_node *name_insert(struct tree_node *p, Entry e) 
{
    /*If there is no root:*/ 
    if (p == NULL) {       
        /*Create a root.*/    
        p = create_node(NULL, NULL, e);    
    }

    /*If there is a root, and the entry belongs before the root:*/    
    else if (strcmp(e.name, p->data.name) < 0) {      
        /*Add before root.*/       
        p->left = name_insert(p->left, e);     
    }

    /*If there is a root, and the entry belongs after the root:*/    
    else if (strcmp(e.name, p->data.name) > 0) {    
        /*Add after root.*/    
        p->right = name_insert(p->right, e);   
    }

    /*Return revised tree.*/ 
    return p; 
}



/*Creates a new node.*/
struct tree_node *create_node (struct tree_node *q, struct tree_node *r, Entry e)
{     
    struct tree_node* newnode;     
    newnode = (struct tree_node*)(malloc(sizeof(struct tree_node)));     
    newnode->data = e;   
    newnode->left = q;     
    newnode->right = r;     
    return newnode;
}


struct tree_node *delete_name(struct tree_node *p, char *name) {
    // based on code found at https://www.geeksforgeeks.org/deletion-in-binary-search-tree/
    if (p == NULL) {
        return p;
    }
    if (strcmp(name, p->data.name) < 0) {        
        
        /*Delete from before root.*/       
        p->left = delete_name(p->left, name);  
    }

    else if (strcmp(name, p->data.name) > 0) {  

        /*Delete from after root.*/       
        p->right = delete_name(p->right, name);    
    }

    // node found
    else if (strcmp(name, p->data.name) == 0) {
        // node with only 1 child or no children
        if (p->left == NULL) {
            struct tree_node *tmp = p->right;
            free(p);
            return tmp;
        }

        else if (p->right == NULL) {
            struct tree_node *tmp = p->left;
            free(p);
            return tmp;
        }

        // node with 2 children 
        struct tree_node *tmp = findmin(p->right);

        // copy the inorder successor's content to this node
        p->data = tmp->data;
        p->right = delete_name(p->right, tmp->data.name);
    }
    return p;
}

struct tree_node *delete_num(struct tree_node *p, char *num) {
    // based on code found at https://www.geeksforgeeks.org/deletion-in-binary-search-tree/

    if (p == NULL) {
        return p;
    }

    if (strcmp(num, p->data.phone) < 0) {        
        /*Delete from before root.*/    
        p->left = delete_num(p->left, num);  
    }

    else if (strcmp(num, p->data.phone) > 0) {  
        /*Delete from after root.*/        
        p->right = delete_num(p->right, num);    
    }

    // node found
    else if (strcmp(num, p->data.phone) == 0) {
        // node with only 1 child or no children
        if (p->left == NULL) {
            struct tree_node *tmp = p->right;
            free(p);
            return tmp;
        }

        else if (p->right == NULL) {
            struct tree_node *tmp = p->left;
            free(p);
            return tmp;
        }

        // node with 2 children 
        struct tree_node *tmp = findmin(p->right);

        // copy the inorder successor's content to this node
        p->data = tmp->data;
        p->right = delete_num(p->right, tmp->data.phone);
    }
    return p;
}


struct tree_node *findmin(struct tree_node *p)
{
    struct tree_node *curr = p;

    /*find the leftmost node in tree*/
    while (curr && curr->left != NULL){
        curr = curr->left;
    }
    return curr;
}



/*Searches for a node and retrieves data.*/
struct tree_node *name_search(struct tree_node *p, char n[]) 
{    
    /*If entry is before root:*/    
    if (p == NULL) {
        printf("Member not found.\n");
        return p;
    }

    else if (strcmp(n, p->data.name) < 0) {         
        /*Check before root.*/
        name_search(p->left, n);
    }

    /*If entry is after root:*/    
    else if (strcmp(n, p->data.name) > 0) {
        /*Check after root.*/       
        name_search(p->right, n);
    }

    /*If entry is located:*/    
    else if (strcmp(n, p->data.name) == 0) {
        struct tree_node *new_p = malloc(sizeof(struct tree_node));
        new_p = p;
        return new_p;
    }

    /*If entry is not found:*/   
    else {      
        /*Error.*/       
        printf("Record could not be found.\n\n");
        return NULL;
    }

    return NULL;
}



/*Searches for a node and retrieves data.*/
struct tree_node *num_search(struct tree_node *p, char n[]) 
{   
    if (p == NULL){
        printf("Member not found.\n");
        return NULL;
    }

    else if (strcmp(n, p->data.phone) < 0) {         
        /*Check before root.*/
        num_search(p->left, n);
    }

    /*If entry is after root:*/    
    else if (strcmp(n, p->data.phone) > 0) {
        /*Check after root.*/     
        num_search(p->right, n);
    }

    /*If entry is located:*/    
    else if (strcmp(n, p->data.phone) == 0) {
       struct tree_node *new_p = malloc(sizeof(struct tree_node));
       new_p = p;
       return new_p;
    }

    /*If entry is not found:*/   
    else {
        /*Error.*/
        printf("Record could not be found.\n\n");
        return NULL;     
    }

    return NULL;   
}



/*Prints contents of tree.*/
void print_tree(struct tree_node *p) {   
    if (p != NULL){         
        /*Print node data.*/        
        print_tree(p->left);        
        printf("%s, %s, %s\n\n", p->data.name, p->data.phone, p->data.address);        
        print_tree(p->right);
    }
}



struct tree_node **test(struct tree_node *nametree, struct tree_node *numtree, Entry e) {
    FILE * fp;
    struct tree_node **pointer_array = malloc(2 * sizeof(struct tree_node));
    char line[256];
    // ssize_t read;
    int j = 0;

    // char *sub;

    int pname = 0;
    int pnum = 0;
    int padd = 0;

    char * name;
    char * num;
    char * address;

    char **names = malloc(7 * sizeof(char*));
    char **nums = malloc(7 * sizeof(char*));
    char **addresses = malloc(7 * sizeof(char*));

    //open test data file
    fp = fopen("test_data.txt", "r");
    // if file does not exist

    if (fp == NULL){
        printf("Failure opening file");
        exit(EXIT_FAILURE);
    }
    printf("Caution: do not modify the test data file length.\n");

    while(fgets(line, sizeof(line), fp)) {
        // removing trailing newline
        line[strcspn(line, "\n")] = 0;

        if(j % 3 == 0) {
            name = line;
            names[pname] = strdup(name);         
            pname++;
        }

        // number line
        else if(j % 3 == 1){
            num = line;
            nums[pnum] = strdup(num);
            pnum++;
        }

        //address line
        else if(j % 3 == 2){
            address = line;
            addresses[padd] = strdup(address);
            padd++;
        }

        j++;
    }

    
    printf("Generating test trees...\n\n");
    for (int i = 0; i < 6; i++)
    {
        names[i][strcspn(names[i], "\n")] = 0;
        strcpy(e.name, names[i]);
        nums[i][strcspn(nums[i], "\n")] = 0;
        strcpy(e.phone, nums[i]);
        addresses[i][strcspn(addresses[i], "\n")] = 0;
        strcpy(e.address, addresses[i]);
        
        nametree = name_insert(nametree, e);
        numtree = num_insert(numtree, e);
    }


    print_tree(numtree);
    pointer_array[0] = nametree;
    pointer_array[1] = numtree;

    fclose(fp);

    

    return pointer_array;
}

void clean_stdin(void)
{
    int c;
    do {
        c = getchar();
    } while (c != '\n' && c != EOF);
}
