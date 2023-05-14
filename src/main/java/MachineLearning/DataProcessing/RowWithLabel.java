package MachineLearning.DataProcessing;

import java.util.List;

public class RowWithLabel extends Row {

    //ATRIBUTOS
    private int numberClass;

    //CONSTRUCTORES
    public RowWithLabel(int value, List<Double> data) {
        super(data);
        this.numberClass = value;
    }
    public RowWithLabel() {}

    //METODOS
    public int getNumberClass() {
        return numberClass;
    }

    @Override
    public List<Double> getData() {
        return super.getData();
    }



}
