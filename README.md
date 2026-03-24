ENGLISH BELOW 
# Pubito – aplikacja do wyszukiwania pubów i barów

Pubito to aplikacja mobilna umożliwiająca wyszukiwanie lokalnych pubów i barów, przeglądanie opinii oraz interakcję z innymi użytkownikami.

## Status projektu

Projekt w trakcie rozwoju.

---

## Funkcjonalności

### Wyszukiwanie barów
- filtrowanie po cenie, ocenie, promocjach i lokalizacji
- sortowanie wyników (np. najtańsze, najlepiej oceniane)
- podgląd szczegółów baru wraz z lokalizacją na mapie

### Zarządzanie barami (admin)
- dodawanie nowych barów
- edycja danych (opis, kontakt, kategoria)
- usuwanie barów
- przeglądanie listy lokali

### Opinie użytkowników
- dodawanie opinii (ocena 1–5 + komentarz)
- edycja i usuwanie własnych opinii
- automatyczne liczenie średniej oceny baru

### Czat
- czat przypisany do konkretnego baru
- komunikacja w czasie rzeczywistym

### Ruletka zakupowa
- losowanie napoju z menu baru
- możliwość ponownego losowania

### System użytkowników
- rejestracja i logowanie (JWT)
- role użytkowników
- ranking użytkowników na podstawie liczby opinii

---

## Technologie

Backend:
- Java
- Spring Boot

Baza danych:
- MySQL

---

## Architektura

Projekt składa się z:
- backendu (REST API)
- frontendu (aplikacja mobilna)
- bazy danych (relacyjnej)

Główne encje:
- Users
- Bars
- Reviews
- Menus
- Addresses
- Roles

---

## Przykładowe funkcjonalności backendowe

- filtrowanie barów po lokalizacji i ocenie
- ranking użytkowników według liczby opinii
- wyszukiwanie „trendy” barów
- analiza menu (np. najtańsze produkty)

---

## Wymagania niefunkcjonalne

- hasła przechowywane w formie zaszyfrowanej (BCrypt)
- obsługa dużej liczby użytkowników (ok. 10 000+)
- walidacja danych wejściowych
- spójność danych


---------------ENGLISH---------------

# Pubito – pub and bar discovery application

Pubito is a mobile application that allows users to search for local pubs and bars, browse reviews, and interact with other users.

## Project status

Project in progress.

---

## Features

### Bar search
- filtering by price, rating, promotions, and location
- sorting results (e.g. cheapest, highest rated)
- viewing bar details along with location on the map

### Bar management (admin)
- adding new bars
- editing data (description, contact, category)
- deleting bars
- browsing the list of venues

### User reviews
- adding reviews (rating 1–5 + comment)
- editing and deleting own reviews
- automatic calculation of the average bar rating

### Chat
- chat assigned to a specific bar
- real-time communication

### Drink roulette
- drawing a drink from the bar menu
- option to redraw

### User system
- registration and login (JWT)
- user roles
- user ranking based on the number of reviews

---

## Technologies

Backend:
- Java
- Spring Boot

Database:
- MySQL

---

## Architecture

The project consists of:
- backend (REST API)
- frontend (mobile application)
- database (relational)

Main entities:
- Users
- Bars
- Reviews
- Menus
- Addresses
- Roles

---

## Example backend functionalities

- filtering bars by location and rating
- user ranking based on the number of reviews
- searching for “trending” bars
- menu analysis (e.g. cheapest products)

---

## Non-functional requirements

- passwords stored in encrypted form (BCrypt)
- support for a large number of users (approx. 10,000+)
- input data validation
- data consistency

---
