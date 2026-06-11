# StudySync (Spring Boot Upgrade)

A web-based platform that helps university students find and organize study groups for their courses. Students can register, view their courses, post study sessions, and join sessions created by other students.

> **Note on this Repository:** This project is a solo, modified version of a database class group project. I am rewriting the entire backend infrastructure from scratch using **Java and Spring Boot** to expand my technical proficiency, transition away from Python frameworks, and practice enterprise-level backend architecture, security, and type safety.

## Tech Stack
 
- **Backend** — Java 26 / Spring Boot 4.x (Spring Data JPA + Hibernate) [Upgraded from Flask]
- **Frontend** — React (Node 20)
- **Database** — PostgreSQL 16
- **Containerization** — Docker + Docker Compose

## Prerequisites
 
You need **Git**, **Docker Desktop**, and a **Java Development Kit (JDK 26)** installed before anything else.
 
### Install Git
- **Windows / macOS**: https://git-scm.com/downloads
- **Linux**: `sudo apt install git`
 
### Install Docker Desktop
- **Windows / macOS**: https://www.docker.com/products/docker-desktop
- **Linux**: https://docs.docker.com/engine/install/

### Install JDK 26
- **All OS**: Download from [Eclipse Temurin (Adoptium)](https://adoptium.net/) or use your IDE (e.g., IntelliJ IDEA) to detect and install it automatically.

---

## Getting Started

### 1. Clone the repository
```bash
git clone <your-repository-ssh-or-https-link>
cd backend