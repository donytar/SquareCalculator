package com.donytar;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static final int MAX_LINES = 100;
    public static final int MAX_VALUE = 10000;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Wrong arguments");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        final Set<Point> pointSet = new TreeSet<Point>();

        try {
            OneByOneStringReader.Receiver receiver = new OneByOneStringReader.Receiver() {
                private int counter = 0;

                @Override
                public boolean onStringRead(String string) throws InvalidFormatException {
                    if (++counter > MAX_LINES) {
                        return false;
                    }

                    for (Point point : parsePoints(string)) {
                        pointSet.add(point);
                    }

                    return true;
                }
            };

            OneByOneStringReader.read(new FileInputStream(new File(inputFile)), receiver);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            System.err.println(e.toString());
        }


        try {
            PrintWriter file = new PrintWriter(outputFile);
            file.print(pointSet.size());
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static List<Point> parsePoints(String string) throws InvalidFormatException {
        List<Point> points = new LinkedList<Point>();
        int[] numbers = stringsToInts(string.split(" "));
        if (numbers.length != 4) {
            throw new InvalidFormatException("Incorrect coordinates of rectangle");
        }

        for (int x = numbers[0]; x < numbers[2]; x++) {
            for (int y = numbers[1]; y < numbers[3]; y++) {
                points.add(new Point(x, y));
            }
        }

        return points;
    }

    public static int[] stringsToInts(String[] strings) throws InvalidFormatException{
        try {
            int[] ints = new int[strings.length];
            for (int i = 0; i < strings.length; ++i) {
                ints[i] = Integer.parseInt(strings[i]);
                if (Math.abs(ints[i]) > MAX_VALUE) {
                    throw new InvalidFormatException("Coordinate value overflow");
                }
            }

            return ints;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(e.toString());
        }
    }
}
