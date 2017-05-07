package service;

import javafx.geometry.Rectangle2D;

import java.math.BigDecimal;

/**
 * Created by Solyk on 07.05.2017.
 */
public interface Scalable {
    double HEIGHT_APPLICATION_BUILD = 738;
    double WIDTH_APPLICATION_BUILD = 1366;

    default double getScaleDelta(Rectangle2D bounds){
        double heightDelta = bounds.getHeight()/ HEIGHT_APPLICATION_BUILD;
        double widthDelta = bounds.getWidth()/ WIDTH_APPLICATION_BUILD;
        double result = heightDelta <= widthDelta ? heightDelta : widthDelta;
        return BigDecimal.valueOf(result ).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
