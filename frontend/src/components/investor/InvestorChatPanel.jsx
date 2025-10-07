import { useEffect, useMemo, useState } from "react";
import "../business/InvestorChatPanel.css";

const formatTime = (date) =>
  date.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });

const InvestorChatPanel = ({ business, messages = [], onSendMessage, onBack }) => {
  const [draft, setDraft] = useState("");

  useEffect(() => {
    setDraft("");
  }, [business?.id]);

  const sortedMessages = useMemo(
    () =>
      [...messages].sort(
        (a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime(),
      ),
    [messages],
  );

  const handleSubmit = (event) => {
    event.preventDefault();
    const trimmed = draft.trim();
    if (!trimmed) return;

    onSendMessage(trimmed);
    setDraft("");
  };

  return (
    <div className="chat-panel">
      <div className="chat-header">
        <button type="button" className="chat-back-button" onClick={onBack} aria-label="Back to saved businesses">
          ←
        </button>
        <div className="chat-header-title">
          {business?.contactName ? `${business.contactName} (${business.businessName})` : business?.businessName}
        </div>
      </div>

      <div className="chat-body">
        {sortedMessages.map((message) => (
          <div
            key={message.id}
            className={`chat-message ${message.from === "investor" ? "chat-message-out" : "chat-message-in"}`}
          >
            <div className="chat-bubble">{message.text}</div>
            <span className="chat-timestamp">{formatTime(new Date(message.createdAt))}</span>
          </div>
        ))}
      </div>

      <form className="chat-input" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Type a message..."
          value={draft}
          onChange={(event) => setDraft(event.target.value)}
        />
        <button type="submit">Send</button>
      </form>
    </div>
  );
};

export default InvestorChatPanel;