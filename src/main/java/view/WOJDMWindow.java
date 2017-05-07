package view;

import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import service.Scalable;
import service.ViewProperties;

/**
 * Created by Solyk on 04.05.2017.
 */
public class WOJDMWindow implements Scalable{

    private String headText;
    private String headUnderText;
    private String bodyText;

    private double scaleDelta;

    private ViewProperties properties;

    public WOJDMWindow(String descriptionHead, String descriptionHeadUnder, String descriptionBody, ViewProperties properties,
                       double scaleDelta) {
        this.headText = descriptionHead;
        this.headUnderText = descriptionHeadUnder;
        this.bodyText = descriptionBody;
        this.properties = properties;
        this.scaleDelta = scaleDelta;
    }

    public Group createGroup(){

        Group group = new Group();

        Polygon basicLayer = properties.getBasicLayer(new Polygon(getPolygonAdjustmentLayer()));

        Polygon bodyLayerBlack = properties.getBodyLayer(new Polygon(getPolygonBodyBlack()), null);
        Polygon bodyLayerRed = properties.getBodyLayer(new Polygon(getPolygonBodyRed()), Color.rgb(255,40,0));

        Polyline polylineBlurHead = properties.getPolylineBoxBlur(new Polyline(getPolylineHead()));
        Polyline polylineGlowHead = properties.getPolylineGlow(new Polyline(getPolylineHead()));

        Group polylineLayerHead = new Group(polylineBlurHead, polylineGlowHead);

        Polyline polylineBlurBody = properties.getPolylineBoxBlur(new Polyline(getPolylineBody()));
        Polyline polylineGlowBody = properties.getPolylineGlow(new Polyline(getPolylineBody()));

        Group polylineLayerBody = new Group(polylineBlurBody, polylineGlowBody);

        Polyline polylineBlurFooter = properties.getPolylineBoxBlur(new Polyline(getPolylineFooter()));
        Polyline polylineGlowFooter = properties.getPolylineGlow(new Polyline(getPolylineFooter()));

        Group polylineLayerFooter = new Group(polylineBlurFooter, polylineGlowFooter);

        Polygon adjustmentLayer = properties.getAdjustmentLayer(new Polygon(getPolygonAdjustmentLayer()));

        Text textH = properties.getTextProperties(headText, 12);
        Text textHU = properties.getTextProperties(headUnderText, 16);

        TextFlow textLayerHead = properties.getTextFlowForWhyWinHead(textH);
        TextFlow textLayerHeadUnder = properties.getTextFlowForWhyWinHeadUnder(textHU);
        TextArea textLayerBody = properties.getTextFlowForWhyWinBody(bodyText);

        Polygon usedBodyLayerRed = properties.getBodyLayer(new Polygon(getPolygonBodyRed()), Color.rgb(0,0,0));
        usedBodyLayerRed.setOpacity(0.01);

        ImageView closeButton = getCloseButton();

        group.getChildren().addAll(basicLayer,
                bodyLayerBlack, bodyLayerRed,
                polylineLayerHead, polylineLayerBody, polylineLayerFooter,
                adjustmentLayer,
                textLayerHead, textLayerHeadUnder, textLayerBody,
                usedBodyLayerRed, closeButton);

        group.setScaleX(scaleDelta);
        group.setScaleY(scaleDelta);

        return group;
    }

    private ImageView getCloseButton() {
        ImageView closeButton = new  ImageView(new Image("CloseButton.png"));
        closeButton.setScaleX(0.70);
        closeButton.setScaleY(0.70);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(262);
        closeButton.setOpacity(0.25);
        return closeButton;
    }

    private double[] getPolylineFooter() {
        return new double[]{
                23.09,264.145,
                21.935,264.145,
                23.09,267.995,
                23.09,287.245,
                10,294.803,
                10,300.121,
                23.09,307.678,
                23.09,322.975,
                10,335.102,
                10,351.084,
                12.31,353.394,
                133.2,353.394,
                146.29,340.304,
                271.03,340.304,
                284.12,353.394,
                405.01,353.394,
                407.32,351.084,
                407.32,335.102,
                394.23,322.975,
                394.23,307.678,
                407.32,300.121,
                407.32,294.803,
                394.23,287.245,
                394.23,267.995,
                395.385,264.145,
                394.23,264.145
        };
    }

    private double[] getPolylineBody() {
        return new double[]{
                394.23,149.35,
                393.075,149.35,
                394.23,145.5,
                394.23,126.25,
                407.32,118.693,
                407.32,113.375,
                394.23,105.817,
                394.23,85.951,
                407.32,78.394,
                407.32,53.12,
                405.01,50.81,
                12.31,50.81,
                10,53.12,
                10,78.394,
                23.09,85.951,
                23.09,105.817,
                10,113.375,
                10,118.693,
                23.09,126.25,
                23.09,145.5,
                24.245,149.35,
                23.09,149.35
        };
    }

    public double[] getPolygonBodyRed() {
        return new double[]{
                407.32,30.02,
                405.01,27.71,
                301.83,27.71,
                297.887,26.077,
                283.443,11.633,
                279.5,10,
                137.82,10,
                133.877,11.633,
                119.433,26.077,
                115.49,27.71,
                12.31,27.71,
                10,30.02,
                10,48.5,
                12.31,50.81,
                405.01,50.81,
                407.32,48.5
        };
    }

    private double[] getPolylineHead() {
        return new double[]{
                407.32,30.02,
                405.01,27.71,
                301.83,27.71,
                297.887,26.077,
                283.443,11.633,
                279.5,10,
                137.82,10,
                133.877,11.633,
                119.433,26.077,
                115.49,27.71,
                12.31,27.71,
                10,30.02,
                10,48.5,
                12.31,50.81,
                405.01,50.81,
                407.32,48.5,
                407.32,30.02
        };
    }

    private double[] getPolygonBodyBlack() {
        return new double[]{
                10,78.394,
                23.09,85.951,
                23.09,105.817,
                10,113.375,
                10,118.693,
                23.09,126.25,
                23.09,287.245,
                10,294.803,
                10,300.121,
                23.09,307.678,
                23.09,322.975,
                10,335.102,
                10,351.084,
                12.31,353.394,
                133.2,353.394,
                146.29,340.304,
                271.03,340.304,
                284.12,353.394,
                405.01,353.394,
                407.32,351.084,
                407.32,335.102,
                394.23,322.975,
                394.23,307.678,
                407.32,300.121,
                407.32,294.803,
                394.23,287.245,
                394.23,126.25,
                407.32,118.693,
                407.32,113.375,
                394.23,105.817,
                394.23,85.951,
                407.32,78.394,
                407.32,53.12,
                405.01,50.81,
                12.31,50.81,
                10,53.12
        };
    }

    private double[] getPolygonAdjustmentLayer() {
        return new double[]{
                23.09,284.936,
                8,293.648,
                8,351.912,
                11.482,355.394,
                405.838,355.394,
                409.32,351.912,
                409.32,293.648,
                394.23,284.936,
                394.23,128.56,
                409.32,119.848,
                409.32,29.192,
                405.838,25.71,
                302.228,25.71,
                299.019,24.381,
                284.576,9.938,
                279.898,8,
                137.422,8,
                132.744,9.938,
                118.301,24.381,
                115.092,25.71,
                11.482,25.71,
                8,29.192,
                8,119.848,
                23.09,128.56
        };
    }
}
