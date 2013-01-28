#include <stdio.h>
#include <stdlib.h>
#define HEAP_SIZE 101
#define SWAP(a,b) {int t; t = a; a = b; b = t;} 

void insert_heap(int heap[], int data, int *n);
void delete_heap(int heap[], int *n);
void print_heap(int heap[], int n);

int main(void)
{
    FILE *fp;
    int heap[HEAP_SIZE] = {0}, data, i;
    int n = 1, count; /* n�q array����1�Ӷ}�l�ϥ�  count �ΨӼȦsnode�Ӽ� */
    
    fp = fopen("heap.txt","r"); /* �}�� */ 
    for(i = 0;!feof(fp);i++) { /* �CŪ�@�ӭȴN���Ʃ�Jheap */ 
        fscanf(fp,"%d",&data);
        insert_heap(heap, data, &n);  
        print_heap(heap ,n);
    }
    fclose(fp); /* ���� */ 
    count = n-1; /* node���ӼƬ�n-1 �]�� heap[0]���ϥ� */
    for(i = 0;i < count;i++) {
        delete_heap(heap, &n);
        print_heap(heap ,n);
    }
    system("pause");
    return 0;
}
void insert_heap(int heap[], int data, int *n) /* ���ƴ��Jheap�è̷Ӥj�p�ƦC */ 
{
    int i = *n;
    if(*n == HEAP_SIZE) {
        fprintf(stderr,"The heap is full.\n");
        system("pause");
        exit(1);
    }
    heap[(*n)] = data;
    if(*n != 1) {
        while(i != 1) {
            if(heap[i] > heap[i/2])  /* �p�G�s���J��heap�� > ���`�I�ȫh����bubbling up */ 
                SWAP(heap[i], heap[i/2])
            i /= 2;
        }
    }
    (*n)++;
}
void delete_heap(int heap[], int *n) /* �ΨӧR���Ĥ@�ӭȨ�bubbling up */ 
{
    int data, i = --(*n), parent = 1;
    if(*n == 0) {
        fprintf(stderr,"The heap is empty.\n");
        system("pause");
        exit(1);
    }
    heap[1] = heap[i];
    heap[i] = 0;
    while(heap[parent*2] && heap[parent*2+1]) { /* �Y��Ӥl�`�I���s�b�h�i�J�^�� */ 
        if(heap[parent*2] > heap[parent*2+1]) { /* �Y���l�`�I�j��k�l�`�I�hSWAP */ 
            SWAP(heap[parent*2], heap[parent])
            parent = parent * 2;
        }
        else {
            SWAP(heap[parent*2+1], heap[parent]) /* �Y�k�l�`�I�j�󥪤l�`�I�hSWAP */ 
            parent = parent * 2 + 1;
        }
    }
}
void print_heap(int heap[], int n) /* �L�XLevel order��heap */
{
    int i;
    for (i = 1;i < n;i++) 
        printf("%d[%d]\t",i, heap[i]);
    printf("\n");
}
