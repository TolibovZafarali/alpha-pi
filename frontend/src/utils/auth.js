const LS = {
  userId: "userId",
  role: "userRole",
  email: "userEmail",
  at: "accessToken",
  rt: "refreshToken",
};

export const setUser = ({ userId, role, email }) => {
  if (userId != null) localStorage.setItem(LS.userId, String(userId));
  if (role) localStorage.setItem(LS.role, role);
  if (email) localStorage.setItem(LS.email, email);
  window.dispatchEvent(new Event("authChanged"));
};

export const setTokens = ({ accessToken, refreshToken }) => {
  if (accessToken) localStorage.setItem(LS.at, accessToken);
  if (refreshToken) localStorage.setItem(LS.rt, refreshToken);
  window.dispatchEvent(new Event("authChanged"));
};

export const getAccessToken = () => localStorage.getItem(LS.at) || "";
export const getRefreshToken = () => localStorage.getItem(LS.rt) || "";

export const getAuth = () = ({
  id: localStorage.getItem(LS.userId),
  role: localStorage.getItem(LS.role),
  email: localStorage.getItem(LS.email),
  accessToken: localStorage.getItem(LS.at),
  refreshToken: localStorage.getItem(LS.rt),
});

export const clearAuth = () => {
  Object.values(LS).forEach((k) => localStorage.removeItem(k));
  window.dispatchEvent(new Event("authChanged"));
};