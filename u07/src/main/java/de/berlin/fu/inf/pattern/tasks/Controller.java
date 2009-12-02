package de.berlin.fu.inf.pattern.tasks;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.ArrayList;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class);
	
	class TransformFunc implements Function<Digit, Entry<Vectorable, Integer>> {
		public Entry<Vectorable, Integer> apply(Digit digit) {
			double vecData[] = new double[Digit.POINT_NUMBER*2];
			int i = 0;
			for( DigitPoint point : digit.getPoints() ) {
				// scale
				vecData[i++] = point.getX()*1d/DigitPoint.MAX_X;
				vecData[i++] = point.getY()*1d/DigitPoint.MAX_Y;
			}
			DoubleVector vec = new DoubleVector(vecData);
			if( logger.isTraceEnabled() ) {
				logger.trace("new Entry: Vect" + vec + " Group:" + digit.getGroup());
			}
			return new Entry<Vectorable, Integer>(vec, digit.getGroup());
		}
	}
	
	private final TransformFunc func = new TransformFunc();

	
	
	public Collection<Entry<Vectorable, Integer>> transformDigits(Collection<Digit> c) {
		return new ArrayList<Entry<Vectorable, Integer>>(Collections2.transform(c, func));
	}
	
}
