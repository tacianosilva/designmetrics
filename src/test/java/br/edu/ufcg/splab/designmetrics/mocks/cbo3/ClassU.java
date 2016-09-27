package br.edu.ufcg.splab.designmetrics.mocks.cbo3;

import br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassSeven;

public class ClassU {

    private String name;
    private ClassT t;

    public ClassU() {
        this.name = "ClassU";
        this.t = new ClassT();
    }

    public ClassSeven getSeven() {
        return this.t.getSeven();
    }

    public String getName() {
        return this.name;
    }
}
