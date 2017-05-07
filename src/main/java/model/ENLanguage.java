package model;

/**
 * Created by Solyk on 03.05.2017.
 */
public class ENLanguage implements Language{

    @Override
    public String getCVNodeDescription() {
        return "CV";
    }

    @Override
    public String getDownloadNodeDescription() {
        return "Save";
    }

    @Override
    public String getChatNodeDescription() {
        return "Chat";
    }

    @Override
    public String getLanguageNodeDescription() {
        return "Language RU";
    }

    @Override
    public String getOldJobNodeDescription() {
        return "Old Job";
    }

    @Override
    public String getMoreNodeDescription() {
        return "More Info";
    }

    @Override
    public String getWhyNodeDescription() {
        return "Why I Did It";
    }

    @Override
    public String getCVWinHead() {
        return "dmitriy_lyashenko_cv";
    }

    @Override
    public String getCVWinHeadUnder() {
        return "Java Developer";
    }

    @Override
    public String getCVWinBody() {
        return ENFromFile.cv;
    }

    @Override
    public String getCVWinFooter() {
        return ENFromFile.cvInfo;
    }

    @Override
    public String getWhyWinHead() {
        return "Why I Did It";
    }

    @Override
    public String getWhyWinHeadUnder() {
        return "What else can I do?";
    }

    @Override
    public String getWhyWinBody() {
        return ENFromFile.why;
    }

    @Override
    public String getMoreWinHead() {
        return "More Info";
    }

    @Override
    public String getMoreWinHeadUnder() {
        return "Who I Am";
    }

    @Override
    public String getMoreWinBody() {
        return ENFromFile.more;
    }

    @Override
    public String getOldJobWinHead() {
        return "Old Experience";
    }

    @Override
    public String getOldJobWinHeadUnder() {
        return "What I do before Java";
    }

    @Override
    public String getOldJobWinBody() {
        return ENFromFile.oldJob;
    }

    @Override
    public String getChatWinHead() {
        return "Chat";
    }

    @Override
    public String getChatWinHeadUnder() {
        return "Get Chat With Me, if i online)";
    }

    @Override
    public String getChatWinButton() {
        return "Send";
    }

    @Override
    public String getChatInitTextOfInput() {
        return "Input field";
    }

    @Override
    public String getChatInitTextOfOutput() {
        return "If Send button is green - I'm online, write to me;)";
    }

    @Override
    public String getSaveOk() {
        return "Resume successfully saved to the folder in which the executable was saved";
    }

    @Override
    public String getSaveError() {
        return "Error! Resume was not saved( ";
    }
}
