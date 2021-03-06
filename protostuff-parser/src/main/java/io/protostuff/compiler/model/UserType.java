package io.protostuff.compiler.model;

/**
 * User type is a base interface for all types that can be defined
 * by user - messages, enums and groups.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public interface UserType extends Descriptor, FieldType {

    /**
     * Short name. For example, {@code Baz}
     */
    @Override
    String getName();

    void setName(String name);

    Proto getProto();

    void setProto(Proto proto);

    /**
     * Returns fully qualified name for this user type. It always starts with dot.
     */
    @Override
    String getFullyQualifiedName();

    void setFullyQualifiedName(String fullyQualifiedName);

    @Override
    UserTypeContainer getParent();

    /**
     * Test if this type is nested (declared inside of other message).
     */
    boolean isNested();

}
