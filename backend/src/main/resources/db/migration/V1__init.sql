-- users
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_at DATETIME(6) NOT NULL,
  email VARCHAR(254) NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  role ENUM('BUSINESS','INVESTOR') NOT NULL,
  token_version INT NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX uk_users_email ON users(email);

-- business_profile
CREATE TABLE business_profile (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  business_name VARCHAR(255),
  contact_email VARCHAR(255),
  contact_name VARCHAR(255),
  contact_phone VARCHAR(255),
  current_revenue DOUBLE,
  description VARCHAR(255),
  founded_date DATE,
  funding_goal DOUBLE,
  industry VARCHAR(255),
  is_published BIT,
  logo_url VARCHAR(255),
  user_id BIGINT NOT NULL,
  CONSTRAINT UK_business_user UNIQUE (user_id),
  CONSTRAINT fk_business_profile_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- investor_profile
CREATE TABLE investor_profile (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  contact_email VARCHAR(255),
  contact_name VARCHAR(255),
  contact_phone VARCHAR(255),
  interests TEXT,
  investment_range VARCHAR(255),
  photo_url VARCHAR(255),
  state VARCHAR(255),
  user_id BIGINT NOT NULL,
  CONSTRAINT UK_investor_user UNIQUE (user_id),
  CONSTRAINT fk_investor_profile_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- investor_saved_business
CREATE TABLE investor_saved_business (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  saved_at DATETIME(6) NOT NULL,
  business_id BIGINT NOT NULL,
  investor_id BIGINT NOT NULL,
  CONSTRAINT fk_isb_business FOREIGN KEY (business_id) REFERENCES business_profile(id),
  CONSTRAINT fk_isb_investor FOREIGN KEY (investor_id) REFERENCES investor_profile(id)
);

-- refresh_tokens
CREATE TABLE refresh_tokens (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  token_hash VARCHAR(128) NOT NULL UNIQUE,
  created_at DATETIME(6) NOT NULL,
  expires_at DATETIME(6) NOT NULL,
  revoked_at DATETIME(6) NULL,
  CONSTRAINT fk_rt_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_rt_user ON refresh_tokens(user_id);
