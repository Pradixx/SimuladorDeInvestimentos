# Simulador de Investimentos 📈 (Em Desenvolvimento Ativo)

Este é o meu **maior projeto pessoal** e um laboratório contínuo de engenharia de software. O objetivo é criar uma plataforma completa para simulação e análise de investimentos, integrando backend robusto, frontend moderno e inteligência artificial.

⚠️ **Aviso Importante:** Este projeto está em **fase inicial de desenvolvimento**. O código atual representa os primeiros passos de uma arquitetura complexa e passará por **grandes refatorações e mudanças estruturais** nos próximos meses. Ele não deve ser considerado um produto finalizado, mas sim um registro da minha evolução técnica.

---

## 🏗️ Arquitetura do Sistema (Work in Progress)

O projeto está sendo estruturado como um ecossistema de serviços integrados:

- **`Simulador-Investimentos-Back-End`**: Núcleo de processamento e lógica de negócios dos investimentos.
- **`Simulador-Investimentos-Login-Back-End`**: Serviço dedicado à autenticação e gestão de usuários (Spring Security).
- **`Simulador-Investimentos-Front-End`**: Interface de usuário desenvolvida em **Angular**, focada em proporcionar uma experiência fluida e intuitiva.
- **`Simulador-Investimentos-Agente-IA`**: Módulo experimental para integração de inteligência artificial na análise de dados financeiros.
- **`Docker Compose`**: Orquestração de todo o ambiente para facilitar o desenvolvimento e deploy.

---

## 🛠️ Tecnologias Utilizadas (Até o momento)

- **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA.
- **Frontend**: Angular, TypeScript, CSS.
- **Infraestrutura**: Docker, Docker Compose.
- **Banco de Dados**: MySQL (via Docker).
- **IA**: Integrações experimentais com agentes inteligentes.

---

## 🎯 Visão de Futuro e Próximos Passos

Este projeto é o meu principal foco de estudo para os próximos meses. O roadmap inclui:

1.  **Refatoração Completa**: Melhoria da arquitetura de microserviços e padrões de projeto.
2.  **Expansão da IA**: Tornar o agente de IA mais integrado e funcional para auxiliar nas simulações.
3.  **Dashboard Financeiro**: Evolução do frontend Angular com gráficos e indicadores em tempo real.
4.  **Cobertura de Testes**: Implementação rigorosa de testes unitários (Mockito) e de integração.
5.  **CI/CD**: Automatização do fluxo de build e deploy.

---

## 🚀 Como Acompanhar

Como o projeto está em constante mudança, a estrutura de execução pode variar. Atualmente, a base para rodar o ambiente completo é via Docker:

```bash
docker-compose up -d
```

---

## 🧠 Sobre o Projeto

O **Simulador de Investimentos** nasceu do desejo de unir duas paixões: tecnologia e finanças. Ele é o reflexo do meu compromisso com o aprendizado contínuo, onde aplico cada novo conceito que domino em arquitetura, segurança e escalabilidade.

---
*Desenvolvido por [Diego Prado](https://www.linkedin.com/in/diego-prado-dev/) — Em constante evolução.*
