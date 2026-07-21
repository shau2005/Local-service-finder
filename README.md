# Local Service Finder and Booking Platform

A full-stack backend project built using **Spring Boot, MySQL, Docker, AWS EC2, and GitHub Actions CI/CD**.

This project is a mini version of platforms like Urban Company, where users can create local service requests, verified providers can send offers, users can accept offers, bookings are created, services are completed, and users can leave reviews.

---

## Project Overview

The Local Service Finder platform helps users find verified local service providers such as plumbers, electricians, cleaners, and other home service professionals.

The project focuses on:

- Real-world backend business logic
- Role-based workflow
- MySQL database design
- REST API development
- Docker containerization
- AWS EC2 deployment
- GitHub Actions CI/CD pipeline

---

## Problem Statement

In many local areas, users face difficulty finding reliable service providers. Providers also need a platform where they can receive service requests based on their category and city.

This project solves that problem by connecting users with verified service providers through a structured booking flow.

---

## User Roles

### 1. User

A user can:

- Register as a customer
- Create service requests
- View offers from providers
- Accept one offer
- Get a booking created
- Give review after service completion

### 2. Provider

A provider can:

- Register as a provider
- Create provider profile
- Select service category and city
- Send offers only after admin verification
- View assigned bookings

### 3. Admin

An admin can:

- Add service categories
- Update categories
- Activate or deactivate categories
- Verify providers
- View dashboard summary

---

## Main Features

- User registration
- Provider registration
- Admin role support
- Service category management
- Active/inactive category feature
- Provider profile creation
- Admin provider verification
- Service request creation
- Provider offer system
- Booking creation after offer acceptance
- Booking status update
- Review system
- Admin dashboard summary
- Dockerized backend and MySQL
- AWS EC2 deployment
- GitHub Actions CI/CD deployment

---

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation

### Database

- MySQL

### DevOps

- Docker
- Docker Compose
- AWS EC2
- GitHub Actions
- GitHub Secrets
- SSH deployment

### Testing Tool

- Postman

---

## Project Architecture

```text
Client / Postman / Future React Frontend
        |
        v
Spring Boot REST API
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
MySQL Database