package br.edu.ufcg.splab.designmetrics.mocks.cbo2;

public class ClassOne implements InterfaceOne {

    private Integer id;

    private String name;

    public ClassOne() {
        this.id = 1;
        this.name = "One";
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
}
