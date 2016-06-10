package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassSix {

    private Integer id;

    private String name;

    private ClassSeven seven;

    public ClassSix() {
        this.id = 6;
        this.name = "Six";
        this.seven = new ClassSeven();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSum() {
        Integer a = this.seven.getId();
        Integer b = this.getId();
        return a + b;
    }
}
