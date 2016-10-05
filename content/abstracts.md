title=Abstracts
date=2016-01-30
type=page
status=published
~~~~~~


## Talks

###  Cloud Native Java

“It is not necessary to change. Survival is not mandatory.” -W. Edwards Deming

Work takes time to flow through an organization and ultimately be deployed to production where it captures value. It’s critical to reduce time-to-production. Software - for many organizations and industries - is a competitive advantage.

Organizations break their larger software ambitions into smaller, independently deployable, feature -centric batches of work - microservices. In order to reduce the round-trip between stations of work, organizations collapse or consolidate as much of them as possible and automate the rest; developers and operations beget “devops,” cloud-based services and platforms (like Cloud Foundry) automate operations work and break down the need for ITIL tickets and change management boards.

But velocity, for velocity’s sake, is dangerous. Microservices invite architectural complexity that few are prepared to address. In this talk, we’ll look at how high performance organizations like Ticketmaster, Alibaba, and Netflix make short work of that complexity with Spring Boot and Spring Cloud.


### The Bootiful Application

Alright, so maybe "bootiful" won't ever work, but I tried, and it was worth it too because you're reading this. Spring Boot, the new convention-over-configuration centric framework from the Spring team at Pivotal, marries Spring's flexibility with conventional, common sense defaults to make application development not just fly, but pleasant! Spring Boot aims to make address the common functional and non-functional requirements that gate quickly moving to production.

Join Spring developer advocate Josh Long for a look at what Spring Boot is, why it's turning heads, why you should consider it for your next application (REST, micro services, web, batch, big data, integration, whatever!) and how to get started.

### The Bootiful Microservice

We get it already! Microservices help you build smaller, singly-focused services, quicker. They scale out. They’re more agile because individual teams can deliver them at their own pace. They work well in the cloud because they’re smaller, and benefit from elastic, horizontal scaling. But what about the complexity? There’s a cost associated with adding services and coordinating the interactions between them. In this talk, we’ll look at Spring Cloud, which builds atop Spring Boot and the Netflix OSS stack, and see how it lets you easily integrate service-discovery, security, reliability patterns like the circuit breaker, and centralized and journaled property configuration (and more) to more quickly build microservices that scale.

Join Spring Developer Advocate Josh Long to find out what Netflix, Alibaba, Ticketmaster, and countless other organizations already know: Spring Cloud handles the non-functional requirements associated with adopting microservices to enable them to progress quickly.

### The Operationalized Application

We all talk about agile, but being agile means being shippable, and shippable specifically to production! Anyone who has ever read Michael Nygard's amazing tome, "Release It!" knows that code complete != production ready! In this talk we'll look at how Spring Boot provides solutions for things that will frustrate your attempts to move to production. We'll look at how to introduce smart, semantic metrics and logging, how to manage metric and log analysis and visualization in joined-up dashboards, how to manage processes in environments with init.d and system.d, and how to make services self describing and humane once they're deployed.

### Reactive Spring

Spring 5 is almost here! One of the most exciting introductions in this release is support for reactive programming, building on the Pivotal Reactor project to support message-driven, elastic, resilient and responsive services. Spring 5 integrates an MVC-like component model adapted to support reactive processing and a new type of web endpoint, functional reactive endpoints. In this talk, we'll look at the net-new Netty-based web runtime, how existing Servlet code can run on the new world, and how to integrate it with existing Spring-stack technologies.

## Workshops

### Cloud Native Java Workshop

In this workshop we'll look at how to build cloud-native Java applications. A cloud native application is one that is designed to fully exploit a cloud platform both in the application layer - where things decompose into microservices - and at the data layer where NoSQL offers better horizontal scaling and fitness for specific purpose.

We'll look at:
 - writing services and handling non-functional requirements like
metrics and logging with Spring Boot
 - scaling out safely and building fault-tolerant systems using Spring
Cloud and its support for distributed systems patterns like the
circuit breaker, service registration and discovery, and centralized
configuration management.
- offloading as much operational work as possible to the platform, Cloud Foundry
