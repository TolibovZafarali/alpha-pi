import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/saved';

// Save a business for an investor
export const saveBusiness = (investorId, businessId) => {
  return axios.post(`${BASE_URL}/${investorId}/${businessId}`);
};

// Unsave a business for an investor
export const unsaveBusiness = (investorId, businessId) => {
  return axios.delete(`${BASE_URL}/${investorId}/${businessId}`);
};

// Get list of investors who saved a specific business
export const getInterestedInvestors = (businessId) => {
  return axios.get(`${BASE_URL}/business/${businessId}`);
};
