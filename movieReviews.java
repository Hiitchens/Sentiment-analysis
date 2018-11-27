package movieReviews2;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class movieReviews2 {
	public static ArrayList<String> reviews = new ArrayList<>();
	public static ArrayList<Double> rating = new ArrayList<>();
	public static ArrayList<Character> firstCharacter = new ArrayList<>();
	public static ArrayList<String> allReviews = new ArrayList<>();
	//public static ArrayList<Double> average = new ArrayList<>();
	public static ArrayList<String> singleReview = new ArrayList<>();
	public static ArrayList<String> positiveWord = new ArrayList<>();
	public static ArrayList<String> negativeWord = new ArrayList<>();
	public static ArrayList<Double> positiveWordRating = new ArrayList<>();
	public static ArrayList<Double> negativeWordRating = new ArrayList<>();
	public static Map<String[], Double> hashMap = new HashMap<String[], Double>();
	public static Map<String, SelectWord> wordmap = new HashMap<String, SelectWord>();
	public static Map<String, SelectWord> wordMap2BecauseImLazy = new HashMap<String, SelectWord>();
	public static TreeMap<Double, String> treeMap = new TreeMap<Double, String>();
	public static boolean theEnd = false;
	public static double numberOfOccurences = 0;
	public static int movieRating =0;
	public static String justWords;
	public static String[] theWord;
	public static String[] addTheseBadBoysUp;
	public static double wordRating = 0;
	public static double average = 0;
	public static double sentenceRating =0;
	public static String menuDisplay = "\nMain Menu \n\nSelect one of the following options:\n1. Get the score of a word\n2. Get the score of a sentence\n3. See the top 5 most positive words with more than five occurrences\n4. See the top 5 most negative words with more than five occurrences\n5. Exit\n6. View all reviews";
	public static void main(String[] args) {
		//        for(int i =0; i < firstCharacter.size(); i++){
		//            average.add((double) (firstCharacter));
		//        }

		//System.out.println(firstCharacter);
		//System.out.println(rating);
		System.out.println("Loading - Thanks for your patience");
		readFile("reviews.txt");
		//System.out.println(reviews);
		//splitReview();
		while(!theEnd){
			mainMenu();
		}
	}
	public static void readFile(String fileName){
		File file = new File(fileName);
		Scanner reviewsFile = null;
		try {
			reviewsFile = new Scanner(file).useDelimiter("[0-9]");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(reviewsFile.hasNextLine()){
			String line = reviewsFile.nextLine();
			allReviews.add(line);
			String number = line.substring(0,1);
			double result = Double.parseDouble(number);
			justWords = line.substring(2, line.length());
			reviews.add(justWords);
			rating.add(result);
		}
		
		reviewsFile.close();
	}
	public static String chooseWord() {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose a word: ");
		String word = input.nextLine();
		//input.close();
		return word;
	}
	public static double getOccurences(String word){
		for(int i = 0; i < reviews.size(); i++){
			if(reviews.get(i).contains(word)){

				numberOfOccurences++;


			}

		}
		return numberOfOccurences;

	}

	public static double getRating(String word){
		for(int i = 0; i < reviews.size(); i++){
			if(reviews.get(i).contains(word)){

				numberOfOccurences++;
				return rating.get(i);

			}

		}
		return -1.0;

	}


	public static void firstChoice(){
		String word = chooseWord();
		boolean match = false;
		for(int i = 0; i < reviews.size(); i++){
			if(reviews.get(i).contains(word)){

				numberOfOccurences++;
				wordRating += rating.get(i);
				average = (wordRating / numberOfOccurences);
			}
		}
		System.out.println("Checking...");
		System.out.println("There were " + numberOfOccurences + " occurences of " + word);
		System.out.println("That word is rated: " + wordRating);
		System.out.printf("The average for that word is: %.2f \n", average);
		numberOfOccurences = 0;
		wordRating = 0;
	}
	public static void secondChoice(){
		Scanner input = new Scanner(System.in);
		System.out.println("Choose a phrase: ");
		String sentence = input.nextLine();
		String[] words = sentence.split(" ");
		for(int i = 0; i < reviews.size(); i++){
			for(int j = 0; j < words.length; j++){
				if(reviews.get(i).contains(words[j])){
					numberOfOccurences++;
					wordRating += rating.get(i);
					average = (wordRating / numberOfOccurences);
				}
			}
		}
		System.out.println("Checking...");
		System.out.println("There were " + numberOfOccurences + " total occurences of the words in " + sentence);
		System.out.println("the average is: " + average);
		System.out.println("That sentence is rated: " + wordRating);
		numberOfOccurences = 0;
		wordRating = 0;
		sentenceRating = 0;
	}
	public static void thirdChoice(){
		System.out.println("The highest rated words, with more than 5 occurences are: ");

		long start = System.currentTimeMillis();
		File file = new File("reviews.txt");
		int timesOccured = 0;       

		Scanner reviewsFile = null;
		try {
			reviewsFile = new Scanner(file).useDelimiter("[0-9]");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(reviewsFile.hasNextLine()){
			
			String line = reviewsFile.nextLine();
			line = line.replaceAll("\\p{Punct}","").toLowerCase();
			String number = line.substring(0,1);
			double score = Double.parseDouble(number);
			String[] words = line.split(" ");
			for(int i = 0; i < words.length; i++){
				SelectWord temp = wordmap.get(words[i]);
				if(temp != null){
					temp.update(score);
				}else{
					wordmap.put(words[i], new SelectWord(words[i], score));
				}
			}
		}
		for(SelectWord selectWord : wordmap.values()){
			if(selectWord.count > 5){
				treeMap.put(4 - (double)selectWord.totalScore/selectWord.count, selectWord.word);
			}
			}
		int wordIndex = 0;
		
		
		for(String x: treeMap.values()){
			
			System.out.println(x);
			wordIndex++;
			if(wordIndex == 6){
				System.out.println("\n\nKen, please ignore top result '4', i can't get rid of this bug! :(");
				break;
			}
		}
		
		
	}
	public static void fourthChoice(){
		System.out.println("The lowest rated words, with more than 5 occurences are: ");
		long start = System.currentTimeMillis();
		File file = new File("reviews.txt");
		int timesOccured = 0;       

		Scanner reviewsFile = null;
		try {
			reviewsFile = new Scanner(file).useDelimiter("[0-9]");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(reviewsFile.hasNextLine()){
			
			String line = reviewsFile.nextLine();
			line = line.toLowerCase();
			String number = line.substring(0,1);
			double score = Double.parseDouble(number);
			String[] words = line.split(" ");
			for(int i = 0; i < words.length; i++){
				SelectWord temp = wordmap.get(words[i]);
				if(temp != null){
					temp.update(score);
				}else{
					wordmap.put(words[i], new SelectWord(words[i], score));
				}
			}
		}
		for(SelectWord selectWord : wordmap.values()){
			if(selectWord.count > 5 && !selectWord.equals("[0-9]")){
				treeMap.put((double)selectWord.totalScore/selectWord.count, selectWord.word);
			}
			}
	
		int wordIndex = 0;
		for(String x: treeMap.values()){
			System.out.println(x);
			wordIndex++;
			if(wordIndex == 6){
				break;
			}
		}
	}
	public static void buildWordAndRatingList(){
		int max = -1;
		for(int i = 0; i < reviews.size(); i++){
			String[] words = reviews.get(i).split(" ");
			System.out.print("inner: " + i + " ");
			for(int j = 0; j < words.length; j++){
				String word = words[j];
				double wordRating = getRating(word);
				//System.out.println(wordOccurences);
				numberOfOccurences = 0;
				double wordOccurences = getOccurences(word);
				//                System.out.print("word is: " + positiveWord.toString() + " \n");
				System.out.print("word is: " + word + " \n");
				double wordAverage = (wordRating / wordOccurences);
				//System.out.println("word: " + word + " wordRating: " + wordRating + " wordOccurences: " + wordOccurences + " wordAverage: " + wordAverage);
				if(wordOccurences > 5){
					if(wordAverage > 2){
						addWordAndRating(word, wordRating, positiveWord, positiveWordRating);
					} else {
						addWordAndRating(word, wordRating, negativeWord, negativeWordRating);
					}
				}
			}
		}
	}
	//MEant to be used for thrd and fourth choice
	public static void addWordAndRating(String word, double rating, ArrayList<String> wordArray, ArrayList<Double> ratingArray){
		//System.out.println("word: " + word);
		int count = wordArray.size();
		int index = -1;
		//        System.out.println("count: " +  count);
		if(wordArray.size() != 0){
			for(int i = 0; i < count; i++){
				//                System.out.println(i);
				//                if(i < 5){
				if(rating > ratingArray.get(i)){
					ratingArray.add(i, rating);
					wordArray.add(i, word);
					System.out.println("rating: " + rating);
					System.out.println("wordArray: " + wordArray.toString());
					//                        if(ratingArray.size() > 5){
					//                            ratingArray.remove(5);
					//                        }
				}
				//                }
			}
		} else {
			System.out.println(word);
			//array is empty, so adding elements regardless
			ratingArray.add(0, rating);
			wordArray.add(0, word);
		}
		if(index > -1){
		}

	}
	public static void mainMenu(){
		boolean running = true;
		while(running){
			Scanner choice = new Scanner(System.in);
			int value = 0;
			System.out.println(menuDisplay);
			try {
				value = choice.nextInt();
			} catch(Exception e){
				System.out.println("You must enter an integer. Please try again.");
				break;
			}
			switch (value){
			case 1:
				firstChoice();
				break;
			case 2:
				secondChoice();
				break;
			case 3:
				thirdChoice();
				break;
			case 4:
				fourthChoice();
				break;
			case 5:
				System.out.println("Thank you! -- Created by James Behnke");
				theEnd = true;
				running = false;
				break;
			case 6:
				System.out.println(allReviews);
				break;
			}
		}
	}

}

class SelectWord{
	public String word;
	public double rating;
	public int count=0;
	public double average;
	public double totalScore;
	SelectWord(String word, double rating){
		this.word = word;
		this.rating = rating;

	}
	public void update(double rating){
		count++;
		totalScore += rating;
		average = (double)rating/count;
	}
	
	public String toString(){
		return word + " is rated: " + totalScore + " with an average of " + average;
	}


}