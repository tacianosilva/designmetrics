package br.edu.ufcg.splab.designmetrics.mocks.cbo1;

public class ClassM {

    private ClassF f;
    private ClassL l;

    public String getStrD() {
        f.setD((ClassD)l.getD());
        return "D";
    }

    public String getStrE() {
        @SuppressWarnings("unused")
		ClassE e;
        return "e";
    }
}
