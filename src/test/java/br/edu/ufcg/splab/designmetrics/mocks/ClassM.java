package br.edu.ufcg.splab.designmetrics.mocks;

public class ClassM {

    private ClassF f;
    private ClassL l;

    public String getStrD() {
        f.setD((ClassD)l.getD());
        return "D";
    }

    public String getStrE() {
        ClassE e;
        return "e";
    }
}
