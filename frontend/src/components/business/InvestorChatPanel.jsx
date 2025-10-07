import { useEffect, useMemo, useState } from "react";
import "./InvestorChatPanel.css";

const formatTime = (date) =>
  date.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });

const createInitialMessages = (investor) => [
  {
    id: "intro",
    from: "investor",
    text: `Hi there! I'm ${investor.contactName} and I'd love to learn more about your business.`,
    createdAt: new Date(),
  },
];

const InvestorChatPanel = ({ investor, onBack }) => {
  const [messages, setMessages] = useState(() => createInitialMessages(investor));
  const [draft, setDraft] = useState("");

  useEffect(() => {
    setMessages(createInitialMessages(investor));
    setDraft("");
  }, [investor]);

  const sortedMessages = useMemo(
    () => [...messages].sort((a, b) => a.createdAt.getTime() - b.createdAt.getTime()),
    [messages],
  );

  const handleSubmit = (event) => {
    event.preventDefault();
    const trimmed = draft.trim();
    if (!trimmed) return;

    setMessages((prev) => [
      ...prev,
      {
        id: `me-${Date.now()}`,
        from: "business",
        text: trimmed,
        createdAt: new Date(),
      },
    ]);
    setDraft("");
  };

  return (
    <div className="chat-panel">
      <div className="chat-header">
        <button type="button" className="chat-back-button" onClick={onBack} aria-label="Back to investors">
          ←
        </button>
        <div className="chat-header-title">{investor.contactName}</div>
      </div>

      <div className="chat-body">
        {sortedMessages.map((message) => (
          <div
            key={message.id}
            className={`chat-message ${message.from === "business" ? "chat-message-out" : "chat-message-in"}`}
          >
            <div className="chat-bubble">{message.text}</div>
            <span className="chat-timestamp">{formatTime(message.createdAt)}</span>
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