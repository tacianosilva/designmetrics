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
        // Classes in the methods type return or method parameters
        directRelatedEntities.addAll(getMethodRelatedEntities(classNode));
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
            if (isNewRelatedClass(classNode, callee)) {
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
            if (isNewRelatedClass(classNode, type)) {
                feedback.add(type);
            }
        }

        return feedback;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getMethodRelatedEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<ClassNode>();

        Set<MethodNode> methods = new HashSet<MethodNode>();
        methods.addAll(classNode.getAllMethods());
        methods.addAll(classNode.getCalleeMethods());

        for (MethodNode method : methods) {
            ClassNode type = method.getReturnType();
            if (isNewRelatedClass(classNode, type)) {
                feedback.add(type);
            }

            Set<ClassNode> parameters = getParameters(method);

            for (ClassNode parameter : parameters) {
                if (isNewRelatedClass(classNode, parameter)) {
                    feedback.add(parameter);
                }
            }
        }
        return feedback;
    }

    private Set<ClassNode> getParameters(MethodNode method) {
        Set<ClassNode> parameters = method.getParameters();

        if (parameters == null) {
            return new HashSet<ClassNode>();
        }

        return parameters;
    }

    private boolean isInTheDesign(ClassNode classNode) {
        if (this.designwizard == null || designwizard.getAllClasses() == null) {
            return false;
        }
        return designwizard.getAllClasses().contains(classNode);
    }

    private boolean isNewRelatedClass(ClassNode classNode, ClassNode newClass) {
        if (newClass != null && !newClass.equals(classNode) && !newClass.getClassName().equals("java.lang.Object")
                && isInTheDesign(newClass)) {
            return true;
        }
        return false;
    }
}
