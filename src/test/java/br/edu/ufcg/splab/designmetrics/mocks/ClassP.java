package br.edu.ufcg.splab.designmetrics.mocks;

import java.util.HashSet;
import java.util.Set;

public class ClassP {

    public Set<ClassA> testA() {
        Set<ClassA> setA = new HashSet<ClassA>();
        return setA;
    }

    public void testB() {
        ClassB b = null;
        Set<String> strs = new HashSet<>();
        for (String string : strs) {
            b = (ClassB) getObj(string);
        }
        System.out.println(b);
    }

    public Object getObj(String str) {
        return new Object();
    }

}
