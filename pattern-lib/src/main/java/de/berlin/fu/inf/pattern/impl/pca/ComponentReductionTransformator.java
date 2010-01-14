package de.berlin.fu.inf.pattern.impl.pca;

import com.google.common.base.Function;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;

/**
 * removes given "component" from any vector, without dimension reduction
 *
 * @author alex
 */
class ComponentReductionTransformator implements Function<Float64Vector, Float64Vector> {

    private Float64Vector component;
    private Float64Vector translation;

    public ComponentReductionTransformator() {
    }

    public ComponentReductionTransformator(Float64Vector component) {
        if (component != null) {
            this.component = component.times(component.norm());
        }
    }

    public void setComponent(Float64Vector component) {
        this.component = component.times(1/component.normValue());
    }

    public void setTranslation(Float64Vector translation) {
        this.translation = translation;
    }

    public Float64Vector apply(Float64Vector vec) {
        if( translation != null )
            return vec.plus(translation).minus(component.times(vec.times(component)));
        else
            return vec.minus(component.times(vec.times(component)));
    }
}
