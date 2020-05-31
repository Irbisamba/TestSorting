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
    public static void main(String[] args) {
        TestSorting testSorting  = new TestSorting();

        testSorting.sortByNaturalOrdering("file.txt", "sorted1.txt");
        testSorting.sortByStringLenght("file.txt", "sorted2.txt");
        testSorting.sortByQuantityInput("file.txt", "sorted3.txt");

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
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        stringList.sort(comparator);
        writer.write(stringList, fileTo);
        return stringList;
    }

    public List<String> sortByQuantityInput(String fileFrom, String fileTo) {
        int number = getNumberFromConsole();
        LoaderImpl loader = new LoaderImpl();
        WriterImpl writer = new WriterImpl();
        List<String> stringList = loader.load(fileFrom);
        List<StringBuilder> stringBuilders = new ArrayList<>(stringList.size());
        for(String s : stringList) {
            stringBuilders.add(new StringBuilder(s).append(" 1"));
        }

        Comparator<StringBuilder> comparator1 = (o1, o2) -> {
            String s1 = o1.toString();
            String s2 = o2.toString();
            String [] array1 = s1.split(" ");
            String [] array2 = s2.split(" ");
            try {
                if(number<=array1.length-1&&number<=array2.length-1){
                if (array1[number - 1].equals(array2[number - 1])) {
                    changeSuffix(o1);
                    changeSuffix(o2);
                    return 0;
                } else {
                    return array1[number - 1].compareTo(array2[number - 1]);
                } }
                else {
                    throw new IndexOutOfBoundsException();
                }
            }catch (IndexOutOfBoundsException e) {
                System.out.println("Упс, кажется, число слишком большое. В нашей строке нет столько слов");
                return 0;
            }
        };

        Comparator<StringBuilder> comparator2 = (o1, o2) -> {
            StringBuilder rev1 = o1.reverse();
            String s1 = rev1.toString();
            StringBuilder rev2 = o2.reverse();
            String s2 = rev2.toString();
            o1.reverse();
            o2.reverse();
            return s1.compareTo(s2);
        };

        stringBuilders.sort(comparator1);
        stringBuilders.sort(comparator2);
       // Collections.reverse(stringBuilders); //порядок от большего числа вхождений к меньшему
        writer.writeBuilder(stringBuilders, fileTo);
        return stringList;
    }

    private int getNumberFromConsole() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int number;
            System.out.println("Введите число от 1 до 9");
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
            e.printStackTrace();
        } finally {
            int index = stringBuilder.lastIndexOf(suff);
            stringBuilder.deleteCharAt(index);
            stringBuilder.trimToSize();
            stringBuilder.append(" ").append(i);
        }
    }
}
