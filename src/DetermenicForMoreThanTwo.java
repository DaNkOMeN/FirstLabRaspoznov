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
        for (Map.Entry classMatrix : matrixesOfFile.entrySet()) { //���� �� ���� �������� ��� ������� �� �������
            List<Double[]> matrixForFormula = (List<Double[]>)classMatrix.getValue();
            int i = 0;
            int streak = 0;
            int iter = 0;
             //���� �� �������
            while (streak != matrixForFormula.size()) {
                if (koefMap.containsKey((Integer)classMatrix.getKey())) {    //���� ���� ������� ������������� ��� ����� ������
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
                 } else {                                                    //���� ����, ����� ������� ��� ��� ������ ������������ � ������ ����
                    List<Double[]> koefList = new ArrayList<>();
                    koefList.add(matrixForFormula.get(i));  // ������� �����������
                    koefMap.put((Integer)classMatrix.getKey(), koefList);   //������
                    i++;
                }
                iter++;
            }
            System.out.println("�������� ��������� ��� ������ " + String.valueOf((Integer)classMatrix.getKey()) + " .���������� �������� " + iter);

        }
        System.out.println("������ �������� ���������");
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

        System.out.println("������� ������ ����������� " + maxClass + " ������");
    }

    private double formula(Double[] currentLine, Integer classNumber) {
        double result = 0; //�������
        for (int i = 0; i < koefMap.get(classNumber).size(); i++) { //���� �� ���� �������������
            double resultForLine = 0;
            for (int j = 0; j < currentLine.length; j++) {
                resultForLine += Math.pow(currentLine[j] - koefMap.get(classNumber).get(i)[j], 2);
            }
            result = result + Math.pow(Math.E, resultForLine * -1);
        }
        return result;
    }
}
