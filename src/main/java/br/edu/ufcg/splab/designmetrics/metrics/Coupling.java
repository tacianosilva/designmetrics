package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;

public class Coupling {

    private DesignWizard designwizard;

    public Coupling(DesignWizard designWizard) {
        // Design for all classes in the project.
        this.designwizard = designWizard;
    }

    public Integer efferentCoupling(ClassNode node) {

        Metric metric = classNode -> {
            if (classNode == null) {
                return 0;
            }
            return getDirectRelatedEntities(classNode).size();
        };

        return calculate(node, metric);

    }

    public Integer calculate(ClassNode node, Metric metric) {
        return metric.calculate(node);
    }

    /**
     * Return the direct related entities to the class.
     *
     * @param setClasses
     * @param name
     * @return
     */
    public Set<ClassNode> getDirectRelatedEntities(ClassNode classNode) {
        Set<ClassNode> directRelatedEntities = new HashSet<ClassNode>();
        Set<ClassNode> callees = classNode.getCalleeClasses();
        for (ClassNode callee : callees) {
            if (!callee.equals(classNode) && !callee.getClassName().equals("java.lang.Object")
                    && designwizard.getAllClasses().contains(callee)) {
                directRelatedEntities.add(callee);
            }
        }
        directRelatedEntities.addAll(getFieldDeclaredEntities(classNode));
        return directRelatedEntities;
    }

    private Set<ClassNode> getFieldDeclaredEntities(ClassNode classNode) {
        Set<ClassNode> feedBack = new HashSet<ClassNode>();
        Set<FieldNode> fieldsDeclared = classNode.getAllFields();

        for (FieldNode field : fieldsDeclared) {
            ClassNode type = field.getType();
            if (!type.equals(classNode) && designwizard.getAllClasses().contains(type)) {
                feedBack.add(type);
            }
        }

        return feedBack;
    }


}
