import api from "./api";

export const startConversation = (payload) =>
    api.post("/api/chat/start", payload);

export const listConversations = (page = 0, size = 20) =>
    api.get("/api/chat/conversations", { params: { page, size } });

export const getConversationMessages = (conversationId, page = 0, size = 30) =>
    api.get(`/api/chat/conversations/${conversationId}/messages`, {
        params: { page, size },
    });

export const sendMessage = (conversationId, content) =>
    api.post(`/api/chat/conversations/${conversationId}/messages`, { content });

export const markConversationRead = (conversationId) =>
    api.post(`/api/chat/conversations/${conversationId}/read`);

export const getUnreadCount = () => api.get("/api/chat/unread-count");