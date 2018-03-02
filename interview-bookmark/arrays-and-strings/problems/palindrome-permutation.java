// Palindrome Permutation: Given a string, write a function to check if it is a permutation of
// a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A
// permutation is a rearrangement of letters. The palindrome does not need to be limited to just
// dictionary words.


boolean isPermutationOfPalindrome(String Str){
  int cnt[] = new int[26];
  int oddCnt  = 0;

  for(char c : str){
    cnt[c - 'a']++;
    if(cnt[c - 'a']%2 == 1) oddCnt++;
    else oddCnt --;
  }

  return oddCnt <= 1;
}
