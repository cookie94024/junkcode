#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define MAX_QUEUE_SIZE 101

typedef struct BinaryTree *treePointer;
typedef struct BinaryTree {
        char data[10];
        treePointer leftchild, rightchild;
        } binTree;

treePointer Gen_BinTree(treePointer ptr); /* Function Prototype */
void Pre_traversal(treePointer ptr);
void In_traversal(treePointer ptr);
void Post_traversal(treePointer ptr);
void Level_traversal(treePointer ptr);
void addq(treePointer item);
treePointer deleteq(void);
char* CutNumber (void);

treePointer queue[MAX_QUEUE_SIZE]; /* �ŧi�����ܼƫ��з|����}�C��l�Ƭ�0 */
int front = 0, n = 0;
int rear = 0;
char formula[100];

int main(void)
{
    FILE *fp;
    treePointer root;

    root = (treePointer)malloc(sizeof(binTree)); /* �t�m�ڪ��O����Ŷ� */

    fp = fopen("three_tuple.txt","r"); /* Ū�� */
    fgets(formula, 100, fp);
    fclose(fp); /* ���� */

    printf("List representation:\n");
    puts(formula);
    Gen_BinTree(root);
    printf("\nPreorder Traversal:\n");
    Pre_traversal(root);
    printf("\n\nInorder Traversal:\n");
    In_traversal(root);
    printf("\n\nPostorder Traversal:\n");
    Post_traversal(root);
    printf("\n\nLevelorder Traversal:\n");
    Level_traversal(root);
    printf("\n");

    system("pause");
    return 0;
}
treePointer Gen_BinTree(treePointer ptr) /* �t�d�гy�@���� */
{
    treePointer left_subtree, right_subtree;

    while(formula[n] == '(' || formula[n] == ',' || formula[n] == ')')n++; /* �������k�A���γr�I*/
    if(formula[n] == '+' || formula[n] == '-' || formula[n] == '*' || formula[n] == '/' || formula[n] == '%') {
        /* �YŪ��B��Ÿ� �зs�l�`�I�åH���j�覡�I�s���k�l�� */
        left_subtree = (treePointer)malloc(sizeof(binTree));
        right_subtree = (treePointer)malloc(sizeof(binTree));
        ptr->data[0] = formula[n++];
        ptr->data[1] = '\0';
        ptr->leftchild = Gen_BinTree(left_subtree);
        ptr->rightchild = Gen_BinTree(right_subtree);
    } else { /* �YŪ��Ʀr ��Ʀr��i���`�I��return�ۤv��} */
        strcpy(ptr->data, CutNumber());
        ptr->leftchild = NULL;
        ptr->rightchild = NULL;
    }
    return ptr;
}
void Pre_traversal(treePointer ptr)
{
    if(ptr) {
        printf("%s ",ptr->data);
        Pre_traversal(ptr->leftchild);
        Pre_traversal(ptr->rightchild);
    }
}
void In_traversal(treePointer ptr)
{
    if(ptr) {
        In_traversal(ptr->leftchild);
        printf("%s ",ptr->data);
        In_traversal(ptr->rightchild);
    }
}
void Post_traversal(treePointer ptr)
{
    if (ptr) {
        Post_traversal(ptr->leftchild);
        Post_traversal(ptr->rightchild);
        printf("%s ",ptr->data);
    }
}
void Level_traversal(treePointer ptr)
{
    if(!ptr)return;
    addq(ptr);
    for(;;) {
        ptr = deleteq();
        if(ptr) {
            printf("%s ",ptr->data);
            if(ptr->leftchild)
                addq(ptr->leftchild);
            if(ptr->rightchild)
                addq(ptr->rightchild);
        }
        else break;
    }
}
void addq(treePointer item)
{
    if(rear == MAX_QUEUE_SIZE-1) {
        printf("Queue is full\n");
        system("pause");
        exit(1);
    }
    queue[++rear] = item;
}
treePointer deleteq(void)
{
    /*if(front == rear) {
        printf("Queue is empty\n");
        system("pause");
        exit(1);
    }*/
    return queue[++front];
}
char* CutNumber (void) /* ��Ʀr�r���^���U�Өæ^���^�����r���r�� */
{
    int i;;
    static char data[100];

    for(i = 0; formula[n] != ',' && formula[n] != ')'; i++)
        data[i] = formula[n++];
    data[i] = '\0';
    return data;
}
