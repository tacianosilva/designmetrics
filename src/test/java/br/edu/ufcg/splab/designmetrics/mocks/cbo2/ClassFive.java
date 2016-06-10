package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassFive {

    private Integer id;

    private String name;

    private ClassTwo two;

    public ClassFive() {
        this.id = 5;
        this.name = "Five";
        this.two = new ClassTwo();
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

    public ClassTwo getTwo() {
        return two;
    }

    public void setTwo(ClassTwo two) {
        this.two = two;
    }

    public Integer getSum() {
        Integer a = getTwo().getId();
        ClassThree three = new ClassThree();
        Integer b = three.getId();
        return a + b;
    }
}
