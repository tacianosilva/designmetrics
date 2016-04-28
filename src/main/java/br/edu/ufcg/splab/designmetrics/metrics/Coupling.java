package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;

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
            return getRelatedEntities(classNode).size();
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
    public Set<ClassNode> getRelatedEntities(ClassNode classNode) {
        Set<ClassNode> directRelatedEntities = new HashSet<ClassNode>();

        // Classes directly calls
        directRelatedEntities.addAll(getDirectRelatedEntities(classNode));
        // Classes in the methods type return
        directRelatedEntities.addAll(getMethodReturnTypesEntities(classNode));
        // Fields Declared
        directRelatedEntities.addAll(getFieldDeclaredEntities(classNode));

        return directRelatedEntities;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getDirectRelatedEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<ClassNode>();
        Set<ClassNode> callees = classNode.getCalleeClasses();
        for (ClassNode callee : callees) {
            if (!callee.equals(classNode) && !callee.getClassName().equals("java.lang.Object")
                    && designwizard.getAllClasses().contains(callee)) {
                feedback.add(callee);
            }
        }
        return feedback;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getFieldDeclaredEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<ClassNode>();
        Set<FieldNode> fieldsDeclared = classNode.getAllFields();

        for (FieldNode field : fieldsDeclared) {
            ClassNode type = field.getType();
            if (!type.equals(classNode) && designwizard.getAllClasses().contains(type)) {
                feedback.add(type);
            }
        }

        return feedback;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getMethodReturnTypesEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<ClassNode>();
        Set<MethodNode> calleeMethods = classNode.getCalleeMethods();
        for (MethodNode method : calleeMethods) {
            ClassNode type = method.getReturnType();
            if (type != null && !type.equals(classNode) && !type.getClassName().equals("java.lang.Object")
                    && designwizard.getAllClasses().contains(type)) {
                feedback.add(type);
            }
        }
        return feedback;
    }
}
