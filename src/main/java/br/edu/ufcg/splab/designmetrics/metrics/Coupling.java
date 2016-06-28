package br.edu.ufcg.splab.designmetrics.metrics;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

public class Coupling {

    private EfferentCouplingMetric ceMetric;
    private AfferentCouplingMetric caMetric;

    public Coupling(DesignWizard designwizard) {
        // Design for all classes in the project.
        this.ceMetric = new EfferentCouplingMetric(designwizard);
        this.caMetric = new AfferentCouplingMetric(designwizard);
    }

    public Integer efferentCoupling(ClassNode node) {
        return ceMetric.calculate(node);
    }

    public Integer afferentCoupling(ClassNode node) {
        return caMetric.calculate(node);
    }

    public Integer efferentCoupling() {
        return ceMetric.calculate();
    }

    public Double instability(ClassNode node) {
        Double instabilityMetric = 0.0;
        Double efferent = (double) efferentCoupling(node);
        Double afferent = (double) afferentCoupling(node);
        if (efferentCoupling(node) != 0) {
            instabilityMetric = efferent / (efferent + afferent);
        }
        return instabilityMetric;
    }

    public Integer efferentCoupling(ClassNode nodeA, ClassNode nodeB) {
        return ceMetric.calculate(nodeA, nodeB);
    }

    public Integer efferentCouplingMethodLevel(ClassNode nodeA, ClassNode nodeB) {
        return ceMetric.calculateMethodLevel(nodeA, nodeB);
    }
}
