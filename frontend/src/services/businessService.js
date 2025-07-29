import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/businesses';

// Sign up a new business owner
export const signupBusiness = (businessData) => {
  return axios.post(`${BASE_URL}/signup`, businessData);
};

// Log in a business owner
export const loginBusiness = (credentials) => {
  return axios.post(`${BASE_URL}/login`, credentials);
};

// Get a specific business profile by ID (includes interested investors)
export const getBusinessProfile = (id) => {
  return axios.get(`${BASE_URL}/${id}`);
};

// Update a business profile
export const updateBusinessProfile = (id, updatedData) => {
  return axios.put(`${BASE_URL}/${id}`, updatedData);
};

// Get all published businesses (for investor browsing)
export const getAllPublishedBusinesses = () => {
  return axios.get(`${BASE_URL}`);
};
