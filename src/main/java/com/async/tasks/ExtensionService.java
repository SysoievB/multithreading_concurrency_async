package com.async.tasks;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Implement the addExtension method that accepts two string predicates and returns a function.
 * The returning function should accept one argument and return the argument with .xml suffix if
 * the argument matches the first predicate, .json suffix if the argument matches the second predicate,
 * and the argument itself otherwise.
 * Here is an example:
 *
 * Function<String, String> function = addExtension(
 *         reportName -> reportName.contains("report"),
 *         logsName -> logsName.contains("logs")
 * );
 *
 * String file = function.apply("main_report");
 * System.out.println(file); // main_report.xml
*/

class ExtensionService {
    public static void main(String[] args) {
        Function<String, String> func = addExtension(y -> y.equals("First"), y -> y.equals("Second"));
        Function<String, String> func1 = addExtensionSecond(y -> y.equals("First"), y -> y.equals("Second"));
        System.out.println(func.apply("First"));//should print First.xml
        System.out.println(func.apply("Second"));//should print Second.json
        System.out.println(func.apply("Otherwise"));//should print Otherwise
        System.out.println(func1.apply("First"));//should print First.xml
        System.out.println(func1.apply("Second"));//should print Second.json
        System.out.println(func1.apply("Otherwise"));//should print Otherwise
    }

    public static Function<String, String> addExtension(Predicate<String> predicate1, Predicate<String> predicate2) {
        return str -> predicate1.test(str) ? str + ".xml" : predicate2.test(str) ? str + ".json" : str;
    }

    public static Function<String, String> addExtensionSecond(Predicate<String> predicate1, Predicate<String> predicate2) {
        return str -> {
            if (predicate1.test(str)) {
                return str + ".xml";
            } else if (predicate2.test(str)) {
                return str + ".json";
            }
            return str;
        };
    }
}


