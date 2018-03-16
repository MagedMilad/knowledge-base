// Search in Rotated Array: Given a sorted array of n integers that has been rotated an unknown
// number of times, write code to find an element in the array. You may assume that the array was
// originally sorted in increasing order.

// EXAMPLE
// lnput:find5in{15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14}
// Output: 8 (the index of 5 in the array)


int Search(int[] arr, int key){
  int start = 0;
  int end = arr.length;

  while(start < end){
    int mid = (start + end) / 2;

    if(arr[mid] == key) return mid;

    if(arr[start] <= arr[mid]){
      if(arr[start] <= key && key <= arr[mid])
        end = mid - 1;
      else
        start = mid + 1;
    }else{
      if(arr[mid] <= key && key <= arr[end])
        start = mid + 1;
      else
        end = mid - 1;
    }
  }

  return -1;
}
