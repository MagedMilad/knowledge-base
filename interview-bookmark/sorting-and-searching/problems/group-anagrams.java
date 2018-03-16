// Group Anagrams: Write a method to sort an array ot strings so that all tne anagrnms are next to
// each other.


void sort(String[] array) {
  HashMap<String, List<String> > map = new HashMap<String, LinkedList<String>>();

  for(String s: array){
    String key = sortString(s);
    if(!map.containsKey(key))
      map.set(key, new LinkedList<String>());
    map.get(key).add(s);
  }

  int idx=0;
  for(String key : map.keySet()){
    LinkedList<String> l = map.get(key);
    for(String s : l){
      array[idx++] = s;
    }
  }

}


String sortString(String s){
  char [] content = s.toCharArray();
  Arrays.sort(content);
  return new String(content);
}
