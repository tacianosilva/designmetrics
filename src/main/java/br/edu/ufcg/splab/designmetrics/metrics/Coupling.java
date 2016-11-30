package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.PackageNode;

public class Coupling {

    private EfferentCouplingMetric ceMetric;
    private AfferentCouplingMetric caMetric;
    
    private DesignWizard designwizard;
    
    private Set<ClassNode> topClassesCe = null;

    public Coupling(DesignWizard designwizard) {
        // Design for all classes in the project.
        this.designwizard = designwizard;
        this.ceMetric = new EfferentCouplingMetric(designwizard);
        this.caMetric = new AfferentCouplingMetric(designwizard);
    }

    public Integer efferentCoupling(ClassNode node) {
        return ceMetric.calculate(node);
    }
    
    public Integer efferentCouplingMethodLevel(ClassNode node) {
        return ceMetric.calculateMethodLevel(node);
    }
    
    public Integer efferentCoupling(PackageNode node) {
        return ceMetric.calculate(node);
    }
    
    public Integer efferentCouplingMethodLevel(PackageNode node) {
        return ceMetric.calculateMethodLevel(node);
    }

    public Integer afferentCoupling(ClassNode node) {
        return caMetric.calculate(node);
    }
    
    public Integer afferentCouplingMethodLevel(ClassNode node) {
        return caMetric.calculateMethodLevel(node);
    }
    
    public Integer afferentCoupling(PackageNode node) {
        return caMetric.calculate(node);
    }
    
    public Integer afferentCouplingMethodLevel(PackageNode node) {
        return caMetric.calculateMethodLevel(node);
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

    /**
     * Calculate the Efferent Coupling (Ce) between two classes.
     * Check if the classes are mutual references: Unidirectional sum 1 and bidirectional sum 2.
     * @param nodeA A ClassNode to the class in design.
     * @param nodeB A ClassNode to the class in design.
     * @return The value of the efferent coupling.
     */
    public Integer efferentCoupling(ClassNode nodeA, ClassNode nodeB) {
        return ceMetric.calculate(nodeA, nodeB);
    }

    /**
     * Calculate the Efferent Coupling (Ce) between two classes with method level.
     * Check if the classes do mutual references:
     * For each method call add 1;
     * For each attribute declaration add 1 (called to the constructor).
     * @param nodeA A ClassNode to the class in design.
     * @param nodeB A ClassNode to the class in design.
     * @return The value of the efferent coupling.
     */
    public Integer efferentCouplingMethodLevel(ClassNode nodeA, ClassNode nodeB) {
        return ceMetric.calculateMethodLevel(nodeA, nodeB);
    }

    /**
     * Calculate the Efferent Coupling (Ce) between two packages.
     * @param nodeA A ClassNode to the class in design.
     * @param nodeB A ClassNode to the class in design.
     * @return The value of the efferent coupling between two packages.
     */
    public Integer efferentCoupling(PackageNode nodeA, PackageNode nodeB) {
        return ceMetric.calculate(nodeA, nodeB);
    }

    /**
     * Calculate the Efferent Coupling (Ce) between two packages with method level.
     * Check if the classes do mutual references:
     * For each method call add 1;
     * For each attribute declaration add 1 (called to the constructor).
     * @param nodeA A PackageNode to the class in design.
     * @param nodeB A PackageNode to the class in design.
     * @return The value of the efferent coupling between two packages.
     */
    public Integer efferentCouplingMethodLevel(PackageNode nodeA, PackageNode nodeB) {
        return ceMetric.calculateMethodLevel(nodeA, nodeB);
    }
    
    /**
     * Calculate the classes with efferent coupling greater than the minimum value required.
     * @param coupling Minimum value required.
     */
    public void calculateTopClassesCe(Integer coupling) {
        Set<ClassNode> classes = designwizard.getAllClasses();
        this.topClassesCe = new HashSet<>();
        
        for (ClassNode classNode : classes) {
            Integer ce = ceMetric.calculate(classNode);
            if (ce >= coupling) {
                this.topClassesCe.add(classNode);
            }
        }
    }
    
    /**
     * Returns the classes with efferent coupling greater than the desired value.
     * @param coupling Minimum value required.
     * @return The classes with efferent coupling greater than the desired value.
     */
    public Set<ClassNode> getTopClassesCe(Integer coupling) {
        if (topClassesCe == null) {
            calculateTopClassesCe(coupling);
        }
        return this.topClassesCe;
    }
}