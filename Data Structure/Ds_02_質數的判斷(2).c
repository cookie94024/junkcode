#include<stdio.h>
#include<stdlib.h>
#include<math.h>

int is_prime_word(char str[])          //���լO�_����� 
{
    int i,no,limit,sum=0,count=0;
    for(i=0;;i++){      //�p���J�r����� 
        if(str[i]!='\0'){count++;}
        else break;
    }
    for(i=0;i<count;i++){       //�]�w�C�Ӧr������ 
        if(str[i]>='a'&&str[i]<='z')sum += str[i]-96;   //�p�g�r�� 
        else if(str[i]>='A'&&str[i]<='Z')sum += str[i]-38;      //�j�g�r�� 
    }
    limit = sqrt(sum);
    for(no=2;no<=limit;no++){       //�p��O�_�Q�㰣  �Y�Q�㰣�h���D���  return 0;
        if(sum%no==0) return 0;     
    }
    return 1;                       //�S�Q�㰣���h�����  return 1;
}

int main(void)
{
    int num,x;  
    char str[100];
    printf("��J�r��: ");
    scanf("%s",str);
    
    if(is_prime_word(str)){         //�I�s��ƥ�if else��X 
        printf("It is a prime word!\n");
    }else{
        printf("It is not a prime word!\n");
    }
            
    system("pause");
    return 0;
}
