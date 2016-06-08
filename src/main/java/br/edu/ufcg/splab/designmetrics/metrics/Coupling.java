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
}
