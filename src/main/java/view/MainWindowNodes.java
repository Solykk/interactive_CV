package view;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.*;

import service.Scalable;
import service.ViewProperties;

/**
 * Created by Solyk on 02.05.2017.
 */
public class MainWindowNodes implements Scalable {

    private String text;
    private ViewProperties properties;
    private double scaleDelta;

    public MainWindowNodes(String text, ViewProperties properties, double scaleDelta) {
        this.text = text;
        this.properties = properties;
        this.scaleDelta = scaleDelta;
    }

    public Group createGroup(){

        Group group = new Group();

        Polygon basicLayer = properties.getBasicLayer(new Polygon(getPolygonAdjustmentLayer()));

        Polygon bodyLayer = properties.getBodyLayer(new Polygon(getPolygonBody()), null);

        Polyline polylineBlur = properties.getPolylineBoxBlur(new Polyline(getPolyline()));
        Polyline polylineGlow = properties.getPolylineGlow(new Polyline(getPolyline()));

        Group polylineLayer = new Group(polylineBlur, polylineGlow);

        Polygon adjustmentLayer = properties.getAdjustmentLayer(new Polygon(getPolygonAdjustmentLayer()));

        Text text = properties.getTextProperties(getText(), 19);

        TextFlow textLayer = new TextFlow(text);
        textLayer.setLayoutX(633);
        textLayer.setLayoutY(370);
        textLayer.setTextAlignment(TextAlignment.CENTER);
        textLayer.setPrefSize(100,20);

        group.getChildren().addAll(basicLayer, bodyLayer, polylineLayer, adjustmentLayer, textLayer);

        group.setScaleX(scaleDelta);
        group.setScaleY(scaleDelta);

        return group;
    }

    private double [] getPolygonAdjustmentLayer(){
        return new double[]{
                644.634,317.548,
                606.268,384,
                644.634,450.452,
                721.366,450.452,
                759.732,384,
                721.366,317.548};
    }

    private double [] getPolygonBody(){
        return new double[]{
                614.25, 373.175,
                608, 384,
                645.5, 448.952,
                658, 448.952,
                663, 443.952,
                703, 443.952,
                708, 448.952,
                720.5, 448.952,
                758, 384,
                751.75, 373.175,
                744.92, 371.345,
                724.92, 336.704,
                726.75, 329.873,
                720.5, 319.048,
                645.5, 319.048,
                639.25, 329.873,
                641.08, 336.704,
                621.08, 371.345 };
    }

    private double [] getPolyline(){
        return new double[]{
                614.25, 373.175,
                608, 384,
                645.5, 448.952,
                658, 448.952,
                663, 443.952,
                703, 443.952,
                708, 448.952,
                720.5, 448.952,
                758, 384,
                751.75, 373.175,
                744.92, 371.345,
                724.92, 336.704,
                726.75, 329.873,
                720.5, 319.048,
                645.5, 319.048,
                639.25, 329.873,
                641.08, 336.704,
                621.08, 371.345,
                614.25, 373.175};
    }

    public String getText() {
        return text;
    }
}
