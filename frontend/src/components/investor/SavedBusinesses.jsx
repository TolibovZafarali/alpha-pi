import { useCallback, useEffect, useMemo, useState } from "react";
import formatPhoneNumber from "../../utils/getPhoneNumber";
import InvestorChatPanel from "./InvestorChatPanel";
import {
  getConversationMessages,
  markConversationRead,
  sendMessage,
  startConversation,
} from "../../services/chatService";
import "./SavedBusinesses.css";

const normalizeMessages = (page) => {
  const items = page?.content || [];
  return [...items]
    .map((message) => ({
      id: message.id,
      senderId: message.senderId,
      content: message.content,
      createdAt: message.createdAt,
    }))
    .reverse();
};

const SavedBusinesses = ({ savedBusinesses, onRemove }) => {
  const [expandedMoreId, setExpandedMoreId] = useState(null);
  const [activeBusinessId, setActiveBusinessId] = useState(null);
  const [activeConversation, setActiveConversation] = useState(null);
  const [messages, setMessages] = useState([]);
  const [loadingMessages, setLoadingMessages] = useState(false);
  const [sendingMessage, setSendingMessage] = useState(false);
  const [chatStatus, setChatStatus] = useState("");

  const activeBusiness = useMemo(
    () => savedBusinesses.find((business) => business.id === activeBusinessId) || null,
    [activeBusinessId, savedBusinesses],
  );

  useEffect(() => {
    if (activeBusinessId && !savedBusinesses.some((b) => b.id === activeBusinessId)) {
      setActiveBusinessId(null);
      setActiveConversation(null);
      setMessages([]);
      setChatStatus("");
    }
  }, [activeBusinessId, savedBusinesses]);

  const loadMessages = useCallback(async (conversationId) => {
    try {
      setChatStatus("");
      const { data } = await getConversationMessages(conversationId);
      const normalized = normalizeMessages(data);
      setMessages(normalized);
      await markConversationRead(conversationId);
    } catch (error) {
      console.error("Failed to load chat messages", error);
      setMessages([]);
      setChatStatus("Unable to load messages right now. Please try again later.");
    }
  }, []);

  const handleToggleMore = (id) => {
    setExpandedMoreId(expandedMoreId === id ? null : id);
  };

  const handleOpenChat = useCallback(
    async (business) => {
      if (!business?.id) return;

      setActiveBusinessId(business.id);
      setChatStatus("");
      setMessages([]);
      setLoadingMessages(true);

      try {
        const { data } = await startConversation({ businessId: business.id });
        setActiveConversation(data);
        if (data?.id) {
          await loadMessages(data.id);
        } else {
          setChatStatus("Conversation is not ready yet. Please try again later.");
        }
      } catch (error) {
        console.error("Failed to open chat", error);
        setActiveConversation(null);
        setChatStatus("Unable to start a conversation with this business right now.");
      } finally {
        setLoadingMessages(false);
      }
    },
    [loadMessages],
  );

  const handleSendMessage = useCallback(
    async (text) => {
      if (!activeConversation?.id) return;
      try {
        setSendingMessage(true);
        setChatStatus("");
        const { data } = await sendMessage(activeConversation.id, text);
        if (data) {
          setMessages((prev) => [
            ...prev,
            {
              id: data.id,
              senderId: data.senderId,
              content: data.content,
              createdAt: data.createdAt,
            },
          ]);
        }
      } catch (error) {
        console.error("Failed to send message", error);
        setChatStatus("Message failed to send. Please try again.");
      } finally {
        setSendingMessage(false);
      }
    },
    [activeConversation?.id],
  );

  const handleCloseChat = () => {
    setActiveBusinessId(null);
    setActiveConversation(null);
    setMessages([]);
    setChatStatus("");
  };

  if (!savedBusinesses || savedBusinesses.length === 0) {
    return (
      <div className="saved-businesses">
        <p>No saved businesses yet.</p>
      </div>
    );
  }

  if (activeBusiness) {
    return (
      <div className="saved-businesses chat-active">
        <InvestorChatPanel
          business={activeBusiness}
          conversation={activeConversation}
          messages={messages}
          loadingMessages={loadingMessages}
          sendingMessage={sendingMessage}
          disableInput={loadingMessages || !activeConversation?.id}
          statusMessage={chatStatus}
          onSendMessage={handleSendMessage}
          onBack={handleCloseChat}
        />
      </div>
    );
  }

  return (
    <div className="saved-businesses">
      {savedBusinesses.map((business) => {
        const isMoreOpen = expandedMoreId === business.id;

        return (
          <div key={business.id} className="business-card">
            <div className="business-card-top">
              {/* Logo */}
              <div className="business-logo">
                <img
                  src={business.logoUrl || "/LOGO.svg"}
                  alt="logo"
                  onError={(e) => {
                    if (e.target.src.endsWith("/LOGO.svg")) return;
                    e.target.src = "/LOGO.svg";
                  }}
                />
              </div>

              {/* Name + Industry */}
              <div className="business-name-industry">
                <div className="business-name">{business.businessName}</div>
                <div className="business-industry">{business.industry}</div>
              </div>

              {/* Contact Info */}
              <div className="business-contact-info">
                <div>{business.contactName}</div>
                <div>{business.contactEmail}</div>
                <div>{formatPhoneNumber(business.contactPhone)}</div>
              </div>

              {/* Action Buttons */}
              <div className="business-action-buttons">
                <button onClick={() => handleOpenChat(business)}>Message</button>
                <button onClick={() => onRemove(business.id)}>Remove</button>
                <button onClick={() => handleToggleMore(business.id)}>
                  {isMoreOpen ? "Close" : "More"}
                </button>
              </div>
            </div>

            {/* Expanded More Info */}
            {isMoreOpen && (
              <div className="business-more-info">
                <p>
                  <strong>Funding Goal:</strong> ${business.fundingGoal?.toLocaleString()}
                </p>
                <p>
                  <strong>Current Revenue:</strong> ${business.currentRevenue?.toLocaleString()}
                </p>
                <p>
                  <strong>Founded:</strong> {business.foundedDate}
                </p>
                <p>
                  <strong>Description:</strong> {business.description}
                </p>
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
};

export default SavedBusinesses;