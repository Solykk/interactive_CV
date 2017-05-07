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

import model.Language;

import service.Scalable;
import service.ViewProperties;

/**
 * Created by Solyk on 03.05.2017.
 */
public class CVWindow implements Scalable {

    private String headText;
    private String headUnderText;
    private String bodyText;
    private String footerText;

    private double scaleDelta;

    private ViewProperties properties;

    public CVWindow(Language language, ViewProperties properties, double scaleDelta ) {
        this.headText = language.getCVWinHead();
        this.headUnderText = language.getCVWinHeadUnder();
        this.bodyText = language.getCVWinBody();
        this.footerText = language.getCVWinFooter();
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

        ImageView imageView = new ImageView(new Image("CVpicture.jpg"));
        imageView.setScaleX(0.7);
        imageView.setScaleY(0.7);
        imageView.setLayoutX(28);
        imageView.setLayoutY(500);
        imageView.setOpacity(0.6);

        Polygon adjustmentLayer = properties.getAdjustmentLayer(new Polygon(getPolygonAdjustmentLayer()));

        Text textH = properties.getTextProperties(headText, 14);
        Text textHU = properties.getTextProperties(headUnderText, 18);

        TextFlow textLayerHead = properties.getTextFlowForCVWinHead(textH);
        TextFlow textLayerHeadUnder = properties.getTextFlowForCVWinHeadUnder(textHU);
        TextArea textLayerBody = properties.getTextFlowForCVWinBody(bodyText);
        TextArea textLayerFooter = properties.getTextFlowForCVWinFooter(footerText);

        ImageView closeButton = getCloseButton();

        Polygon usedBodyLayerRed = properties.getBodyLayer(new Polygon(getPolygonBodyRed()), Color.rgb(0,0,0));
        usedBodyLayerRed.setOpacity(0.01);

        group.getChildren().addAll(basicLayer,
                bodyLayerBlack, bodyLayerRed,
                polylineLayerHead, polylineLayerBody, polylineLayerFooter,
                imageView,
                adjustmentLayer,
                textLayerHead, textLayerHeadUnder, textLayerBody, textLayerFooter,
                usedBodyLayerRed, closeButton);

        group.setScaleX(scaleDelta);
        group.setScaleY(scaleDelta);

        return group;
    }

    private ImageView getCloseButton() {
        ImageView closeButton = new  ImageView(new Image("CloseButton.png"));
        closeButton.setScaleX(0.85);
        closeButton.setScaleY(0.85);
        closeButton.setLayoutY(13);
        closeButton.setLayoutX(346);
        closeButton.setOpacity(0.25);
        return closeButton;
    }

    private double[] getPolylineFooter() {
        return new double[]{
                214,575.169,
                214,545.169,
                197,529.419,
                27,529.419,
                10,545.169,
                10,576.803,
                27,592.553,
                27,673.734,
                10,689.484,
                10,710.239,
                13,713.239,
                170,713.239,
                187,696.239,
                349,696.239,
                366,713.239,
                523,713.239,
                526,710.239,
                526,689.484,
                509,673.734,
                509,592.553,
                526,576.803,
                526,545.169,
                509,529.419,
                507.5,524.419,
                510.5,524.419
        };
    }

    private double[] getPolylineBody() {
        return new double[]{
                509,216.775,
                507.5,216.775,
                509,211.775,
                509,186.775,
                526,176.96,
                526,170.053,
                509,160.239,
                509,108.638,
                526,98.823,
                526,66,
                523,63,
                13,63,
                10,66,
                10,98.823,
                27,108.638,
                27,160.239,
                10,170.053,
                10,176.96,
                27,186.775,
                27,211.775,
                28.5,216.775,
                27,216.775
        };
    }

    public double[] getPolygonBodyRed() {
        return new double[]{
                526,36,
                523,33,
                389,33,
                383.879,30.879,
                365.121,12.121,
                360,10,
                176,10,
                170.879,12.121,
                152.121,30.879,
                147,33,
                13,33,
                10,36,
                10,60,
                13,63,
                523,63,
                526,60
        };
    }

    private double[] getPolylineHead() {
        return new double[]{
                526,36,
                523,33,
                389,33,
                383.879,30.879,
                365.121,12.121,
                360,10,
                176,10,
                170.879,12.121,
                152.121,30.879,
                147,33,
                13,33,
                10,36,
                10,60,
                13,63,
                523,63,
                526,60,
                526,36
        };
    }

    private double[] getPolygonBodyBlack() {
        return new double[]{
                27,108.638,
                27,160.239,
                10,170.053,
                10,176.96,
                27,186.775,
                27,529.419,
                10,545.169,
                10,576.803,
                27,592.553,
                27,673.734,
                10,689.484,
                10,710.239,
                13,713.239,
                170,713.239,
                187,696.239,
                349,696.239,
                366,713.239,
                523,713.239,
                526,710.239,
                526,689.484,
                509,673.734,
                509,592.553,
                526,576.803,
                526,545.169,
                509,529.419,
                509,186.775,
                526,176.96,
                526,170.053,
                509,160.239,
                509,108.638,
                526,98.823,
                526,66,
                523,63,
                13,63,
                10,66,
                10,98.823 };
    }

    private double[] getPolygonAdjustmentLayer() {
        return new double[]{
                511,211.775,
                511,187.93,
                528,178.115,
                528,34,
                525,31,
                389.828,31,
                384.707,28.879,
                365.95,10.121,
                360.828,8,
                175.172,8,
                170.05,10.121,
                151.293,28.879,
                146.172,31,
                11,31,
                8,34,
                8,178.115,
                25,187.93,
                25,211.775,
                27,216.775,
                27,526.693,
                8,544.295,
                8,712.239,
                11,715.239,
                525,715.239,
                528,712.239,
                528,544.295,
                509,526.693,
                509,216.775
        };
    }
}
