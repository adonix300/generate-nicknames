package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3letters = new AtomicInteger(0);
    public static AtomicInteger counter4letters = new AtomicInteger(0);
    public static AtomicInteger counter5letters = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3), random);
        }


        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        counter3letters.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4letters.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5letters.incrementAndGet();
                    }
                }
            }
        });

        Thread OneLetterForWordThread = new Thread(() -> {
            for (String text : texts) {
                if (isOneLetterForWord(text)) {
                    if (text.length() == 3) {
                        counter3letters.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4letters.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5letters.incrementAndGet();
                    }
                }
            }
        });

        Thread IncreasingLettersThread =  new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingLetters(text)) {
                    if (text.length() == 3) {
                        counter3letters.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4letters.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5letters.incrementAndGet();
                    }
                }
            }
        });


        palindromeThread.start();
        OneLetterForWordThread.start();
        IncreasingLettersThread.start();

        try {
            palindromeThread.join();
            OneLetterForWordThread.join();
            IncreasingLettersThread.join();

            System.out.println("Красивых слов с длиной 3: " + counter3letters.get() + " шт");
            System.out.println("Красивых слов с длиной 4: " + counter4letters.get() + " шт");
            System.out.println("Красивых слов с длиной 5: " + counter5letters.get() + " шт");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String generateText(String letters, int length, Random random) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

//    public static void processTexts(String[] texts, int length, AtomicInteger counter) {
//        for (String text : texts) {
//            if (text.length() == length) {
//                if (isPalindrome(text) || isIncreasingLetters(text) || isOneLetterForWord(text)) {
//                    counter.incrementAndGet();
//                }
//            }
//        }
//    }

    public static boolean isPalindrome(String text) {
        return new StringBuilder(text).reverse().toString().equals(text);
    }

    public static boolean isOneLetterForWord(String text) {
        return text.chars().distinct().count() == 1;
    }

    public static boolean isIncreasingLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
