-- ============================================================================
-- HelpDesk Management System - Full MySQL Schema
-- Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
-- Tested on: MySQL 8.x
-- ============================================================================

-- 1) Create database
CREATE DATABASE IF NOT EXISTS helpdesk_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE helpdesk_db;

-- 2) Users table
--    Stores both customers and agents.
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    username        VARCHAR(50) NOT NULL UNIQUE,
    password_hash   CHAR(64) NOT NULL,    -- SHA-256 hex string
    full_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(120),
    role            VARCHAR(20) NOT NULL, -- e.g. 'CUSTOMER', 'AGENT'
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                  ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT chk_users_role
        CHECK (role IN ('CUSTOMER', 'AGENT'))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Useful index for logins
CREATE INDEX idx_users_username ON users (username);

-- 3) Tickets table
DROP TABLE IF EXISTS tickets;

CREATE TABLE tickets (
    id                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    subject              VARCHAR(255) NOT NULL,
    description          TEXT NOT NULL,
    status               VARCHAR(50) NOT NULL,   -- 'OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED'
    priority             VARCHAR(50) NOT NULL,   -- 'LOW', 'MEDIUM', 'HIGH', 'CRITICAL'
    created_by_user_id   BIGINT UNSIGNED NOT NULL,
    assigned_to_user_id  BIGINT UNSIGNED NULL,
    created_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_tickets_created_by
        FOREIGN KEY (created_by_user_id)
        REFERENCES users (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_tickets_assigned_to
        FOREIGN KEY (assigned_to_user_id)
        REFERENCES users (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT chk_tickets_status
        CHECK (status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),

    CONSTRAINT chk_tickets_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Indexes for common queries
CREATE INDEX idx_tickets_created_by ON tickets (created_by_user_id);
CREATE INDEX idx_tickets_assigned_to ON tickets (assigned_to_user_id);
CREATE INDEX idx_tickets_status ON tickets (status);
CREATE INDEX idx_tickets_created_at ON tickets (created_at);

-- 4) Demo data
-- Passwords are SHA-256 hashes of:
--   agent1    -> password: admin123
--   customer1 -> password: customer123
--
-- Hashes generated with: AuthService.hashPassword or any SHA-256 tool.

INSERT INTO users (username, password_hash, full_name, email, role)
VALUES
(
    'agent1',
    '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', -- admin123
    'Agent One',
    'agent1@example.com',
    'AGENT'
),
(
    'customer1',
    'b041c0aeb35bb0fa4aa668ca5a920b590196fdaf9a00eb852c9b7f4d123cc6d6', -- customer123
    'Customer One',
    'customer1@example.com',
    'CUSTOMER'
);

-- Optional: insert a sample ticket
INSERT INTO tickets (subject, description, status, priority, created_by_user_id,
                     assigned_to_user_id, created_at, updated_at)
VALUES (
    'Sample ticket: Cannot login',
    'I am unable to login to the system. It says invalid credentials.',
    'OPEN',
    'MEDIUM',
    (SELECT id FROM users WHERE username = 'customer1'),
    (SELECT id FROM users WHERE username = 'agent1'),
    NOW(),
    NOW()
);
