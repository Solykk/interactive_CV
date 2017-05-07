package service;

import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * Created by Solyk on 03.05.2017.
 */
public class ViewProperties {

    private double scaleData;

    public ViewProperties(double scaleData) {
        this.scaleData = scaleData;
    }

    public Polyline getPolylineBoxBlur(Polyline polyline) {
        polyline.setStroke(Color.WHITE);
        polyline.setStrokeWidth(1);
        polyline.setEffect(new BoxBlur(2,2,1));
        return polyline;
    }

    public Polyline getPolylineGlow(Polyline polyline) {
        polyline.setStroke(Color.WHITE);
        polyline.setStrokeWidth(1);
        polyline.setEffect(new Glow(1));
        return polyline;
    }

    public Polygon getBasicLayer(Polygon polygon) {
        polygon.setFill(Color.WHITE);
        polygon.setOpacity(0.2);
        polygon.setEffect(new BoxBlur(5,5,2));
        return polygon;
    }

    public Polygon getBodyLayer(Polygon polygon, Color color) {
        if(color != null){
            polygon.setFill(color);
        }
        polygon.setOpacity(0.85);
        return polygon;
    }

    public Polygon getAdjustmentLayer(Polygon polygon) {
        polygon.setFill(Color.WHITE);
        polygon.setOpacity(0.1);
        polygon.setEffect(new BoxBlur(1,1,2));
        return polygon;
    }

    public Text getTextProperties(String text, int size) {
        Text result = new Text();
        result.setFont(new Font("Book Antiqua", size));
        result.setFill(Color.WHITE);
        result.setTextAlignment(TextAlignment.JUSTIFY);
        result.setText(text);
        result.setEffect(new Glow(0.75));
        return result;
    }

    public TextFlow getTextFlowForNodeWin(Text text){
        TextFlow textLayer = new TextFlow(text);
        textLayer.setLayoutX(633);
        textLayer.setLayoutY(370);
        textLayer.setTextAlignment(TextAlignment.CENTER);
        textLayer.setPrefSize(100,20);
        return textLayer;
    }

    public TextFlow getTextFlowForCVWinHead(Text text){
        TextFlow textLayerHead = new TextFlow(text);
        textLayerHead.setLayoutX(188);
        textLayerHead.setLayoutY(12);
        textLayerHead.setTextAlignment(TextAlignment.CENTER);
        textLayerHead.setPrefSize(160,20);
        return textLayerHead;
    }

    public TextFlow getTextFlowForCVWinHeadUnder(Text text){
        TextFlow textLayerHeadUnder = new TextFlow(text);
        textLayerHeadUnder.setLayoutX(28);
        textLayerHeadUnder.setLayoutY(38);
        textLayerHeadUnder.setTextAlignment(TextAlignment.CENTER);
        textLayerHeadUnder.setPrefSize(480,20);
        return textLayerHeadUnder;
    }

    public TextArea getTextFlowForCVWinBody(String text){
        TextArea textLayerBody = new TextArea(text);
        textLayerBody.setEditable(false);
        textLayerBody.setWrapText(true);
        textLayerBody.setEffect(new Glow(0.4));
        textLayerBody.setOpacity(0.7);
        textLayerBody.setLayoutX(35);
        textLayerBody.setLayoutY(76);
        textLayerBody.setPrefSize(465,440);
        return textLayerBody ;
    }

    public TextArea getTextFlowForCVWinFooter(String text){
        TextArea textLayerFooter = new TextArea(text);
        textLayerFooter.setEditable(false);
        textLayerFooter.setWrapText(true);
        textLayerFooter.setEffect(new Glow(0.4));
        textLayerFooter.setOpacity(0.7);
        textLayerFooter.setLayoutX(215);
        textLayerFooter.setLayoutY(565);
        textLayerFooter.setPrefSize(300,165);
        return textLayerFooter ;
    }

    public TextFlow getTextFlowForWhyWinHead(Text text){
        TextFlow textLayerHead = new TextFlow(text);
        textLayerHead.setLayoutX(138.66);
        textLayerHead.setLayoutY(12.2);
        textLayerHead.setTextAlignment(TextAlignment.CENTER);
        textLayerHead.setPrefSize(140,15);
        return textLayerHead;
    }

    public TextFlow getTextFlowForWhyWinHeadUnder(Text text){
        TextFlow textLayerHeadUnder = new TextFlow(text);
        textLayerHeadUnder.setLayoutX(23.66);
        textLayerHeadUnder.setLayoutY(30.9);
        textLayerHeadUnder.setTextAlignment(TextAlignment.CENTER);
        textLayerHeadUnder.setPrefSize(370,18);
        return textLayerHeadUnder;
    }

    public TextArea getTextFlowForWhyWinBody(String text){
        TextArea textLayerBody = new TextArea(text);
        textLayerBody.setEditable(false);
        textLayerBody.setWrapText(true);
        textLayerBody.setEffect(new Glow(0.4));
        textLayerBody.setOpacity(0.7);
        textLayerBody.setLayoutX(33);
        textLayerBody.setLayoutY(60);
        textLayerBody.setPrefSize(352,271);
        return textLayerBody ;
    }

    public TextArea getTextFlowForChatInputLayer(){
        TextArea textLayerBody = new TextArea();
        textLayerBody.setEditable(true);
        textLayerBody.setWrapText(true);
        textLayerBody.setEffect(new Glow(0.4));
        textLayerBody.setOpacity(0.7);
        textLayerBody.setLayoutX(122);
        textLayerBody.setLayoutY(245);
        textLayerBody.setPrefSize(261,81);
        return textLayerBody ;
    }

    public TextArea getTextFlowForChatOutputLayer(){
        TextArea textLayerBody = new TextArea();
        textLayerBody.setEditable(false);
        textLayerBody.setWrapText(true);
        textLayerBody.setEffect(new Glow(0.4));
        textLayerBody.setOpacity(0.7);
        textLayerBody.setLayoutX(33);
        textLayerBody.setLayoutY(60);
        textLayerBody.setPrefSize(352,171);
        return textLayerBody ;
    }

    public TextFlow getTextFlowForChatSendButton(Text text){
        TextFlow textLayerHead = new TextFlow(text);
        textLayerHead.setLayoutX(35);
        textLayerHead.setLayoutY(292);
        textLayerHead.setTextAlignment(TextAlignment.CENTER);
        textLayerHead.setPrefSize(65,16);
        return textLayerHead;
    }

}
