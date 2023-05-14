package MachineLearning.DataProcessing;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TableWithLabels extends Table {

    //ATRIBUTOS
    private Map<String, Integer> labelsToIndex;

    //CONSTRUCTORES
    public TableWithLabels(List<String> cabecera) {
        super(cabecera);
        this.labelsToIndex = new HashMap<>();
    }
    public TableWithLabels() {}


    //METODOS
    public RowWithLabel getRowAt(int num) {
        return (RowWithLabel) super.getRowAt(num);
    }

    public void setLabelsToIndex(Map<String, Integer> labelsToIndex) {
        this.labelsToIndex = labelsToIndex;
    }

    public Map<String, Integer> getLabelsToIndex() {
        return labelsToIndex;
    }


    // TO STRING (PARA LOS TEST)
    public String rowToString(RowWithLabel row) {
        StringBuilder rowContent = new StringBuilder();
        for (Double data : row.getData()) {

            //Al pasar el double a string nos cambiaba los puntos por comas, hemos encontrado esta solución.
            rowContent.append(String.format(Locale.ROOT, "%.2f,", data));
        }
        for (String key : labelsToIndex.keySet()) {
            if (row.getNumberClass() == labelsToIndex.get(key)) {
                rowContent.append(key);
                break;
            }
        }
        return rowContent.toString();
    }
}
