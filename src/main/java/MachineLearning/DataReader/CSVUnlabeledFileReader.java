package MachineLearning.DataReader;


import MachineLearning.DataProcessing.Row;
import MachineLearning.DataProcessing.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVUnlabeledFileReader extends ReaderTemplate {


    public CSVUnlabeledFileReader(String source) {
        super(source);
    }

    @Override
    void openSource(String source)  {
        Scanner entry;
        try {
            entry = new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        setEntry(entry);

    }

    @Override
    void processHeaders(String headers) {
        String[] headersArray = headers.split(",");
        this.table = new Table(Arrays.asList(headersArray));
    }


    @Override
    void processData(String data) {

        String[] rowData = data.split(",");
        List<Double> dataContent = new ArrayList<>();

        for (String dim : rowData) {
            dataContent.add(Double.parseDouble(dim));
        }
        table.addRow(new Row(dataContent));
    }

    @Override
    void closeSource() {
        entry.close();
    }

    @Override
    boolean hasMoreData() {
        return entry.hasNext();
    }

    @Override
    String getNextData() {

        return entry.nextLine() ;
    }
}
