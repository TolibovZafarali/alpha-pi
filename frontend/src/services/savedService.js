import api from "./api";

// uses identity from JWT; no investorId in URL
export const saveBusiness = (businessId) => api.post(`/api/me/saved/${businessId}`);
export const unsaveBusiness = (businessId) => api.delete(`/api/me/saved/${businessId}`);