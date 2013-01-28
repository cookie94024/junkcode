#include <stdio.h>
#include <stdlib.h>

/* �w�q�s���A */
typedef enum {
    lparen, rparen, plus, minus, times, divide, mod, eos, operand
    } precedence;
typedef struct stack *stackPointer;
typedef struct stack {
        precedence opt;
        stackPointer link;
        } stack;
typedef struct expression *exprPointer;
typedef struct expression {
       char ch;
       exprPointer link;
       } expression; /* �Ψ��x�s���Ǧ��P��Ǧ� */
        
/* �ŧi Function Prototype */
exprPointer add(exprPointer previous, char ch);
void printList(exprPointer first);
void* get_node(void);
void ret_node(stackPointer node);
void cerase(stackPointer *node);
void push(precedence item);
precedence pop(void);
precedence getToken(exprPointer *expr, char *symbol);
exprPointer saveToken(exprPointer previous, precedence token);
void postfix(void);
int eval(void);

/* �����ܼƪ�l�� */
exprPointer expr_first, post_first;
stackPointer top = NULL;
stackPointer avail = NULL; /* �Ψ��x�s�i�Ϊ��`�I */
int isp[] = { 0, 19, 14, 13, 12, 11, 10, 0}; /* ���[�ū᭼�����u���v */
int icp[] = {20, 19, 14, 13, 13, 11, 10, 0};

int main(void)
{
    FILE *fp;
    int i = 0;
    char ch;
    exprPointer previous = NULL;
    
    fp = fopen("calculate.txt","r");
    
    for(i = 0;(ch = fgetc(fp))!='\n';i++) {
        if(ch != ' ')previous = add(previous,ch);
        if(i == 0)expr_first = previous;
    }
    fclose(fp);
    add(previous,' '); /* �̫��Jeos */ 
    
    printf("Infix expression: \n");
    printList(expr_first);
    postfix();
    printf("Postfix expression: \n");
    printList(post_first);
    printf("The result is %d\n",eval());
    system("pause");
    return 0;
}
exprPointer add(exprPointer previous, char ch) /* �s�и`�I�Ψ��x�s�r�� */
{
    exprPointer current;
    
    current = (exprPointer)get_node();
    if(!previous) {
        current->ch = ch;
        return current;
    }
    else {
        previous->link = current;
        current->ch = ch;
        current->link = NULL;
        return current;
    }
}
void printList(exprPointer first)
{
    for(;first;first = first->link)
        printf("%c",first->ch);
    printf("\n");
}
void* get_node(void) /* �ϥζ��m�����`�I */
{
    void* node;
    if(avail) {
        node = avail;
        avail = avail->link;
    }
    else
        node = (stackPointer)malloc(sizeof(stack));
    return node;
}
void ret_node(stackPointer node) /* �k�٤��ϥθ`�I */
{
    node->link = avail;
    avail = node;
}
void cerase(stackPointer *ptr) /* �@���k�٩Ҧ���C */
{
    stackPointer temp;
    if(*ptr) {
        temp = (*ptr)->link;
        (*ptr)->link = avail;
        avail = temp;
        *ptr = NULL;
    }
}
void push(precedence item) /* ���Ʃ�Jstack */
{
    stackPointer temp;
    temp = (stackPointer)get_node(); /* �ϥ�get_noed �Ө��o�i�θ`�I */
    temp->opt = item;
    temp->link = top;
    top = temp;
}
precedence pop(void) /* ���Ʊqstack�����X */
{
    stackPointer temp = top;
    precedence item;
    if (!temp) { /* �ˬdstack�O�_���� */
        printf("Stack is empty.\n");
        system("pause");
        exit(-1);
    }
    item = temp->opt;
    top = temp->link;
    ret_node(temp);
    return item;
}
precedence getToken(exprPointer *expr, char *symbol) /* ��r�����N��������r */
{
    *symbol = (*expr)->ch;
    switch (*symbol) {
        case '(': (*expr) = (*expr)->link; return lparen;
        case ')': (*expr) = (*expr)->link; return rparen;
        case '+': (*expr) = (*expr)->link; return plus;
        case '-': (*expr) = (*expr)->link; return minus;
        case '*': (*expr) = (*expr)->link; return times;
        case '/': (*expr) = (*expr)->link; return divide;
        case '%': (*expr) = (*expr)->link; return mod;
        case ' ': (*expr) = (*expr)->link; return eos;
        default : (*expr) = (*expr)->link; return operand;
    }
}
exprPointer saveToken(exprPointer previous, precedence token) /* �N�ӹB��Ŧs�Jpost_expr */
{
    switch (token) {
        case plus   : previous = add(previous, '+'); break;
        case minus  : previous = add(previous, '-'); break;
        case times  : previous = add(previous, '*'); break;
        case divide : previous = add(previous, '/'); break;
        case mod    : previous = add(previous, '%'); break;
        case eos    : previous = add(previous, ' '); break;
    }
    return previous;
}
void postfix(void) /* �������ǳB�z */
{
    char symbol;
    int count = 0;
    exprPointer previous = NULL;
    precedence token;
    
    push(eos);
    
    for(token = getToken(&expr_first, &symbol); token != eos; token = getToken(&expr_first, &symbol), count++) {
        if (token == operand)
            previous = add(previous, symbol);
        else if (token == rparen) {
            while (top->opt != lparen)
                previous = saveToken(previous, pop());
            pop(); /* �⥪�A��pop�X�� */
        }
        else {
            while (isp[top->opt] >= icp[token])
                previous = saveToken(previous, pop());
            push(token);
        }
        if(count == 1)post_first = previous;
    }
    while ((token = pop()) != eos) {
        previous = saveToken(previous, token);
    }
    previous = add(previous, ' '); /* �N�̫�Ÿ��]��eos */
}
int eval(void) /* �p���ǹB�⦡ */
{
    precedence token;
    char symbol;
    int op1, op2;
    cerase(&top); /* ���s�ϥ�stack �ҥH�N�쥻����C�����k�� */
    for (token = getToken(&post_first, &symbol); token != eos; token = getToken(&post_first, &symbol)) {
        if (token == operand)
            push(symbol - '0'); /* ��char�নint �H�K��ƭp�� */
        else {
            op2 = pop();
            op1 = pop();
            switch (token) {
                case plus  : push(op1 + op2); break;
                case minus : push(op1 - op2); break;
                case times : push(op1 * op2); break;
                case divide: push(op1 / op2); break;
                case mod   : push(op1 % op2); break;
            }
        }
    }
    return pop(); /* �^�ǵ��G */
}
