# Redis Stream com Spring Boot para segurança de persistência no PostgresSQL

---
Esse projeto é uma versão inicial de um sistema que usa Redis Streams com Spring Boot pra fazer a persistência assíncrona de dados. A ideia é que, em vez de salvar direto no banco, os dados vão pra uma fila no Redis e um worker fica responsável por processar isso em segundo plano. O consumo das mensagens é feito com um consumer group, e o worker só marca a mensagem como concluída (ACK) depois que salva tudo certinho no banco. Ainda é uma implementação incompleta não tem tratamento de erros mais robusto, nem retries automáticos ou verificação das mensagens que ficaram pendentes. Por enquanto, o objetivo é validar a estrutura e ver se a arquitetura se sustenta pra cenários maiores.

# Cola para o redis

XRANGE persistence-jobs - +
XPENDING persistence-jobs persistence-group
