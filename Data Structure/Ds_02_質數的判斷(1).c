#include<stdio.h>
#include<stdlib.h>
#include<math.h>    //�I�s sqrt function

int main(void)
{
    int no,num,i,limit,pn=2;  //no�O�Q������  num����J����  pn���ثe�Ҵ��X��ƪ��Ӽ�
    int *prime;   //�Ψ��x�s���

    printf("Please insert the prime limit: ");
    scanf("%d",&num);
    prime = malloc(sizeof(int)*num);  //��ƭӼƥ��� �ΰʺA�t�m���x�s
    prime[0] = 2;
    prime[1] = 3;

    if(num >= 2){         // �p�G�j�� 2��3 �L�X2��3
        printf("2\n");
        if(num >= 3)printf("3\n");
    }

    for(no=5;no <= num ;no+=2){    //���լO�_�����
        limit = (int)sqrt(no);
        for(i=1;i<pn;i++){
            if(prime[i]<=limit && no%prime[i]==0)break;
        }
        if(i==pn){
            printf("%d\n",no);
            prime[pn] = no;
            pn++;
        }
    }
    return 0;
}
