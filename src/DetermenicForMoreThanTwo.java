import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetermenicForMoreThanTwo extends Process {

    Map<Integer, List<Double[]>> matrixesOfFile = new HashMap<>();
    Map<Integer, List<Double[]>> koefMap = new HashMap();

    DetermenicForMoreThanTwo(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.lines().forEach(line -> {

                if (matrixesOfFile.containsKey(getClassByString(line))) {

                    List<Double[]> matrix = matrixesOfFile.get(getClassByString(line));
                    matrix.add(convertStringToIntMas(line));
                    matrixesOfFile.put(getClassByString(line), matrix);

                } else {
                    List<Double[]> matrix = new ArrayList<>();
                    matrix.add(convertStringToIntMas(line));
                    matrixesOfFile.put(getClassByString(line), matrix);
                }


            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void study() {
        for (Map.Entry classMatrix : matrixesOfFile.entrySet()) { //идем по всем матрицам для каждого из классов
            List<Double[]> matrixForFormula = (List<Double[]>)classMatrix.getValue();
            int i = 0;
            int streak = 0;
            int iter = 0;
             //идем по матрице
            while (streak != matrixForFormula.size()) {
                if (koefMap.containsKey((Integer)classMatrix.getKey())) {    //если есть матрица коэффициентов для этого класса
                    double result = formula(matrixForFormula.get(i), (Integer)classMatrix.getKey());
                    if (result <= 0) {
                        List<Double[]> koefListForClass = koefMap.get((Integer)classMatrix.getKey());
                        koefListForClass.add(matrixForFormula.get(i));
                        koefMap.put((Integer)classMatrix.getKey(), koefListForClass);
                        streak = 0;
                    } else {
                        streak++;
                    }
                    if (i == matrixForFormula.size()- 1) {
                        i = 0;
                    } else {
                        i++;
                    }
                 } else {                                                    //если нету, тогда считаем что это первые коэффициенты и кладем туда
                    List<Double[]> koefList = new ArrayList<>();
                    koefList.add(matrixForFormula.get(i));  // полчаем коэффициент
                    koefMap.put((Integer)classMatrix.getKey(), koefList);   //кладем
                    i++;
                }
                iter++;
            }
            System.out.println("Обучение завершено для класса " + String.valueOf((Integer)classMatrix.getKey()) + " .Количество итераций " + iter);

        }
        System.out.println("Полное обучение завершено");
    }

    public void work(Double[] test){
        Double max = 0.0;
        int maxClass = 1;
        for (Map.Entry koef : koefMap.entrySet()){
            double result = formula(test, (Integer)koef.getKey());
            if (result > max) {
                maxClass = (Integer)koef.getKey();
                max = result;
            }
        }

        System.out.println("Входной вектор принадлежит " + maxClass + " классу");
    }

    private double formula(Double[] currentLine, Integer classNumber) {
        double result = 0; //подсчет
        for (int i = 0; i < koefMap.get(classNumber).size(); i++) { //идем по всем коэффициентам
            double resultForLine = 0;
            for (int j = 0; j < currentLine.length; j++) {
                resultForLine += Math.pow(currentLine[j] - koefMap.get(classNumber).get(i)[j], 2);
            }
            result = result + Math.pow(Math.E, resultForLine * -1);
        }
        return result;
    }
}
