import api from "./api";

// ME (INVESTOR)
export const getMyInvestor = () => api.get("/api/me/investor");
export const updateMyInvestor = (payload) => api.put("/api/me/investor", payload);