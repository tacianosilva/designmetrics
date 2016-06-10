package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassTwo {
    private Integer id;

    private String name;

    private ClassOne one;

    public ClassTwo() {
        this.id = 2;
        this.name = "Two";
        this.one = new ClassOne();
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

    public ClassOne getOne() {
        return one;
    }

    public void setOne(ClassOne one) {
        this.one = one;
    }
}
