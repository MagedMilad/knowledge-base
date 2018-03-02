// Is Unique:
// Implement an algorithm to determine if a string has all unique characters.
// What if you cannot use additional data structures?

// time: O(N)
// memory: O(1)
boolean isUniqueString(String str){
  if (str.length() > 265) return false;

  boolean vis[] = new boolean[265];

  for(char c: str){
    int val = c;
    if(vis[val]) return false;
    vis[val] = true;
  }
  return true;
}
