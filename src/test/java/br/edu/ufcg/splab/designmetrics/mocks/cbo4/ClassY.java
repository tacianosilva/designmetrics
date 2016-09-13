package br.edu.ufcg.splab.designmetrics.mocks.cbo4;

import br.edu.ufcg.splab.designmetrics.mocks.cbo3.ClassX;

public class ClassY {

    private String name;
    private ClassX x;

    public ClassY() {
        this.name = "ClassY";
        this.x = new ClassX();
    }

    public String getXY() {
        return this.name + "-" + this.x.getName();
    }

    public Integer getX() {
        return this.x.getValue();
    }
}
