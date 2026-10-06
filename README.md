# SOP_Coursework

Микросервисная система управления салоном красоты на Java / Spring Boot.

## О проекте

Проект по сервис-ориентированному проектированию: 6 микросервисов, взаимодействие через REST и RabbitMQ, мониторинг через Prometheus и Zipkin.

## Сервисы

- **beauty-salon** — основной сервис (услуги, мастера, записи)
- **beauty-salon-api** — контракты API
- **analytics-service-beautySalon** — расчёт рейтинга услуг (gRPC)
- **audit-service-beautySalon** — аудит событий
- **notification-service-beautySalon** — WebSocket-уведомления
- **events-contract-beautySalon** — общие DTO событий

## Стек

- Java 21
- Spring Boot 3.2
- Spring Data JPA, Hibernate
- PostgreSQL
- RabbitMQ (topic и fanout exchange, DLQ, publisher confirms)
- gRPC
- WebSocket
- Docker, Docker Compose
- Prometheus, Grafana, Zipkin
- Maven (многомодульный проект)

## Что реализовано

- REST API на основе контракта, HATEOAS, GraphQL (DGS Framework)
- Асинхронное взаимодействие через RabbitMQ: события appointment.created, appointment.deleted, рейтинг услуг
- Отказоустойчивая обработка: publisher confirms, ручные ack, dead letter queue, идемпотентность
- gRPC-сервис аналитики
- WebSocket-уведомления в реальном времени
- Распределённая трассировка: Micrometer Tracing + Zipkin
- Мониторинг: Prometheus + Grafana
- Docker Compose для оркестрации всех сервисов

## Запуск

```bash
docker-compose up --build -d
```

Основной сервис: http://localhost:8080

RabbitMQ: http://localhost:15672

Prometheus: http://localhost:9090

Grafana: http://localhost:3000

Zipkin: http://localhost:9411
