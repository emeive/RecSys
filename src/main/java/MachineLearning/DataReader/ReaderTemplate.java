package MachineLearning.DataReader;


import MachineLearning.DataProcessing.Table;

import java.util.Scanner;

public abstract class ReaderTemplate {

    String source;
    Scanner entry;
    Table table;

    public ReaderTemplate(String source) {
        this.source = source;
        this.table = new Table();
    }

    public void setEntry(Scanner entry) {
        this.entry = entry;
    }

    abstract void openSource(String source);

    abstract void processHeaders(String headers);

    abstract void processData(String data);

    abstract void closeSource();

    abstract boolean hasMoreData();

    abstract String getNextData();


    public final Table readTableFromSource() {
        // Open the data source.
        openSource(source);

        // Read the header.
        processHeaders(getNextData());
        // Read all data points
        while (hasMoreData()) {
            processData(getNextData());
        }
        //Close the data source.
        closeSource();

        return this.table;
    }


}


