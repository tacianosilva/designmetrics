package br.edu.ufcg.splab.designmetrics.mocks;

import java.util.HashSet;
import java.util.Set;

public class ClassF {

    private ClassD d;

    private ClassE e1;

    private ClassE e2;

    private String str;

    private Integer val;

    private Set<ClassD> conjunto;

    public ClassF() {
        this.conjunto = new HashSet<>();
    }

    public ClassD getD() {
        return d;
    }

    public void setD(ClassD d) {
        this.d = d;
    }

    public ClassE getE1() {
        return e1;
    }

    public void setE1(ClassE e1) {
        this.e1 = e1;
    }

    public ClassE getE2() {
        return e2;
    }

    public void setE2(ClassE e2) {
        this.e2 = e2;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public Set<ClassD> getConjunto() {
        return conjunto;
    }

    public void setConjunto(Set<ClassD> conjunto) {
        this.conjunto = conjunto;
    }
}