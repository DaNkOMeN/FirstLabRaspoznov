import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stohastic extends Process {
    Map<Integer, List<Double[]>> matrixesOfFile = new HashMap<>();
    Map<Integer, List<Double[]>> koefMap = new HashMap<>();
    List<List<Integer>> stohList = new ArrayList<>();
    int epoch = 1;

    Stohastic(File file) {
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
        for (int i = 0; i < matrixesOfFile.entrySet().size(); i++) {
            stohList.add(new ArrayList<>());
        }

        for (Map.Entry classMatrix : matrixesOfFile.entrySet()) { //���� �� ���� �������� ��� ������� �� �������
            List<Double[]> matrixForFormula = (List<Double[]>) classMatrix.getValue();
            //������� ������ ���������� �������
            int i = 0;
            //������ ���������� ��������
            int streak = 0;
            //����� ��������
            int iter = 0;


            //���� �� �������
            while (streak != matrixForFormula.size()) {
                if (koefMap.containsKey((Integer) classMatrix.getKey())) {    //���� ���� ������� ������������� ��� ����� ������
                    double result = formula(matrixForFormula.get(i), (Integer) classMatrix.getKey());
                    if (result < 0) {
                        List<Double[]> koefListForClass = koefMap.get((Integer) classMatrix.getKey());
                        koefListForClass.add(matrixForFormula.get(i));
                        koefMap.put((Integer) classMatrix.getKey(), koefListForClass);
                        streak = 0;
                        if (stohList.get((Integer) classMatrix.getKey()).isEmpty()) {
                            List<Integer> stohForVector = new ArrayList<>();
                            stohForVector.add(1);
                            stohList.set((Integer) classMatrix.getKey(), stohForVector);
                        } else {
                            int max = stohList.get((Integer) classMatrix.getKey()).get(stohList.get((Integer) classMatrix.getKey()).size() - 1);
                            int newMax = max + 1;
                            List<Integer> stohForVector = stohList.get((Integer) classMatrix.getKey());
                            stohForVector.add(newMax);
                            stohList.set((Integer) classMatrix.getKey(), stohForVector);
                        }
                    } else {
                        streak++;
                    }
                    //������������ � ������ �����, ����� �� ���� �����������
                    if (i == matrixForFormula.size() - 1) {
                        i = 0;
                    } else {
                        i++;
                    }
                } else {                                                    //���� ����, ����� ������� ��� ��� ������ ������������ � ������ ����
                    List<Double[]> koefList = new ArrayList<>();
                    koefList.add(matrixForFormula.get(i));  // ������� �����������
                    koefMap.put((Integer) classMatrix.getKey(), koefList);   //������
                    i++;
                }
                iter++;
            }
            System.out.println("�������� ��������� ��� ������ " + String.valueOf((Integer) classMatrix.getKey()) + " .���������� �������� " + iter);

        }
        System.out.println("������ �������� ���������");
    }

    public void work(Double[] test) {
        Double max = 0.0;
        int maxClass = 0;
        for (Map.Entry koef : koefMap.entrySet()) {
            double result = formula(test, (Integer) koef.getKey());
            if (result > max) {
                maxClass = (Integer) koef.getKey();
                max = result;
            }
        }
        if (maxClass == 0) {
            System.out.println("���������� ��������� ������������ ���������");
        } else {
            System.out.println("������� ������ ����������� " + maxClass + " ������");
        }
    }

    private double formula(Double[] currentLine, Integer classNumber) {
        double result = 0; //�������
        List<Integer> stohForVector;

        int classNumberForKoefVector = classNumber - 1;
        if (stohList.get(classNumberForKoefVector).isEmpty()) {
            stohForVector = stohList.get(classNumberForKoefVector);
            stohForVector.add(1);
        } else {
            int max = stohList.get(classNumberForKoefVector).get(stohList.get(classNumberForKoefVector).size() - 1);
            int newMax = max + 1;
            stohForVector = stohList.get(classNumberForKoefVector);
            stohForVector.add(newMax);
        }

        for (int i = 0; i < koefMap.get(classNumber).size(); i++) { //���� �� ���� �������������
            double resultForLine = 0;
            for (int j = 0; j < currentLine.length; j++) {
                resultForLine += Math.pow(currentLine[j] - koefMap.get(classNumber).get(i)[j], 2);
            }
            result = result + Math.pow(Math.E, resultForLine * -1) / stohForVector.get(i);
        }
        return result;
    }
}
