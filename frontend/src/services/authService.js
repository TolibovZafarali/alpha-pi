import { clearAuth, setTokens, setUser } from "../utils/auth";
import api from "./api";

export async function signup({ email, password, role }) {
    const { data } = await api.post("/api/auth/signup", { email, password, role });
    // backend return: { userId, email, role, message, accessToken, refreshToken }
    setUser({ userId: data.userId, role: data.role, email: data.email });
    setTokens({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken ?? data.refresh ?? data.refresh_token,
    })
    return data;
}

export async function login({ email, password }) {
    const { data } = await api.post("/api/auth/login", { email, password });
    setUser({ userId: data.userId, role: data.role, email: data.email });
    setTokens({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken ?? data.refresh ?? data.refresh_token,
    })
    return data;
}

export async function logout() {
    try {
        const rt = localStorage.getItem("refreshToken");
        if (rt) await api.post("/api/auth/logout", { refreshToken: rt });
    } catch { /* ignore */ }
    clearAuth();
}