package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassSeven {

    private Integer id;

    private String name;

    private ClassSix six;

    public ClassSeven() {
        this.id = 7;
        this.name = "Seven";
        this.six = new ClassSix();
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
        Integer a = six.getId();
        Integer b = this.getId();
        return a + b;
    }
}
