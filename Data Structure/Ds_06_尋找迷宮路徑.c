#include <stdio.h>
#include <stdlib.h>
#define MAXSTACK 101

typedef struct {
    short int row, col, dir;
} element;
typedef struct {
    short int vert, horiz;
} offsets;

int top = -1;
int **mark, **maze; /* �ϥΤG���}�C�ʺA�t�m�O���� */
 
element stack[MAXSTACK];
offsets move[8] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
                   
void push(element item);
element pop(void);
void path(int startRow, int startCol, int exitRow, int exitCol);

int main(void)
{
    FILE *fp;
    int i, j, maxRow, maxCol, startRow, startCol, exitRow, exitCol;
    
    fp = fopen("maze.txt","r");
    fscanf(fp,"%d\t%d\t%d\t%d\t%d\t%d\n",&maxRow,&maxCol,&startRow,&startCol,&exitRow,&exitCol);
    
    maze = (int**)malloc(sizeof(int) * maxRow); /* �ʺA�t�m�G���}�C */
    mark = (int**)calloc(maxRow,sizeof(int));
    for(i=0;i < maxRow;i++) {
        maze[i] = (int*)malloc(sizeof(int) * maxCol);
        mark[i] = (int*)calloc(maxCol,sizeof(int));
    }
    
    for(i=0;i < maxRow;i++) {
        for(j=0;j < maxCol;j++) {
            fscanf(fp,"%1d",&maze[i][j]);
            printf("%d",maze[i][j]);
        }
        printf("\n");
    }
    fclose(fp);
    
    path(startRow, startCol, exitRow, exitCol);
    free(maze);
    free(mark);
    system("pause");
    return 0;
}
void push(element item) /* ���J��� */
{
    if (top >= MAXSTACK - 1) {
        printf("Stack is full.\n");
    } else {
        stack[++top] = item;
    }
} 
element pop(void) /*���X��� */
{
    if (top == -1) {
        printf("Stack is empty.\n");
    } else {
        return stack[top--];
    }
}
void path(int startRow, int startCol, int exitRow, int exitCol) /* ��X�Ҧ����|�æL�X�� */
{
    int i, j,k, row, col, nextRow, nextCol, dir, found = 0, count = 1;
    element position;
    
    mark[1][1] = 1;
    top = 0;
    stack[0].row = startRow;
    stack[0].col = startCol;
    stack[0].dir = 1;
    
    while (top > -1) {
        if(found) mark[row][col] = 0;
        position = pop();
        row = position.row;
        col = position.col;
        dir = position.dir;
        while (dir < 8) {
            nextRow = row + move[dir].vert;
            nextCol = col + move[dir].horiz;
            if (nextRow == exitRow && nextCol == exitCol) {/*�P�_�O�_����I */
                found = 1;/* �аO�w��� */ 
                printf("\nThe path %d is:\n\n",count++);/* �L�X���|���G */
                printf("Row  Col  Dir\n");
                for (i = 0; i <= top; i++)
                    printf("%2d%5d%5d\n",stack[i].row, stack[i].col, stack[i].dir-1);
                printf("%2d%5d%5d\n",row,col,dir);
                printf("%2d%5d%5d\n",exitRow, exitCol, 0);
                break;
            }
            else if ( !maze[nextRow][nextCol] && !mark[nextRow][nextCol]){/*�P�_�p�G�S����B�S���L */
                mark[nextRow][nextCol] = 1;/* �N���L��m�аO 1 */
                position.row = row;/* �x�s�{�b��m */
                position.col = col;
                position.dir = ++dir;
                push(position);
                row = nextRow;/* row col���V�U�@�Ӧ�m dir �k�s*/ 
                col = nextCol;
                dir = 0;
            }
            else ++dir; /* ����V�~����� */
        }
    }
    if(!found)
        printf("The maze doesn't have a path.\n"); //�䤣����I�L�X����� 
}
