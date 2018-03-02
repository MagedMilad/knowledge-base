# Hash Tables
- we can impelement a hash table using `balanced binary search tree`
    + `O(log N)` lookup time
    + using less space because we allocate the needed space only
    + we can iterate throught it in order

# Strings
- Sorting String in Java
```java
String sort(String str){
    char [] content = str.toCharArray();
    Arrays.sort(content);
    return new String(content);
}
```
