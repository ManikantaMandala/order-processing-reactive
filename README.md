### Order processing System

***🔥 Problem Statement — Reactive Live Order Processing System***

**You are tasked with building a Reactive Order Processing application using Spring WebFlux.**

* 🎯 Core Requirements
* 1️⃣ Create REST Reactive APIs

## Implement the following non-blocking endpoints:

Method | Endpoint | Description
1. POST	| `/orders`	| Create a new order and return the saved order (Mono<Order>)
1. GET	| `/orders/{id}`	| Fetch an order if exists (Mono<Order>)
1. GET	| `/orders` |	Stream all orders as Server-Sent Events (Flux<Order>)

## 2️⃣ Reactive Validation

Reject and return meaningful error responses when:

```
Order amount ≤ 0
```

Product name is missing or blank
Use reactive exception handling (`onErrorResume` / `@ControllerAdvice`).

## 3️⃣ Simulated Reactive Workflow

### Every order goes through three async stages:

### Stage	Delay	Implementation

```
Payment Processing	    1s delay	Simulated via Mono.delay
Packaging	             2s delay	Simulated
Shipment	                      3s delay	Simulated
```

✔ Workflow must be reactive, without any blocking.
✔ Use flatMap, then, zip, concatMap, or expand, etc.

When all stages are done → update order status to COMPLETED.

## 4️⃣ Reactive Persistence

### Use either:

1. R2DBC (PostgreSQL recommended), OR

1. In-Memory Reactive Repository (for quicker prototype)

#### All DB operations must return:

```text
Mono<Order> or Flux<Order> → no blocking repositories allowed.
```

### 🔔 Bonus Challenges

#### Add any of these to level up:

##### Feature	What it tests: 
* SSE Status Updates	Hot streaming flux & backpressure
* Order Cancellation	Subscription cancellation + asynchronous state update
* Retry Strategy	retryWhen for failures in workflow
* Rate Limiting per Client	Reactor operators + WebFilter
* Real-time metrics	Reactor hooks + monitoring
* 🧪 Sample Test Scenario

##### 1️⃣ Client calls POST /orders:

```json
{
  "productName": "Laptop",
  "amount": 95000
}
```

###### Response:

```json
{
  "id": "abc123",
  "productName": "Laptop",
  "status": "RECEIVED"
}
```


2️⃣ Client subscribes to status stream:

```
GET /orders/stream/abc123
```

Example Stream Output:

```json
{"status":"PAYMENT_PROCESSING"}
{"status":"PACKAGING"}
{"status":"SHIPPED"}
{"status":"COMPLETED"}
```

🧠 Skills You Will Demonstrate:
* Skill	Why It Matters
* Spring WebFlux	Fully non-blocking request handling
* Project Reactor	Transforming async workflow
* Backpressure	Smooth streaming under load
* R2DBC	End-to-end reactive stack
* Error Handling	Real-world production behavior
* Testing	StepVerifier / WebTestClient
