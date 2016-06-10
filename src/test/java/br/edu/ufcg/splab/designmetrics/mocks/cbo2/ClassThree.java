package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassThree {

    private Integer id;

    private String name;

    private ClassTwo two;

    public ClassThree() {
        this.id = 3;
        this.name = "Three";
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
}
