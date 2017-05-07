package control;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import model.ENLanguage;
import model.Language;
import model.RULanguage;
import service.Scalable;
import service.ViewProperties;
import view.CVWindow;
import view.ChatWindow;
import view.MainWindowNodes;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import view.WOJDMWindow;

import java.io.*;

/**
 * Created by Solyk on 28.04.2017.
 */
public class CVApplication extends Application implements Scalable {

    private final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
    private final double SCALE_DELTA = getScaleDelta(BOUNDS);
    private final ViewProperties PROPERTIES = new ViewProperties(SCALE_DELTA);

    private Stage stageForWhy;

    private Stage stageForCV;
    private Stage stageForMore;
    private Stage stageForChat;
    private Stage stageForOldJob;

    private Language language;

    private Group cvNode;
    private Group downloadNode;
    private Group whyNode;
    private Group languageNode;
    private Group chatNode;
    private Group moreNode;
    private Group oldJobNode;

    private Group cvWindow;
    private Group whyWindow;
    private Group moreWindow;
    private Group chatWindow;
    private Group oldJobWindow;

    private boolean isCVWindowOn;
    private boolean isWhyWindowOn;
    private boolean isMoreWindowOn;
    private boolean isChatWindowOn;
    private boolean isOldJobWindowOn;

    private final int FONT_SIZE_20 = 20;
    private final int FORTH_CHILDREN_OF_GROUP = 4;
    private final int SIXTH_CHILDREN_OF_GROUP = 6;
    private final int SEVENTH_CHILDREN_OF_GROUP = 7;
    private final int EIGHTH_CHILDREN_OF_GROUP = 8;
    private final int NINTH_CHILDREN_OF_GROUP = 9;
    private final int TENTH_CHILDREN_OF_GROUP = 10;
    private final int ELEVENTH_CHILDREN_OF_GROUP = 11;

    private double xOffsetCVWin;
    private double yOffsetCVWin;

    private double xOffsetWhyWin;
    private double yOffsetWhyWin;

    private double xOffsetMoreWin;
    private double yOffsetMoreWin;

    private double xOffsetChatWin;
    private double yOffsetChatWin;

    private double xOffsetOldJobWin;
    private double yOffsetOldJobWin;

    @Override
    public void start(Stage stage) throws Exception {
        initMet();

        stage.initStyle(StageStyle.TRANSPARENT);

        cvNode = new MainWindowNodes(language.getCVNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        cvNodeProperties();

        whyNode = new MainWindowNodes(language.getWhyNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        whyWinProperties();

        downloadNode = new MainWindowNodes(language.getDownloadNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        scaleAndMouseEvent(downloadNode);
        downloadNode.setOnMouseClicked(event -> {
            try {
                save();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText(language.getSaveOk());
                alert.showAndWait();

            } catch (IOException | DocumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText(language.getSaveError() + e.getMessage());
                alert.showAndWait();
            }
        });

        languageNode = new MainWindowNodes(language.getLanguageNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        scaleAndMouseEvent(languageNode);
        languageNode.setOnMouseClicked(event -> reLanguage());

        moreNode = new MainWindowNodes(language.getMoreNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        moreWinProperties();

        oldJobNode = new MainWindowNodes(language.getOldJobNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        oldJobWinProperties();

        chatNode = new MainWindowNodes(language.getChatNodeDescription(), PROPERTIES, SCALE_DELTA).createGroup();
        chatProperties();

        circlePathTran(BOUNDS);

        ImageView closeButton = new  ImageView(new Image("CloseButton.png"));
        closeButton.setLayoutX(800);
        closeButton.setLayoutY(200);
        closeButton.setOpacity(0.25);

        closeButton.setOnMouseClicked(event -> {
            if (stageForCV != null){
                stageForCV.close();
            }
            if (stageForWhy != null){
                stageForWhy.close();
            }
            if (stageForMore != null){
                stageForMore.close();
            }
            if (stageForOldJob != null){
                stageForOldJob.close();
            }
            stage.close();
        });
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0)");
        Group groupPa = new Group(downloadNode, whyNode, chatNode, languageNode, moreNode, oldJobNode, cvNode, closeButton);

        pane.getChildren().add(groupPa);

        pane.setScaleX(SCALE_DELTA);
        pane.setScaleY(SCALE_DELTA);

        Scene scene = new Scene(pane, 1366 * SCALE_DELTA, 738 * SCALE_DELTA);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Main");
        stage.getIcons().add(new Image("Master-Joda.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void save() throws IOException, DocumentException {
        if (language instanceof ENLanguage){
            savePdf("Dmitriy_Lyashenko_Java_cv_en.pdf");
        } else {
            savePdf("Dmitriy_Lyashenko_Java_cv_ru.pdf");
        }
    }

    private void savePdf(String url) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(url);
        Document document = new Document(reader.getPageSizeWithRotation(1));
        PdfCopy copy = new PdfCopy(document , new FileOutputStream( url));
        document.open();
        copy.addPage(copy.getImportedPage(reader, 1));
        document.close();
    }

    private void initMet() {
        language = new ENLanguage();

        System.out.println(SCALE_DELTA);
        System.out.println(BOUNDS.getWidth() + " " + BOUNDS.getHeight());
        isCVWindowOn = false;
        isWhyWindowOn = false;
        isMoreWindowOn = false;
        isChatWindowOn = false;
        isOldJobWindowOn = false;
    }

    private void scaleAndMouseEvent(Group groupNode) {
        groupNode.setScaleX(0.75 * SCALE_DELTA);
        groupNode.setScaleY(0.75 * SCALE_DELTA);
        groupOnMouseEntered(groupNode,  SCALE_DELTA);
        groupOnMouthExited(groupNode, 0.75 * SCALE_DELTA);
    }

    private void circlePathTran(Rectangle2D bounds) {

        Circle circle = getCircle(bounds, 60);
        Circle circle1 = getCircle(bounds, 120);
        Circle circle2 = getCircle(bounds, 180);
        Circle circle3 = getCircle(bounds, 240);
        Circle circle4 = getCircle(bounds, 300);
        Circle circle5 = getCircle(bounds, 360);

        PathTransition transition = new PathTransition();
        transition.setNode(downloadNode);
        transition.setDuration(Duration.seconds(15));
        transition.setPath(circle);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();

        PathTransition transition1 = new PathTransition();
        transition1.setNode(whyNode);
        transition1.setDuration(Duration.seconds(15));
        transition1.setPath(circle1);
        transition1.setCycleCount(PathTransition.INDEFINITE);
        transition1.play();

        PathTransition transition2 = new PathTransition();
        transition2.setNode(chatNode);
        transition2.setDuration(Duration.seconds(15));
        transition2.setPath(circle2);
        transition2.setCycleCount(PathTransition.INDEFINITE);
        transition2.play();

        PathTransition transition3 = new PathTransition();
        transition3.setNode(languageNode);
        transition3.setDuration(Duration.seconds(15));
        transition3.setPath(circle3);
        transition3.setCycleCount(PathTransition.INDEFINITE);
        transition3.play();

        PathTransition transition4 = new PathTransition();
        transition4.setNode(moreNode);
        transition4.setDuration(Duration.seconds(15));
        transition4.setPath(circle4);
        transition4.setCycleCount(PathTransition.INDEFINITE);
        transition4.play();

        PathTransition transition5 = new PathTransition();
        transition5.setNode(oldJobNode);
        transition5.setDuration(Duration.seconds(15));
        transition5.setPath(circle5);
        transition5.setCycleCount(PathTransition.INDEFINITE);
        transition5.play();

        Circle circle6 = new Circle(2);
        circle6.setLayoutY(bounds.getHeight()/ 2);
        circle6.setLayoutX(bounds.getWidth()/ 2);

        PathTransition transition6 = new PathTransition();
        transition6.setNode(cvNode);
        transition6.setDuration(Duration.seconds(15));
        transition6.setPath(circle6);
        transition6.setCycleCount(PathTransition.INDEFINITE);
        transition6.play();
    }

    private Circle getCircle(Rectangle2D bounds, int value) {
        Circle circle1 = new Circle((int)(110 * SCALE_DELTA));
        circle1.setRotate(value);
        circle1.setLayoutY(bounds.getHeight()/ 2);
        circle1.setLayoutX(bounds.getWidth()/ 2);
        return circle1;
    }

    private void cvNodeProperties() {
        groupOnMouseEntered(cvNode, 1.2 * SCALE_DELTA);
        groupOnMouthExited(cvNode, SCALE_DELTA);

        cvNode.setOnMouseClicked(event -> {
            if(!isCVWindowOn) {
                stageForCV = new Stage();
                stageForCV.initStyle(StageStyle.TRANSPARENT);

                cvWindow = new CVWindow(language, PROPERTIES, SCALE_DELTA).createGroup();
                cvWindow.getChildren().get(13).setOnMouseClicked(event13 -> {
                    stageForCV.close();
                    isCVWindowOn = false;
                    cvWindow = null;
                });

                cvWindow.getChildren().get(12).setOnMousePressed(event1 ->{
                    xOffsetCVWin = event1.getSceneX();
                    yOffsetCVWin = event1.getSceneY();
                });

                cvWindow.getChildren().get(12).setOnMouseDragged(event12 ->{
                    stageForCV.setX(event12.getScreenX() - xOffsetCVWin);
                    stageForCV.setY(event12.getScreenY() - yOffsetCVWin);
                });

                Scene scene = new Scene(cvWindow, 536, 724);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("styles.css");

                stageForCV.setScene(scene);
                stageForCV.setTitle("CV");
                stageForCV.getIcons().add(new Image("StarDarth.png"));
                stageForCV.setX(0);
                stageForCV.setY(0);
                stageForCV.show();
                isCVWindowOn = true;
            }
        });
    }

    private void whyWinProperties() {
        scaleAndMouseEvent(whyNode);

        whyNode.setOnMouseClicked(event -> {
            if(!isWhyWindowOn) {
                stageForWhy = new Stage();
                stageForWhy.initStyle(StageStyle.TRANSPARENT);

                whyWindow = new WOJDMWindow(language.getWhyWinHead(),
                                            language.getWhyWinHeadUnder(),
                                            language.getWhyWinBody(),
                        PROPERTIES,
                        SCALE_DELTA).createGroup();

                whyWindow.getChildren().get(11).setOnMouseClicked(event13 -> {
                    stageForWhy.close();
                    isWhyWindowOn = false;
                    whyWindow = null;
                });

                whyWindow.getChildren().get(10).setOnMousePressed(event1 ->{
                    xOffsetWhyWin = event1.getSceneX();
                    yOffsetWhyWin = event1.getSceneY();
                });

                whyWindow.getChildren().get(10).setOnMouseDragged(event12 ->{
                    stageForWhy.setX(event12.getScreenX() - xOffsetWhyWin);
                    stageForWhy.setY(event12.getScreenY() - yOffsetWhyWin);
                });

                Scene scene = new Scene(whyWindow, 420, 370);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("styles.css");
                stageForWhy.setTitle("Why");
                stageForWhy.getIcons().add(new Image("R2-D2.png"));
                stageForWhy.setScene(scene);

                stageForWhy.setX(BOUNDS.getWidth()/2 - 150);
                stageForWhy.setY(BOUNDS.getHeight()/2 - 25);

                stageForWhy.show();
                isWhyWindowOn = true;
            }
        });
    }

    private void moreWinProperties() {
        scaleAndMouseEvent(moreNode);

        moreNode.setOnMouseClicked(event -> {
            if(!isMoreWindowOn) {
                stageForMore = new Stage();
                stageForMore.initStyle(StageStyle.TRANSPARENT);

                moreWindow = new WOJDMWindow(language.getMoreWinHead(),
                        language.getMoreWinHeadUnder(),
                        language.getMoreWinBody(),
                        PROPERTIES,
                        SCALE_DELTA).createGroup();

                moreWindow.getChildren().get(11).setOnMouseClicked(event13 -> {
                    stageForMore.close();
                    isMoreWindowOn = false;
                    moreWindow = null;
                });

                moreWindow.getChildren().get(10).setOnMousePressed(event1 ->{
                    xOffsetMoreWin = event1.getSceneX();
                    yOffsetMoreWin = event1.getSceneY();
                });

                moreWindow.getChildren().get(10).setOnMouseDragged(event12 ->{
                    stageForMore.setX(event12.getScreenX() - xOffsetMoreWin);
                    stageForMore.setY(event12.getScreenY() - yOffsetMoreWin);
                });

                Scene scene = new Scene(moreWindow, 420, 370);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("styles.css");
                stageForMore.setTitle("More");
                stageForMore.getIcons().add(new Image("Darth.png"));
                stageForMore.setScene(scene);

                stageForMore.setX(BOUNDS.getWidth()/2 - 150);
                stageForMore.setY(BOUNDS.getHeight()/2 + - 380);

                stageForMore.show();
                isMoreWindowOn = true;
            }
        });
    }

    private void oldJobWinProperties() {
        scaleAndMouseEvent(oldJobNode);

        oldJobNode.setOnMouseClicked(event -> {
            if(!isOldJobWindowOn) {
                stageForOldJob = new Stage();
                stageForOldJob.initStyle(StageStyle.TRANSPARENT);

                oldJobWindow = new WOJDMWindow(language.getOldJobWinHead(),
                        language.getOldJobWinHeadUnder(),
                        language.getOldJobWinBody(),
                        PROPERTIES,
                        SCALE_DELTA).createGroup();

                oldJobWindow.getChildren().get(11).setOnMouseClicked(event13 -> {
                    stageForOldJob.close();
                    isOldJobWindowOn = false;
                    oldJobWindow = null;
                });

                oldJobWindow.getChildren().get(10).setOnMousePressed(event1 ->{
                    xOffsetOldJobWin = event1.getSceneX();
                    yOffsetOldJobWin = event1.getSceneY();
                });

                oldJobWindow.getChildren().get(10).setOnMouseDragged(event12 ->{
                    stageForOldJob.setX(event12.getScreenX() - xOffsetOldJobWin);
                    stageForOldJob.setY(event12.getScreenY() - yOffsetOldJobWin);
                });

                Scene scene = new Scene(oldJobWindow, 420, 370);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("styles.css");
                stageForOldJob.setTitle("OldJob");
                stageForOldJob.getIcons().add(new Image("Grievous.png"));

                stageForOldJob.setX(BOUNDS.getWidth()/1.24 - 150);
                stageForOldJob.setY(BOUNDS.getHeight()/2 + - 380);

                stageForOldJob.setScene(scene);
                stageForOldJob.show();
                isOldJobWindowOn = true;
            }
        });
    }

    private void chatProperties() {
        scaleAndMouseEvent(chatNode);

        chatNode.setOnMouseClicked(event -> {
            if(!isChatWindowOn) {
                stageForChat = new Stage();
                stageForChat.initStyle(StageStyle.TRANSPARENT);

                chatWindow = new ChatWindow(language.getChatWinHead(),
                        language.getChatWinHeadUnder(),
                        language.getChatWinButton(),
                        language.getChatInitTextOfInput(),
                        language.getChatInitTextOfOutput(),
                        PROPERTIES,
                        SCALE_DELTA).createGroup();

                chatWindow.getChildren().get(12).setOnMouseClicked(event13 -> {
                    stageForChat.close();
                    isChatWindowOn = false;
                    chatWindow = null;
                });

                chatWindow.getChildren().get(11).setOnMousePressed(event1 ->{
                    xOffsetChatWin = event1.getSceneX();
                    yOffsetChatWin = event1.getSceneY();
                });

                chatWindow.getChildren().get(11).setOnMouseDragged(event12 ->{
                    stageForChat.setX(event12.getScreenX() - xOffsetChatWin);
                    stageForChat.setY(event12.getScreenY() - yOffsetChatWin);
                });

                Scene scene = new Scene(chatWindow, 420, 370);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("styles.css");
                stageForChat.setTitle("Chat");
                stageForChat.getIcons().add(new Image("C-3PO.png"));
                stageForChat.setScene(scene);

                stageForChat.setX(BOUNDS.getWidth()/1.24 - 150);
                stageForChat.setY(BOUNDS.getHeight()/2 + - 25);

                stageForChat.show();
                isChatWindowOn = true;
            }
        });
    }

    private void reLanguage() {
        if (language instanceof ENLanguage){
            language = new RULanguage();
        } else {
            language = new ENLanguage();
        }

        reLangForNode(downloadNode, language.getDownloadNodeDescription(), FONT_SIZE_20);
        reLangForNode(cvNode, language.getCVNodeDescription(), FONT_SIZE_20);
        reLangForNode(whyNode, language.getWhyNodeDescription(), FONT_SIZE_20);
        reLangForNode(moreNode, language.getMoreNodeDescription(), FONT_SIZE_20);
        reLangForNode(oldJobNode, language.getOldJobNodeDescription(), FONT_SIZE_20);
        reLangForNode(languageNode, language.getLanguageNodeDescription(), FONT_SIZE_20);
        reLangForNode(chatNode, language.getChatNodeDescription(), FONT_SIZE_20);

        if(isCVWindowOn) {
            reLangForCVWin(cvWindow, language);
        }
        if(isWhyWindowOn) {
            reLangForWOJDMWin(whyWindow, language.getWhyWinHead(), language.getWhyWinHeadUnder(), language.getWhyWinBody());
        }
        if(isMoreWindowOn) {
            reLangForWOJDMWin(moreWindow, language.getMoreWinHead(), language.getMoreWinHeadUnder(), language.getMoreWinBody());
        }
        if(isOldJobWindowOn) {
            reLangForWOJDMWin(oldJobWindow, language.getOldJobWinHead(), language.getOldJobWinHeadUnder(), language.getOldJobWinBody());
        }
        if(isChatWindowOn) {
            reLangForChatWin(chatWindow, language);
        }
    }

    private void reLangForNode(Group group, String description, int fontSize) {
        Text text = PROPERTIES.getTextProperties(description, fontSize);
        TextFlow textFlow = PROPERTIES.getTextFlowForNodeWin(text);
        group.getChildren().remove(FORTH_CHILDREN_OF_GROUP);
        group.getChildren().add(textFlow);
    }

    private void reLangForCVWin(Group group, Language language) {
        Text textH = PROPERTIES.getTextProperties(language.getCVWinHead(), 14);
        TextFlow textLayerHead = PROPERTIES.getTextFlowForCVWinHead(textH);

        Text textHU = PROPERTIES.getTextProperties(language.getCVWinHeadUnder(), 18);
        TextFlow textLayerHeadUnder = PROPERTIES.getTextFlowForCVWinHeadUnder(textHU);

        TextArea textLayerBody = PROPERTIES.getTextFlowForCVWinBody(language.getCVWinBody());

        TextArea textLayerFooter = PROPERTIES.getTextFlowForCVWinFooter(language.getCVWinFooter());

        group.getChildren().remove(EIGHTH_CHILDREN_OF_GROUP);
        group.getChildren().add(EIGHTH_CHILDREN_OF_GROUP, textLayerHead);

        group.getChildren().remove(NINTH_CHILDREN_OF_GROUP);
        group.getChildren().add(NINTH_CHILDREN_OF_GROUP, textLayerHeadUnder);

        group.getChildren().remove(TENTH_CHILDREN_OF_GROUP);
        group.getChildren().add(TENTH_CHILDREN_OF_GROUP, textLayerBody);

        group.getChildren().remove(ELEVENTH_CHILDREN_OF_GROUP);
        group.getChildren().add(ELEVENTH_CHILDREN_OF_GROUP, textLayerFooter);
    }

    private void reLangForChatWin(Group group, Language language) {
        Text textH = PROPERTIES.getTextProperties(language.getChatWinHead(), 12);
        TextFlow textLayerHead = PROPERTIES.getTextFlowForWhyWinHead(textH);

        Text textHU = PROPERTIES.getTextProperties(language.getChatWinHeadUnder(), 16);
        TextFlow textLayerHeadUnder = PROPERTIES.getTextFlowForWhyWinHeadUnder(textHU);

        Text textB = PROPERTIES.getTextProperties(language.getChatWinButton(), 12);
        TextFlow textLayerSendButton = PROPERTIES.getTextFlowForChatSendButton(textB);

        TextArea hasText = (TextArea) group.getChildren().get(NINTH_CHILDREN_OF_GROUP);

        if (hasText.getText().isEmpty()){
            TextArea textAreaInput = PROPERTIES.getTextFlowForChatInputLayer();
            textAreaInput.setPromptText(language.getChatInitTextOfInput());

            group.getChildren().remove(NINTH_CHILDREN_OF_GROUP);
            group.getChildren().add(NINTH_CHILDREN_OF_GROUP, textAreaInput);
        }

        TextArea textAreaOutput = PROPERTIES.getTextFlowForChatOutputLayer();
        textAreaOutput.setPromptText(language.getChatInitTextOfOutput());

        group.getChildren().remove(SEVENTH_CHILDREN_OF_GROUP);
        group.getChildren().add(SEVENTH_CHILDREN_OF_GROUP, textLayerHead);

        group.getChildren().remove(EIGHTH_CHILDREN_OF_GROUP);
        group.getChildren().add(EIGHTH_CHILDREN_OF_GROUP, textLayerHeadUnder);

        group.getChildren().remove(TENTH_CHILDREN_OF_GROUP);
        group.getChildren().add(TENTH_CHILDREN_OF_GROUP, textAreaOutput);

        group.getChildren().remove(13);
        group.getChildren().add(13, textLayerSendButton);

    }

    private void reLangForWOJDMWin(Group group, String descriptionHead, String descriptionHeadUnder, String descriptionBody) {
        Text textH = PROPERTIES.getTextProperties(descriptionHead, 12);
        TextFlow textLayerHead = PROPERTIES.getTextFlowForWhyWinHead(textH);

        Text textHU = PROPERTIES.getTextProperties(descriptionHeadUnder, 16);
        TextFlow textLayerHeadUnder = PROPERTIES.getTextFlowForWhyWinHeadUnder(textHU);

        TextArea textLayerBody = PROPERTIES.getTextFlowForWhyWinBody(descriptionBody);

        group.getChildren().remove(SEVENTH_CHILDREN_OF_GROUP);
        group.getChildren().add(SEVENTH_CHILDREN_OF_GROUP, textLayerHead);

        group.getChildren().remove(EIGHTH_CHILDREN_OF_GROUP);
        group.getChildren().add(EIGHTH_CHILDREN_OF_GROUP, textLayerHeadUnder);

        group.getChildren().remove(NINTH_CHILDREN_OF_GROUP);
        group.getChildren().add(NINTH_CHILDREN_OF_GROUP, textLayerBody);

    }

    private void groupOnMouseEntered(final Group group, double scale) {
        group.setOnMouseEntered(event -> {
            ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), group);
            transition.setToX(scale);
            transition.setToY(scale);
            transition.play();
        });
    }

    private void groupOnMouthExited(final Group group, double scale) {
        group.setOnMouseExited(event -> {
            ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), group);
            transition.setToX(scale);
            transition.setToY(scale);
            transition.play();
        });
    }
}
