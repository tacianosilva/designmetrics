package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;

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
        return getAllClasses().contains(classNode);
    }

    protected Set<ClassNode> getAllClasses() {
        if (this.designwizard == null) {
            return new HashSet<>();
        }
        return designwizard.getAllClasses();
    }

    private boolean isNewRelatedClass(ClassNode classNode, ClassNode newClass) {
        if (newClass != null && !newClass.equals(classNode) && !OBJECT_CLASS_NAME.equals(newClass.getClassName())
                && isInTheDesign(newClass)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the dependencies set from related Entities for two ClassNodes.
     * @param classNodeA A classNode of the design.
     * @param classNodeB A classNode of the design.
     * @return A set with the intersection of the related entities for two ClassNodes or empty set.
     */
    public Set<ClassNode> dependenciesBetweenEntities(ClassNode classNodeA, ClassNode classNodeB) {
        Set<ClassNode> dependencies = new HashSet<>();

        Set<ClassNode> relatedA = getRelatedEntities(classNodeA);
        Set<ClassNode> relatedB = getRelatedEntities(classNodeB);

        if (relatedA.contains(classNodeB)) {
            dependencies.add(classNodeB);
        }
        if (relatedB.contains(classNodeA)) {
            dependencies.add(classNodeA);
        }

        dependencies.addAll(intersection(relatedA, relatedB));

        return dependencies;
    }

    /**
     * Returns the dependencies set from related methods for two ClassNodes.
     * @param classNodeA A classNode of the design.
     * @param classNodeB A classNode of the design.
     * @return A set with the related methods for two ClassNodes or empty set.
     */
    public Set<MethodNode> methodDependenciesBetweenEntities(ClassNode classNodeA, ClassNode classNodeB) {
        Set<MethodNode> dependencies = new HashSet<>();

        Set<MethodNode> calleesA = classNodeA.getCalleeMethods();
        Set<MethodNode> calleesB = classNodeB.getCalleeMethods();

        for (MethodNode methodNode : calleesA) {
            if (classNodeB.equals(methodNode.getDeclaringClass())) {
                System.out.println(classNodeA.getShortName() + " call " + classNodeB.getShortName() + " Method: " + methodNode.getName());
                dependencies.add(methodNode);
            }
        }

        for (MethodNode methodNode : calleesB) {
            if (classNodeA.equals(methodNode.getDeclaringClass())) {
                System.out.println(classNodeB.getShortName() + " call " + classNodeA.getShortName() + " Method: " + methodNode.getName());
                dependencies.add(methodNode);
            }
        }

        // Fields Declared
        Set<ClassNode> fieldsA = getFieldDeclaredEntities(classNodeA);
        Set<ClassNode> fieldsB = getFieldDeclaredEntities(classNodeB);

        if (fieldsA.contains(classNodeB)) {
            if (classNodeB.getConstructors().contains(new MethodNode(classNodeB.getName() + ".<init>()", true))) {
                System.out.println(classNodeA.getShortName() + " declare " + classNodeB.getShortName());
                try {
                    dependencies.add(designwizard.getMethod(classNodeB.getName() + ".<init>()"));
                } catch (InexistentEntityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (fieldsB.contains(classNodeA)) {
            if (classNodeA.getConstructors().contains(new MethodNode(classNodeA.getName() + ".<init>()", true))) {
                System.out.println(classNodeB.getShortName() + " declare " + classNodeA.getShortName());
                try {
                    dependencies.add(designwizard.getMethod(classNodeA.getName() + ".<init>()"));
                } catch (InexistentEntityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return dependencies;
    }

    // método genérico que permite obter a interseção de dois conjuntos
    private <T> Set<T> intersection(Set<T> conjA, Set<T> conjB) {
        Set<T> conjC = new HashSet<>();
        // percorremos todos os elementos do conjunto A
        for (T elemento : conjA) {
            // e verificamos se o elemento está contido no conjunto B
            if (conjB.contains(elemento)) {
                conjC.add(elemento); // se estiver contido nós o adicionamos no
                                        // conjunto C
            }
        }
        return conjC; // e retornamos o conjunto C
    }
}
