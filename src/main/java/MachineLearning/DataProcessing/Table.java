package MachineLearning.DataProcessing;

import java.util.ArrayList;
import java.util.List;

public class Table {

    //ATRIBUTES
    private List<String> headers;
    private List<Row> rows;

    //CONSTRUCTORS
    public Table(List<String> header){
        this.headers = header;
        this.rows = new ArrayList<>();
    }
    public Table(){}

    // header getter/setter
    public List<String> getHeader(){ return headers; }
    public void setHeaders(List<String> headers){ this.headers = headers; }

    //rows getter/setter
    public List<Row> getRows(){ return rows; }

    public void setRows(List<Row> rows) { this.rows = rows; }

    public Row getRowAt(int num){ return this.rows.get(num);}

    //methods
    public void addRow(Row row){ rows.add(row); }

    //methods to be used in testing

    public int countRows(){ return rows.size(); //rows + header
    }
    public int countHeaders(){return headers.size();}
}