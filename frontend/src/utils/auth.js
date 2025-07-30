// Save ID and user type
export const saveAuth = (id, userType, userName) => {
  localStorage.setItem("userId", id);
  localStorage.setItem("userType", userType);
  localStorage.setItem("userName", userName)
};
  
// Get current auth info
export const getAuth = () => ({
  id: localStorage.getItem("userId"),
  type: localStorage.getItem("userType"),
  name: localStorage.getItem("userName")
});

// Clear on logout
export const clearAuth = () => localStorage.clear();
