
import com.sun.nio.sctp.AbstractNotificationHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class MainClass {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Детерменированный - D : Стохастический - S");
        Scanner in = new Scanner(System.in);
        System.out.println("Введите букву: ");
        String enter = in.nextLine();
        //закомментить, если надоест вводить в клавы
        System.out.println("Введите название файла");
        String nameFile = in.nextLine();
        File file = new File(nameFile);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.lines().forEach(System.out::println);
//        File file = new File("testFormPresentation.txt");
//        File file = new File("KM2.txt");
//        File file = new File("Mx3.txt");
//        File file = new File("test01.txt");
      //  System.out.println("Нажмите Q для выхода");



            if (enter.equals("D") || enter.equals("d")) {
                int howManyClasses = howManyClasses(file);
                if (howManyClasses > 2) {
                    DetermenicForMoreThanTwo process = new DetermenicForMoreThanTwo(file);
                    process.study();
                    System.out.println("Введите вектор значения вектора");
                    while (true) {

                        String vector = in.nextLine();
                        String[] stringMasVector = vector.split(" ");
                        Double[] doubleVector = new Double[stringMasVector.length];
                        for (int i = 0; i < doubleVector.length; i++) {
                            doubleVector[i] = Double.parseDouble(stringMasVector[i]);
                        }
                        process.work(doubleVector);
                        System.out.println("Введите вектор значения вектора");
                    }

                } else {
                    DetermenicForTwo process = new DetermenicForTwo(file);
                    process.study();
                    System.out.println("Введите вектор значения вектора");
                    while (true) {

                        String vector = in.nextLine();
                        String[] stringMasVector = vector.split(" ");
                        Double[] doubleVector = new Double[stringMasVector.length];
                        for (int i = 0; i < doubleVector.length; i++) {
                            doubleVector[i] = Double.parseDouble(stringMasVector[i]);
                        }
                        process.work(doubleVector);
                        System.out.println("Введите вектор значения вектора");
                    }
                }
            } else {
                Stohastic process = new Stohastic(file);
                process.study();
                while (true) {
                    System.out.println("Введите вектор значения вектора");
                    String vector = in.nextLine();
                    String[] stringMasVector = vector.split(" ");
                    Double[] doubleVector = new Double[stringMasVector.length];
                    for (int i = 0; i < doubleVector.length; i++) {
                        doubleVector[i] = Double.parseDouble(stringMasVector[i]);
                    }
                    process.work(doubleVector);
                }
            }


    }

    public static int howManyClasses(File file) throws FileNotFoundException {
        Map<String, String> listClass = new HashMap<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.lines().forEach(line -> {
            if (!listClass.containsKey(line.substring(0,1))){
                listClass.put(line.substring(0,1), line.substring(0,1));
            }
        });
        return listClass.entrySet().size();
    }
}


