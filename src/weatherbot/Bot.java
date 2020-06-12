/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 *
 * @author Mikhail
 */
public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            System.out.println("Bot starting...");
            botapi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        //return "1248209264:AAEFthUkXg2Eg3wV0rq21giM7k3MzZGxgbY";
        // 1248209264:AAEFthUkXg2Eg3wV0rq21giM7k3MzZGxgbY
        return "1248209264:AAEFthUkXg2Eg3wVOrq21giM7k3MzZGxgbY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model  = new Model();
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "How can i help?");
                    break;
                //execute(sendMessage.setText(message.getText() + "How can i help?"));
                case "/settings":
                    sendMsg(message, "What are we going to customize?");
                    // execute(sendMessage.setText(message.getText() + "What are we going to customize?"));
                    break;
                default:
                    try{
                        String weatherInfo = Weather.getWeather(message.getText(), model);
                        System.out.println(weatherInfo);
                        sendMsg(message,weatherInfo);
                        
                    }
                    catch(IOException e){
                        sendMsg(message,"City not found!");
                    }
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "TheBestInTheWorldWeatherBot";
    }

    public void sendMsg(Message message, String text) {
        SendMessage s = new SendMessage();
        s.enableMarkdown(true);
        s.setChatId(message.getChatId().toString()); // Р‘РѕС‚Сѓ РјРѕР¶РµС‚ РїРёСЃР°С‚СЊ РЅРµ РѕРґРёРЅ С‡РµР»РѕРІРµРє, Рё РїРѕСЌС‚РѕРјСѓ С‡С‚РѕР±С‹ РѕС‚РїСЂР°РІРёС‚СЊ СЃРѕРѕР±С‰РµРЅРёРµ, РіСЂСѓР±Рѕ РіРѕРІРѕСЂСЏ РЅСѓР¶РЅРѕ СѓР·РЅР°С‚СЊ РєСѓРґР° РµРіРѕ РѕС‚РїСЂР°РІР»СЏС‚СЊ
        //s.setReplyToMessageId(message.getMessageId());
        s.setText(text);
        try { //Р§С‚РѕР±С‹ РЅРµ РєСЂР°С€РЅСѓР»Р°СЃСЊ РїСЂРѕРіСЂР°РјРјР° РїСЂРё РІС‹Р»РµС‚Рµ Exception 
            setButtons(s);
            sendMessage(s);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyBoardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyBoardMarkup);
        replyKeyBoardMarkup.setSelective(Boolean.TRUE);
        replyKeyBoardMarkup.setResizeKeyboard(Boolean.TRUE);
        replyKeyBoardMarkup.setOneTimeKeyboard(Boolean.FALSE);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyBoardMarkup.setKeyboard(keyboardRowList);
    }

}
