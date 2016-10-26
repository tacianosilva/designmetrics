package br.edu.ufcg.splab.designmetrics.metrics;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

/**
 * Class to calculate Afferent Coupling (Ca) is the number of methods declared in one class use methods or
 * instance variables defined by the other class. The uses relationship can go either way:
 * both uses and used-by relationships are taken into account, but only once. {@link DesignWizard}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class AfferentCouplingMetric extends AbstractMetric {

    /**
     * Construct the metric for the design parameter.
     * @param designwizard An instance with the design.
     */
    public AfferentCouplingMetric(DesignWizard designwizard) {
        super(designwizard);
    }

    /* (non-Javadoc)
     * @see br.edu.ufcg.splab.designmetrics.metrics.Metric#calculate(org.designwizard.design.ClassNode)
     */
    @Override
    public Integer calculate(ClassNode classNode) {
        if (classNode == null) {
            return 0;
        }
        return getDependentsEntities(classNode).size();
    }
    
    public Integer calculateMethodLevel(ClassNode classNode) {
        if (classNode == null) {
            return 0;
        }
        return getDependentsMethods(classNode).size();
    }
}
