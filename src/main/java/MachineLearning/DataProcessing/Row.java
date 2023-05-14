package MachineLearning.DataProcessing;

import java.util.List;

public class Row {

    //ATRIBUTOS
    private  List<Double> data;


    //CONSTRUCTORRES
    public Row(List<Double> data ) {
        this.data = data;
    }
    public Row(){}

    //METODOS
    public List<Double> getData() {
        return data;
    }

    public void setdata(List<Double> data) {
        this.data = data;
    }

}
