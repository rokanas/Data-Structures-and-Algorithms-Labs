# Lab: Text indexing

In this lab, you will implement a very fast search for large text files.

[This readme is still being updated! Pull again later to get the full version.]

## About the labs

* This lab is part of the examination of the course.
  Therefore, you must not copy code from or show code to other students.
  You are welcome to discuss general ideas with one another, but anything you do must be **your own work**.
* Further info on Canvas:
  - [general information](https://chalmers.instructure.com/courses/23356/pages/labs-general-information)
  - [the lab system](https://chalmers.instructure.com/courses/23356/pages/the-lab-system)
  - [running Java labs](https://chalmers.instructure.com/courses/23356/pages/running-java-labs)

# Background

If we want to search for a string in a text file, we usually iterate through the file from the start until we find an occurrence of the string.
This works fine for small-to-medium sized texts,
but when the files contain millions of words this simple idea becomes too slow.

To solve this, we can calculate a *search index* in advance.
Much like an index in a book, a search index is a data structure that allows us to quickly find all the places where a given string appears.

Search indexes are used in many applications -- for example, database engines use them to be able to search quickly.

In this lab, you will build a search index using a data structure called a *suffix array*.
Suffix arrays build on ideas from *sorting algorithms* and *binary search* to search efficiently in large texts.

Later in the course, you will learn about two more data structures, called *hash tables* and *search trees*, which can be used to implement a search index that can be updated quickly.

The lab has four parts (but some of them are rather short):

* In part 1, you will implement versions of insertion sort and quicksort.
* In part 2, you will create a search index for a text file, using your sorting algorithms from part 1.
* In part 3, you will use the search index to find strings in the text file quickly.
* In part 4, you will implement multi-key quicksort, a specialized sorting algorithm for strings.

By the end, you will be able to search through large texts in a millisecond!
(Building the index will take some time, but the search will be instantaneous.)

# Getting started

The lab directory contains several Java files.
You will be modifying the following files:

Part 1:
- **sorting/InsertionSort.java**: implementation of insertion sort
- **sorting/Quicksort.java**: implementation of quicksort

Part 2:
- **SuffixArray.java**: building the search index (`build()`)

Part 3:
- **SuffixArray.java**: performing search queries (`searchForKey(...)`)

Part 4:
- **sorting/MultiKeyQuicksort.java**: implementation of multi-key quicksort

In the `texts` directory you will find three excerpts from the British National Corpus (BNC), a large collection of English-language text collected from all kinds of spoken and written sources:

- `bnc-mini.txt` (2.0Mb): the first 20,000 sentences of BNC (≈0.4 million words)
- `bnc-small.txt` (5.5Mb): the first 60,000 sentences of BNC (≈1 million words)
- `bnc-medium.txt` (19Mb): the first 200,000 sentences of BNC (≈3.5 million words)

These are just ordinary text files; you can open `bnc-mini.txt` in your text editor if you like to see what it looks like.

The rest of this readme is still being written.
For now, see the comments in the above files for instructions.

## Literature

- Wikipedia explains suffix arrays: https://en.wikipedia.org/wiki/Suffix_array

- Sedgewick & Wayne (2011) has a full chapter on string algorithms (chapter 5), including text searching.
  They even have a section about suffix arrays (in chapter 6 "Context").
