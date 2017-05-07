package model;

/**
 * Created by Solyk on 03.05.2017.
 */
public class RULanguage implements Language {
    @Override
    public String getCVNodeDescription() {
        return "Резюме";
    }

    @Override
    public String getDownloadNodeDescription() {
        return "Сохранить";
    }

    @Override
    public String getChatNodeDescription() {
        return "Чат";
    }

    @Override
    public String getLanguageNodeDescription() {
        return "Язык EN";
    }

    @Override
    public String getOldJobNodeDescription() {
        return "Прошлый Опыт";
    }

    @Override
    public String getMoreNodeDescription() {
        return "Больше Инф";
    }

    @Override
    public String getWhyNodeDescription() {
        return "Зачем Это";
    }

    @Override
    public String getCVWinHead() {
        return "dmitriy_lyashenko_cv";
    }

    @Override
    public String getCVWinHeadUnder() {
        return "Java Разработчик";
    }

    @Override
    public String getCVWinBody() {
        return RUFromFile.cv;
    }

    @Override
    public String getCVWinFooter() {
        return RUFromFile.cvInfo;
    }

    @Override
    public String getWhyWinHead() {
        return "Зачем Это";
    }

    @Override
    public String getWhyWinHeadUnder() {
        return "А что мне еще сделать?";
    }

    @Override
    public String getWhyWinBody() {
        return RUFromFile.why;
    }

    @Override
    public String getMoreWinHead() {
        return "Болтше Инфы";
    }

    @Override
    public String getMoreWinHeadUnder() {
        return "Кто я, чем я занимась";
    }

    @Override
    public String getMoreWinBody() {
        return RUFromFile.more;
    }

    @Override
    public String getOldJobWinHead() {
        return "Прежний Опыт";
    }

    @Override
    public String getOldJobWinHeadUnder() {
        return "Чем я занимался до Java";
    }

    @Override
    public String getOldJobWinBody() {
        return RUFromFile.oldJob;
    }

    @Override
    public String getChatWinHead() {
        return "Чат";
    }

    @Override
    public String getChatWinHeadUnder() {
        return "Давайте пообщаемся если я онлайн)";
    }

    @Override
    public String getChatWinButton() {
        return "Отправить";
    }

    @Override
    public String getChatInitTextOfInput() {
        return "Поле для ввода";
    }

    @Override
    public String getChatInitTextOfOutput() {
        return "Если кнопка Отправить зеленого цвета - я онлайн, напишите мне;)";
    }

    @Override
    public String getSaveOk() {
        return "Резюме успешно сохранено в папку в которую был сохранен запускаемый файл";
    }

    @Override
    public String getSaveError() {
        return "Ошибка! Резюме не сохранено";
    }
}
