#include<stdio.h>
#include<math.h>

int main(void)
{
	int m,n,i,j,x[255],y[255],min;
	FILE *fp;

	fp=fopen("x.txt","r"); //�}��x.txt
	for(m=0;!feof(fp);m++){ //�ΰj��Ū����ƨé���\n
		fscanf(fp,"%d\n",&x[m]);
	}
	fclose(fp); //�����ɮ�

	fp=fopen("y.txt","r"); //�}��x.txt
	for(n=0;!feof(fp);n++){ //�ΰj��Ū����ƨé���\n
		fscanf(fp,"%d\n",&y[n]);
	}
	fclose(fp); //�����ɮ�
	
	min=abs(x[0]-y[0]); //��l�� min
	for(i=0;i<m;i++){ //�ϥμɤO�k�D��
		for(j=0;j<n;j++){
			if(abs(x[i]-y[j])<min)min=abs(x[i]-y[j]);
		}
	}
	printf("%d",min);
	system("pause");
	return 0;
}
