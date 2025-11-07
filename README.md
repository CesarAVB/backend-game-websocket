# Backend Pedra, Papel e Tesoura

Servidor WebSocket para jogo multiplayer de Pedra, Papel e Tesoura. Gerencia conexões de jogadores, recebe escolhas e determina vencedores em tempo real.

## O que o projeto faz

- Aceita conexões WebSocket de jogadores
- Aguarda até que 2 jogadores se conectem
- Recebe escolhas dos jogadores (pedra, papel ou tesoura)
- Calcula o resultado da partida
- Envia resultado para ambos os jogadores
- Reinicia jogo automaticamente após cada rodada

## Tecnologias

- Java 21
- Spring Boot 3.5.7
- Spring WebSocket
- Maven 3.6+

## Requisitos

**Software necessário:**
- Java 21 ou superior
- Maven 3.6 ou superior

**Opcional:**
- Cliente frontend (Angular, React, ou qualquer que suporte WebSocket)

## Fluxo de jogo

```
1. Jogador 1 conecta → Recebe: "Aguardando outro jogador..."
2. Jogador 2 conecta → Ambos recebem: "Jogadores conectados!"
3. Jogador 1 envia: "pedra"
4. Jogador 2 envia: "tesoura"
5. Ambos recebem: "Jogador 1 venceu! (pedra vence tesoura)"
6. Sessões encerradas (jogo reinicia)
```

## Regras do jogo

- Pedra vence Tesoura
- Tesoura vence Papel
- Papel vence Pedra
- Escolhas iguais resultam em empate

## Estrutura do projeto

```
src/
└── main/
    └── java/
        └── br/com/sistema/
            ├── config/
            │   ├── GameSocketHandler.java   # Lógica do jogo
            │   └── WebSocketConfig.java     # Configuração WebSocket
            └── ProjetoWebsocketApplication.java
```

## Componentes principais

### GameSocketHandler

Gerencia toda lógica do jogo:
- `afterConnectionEstablished()`: Adiciona jogador à lista
- `handleTextMessage()`: Processa escolhas
- `afterConnectionClosed()`: Remove jogador desconectado
- `getResult()`: Calcula vencedor

### WebSocketConfig

Configura endpoint WebSocket:
- Endpoint: `/game`
- Permite todas as origens (CORS: `*`)

## Configurações

### Porta do servidor

Edite `src/main/resources/application.properties`:

```properties
server.port=8080
```

### CORS

Por padrão, aceita conexões de qualquer origem (`*`).

Para restringir, edite `WebSocketConfig.java`:

```java
.setAllowedOrigins("http://localhost:4200")
```

## Limitações conhecidas

- Aceita apenas 2 jogadores por vez
- Sessões são encerradas após cada partida
- Não mantém histórico de jogos
- Não identifica jogadores (sem autenticação)
- Não persiste dados em banco

## Melhorias futuras

### Funcionalidades de jogo
- Adicionar salas de jogo (múltiplas partidas simultâneas)
- Implementar sistema de placar por sessão
- Criar modo melhor de 3 rodadas (best of 3)
- Adicionar modo melhor de 5 rodadas (best of 5)
- Implementar timeout para jogadas (15-30 segundos)
- Adicionar modo espectador
- Criar ranking global de jogadores
- Implementar sistema de conquistas/badges
- Adicionar modo torneio (eliminatórias)
- Criar variação do jogo (Pedra, Papel, Tesoura, Lagarto, Spock)

### Sistema de usuários
- Adicionar autenticação de jogadores
- Criar sistema de registro/login
- Implementar perfis de usuários
- Adicionar avatares personalizáveis
- Criar sistema de amigos
- Implementar chat entre jogadores
- Adicionar histórico pessoal de partidas

### Persistência e dados
- Persistir histórico de partidas em banco
- Salvar estatísticas de jogadores
- Criar relatórios de desempenho
- Implementar cache Redis para sessões
- Adicionar backup automático de dados
- Criar API REST para consulta de dados históricos

### Interface e experiência
- Criar dashboard administrativo
- Adicionar painel de estatísticas em tempo real
- Implementar notificações push
- Criar sistema de convites para partidas
- Adicionar sons e efeitos visuais (via mensagens)
- Implementar tema escuro/claro

### Qualidade e manutenção
- Adicionar testes unitários (JUnit)
- Criar testes de integração
- Implementar testes de carga
- Adicionar cobertura de código (JaCoCo)
- Criar documentação Swagger/OpenAPI
- Implementar monitoramento com Actuator
- Adicionar logs estruturados (Logback)

### Infraestrutura
- Dockerizar a aplicação
- Criar docker-compose para ambiente completo
- Implementar CI/CD (GitHub Actions)
- Adicionar health check endpoint
- Configurar balanceamento de carga
- Implementar failover automático

### Segurança
- Implementar rate limiting por IP
- Adicionar proteção contra DDoS
- Criar sistema de ban/kick de jogadores
- Implementar validação de entrada rigorosa
- Adicionar logs de auditoria
- Configurar CSP (Content Security Policy)

### Reconexão e robustez
- Implementar reconexão automática de clientes
- Adicionar recuperação de estado após queda
- Criar sistema de heartbeat/ping-pong
- Implementar fila de espera para jogadores
- Adicionar modo offline (contra IA)

## Integrando com frontend

Este backend foi projetado para funcionar com o frontend Angular disponível em:
```
[Frontend Angular](https://github.com/CesarAVB/angular-game-websocket)
```

Para conectar:
1. Execute este backend na porta 8080
2. Execute o frontend na porta 4200
3. O frontend conectará automaticamente


## Notas importantes

- WebSocket mantém conexão persistente (diferente de HTTP)
- Servidor reinicia jogo após cada partida
- Escolhas devem ser em minúsculas (`pedra`, não `PEDRA`)
- Mensagens de resultado usam `\n` para quebras de linha
- CORS está configurado para aceitar qualquer origem (apenas para desenvolvimento)
