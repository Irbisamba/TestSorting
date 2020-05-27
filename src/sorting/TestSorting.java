package sorting;

import sorting.service.LoaderImpl;
import sorting.service.WriterImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestSorting {
    public static void main(String[] args) throws IOException {
        TestSorting testSorting  = new TestSorting();

        List<String> firstSorting = testSorting.sortByNaturalOrdering("file.txt", "sorted1.txt");
        List<String> secondSorting = testSorting.sortByStringLenght("file.txt", "sorted2.txt");
        List<String> thirddSorting = testSorting.sortByQuantityInput("file.txt", "sorted3.txt");

    }
    public List<String> sortByNaturalOrdering(String fileFrom, String fileTo) {
        LoaderImpl loader = new LoaderImpl();
        WriterImpl writer = new WriterImpl();
        List<String> stringList = loader.load(fileFrom);
        Collections.sort(stringList);
        writer.write(stringList, fileTo);
        return stringList;
    }

    public List<String> sortByStringLenght(String fileFrom, String fileTo) {
        LoaderImpl loader = new LoaderImpl();
        WriterImpl writer = new WriterImpl();
        List<String> stringList = loader.load(fileFrom);
        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;
                if(s1.length()==s2.length())
                    return 0;
                if(s1.length()>s2.length())
                    return 1;
                if(s1.length()<s2.length())
                    return -1;
                return 0;
            }
        };
        Collections.sort(stringList, comparator);
        writer.write(stringList, fileTo);
        return stringList;
    }

    public List<String> sortByQuantityInput(String fileFrom, String fileTo) throws IOException {
        int number = getNumberFromConsole();
        LoaderImpl loader = new LoaderImpl();
        WriterImpl writer = new WriterImpl();
        List<String> stringList = loader.load(fileFrom);
        List<StringBuilder> stringBuilders = new ArrayList<>(stringList.size());
        for(String s : stringList) {
            stringBuilders.add(new StringBuilder(s).append(" 1"));
        }

        Comparator<StringBuilder> comparator1 = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = ((StringBuilder) o1).toString();
                String s2 = ((StringBuilder) o2).toString();
                String [] array1 = s1.split(" ");
                String [] array2 = s2.split(" ");
                if(array1[number-1].equals(array2[number-1])) {
                    changeSuffix((StringBuilder) o1);
                    changeSuffix((StringBuilder) o2);
                    return 0;
                } else {
                    return array1[number-1].compareTo(array2[number-1]);
                }
            }
        };
        Comparator<StringBuilder> comparator2 = new Comparator<StringBuilder>() {
            @Override
            public int compare(StringBuilder o1, StringBuilder o2) {
                StringBuilder rev1 = o1.reverse();
                String s1 = rev1.toString();
                StringBuilder rev2 = o2.reverse();
                String s2 = rev2.toString();
                o1.reverse();
                o2.reverse();
                return s1.compareTo(s2);
            }
        };
//        List<String[]> listOfArrays = new ArrayList<>();
//        List<String> resultList = new ArrayList<>();
//        for(String s : stringList) {
//            String [] array = s.split(" ");
//            listOfArrays.add(array);
//        }
//        for(int i = 0; i < listOfArrays.size(); i++) {
//            int outerCounter = 0;
//            for(int j = 0; j < listOfArrays.size(); j++) {
//
//                if(listOfArrays.get(i)[number-1].equals(listOfArrays.get(j)[number-1])) {
//                    outerCounter++; }
//            }
//
//            StringBuilder stringBuilder = new StringBuilder(stringList.get(i));
//            stringBuilder.append(" " + outerCounter);
//            resultList.add(stringBuilder.toString());
//        }
//        resultList = resultList.stream()
//                .map(s->reverse(s))
//                .sorted()
//                .map(s->reverse(s))
//                .collect(toList());
        Collections.sort(stringBuilders, comparator1);
        Collections.sort(stringBuilders, comparator2);
       // Collections.reverse(stringBuilders); //порядок от большего числа вхождений к меньшему
        writer.writeBuilder(stringBuilders, fileTo);
        return stringList;
    }

    private String reverse(String d){
        return new StringBuilder(d).reverse().toString();
    }

    private int getNumberFromConsole() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int number;
            System.out.println("Введите число от 1 до 10");
            String s = reader.readLine();
            try {
                number = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Введенное значение - не число");
                System.out.println("Попробуйте еще раз");
                return getNumberFromConsole();
            }
            if (number<=0||number>10) {
                System.out.println("Так не пойдет. Вы ввели слишком большое число. Или слишком маленькое...");
                return getNumberFromConsole();
            }
            return number;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void changeSuffix(StringBuilder stringBuilder) {
        String s = stringBuilder.toString();
        String[] array = s.split(" ");
        String suff = array[array.length-1];
        int i = 1;
        try {
            i = Integer.parseInt(suff);
            i++;
        } catch(NumberFormatException e) {

        } finally {
            int index = stringBuilder.lastIndexOf(suff);
            stringBuilder.deleteCharAt(index);
            stringBuilder.trimToSize();
            stringBuilder.append(" " + i);
        }
    }

}
