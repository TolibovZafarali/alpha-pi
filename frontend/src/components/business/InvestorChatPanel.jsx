import ChatPanel from "../chat/ChatPanel";
import "./InvestorChatPanel.css";

const InvestorChatPanel = ({
  investor,
  conversation,
  messages,
  loadingMessages,
  sendingMessage,
  disableInput,
  statusMessage,
  onSendMessage,
  onBack,
}) => {
  const title = investor?.contactName || conversation?.otherUser?.name || "Conversation";
  const subtitle = investor?.contactEmail || conversation?.otherUser?.name || "";

  return (
    <ChatPanel
      title={title}
      subtitle={subtitle}
      messages={messages}
      loadingMessages={loadingMessages}
      sendingMessage={sendingMessage}
      disableInput={disableInput}
      statusMessage={statusMessage}
      onSendMessage={onSendMessage}
      onBack={onBack}
      emptyStateMessage="No messages yet. Say hello!"
    />
  );
};

export default InvestorChatPanel;