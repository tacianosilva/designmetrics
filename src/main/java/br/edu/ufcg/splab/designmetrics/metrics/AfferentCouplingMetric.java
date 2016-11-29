package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.PackageNode;

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
    
    public Integer calculate(PackageNode packageNode) {
        if (packageNode == null) {
            return 0;
        }
        int total = 0;
        Set<ClassNode> classes = packageNode.getAllClasses();
        for (ClassNode classNode : classes) {
            total += calculate(classNode);
        }
        return total;
    }
    
    public Integer calculateMethodLevel(PackageNode packageNode) {
        if (packageNode == null) {
            return 0;
        }
        int total = 0;
        Set<ClassNode> classes = packageNode.getAllClasses();
        for (ClassNode classNode : classes) {
            total += calculateMethodLevel(classNode);
        }
        return total;
    }
}
