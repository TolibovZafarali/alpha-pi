import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/investors';

// Sign up a new investor
export const signupInvestor = (investorData) => {
  return axios.post(`${BASE_URL}/signup`, investorData);
};

// Log in an investor
export const loginInvestor = (credentials) => {
  return axios.post(`${BASE_URL}/login`, credentials);
};

// Get investor profile by ID (includes saved businesses)
export const getInvestorProfile = (id) => {
  return axios.get(`${BASE_URL}/${id}`);
};

// Update investor profile
export const updateInvestorProfile = (id, updatedData) => {
  return axios.put(`${BASE_URL}/${id}`, updatedData);
};
