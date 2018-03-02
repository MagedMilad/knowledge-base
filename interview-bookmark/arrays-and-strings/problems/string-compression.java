// String Compression: Implement a method to perform basic string compression using the counts
// of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
// "compressed" string would not become smaller than the original string, your method should return
// the original string. You can assume the string has only uppercase and lowercase letters (a - z).

String compress(String original){
  StringBuilder sb = new StringBuilder();
  int prevCnt = 1;
  for(int i=1; i<original.length(); i++){
    if(original.charAt(i) == original.charAt(i-1)){
      prevCnt++;
    }else{
      sb.append(original.charAt(i-1));
      sb.append(prevCnt);
      prevCnt = 1;
    }
  }
  sb.append(original.charAt(original.length()-1));
  sb.append(prevCnt);

  return sb.length() < original.length() ? sb.toString() : original;
}
