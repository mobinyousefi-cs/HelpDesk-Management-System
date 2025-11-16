# HelpDesk Management System

A simple, clean **Help Desk / Ticket Management** web application built with:

- Java (Servlets, JSP, MVC)
- MySQL
- Maven
- Deployed on a Servlet container (e.g., Apache Tomcat)

Users can log in, raise support tickets (complaints), and track their status via a ticket-based workflow. Support agents can view all tickets and update their status.

> Author: **Mobin Yousefi**  
> GitHub: [github.com/mobinyousefi-cs](https://github.com/mobinyousefi-cs)

---

## ✨ Features

- User authentication (Customer / Agent roles)
- Create new tickets with subject, description, and priority
- View list of tickets:
  - Customers: only their own tickets
  - Agents: all tickets
- Ticket detail view with metadata
- Agents can update ticket status (OPEN → IN_PROGRESS → RESOLVED → CLOSED)
- Clean layered architecture:
  - **Model** (POJOs, enums)
  - **DAO** (JDBC + MySQL)
  - **Service** (business logic)
  - **Servlets + JSP** (MVC web layer)

---

## 🧱 Tech Stack

- **Language:** Java 17  
- **Build:** Maven  
- **Web:** Jakarta Servlets 5, JSP, JSTL  
- **Database:** MySQL 8.x  
- **App server:** Apache Tomcat 10.x  

---

## 📁 Project Structure

```text
helpdesk-management-system/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/
│  │  │     └─ mobinyousefi/
│  │  │        └─ helpdesk/
│  │  │           ├─ config/
│  │  │           │  └─ DBConnectionManager.java
│  │  │           ├─ model/
│  │  │           │  ├─ User.java
│  │  │           │  ├─ Ticket.java
│  │  │           │  ├─ TicketStatus.java
│  │  │           │  └─ TicketPriority.java
│  │  │           ├─ dao/
│  │  │           │  ├─ UserDao.java
│  │  │           │  └─ TicketDao.java
│  │  │           ├─ service/
│  │  │           │  ├─ AuthService.java
│  │  │           │  └─ TicketService.java
│  │  │           └─ web/
│  │  │              ├─ LoginServlet.java
│  │  │              ├─ LogoutServlet.java
│  │  │              ├─ TicketCreateServlet.java
│  │  │              ├─ TicketListServlet.java
│  │  │              ├─ TicketDetailServlet.java
│  │  │              └─ TicketUpdateStatusServlet.java
│  │  └─ webapp/
│  │     ├─ index.jsp
│  │     ├─ resources/
│  │     │  └─ css/
│  │     │     └─ styles.css
│  │     └─ WEB-INF/
│  │        ├─ web.xml
│  │        └─ views/
│  │           ├─ includes/
│  │           │  ├─ header.jspf
│  │           │  └─ footer.jspf
│  │           ├─ login.jsp
│  │           ├─ dashboard.jsp
│  │           ├─ tickets.jsp
│  │           ├─ ticket_detail.jsp
│  │           └─ ticket_new.jsp
```
## ⚙️ Configuration

### 1. Database (MySQL)

Create the database and tables using the **full schema** below:

- Database: `helpdesk_db`
- Tables:
  - `users`
  - `tickets`

👉 See **Full MySQL schema** section.

---

### 2. JDBC Settings

Database connection parameters are configured via `web.xml` as context parameters:

```xml
<context-param>
    <param-name>JDBC_URL</param-name>
    <param-value>jdbc:mysql://localhost:3306/helpdesk_db?useSSL=false&amp;serverTimezone=UTC</param-value>
</context-param>
<context-param>
    <param-name>JDBC_USER</param-name>
    <param-value>root</param-value>
</context-param>
<context-param>
    <param-name>JDBC_PASSWORD</param-name>
    <param-value>your_password_here</param-value>
</context-param>
```

## 🚀 Getting Started
### Requirements:
- JDK 17+
- Maven 3.8+
- MySQL 8.x
- Apache Tomcat 10.x

### Steps:
1. Clone the repo
```bash
git clone https://github.com/mobinyousefi-cs/helpdesk-management-system.git
cd helpdesk-management-system
```

2. Create the database using the schema below.
3. Edit DB credentials in WEB-INF/web.xml.
4. Build
```bash
mvn clean package
```
5. Deploy
- Copy the WAR to Tomcat’s webapps/ folder.
- Open:
```bash
http://localhost:8080/helpdesk-management-system
```

## 👤 Demo User Accounts
### Agent (full access)
- Username: agent1
- Password: admin123

### Customer
Username: customer1
Password: customer123