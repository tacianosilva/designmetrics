package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

/**
 * Class to calculate Afferent Coupling (Ce) is the number of methods declared in one class use methods or
 * instance variables defined by the other class. The uses relationship can go either way:
 * both uses and used-by relationships are taken into account, but only once. {@link DesignWizard}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class EfferentCouplingMetric extends AbstractMetric {

    public EfferentCouplingMetric(DesignWizard designwizard) {
        super(designwizard);
    }

    /**
     * Efferent Coupling (Ce) is the number of methods declared in one class use methods or
     * instance variables defined by the other class. The uses relationship can go either way:
     * both uses and used-by relationships are taken into account, but only once.
     *
     * @see <a href="http://www.aivosto.com/project/help/pm-oo-ck.html">Chidamber Kemerer object-oriented metrics suite</a>
     *
     * @param classNode
     *            The classNode that is desired efferent coupling.
     * @return The value of efferent coupling. If
     *         {@code Entity} is <code>null</code> returns <code>0</code>.
     */
    @Override
    public Integer calculate(ClassNode classNode) {
        if (classNode == null) {
            return 0;
        }
        return getRelatedEntities(classNode).size();
    }

    public Integer calculate(ClassNode classNodeA, ClassNode classNodeB) {
        if (classNodeA == null || classNodeB == null || classNodeA.equals(classNodeB)) {
            return 0;
        }
        return dependenciesBetweenEntities(classNodeA, classNodeB).size();
    }

    public Integer calculate() {
        int total = 0;
        Set<ClassNode> classes = getAllClasses();
        for (ClassNode classNode : classes) {
            total += calculate(classNode);
        }
        return total;
    }
}
