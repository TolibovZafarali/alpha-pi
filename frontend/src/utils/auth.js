// Save ID and user type
export const saveAuth = (id, userType) => {
  localStorage.setItem("userId", id);
  localStorage.setItem("userType", userType);

  window.dispatchEvent(new Event("authChanged"));
};
  
// Get current auth info
export const getAuth = () => ({
  id: localStorage.getItem("userId"),
  type: localStorage.getItem("userType"),
});

// Clear on logout
export const clearAuth = () => {
  localStorage.clear();
  window.dispatchEvent(new Event("authChanged"));
}
