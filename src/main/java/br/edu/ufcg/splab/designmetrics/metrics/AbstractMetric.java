package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;

public abstract class AbstractMetric implements Metric {

    private static final String OBJECT_CLASS_NAME = "java.lang.Object";

    private DesignWizard designwizard;

    /**
     * Construct the metric for the design parameter.
     * @param designwizard An instance with the design.
     */
    public AbstractMetric(DesignWizard designwizard) {
        this.designwizard = designwizard;
    }

    /**
     * Return the direct related entities to the class.
     *
     * @param classNode The class that you want the directly related entities.
     * @return Returns a set with the entities classeNodes direct related.
     */
    public Set<ClassNode> getRelatedEntities(ClassNode classNode) {
        Set<ClassNode> directRelatedEntities = new HashSet<>();

        // Classes directly calls
        directRelatedEntities.addAll(getDirectRelatedEntities(classNode));
        // Classes in the methods type return or method parameters
        directRelatedEntities.addAll(getMethodRelatedEntities(classNode));
        // Fields Declared
        directRelatedEntities.addAll(getFieldDeclaredEntities(classNode));

        return directRelatedEntities;
    }

    /**
     * Return the direct dependents entities to the class.
     *
     * @param classNode The class that you want the directly dependents entities.
     * @return Returns a set with the entities classeNodes direct dependents.
     */
    public Set<ClassNode> getDependentsEntities(ClassNode classNode) {
        Set<ClassNode> directDependentsEntities = new HashSet<>();

        // Classes directly callers
        directDependentsEntities.addAll(getDirectDependentsEntities(classNode));
        // Classes in the methods type return or method parameters
        directDependentsEntities.addAll(getMethodDependentsEntities(classNode));

        return directDependentsEntities;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getDirectRelatedEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<>();
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
    private Set<ClassNode> getDirectDependentsEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<>();
        Set<ClassNode> callers = classNode.getCallerClasses();

        for (ClassNode caller : callers) {
            if (isNewRelatedClass(classNode, caller)) {
                feedback.add(caller);
            }
        }
        return feedback;
    }

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getFieldDeclaredEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<>();
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
        Set<ClassNode> feedback = new HashSet<>();

        Set<MethodNode> methods = new HashSet<>();
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

    /**
     * @param classNode
     * @return
     */
    private Set<ClassNode> getMethodDependentsEntities(ClassNode classNode) {
        Set<ClassNode> feedback = new HashSet<>();

        Set<ClassNode> callerClasses = classNode.getCallerClasses();
        for (ClassNode callerClass : callerClasses) {
            Set<MethodNode> methods = callerClass.getAllMethods();

            for (MethodNode method : methods) {
                feedback.addAll(getDependentsDeclaringClasses(classNode, method));
            }
        }
        return feedback;
    }

    private Set<ClassNode> getDependentsDeclaringClasses(ClassNode classNode, MethodNode method) {
        Set<ClassNode> feedback = new HashSet<>();
        ClassNode type = method.getReturnType();
        Set<ClassNode> parameters = getParameters(method);

        if (type.equals(classNode) || parameters.contains(classNode)) {
            Set<MethodNode> callerMethods = method.getCallerMethods();

            for (MethodNode callerMethod : callerMethods) {
                ClassNode declaringClass = callerMethod.getDeclaringClass();

                if (isNewRelatedClass(classNode, declaringClass)) {
                    feedback.add(declaringClass);
                }
            }
        }
        return feedback;
    }

    private Set<ClassNode> getParameters(MethodNode method) {
        Set<ClassNode> parameters = method.getParameters();

        if (parameters == null) {
            return new HashSet<>();
        }

        return parameters;
    }

    private boolean isInTheDesign(ClassNode classNode) {
        if (this.designwizard == null) {
            return false;
        }
        return designwizard.getAllClasses().contains(classNode);
    }

    private boolean isNewRelatedClass(ClassNode classNode, ClassNode newClass) {
        if (newClass != null && !newClass.equals(classNode) && !OBJECT_CLASS_NAME.equals(newClass.getClassName())
                && isInTheDesign(newClass)) {
            return true;
        }
        return false;
    }
}
