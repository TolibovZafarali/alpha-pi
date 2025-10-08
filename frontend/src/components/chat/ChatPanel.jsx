import { useEffect, useMemo, useRef, useState } from "react";
import { getAuth } from "../../utils/auth";
import "../business/InvestorChatPanel.css";

const formatTime = (value) => {
  const date = value instanceof Date ? value : new Date(value);
  if (Number.isNaN(date.getTime())) return "";
  return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
};

const ChatPanel = ({
  title,
  subtitle,
  messages = [],
  onSendMessage,
  onBack,
  loadingMessages = false,
  sendingMessage = false,
  disableInput = false,
  statusMessage,
  emptyStateMessage = "No messages yet. Start the conversation!",
}) => {
  const [draft, setDraft] = useState("");
  const { id: currentUserId } = getAuth();
  const bodyEndRef = useRef(null);

  useEffect(() => {
    setDraft("");
  }, [title]);

  useEffect(() => {
    bodyEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages, loadingMessages]);

  const sortedMessages = useMemo(() => {
    return [...messages]
      .filter(Boolean)
      .sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
  }, [messages]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const trimmed = draft.trim();
    if (!trimmed || !onSendMessage || disableInput) return;

    try {
      const result = onSendMessage(trimmed);
      if (result && typeof result.then === "function") {
        await result;
      }
      setDraft("");
    } catch (error) {
      console.error("Failed to send message", error);
    }
  };

  const handleDraftChange = (event) => {
    setDraft(event.target.value);
  };

  const isInputDisabled = disableInput || sendingMessage || !onSendMessage;

  return (
    <div className="chat-panel">
      <div className="chat-header">
        <button type="button" className="chat-back-button" onClick={onBack} aria-label="Back" disabled={!onBack}>
          ←
        </button>
        <div>
          <div className="chat-header-title">{title}</div>
          {subtitle ? <div className="chat-subtitle">{subtitle}</div> : null}
        </div>
      </div>

      {statusMessage ? <div className="chat-status">{statusMessage}</div> : null}

      <div className="chat-body">
        {loadingMessages ? (
          <div className="chat-loading">Loading messages...</div>
        ) : sortedMessages.length === 0 ? (
          <div className="chat-empty">{emptyStateMessage}</div>
        ) : (
          sortedMessages.map((message) => {
            const isFromMe = currentUserId && String(message.senderId) === String(currentUserId);
            const bubbleClassName = `chat-message ${isFromMe ? "chat-message-out" : "chat-message-in"}`;
            return (
              <div
                key={message.id ?? `${message.senderId}-${message.createdAt}`}
                className={bubbleClassName}
              >
                <div className="chat-bubble">{message.content ?? message.text}</div>
                <span className="chat-timestamp">{formatTime(message.createdAt)}</span>
              </div>
            );
          })
        )}
        <div ref={bodyEndRef} />
      </div>

      <form className="chat-input" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder={isInputDisabled ? "Messaging disabled" : "Type a message..."}
          value={draft}
          onChange={handleDraftChange}
          disabled={isInputDisabled}
        />
        <button type="submit" disabled={isInputDisabled || !draft.trim()}>
          {sendingMessage ? "Sending..." : "Send"}
        </button>
      </form>
    </div>
  );
};

export default ChatPanel;