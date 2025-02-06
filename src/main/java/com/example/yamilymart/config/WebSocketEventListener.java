package com.example.yamilymart.config;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.yamilymart.config.WebSocketEventListener;
import com.example.yamilymart.service.Message;
import com.example.yamilymart.service.MsgType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messageOperations;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if (Objects.nonNull(username)) {
			System.out.println("user disconnected: " + username);
			
			//chat
			
			messageOperations.convertAndSend("/topic/chat", Message.builder().type(MsgType.LEAVE).sender(username).build());
		}
	}
}