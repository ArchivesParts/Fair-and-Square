/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gregory ANNE <gregonmac@gmail.com>
 */
public class FairAndSquare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String inputFile = "C-small-attempt0.in";
//        String outputFile = "C-small-attempt0.test";
//        String inputFile = "test.txt";
//        String outputFile = "output.txt";
//        String inputFile = "C-large-1.in";
//        String outputFile = "C-large-1.out";
        String inputFile = "A-small-attempt0.in";
        String outputFile = "A-small-attempt0.out";
        BufferedReader reader;
        FairAndSquare littleJohn = new FairAndSquare();
        TicTacToe ttt = new TicTacToe();
        try {
            // TODO code application logic here
            reader = new BufferedReader(new FileReader(inputFile));

            String firstLine = reader.readLine();
            Integer testNumber = new Integer(firstLine);
            String testCase;
            File file = new File(outputFile);
            PrintWriter writer = new PrintWriter(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            for (int i = 1; i <= testNumber; i++) {
                for (int y = 1; y <= 4; y++) {
                    testCase = reader.readLine();
                    for (int x = 1; x <= 4; x++) {
                        ttt.addValue(testCase.charAt(x-1), x, y);
                    }
                }
                writer.println("Case #" + ttt.getResult());
//                Double start = new Double(tokenizer.nextToken());
//                Double stop = new Double(tokenizer.nextToken());
//                littleJohn.addEntry(i, start, stop);
            }
//            for (InputLine inputLine : littleJohn.launchScan()) {
//                writer.println("Case #" + inputLine.getPosition() + ": " + inputLine.getCount());
//            }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FairAndSquare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FairAndSquare.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }

    }
    private HashMap<Double, Done> alreadyDone;
    private Double limit;
    List<InputLine> datas = new ArrayList<InputLine>();

    public FairAndSquare() {
        this.alreadyDone = new HashMap<Double, Done>();
        limit = new Double(100000);
    }

    public List<InputLine> launchScan() {
        Collections.sort(datas);
        int counter = datas.size();
        long startTime = new Date().getTime();
        System.out.println("Start  :" + new Date());
        for (InputLine inputLine : datas) {
            System.out.println(inputLine + "- rest :" + counter);
            this.optimizedScanner(inputLine);
            counter--;
        }
        System.out.println("Stop  :" + new Date());
        long endTime = new Date().getTime();
        System.out.println(" in " + (endTime - startTime));
        Collections.sort(datas, new PositionComparator());
        return datas;
    }

    public void addEntry(final Integer position, final Double start, final Double stop) {
        InputLine t = new InputLine(position, start, stop);
        datas.add(t);
    }

    private void optimizedScanner(final InputLine line) {
        int counter = 0;
        Double start = line.getStart();
        Double stop = line.getStop();
        if (stop - start >= limit) {
            // Step 1
            if (start % limit != 0) {
                counter += this.scanner(start, limit - 1);
                start = limit;
            }
            // Step 2
            Double preStop = (Math.floor(stop / limit) * limit);
            while (start < preStop) {
                int part;
                Done done = this.alreadyDone.get(start);
                if (done == null) {
                    done = new Done(start, start + limit - 1, this.scanner(start, start + limit - 1));
                    this.alreadyDone.put(start, done);
                }
                counter += done.getCount();
                start = start + limit;
            }
            // Step 3
            if (!preStop.equals(stop)) {
                counter += this.scanner(preStop, stop);
            }


        } else {
            counter = this.scanner(start, stop);
        }
        line.setCount(counter);
    }

    private int scanner(final Double start, final Double stop) {
        StringBuilder b = new StringBuilder();
        b.append(start).append("=").append(stop);
        Logger.getLogger(FairAndSquare.class.getName()).log(Level.INFO, b.toString());
        int counter = 0;
        for (Double i = start; i <= stop; i++) {
            if (this.isFairAndSquare(i)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isFairAndSquare(final Double input) {
        Double square = Math.floor(Math.sqrt(input));
        Boolean result = false;
        if (Math.pow(square, 2) == input && this.isPalindrome(input)) {
            result = this.isPalindrome(square);
        }
        return result;
    }

    private boolean isPalindrome(final Double entry) {
        String s1 = new Integer(entry.intValue()).toString();
        String s2 = new StringBuffer(s1).reverse().toString();
        return s1.equals(s2);


    }

    public class InputLine implements Comparable<InputLine> {

        private Double start;
        private Double stop;
        private int position;
        private int count;

        public InputLine(Integer position, Double start, Double stop) {
            this.start = start;
            this.stop = stop;
            this.count = 0;
            this.position = position;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Double getStart() {
            return start;
        }

        public Double getStop() {
            return stop;
        }

        public int getCount() {
            return count;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public int compareTo(InputLine t) {
            Double range1 = new Double(this.stop - this.start);
            Double range2 = new Double(t.getStop() - t.getStart());
            return range1.compareTo(range2);
        }

        @Override
        public String toString() {
            return "InputLine{" + "start=" + start + ", stop=" + stop + ", position=" + position + ", count=" + count + '}';
        }
    }

    protected class PositionComparator implements Comparator<InputLine> {

        @Override
        public int compare(InputLine t, InputLine t1) {
            return t.getPosition().compareTo(t1.getPosition());
        }
    }

    private class Done {

        private Double start;
        private Double stop;
        private int count;

        public Done(Double start, Double stop, int count) {
            this.start = start;
            this.stop = stop;
            this.count = count;
        }

        public Double getStart() {
            return start;
        }

        public Double getStop() {
            return stop;
        }

        public int getCount() {
            return count;
        }
    }
}
