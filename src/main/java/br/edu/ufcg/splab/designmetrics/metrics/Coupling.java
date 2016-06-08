package br.edu.ufcg.splab.designmetrics.metrics;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

public class Coupling {

    private DesignWizard designwizard;

    public Coupling(DesignWizard designWizard) {
        // Design for all classes in the project.
        this.designwizard = designWizard;
    }

    public Integer efferentCoupling(ClassNode node) {
        Metric metric = new EfferentCouplingMetric(this.designwizard);
        return metric.calculate(node);
    }

    public Integer afferentCoupling(ClassNode node) {
        Metric metric = new AfferentCouplingMetric(this.designwizard);
        return metric.calculate(node);
    }
}
