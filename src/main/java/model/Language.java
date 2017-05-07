package model;

/**
 * Created by Solyk on 03.05.2017.
 */
public interface Language {

    String getCVNodeDescription();
    String getDownloadNodeDescription();
    String getChatNodeDescription();
    String getLanguageNodeDescription();
    String getOldJobNodeDescription();
    String getMoreNodeDescription();
    String getWhyNodeDescription();

    String getCVWinHead();
    String getCVWinHeadUnder();
    String getCVWinBody();
    String getCVWinFooter();

    String getWhyWinHead();
    String getWhyWinHeadUnder();
    String getWhyWinBody();

    String getMoreWinHead();
    String getMoreWinHeadUnder();
    String getMoreWinBody();

    String getOldJobWinHead();
    String getOldJobWinHeadUnder();
    String getOldJobWinBody();

    String getChatWinHead();
    String getChatWinHeadUnder();
    String getChatWinButton();
    String getChatInitTextOfInput();
    String getChatInitTextOfOutput();

    String getSaveOk();
    String getSaveError();

}
