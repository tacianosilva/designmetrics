package br.edu.ufcg.splab.designmetrics.metrics;

import org.designwizard.design.ClassNode;

/**
 * Calculates the metric value for the {@link ClassNode}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@FunctionalInterface
public interface Metric {

    /**
     * Calculates the metric value for the ClassNode.
     * @param classNode A class contained in the design.
     * @return The value for this metric.
     */
    public Integer calculate(ClassNode classNode);

}
