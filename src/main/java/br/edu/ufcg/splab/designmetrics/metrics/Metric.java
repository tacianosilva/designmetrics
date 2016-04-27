package br.edu.ufcg.splab.designmetrics.metrics;

import org.designwizard.design.ClassNode;

@FunctionalInterface
public interface Metric {

    public Integer calculate(ClassNode classNode);

}
