#include<stdio.h>
#include<stdlib.h>

int find_min(int x,int y) //��̤p�Ȫ�function
{
	if(x<y)return x;
	else return y;
}
int main(void)
{
	int m,n,i,j,x[255],y[255],min,min_x; //min_x���Ȧsmin��
	FILE *fp;

	fp=fopen("x.txt","r"); //�}��x.txt
	for(m=0;!feof(fp);m++){ //�ΰj��Ū����ƨé���\n
		fscanf(fp,"%d\n",&x[m]);
	}
	fclose(fp); //�����ɮ�

	fp=fopen("y.txt","r"); //�}��y.txt
	for(n=0;!feof(fp);n++){ //�ΰj��Ū����ƨé���\n
		fscanf(fp,"%d\n",&y[n]);
	}
	fclose(fp); //�����ɮ�
	
	min=abs(x[0]-y[0]); //��l�� min
	for(i=0;i<n;i++){ //�ϥΫD�ɤO�k�D��
		for(j=0;j<m;j++){
			if(x[j]>y[i])break;
		}
		if(j==m){min_x=abs(y[i]-x[j-1]);}
		else if(j==0){min_x=abs(x[0]-y[i]);}
		else {min_x=find_min(abs(y[i]-x[j-1]),abs(y[i]-x[j]));}		
		min=find_min(min_x,min);
	}
	printf("%d",min);
	system("pause");
	return 0;
}
