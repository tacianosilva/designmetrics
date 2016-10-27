package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.PackageNode;

/**
 * Class to calculate Afferent Coupling (Ce) is the number of methods declared in one class use methods or
 * instance variables defined by the other class. The uses relationship can go either way:
 * both uses and used-by relationships are taken into account, but only once. {@link DesignWizard}.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
public class EfferentCouplingMetric extends AbstractMetric {

    /**
     * Construct the metric for the design parameter.
     * @param designwizard An instance with the design.
     */
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
    
    /**
     * Efferent Coupling (Ce) is the number of methods declared in one class use methods or
     * instance variables defined by the other class. The uses relationship can go either way:
     * both uses and used-by relationships are taken into account, but only once.
     *
     * @param classNode
     *            The classNode that is desired efferent coupling.
     * @return The value of efferent coupling. If
     *         {@code Entity} is <code>null</code> returns <code>0</code>.
     */
    public Integer calculateMethodLevel(ClassNode classNode) {
        if (classNode == null) {
            return 0;
        }
        return getRelatedMethods(classNode).size();
    }

    /**
     *
     * @param classNodeA
     * @param classNodeB
     * @return
     */
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

    /**
     * Calculate the Efferent Coupling (Ce) between two classes with method level.
     * @param classNodeA A ClassNode to the class in design.
     * @param classNodeB A ClassNode to the class in design.
     * @return The value of the efferent coupling between two classes.
     */
    public Integer calculateMethodLevel(ClassNode classNodeA, ClassNode classNodeB) {
        if (classNodeA == null || classNodeB == null || classNodeA.equals(classNodeB)) {
            return 0;
        }
        return methodDependenciesBetweenEntities(classNodeA, classNodeB).size();
    }

    /**
     * Calculate the Efferent Coupling (Ce) between two packages.
     * @param packageA A PackageNode to the class in design.
     * @param packageB A PackageNode to the class in design.
     * @return The value of the efferent coupling between two packages.
     */
    public Integer calculate(PackageNode packageA, PackageNode packageB) {
        if (packageA == null || packageB == null || packageA.equals(packageB)) {
            return 0;
        }
        Set<ClassNode> classesA = packageA.getAllClasses();
        Set<ClassNode> classesB = packageB.getAllClasses();
        
        Set<ClassNode> dependencies = new HashSet<>();

        for (ClassNode classA : classesA) {
            for (ClassNode classB : classesB) {
            	Set<ClassNode> depAB = dependenciesBetweenEntities(classA, classB);
                dependencies.addAll(depAB);
                for (ClassNode classNode : depAB) {
                    System.out.println("Dep("+classA.getName()+ ", "+classB.getName()+") = " + classNode.getName());
                }
            }
        }

        return dependencies.size();
    }

    /**
     * Calculate the Efferent Coupling (Ce) between two packages with method level.
     * @param packageA A PackageNode to the class in design.
     * @param packageB A PackageNode to the class in design.
     * @return The value of the efferent coupling between two packages.
     */
    public Integer calculateMethodLevel(PackageNode packageA, PackageNode packageB) {
        if (packageA == null || packageB == null || packageA.equals(packageB)) {
            return 0;
        }
        return 0;
    }
}
