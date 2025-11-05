package com.example.demo.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
/**
* WebSocket 設定類別
* 啟用 STOMP 通訊協定，並設定訊息代理與連線端點。
*/

@Configuration
@EnableWebSocketMessageBroker // 啟用 WebSocket 訊息代理功能，支援以 STOMP 協定傳送訊息
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
   /**
    * 設定訊息代理（Message Broker）
    * - 設定伺服器推播用的目的地前綴（/topic）
    * - 設定客戶端送出訊息的前綴（/app）
    */
   @Override
   public void configureMessageBroker(MessageBrokerRegistry config) {
       config.enableSimpleBroker("/topic"); // 啟用內建簡易代理器，處理訂閱 /topic 開頭的訊息
       config.setApplicationDestinationPrefixes("/app"); // 設定應用程式端點前綴，客戶端傳送訊息時需加上 /app
   }
   /**
    * 註冊 STOMP 連線端點
    * - 客戶端會透過這個端點連線到 WebSocket 伺服器
    * - 設定允許的來源網域，並啟用 SockJS 備援方案
    */
   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
       registry.addEndpoint("/ws-chat") // 定義客戶端連線使用的端點 URL
               .setAllowedOriginPatterns("http://192.168.1.*") // 允許的來源網域模式（適用內網 IP）
               .withSockJS() // 啟用 SockJS，支援瀏覽器不支援 WebSocket 時的回退機制
               .setHeartbeatTime(25000); // 設定心跳間隔時間為 25 秒（用來維持連線存活）
   }
  
   /**
    * 設定 WebSocket 傳輸層參數
    * - 發送超時時間
    * - 發送緩衝區大小
    */
   @Override
   public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
       registration.setSendTimeLimit(15 * 1000) // 設定單次訊息發送的最大時間限制為 15 秒
                  .setSendBufferSizeLimit(512 * 1024); // 設定訊息緩衝區最大為 512KB
   }
}



