package br.edu.ufcg.splab.designmetrics.mocks.cbo4;

import br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassSeven;
import br.edu.ufcg.splab.designmetrics.mocks.cbo3.ClassT;

public class ClassV {

    private String name;
    private ClassT t;

    public ClassV() {
        this.name = "ClassV";
        this.t = new ClassT();
    }

    public ClassSeven getSeven() {
        return this.t.getSeven();
    }

    public String getName() {
        return this.name;
    }
}
