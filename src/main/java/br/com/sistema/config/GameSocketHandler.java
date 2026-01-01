package br.com.sistema.config;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

public class GameSocketHandler extends TextWebSocketHandler {

	private final List<WebSocketSession> players = new ArrayList<>();
	private final Map<WebSocketSession, String> choices = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		players.add(session);
		send(session, "info", "Aguardando outro jogador...");

		if (players.size() == 2) {
			broadcast("start", "Jogadores conectados! Escolham pedra, papel ou tesoura.");
		}
	}

	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		choices.put(session, message.getPayload());

		if (choices.size() == 2) {
			var p1 = choices.get(players.get(0));
			var p2 = choices.get(players.get(1));
			var result = getResult(p1, p2);

			broadcast("result", result);

			choices.clear();
			players.clear(); // reinicia
		}
	}

	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		players.remove(session);
		choices.remove(session);
	}

	
	private void send(WebSocketSession s, String type, String msg) throws Exception {
        String escapedMsg = msg.replace("\n", "\\n");

        String finalJsonString = "{"
            + "\"type\":\"" + type + "\","
            + "\"msg\":\"" + escapedMsg + "\""
            + "}";

        System.out.println("DEBUG - Backend enviando JSON: " + finalJsonString);

        // 4. Enviar a mensagem WebSocket
        s.sendMessage(new TextMessage(finalJsonString));
    }

	
	private void broadcast(String type, String msg) throws Exception {
		for (var s : players)
			send(s, type, msg);
	}
	

	private String getResult(String c1, String c2) {
        if (c1.equals(c2))
            return "Empate! Ambos escolheram " + c1;
        if ((c1.equals("pedra") && c2.equals("tesoura")) || (c1.equals("tesoura") && c2.equals("papel"))
                || (c1.equals("papel") && c2.equals("pedra"))) {
            return "Jogador 1 venceu! \n(" + c1 + " vence " + c2 + ")";
        }
        return "Jogador 2 venceu! \n(" + c2 + " vence " + c1 + ")";
    }
}
