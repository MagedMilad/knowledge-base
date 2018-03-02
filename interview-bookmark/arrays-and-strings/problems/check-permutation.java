// Check Permutation:
// Given two strings, write a method to decide if one is a permutation of the other.


boolean isPermutation(String s1, String s2){
  if(s1.length() != s2.length()) return false;

  int [] cnt = new int [256];
  for(int i=0; i<s1.length(); i++){
    cnt[s1.charAt(i)]++;
    cnt[s2.charAt(i)]--;
  }

  for(int i=0; i<256; i++){
    if(cnt[i] != 0) return false;
  }
  return true;
}
