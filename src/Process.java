public abstract class Process {

    protected static Double[] convertStringToIntMas(String line) {
        String[] stringMas = (line.substring(line.indexOf("\t") + 1)).split("\t");
        Double[] resultMas = new Double[stringMas.length];
        for (int i = 0; i < stringMas.length; i++) {
            resultMas[i] = Double.parseDouble(stringMas[i].replace(",", "."));
        }
        return resultMas;
    }

    protected static Integer getClassByString(String line) {
        String clazz = line.substring(0, 1);
        return Integer.parseInt(clazz);
    }
}
