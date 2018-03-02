// One Away: There are three types of edits that can be performed on strings: insert a character,
// remove a character, or replace a character. Given two strings, write a function to check if they are
// one edit (or zero edits) away.


boolean oneEditAway(String first, String second){
  int idx1 = 0, idx2 = 0;
  boolean edit = false;

  // str1.length() >= str2.length()
  String str1 = first.length() > second.length() ? first : second;
  String str2 = first.length() > second.length() ? second : first;

  while(idx1 < str1.length() && idx2 < str2.length()){
    if(str1.charAt(idx1) == str2.charAt(idx2)){
      idx1++;
      idx2++;
      continue;
    }
    if(edit) return false;
    edit = true;

    if(str1.length() == str2.length()) idx2++;
    idx1++;
  }
}
