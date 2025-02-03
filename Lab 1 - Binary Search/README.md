# Lab: Binary search

This short lab has two purposes:
* to introduce you to the lab system,
* to practice versions of binary search.

## About the labs

* This lab is part of the examination of the course.
  Therefore, you must not copy code from or show code to other students.
  You are welcome to discuss general ideas with one another, but anything you do must be **your own work**.
* Further info on Canvas:
  - [general information](https://chalmers.instructure.com/courses/23356/pages/labs-general-information)
  - [the lab system](https://chalmers.instructure.com/courses/23356/pages/the-lab-system)
  - [running Java labs](https://chalmers.instructure.com/courses/23356/pages/running-java-labs)

## Overview

Binary search is a highly efficient way to find an item in a *sorted* array.
It works by looking at increasingly smaller *ranges* of the array (where each range is given by a low index and a high index).
Initiality, the range is the entire array.
In each step, we compare the given item with the middle element of the current range:
* If the item is smaller, it can only be in the left side of the range.
* If the item is larger, it can only be in the right side of the range.

We continue with this, if necessary until the range is empty.

In this lab, you will implement two versions of binary search in `BinarySearch.java` (without looking up how to do it):
* a basic version that just checks if the item is in the array,
* an advanced version that finds the *first position* where the item occurs.

Both versions should use as few comparisons as possible.
We encourage you to experiment with both the *iterative* and *recursive* styles to write these algorithms.

Finally, you will answer a few questions in `answers.txt`.

## Details

Your task is to implement the following two functions in `BinarySearch.java`:

```java
    // Check if the array `a` contains the given search key.
    public static<T> boolean contains(T[] a, T key, Comparator<T> comparator) {
        [...]
    }

    // Return the *first position* of `key` in `a`, or -1 if `key` does not occur.
    public static<T> int firstIndexOf(T[] a, T key, Comparator<T> comparator) {
        [...]
    }
```

For example, calling `firstIndexOf` on the array {1, 1, 3, 3, 5, 5} with key 3 should return 2 because the first occurrence of 3 in the array is at index 2.

You should assume that all arguments are non-null and `a` is sorted according to the given comparator (i.e., smaller items come first).

These functions are *generic* in the type T of the array elements.
That means they can be called with T substituted by any concrete type, for example numbers (`Integer`) or strings (`String`) (but not primitive types such as `int`).
When writing these functions, we do not know anything about T, so we have to treat it as a black box.

### Comparing two items

Since we do not know anything about the type T, we have to rely on the provided *comparator* to tell us how two objects x and y of type T are related.
For this, we call the `compare` method of the comparator:

```java
int c = comparator.compare(x, y);
```

This has the following meaning:
* If c < 0, then x is **less than** y.
* If c = 0, then x is **equal to** y.
* If c > 0, then x is **greater than** y.

**Note:**
The class `BinarySearch` also contains overloaded versions of `contains` and `firstIndexOf` that do not take a comparator argument, but instead require T to implement the interface `Comparable`.
For example, this the case for `Integer` and `String` (with the alphabetic ordering).
So when you test on arrays of such types, you do not need to specify a comparator.
Instead, the natural ordering will be used.

### Different approaches

There are two main approaches for implementing binary search:
* the *iterative style*: using a loop,
* the *recursive style*: using a helper function that calls itself.

We want you to experiment with both styles.
So write **one function iteratively** and **the other recursively**.
You can choose which style to use where.

**Note**:
If you are stuck on the recursive approach, have a look at the [recursion practice](https://chalmers.instructure.com/courses/23356/pages/reading-material#recursion-practice).
If you are really stuck, here are some hints (click to see):

<details>
<summary>Spoiler 1</summary>

Do you have an iterative solution (using a loop)?
Try to convert it into a recursive solution by turning the loop into a recursive helper function.
</details>

<details>
<summary>Spoiler 2</summary>

For the recursive solution, you will have to add a helper function to `BinarySearch`.
This function should have the same signature as the original function, except for some extra arguments that change in the recursive call.
</details>

<details>
<summary>Spoiler 3</summary>

Try to give the recursive helper function two extra arguments `lo` and `hi`.
These tell you which range of the array to search in.
How does the helper function call itself?
</details>

### Requirements

* You are not allowed to call `Arrays.binarySearch` from `java.util` or look up code for binary search elsewhere.
  You have to implement your own binary search!

* We consider values x and y of type T equal if `comparator.compare(a, b)` returns 0.
  Do not use `x.equals(y)` or `x == y` (reference equality).

* Both functions should use as few comparisons as possible for a given array size.
  (A comparison is a call `comparator.compare(…)`.)
  Note that the array can contain lots of duplicate elements.

## Testing

* The class `BinarySearch` has a main function with a few tests, using `assert`.
  To use them, you need to enable assertions when running Java (e.g., `java -ea BinarySearch`).
  Feel free to add your own tests.

* The debugger is very useful for finding out where your function goes wrong.
  It allows you to go through your function line-by-line and see how the variables change.
  If you have not used a debugger before, grab a teaching assistant to teach you.

* [Robograder](https://chalmers.instructure.com/courses/23356/pages/the-lab-system#robograder) will run some pretty comprehensive tests on your code.
  Make sure to use it before you submit.

## Submission

Double check:
* Have you answered the questions in `answers.txt`?
* Have you tested your code with Robograder?

[How to submit](https://chalmers.instructure.com/courses/23356/pages/the-lab-system#submit-a-lab).
