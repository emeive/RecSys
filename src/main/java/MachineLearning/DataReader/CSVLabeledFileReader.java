package MachineLearning.DataReader;

import MachineLearning.DataProcessing.RowWithLabel;
import MachineLearning.DataProcessing.TableWithLabels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CSVLabeledFileReader extends CSVUnlabeledFileReader{

    public CSVLabeledFileReader(String source) {
        super(source);
    }

    @Override
    void processHeaders(String headers) {
        String[] headersArray = headers.split(",");
        this.table = new TableWithLabels(Arrays.asList(headersArray));
    }

    @Override
    void processData(String data) {

        TableWithLabels t = (TableWithLabels) this.table;
        Map<String, Integer> map = t.getLabelsToIndex();

        String[] rowData = data.split(",");
        List<Double> dataContent = new ArrayList<>();

        for (int i = 0; i < rowData.length - 1; i++) {
            dataContent.add(Double.parseDouble(rowData[i]));
        }

        String label = rowData[rowData.length - 1];

        if (!map.containsKey(label)) {
            int value = map.size();
            map.put(label, value);
        }
        table.addRow(new RowWithLabel(map.get(label), dataContent));

    }

}
