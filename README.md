# Campus Event Management System

A Java Servlet + JSP web application for managing university campus events, built for Jordan University of Science and Technology (JUST).

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 11 |
| Server | Apache Tomcat 9 |
| Servlet API | javax.servlet 4.0 |
| View | JSP 2.3 + JSTL 1.2 |
| Build | Maven 3 (WAR) |
| Database | PostgreSQL (Supabase) via JDBC |
| Password | jBCrypt 0.4 |
| UI | Bootstrap 5.3.3 (CDN) |

## Design Patterns

- **Factory Method** — `EventFactoryProvider` selects the correct factory (Workshop, Seminar, ClubSocial, SportsActivity) when creating events
- **Strategy Pattern** — `EventSearchContext` chains 6 filter strategies (title, type, category, date, department, availability)
- **MVC** — Servlets as controllers, JSP as views, DAO classes as model

## Prerequisites

- Java 11 or higher
- Maven 3.x
- Apache Tomcat 9.x

## Database Setup

This project connects to a hosted Supabase PostgreSQL database. The connection is pre-configured in `src/main/resources/db.properties`.

If you want to use your own database, run the migration files in order:

```
WEP_v1/supabase/migrations/001_schema.sql   — tables and enums
WEP_v1/supabase/migrations/002_functions.sql — stored procedures
WEP_v1/supabase/migrations/003_seed_admin.sql — admin account
```

Then update `src/main/resources/db.properties`:

```properties
db.url=jdbc:postgresql://<host>:5432/<database>?sslmode=require
db.user=<user>
db.password=<password>
```

## Build

```bash
mvn package -DskipTests
```

This produces `target/campus-events.war`.

## Deploy to Tomcat

1. Copy the WAR to your Tomcat `webapps/` directory:

```bash
cp target/campus-events.war /path/to/apache-tomcat-9/webapps/
```

2. Start Tomcat:

```bash
# Make scripts executable (first time only)
chmod +x /path/to/apache-tomcat-9/bin/*.sh

# Start
/path/to/apache-tomcat-9/bin/startup.sh
```

3. Open the app:

```
http://localhost:8080/campus-events/
```

Tomcat hot-deploys automatically when the WAR is replaced — no restart needed.

## Default Accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@campus.edu | Admin@123 |
| Student | Register at `/auth/register` | — |
| Organizer | Register at `/auth/register` | — |

## Project Structure

```
src/main/java/com/campusevents/
  controller/     — Servlets (AuthServlet, EventServlet, OrganizerServlet, AdminServlet, ...)
  dao/            — JDBC data access (UserDAO, EventDAO, ReservationDAO, ...)
  model/          — POJOs (User, Event, Reservation, ...)
  pattern/
    factory/      — Factory Method Pattern (EventFactory, EventFactoryProvider, ...)
    strategy/     — Strategy Pattern (SearchStrategy, EventSearchContext, ...)
  filter/         — AuthFilter (session guard + role enforcement)
  util/           — DBConnection, PasswordUtil

src/main/webapp/
  WEB-INF/
    jsp/          — JSP views (auth/, student/, organizer/, admin/, common/)
    web.xml       — Servlet and filter mappings
  css/, js/       — Static assets
```

## Features

### Student
- Browse and filter events (6 search criteria)
- Reserve and cancel tickets
- View my reservations
- Rate completed events (1–5 stars + review)

### Organizer
- Dashboard with event list and stats
- Create events (Factory Method selects type-specific factory)
- Edit and manage own events
- View attendees with university number
- Mark attendance (present / absent)

### Admin
- Manage all users (block / unblock / delete)
- Manage all events (change status / delete)
- Manage event categories
- Cannot block or delete own account
- Events auto-expire when their date/time passes

## Logging

Runtime errors are logged to Tomcat's `logs/localhost.YYYY-MM-DD.log` (not `catalina.out`).
