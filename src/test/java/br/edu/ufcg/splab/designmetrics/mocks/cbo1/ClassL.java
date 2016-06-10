package br.edu.ufcg.splab.designmetrics.mocks.cbo1;

public class ClassL {

    private ClassF f;

    public String getStrD() {
        ClassD d = new ClassD();
        f.setD(d);
        return "";
    }

    public String getStrE() {
        f.setE1(new ClassE());
        return "";
    }

    public Object getD() {
        return new ClassD();
    }
}
