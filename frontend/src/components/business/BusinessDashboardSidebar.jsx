import { useCallback, useEffect, useMemo, useState } from "react";
import InterestedInvestors from "./InterestedInvestors";
import PublishToggle from "./PublishToggle";
import InvestorChatPanel from "./InvestorChatPanel";
import {
  getConversationMessages,
  listConversations,
  markConversationRead,
  sendMessage,
  startConversation,
} from "../../services/chatService";
import "./BusinessDashboardSidebar.css";

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

const findConversationForInvestor = (investor, conversations) => {
  if (!investor) return null;
  const candidateId = investor.investorId ?? investor.id ?? null;
  const email = investor.contactEmail?.toLowerCase();

  return conversations.find((conversation) => {
    if (!conversation) return false;
    if (candidateId && conversation.investorId && conversation.investorId === candidateId) return true;
    if (email && conversation.otherUser?.name?.toLowerCase() === email) return true;
    return false;
  }) || null;
};

const BusinessDashboardSidebar = ({
  isProfileComplete,
  isPublished,
  onPublishChange,
  interestedInvestors = [],
  onChatViewChange,
}) => {
  const [activeInvestorId, setActiveInvestorId] = useState(null);
  const [activeConversation, setActiveConversation] = useState(null);
  const [messages, setMessages] = useState([]);
  const [loadingMessages, setLoadingMessages] = useState(false);
  const [sendingMessage, setSendingMessage] = useState(false);
  const [conversationStatus, setConversationStatus] = useState("");
  const [conversations, setConversations] = useState([]);

  const activeInvestor = useMemo(() => {
    if (!activeInvestorId) return null;

    return (
      interestedInvestors.find((investor) => {
        if (!investor) return false;
        if (investor.id && investor.id === activeInvestorId) return true;
        if (investor.investorId && investor.investorId === activeInvestorId) return true;
        if (investor.contactEmail && investor.contactEmail === activeInvestorId) return true;
        if (investor.contactName && investor.contactName === activeInvestorId) return true;
        return false;
      }) || null
    );
  }, [activeInvestorId, interestedInvestors]);

  const fetchConversations = useCallback(async () => {
    try {
      const { data } = await listConversations();
      const content = data?.content || [];
      setConversations(content);
      return content;
    } catch (error) {
      console.error("Failed to load conversations", error);
      return [];
    }
  }, []);

  useEffect(() => {
    fetchConversations();
  }, [fetchConversations]);

  useEffect(() => {
    if (!activeInvestorId) return;
    const stillExists = interestedInvestors.some((investor) => {
      if (!investor) return false;
      return (
        investor.id === activeInvestorId ||
        investor.investorId === activeInvestorId ||
        investor.contactEmail === activeInvestorId ||
        investor.contactName === activeInvestorId
      );
    });
    if (!stillExists) {
      setActiveInvestorId(null);
      setActiveConversation(null);
      setMessages([]);
      setConversationStatus("");
    }
  }, [activeInvestorId, interestedInvestors]);

  const loadMessages = useCallback(async (conversationId) => {
    try {
      setLoadingMessages(true);
      setConversationStatus("");
      const { data } = await getConversationMessages(conversationId);
      const normalized = normalizeMessages(data);
      setMessages(normalized);
      await markConversationRead(conversationId);
    } catch (error) {
      console.error("Failed to load chat messages", error);
      setMessages([]);
      setConversationStatus("Unable to load messages. Please try again later.");
    } finally {
      setLoadingMessages(false);
    }
  }, []);

  const ensureConversation = useCallback(async (investor) => {
    if (!investor) return null;

    const candidateId = investor.investorId ?? investor.id ?? null;
    if (candidateId) {
      try {
        const { data } = await startConversation({ investorId: candidateId });
        if (data) return data;
      } catch (error) {
        console.warn("Unable to start conversation directly", error);
      }
    }

    const fromState = findConversationForInvestor(investor, conversations);
    if (fromState) return fromState;

    const refreshed = await fetchConversations();
    return findConversationForInvestor(investor, refreshed);
  }, [conversations, fetchConversations]);

  const handleOpenChat = useCallback(async (investor) => {
    const identifier = investor?.investorId || investor?.id || investor?.contactEmail || investor?.contactName || null;
    setActiveInvestorId(identifier);
    setConversationStatus("");
    setMessages([]);

    try {
      const conversation = await ensureConversation(investor);
      if (!conversation) {
        setActiveConversation(null);
        setConversationStatus("No conversation yet. Ask the investor to reach out to start chatting.");
        return;
      }

      setActiveConversation(conversation);
      await loadMessages(conversation.id);
      await fetchConversations();
    } catch (error) {
      console.error("Failed to open chat", error);
      setActiveConversation(null);
      setConversationStatus("Unable to open this conversation. Please try again later.");
    }
  }, [ensureConversation, fetchConversations, loadMessages]);

  const handleCloseChat = useCallback(() => {
    setActiveInvestorId(null);
    setActiveConversation(null);
    setMessages([]);
    setConversationStatus("");
  }, []);

  const handleSendMessage = useCallback(async (text) => {
    if (!activeConversation?.id) return;

    try {
      setSendingMessage(true);
      setConversationStatus("");
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
        setActiveConversation((prev) =>
          prev
            ? {
                ...prev,
                lastMessage: {
                  senderId: data.senderId,
                  content: data.content,
                  createdAt: data.createdAt,
                },
              }
            : prev,
        );
      }
      await fetchConversations();
    } catch (error) {
      console.error("Failed to send message", error);
      setConversationStatus("Message failed to send. Please try again.");
    } finally {
      setSendingMessage(false);
    }
  }, [activeConversation?.id, fetchConversations]);

  const sidebarClassName = `dashboard-sidebar${activeInvestor ? " chat-active" : ""}`;

  useEffect(() => {
    if (typeof onChatViewChange === "function") {
      onChatViewChange(!!activeInvestor);
    }

    return () => {
      if (typeof onChatViewChange === "function") {
        onChatViewChange(false);
      }
    };
  }, [activeInvestor, onChatViewChange]);

  return (
    <div className={sidebarClassName}>
      {activeInvestor ? (
        <div className="chat-panel-container">
          <button
            type="button"
            className="chat-floating-back"
            onClick={handleCloseChat}
            aria-label="Back to interested investors"
          >
            ← Back
          </button>
          <InvestorChatPanel
            investor={activeInvestor}
            conversation={activeConversation}
            messages={messages}
            loadingMessages={loadingMessages}
            sendingMessage={sendingMessage}
            disableInput={loadingMessages || !activeConversation?.id}
            statusMessage={conversationStatus}
            onSendMessage={handleSendMessage}
            onBack={handleCloseChat}
          />
        </div>
      ) : (
        <>
          <h2 className="sidebar-header">Interested Investors</h2>

          <InterestedInvestors interestedInvestors={interestedInvestors} onMessage={handleOpenChat} />

          <div className="business-sidebar-toggle-bottom">
            <span className="publish-label">Post your profile:</span>
            <PublishToggle
              checked={isPublished}
              disabled={!isProfileComplete}
              onChange={onPublishChange}
            />
          </div>
        </>
      )}
    </div>
  );
};

export default BusinessDashboardSidebar;