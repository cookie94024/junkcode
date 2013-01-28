#include<stdio.h>
#include<stdlib.h>    //�ϥ�rand�禡 
#include<time.h>      //srand��time���ؤl 

void swap(int *a,int *b) //�洫 
{
    int temp;
    temp=*a;
    *a=*b;
    *b=temp;
}
void bubble_sort(int n,int *num) //�w�w�ƧǪk 
{
    int i,j;
    for(i=0;i<n-1;i++){
        for(j=0;j<n-i-1;j++){
            if(num[j]>num[j+1]){
                swap(&num[j],&num[j+1]);
            }
        }
    }
}
int main(void)
{
    int i,n,num[10000];
    
    srand(time(NULL));
    printf("Enter a number(0~10000): ");
    scanf("%d",&n);
    printf("Before sort:\n");
    for(i=0;i<n;i++){
        num[i]=rand()%10000;
        printf("%d\n",num[i]);
    }
    bubble_sort(n,num);
    
    printf("\nAfter sort:\n\n");
    
    for(i=0;i<n;i++){
        printf("%d\n",num[i]);
    }
    
    system("pause"); //�Ȱ� 
    return 0;
}
