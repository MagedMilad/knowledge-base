// URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
// has sufficient space at the end to hold the additional characters, and that you are given the "true"
// length of the string. (Note: if implementing in Java, please use a character array so that you can
// perform this operation in place.)


void URLify(char[] str, int trueLength){
  int spaceCnt = 0;
  for(int i = 0; i < trueLength; i++){
    if(str[i] == ' ') spaceCnt++;
  }

  int idx = trueLength + spaceCnt * 2;
  str[idx] = '\0';

  for(int i = trueLength-1; i >= 0 ; i--){
    if(str[i] == ' '){
      str[idx - 1] = '0';
      str[idx - 2] = '2';
      str[idx - 3] = '%';
      idx -= 3;
    }else{
      str[idx - 1] = str[i];
      idx --;
    }
  }
}
