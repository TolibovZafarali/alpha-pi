import axios from "axios";
import { clearAuth, getAccessToken, getRefreshToken } from "../utils/auth";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",
    withCredentials: false,
})

// === attach Authorization: Bearer <accessToken> ===
api.interceptors.request.use((config) => {
    const token = getAccessToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

// === automatic refresh on 401, once ===
let isRefreshing = false;
let queue = [];

function flushQueue(err, token = null) {
    queue.forEach((p) => (err ? p.reject(err) : p.resolve(token)));
    queue = [];
}

api.interceptors.response.use(
    (res) => res,
    async (error) => {
        const original = error.config;
        if (!error.response) throw error;

        // if unauthorized and not already retried, try refresh
        if (error.response.status === 401 && !original._retry) {
            if (isRefreshing) {
                // wait until the current refresh finishes
                return new Promise((resolve, reject) => {
                    queue.push({ resolve, reject });
                }).then((token) => {
                    original.headers.Authorization = `Bearer ${token}`;
                    return api(original);
                });
            }

            original._retry = true;
            isRefreshing = true;

            try {
                const rt = getRefreshToken();
                if (!rt) throw new Error("no refresh token");
                const { data } = await axios.post(
                    `${api.defaults.baseURL}/api/auth/refresh`,
                    { refreshToken: rt },
                    { headers: { "Content-Type": "application/json"} }
                );
                // backend returns { accessToken, refreshToken }
                setTokens({ accessToken: data.accessToken, refreshToken: data.refreshToken });
                flushQueue(null, data.accessToken);
                original.headers.Authorization = `Bearer ${data.accessToken}`;
                return api(original);
            } catch (e) {
                flushQueue(e, null);
                clearAuth();
                throw e;
            } finally {
                isRefreshing = false;
            }
        }

        throw error;
    }
)

export default api;