// Sparse Search: Given a sorted array of strings that is interspersed with empty strings, write a
// method to find the location of a given string.
// EXAMPLE
// Input: ball, {"at",
// ""}
// "" , "" , "" ,
// "ball", ,,,, , ,,,, , "car",
// "" , "" ,
// "dad",
// "" ,
// Output: 4


int search(String[] arr, String val){
  int low = 0;
  int high = arr.length;

  while (low < high) {
    int mid = (low + high) / 2;

    if(arr[mid].equals(val)) return mid;

    if(arr[mid].equals("")){
      while(true){
        int left = mid-1;
        int right = mid+1;

        if(left < 0 && right >= arr.length) return -1;

        if(left >= low && !arr[left].equals("")){
          mid = left;
          break;
        }

        if(righ <= high && !arr[right].equals("")){
          mid = right;
          break;
        }
      }
    }

    if(arr[mid].compareTo(val) < 0){
      high = mid - 1;
    }else{
      low = mid + 1;
    }
  }

  return -1;
}
