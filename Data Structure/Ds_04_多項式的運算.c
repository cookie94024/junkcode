#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<math.h>
#define MAX_TERM 100
#define X (float)3.14

typedef struct{
	float coef;//�Y��
	unsigned int expon;//����
}polynomial;

polynomial p[MAX_TERM];//�Υ����ܼ�

void poly_add(int startA,int endA,int startB,int endB,int *startD,int *avail)//�h�����[�k
{
	int i=0,j=0,k=0;
	while(startA+i <= endA && startB+j <= endB){
		if(p[startA+i].expon > p[startB+j].expon){ 
			p[*startD+k] = p[startA+i];
			i++;
			k++;
		}
		else if(p[startA+i].expon < p[startB+j].expon){
			p[*startD+k] = p[startB+j];
			j++;
			k++;
		}
		else {
			if(p[startA+i].coef + p[startB+j].coef == 0){i++; j++;} //�Y�Ƭۥ[���G��0���s�J�}�C 
			else {
				p[*startD+k].coef = p[startA+i].coef + p[startB+j].coef;
				p[*startD+k].expon = p[startA+i].expon;
				i++;
				j++;
				k++;
			}
		}
	}
	while(startA+i <= endA){
		p[*startD+k] = p[startA+i];
		i++;
		k++;
	}
	while(startB+j <= endB){
		p[*startD+k] = p[startB+j];
		j++;
		k++;
	}
	*avail = *startD+k; //��l��avail 
}

void poly_multiple(int startA,int endA,int startB,int endB,int *avail,int *avail_end) //�h�������k 
{
	int i, j, n;
	
	for(i=0;startA+i <= endA;i++){ //��X���k��  
        for(j=0;startB+j <= endB;j++,(*avail)++){
	        p[*avail].coef = p[startA+i].coef * p[startB+j].coef;
		    p[*avail].expon = p[startA+i].expon + p[startB+j].expon;
        }
        if(i==1){ //�ۥ[�e�ⶵ���k��
            poly_add(*avail-j*2,*avail-j-1,*avail-j,*avail-1,avail,avail_end);
            n = *avail_end - *avail;
            *avail = *avail_end;
        }
        if(i>1){  //�ۥ[�e�@�����k���P��@���ۥ[�᭼�k�� 
            poly_add(*avail-j-n,*avail-j-1,*avail-j,*avail-1,avail,avail_end);
            n = *avail_end - *avail;
            *avail = *avail_end;
        }
	}
	*avail-=n;
	for(i=0;(*avail)+i < *avail_end;i++){ //�L�X���G 
        if(i!=0)printf(" + ");
		    printf("%g",p[*avail_end-n+i].coef);
		if(p[*avail_end-n+i].expon != 0)
			printf("X^%u\n",p[*avail_end-n+i].expon);
    }
    
}

float substitute(int start, int end) // �h�����N�J�Y��
{
    int i;
    float sum = 0;
    
    for(i=0;start+i <= end;i++){
        sum += p[start+i].coef * pow(X,p[start+i].expon);
    }
    return sum;
}
int main(void)
{
	FILE *fp;
	int i;
	int startA = 0, endA, startB, endB, startD, avail, avail_end;

	memset(p,0,MAX_TERM);//�� p[MAX_TERM]���}�C��l�Ƭ�0

	fp = fopen("poly_a.txt","r");//Ū��poly_a.txt�æL�X-------------------------------
	if(fp==NULL)printf("�}�ɥ���");
	printf("A(x) = ");
	for(i=0;!feof(fp);i++){
		if(i!=0)printf(" + ");
		    fscanf(fp,"%f\t%u\n",&p[startA+i].coef,&p[startA+i].expon);
		    printf("%g",p[startA+i].coef);
		if(p[startA+i].expon != 0)
			printf("X^%u",p[startA+i].expon);
	}
	printf("\n");
	fclose(fp);//����poly_a.txt-------------------------------------------------------

	endA = startA+i-1;
	startB = endA+1;
	printf("\nB(x) = ");
	fp = fopen("poly_b.txt","r");//Ū��poly_b.txt�æL�X-------------------------------
	if(fp==NULL)printf("�}�ɥ���");
	for(i=0;!feof(fp);i++){
		if(i!=0)printf(" + ");
		    fscanf(fp,"%f\t%u\n",&p[startB+i].coef,&p[startB+i].expon);
		    printf("%g",p[startB+i].coef);
		if(p[startB+i].expon != 0)
			printf("X^%u",p[startB+i].expon);
	}
	printf("\n");
	fclose(fp);//����poly_b.txt-------------------------------------------------------

	endB = startB+i-1;
	startD = endB+1;
	
	poly_add(startA,endA,startB,endB,&startD,&avail);
	printf("\nA(x) + B(x) = ");
	for(i=0;p[startD+i].coef!=0;i++){
		if(i!=0)printf(" + ");
		    printf("%g",p[startD+i].coef);
		if(p[startD+i].expon != 0)
			printf("X^%u",p[startD+i].expon);
	}
	printf("\n");
	printf("\n[A(x) + B(x)] * B(x) = \n");
    poly_multiple(startD,avail-1,startB,endB,&avail,&avail_end);
    printf("\nX = 3.14 : %f\n",substitute(avail,avail_end));
    system("pause");
	return 0;
}
