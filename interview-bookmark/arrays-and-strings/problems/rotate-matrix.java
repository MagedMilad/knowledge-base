// Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
// bytes, write a method to rotate the image by 90 degrees. Can you do this in place?


boolean rotate(int[][] matrix) {
  if(matrix.length != matrix[0].length) return false;

  int n = matrix.length;

  for(int layer = 0; layer < n/2 ; layer++){
    int first = layer;
    int last = n - layer - 1; // -1 no not repeat ourself
    for(int i = first; i < last; i++){
      int offset = i - first;

      int top = matrix[first][i];

      matrix[first][i] = matrix[last - offset][first];
      matrix[last - offset][first] = matrix[last][last - offset];
      matrix[last][last - offset] = matrix[i][last];

      matrix[i][last] = top;

    }
  }
}
