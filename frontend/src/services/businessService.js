import api from "./api";

// PUBLIC
export const getAllPublishedBusinesses = () => api.get("/api/businesses");
export const getBusinessProfileById = (profileId) => api.get(`/api/businesses/profile/${profileId}`);

// ME (BUSINESS)
export const getMyBusiness = () => api.get("/api/me/business");
export const updateMyBusiness = (payload) => api.put("/api/me/business", payload);
export const setPublishMyBusiness = (value) => api.patch(`/api/me/business/publish`, null, { params: { value }});
export const getInterestedInMyBusiness = () => api.get("/api/me/business/interested");