import ChatPanel from "../chat/ChatPanel";
import "../business/InvestorChatPanel.css";

const InvestorChatPanel = ({
  business,
  conversation,
  messages,
  loadingMessages,
  sendingMessage,
  disableInput,
  statusMessage,
  onSendMessage,
  onBack,
}) => {
  const businessName = business?.businessName || conversation?.otherUser?.name || "Conversation";
  const title = business?.contactName ? `${business.contactName} (${businessName})` : businessName;
  const subtitle = business?.contactEmail || conversation?.otherUser?.name || "";

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
      emptyStateMessage="No messages yet. Start a conversation!"
    />
  );
};

export default InvestorChatPanel;