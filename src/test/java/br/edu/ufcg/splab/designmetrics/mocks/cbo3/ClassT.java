package br.edu.ufcg.splab.designmetrics.mocks.cbo3;

import br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassSeven;

public class ClassT {

    private ClassSeven seven;

    public ClassT() {
        this.seven = new ClassSeven();
    }

    public ClassSeven getSeven() {
        return seven;
    }

    public void setSeven(ClassSeven seven) {
        this.seven = seven;
    }
}
