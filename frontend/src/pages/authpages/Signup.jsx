import { useState } from "react";
import { useNavigate } from "react-router-dom";
import getFullName from "../../utils/joinName";
import SignupRequest from "../../models/SignupRequest";
import { signupBusiness } from "../../services/businessService";
import { signupInvestor } from "../../services/investorService";
import { saveAuth } from "../../utils/auth";
import PasswordValidator from "../../utils/PasswordValidator";
import "./Signup.css"

const Signup = () => {
    const navigate = useNavigate();

    const [role, setRole] = useState("business");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [passwordMatch, setPasswordMatch] = useState(true);
    const [passwordValid, setPasswordValid] = useState(true);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const contactName = getFullName(firstName, lastName);
        const request = new SignupRequest(email, password);

        if (password != confirmPassword) {
            setPasswordMatch(false);
            setPasswordValid(true);
            return;
        } else if (!PasswordValidator(password)) {
            setPasswordMatch(true);
            setPasswordValid(false);
            return;
        }

        try {
            const response = role === "business"
                ? await signupBusiness(request)
                : await signupInvestor(request);

            const id = response.data;
            saveAuth(id, role, contactName);
            navigate(`/${role}/${id}`);
        } catch (err) {
            setError(err.response?.data || "Something went wrong.");
        }
    };
    
    return (
        <div className="signup-page">
            <div className="sign-page-div">
                <h1>Sign Up</h1>

                {/* Toggle */}
                <div className="role-toggle-switch">
                    <div className={`toggle-option ${role === "business" ? "active" : ""}`} onClick={() => setRole("business")}>
                        Business
                    </div>
                    <div className={`toggle-option ${role === "investor" ? "active" : ""}`} onClick={() => setRole("investor")}>
                        Investor
                    </div>
                    <div className={`toggle-slider ${role}`}></div>
                </div>

                {/* Form */}
                <form onSubmit={handleSubmit}>
                    <div className="name-row">
                        <div className="input-wrapper">
                            <input 
                                type="text"
                                placeholder=" "
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                required
                                />
                            <label className="floating-label">First Name</label>
                        </div>

                        <div className="input-wrapper">
                            <input 
                                type="text"
                                placeholder=" "
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                            <label className="floating-label">Last Name</label>
                        </div>
                    </div>

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

                    <div className="input-wrapper">
                        <input 
                            type="password"
                            placeholder=" "
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                        <label className="floating-label">Confirm Password</label>
                    </div>

                    {!passwordMatch && (
                        <p className="error-message">Passwords do not match.</p>
                    )}

                    {!passwordValid && (
                        <p className="error-message">
                            Your password must:
                            <br/>1. Be at least 8 characters long.
                            <br/>2. Contain at least one letter.
                            <br/>3. Contain at least one number.
                        </p>
                    )}

                    {error && <p className="error-message">{error}</p>}

                    <button type="submit" className="submit-btn" disabled={!firstName || !lastName || !email || !password || !confirmPassword}>Create Account</button>
                </form>
            </div>
        </div>
    );
}
 
export default Signup;