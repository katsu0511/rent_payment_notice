package com.google.haradakatsuya190511;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.FollowEvent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

@SpringBootApplication
@LineMessageHandler
public class RentPaymentNoticeApplication {
    private final MessagingApiClient messagingApiClient;

    public static void main(String[] args) {
        SpringApplication.run(RentPaymentNoticeApplication.class, args);
    }

    public RentPaymentNoticeApplication(MessagingApiClient messagingApiClient) {
        this.messagingApiClient = messagingApiClient;
    }
    
    @EventMapping
    public List<Message> handleFollowEvent(FollowEvent event) {
        return List.of(new TextMessage("友達追加ありがとうございます！"), new TextMessage("通知してほしいタイミングを「月初め」、「中旬」、「月末」のどれかを選んで送信してください！"));
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent event) {
    	System.out.println("event: " + event);
        final String originalMessageText = ((TextMessageContent) event.message()).text();
        List<Message> message = new ArrayList<Message>();
        if (originalMessageText.equals("うん")) {
        	message = List.of(new TextMessage("こ"), new TextMessage("したい"));
        } else if (originalMessageText.contains("おやすみ")) {
        	message = List.of(new TextMessage("おやゆび〜"));
        } else if (originalMessageText.contains("うんこ")) {
        	message = List.of(new TextMessage("きちゃないねん"));
        } else if (originalMessageText.contains("好き") || originalMessageText.contains("愛してる")) {
        	message = List.of(new TextMessage("わいもやで"));
        } else if (originalMessageText.contains("おはよう") || originalMessageText.contains("こんにちは")) {
        	message = List.of(new TextMessage("ナマステ"));
        } else if (originalMessageText.contains("バイバイ") || originalMessageText.contains("ばいばい") || originalMessageText.contains("またね")) {
        	message = List.of(new TextMessage("ほなね！"));
        } else {
        	message = List.of(new TextMessage("やかましいわ"));
        }
        messagingApiClient.replyMessage(
            new ReplyMessageRequest.Builder(event.replyToken(), message).build()
        );
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
