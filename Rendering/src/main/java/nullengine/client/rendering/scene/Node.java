package nullengine.client.rendering.scene;

import com.github.mouse0w0.observable.value.MutableObjectValue;
import com.github.mouse0w0.observable.value.ObservableObjectValue;
import com.github.mouse0w0.observable.value.SimpleMutableObjectValue;
import nullengine.client.rendering.math.Transform;
import org.apache.commons.lang3.Validate;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

import java.util.*;

public class Node {

    private final MutableObjectValue<Node> parent = new SimpleMutableObjectValue<>();

    final MutableObjectValue<Scene> scene = new SimpleMutableObjectValue<>();

    private final List<Node> children = new ArrayList<>();
    private final List<Node> unmodifiableChildren = Collections.unmodifiableList(children);

    private Transform transform = new Transform();
    private Transform worldTransform = new Transform();

    private Map<Object, Object> properties;

    public Node() {
        parent.addChangeListener((observable, oldValue, newValue) -> refreshTransform());
    }

    public final ObservableObjectValue<Scene> scene() {
        return scene.toImmutable();
    }

    public final ObservableObjectValue<Node> parent() {
        return parent.toImmutable();
    }

    public final Scene getScene() {
        return scene.getValue();
    }

    public final Node getParent() {
        return parent.getValue();
    }

    private void setParent(Node parent) {
        Node oldParent = getParent();
        if (oldParent == parent) {
            return;
        }
        if (oldParent != null) {
            scene.unbindBidirectional(oldParent.scene);
            oldParent.children.remove(this);
        }
        this.parent.set(parent);
        if (parent != null) {
            scene.bindBidirectional(parent.scene);
        } else {
            scene.set(null);
        }
    }

    public final List<Node> getChildren() {
        return unmodifiableChildren;
    }

    public void addChild(Node node) {
        Validate.notNull(node);
        children.add(node);
        node.setParent(this);
    }

    public void removeChild(Node node) {
        node.setParent(null);
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform.set(transform);
        refreshTransform();
    }

    public void setTranslation(Vector3fc translation) {
        transform.setTranslation(translation);
        refreshTransform();
    }

    public void setTranslation(float x, float y, float z) {
        transform.setTranslation(x, y, z);
        refreshTransform();
    }

    public void setRotation(Vector3fc rotation) {
        transform.setRotation(rotation);
        refreshTransform();
    }

    public void setRotation(float angleX, float angleY, float angleZ) {
        transform.setRotation(angleX, angleY, angleZ);
        refreshTransform();
    }

    public void setRotation(Quaternionfc rotation) {
        transform.setRotation(rotation);
        refreshTransform();
    }

    public void setRotation(float x, float y, float z, float w) {
        transform.setRotation(x, y, z, w);
        refreshTransform();
    }

    public void setScale(Vector3fc scale) {
        transform.setScale(scale);
        refreshTransform();
    }

    public void setScale(float x, float y, float z) {
        transform.setScale(x, y, z);
        refreshTransform();
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }

    public Vector3fc getWorldTranslation() {
        return worldTransform.getTranslation();
    }

    public Quaternionfc getWorldRotation() {
        return worldTransform.getRotation();
    }

    public Vector3fc getWorldScale() {
        return worldTransform.getScale();
    }

    protected void refreshTransform() {
        worldTransform.set(transform);
        Node parent = getParent();
        if (parent != null) {
            worldTransform.applyParent(parent.worldTransform);
        }
        getChildren().forEach(node -> refreshTransform());
    }

    public Map<Object, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
        return properties;
    }

    public boolean hasProperties() {
        return properties != null && !properties.isEmpty();
    }
}
