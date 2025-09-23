-- Conversations & Messages for 1:1 chat

CREATE TABLE conversations (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user1_id BIGINT NOT NULL,
  user2_id BIGINT NOT NULL,
  business_id BIGINT NULL,
  investor_id BIGINT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  CONSTRAINT fk_conv_user1 FOREIGN KEY (user1_id) REFERENCES users(id),
  CONSTRAINT fk_conv_user2 FOREIGN KEY (user2_id) REFERENCES users(id),
  CONSTRAINT fk_conv_business FOREIGN KEY (business_id) REFERENCES business_profile(id),
  CONSTRAINT fk_conv_investor FOREIGN KEY (investor_id) REFERENCES investor_profile(id),
  UNIQUE KEY uniq_user_pair (user1_id, user2_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE messages (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  conversation_id BIGINT NOT NULL,
  sender_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  created_at DATETIME(6) NOT NULL,
  read_at DATETIME(6) NULL,
  CONSTRAINT fk_msg_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
  CONSTRAINT fk_msg_sender FOREIGN KEY (sender_id) REFERENCES users(id),
  INDEX idx_msg_conversation_created (conversation_id, created_at),
  INDEX idx_msg_unread (conversation_id, read_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
