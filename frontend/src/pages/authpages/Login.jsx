import { useState } from "react";
import "./Signup.css"
import { useNavigate } from "react-router-dom";
import { login } from "../../services/authService";
import { getAuth } from "../../utils/auth";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            await login({ email, password });
            const { role } = getAuth();
            navigate(role === "BUSINESS" ? "/business/dashboard" : "/investor/dashboard");
        } catch (err) {
            setError(err?.response?.data || "Invalid credentials. Please check your email and password.")
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="signup-page">
            <div className="sign-page-div">
                <h1>Log In</h1>
                <form onSubmit={handleSubmit}>
                    <div className="input-wrapper">
                        <input
                            type="email"
                            placeholder=" "
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <label className="floating-label">Email</label>
                    </div>

                    <div className="input-wrapper">
                        <input 
                            type="password"
                            placeholder=" "
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                        <label className="floating-label">Password</label>
                    </div>

                    {error && <p className="error-message">{error}</p>}

                    <button type="submit" className="submit-btn" disabled={loading || !email || !password}>
                        {loading ? "Logging in..." : "Log In"}
                    </button>
                </form>
            </div>
        </div>
    );
}
 
export default Login;