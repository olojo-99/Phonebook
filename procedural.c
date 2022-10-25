#include <stdio.h> 
#include <string.h> 
#include <stdlib.h>

/*Create phone book entry structure.*/
typedef struct pbentry {   
    char name[50];  
    unsigned long phone;
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
struct tree_node *delete_num (struct tree_node *p, unsigned long n);
struct tree_node *findmin(struct tree_node *p);

unsigned long name_search(struct tree_node *p, char n[]);
void num_search(struct tree_node *p, unsigned long n);

void print_tree(struct tree_node *p);

void test(struct tree_node *nametree, struct tree_node *numtree, Entry e);


// Main function
int main(void)
{
    int option = 0; /*Variable for option selection.*/    
    Entry e;  /*phone book entry*/

    struct tree_node *pname = NULL; /*name tree node*/    
    struct tree_node *pnum = NULL; /*num tree node*/

    char name[50]; /*Used for deletions, editing, and searching*/
    unsigned long num;
    // char temp; // used to clear input stream for space delimitted inputs

    /*Return to menu after each instruction until the user quits.*/    
    while (option != 6) {

    /*Show user the option menu.*/        
        printf("MENU\n");         
        printf("1. Add\n");         
        printf("2. Delete\n");                 
        printf("3. Name Search\n");
        printf("4. Number Search\n");         
        printf("5. List\n");         
        printf("6. Quit\n"); 
        printf("7. Test.\n");        

        /*Get option from the user.*/        
        printf("\nPlease select an option: ");         
        scanf("%d", &option);   

        /*If option is 1 (Add):*/    
        if (option == 1) {            

            // scanf("%c", &temp); // temp statement to clear buffer
            fflush(stdin);
            printf("Please enter the name: ");             
            scanf("%[^\n]", e.name);

            printf("Please enter the phone number: ");             
            scanf("%lu", &e.phone);             

            // scanf("%c", &temp); // temp statement to clear buffer
            fflush(stdin);
            printf("Please enter the address: ");             
            scanf("%[^\n]", e.address);

            /*Insert name*/           
            pname = name_insert(pname, e);

            /*Insert number*/        
            pnum = num_insert(pnum, e);

            /*Confirm node creation.*/            
            printf("Record added successfully.\n\n");
        }


        /*If option is 2 (Delete):*/                
        else if (option == 2) {
            // scanf("%c", &temp); // temp statement to clear buffer
            fflush(stdin);
            printf("Please enter the name: "); 
            scanf("%[^\n]", name);

            /* Find num associated with name */
            num = name_search(pname, name);        

            /*Delete a node from name tree*/            
            pname = delete_name(pname, name);

            /*Delete the associated num from num tree*/
            pnum = delete_num(pnum, num);
        }


        /*If option is 3 (Name Search):*/                
        else if (option == 3) {          
            // scanf("%c", &temp); // temp statement to clear buffer
            fflush(stdin);
            printf("Please enter the name: ");             
            scanf("%[^\n]", name); 

            /*Search for a node.*/            
            name_search(pname, name);         
        }


        /*If option is 4 (Number Search):*/                
        else if (option == 4) {          
            // scanf("%c", &temp); // temp statement to clear buffer
            fflush(stdin);
            printf("Please enter the number: ");             
            scanf("%lu", &num);

            /*Search for a node.*/            
            num_search(pnum, num);         
        }


        /*If option is 5 (List):*/              
        else if (option == 5) {          
            print_tree(pname);       
        }   


        /*If option is 6 (Quit):*/              
        else if (option == 6) {
            printf("Exiting.\n");           
            break; /*End the program.*/       
        }

        else if (option == 7) {
            test(pname, pnum, e);
        } 


        /*If the user does not select an existing option.*/            
        else {            
            /*Print error message.*/            
            printf("That option does not exist. Exiting. \n\n");
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
    else if (e.phone < p->data.phone) {      
        /*Add before root.*/       
        p->left = num_insert(p->left, e);     
    }

    /*If there is a root, and the entry belongs after the root:*/    
    else if (e.phone > p->data.phone) {    
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



 /*Deletes a node from the tree.*/
struct tree_node *delete_name (struct tree_node *p, char n[])
{
    // empty node
    if (p == NULL) {
        return NULL;
    }

    /*If entry is before root:*/   
    if (strcmp(n, p->data.name) < 0) {        
        
        /*Delete from before root.*/       
        p->left = delete_name(p->left, n);    
    }

    /*If entry is after root:*/   
    else if (strcmp(n, p->data.name) > 0) {  

        /*Delete from after root.*/       
        p->right = delete_name(p->right, n);    
    }

    /*If entry is located and has a left and right branch:*/ 
    else if (p->left != NULL && p->right != NULL) {   

        /*Find which branch moves up in the tree.*/  
        p->data = findmin(p->right)->data;        
        p->right = delete_name(p->right, n);

        /*Confirm node deletion.*/        
        printf("Record deleted successfully.\n\n");     
    }

    /*If entry is located and has a left branch:*/    
    else if (p->left != NULL) {         
        /*Move left branch up.*/       
        p = p->left;         
        /*Confirm node deletion.*/        
        printf("Record deleted successfully.\n\n");   
    }

    /*If entry is located and has a right branch:*/  
    else if (p->right != NULL) {      
        /*Move right branch up.*/       
        p = p->right;      
        /*Confirm node deletion.*/  
        printf("Record deleted successfully.\n\n");    
    }

    /*If entry is not found:*/ 
    else {       
        /*Error.*/     
        printf("Name record could not be found.\n\n");
    }

    /*Return revised tree.*/    
    return p;
}



 /*Deletes a node from the tree.*/
struct tree_node *delete_num (struct tree_node *p, unsigned long n)
{
    // empty node
    if (p == NULL) {
        return NULL;
    } 

    /*If entry is before root:*/   
    if (n < p->data.phone) {        
        
        printf("on left");
        /*Delete from before root.*/       
        p->left = delete_num(p->left, n);   
    }

    /*If entry is after root:*/   
    else if (n > p->data.phone) {  

        printf("on right");
        /*Delete from after root.*/       
        p->right = delete_num(p->right, n);    
    }

    /*If entry is located and has a left and right branch:*/ 
    else if (p->left != NULL && p->right != NULL) {
        printf("has left and right children");  
        /*Find which branch moves up in the tree.*/  
        p->data = findmin(p->right)->data;        
        p->right = delete_num(p->right, n);

        // /*Confirm node deletion.*/        
        // printf("Record deleted successfully.\n\n");     
    }

    /*If entry is located and has a left branch:*/    
    else if (p->left != NULL) {         
        /*Move left branch up.*/       
        p = p->left;         
        // /*Confirm node deletion.*/        
        // printf("Record deleted successfully.\n\n");   
    }

    /*If entry is located and has a right branch:*/  
    else if (p->right != NULL) {      
        /*Move right branch up.*/       
        p = p->right;      
        // /*Confirm node deletion.*/  
        // printf("Record deleted successfully.\n\n");    
    }

    /*If entry is not found:*/ 
    else {       
        /*Error.*/     
        printf("Number record could not be found.\n\n");    

    }

    /*Return revised tree.*/    
    return p;
}



/*Finds the leftmost node in the right branch.*/
struct tree_node *findmin(struct tree_node *p)
{    
    /*If left node is not empty.*/    
    if (p->left != NULL) {         
        /*Go to the left node.*/        
        findmin(p->left);
    }

    /*Return leftmost node.*/    
    return p;
}   



/*Searches for a node and retrieves data.*/
unsigned long name_search(struct tree_node *p, char n[]) 
{    
    /*If entry is before root:*/    
    if (p == NULL) {
        printf("Member not found.\n");
        return 0;
    }

    if (strcmp(n, p->data.name) < 0) {         
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
        printf("%s, %lu, %s\n\n", p->data.name, p->data.phone, p->data.address); /*Print out*/
        return p->data.phone;
    }

    /*If entry is not found:*/   
    else {      
        /*Error.*/       
        printf("Record could not be found.\n\n");
    }

    return 0;
}



/*Searches for a node and retrieves data.*/
void num_search(struct tree_node *p, unsigned long n) 
{    
    /*If entry is before root:*/    
    if (p == NULL) {
        printf("Member not found.\n");
        return;
    }

    if (n < p->data.phone) {         
        /*Check before root.*/
        num_search(p->left, n);
    }

    /*If entry is after root:*/    
    else if (n > p->data.phone) {
        /*Check after root.*/       
        num_search(p->right, n);
    }

    /*If entry is located:*/    
    else if (n == p->data.phone) {
        printf("%s, %lu, %s\n\n", p->data.name, p->data.phone, p->data.address); /*Print out*/
    }

    /*If entry is not found:*/   
    else {      
        /*Error.*/       
        printf("Record could not be found.\n\n");
    }
}



/*Prints contents of tree.*/
void print_tree(struct tree_node *p) {   
    /*if tree has nodes:*/    
    if (p != NULL){         
        
        /*Print node data.*/        
        print_tree(p->left);        
        printf("%s, %lu, %s\n\n", p->data.name, p->data.phone, p->data.address);        
        print_tree(p->right);
    }
}

void test(struct tree_node *nametree, struct tree_node *numtree, Entry e) {
    FILE * fp;
    char * line;
    size_t len = 0;
    ssize_t read;
//    char traverser = ",";
    int j = 0;

    char *sub;

    int pname = 0;
    int pnum = 0;
    int padd = 0;

    char * name;
    char * num;
    char * address;

    char *names[6];
    char *nums[6];
    char *addresses[6];

    //open test data file
    fp = fopen("test_data.txt", "r");
    // if file does not exist

    if (fp == NULL){
        printf("failure opening file");
        exit(EXIT_FAILURE);
    }

    while((read = getline(&line, &len, fp)) != -1){
        printf("%s", line); //used to test file contents
        
        //name line
        if(j % 3 == 0) {
            name = line;
            names[pname] = name;
            pname++;
            // printf("%s", name); test code
        }

        // number line
        else if(j % 3 == 1){
            num = line;
            nums[pnum] = num;
            pnum++;
            // printf("%s", num);
        }

        //address line
        else if(j % 3 == 2){
            address = line;
            addresses[padd] = address;
            padd++;
            // printf("%s", address);
        }

        j++;
    }
    for (int i = 0; i < 6; i++)
    {
        strcpy(e.name, names[i]);
        e.phone = atoi(nums[i]);
        strcpy(e.address, addresses[i]);
    }
    

    fclose(fp);
    if(line){
        free(line);
    }
}