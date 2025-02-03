import java.util.Comparator;

public class BinarySearch {

  // Common description of the below functions:
  // * Precondition: `a` is sorted according to the given comparator.
  // * Precondition: all arguments are non-null (no need to check).
  // * Required complexity: O(log(n)) comparisons where n is the length of `a`.

  // Check if the array `a` contains the given search key.
  public static <T> boolean contains(T[] a, T key, Comparator<T> comparator) {
    int low = 0;
    int high = (a.length - 1);

    return recursiveFunction(a, key, comparator, low, high);
  }

  public static <T> boolean recursiveFunction(T[] a, T key, Comparator<T> comparator, int low, int high) {
    if (low <= high) {
      int mid = (low + high) / 2;
      int c = comparator.compare(key, a[mid]);
      if (c == 0) {
        return true;
      } else if (c < 0) {
        return recursiveFunction(a, key, comparator, low, (mid - 1));
      } else if (c > 0) {
        return recursiveFunction(a, key, comparator, (mid + 1), high);
      }
    }
    return false;
  }

  // Return the *first position* of `key` in `a`, or -1 if `key` does not occur.
  public static <T> int firstIndexOf(T[] a, T key, Comparator<T> comparator) {
    int low = 0;
    int high = (a.length - 1);
    int mid;
    int firstIndex = -1;
    while (low <= high) {
      mid = (low + high) / 2;
      int c = comparator.compare(key, a[mid]);
      if (c == 0) {
        firstIndex = mid;
        high = mid - 1;
      } else if (c > 0) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return firstIndex;
  }

  // Versions of the above functions that use the natural ordering of the type T.
  // T needs to be "comparable" (i.e., implement the interface Comparable).
  // Examples: Integer, String (the alphabetic ordering)

  public static <T extends Comparable<? super T>> boolean contains(T[] a, T key) {
    return contains(a, key, Comparator.naturalOrder());
  }

  public static <T extends Comparable<? super T>> int firstIndexOf(T[] a, T key) {
    return firstIndexOf(a, key, Comparator.naturalOrder());
  }

  // Your tests.

  public static void main(String[] args) {
    Integer[] a = { 1, 3, 5, 7, 9 };
    assert contains(a, 1);
    assert !contains(a, 4);
    assert contains(a, 7);

    String[] b = { "cat", "cat", "cat", "dog", "turtle", "turtle" };
    assert firstIndexOf(b, "cat") == 0;
    assert firstIndexOf(b, "dog") == 3;
    assert firstIndexOf(b, "turtle") == 4;
    assert firstIndexOf(b, "zebra") == -1;
    assert firstIndexOf(b, "bee") == -1;
  }
}
